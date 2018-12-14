package com.annie.annieforchild.bean.radio;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/12/13.
 */

public class RadioBean implements Serializable {
    private List<RadioTag> age;
    private List<RadioTag> function;
    private List<RadioTag> theme;
    private List<RadioTag> type;

    public List<RadioTag> getAge() {
        return age;
    }

    public void setAge(List<RadioTag> age) {
        this.age = age;
    }

    public List<RadioTag> getFunction() {
        return function;
    }

    public void setFunction(List<RadioTag> function) {
        this.function = function;
    }

    public List<RadioTag> getTheme() {
        return theme;
    }

    public void setTheme(List<RadioTag> theme) {
        this.theme = theme;
    }

    public List<RadioTag> getType() {
        return type;
    }

    public void setType(List<RadioTag> type) {
        this.type = type;
    }
}
