package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/9/7.
 */

public class ClockIn implements Serializable {
    private int openaccount;
    private int totaldays;
    private int clockindays;
    private int moerduotoday;
    private int moerduototal;
    private int readingtoday;
    private int readingtotal;
    private int speakingtoday;
    private int speakingtotal;
    private int ismoerduoclockin;
    private int isreadingclockin;
    private int isspeakingclockin;

    public int getOpenaccount() {
        return openaccount;
    }

    public void setOpenaccount(int openaccount) {
        this.openaccount = openaccount;
    }

    public int getTotaldays() {
        return totaldays;
    }

    public void setTotaldays(int totaldays) {
        this.totaldays = totaldays;
    }

    public int getClockindays() {
        return clockindays;
    }

    public void setClockindays(int clockindays) {
        this.clockindays = clockindays;
    }

    public int getMoerduotoday() {
        return moerduotoday;
    }

    public void setMoerduotoday(int moerduotoday) {
        this.moerduotoday = moerduotoday;
    }

    public int getMoerduototal() {
        return moerduototal;
    }

    public void setMoerduototal(int moerduototal) {
        this.moerduototal = moerduototal;
    }

    public int getReadingtoday() {
        return readingtoday;
    }

    public void setReadingtoday(int readingtoday) {
        this.readingtoday = readingtoday;
    }

    public int getReadingtotal() {
        return readingtotal;
    }

    public void setReadingtotal(int readingtotal) {
        this.readingtotal = readingtotal;
    }

    public int getSpeakingtoday() {
        return speakingtoday;
    }

    public void setSpeakingtoday(int speakingtoday) {
        this.speakingtoday = speakingtoday;
    }

    public int getSpeakingtotal() {
        return speakingtotal;
    }

    public void setSpeakingtotal(int speakingtotal) {
        this.speakingtotal = speakingtotal;
    }

    public int getIsmoerduoclockin() {
        return ismoerduoclockin;
    }

    public void setIsmoerduoclockin(int ismoerduoclockin) {
        this.ismoerduoclockin = ismoerduoclockin;
    }

    public int getIsreadingclockin() {
        return isreadingclockin;
    }

    public void setIsreadingclockin(int isreadingclockin) {
        this.isreadingclockin = isreadingclockin;
    }

    public int getIsspeakingclockin() {
        return isspeakingclockin;
    }

    public void setIsspeakingclockin(int isspeakingclockin) {
        this.isspeakingclockin = isspeakingclockin;
    }
}
