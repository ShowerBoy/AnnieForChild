package com.annie.annieforchild.Utils.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.song.MusicPart;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.pk.MusicPlayActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.SongView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wanglei on 2018/7/20.
 */

public class MusicService extends Service {
    public static MediaPlayer mediaPlayer; //声明操作媒体的对象
    public static int pos = 0; //记录播放的位置
    public static boolean isPlay; //false:未播放 true:播放中
    public static List<Song> musicList; //播放列表
    public static List<MusicPart> musicPartList; //
    public static String musicTitle = null; //播放曲名
    public static String musicImageUrl = null; //播放图片
    public static int musicNum; //单个列表播放总数量
    public static int listTag; //播放列表的位置
    public static String end; //当前音频总时长
    public static String start; //当前音频播放时长
    public static boolean singleLoop = false; //是否单曲循环
    public static Timer mTimer = new Timer();
    public static TimerTask task;
    public static int musicOrigin, musicAudioType, musicAudioSource, musicResourceId, musicCollectType;
    public static int musicDuration; //总时长(秒)
    public static int duration = 0; //播放时长(时长自增)
    public static int musicIsCollect; //是否收藏
    public static boolean listVisibility = false; //列表显示或隐藏
    public static String type = null;  //radio,collection,recently
    private static GrindEarPresenter presenter;
    public static SongView musicSongView;
    private static MyApplication application;

    public class MyBinder extends Binder {
        public MusicService getService() {
            //绑定服务同时进行播放
//            play();
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = (MyApplication) getApplicationContext();
        musicList = new ArrayList<>();
        musicPartList = new ArrayList<>();
        //初始化播放对象
        if (mediaPlayer == null) {
//            mediaPlayer = mediaPlayer.create(MusicService.this, R.raw.aaa);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setLooping(false);
        }
        //监听播放结束，释放资源
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCompletion(MediaPlayer mp) {
//                mediaPlayer.release();
                if (isPlay) {
                    if (singleLoop) {
                        if (duration > 0) {
                            presenter.uploadAudioTime(musicOrigin, musicAudioType, musicAudioSource, musicResourceId, duration);
                            duration = 0;
                        }
                        pos = 0;
                        play();
                    } else {
                        listTag++;
                        if (listTag >= musicNum) {
                            //列表循环
//                        if (task != null) {
//                            task.cancel();
//                        }
//                        stop();
//                        MusicPlayActivity.Complete();
                            listTag = 0;
                            pos = 0;
                            play();
                            if (MusicPlayActivity.isLyric) {
                                MusicPlayActivity.isLyric = false;
                                MusicPlayActivity.image.setVisibility(View.VISIBLE);
                                MusicPlayActivity.lyricRecycler.setVisibility(View.GONE);
                            }
                        } else {
                            if (duration > 0) {
                                presenter.uploadAudioTime(musicOrigin, musicAudioType, musicAudioSource, musicResourceId, duration);
                                duration = 0;
                            }
                            pos = 0;
                            play();
                            if (MusicPlayActivity.isLyric) {
                                MusicPlayActivity.isLyric = false;
                                MusicPlayActivity.image.setVisibility(View.VISIBLE);
                                MusicPlayActivity.lyricRecycler.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {

                }
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                MusicPlayActivity.seekBar.setSecondaryProgress(percent);
//                int currentProgress = MusicPlayActivity.seekBar.getMax() * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
//                musicSongView.dismissLoad();
                MusicPlayActivity.animation.start();
                musicDuration = mediaPlayer.getDuration() / 1000;
                int second = musicDuration % 60;
                String sss;
                if (second < 10) {
                    sss = "0" + second;
                    end = musicDuration / 60 + ":" + sss;
                } else {
                    end = musicDuration / 60 + ":" + second;
                }
                start = "0:00";
                MusicPlayActivity.end.setText(end);
                MusicPlayActivity.start.setText(start);
                MusicPlayActivity.state = MusicPlayActivity.STATE_PLAYING;
                mediaPlayer.start();
                MusicPlayActivity.animation.start();
                MusicPlayActivity.play.setImageResource(R.drawable.icon_music_pause_big);
                MusicPlayActivity.animation.start();
                MusicPlayActivity.name.setText(musicPartList.get(listTag).getName());
                if (musicList.get(listTag).getIsCollected() == 0) {
                    MusicPlayActivity.collect.setImageResource(R.drawable.icon_player_collection_f);
                } else {
                    MusicPlayActivity.collect.setImageResource(R.drawable.icon_player_collection_t);
                }
                Glide.with(MusicService.this).load(musicPartList.get(listTag).getImageUrl()).into(MusicPlayActivity.image);
                //TODO:
                setMusicTitle(musicPartList.get(listTag).getName(), musicPartList.get(listTag).getImageUrl(), musicOrigin, musicAudioType, musicAudioSource, musicPartList.get(listTag).getBookId(), musicList.get(listTag).getIsCollected(), musicCollectType, musicSongView);
                for (int i = 0; i < musicPartList.size(); i++) {
                    musicPartList.get(i).setPlaying(false);
                }
                musicPartList.get(MusicService.listTag).setPlaying(true);
                MusicPlayActivity.adapter.notifyDataSetChanged();
            }
        });
        presenter = new GrindEarPresenterImp(this);
        presenter.initViewAndData();
    }

    public static void setMusicPosition(int musicPosition) {
        if (musicNum != 0) {
            listTag = musicPosition;
        }
    }

    public static void setMusicList(List<Song> list) {
        if (musicList == null) {
            musicList = new ArrayList<>();
        }
        if (musicPartList == null) {
            musicPartList = new ArrayList<>();
        }
        pos = 0;
        musicList.clear();
        musicList.addAll(list);
        musicNum = musicList.size();
        musicPartList.clear();
        for (int i = 0; i < musicList.size(); i++) {
            MusicPart musicPart = new MusicPart();
            musicPart.setName(musicList.get(i).getBookName());
            musicPart.setBookId(musicList.get(i).getBookId());
            musicPart.setImageUrl(musicList.get(i).getBookImageUrl());
            musicPart.setMusicUrl(musicList.get(i).getPath());
            musicPartList.add(musicPart);
        }
    }

    public static void setMusicTitle(String title, String imageUrl, int origin, int audioType, int audioSource, int resourceId, int isCollect, int collectType, SongView songView) {
        musicTitle = title;
        musicImageUrl = imageUrl;
        musicOrigin = origin;
        musicAudioType = audioType;
        musicAudioSource = audioSource;
        musicResourceId = resourceId;
        musicIsCollect = isCollect;
        musicCollectType = collectType;
        musicSongView = songView;
    }

    public static void setMusicCollect(int isCollect) {
        musicIsCollect = isCollect;
        MusicService.musicList.get(listTag).setIsCollected(isCollect);
    }

    //用于开始播放的方法
    public static void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            isPlay = true;
            if (pos != 0) {
                //根据指定位置进行播放
//                MusicPlayActivity.animation.start();
                mediaPlayer.seekTo(pos);
                mediaPlayer.start();
            } else {
//                if (musicSongView != null) {
//                    musicSongView.showLoad();
//                }
//                MusicPlayActivity.showLoad();
                //首次或从头播放，并显示通知
//                    notificationManager.notify(200, notification);
//                mediaPlayer.stop();
//                mediaPlayer.prepare();
//                mediaPlayer.start();
                try {
                    /*数组越界*/
                    if (musicList != null && musicList.size() > 0) {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(musicList.get(listTag).getPath());
                        mediaPlayer.prepareAsync();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }

            }
            if (task != null) {
                task.cancel();
            }
            task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (mediaPlayer == null)
                            return;
                        if (mediaPlayer.isPlaying() && MusicPlayActivity.seekBar.isPressed() == false) {
                            handler.sendEmptyMessage(0); // 发送消息
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            };
            if (mTimer == null) {
                mTimer = new Timer();
            }
            mTimer.schedule(task, 0, 1000);

            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_MUSIC;
            message.obj = true;
            EventBus.getDefault().post(message);
        }
    }

    //暂停播放
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            //获取播放位置
            pos = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            MusicPlayActivity.state = MusicPlayActivity.STATE_PAUSE;
            isPlay = false;

            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_MUSIC;
            message.obj = false;
            EventBus.getDefault().post(message);

            MusicPlayActivity.play.setImageResource(R.drawable.icon_music_play_big);
            MusicPlayActivity.animation.pause();
            if (duration > 0) {
                presenter.uploadAudioTime(musicOrigin, musicAudioType, musicAudioSource, musicResourceId, duration);
                duration = 0;
            }
        }
    }

    //停止播放
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void stop() {
        if (mediaPlayer != null) {
            isPlay = false;
            mediaPlayer.pause();
            mediaPlayer.stop();
            MusicPlayActivity.state = MusicPlayActivity.STATE_STOP;
            mediaPlayer.seekTo(0);

            pos = 0; //停止后播放位置置为0
            //
            Song song = new Song();
            /**/
            if (musicPartList != null && musicPartList.size() > 0) {
                song.setBookId(musicPartList.get(listTag).getBookId());
                song.setBookName(musicPartList.get(listTag).getName());
                song.setBookImageUrl(musicPartList.get(listTag).getImageUrl());
                song.setPath(musicPartList.get(listTag).getMusicUrl());
                song.setIsCollected(musicList.get(listTag).getIsCollected());
                addPlayLists(song);
            }

            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_MUSIC;
            message.obj = false;
            EventBus.getDefault().post(message);
            MusicPlayActivity.play.setImageResource(R.drawable.icon_music_play_big);
            MusicPlayActivity.animation.resume();
            MusicPlayActivity.animation.pause();
            if (duration > 0) {
                presenter.uploadAudioTime(musicOrigin, musicAudioType, musicAudioSource, musicResourceId, duration);
                duration = 0;
            }
            start = "0:00";
        }
    }

    private static void addPlayLists(Song song) {
        if (application.getSystemUtils().getPlayLists() == null) {
            application.getSystemUtils().setPlayLists(new ArrayList<>());
        }

        if (application.getSystemUtils().getPlayLists().size() < 20) {
            application.getSystemUtils().getPlayLists().add(song);
        } else {
            application.getSystemUtils().getPlayLists().remove(19);
            application.getSystemUtils().getPlayLists().add(song);
        }
    }

    @SuppressLint("HandlerLeak")
    static Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mediaPlayer == null) {
                return;
            }
            try {
                int position = mediaPlayer.getCurrentPosition();
                int durations = mediaPlayer.getDuration();
                if (durations > 0) {
                    // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                    long pos = MusicPlayActivity.seekBar.getMax() * position / durations;
                    MusicPlayActivity.seekBar.setProgress((int) pos);
                    int currentSecond = position / 1000;
                    int se = currentSecond % 60;
                    if (se < 10) {
                        start = currentSecond / 60 + ":0" + se;
                    } else {
                        start = currentSecond / 60 + ":" + se;
                    }
                    MusicPlayActivity.start.setText(start);
                    duration++;
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onUnbind(Intent intent) {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        musicTitle = null;
        musicImageUrl = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        musicTitle = null;
        musicImageUrl = null;
    }
}
