package com.annie.annieforchild.view;

import com.annie.annieforchild.view.info.ViewInfo;
import com.daimajia.slider.library.SliderLayout;

import java.util.HashMap;

/**
 * Created by WangLei on 2018/1/19 0019
 */

public interface MainView extends ViewInfo {
    SliderLayout getImageSlide();
    HashMap<String, Integer> getFile_maps();
}
