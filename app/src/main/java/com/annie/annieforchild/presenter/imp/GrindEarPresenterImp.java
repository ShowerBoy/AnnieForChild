package com.annie.annieforchild.presenter.imp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.mtp.MtpConstants;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.AnimationData;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.PkResult;
import com.annie.annieforchild.bean.ReadingData;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.grindear.GrindEarData;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.bean.login.SigninBean;
import com.annie.annieforchild.bean.material.MaterialGroup;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.interactor.GrindEarInteractor;
import com.annie.annieforchild.interactor.imp.GrindEarInteractorImp;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.adapter.AnimationAdapter;
import com.annie.annieforchild.ui.adapter.PopupAdapter;
import com.annie.annieforchild.ui.adapter.viewHolder.CommitBookViewHolder;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.annieforchild.view.ReadingView;
import com.annie.annieforchild.view.SongView;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
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
    private ViewInfo viewInfo;
    private GrindEarInteractor interactor;
    private List<Banner> bannerList;
    private int classId;
    private int lineId;
    private int pkType;
    private HashMap<Integer, String> file_maps;

    public GrindEarPresenterImp(Context context) {
        this.context = context;
    }

    public GrindEarPresenterImp(Context context, GrindEarView grindEarView) {
        this.context = context;
        this.grindEarView = grindEarView;
    }

    public GrindEarPresenterImp(Context context, SongView songView) {
        this.context = context;
        this.songView = songView;
    }

    public GrindEarPresenterImp(Context context, ViewInfo viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
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
    public void collectCourse(int type, int audioSource, int courseId, int classId) {
        songView.showLoad();
        interactor.collectCourse(type, audioSource, courseId, classId);
    }

    /**
     * 取消收藏
     *
     * @param type
     * @param courseId
     */
    @Override
    public void cancelCollection(int type, int audioSource, int courseId, int classId) {
        songView.showLoad();
        interactor.cancelCollection(type, audioSource, courseId, classId);
    }

    /**
     * 获取儿歌以及分类
     */
    @Override
    public void getMusicClasses() {
        grindEarView.showLoad();
        interactor.getMusicClasses();
    }

    /**
     * 获取阅读分类
     */
    @Override
    public void getReadingClasses() {
        grindEarView.showLoad();
        interactor.getReadingClasses();
    }

    /**
     * 获取某分类的儿歌列表
     *
     * @param classId
     */
    @Override
    public void getMusicList(int classId) {
        if (classId == -1) {
            getSpokenClasses();
        } else {
            songView.showLoad();
            this.classId = classId;
            interactor.getMusicList(classId);
        }
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
     * 上传音频
     *
     * @param resourseId
     * @param page
     * @param lineId
     * @param path
     * @param score
     */
    @Override
    public void uploadAudioResource(int resourseId, int page, int audioType, int audioSource, int lineId, String path, float score, String title, int duration, int origin, String pkUsername) {
        this.lineId = lineId;
        interactor.uploadAudioResource(resourseId, page, audioType, audioSource, lineId, path, score, title, duration, origin, pkUsername);
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

    /**
     * 获取PK结果
     *
     * @param bookId
     * @param pkUsername
     * @param pkType
     */
    @Override
    public void getPkResult(int bookId, String pkUsername, int pkType) {
        songView.showLoad();
        interactor.getPkResult(bookId, pkUsername, pkType);
    }

    /**
     * 加入教材
     *
     * @param bookId
     */
    @Override
    public void joinMaterial(int bookId, int classId) {
        songView.showLoad();
        interactor.joinMaterial(bookId, classId);
    }

    /**
     * 取消加入教材
     *
     * @param bookId
     * @param classId
     */
    @Override
    public void cancelMaterial(int bookId, int classId) {
        songView.showLoad();
        interactor.cancelMaterial(bookId, classId);
    }

    /**
     * 榜单
     *
     * @param spaceType
     * @param timeType
     */
    @Override
    public void getRank(int spaceType, int timeType) {
        songView.showLoad();
        interactor.getRank(spaceType, timeType);
    }

    /**
     * 广场排行榜首页
     */
    @Override
    public void getSquareRank() {
        songView.showLoad();
        interactor.getSquareRank();
    }

    /**
     * 获取排行榜列表
     *
     * @param resourceType
     * @param timeType
     * @param locationType
     */
    @Override
    public void getSquareRankList(int resourceType, int timeType, int locationType) {
        songView.showLoad();
        interactor.getSquareRankList(resourceType, timeType, locationType);
    }

    /**
     * 点赞
     *
     * @param likeUsername
     */
    @Override
    public void likeStudent(String likeUsername) {
        songView.showLoad();
        interactor.likeStudent(likeUsername);
    }

    /**
     * 取消点赞
     *
     * @param cancelLikeUsername
     */
    @Override
    public void cancelLikeStudent(String cancelLikeUsername) {
        songView.showLoad();
        interactor.cancelLikeStudent(cancelLikeUsername);
    }

    /**
     * 获取阅读接口
     */
    @Override
    public void getReading() {
        interactor.getReading();
    }

    /**
     * 阅读存折
     */
    @Override
    public void getMyReading() {
        songView.showLoad();
        interactor.getMyReading();
    }

    /**
     * 阅读时长录入
     *
     * @param type
     * @param duration
     */
    @Override
    public void commitReading(String[] type, String[] duration, int books, int words) {
        songView.showLoad();
        interactor.commitReading(type, duration, books, words);
    }

    /**
     * 统计
     *
     * @param timeType
     * @param locationType
     */
    @Override
    public void getDurationStatistics(int timeType, int locationType) {
        songView.showLoad();
        interactor.getDurationStatistics(timeType, locationType);
    }

    /**
     * 获取阅读列表
     *
     * @param classId
     */
    @Override
    public void getReadList(int classId) {
        songView.showLoad();
        this.classId = classId;
        interactor.getReadList(classId);
    }

    /**
     * 获取二维码
     */
    @Override
    public void getQrCode() {
        songView.showLoad();
        interactor.getQrCode();
    }

    /**
     * 获取看动画列表
     *
     * @param title
     */
    @Override
    public void getAnimationList(String title, int classId) {
        songView.showLoad();
        this.classId = classId;
        interactor.getAnimationList(title, classId);
    }

    @Override
    public void getSpokenClasses() {
        interactor.getSpokenClasses();
    }

    /**
     * 获取口语列表
     *
     * @param classId
     */
    @Override
    public void getSpokenList(int classId) {
        songView.showLoad();
        this.classId = classId;
        interactor.getSpokenList(classId);
    }

    /**
     * 添加书名
     *
     * @param lists
     */
    @Override
    public void commitBook(List<String> lists) {
        interactor.commitBook(lists);
    }

    /**
     * 在线获取花蜜
     */
    @Override
    public void DailyPunch() {
        interactor.DailyPunch();
    }

    private void initImageSlide() {
        grindEarView.getImageSlide().removeAllSliders();
        for (int name : file_maps.keySet()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(context);
//            TextSliderView textSliderView = new TextSliderView(context);
//            textSliderView
//                    .description("")
//                    .image(file_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(this);
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle().putInt("extra", name);
//            grindEarView.getImageSlide().addSlider(textSliderView);
            defaultSliderView.image(file_maps.get(name));
            grindEarView.getImageSlide().addSlider(defaultSliderView);
        }
        grindEarView.getImageSlide().setPresetTransformer(SliderLayout.Transformer.DepthPage);
        grindEarView.getImageSlide().setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        grindEarView.getImageSlide().setCustomAnimation(new DescriptionAnimation());
        grindEarView.getImageSlide().setDuration(4000);
        if (file_maps.size() == 1) {
            grindEarView.getImageSlide().stopAutoCycle();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        /**
         * Banner页点击
         */
//        grindEarView.showInfo(slider.getBundle().getString("extra"));
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
                    file_maps.clear();
                    for (int i = 0; i < bannerList.size(); i++) {
                        file_maps.put(i, bannerList.get(i).getImageUrl());
                    }
                    initImageSlide();
                }
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = grindEarData;
                EventBus.getDefault().post(message);
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
            } else if (what == MethodCode.EVENT_GETREADLIST + 6000 + classId) {
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
                 * {@link com.annie.annieforchild.ui.fragment.mygrindear.TodayGrindEarFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.fragment.mygrindear.TotalGrindEarFragment#onMainEventThread(JTMessage)}
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
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.PracticeActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETPKRESULT) {
                PkResult pkResult = (PkResult) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.ChallengeActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = pkResult;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_JOINMATERIAL + 4000 + classId) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.song.ListenSongFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELMATERIAL + 5000 + classId) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.song.ListenSongFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSQUARERANK) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.square.RankingListFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSQUARERANKLIST) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.mains.RankingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_LIKESTUDENT) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.mains.RankingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELLIKESTUDENT) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.mains.RankingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMYREADING) {
                MyGrindEarBean bean = (MyGrindEarBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.reading.MyReadingActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.fragment.myreading.TodayReadingFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.fragment.myreading.TotalReadingFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage jtMessage = new JTMessage();
                jtMessage.what = what;
                jtMessage.obj = bean;
                EventBus.getDefault().post(jtMessage);
            } else if (what == MethodCode.EVENT_COMMITREADING) {
                songView.showInfo((String) result);
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.InputActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.grindEar.InputBookActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETREADING) {
                ReadingData readingData = (ReadingData) result;
                List<Banner> lists = readingData.getBannerList();
                if (lists.size() != 0) {
                    file_maps.clear();
                    for (int i = 0; i < lists.size(); i++) {
                        file_maps.put(i, lists.get(i).getImageUrl());
                    }
                    initImageSlide();
                }
                /**
                 * {@link com.annie.annieforchild.ui.activity.reading.ReadingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = readingData;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETDURATIONSTATISTICS) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.square.CensusFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETQRCODE) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.QrCodeActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETANIMATIONLIST + 7000 + classId) {
                List<AnimationData> lists = (List<AnimationData>) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.song.AnimationFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.FirstFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSPOKENLIST + 8000 + classId) {
                List<Song> songList = (List<Song>) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.song.ListenSongFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = songList;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_COMMITBOOK) {
                /**
                 *  {@link }
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_DAILYPUNCH) {
                int res = (int) result;
                if (res == 1) {
//                    SystemUtils.show(context, res + "");
                    Dialog dialog = SystemUtils.CoundownDialog((Activity) context);
                    dialog.show();
                    SystemUtils.setBackGray((Activity) context, true);
                    SystemUtils.signinBean.setNectar(true);
                    SystemUtils.signinBean.save();
                } else {
                    SystemUtils.signinBean.setNectar(true);
                    SystemUtils.signinBean.save();
                }
                if (SystemUtils.timer != null) {
                    SystemUtils.timer.cancel();
                    SystemUtils.timer = null;
                }
                if (SystemUtils.task != null) {
                    SystemUtils.task.cancel();
                    SystemUtils.task = null;
                }
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        if (grindEarView != null) {
            grindEarView.dismissLoad();
//            grindEarView.showInfo(error);
        }
        if (songView != null) {
            songView.dismissLoad();
//            songView.showInfo(error);
        }
        if (viewInfo != null) {
            viewInfo.dismissLoad();
//            viewInfo.showInfo(error);
        }
    }
}
