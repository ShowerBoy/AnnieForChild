package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.book.Book;
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
import java.util.IllegalFormatCodePointException;
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
    private int lineId;
    private int pkType;
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
     * 收藏
     *
     * @param type     1:磨耳朵 2:阅读 3:口语
     * @param courseId
     */
    @Override
    public void collectCourse(int type, int courseId, int classId) {
        songView.showLoad();
        interactor.collectCourse(type, courseId, classId);
    }

    /**
     * 取消收藏
     *
     * @param type
     * @param courseId
     */
    @Override
    public void cancelCollection(int type, int courseId, int classId) {
        songView.showLoad();
        this.classId = classId;
        interactor.cancelCollection(type, courseId, classId);
    }

    /**
     * 获取儿歌以及分类
     */
    @Override
    public void getMusicClasses() {
        interactor.getMusicClasses();
    }

    /**
     * 获取阅读分类
     */
    @Override
    public void getReadingClasses() {
        interactor.getReadingClasses();
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

    /**
     * 练习，挑战，PK
     *
     * @param bookId
     * @param pkType
     * @param pkUsername
     */
    @Override
    public void getBookAudioData(int bookId, int pkType, String pkUsername) {
        songView.showLoad();
        this.pkType = pkType;
        interactor.getBookAudioData(bookId, pkType, pkUsername);
    }

    /**
     * 上传音频——磨耳朵
     *
     * @param resourseId
     * @param page
     * @param lineId
     * @param path
     * @param score
     */
    @Override
    public void uploadAudioResource(int resourseId, int page, int lineId, String path, float score, String title, int duration) {
        this.lineId = lineId;
        interactor.uploadAudioResource(resourseId, page, lineId, path, score, title, duration);
    }

    /**
     * 获取pk用户
     *
     * @param bookId
     */
    @Override
    public void getPkUsers(int bookId) {
        interactor.getPkUsers(bookId);
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
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES5) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES6) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES7) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES8) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES9) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES10) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICLIST + 1000 + classId) {
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
            } else if (what == MethodCode.EVENT_GETBOOKAUDIODATA) {
                Book book = (Book) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.ExerciseActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.pk.ChallengeActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.pk.pkActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                if (pkType == 0) {
                    message.what = MethodCode.EVENT_PK_EXERCISE;
                } else if (pkType == 1) {
                    message.what = MethodCode.EVENT_PK_CHALLENGE;
                } else if (pkType == 2) {
                    message.what = MethodCode.EVENT_PK_PK;
                }
                message.obj = book;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_UPLOADAUDIO) {
                AudioBean audioBean = (AudioBean) result;
                audioBean.setLineId(lineId);
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.ExerciseActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = audioBean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_COLLECTCOURSE + 2000 + classId) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.song.ListenSongFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.song.ListenSongFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETPKUSERS) {
                List<UserInfo2> lists = (List<UserInfo2>) result;
                //TODO:假数据
//                lists.clear();
//                UserInfo2 userInfo2 = new UserInfo2();
//                userInfo2.setAvatar("http://pic.58pic.com/58pic/14/62/50/62558PICxm8_1024.jpg");
//                userInfo2.setName("小明");
//                UserInfo2 userInfo3 = new UserInfo2();
//                userInfo3.setAvatar("http://pic.58pic.com/58pic/14/62/50/62558PICxm8_1024.jpg");
//                userInfo3.setName("小明");
//                UserInfo2 userInfo4 = new UserInfo2();
//                userInfo4.setAvatar("http://pic.58pic.com/58pic/14/62/50/62558PICxm8_1024.jpg");
//                userInfo4.setName("小明");
//                UserInfo2 userInfo5 = new UserInfo2();
//                userInfo5.setAvatar("http://pic.58pic.com/58pic/14/62/50/62558PICxm8_1024.jpg");
//                userInfo5.setName("小明");
//                lists.add(userInfo2);
//                lists.add(userInfo3);
//                lists.add(userInfo4);
//                lists.add(userInfo5);
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.PracticeActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
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
