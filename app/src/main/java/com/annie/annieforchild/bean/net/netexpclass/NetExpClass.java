package com.annie.annieforchild.bean.net.netexpclass;
import java.util.List;

/**
 */
public class NetExpClass {

    private Video video;

    private List<Info> info;
    private LearningReport LearningReport;
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