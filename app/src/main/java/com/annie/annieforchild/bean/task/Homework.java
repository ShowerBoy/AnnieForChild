package com.annie.annieforchild.bean.task;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/8/23.
 */

public class Homework implements Serializable {
    private int bookid;
    private int homeworkid;
    private String animationUrl;
    private String taskrequirement;
    private String bookimage;
    private String bookname;
    private String bookClassify;
    private int isfinish;
    private int isjoinmaterial;
    private int likes;
    private int listen;
    private String chaptertitle;
    private String type;

    public String getAnimationUrl() {
        return animationUrl;
    }

    public void setAnimationUrl(String animationUrl) {
        this.animationUrl = animationUrl;
    }

    public String getBookClassify() {
        return bookClassify;
    }

    public void setBookClassify(String bookClassify) {
        this.bookClassify = bookClassify;
    }

    public String getChaptertitle() {
        return chaptertitle;
    }

    public void setChaptertitle(String chaptertitle) {
        this.chaptertitle = chaptertitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getHomeworkid() {
        return homeworkid;
    }

    public void setHomeworkid(int homeworkid) {
        this.homeworkid = homeworkid;
    }

    public String getTaskrequirement() {
        return taskrequirement;
    }

    public void setTaskrequirement(String taskrequirement) {
        this.taskrequirement = taskrequirement;
    }

    public String getBookimage() {
        return bookimage;
    }

    public void setBookimage(String bookimage) {
        this.bookimage = bookimage;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public int getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(int isfinish) {
        this.isfinish = isfinish;
    }

    public int getIsjoinmaterial() {
        return isjoinmaterial;
    }

    public void setIsjoinmaterial(int isjoinmaterial) {
        this.isjoinmaterial = isjoinmaterial;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getListen() {
        return listen;
    }

    public void setListen(int listen) {
        this.listen = listen;
    }
}
