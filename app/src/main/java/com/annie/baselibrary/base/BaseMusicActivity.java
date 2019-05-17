package com.annie.baselibrary.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.Utils.service.MusicService2;
import com.annie.annieforchild.bean.JTMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wanglei on 2019/5/17.
 */

public abstract class BaseMusicActivity extends BaseActivity{
    protected MusicService2.MyBinder mBinder;
    protected MusicService2 musicService;
    protected ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService2.MyBinder) service;
            musicService = mBinder.getService();
            musicService.setOnMusicEventListener(mMusicEventListener);
            onChange(musicService.getPlayingPosition());
            if (register) {
                if (!musicService.isPlaying()) {
                    JTMessage message = new JTMessage();
                    message.what = MethodCode.EVENT_MUSICSERVICE;
                    EventBus.getDefault().post(message);
                } else {
                    JTMessage message = new JTMessage();
                    message.what = MethodCode.EVENT_MUSICSERVICE2;
                    EventBus.getDefault().post(message);
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
}
