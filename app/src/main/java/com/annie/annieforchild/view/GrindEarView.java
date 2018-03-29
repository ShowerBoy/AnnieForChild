package com.annie.annieforchild.view;

import com.annie.annieforchild.view.info.ViewInfo;
import com.daimajia.slider.library.SliderLayout;

import java.util.HashMap;

/**
 * Created by WangLei on 2018/1/18 0018
 */

public interface GrindEarView extends ViewInfo {
    SliderLayout getImageSlide();

    HashMap<Integer, String> getFile_maps();

    void setPictureUrl(String listening, String animation);
}
