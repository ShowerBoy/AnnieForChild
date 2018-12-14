package com.annie.annieforchild.bean.net;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/10/22.
 */

public class Game implements Serializable {
    private String gameName;
    private String gameUrl;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }
}
