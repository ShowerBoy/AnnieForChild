package com.annie.annieforchild.bean.tongzhi;

/**
 * 消息
 * Created by WangLei on 2018/2/27 0027
 */

public class Notice {
    private int type; //消息类型  暂时只有1 ： 礼包消息	类型：int
    private String typeName;
    private String title;
    private String createTime;
    private String contents;
    private int giftRecordId;
    private int isChoose; //当 type = 1 时，判断该字段   0：还未选择礼包，点击显示弹窗   1：已经选择完礼包	   类型：int

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getGiftRecordId() {
        return giftRecordId;
    }

    public void setGiftRecordId(int giftRecordId) {
        this.giftRecordId = giftRecordId;
    }

    public int getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(int isChoose) {
        this.isChoose = isChoose;
    }
}
