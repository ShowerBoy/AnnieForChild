package com.annie.annieforchild.bean.task;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/1/25.
 */

public class TaskFade implements Serializable {
    private boolean isLike;
    private boolean isListen;
    private int likes;
    private int listens;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getListens() {
        return listens;
    }

    public void setListens(int listens) {
        this.listens = listens;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isListen() {
        return isListen;
    }

    public void setListen(boolean listen) {
        isListen = listen;
    }
}
