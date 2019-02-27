package com.annie.annieforchild.bean.net.netexpclass;

import java.io.Serializable;

public class Fourthstage implements Serializable {
    /**
     * 体验课详情第四阶段
     */
        private int isshow;
        private String learningreport;
        private String testing;
        private String questionnaire;
        public void setIsshow(int isshow) {
            this.isshow = isshow;
        }
        public int getIsshow() {
            return isshow;
        }

        public void setLearningreport(String learningreport) {
            this.learningreport = learningreport;
        }
        public String getLearningreport() {
            return learningreport;
        }

        public void setTesting(String testing) {
            this.testing = testing;
        }
        public String getTesting() {
            return testing;
        }

        public void setQuestionnaire(String questionnaire) {
            this.questionnaire = questionnaire;
        }
        public String getQuestionnaire() {
            return questionnaire;
        }
}
