package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.grindear.GrindEarData;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.bean.material.MaterialGroup;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.interactor.GrindEarInteractor;
import com.annie.annieforchild.interactor.imp.GrindEarInteractorImp;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.adapter.PopupAdapter;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.annieforchild.view.ReadingView;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BasePresenterImp;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 磨耳朵
 * Created by WangLei on 2018/1/18 0018
 */

public class GrindEarPresenterImp extends BasePresenterImp implements GrindEarPresenter, BaseSliderView.OnSliderClickListener {
    private Context context;
    private GrindEarView grindEarView;
    private SongView songView;
    private GrindEarInteractor interactor;
    private List<Banner> bannerList;
    private int classId;
    private HashMap<Integer, String> file_maps;

    public GrindEarPresenterImp(Context context, GrindEarView grindEarView) {
        this.context = context;
        this.grindEarView = grindEarView;
    }

    public GrindEarPresenterImp(Context context, SongView songView) {
        this.context = context;
        this.songView = songView;
    }

    @Override
    public void initViewAndData() {
        interactor = new GrindEarInteractorImp(context, this);
        if (grindEarView != null) {
            file_maps = grindEarView.getFile_maps();
        }
    }

    /**
     * 获取磨耳朵页面
     */
    @Override
    public void getListening() {
        grindEarView.showLoad();
        interactor.getListening();
    }

    /**
     * 获取我的磨耳朵
     */
    @Override
    public void getMyListening() {
        songView.showLoad();
        interactor.getMyListening();
    }

    /**
     * 获取儿歌以及分类
     */
    @Override
    public void getMusicClasses() {
        interactor.getMusicClasses();
    }

    /**
     * 获取某分类的儿歌列表
     *
     * @param calssId
     */
    @Override
    public void getMusicList(int calssId) {
        songView.showLoad();
        classId = calssId;
        interactor.getMusicList(calssId);
    }

    /**
     * 时长录入
     *
     * @param type
     * @param duration
     */
    @Override
    public void commitDuration(String[] type, String[] duration) {
        songView.showLoad();
        interactor.commitDuration(type, duration);
    }

    /**
     * 获取书籍信息
     *
     * @param bookId
     */
    @Override
    public void getBookScore(int bookId) {
        interactor.getBookScore(bookId);
    }

    private void initImageSlide() {
        for (int name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description("")
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putInt("extra", name);
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
        if (grindEarView != null) {
            grindEarView.dismissLoad();
        }
        if (songView != null) {
            songView.dismissLoad();
        }
        if (result != null) {
            if (what == MethodCode.EVENT_GETLISTENING) {
                GrindEarData grindEarData = (GrindEarData) result;
                if (grindEarData.getBannerList() != null) {
                    bannerList = grindEarData.getBannerList();
                    for (int i = 0; i < bannerList.size(); i++) {
                        file_maps.put(i, bannerList.get(i).getImageUrl());
                    }
                    initImageSlide();
                }
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES1) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES2) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES3) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES4) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICLIST + 10000 + classId) {
                List<Song> songList = (List<Song>) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.song.ListenSongFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = songList;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMYLISTENING) {
                MyGrindEarBean bean = (MyGrindEarBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.MyGrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage jtMessage = new JTMessage();
                jtMessage.what = what;
                jtMessage.obj = bean;
                EventBus.getDefault().post(jtMessage);
            } else if (what == MethodCode.EVENT_COMMITDURATION) {
                songView.showInfo((String) result);
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.InputActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETBOOKSCORE) {
                Song song = (Song) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.PracticeActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = song;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        if (grindEarView != null) {
            grindEarView.dismissLoad();
            grindEarView.showInfo(error);
        }
        if (songView != null) {
            songView.dismissLoad();
            songView.showInfo(error);
        }
    }
}
