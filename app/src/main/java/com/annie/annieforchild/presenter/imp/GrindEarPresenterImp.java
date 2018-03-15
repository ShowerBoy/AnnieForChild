package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.os.Bundle;

import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.baselibrary.base.BasePresenterImp;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

/**
 * 磨耳朵
 * Created by WangLei on 2018/1/18 0018
 */

public class GrindEarPresenterImp extends BasePresenterImp implements GrindEarPresenter,BaseSliderView.OnSliderClickListener{
    private Context context;
    private GrindEarView grindEarView;
    private HashMap<String, String> file_maps;

    public GrindEarPresenterImp(Context context, GrindEarView grindEarView) {
        this.context = context;
        this.grindEarView = grindEarView;
    }

    @Override
    public void initViewAndData() {
        file_maps = grindEarView.getFile_maps();
        file_maps.put("0", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        file_maps.put("1", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        file_maps.put("2", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        file_maps.put("3", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        initImageSlide();
    }

    private void initImageSlide() {
        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description("")
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            grindEarView.getImageSlide().addSlider(textSliderView);
        }
        grindEarView.getImageSlide().setPresetTransformer(SliderLayout.Transformer.DepthPage);
        grindEarView.getImageSlide().setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        grindEarView.getImageSlide().setCustomAnimation(new DescriptionAnimation());
        grindEarView.getImageSlide().setDuration(4000);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        grindEarView.showInfo(slider.getBundle().getString("extra"));
    }

    @Override
    public void Success(int what, Object result) {

    }
}
