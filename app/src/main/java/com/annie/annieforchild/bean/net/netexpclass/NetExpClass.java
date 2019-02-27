package com.annie.annieforchild.bean.net.netexpclass;

import java.util.List;

/**
 */
public class NetExpClass {

//    private Video video;

    private List<Info> info;
    private LearningReport LearningReport;
//    private Integer isShowtest;
//    private String practice;
//    private String testing;
    private String placeholdImg;
    private String fid;
    private FirstStage firststage;
    private Fourthstage fourthstage;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public FirstStage getFirststage() {
        return firststage;
    }

    public void setFirststage(FirstStage firststage) {
        this.firststage = firststage;
    }

    public Fourthstage getFourthstage() {
        return fourthstage;
    }

    public void setFourthstage(Fourthstage fourthstage) {
        this.fourthstage = fourthstage;
    }



    public String getPlaceholdImg() {
        return placeholdImg;
    }

    public void setPlaceholdImg(String placeholdImg) {
        this.placeholdImg = placeholdImg;
    }


//    public void setVideo(Video video) {
//         this.video = video;
//     }
//     public Video getVideo() {
//         return video;
//     }



    public void setInfo(List<Info> info) {
         this.info = info;
     }
     public List<Info> getInfo() {
         return info;
     }

    public void setLearningReport(LearningReport LearningReport) {
         this.LearningReport = LearningReport;
     }
     public LearningReport getLearningReport() {
         return LearningReport;
     }

}