package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;
/*
* 网课介绍
*/
public class SuggestList  implements Serializable {

    private List<String> middle;
    private List<String> top;
    private List<String> bottom;
    public void setMiddle(List<String> middle) {
        this.middle = middle;
    }
    public List<String> getMiddle() {
        return middle;
    }

    public void setTop(List<String> top) {
        this.top = top;
    }
    public List<String> getTop() {
        return top;
    }

    public void setBottom(List<String> bottom) {
        this.bottom = bottom;
    }
    public List<String> getBottom() {
        return bottom;
    }

}