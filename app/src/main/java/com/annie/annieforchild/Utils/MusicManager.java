package com.annie.annieforchild.Utils;

import android.media.MediaPlayer;

import com.annie.annieforchild.bean.song.MusicPart;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.SongView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wanglei on 2019/3/7.
 */

public class MusicManager {
    private MediaPlayer mediaPlayer; //声明操作媒体的对象
    private int pos = 0; //记录播放的位置
    private boolean isPlay; //false:未播放 true:播放中
    private List<Song> musicList; //播放列表
    private List<MusicPart> musicPartList; //
    private String musicTitle = null; //播放曲名
    private String musicImageUrl = null; //播放图片
    private int musicNum; //单个列表播放总数量
    private int listTag; //播放列表的位置
    private String end; //当前音频总时长
    private String start; //当前音频播放时长
    private boolean singleLoop = false; //是否单曲循环
    private Timer mTimer = new Timer();
    private TimerTask task;
    private int musicOrigin, musicAudioType, musicAudioSource, musicResourceId, musicCollectType;
    private int musicDuration; //总时长(秒)
    private int duration = 0; //播放时长(时长自增)
    private int musicIsCollect; //是否收藏
    private boolean listVisibility = false; //列表显示或隐藏
    private String type = null;  //radio,collection,recently
    private GrindEarPresenter presenter;
    private SongView musicSongView;

    private static volatile MusicManager musicManager;

    public MusicManager() {
    }

    public static MusicManager instance() {
        if (musicManager == null) {
            synchronized (MusicManager.class) {
                if (musicManager == null) {
                    musicManager = new MusicManager();
                }
            }
        }
        return musicManager;
    }

    public void play() {

    }
}
