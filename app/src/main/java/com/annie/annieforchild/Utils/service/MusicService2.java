package com.annie.annieforchild.Utils.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MusicManager;
import com.annie.annieforchild.Utils.SystemUtils;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnPreparedListener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wanglei on 2019/5/17.
 */

public class MusicService2 extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private Notification notification;//通知栏
    //    private MediaPlayer mediaPlayer;
    private PLMediaPlayer mMediaPlayer;
    private AVOptions mAVOptions;
    private static final String TAG = MusicService.class.getSimpleName();
    private int musicIndex; //播放位置
    private int musicPos; //播放进度
    private OnMusicEventListener mListener;
    private ExecutorService mProgressUpdatedListener = Executors.newSingleThreadExecutor();
    private PowerManager.WakeLock mWakeLock = null;//获取设备电源锁，防止锁屏后服务被停止

    public MusicService2() {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        start();
        if (mListener != null) {
            mListener.onChange(musicIndex);
        }
    }

    public class MyBinder extends Binder {
        public MusicService2 getService() {
            //绑定服务同时进行播放
//            play();
            return MusicService2.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        if (mListener != null) {
            mListener.onChange(musicIndex);
        }
    }

    @Override
    public void onDestroy() {
        release();
        stopForeground(true);
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startForeground(1, notification);//让服务前台运行
        startForeground(1, notification);
        return Service.START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        acquireWakeLock();
//        notification.contentIntent = pendingIntent;
//        notification.contentView = remoteViews;


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel("000", "安妮花", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(getApplicationContext(), "000").build();
//            startForeground(1, notification);
        } else {
            notification = new Notification(R.drawable.app_icon, "歌曲正在播放", System.currentTimeMillis());
        }
        //标记位，设置通知栏一直存在
        notification.flags = Notification.FLAG_ONGOING_EVENT;


        /**
         * step1:初始化播放列表，获取播放位置
         */
        MusicManager.getInstance().initMusicList();
//        musicIndex = (int) SpUtils.get(this, Constants.PLAY_POS, 0);
        musicIndex = 0;
        if (getPlayingPosition() < 0) {
            musicIndex = 0;
        }
        /**
         * mode1
         */
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setOnCompletionListener(this);
//        mediaPlayer.setOnPreparedListener(this);


        /**
         * mode2
         */
        mAVOptions = new AVOptions();
        // the unit of timeout is ms
        mAVOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        mAVOptions.setInteger(AVOptions.KEY_MEDIACODEC, 0);
        mAVOptions.setInteger(AVOptions.KEY_START_POSITION, 0);
        mMediaPlayer = new PLMediaPlayer(getApplicationContext(), mAVOptions);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        mMediaPlayer.setOnErrorListener(mOnErrorListener);
        mMediaPlayer.setOnInfoListener(mOnInfoListener);
        mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);


        mProgressUpdatedListener.execute(mPublishProgressRunnable);
    }

    /**
     * 播放
     *
     * @param position 音乐列表播放的位置
     * @return 当前播放的位置
     */
    public int play(int position) {
        if (MusicManager.getInstance().musicList.size() <= 0) {
            return -1;
        }
        if (position < 0) {
            position = 0;
        }
        if (position >= MusicManager.getInstance().musicList.size()) {
            position = MusicManager.getInstance().musicList.size() - 1;
        }

        /**
         * mode1
         */
//        try {
//            mediaPlayer.reset();
//            mediaPlayer.setDataSource(MusicManager.getInstance().musicList.get(position).getUrl());
//            mediaPlayer.prepareAsync();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * mode2
         */
        try {
            mMediaPlayer.setDataSource(MusicManager.getInstance().musicList.get(position).getPath());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        musicIndex = position;
//        SpUtils.put(Constants.PLAY_POS, musicIndex);
        return musicIndex;
    }

    /**
     * 继续播放
     *
     * @return 当前播放的位置 默认为0
     */
    public int resume() {
        /**
         * mode1
         */
//        if (mediaPlayer == null) {
//            return -1;
//        }
//        if (isPlaying())
//            return -1;
//        mediaPlayer.start();

        /**
         * mode2
         */
        if (mMediaPlayer == null) {
            return -1;
        }
        if (isPlaying())
            return -1;
        mMediaPlayer.start();
        return musicIndex;
    }

    private void start() {
//        mediaPlayer.start();
    }

    /**
     * 暂停播放
     *
     * @return 当前播放的位置
     */
    public int pause() {
        if (MusicManager.getInstance().musicList.size() == 0) {
            Toast.makeText(getApplicationContext(),
                    "当前手机没有MP3文件", Toast.LENGTH_LONG).show();
            return -1;
        }
        /**
         * mode1
         */
//        if (!isPlaying())
//            return -1;
//        mediaPlayer.pause();

        /**
         * mode2
         */
        if (!isPlaying())
            return -1;
        mMediaPlayer.pause();
        return musicIndex;
    }

    /**
     * 从指定播放位置播放
     *
     * @return
     */
    public int seekTo(int pos) {
        /**
         * mode1
         */
//        if (mediaPlayer == null) {
//            return -1;
//        }
//        musicPos = pos;
//        mediaPlayer.seekTo(pos);

        /**
         * mode2
         */
        if (mMediaPlayer == null) {
            return -1;
        }
        musicPos = pos;
        mMediaPlayer.seekTo(pos);
        return musicPos;
    }

    /**
     * 下一曲
     *
     * @return 当前播放的位置
     */
    public int next() {
        if (musicIndex >= MusicManager.getInstance().musicList.size() - 1) {
            return play(0);
        }
        return play(musicIndex + 1);
    }

    /**
     * 上一曲
     *
     * @return 当前播放的位置
     */
    public int last() {
        if (musicIndex <= 0) {
            return play(MusicManager.getInstance().musicList.size() - 1);
        }
        return play(musicIndex - 1);
    }

    // 申请设备电源锁
    private void acquireWakeLock() {
//        L.l(TAG, "正在申请电源锁");
        if (null == mWakeLock) {
            PowerManager pm = (PowerManager) this
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, "");
            if (null != mWakeLock) {
                mWakeLock.acquire();
//                L.l(TAG, "电源锁申请成功");
            }
        }
    }

    // 释放设备电源锁
    private void releaseWakeLock() {
//        L.l(TAG, "正在释放电源锁");
        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;
//            L.l(TAG, "电源锁释放成功");
        }
    }

    /**
     * 更新进度的线程
     */
    private Runnable mPublishProgressRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                /**
                 * mode1
                 */
//                if (mediaPlayer != null && mediaPlayer.isPlaying() && mListener != null) {
//                    mListener.onPublish(mediaPlayer.getCurrentPosition());
//                }

                /**
                 * mode2
                 */
                if (mMediaPlayer != null && mMediaPlayer.isPlaying() && mListener != null) {
                    mListener.onPublish((int) mMediaPlayer.getCurrentPosition());
                }
            /*
             * SystemClock.sleep(millis) is a utility function very similar
			 * to Thread.sleep(millis), but it ignores InterruptedException.
			 * Use this function for delays if you do not use
			 * Thread.interrupt(), as it will preserve the interrupted state
			 * of the thread. 这种sleep方式不会被Thread.interrupt()所打断
			 */
                SystemClock.sleep(200);
            }
        }
    };


    /**
     * 服务销毁时，释放各种控件
     */
    private void release() {
        if (!mProgressUpdatedListener.isShutdown())
            mProgressUpdatedListener.shutdownNow();
        mProgressUpdatedListener = null;
        //释放设备电源锁
        releaseWakeLock();
        /**
         * mode1
         */
//        if (mediaPlayer != null)
//            mediaPlayer.release();
//        mediaPlayer = null;

        /**
         * mode2
         */
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public int getPlayingPosition() {
        return musicIndex;
    }

    /**
     * 获取当前正在播放音乐的总时长
     *
     * @return
     */
    public int getDuration() {
        if (!isPlaying())
            return 0;
        /**
         * mode1
         */
//        return mediaPlayer.getDuration();

        /**
         * mode2
         */
        return (int) mMediaPlayer.getDuration();
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        /**
         * mode1
         */
//        return null != mediaPlayer && mediaPlayer.isPlaying();

        /**
         * mode2
         */
        return null != mMediaPlayer && mMediaPlayer.isPlaying();
    }

    /**
     * 设置回调
     *
     * @param l
     */
    public void setOnMusicEventListener(OnMusicEventListener l) {
        mListener = l;
    }

    /**
     * 音乐播放回调接口
     */
    public interface OnMusicEventListener {
        public void onPublish(int percent);

        public void onChange(int position);
    }


    private PLOnPreparedListener mOnPreparedListener = new PLOnPreparedListener() {
        @Override
        public void onPrepared(int preparedTime) {
            Log.i(TAG, "On Prepared !");
            mMediaPlayer.start();
            if (mListener != null) {
                mListener.onChange(musicIndex);
            }
//            mIsStopped = false;
        }
    };

    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    break;
                default:
                    break;
            }
        }
    };

    private PLOnBufferingUpdateListener mOnBufferingUpdateListener = new PLOnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(int percent) {
            Log.d(TAG, "onBufferingUpdate: " + percent + "%");
        }
    };

    /**
     * Listen the event of playing complete
     * For playing local file, it's called when reading the file EOF
     * For playing network stream, it's called when the buffered bytes played over
     * <p>
     * If setLooping(true) is called, the player will restart automatically
     * And ｀onCompletion｀ will not be called
     */
    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
            Log.d(TAG, "Play Completed !");
            next();
        }
    };

    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    SystemUtils.show(getApplicationContext(), "IO Error !");
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
                    SystemUtils.show(getApplicationContext(), "failed to open player !");
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
                    SystemUtils.show(getApplicationContext(), "failed to seek !");
                    break;
                default:
                    SystemUtils.show(getApplicationContext(), "unknown error !");
                    break;
            }
//            finish();
            return true;
        }
    };

}
