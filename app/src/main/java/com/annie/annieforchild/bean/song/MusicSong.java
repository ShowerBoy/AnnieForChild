package com.annie.annieforchild.bean.song;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/11/26.
 */

public class MusicSong extends LitePalSupport implements Serializable {
    private String username;
    private List<Song> list;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Song> getList() {
        return list;
    }

    public void setList(List<Song> list) {
        this.list = list;
    }
}
