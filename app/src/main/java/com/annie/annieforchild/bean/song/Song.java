package com.annie.annieforchild.bean.song;

import java.io.Serializable;
import java.util.List;

/**
 * 儿歌
 * Created by wanglei on 2018/3/24.
 * “lastScore”: 20 //上次成绩
 * “totalScore”: 500 //本书总分
 * “totalPages”:10 //总页数
 */

public class Song implements Serializable {
    private String bookName;
    private String bookImageUrl;
    private int count;
    private int bookId;
    private float lastScore;
    private float totalScore;
    private int totalPages;
    private int isCollected; //0：未收藏 1：已收藏
    private int isJoinMaterial; //0：未加入自选教材 1：已加入自选教材
    private int Jurisdiction;
    private List<String> bookResourceUrl;
    private String myResourceUrl;
    private int isusenectar;
    private int nectar;
    private int iskouyu;
    private int ismoerduo;
    private int isyuedu;

    public int getIskouyu() {
        return iskouyu;
    }

    public void setIskouyu(int iskouyu) {
        this.iskouyu = iskouyu;
    }

    public int getIsmoerduo() {
        return ismoerduo;
    }

    public void setIsmoerduo(int ismoerduo) {
        this.ismoerduo = ismoerduo;
    }

    public int getIsyuedu() {
        return isyuedu;
    }

    public void setIsyuedu(int isyuedu) {
        this.isyuedu = isyuedu;
    }

    public int getJurisdiction() {
        return Jurisdiction;
    }

    public void setJurisdiction(int jurisdiction) {
        Jurisdiction = jurisdiction;
    }

    public int getIsusenectar() {
        return isusenectar;
    }

    public void setIsusenectar(int isusenectar) {
        this.isusenectar = isusenectar;
    }

    public int getNectar() {
        return nectar;
    }

    public void setNectar(int nectar) {
        this.nectar = nectar;
    }

    public List<String> getBookResourceUrl() {
        return bookResourceUrl;
    }

    public void setBookResourceUrl(List<String> bookResourceUrl) {
        this.bookResourceUrl = bookResourceUrl;
    }

    public String getMyResourceUrl() {
        return myResourceUrl;
    }

    public void setMyResourceUrl(String myResourceUrl) {
        this.myResourceUrl = myResourceUrl;
    }

    public float getLastScore() {
        return lastScore;
    }

    public void setLastScore(float lastScore) {
        this.lastScore = lastScore;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    public int getIsJoinMaterial() {
        return isJoinMaterial;
    }

    public void setIsJoinMaterial(int isJoinMaterial) {
        this.isJoinMaterial = isJoinMaterial;
    }
}
