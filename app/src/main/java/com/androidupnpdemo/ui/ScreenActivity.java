package com.androidupnpdemo.ui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.androidupnpdemo.Config;
import com.androidupnpdemo.Intents;
import com.androidupnpdemo.control.ClingPlayControl;
import com.androidupnpdemo.control.callback.ControlCallback;
import com.androidupnpdemo.control.callback.ControlReceiveCallback;
import com.androidupnpdemo.entity.ClingDevice;
import com.androidupnpdemo.entity.ClingDeviceList;
import com.androidupnpdemo.entity.DLANPlayState;
import com.androidupnpdemo.entity.IDevice;
import com.androidupnpdemo.entity.IResponse;
import com.androidupnpdemo.listener.BrowseRegistryListener;
import com.androidupnpdemo.listener.DeviceListChangedListener;
import com.androidupnpdemo.service.ClingUpnpService;
import com.androidupnpdemo.service.manager.ClingManager;
import com.androidupnpdemo.service.manager.DeviceManager;
import com.androidupnpdemo.util.Utils;
import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.ui.activity.VideoActivity_new;

import org.fourthline.cling.model.meta.Device;

import java.util.Collection;
import java.util.List;

public class ScreenActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        SeekBar.OnSeekBarChangeListener {

    private static final String TAG = ScreenActivity.class.getSimpleName();
    /** 连接设备状态: 播放状态 */
    public static final int PLAY_ACTION = 0xa1;
    /** 连接设备状态: 暂停状态 */
    public static final int PAUSE_ACTION = 0xa2;
    /** 连接设备状态: 停止状态 */
    public static final int STOP_ACTION = 0xa3;
    /** 连接设备状态: 转菊花状态 */
    public static final int TRANSITIONING_ACTION = 0xa4;
    /** 获取进度 */
    public static final int GET_POSITION_INFO_ACTION = 0xa6;
    /** 投放失败 */
    public static final int ERROR_ACTION = 0xa5;

    private Context mContext;
    private Handler mHandler = new InnerHandler();

    private ListView mDeviceList;
    private SwipeRefreshLayout mRefreshLayout;
    private TextView mTVSelected;
    private SeekBar mSeekProgress;
    private SeekBar mSeekVolume;
    private Switch mSwitchMute;

    private BroadcastReceiver mTransportStateBroadcastReceiver;
    private ArrayAdapter<ClingDevice> mDevicesAdapter;
    private RelativeLayout screenstatus;
    private LinearLayout seeklayout;
    private LinearLayout volume_layout;
    private LinearLayout control_layout;
    private String url;
    private  Long duration;


    /**
     * 投屏控制器
     */
    private ClingPlayControl mClingPlayControl = new ClingPlayControl();

    /** 用于监听发现设备 */
    private BrowseRegistryListener mBrowseRegistryListener = new BrowseRegistryListener();

    private ServiceConnection mUpnpServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(TAG, "mUpnpServiceConnection onServiceConnected");

            ClingUpnpService.LocalBinder binder = (ClingUpnpService.LocalBinder) service;
            ClingUpnpService beyondUpnpService = binder.getService();

            ClingManager clingUpnpServiceManager = ClingManager.getInstance();
            clingUpnpServiceManager.setUpnpService(beyondUpnpService);
            clingUpnpServiceManager.setDeviceManager(new DeviceManager());

            clingUpnpServiceManager.getRegistry().addListener(mBrowseRegistryListener);
            //Search on service created.
            clingUpnpServiceManager.searchDevices();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.e(TAG, "mUpnpServiceConnection onServiceDisconnected");

            ClingManager.getInstance().setUpnpService(null);
        }
    };

    //    private ServiceConnection mSystemServiceConnection = new ServiceConnection() {
    //        @Override
    //        public void onServiceConnected(ComponentName className, IBinder service) {
    //            Log.e(TAG, "mSystemServiceConnection onServiceConnected");
    //
    //            SystemService.LocalBinder systemServiceBinder = (SystemService.LocalBinder) service;
    //            //Set binder to SystemManager
    //            ClingManager clingUpnpServiceManager = ClingManager.getInstance();
    ////            clingUpnpServiceManager.setSystemService(systemServiceBinde r.getService());
    //        }
    //
    //        @Override
    //        public void onServiceDisconnected(ComponentName className) {
    //            Log.e(TAG, "mSystemServiceConnection onServiceDisconnected");
    //
    //            ClingUpnpServiceManager.getInstance().setSystemService(null);
    //        }
    //    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        mContext = this;
        Intent intent = getIntent();
        if (intent != null) {
            url=intent.getStringExtra("url");
            duration=intent.getLongExtra("duration",0);
        }
        initView();
        initListeners();
        bindServices();
        registerReceivers();
        onRefresh();
    }


    private void registerReceivers() {
        //Register play status broadcast
        mTransportStateBroadcastReceiver = new TransportStateBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intents.ACTION_PLAYING);
        filter.addAction(Intents.ACTION_PAUSED_PLAYBACK);
        filter.addAction(Intents.ACTION_STOPPED);
        filter.addAction(Intents.ACTION_TRANSITIONING);
        filter.addAction(Intents.ACTION_POSITION_CALLBACK);
        registerReceiver(mTransportStateBroadcastReceiver, filter);
    }


    private void bindServices() {
        // Bind UPnP service
        Intent upnpServiceIntent = new Intent(ScreenActivity.this, ClingUpnpService.class);
        bindService(upnpServiceIntent, mUpnpServiceConnection, Context.BIND_AUTO_CREATE);
        // Bind System service
        //        Intent systemServiceIntent = new Intent(MainActivity.this, SystemService.class);
        //        bindService(systemServiceIntent, mSystemServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        // Unbind UPnP service
        unbindService(mUpnpServiceConnection);
        // Unbind System service
        //        unbindService(mSystemServiceConnection);
        // UnRegister Receiver
        unregisterReceiver(mTransportStateBroadcastReceiver);

        ClingManager.getInstance().destroy();
        ClingDeviceList.getInstance().destroy();
    }

    private void initView() {
        screenstatus=findViewById(R.id.screenstatus);
        control_layout=findViewById(R.id.control_layout);
        seeklayout=findViewById(R.id.seeklayout);
        volume_layout=findViewById(R.id.volume_layout);

        mDeviceList = (ListView) findViewById(R.id.lv_devices);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        mTVSelected = (TextView) findViewById(R.id.tv_selected);
        mSeekProgress = (SeekBar) findViewById(R.id.seekbar_progress);
        mSeekVolume = (SeekBar) findViewById(R.id.seekbar_volume);
        mSwitchMute = (Switch) findViewById(R.id.sw_mute);

        mDevicesAdapter = new DevicesAdapter(mContext);
        mDeviceList.setAdapter(mDevicesAdapter);

        /** 这里为了模拟 seek 效果(假设视频时间为 15s)，拖住 seekbar 同步视频时间，
         * 在实际中 使用的是片源的时间 */
        mSeekProgress.setMax(duration.intValue());

        // 最大音量就是 100，不要问我为什么
        mSeekVolume.setMax(100);
    }
    private void sethide(boolean hide){
        if(hide){
            seeklayout.setVisibility(View.GONE);
            screenstatus.setVisibility(View.GONE);
            control_layout.setVisibility(View.GONE);
            volume_layout.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
        }else{
            seeklayout.setVisibility(View.VISIBLE);
            screenstatus.setVisibility(View.VISIBLE);
            control_layout.setVisibility(View.VISIBLE);
            volume_layout.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.GONE);
        }
    }

    private void initListeners() {
        mRefreshLayout.setOnRefreshListener(this);

        mDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 选择连接设备
                ClingDevice item = mDevicesAdapter.getItem(position);
                if (Utils.isNull(item)) {
                    return;
                }

                ClingManager.getInstance().setSelectedDevice(item);

                Device device = item.getDevice();
                if (Utils.isNull(device)) {
                    return;
                }

                String selectedDeviceName = String.format(getString(R.string.selectedText), device.getDetails().getFriendlyName());
                mTVSelected.setText(selectedDeviceName);
                play();
                sethide(false);
            }
        });

        // 设置发现设备监听
        mBrowseRegistryListener.setOnDeviceListChangedListener(new DeviceListChangedListener() {
            @Override
            public void onDeviceAdded(final IDevice device) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        mDevicesAdapter.add((ClingDevice) device);
                    }
                });
            }

            @Override
            public void onDeviceRemoved(final IDevice device) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        mDevicesAdapter.remove((ClingDevice) device);
                    }
                });
            }
        });

        // 静音开关
        mSwitchMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mClingPlayControl.setMute(isChecked, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        Log.e(TAG, "setMute success");
                    }

                    @Override
                    public void fail(IResponse response) {
                        Log.e(TAG, "setMute fail");
                    }
                });
            }
        });

        mSeekProgress.setOnSeekBarChangeListener(this);
        mSeekVolume.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        mDeviceList.setEnabled(false);

        mRefreshLayout.setRefreshing(false);
        refreshDeviceList();
        mDeviceList.setEnabled(true);
    }

    /**
     * 刷新设备
     */
    private void refreshDeviceList() {
        Collection<ClingDevice> devices = ClingManager.getInstance().getDmrDevices();
        ClingDeviceList.getInstance().setClingDeviceList(devices);
        if (devices != null) {
            mDevicesAdapter.clear();
            mDevicesAdapter.addAll(devices);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_play:
                play();
                break;

            case R.id.bt_pause:
                pause();
                break;

            case R.id.bt_stop:
                stop();
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    /**
     * 停止
     */
    private void stop() {
        mClingPlayControl.stop(new ControlCallback() {
            @Override
            public void success(IResponse response) {
                Log.e(TAG, "stop success");
            }

            @Override
            public void fail(IResponse response) {
                Log.e(TAG, "stop fail");
            }
        });
    }

    /**
     * 暂停
     */
    private void pause() {
        mClingPlayControl.pause(new ControlCallback() {
            @Override
            public void success(IResponse response) {
                Log.e(TAG, "pause success");
            }

            @Override
            public void fail(IResponse response) {
                Log.e(TAG, "pause fail");
            }
        });
    }

    public void getPositionInfo() {
        mClingPlayControl.getPositionInfo(new ControlReceiveCallback() {
            @Override
            public void receive(IResponse response) {

            }

            @Override
            public void success(IResponse response) {

            }

            @Override
            public void fail(IResponse response) {

            }
        });
    }

    /**
     * 播放视频
     */
    private void play() {
        @DLANPlayState.DLANPlayStates int currentState = mClingPlayControl.getCurrentState();

        /**
         * 通过判断状态 来决定 是继续播放 还是重新播放
         */

        if (currentState == DLANPlayState.STOP) {
            mClingPlayControl.playNew(url, new ControlCallback() {

                @Override
                public void success(IResponse response) {
                    Log.e(TAG, "play success");
                    //                    ClingUpnpServiceManager.getInstance().subscribeMediaRender();
                    //                    getPositionInfo();
                    // TODO: 17/7/21 play success
                    ClingManager.getInstance().registerAVTransport(mContext);
                    ClingManager.getInstance().registerRenderingControl(mContext);
                }

                @Override
                public void fail(IResponse response) {
                    Log.e(TAG, "play fail");
                    mHandler.sendEmptyMessage(ERROR_ACTION);
                }
            });
        } else {
            mClingPlayControl.play(new ControlCallback() {
                @Override
                public void success(IResponse response) {
                    Log.e(TAG, "play success");
                }

                @Override
                public void fail(IResponse response) {
                    Log.e(TAG, "play fail");
                    mHandler.sendEmptyMessage(ERROR_ACTION);
                }
            });
        }
    }

    /******************* start progress changed listener *************************/

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "Start Seek");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "Stop Seek");
        int id = seekBar.getId();
        switch (id) {
            case R.id.seekbar_progress: // 进度

                int currentProgress = seekBar.getProgress() * 1000; // 转为毫秒
                mClingPlayControl.seek(currentProgress, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        Log.e(TAG, "seek success");
                    }

                    @Override
                    public void fail(IResponse response) {
                        Log.e(TAG, "seek fail");
                    }
                });
                break;

            case R.id.seekbar_volume:   // 音量

                int currentVolume = seekBar.getProgress();
                mClingPlayControl.setVolume(currentVolume, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        Log.e(TAG, "volume success");
                    }

                    @Override
                    public void fail(IResponse response) {
                        Log.e(TAG, "volume fail");
                    }
                });
                break;
        }
    }

    /******************* end progress changed listener *************************/

    private final class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PLAY_ACTION:
                    Log.i(TAG, "Execute PLAY_ACTION");
                    Toast.makeText(mContext, "正在投放", Toast.LENGTH_SHORT).show();
                    mClingPlayControl.setCurrentState(DLANPlayState.PLAY);

                    break;
                case PAUSE_ACTION:
                    Log.i(TAG, "Execute PAUSE_ACTION");
                    mClingPlayControl.setCurrentState(DLANPlayState.PAUSE);

                    break;
                case STOP_ACTION:
                    Log.i(TAG, "Execute STOP_ACTION");
                    mClingPlayControl.setCurrentState(DLANPlayState.STOP);
                    break;
                case TRANSITIONING_ACTION:
                    Log.i(TAG, "Execute TRANSITIONING_ACTION");
                    Toast.makeText(mContext, "正在连接", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR_ACTION:
                    Log.e(TAG, "Execute ERROR_ACTION");
                    Toast.makeText(mContext, "投放失败", Toast.LENGTH_SHORT).show();
                    break;
                case GET_POSITION_INFO_ACTION:
                    Log.e(TAG, "Execute ERROR_ACTION");
                    Bundle b = msg.getData();
                    Log.e("seek",b.getInt("seek",0)+"");
                    break;
            }
        }
    }

    /**
     * 接收状态改变信息
     */
    private class TransportStateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "Receive playback intent:" + action);
            if (Intents.ACTION_PLAYING.equals(action)) {
                mHandler.sendEmptyMessage(PLAY_ACTION);

            } else if (Intents.ACTION_PAUSED_PLAYBACK.equals(action)) {
                mHandler.sendEmptyMessage(PAUSE_ACTION);

            } else if (Intents.ACTION_STOPPED.equals(action)) {
                mHandler.sendEmptyMessage(STOP_ACTION);

            } else if (Intents.ACTION_TRANSITIONING.equals(action)) {
                mHandler.sendEmptyMessage(TRANSITIONING_ACTION);
            }else if(Intents.ACTION_POSITION_CALLBACK.equals(action)){
                Message message=new Message();
                Bundle b = new Bundle();// 存放数据
                int seek=0;
                if(intent!=null){
                    seek=intent.getIntExtra(Intents.EXTRA_POSITION,0);
                    Log.e("1111",intent.getIntExtra(Intents.EXTRA_POSITION,0)+"");
                }
                b.putInt("seek",seek);
                message.setData(b);
                mHandler.sendMessage(message);
            }
        }
    }
}