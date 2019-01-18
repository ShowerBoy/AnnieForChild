package com.annie.annieforchild.bean.net;

import java.io.Serializable;

public class NetExpDetailLessonList  implements Serializable {

    private String lessonId;
    private String lessonName;
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
    public String getLessonId() {
        return lessonId;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
    public String getLessonName() {
        return lessonName;
    }

}
