package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/3/22.
 */

public class SpecialPreHeat implements Serializable {
    private String pretitle;
    private List<PreheatConsultList> content;

    public String getPretitle() {
        return pretitle;
    }

    public void setPretitle(String pretitle) {
        this.pretitle = pretitle;
    }

    public List<PreheatConsultList> getContent() {
        return content;
    }

    public void setContent(List<PreheatConsultList> content) {
        this.content = content;
    }
}
