package com.annie.annieforchild.Utils;

import com.annie.annieforchild.bean.song.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2019/3/12.
 */

public class MusicManager {
    private static volatile MusicManager instance;
    public List<Song> musicList; //播放列表

    public MusicManager() {

    }

    public static MusicManager getInstance() {
        if (instance == null) {
            synchronized (MusicManager.class) {
                if (instance == null) {
                    instance = new MusicManager();
                }
            }
        }
        return instance;
    }

    public List<Song> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Song> musicList) {
        this.musicList = musicList;
    }

    /**
     * 初始化列表
     */
    public void initMusicList() {
        musicList = new ArrayList<>();
    }

    /**
     * 更新播放列表
     *
     * @param list
     */
    public void updateMusicList(List<Song> list) {
        musicList.clear();
        musicList.addAll(list);
    }

    /**
     * 清空播放列表
     */
    public void clearMusicList() {
        if (musicList != null) {
            musicList.clear();
        }
    }


}
