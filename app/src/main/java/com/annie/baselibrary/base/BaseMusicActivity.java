package com.annie.baselibrary.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;

import com.aliyun.utils.VcPlayerLog;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService2;
import com.annie.annieforchild.bean.JTMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wanglei on 2019/5/17.
 */

public abstract class BaseMusicActivity extends BaseActivity {
    protected boolean musicStart = false; //播放开关
    protected MusicService2.MyBinder mBinder;
    protected MusicService2 musicService;
    protected ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService2.MyBinder) service;
            musicService = mBinder.getService();
            musicService.setOnMusicEventListener(mMusicEventListener);
//            if (SystemUtils.MusicType == 0) {
//                musicService.setMusicPos(MusicService2.lastMusicPos);
//                musicService.setMusicIndex(MusicService2.lastMusicDuration);
//            }
            onChange(musicService.getMusicIndex());
            if (SystemUtils.MusicType == 1) {
                if (musicStart) {
                    SystemUtils.MusicType = 0;
                    if (!musicService.isPlaying()) {
                        JTMessage message = new JTMessage();
                        message.what = MethodCode.EVENT_UNPLAYING;
                        EventBus.getDefault().post(message);
                    } else {
                        JTMessage message = new JTMessage();
                        message.what = MethodCode.EVENT_ISPLAYING;
                        EventBus.getDefault().post(message);
                    }
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    /**
     * 音乐播放服务回调接口的实现类
     */
    private MusicService2.OnMusicEventListener mMusicEventListener =
            new MusicService2.OnMusicEventListener() {
                @Override
                public void onPublish(int progress) {
                    BaseMusicActivity.this.onPublish(progress);
                }

                @Override
                public void onChange(int position) {
                    BaseMusicActivity.this.onChange(position);
                }
            };

    public void allowBindService() {
        getApplicationContext().bindService(new Intent(this, MusicService2.class), myConnection, Context.BIND_AUTO_CREATE);
    }

    public void allowUnBindService() {
        getApplicationContext().unbindService(myConnection);
    }

    /**
     * 更新进度
     * 抽象方法由子类实现
     * 实现service与主界面通信
     *
     * @param progress 进度
     */
    public abstract void onPublish(int progress);

    /**
     * 切换歌曲
     * 抽象方法由子类实现
     * 实现service与主界面通信
     *
     * @param position 歌曲在list中的位置
     */
    public abstract void onChange(int position);

    protected boolean isStrangePhone() {
        boolean strangePhone = "mx5".equalsIgnoreCase(Build.DEVICE)
                || "Redmi Note2".equalsIgnoreCase(Build.DEVICE)
                || "Z00A_1".equalsIgnoreCase(Build.DEVICE)
                || "hwH60-L02".equalsIgnoreCase(Build.DEVICE)
                || "hermes".equalsIgnoreCase(Build.DEVICE)
                || ("V4".equalsIgnoreCase(Build.DEVICE) && "Meitu".equalsIgnoreCase(Build.MANUFACTURER))
                || ("m1metal".equalsIgnoreCase(Build.DEVICE) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER));

        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;
    }
}
