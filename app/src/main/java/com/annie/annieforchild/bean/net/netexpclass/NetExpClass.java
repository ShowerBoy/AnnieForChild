package com.annie.annieforchild.bean.net.netexpclass;
import java.util.List;

/**
 */
public class NetExpClass {

    private Video video;

    private List<Info> info;
    private LearningReport LearningReport;
    private Integer isShowtest;
    private String practice;
    private String testing;
    private String placeholdImg;

    public String getPlaceholdImg() {
        return placeholdImg;
    }

    public void setPlaceholdImg(String placeholdImg) {
        this.placeholdImg = placeholdImg;
    }

    public Integer getIsShowtest() {
        return isShowtest;
    }

    public void setIsShowtest(Integer isShowtest) {
        this.isShowtest = isShowtest;
    }

    public String getTesting() {
        return testing;
    }

    public void setTesting(String testing) {
        this.testing = testing;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public void setVideo(Video video) {
         this.video = video;
     }
     public Video getVideo() {
         return video;
     }



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