package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/5/7.
 */

public class PkResult implements Serializable {
    private int result;
    private String myscore;
    private String pkscore;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMyscore() {
        return myscore;
    }

    public void setMyscore(String myscore) {
        this.myscore = myscore;
    }

    public String getPkscore() {
        return pkscore;
    }

    public void setPkscore(String pkscore) {
        this.pkscore = pkscore;
    }
}
