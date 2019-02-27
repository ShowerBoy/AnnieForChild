package com.annie.annieforchild.bean.net.netexpclass;

import java.io.Serializable;

public class FirstStage implements Serializable {
    /**
     * 体验课详情第一阶段
     */
    private FirstStageitem questionnaire;
    private FirstStageitem commonproblem;
    private FirstStageitem classanalysis;
    private FirstStageitem weiclass;

    public FirstStageitem getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(FirstStageitem questionnaire) {
        this.questionnaire = questionnaire;
    }

    public FirstStageitem getCommonproblem() {
        return commonproblem;
    }

    public void setCommonproblem(FirstStageitem commonproblem) {
        this.commonproblem = commonproblem;
    }

    public FirstStageitem getClassanalysis() {
        return classanalysis;
    }

    public void setClassanalysis(FirstStageitem classanalysis) {
        this.classanalysis = classanalysis;
    }

    public FirstStageitem getWeiclass() {
        return weiclass;
    }

    public void setWeiclass(FirstStageitem weiclass) {
        this.weiclass = weiclass;
    }
}


