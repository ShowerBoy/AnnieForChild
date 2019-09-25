package com.lebo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aliyun.vodplayerview.activity.AliyunPlayerSkinActivity;
import com.aliyun.vodplayerview.playlist.AlivcPlayListAdapter_own;
import com.aliyun.vodplayerview.view.choice.AlivcShowScreenDialog;
import com.aliyun.vodplayerview.view.gesture.GestureView;
import com.aliyun.vodplayerview.view.more.ShowScreenView;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.netexpclass.VideoDefiniList;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.lebo.utils.AssetsUtil;
import com.lebo.utils.Logger;
import com.lebo.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeboActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG = "LeboActivity";
    private Context mContext;
    private Intent intent;
    private boolean isTime = false; //是否计时
    private boolean isDefinition;//判断是否有清晰度
    private boolean isComplete = false;//判断是否播放完成
    private int isWeb; //判断是否是网页视频播放 0:不是 1:是
    private float speed = 1f; //速度
    private boolean isLoop; //是否循环
    private boolean showLoop; //是否显示循环
    private int animationId, duration, isFinish, position;
    private String netid, stageid, unitid, chaptercontentid;
    private List<VideoList> videoList = new ArrayList<>(); //视频列表
    private String classcode;

    // SDK
    private LelinkHelper mLelinkHelper;
    private boolean isFirstBrowse = true;
    private BrowseAdapter mBrowseAdapter;
    private RecyclerView mBrowseRecyclerView;
    private LelinkServiceInfo mSelectInfo;
    private UIHandler mDelayHandler;


    private boolean isPlayMirror;
    private boolean isPause = false;
    private RecyclerView recyclerView;
    private AlivcPlayListAdapter_own alivcPlayListAdapter;
    private long oldTime;
    private SeekBar mProgressBar;
    private int currentVideoPosition;
    private TextView starttime, endtime;
    private ImageView play_control;
    private Boolean isloop = true;//代表列表循环
    private TextView loop_control;
    private Button quality_view;
    private List<VideoDefiniList> pathlist;
    PopupWindow popupWindow;
    public View popupView;
    private Button exit;
    private LinearLayout nocontent_layout;
    private LinearLayout llVideoList;
    private ImageView quit;
    private TextView text1;
    private String dev_name;//设备名字
    private TextView connect_info;
    private Button change_dev;
    private int current_position;
    private static int isexit;
    private TextView no_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lebo);

        mContext = this;
        //注册EventBus
        EventBus.getDefault().register(this);
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            initLelinkHelper();
        } else {
            ToastUtil.show(mContext, "请检查权限");
            // 若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
//            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_MUST_PERMISSION);
        }
        initdata();
    }

    private void initdata() {
        getbundle();
        text1=findViewById(R.id.text1);
        connect_info=findViewById(R.id.connect_info);
        change_dev=findViewById(R.id.change_dev);
        change_dev.setOnClickListener(this);
        text1.setText(dev_name);
        exit=findViewById(R.id.exit);
        exit.setOnClickListener(this);
        quit=findViewById(R.id.quit);
        quit.setOnClickListener(this);
        nocontent_layout = findViewById(R.id.nocontent_layout);
        llVideoList = findViewById(R.id.ll_video_list);
        mProgressBar = (SeekBar) findViewById(R.id.seekbar_progress);
        mProgressBar.setOnSeekBarChangeListener(mProgressChangeListener);
        starttime = findViewById(R.id.starttime);
        endtime = findViewById(R.id.endtime);
        quality_view = findViewById(R.id.quality_view);
        quality_view.setOnClickListener(this);
        play_control = findViewById(R.id.play_control);
        play_control.setOnClickListener(this);
        loop_control = findViewById(R.id.loop_control);
        loop_control.setOnClickListener(this);

        recyclerView = findViewById(R.id.video_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        alivcPlayListAdapter = new AlivcPlayListAdapter_own(this, videoList, 2);
        recyclerView.setAdapter(alivcPlayListAdapter);
        alivcPlayListAdapter.setSelectVideo(currentVideoPosition);
        alivcPlayListAdapter.notifyDataSetChanged();
        alivcPlayListAdapter.setOnVideoListItemClick(new AlivcPlayListAdapter_own.OnVideoListItemClick() {
            @Override
            public void onItemClick(int position) {
                long currentClickTime = System.currentTimeMillis();
                // 防止快速点击
                if (currentClickTime - oldTime <= 2000) {
                    return;
                }
                if(popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                currentVideoPosition = position;
                play(videoList.get(currentVideoPosition).getUrl(),0);
                alivcPlayListAdapter.setSelectVideo(currentVideoPosition);
                alivcPlayListAdapter.notifyDataSetChanged();
                // 点击视频列表, 切换播放的视频
//                changePlaySource(position);
                oldTime = currentClickTime;
            }
        });
        if (videoList != null && videoList.size() > 0) {
            play(videoList.get(currentVideoPosition).getUrl(),0);
            if (videoList.get(currentVideoPosition).getPath() != null && videoList.get(currentVideoPosition).getPath().size() > 0) {
                quality_view.setVisibility(View.VISIBLE);
                quality_view.setText(videoList.get(currentVideoPosition).getPath().get(0).getTitle() + "");
            }
            else {
                quality_view.setVisibility(View.GONE);
            }
        }
//        if(videoList.size()<=1){
//            llVideoList.setVisibility(View.GONE);
//            nocontent_layout.setVisibility(View.VISIBLE);
//        }else{
//            llVideoList.setVisibility(View.VISIBLE);
//            nocontent_layout.setVisibility(View.GONE);
//        }
    }

    private void getbundle() {
        intent = getIntent();
//        View mCoverView = findViewById(R.id.CoverView);
//        mVideoView.setCoverView(mCoverView);
//        videoPath = intent.getStringExtra("url");

        isTime = intent.getBooleanExtra("isTime", false);
        isDefinition = intent.getBooleanExtra("isDefinition", false);
        showLoop = intent.getBooleanExtra("showLoop", true);

        isFinish = intent.getIntExtra("isFinish", 0);
        animationId = intent.getIntExtra("animationId", 0);
        isWeb = intent.getIntExtra("isWeb", 0);
        /**
         * {@link com.annie.annieforchild.ui.adapter.NetExpFirstVideoAdapter}
         */
        netid = intent.getStringExtra("netid");
        stageid = intent.getStringExtra("stageid");
        unitid = intent.getStringExtra("unitid");
        chaptercontentid = intent.getStringExtra("chaptercontentid");
        classcode = intent.getStringExtra("classcode");
        position = intent.getIntExtra("position", 0);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            videoList = (List<VideoList>) bundle.getSerializable("videoList");
            currentVideoPosition = bundle.getInt("videoPos");
            dev_name = bundle.getString("dev_name");
            isexit=bundle.getInt("isexit");
        }
    }

    private void initLelinkHelper() {
        mLelinkHelper = LelinkHelper.getInstance(mContext);
        mLelinkHelper.setUIUpdateListener(mUIUpdateListener);
//        mLelinkHelper.connect(mSelectInfo);
    }

    public void play(String url,int type) {
        isPlayMirror = false;
        if (null == mLelinkHelper) {
            ToastUtil.show(mContext, "未初始化或未选择设备");
            return;
        }
        List<LelinkServiceInfo> connectInfos = mLelinkHelper.getConnectInfos();
        if (null == connectInfos || connectInfos.isEmpty()) {
            ToastUtil.show(mContext, "请先连接设备");
            return;
        }
        if (isPause) {
            Logger.test(TAG, "resume click");
            isPause = false;
            // 暂停中
            mLelinkHelper.resume();
            return;
        } else {
            Logger.test(TAG, "play click");
        }
        // 网络media
        mLelinkHelper.playNetMedia(url, AllCast.MEDIA_TYPE_VIDEO, "");
        if(type==2){
            mLelinkHelper.seekTo(current_position);
        }
    }

    private void connect(LelinkServiceInfo info) {
        if (null != mLelinkHelper) {
            mLelinkHelper.connect(info);
        } else {
            ToastUtil.show(mContext, "未初始化或未选择设备");
        }
    }

    private IUIUpdateListener mUIUpdateListener = new IUIUpdateListener() {

        @Override
        public void onUpdate(int what, MessageDeatail deatail) {
            Logger.d(TAG, "onUpdateText : " + deatail.text + "\n\n");
            Logger.d(TAG, "IUIUpdateListener state:" + what + " text:" + deatail.text);
            switch (what) {
                case IUIUpdateListener.STATE_SEARCH_SUCCESS:
                    if (isFirstBrowse) {
                        isFirstBrowse = false;
                        Logger.test(TAG, "搜索成功");
                    }
                    if (null != mDelayHandler) {
                        mDelayHandler.removeCallbacksAndMessages(null);
                        mDelayHandler.sendEmptyMessageDelayed(IUIUpdateListener.STATE_SEARCH_SUCCESS,
                                TimeUnit.SECONDS.toMillis(1));
                    }
                    break;
                case IUIUpdateListener.STATE_SEARCH_ERROR:
                    ToastUtil.show(mContext, "Auth错误");
                    break;
                case IUIUpdateListener.STATE_SEARCH_NO_RESULT:
                    if (null != mDelayHandler) {
                        mDelayHandler.removeCallbacksAndMessages(null);
                        mDelayHandler.sendEmptyMessageDelayed(IUIUpdateListener.STATE_SEARCH_SUCCESS,
                                TimeUnit.SECONDS.toMillis(1));
                    }
                    break;
                case IUIUpdateListener.STATE_CONNECT_SUCCESS:
                    Logger.test(TAG, "connect success:" + deatail.text);
                    // 更新列表
//                    updateConnectAdapter();
                    Logger.d(TAG, "ToastUtil " + deatail.text);
                    connect_info.setText("已连接");
                    break;
                case IUIUpdateListener.STATE_DISCONNECT:
                    Logger.test(TAG, "disConnect success:" + deatail.text);
                    Logger.d(TAG, "ToastUtil " + deatail.text);
                    ToastUtil.show(mContext, deatail.text);
                    // 更新列表
//                    updateConnectAdapter();
                    break;
                case IUIUpdateListener.STATE_CONNECT_FAILURE:
                    Logger.test(TAG, "connect failure:" + deatail.text);
                    Logger.d(TAG, "ToastUtil " + deatail.text);
                    ToastUtil.show(mContext, deatail.text);
                    connect_info.setText("连接失败");
                    // 更新列表
//                    updateConnectAdapter();
                    break;
                case IUIUpdateListener.STATE_PLAY:
                    Logger.test(TAG, "callback play");
                    isPause = false;
                    play_control.setBackgroundResource(R.drawable.alivc_playstate_pause);
                    connect_info.setText("已连接");
//                    ToastUtil.show(mContext, "开始播放");
                    break;
                case IUIUpdateListener.STATE_LOADING:
                    Logger.test(TAG, "callback loading");
                    isPause = false;
                    Logger.d(TAG, "ToastUtil 开始加载");
                    connect_info.setText("已连接");
//                    ToastUtil.show(mContext, "开始加载");
                    break;
                case IUIUpdateListener.STATE_PAUSE:
                    Logger.test(TAG, "callback pause");
//                    ToastUtil.show(mContext, "暂停播放");
                    isPause = true;
                    play_control.setBackgroundResource(R.drawable.alivc_playstate_play);
                    break;
                case IUIUpdateListener.STATE_STOP:
                    Logger.test(TAG, "callback stop");
                    isPause = false;
//                    ToastUtil.show(mContext, "播放结束");
                    break;
                case IUIUpdateListener.STATE_SEEK:
                    Logger.test(TAG, "callback seek:" + deatail.text);
                    Logger.d(TAG, "ToastUtil seek完成:" + deatail.text);
                    break;
                case IUIUpdateListener.STATE_PLAY_ERROR:
                    Logger.test(TAG, "callback error:" + deatail.text);
                    ToastUtil.show(mContext, "播放错误：" + deatail.text);
                    break;
                case IUIUpdateListener.STATE_POSITION_UPDATE:
                    Logger.test(TAG, "callback position update:" + deatail.text);
                    long[] arr = (long[]) deatail.obj;
                    long duration = arr[0];
                    long position = arr[1];
                    Logger.d(TAG, "ToastUtil 总长度：" + duration + " 当前进度:" + position);
                    starttime.setText(ToastUtil.FormatRunTime(position));
                    endtime.setText(ToastUtil.FormatRunTime(duration));
                    mProgressBar.setMax((int) duration);
                    mProgressBar.setProgress((int) position);

                    break;
                case IUIUpdateListener.STATE_COMPLETION:
                    Log.e("isexit",isexit+"");
                    if(isexit==1){
                        disConnect(true);
                        if (null != mLelinkHelper ) {
                            mLelinkHelper.stop();
                        }
                        return;
                    }else{
                        if (isloop) {//需要列表循环
                            currentVideoPosition++;
                            if (currentVideoPosition > videoList.size() - 1) {
                                //列表循环播放，如发现播放完成了从列表的第一个开始重新播放
                                currentVideoPosition = 0;
                            }
                        } else {
                            //单曲循环
                        }
                        play(videoList.get(currentVideoPosition).getUrl(),0);

                        alivcPlayListAdapter.setSelectVideo(currentVideoPosition);
                        alivcPlayListAdapter.notifyDataSetChanged();
                    }
                    break;
                case IUIUpdateListener.STATE_INPUT_SCREENCODE:
                    Logger.test(TAG, "input screencode");
                    ToastUtil.show(mContext, deatail.text);
                    break;
                case IUIUpdateListener.RELEVANCE_DATA_UNSUPPORT:
                    Logger.test(TAG, "unsupport relevance data");
                    ToastUtil.show(mContext, deatail.text);
                    break;
                case IUIUpdateListener.STATE_SCREENSHOT:
                    Logger.test(TAG, "unsupport relevance data");
                    ToastUtil.show(mContext, deatail.text);
                    break;
            }
        }

    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        List<LelinkServiceInfo> connectInfos = null;
        if (null != mLelinkHelper) {
            connectInfos = mLelinkHelper.getConnectInfos();
        }
        switch (id) {
            case R.id.play_control:
                if (isPause) {//表示在暂停,需要播放
                    play_control.setBackgroundResource(R.drawable.alivc_playstate_pause);
                    isPause = false;
                    if (null != mLelinkHelper && null != connectInfos && !connectInfos.isEmpty()) {
                        Logger.test(TAG, "pause click");
                        mLelinkHelper.resume();
                    }
                } else {//表示在播放，需要暂停
                    play_control.setBackgroundResource(R.drawable.alivc_playstate_play);
                    isPause = true;
                    if (null != mLelinkHelper && null != connectInfos && !connectInfos.isEmpty()) {
                        Logger.test(TAG, "pause click");
                        mLelinkHelper.pause();
                    }
                }
                break;
            case R.id.loop_control:
                if (isloop) {//正在列表循环，需要变单曲
                    isloop = false;
                    Drawable dra= getResources().getDrawable(R.drawable.loop_unlist);
                    dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
                    loop_control.setCompoundDrawables(dra,null,null,null);
                    loop_control.setText("单曲循环");
                } else {
                    isloop = true;
                    Drawable dra= getResources().getDrawable(R.drawable.loop_list);
                    dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
                    loop_control.setCompoundDrawables(dra,null,null,null);
                    loop_control.setText("列表循环");
                }
                break;
            case R.id.quality_view:
                SystemUtils.setBackGray((Activity) this, true);
                getPopup(this);
//                getPopup(this).showAtLocation(v, Gravity.CENTER, 0,0);
                break;
            case R.id.exit:
                if (null != mLelinkHelper && null != connectInfos && !connectInfos.isEmpty()) {
                    mLelinkHelper.stop();
                }
                disConnect(true);
                finish();
                break;
            case R.id.quit:
                isexit=1;
                finish();
                break;
            case R.id.change_dev:
                showScreen();
                break;

        }
    }
    public  AlivcShowScreenDialog showScreenDialog;
    private void showScreen() {
        showScreenDialog = new AlivcShowScreenDialog(mContext,AliyunScreenMode.Small);
        ShowScreenView showScreenView = new ShowScreenView(LeboActivity.this, mContext,videoList,currentVideoPosition,2);
        showScreenDialog.setContentView(showScreenView);
        showScreenDialog.show();
    }
    public  void hideScreen(){
        if(showScreenDialog!=null && showScreenDialog.isShowing()){
            showScreenDialog.dismiss();
        }
    }
    private void disConnect(boolean isBtnClick) {
        List<LelinkServiceInfo> selectInfos = mLelinkHelper.getConnectInfos();
        if (null != mLelinkHelper && null != selectInfos) {
            if (isBtnClick) {
                //断开所有连接设备
                for (LelinkServiceInfo info : selectInfos) {
                    mLelinkHelper.disConnect(info);
                }
            } else {
                //断开除了当前选择连接以外的所有设备
                for (LelinkServiceInfo info : selectInfos) {
                    if (!AssetsUtil.isContains(mSelectInfo, info)) {
                        mLelinkHelper.disConnect(info);
                    }
                }
            }
        } else {
            if (isBtnClick) {
                ToastUtil.show(mContext, "未初始化或未选择设备");
            }
        }
    }



    public class UIHandler extends Handler {

        private WeakReference<ShowScreenView> mReference;

        UIHandler(ShowScreenView reference) {
            mReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            ShowScreenView mainActivity = mReference.get();
            if (mainActivity == null) {
                return;
            }
            switch (msg.what) {
                case IUIUpdateListener.STATE_SEARCH_SUCCESS:

//                    updateBrowseAdapter();
                    break;
                case IUIUpdateListener.STATE_SEARCH_NO_RESULT:

            }
            super.handleMessage(msg);
        }
    }

    private SeekBar.OnSeekBarChangeListener mProgressChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // ignore
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // ignore
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            if (seekBar.getId() == R.id.seekbar_progress) {
//                ToastUtil.show(mContext, "seek到" + progress);
                mLelinkHelper.seekTo(progress);
            }
//            else if (seekBar.getId() == R.id.seekbar_volume) {
//                Logger.test(TAG, "set volume:" + progress);
//                ToastUtil.show(mContext, "设置音量到" + progress);
//                mLelinkHelper.setVolume(progress);
//            }
        }
    };

    public  PopupWindow getPopup(Context context) {
        RecyclerView quality_list;
        ImageView quality_exit;
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_popup_quality, null, false);
        quality_list=popupView.findViewById(R.id.quality_list);
        quality_exit=popupView.findViewById(R.id.quality_exit);
        QualityAdapter adapter=new QualityAdapter(context, videoList.get(currentVideoPosition).getPath());
        adapter.setOnItemClick(new QualityAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                if(popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    SystemUtils.setBackGray(LeboActivity.this,false);
                }
                if(videoList.get(currentVideoPosition).getPath().get(position).getTitle().equals(quality_view.getText()+"")){
                    return;
                }else{
                    long currentClickTime = System.currentTimeMillis();
                    // 防止快速点击
                    if (currentClickTime - oldTime <= 2000) {
                        return;
                    }
                    current_position=(int)ToastUtil.formatTurnSecond(starttime.getText()+"");
                    Log.e("jjjj",current_position+"");
                    play(videoList.get(currentVideoPosition).getPath().get(position).getUrl(),2);
                    mLelinkHelper.seekTo(current_position);
                    quality_view.setText(videoList.get(currentVideoPosition).getPath().get(position).getTitle()+"");
                    adapter.setcurrentQuality(quality_view.getText()+"");
                    adapter.notifyDataSetChanged();
                    oldTime = currentClickTime;
                }
            }
        });
        quality_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    SystemUtils.setBackGray(LeboActivity.this,false);
                }
            }
        });

        adapter.setcurrentQuality(quality_view.getText()+"");
        quality_list.setAdapter(adapter);
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(popupView);

        getWindow().getDecorView().setPadding(0,0,0,0);
        popupWindow.showAtLocation(quality_view,Gravity.BOTTOM,0,0);
//        popupWindow.showAtLocation(quality_view, Gravity.NO_GRAVITY, location[0],
//                location[1]-popupHeight-quality_view.getHeight()*2-20);
//        popupWindow.showAsDropDown(quality_view, (location[0]+quality_view.getWidth()/2)-popupWidth/2,
//                location[1]+popupHeight,Gravity.NO_GRAVITY);

        return popupWindow;
    }
    @Override
    protected void onDestroy() {
        if(showScreenDialog!=null){
            if(showScreenDialog.isShowing()){
                showScreenDialog.dismiss();
            }
            showScreenDialog=null;
        }
        EventBus.getDefault().unregister(this);//反注册EventBus
        super.onDestroy();

    }
    @Subscribe
    public void onEventMainThread(JTMessage event) {
        if(event.what==999){
            if(showScreenDialog!=null && showScreenDialog.isShowing()){
                showScreenDialog.dismiss();
            }
            initLelinkHelper();
            LelinkServiceInfo info=(LelinkServiceInfo)event.obj;
            if(info!=null){
                text1.setText(""+info.getName());
                connect(info);
                play(videoList.get(currentVideoPosition).getPath().get(position).getUrl(),2);
            }
        }
    }

}
