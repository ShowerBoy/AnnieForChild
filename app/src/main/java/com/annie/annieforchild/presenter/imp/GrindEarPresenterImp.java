package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.AnimationData;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.ClockIn;
import com.annie.annieforchild.bean.PkResult;
import com.annie.annieforchild.bean.ReadingData;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.bean.ShareCoin;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.Release;
import com.annie.annieforchild.bean.grindear.GrindEarData;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.bean.period.PeriodBean;
import com.annie.annieforchild.bean.rank.Hpbean;
import com.annie.annieforchild.bean.rank.ProductionBean;
import com.annie.annieforchild.bean.record.RecordBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.bean.task.Task;
import com.annie.annieforchild.bean.task.TaskBean;
import com.annie.annieforchild.bean.task.TaskContent;
import com.annie.annieforchild.bean.task.TaskDetails;
import com.annie.annieforchild.interactor.GrindEarInteractor;
import com.annie.annieforchild.interactor.imp.GrindEarInteractorImp;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.ui.fragment.book.BookPlayFragment2;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.annieforchild.view.SongView;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;

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
    private int page;
    private int type;
    private int taskid, classify;
    private boolean submitTask = true; //提交作业
    private HashMap<Integer, String> file_maps;
    private MyApplication application;

    public GrindEarPresenterImp(Context context) {
        this.context = context;
        application = (MyApplication) context.getApplicationContext();
    }

    public GrindEarPresenterImp(Context context, GrindEarView grindEarView) {
        this.context = context;
        this.grindEarView = grindEarView;
        application = (MyApplication) context.getApplicationContext();
    }

    public GrindEarPresenterImp(Context context, SongView songView) {
        this.context = context;
        this.songView = songView;
        application = (MyApplication) context.getApplicationContext();
    }

    public GrindEarPresenterImp(Context context, ViewInfo viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
        application = (MyApplication) context.getApplicationContext();
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

    @Override
    public void getSpokenClasses() {
//        grindEarView.showLoad();
        interactor.getSpokenClasses();
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
    public void commitDuration(String type, String duration) {
        songView.showLoad();
        interactor.commitDuration(type, duration);
    }

    /**
     * 获取书籍信息
     *
     * @param bookId
     */
    @Override
    public void getBookScore(int bookId, int record, boolean accessBook) {
        interactor.getBookScore(bookId, record, accessBook);
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
    public void uploadAudioResource(int resourseId, int page, int audioType, int audioSource, int lineId, String path, float score, String title, int duration, int origin, String pkUsername, String imageUrl, int animationCode, int homeworkid, int homeworktype) {
        this.lineId = lineId;
        this.page = page;
//        songView.showLoad();
        interactor.uploadAudioResource(resourseId, page, audioType, audioSource, lineId, path, score, title, duration, origin, pkUsername, imageUrl, animationCode, homeworkid, homeworktype);
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
    public void joinMaterial(int bookId, int audioSource, int type, int classId) {
        songView.showLoad();
        interactor.joinMaterial(bookId, audioSource, type, classId);
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
    public void commitReading(String type, String duration, int books, int words) {
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
//        songView.showLoad();
        this.classId = classId;
        interactor.getAnimationList(title, classId);
    }


    /**
     * 获取口语列表
     *
     * @param classId
     */
    @Override
    public void getSpokenList(int classId) {
//        songView.showLoad();
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

    @Override
    public void iWantListen(int type) {
        songView.showLoad();
        interactor.iWantListen(type);
    }

    /**
     * 访问书籍
     *
     * @param bookId
     */
    @Override
    public void accessBook(int bookId) {
        interactor.accessBook(bookId);
    }

    /**
     * 上传音频
     *
     * @param origin
     * @param audioType
     * @param audioSource
     * @param resourseId
     * @param duration
     */
    @Override
    public void uploadAudioTime(int origin, int audioType, int audioSource, int resourseId, int duration) {
        interactor.uploadAudioTime(origin, audioType, audioSource, resourseId, duration);
    }

    /**
     * 课时核对
     */
    @Override
    public void myPeriod() {
        songView.showLoad();
        interactor.myPeriod();
    }

    /**
     * 课时提异
     *
     * @param periodid
     */
    @Override
    public void suggestPeriod(int periodid) {
        interactor.suggestPeriod(periodid);
    }

    /**
     * 我的作业
     */
    @Override
    public void myTask() {
        songView.showLoad();
        interactor.myTask();
    }

    /**
     * 作业详情
     */
    @Override
    public void taskDetails(int classid, int type, String week, String taskTime, int classify) {
        this.classify = classify;
        songView.showLoad();
        interactor.taskDetails(classid, type, week, taskTime, classify);
    }

    /**
     * 完成作业
     *
     * @param taskid
     * @param likes
     * @param listen
     * @param homeworkid
     */
    @Override
    public void completeTask(int cid, int taskid, int type, int likes, int listen, int homeworkid) {
        this.taskid = taskid;
        songView.showLoad();
        interactor.completeTask(cid, taskid, type, likes, listen, homeworkid);
    }

    /**
     * 上传作业图片
     *
     * @param taskid
     * @param path
     */
    @Override
    public void uploadTaskImage(int taskid, List<String> path, int type) {
        this.taskid = taskid;
        songView.showLoad();
        interactor.uploadTaskImage(taskid, path, type);
    }

    /**
     * 提交作业
     *
     * @param taskid
     * @param remarks
     */
    @Override
    public void submitTask(int taskid, String remarks, int status, int type) {
        if (submitTask) {
            submitTask = false;
            this.taskid = taskid;
            songView.showLoad();
            interactor.submitTask(taskid, remarks, status, type);
        }

    }

    @Override
    public void clockinShare(int type, int bookid) {
        songView.showLoad();
        interactor.clockinShare(type, bookid);
    }

    @Override
    public void getCardDetail() {
//        songView.showLoad();
        interactor.getCardDetail();
    }

    @Override
    public void shareSuccess(int moerduo, int yuedu, int kouyu) {
        interactor.shareSuccess(moerduo, yuedu, kouyu);
    }

    @Override
    public void getMySpeaking() {
        songView.showLoad();
        interactor.getMySpeaking();
    }

    @Override
    public void commitSpeaking(String type, String duration) {
        songView.showLoad();
        interactor.commitSpeaking(type, duration);
    }

    @Override
    public void unlockBook(int nectar, String bookname, int bookid, int classId) {
        this.classId = classId;
        interactor.unlockBook(nectar, bookname, bookid, classId);
    }

    @Override
    public void getSpeaking() {
        interactor.getSpeaking();
    }

    @Override
    public void getRelease(int bookId) {
        interactor.getRelease(bookId);
    }

    @Override
    public void ReleaseBook(int bookId) {
        songView.showLoad();
        interactor.ReleaseBook(bookId);
    }

    @Override
    public void releaseSuccess(int bookId) {
        songView.showLoad();
        interactor.releaseSuccess(bookId);
    }

    @Override
    public void playTimes(int id) {
        interactor.playtimes(id);
    }

    @Override
    public void addlikes(int id) {
        songView.showLoad();
        interactor.addlikes(id);
    }

    @Override
    public void cancellikes(int id) {
        songView.showLoad();
        interactor.cancellikes(id);
    }

    @Override
    public void shareCoin(int record, int type) {
        songView.showLoad();
        interactor.shareCoin(record, type);
    }

    @Override
    public void getRadio(String type, int radioid) {
        grindEarView.showLoad();
        interactor.getRadio(type, radioid);
    }

    @Override
    public void getLyric(int bookid) {
        songView.showLoad();
        interactor.getLyric(bookid);
    }

    @Override
    public void luckDraw(int nectar) {
        interactor.luckDraw(nectar);
    }

    @Override
    public void getHomepage(String otherusername) {
        songView.showLoad();
        interactor.getHomepage(otherusername);
    }

    @Override
    public void getProdutionList(int page, String otherusername) {
//        songView.showLoad();
        interactor.getProductionList(page, otherusername);
    }

    @Override
    public void myRecordings(int type, int page) {
        this.type = type;
        interactor.myRecordings(type, page);
    }

    @Override
    public void deleteRecording(int recordingId, int origin, int tag) {
        this.type = tag;
        songView.showLoad();
        interactor.deleteRecording(recordingId, origin, tag);
    }

    @Override
    public void cancelRelease(int bookid, int tag) {
        songView.showLoad();
        type = tag;
        interactor.cancelRelease(bookid, tag);
    }

    @Override
    public void uploadimgH5(String path, String title) {
        songView.showLoad();
        interactor.uploadimgH5(path, title);
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
            defaultSliderView.bundle(new Bundle());
            defaultSliderView.getBundle().putInt("extra", name);
            defaultSliderView.setOnSliderClickListener(this);
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
//        Uri uri = Uri.parse(bannerList.get(slider.getBundle().getInt("extra")).getUrl());
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        context.startActivity(intent);
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
                 * {@link com.annie.annieforchild.ui.activity.reading.ReadingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES7) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.reading.ReadingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES8) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.reading.ReadingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES9) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.reading.ReadingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES10) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.reading.ReadingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES11) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.reading.ReadingActivity#onMainEventThread(JTMessage)}
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
                 * {@link com.annie.annieforchild.ui.activity.mains.BankBookActivity#onMainEventThread(JTMessage)}
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
                } else {
                    message.what = MethodCode.EVENT_PK_EXERCISE;
                }
                message.obj = book;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_UPLOADAUDIO) {
                AudioBean audioBean = (AudioBean) result;
                audioBean.setLineId(lineId);
                audioBean.setPage(page);
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.ExerciseActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.pk.RecordingActivity#onMainEventThread(JTMessage)}
                 * {@link BookPlayFragment2#onMainEventThread(JTMessage)}
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
                 * {@link com.annie.annieforchild.ui.activity.mains.HomePageActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELLIKESTUDENT) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.mains.RankingActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.mains.HomePageActivity#onMainEventThread(JTMessage)}
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
//                songView.showInfo((String) result);
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
//                    Dialog dialog = SystemUtils.CoundownDialog((Activity) context);
//                    dialog.show();
//                    SystemUtils.setBackGray((Activity) context, true);
                    SystemUtils.show(context, "今日已签到");

                    application.getSystemUtils().getSigninBean().setNectar(true);
                    application.getSystemUtils().getSigninBean().save();
                } else {
                    application.getSystemUtils().getSigninBean().setNectar(true);
                    application.getSystemUtils().getSigninBean().save();
                }
                if (application.getSystemUtils().getTimer() != null) {
                    application.getSystemUtils().getTimer().cancel();
                    application.getSystemUtils().setTimer(null);
                }
                if (application.getSystemUtils().getTimer() != null) {
                    application.getSystemUtils().getTimer().cancel();
                    application.getSystemUtils().setTimer(null);
                }
            } else if (what == MethodCode.EVENT_IWANTLISTEN) {
                List<Song> songList = (List<Song>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.mains.IWantListenActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = songList;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_ACCESSBOOK) {

            } else if (what == MethodCode.EVENT_UPLOADAUDIOTIME) {

            } else if (what == MethodCode.EVENT_LUCKDRAW) {
                AudioBean audioBean = (AudioBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.RecordingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = audioBean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYPERIOD) {
                PeriodBean periodBean = (PeriodBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.MyPeriodActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = periodBean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_SUGGESTPERIOD) {
                String msg = (String) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.MyPeriodActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = msg;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYTASK) {
                TaskBean taskBean = (TaskBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.task.TaskFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.lesson.TaskActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = taskBean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_TASKDETAILS + 50000 + classify) {
                TaskDetails taskDetails = (TaskDetails) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.task.TaskContentFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = taskDetails;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_COMPLETETASK + 70000 + taskid) {
                int result1 = (int) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.task.TaskContentFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result1;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_UPLOADTASKIMAGE + 40000 + taskid) {
                String msg = (String) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.task.TaskContentFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = msg;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_SUBMITTASK + 60000 + taskid) {
                submitTask = true;
                String msg = (String) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.task.TaskContentFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = msg;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CLOCKINSHARE) {
                ShareBean shareBean = (ShareBean) result;
//                String url = (String) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.DakaFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = shareBean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETCARDDETAIL) {
                ClockIn clockIn = (ClockIn) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.DakaFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = clockIn;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMYSPEAKING) {
                MyGrindEarBean bean = (MyGrindEarBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.myspeaking.TodaySpeakingFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.fragment.myspeaking.TotalSpeakingFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage jtMessage = new JTMessage();
                jtMessage.what = what;
                jtMessage.obj = bean;
                EventBus.getDefault().post(jtMessage);
            } else if (what == MethodCode.EVENT_COMMITSPEAKING) {
                songView.showInfo((String) result);
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.InputActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_UNLOCKBOOK + 9000 + classId) {
                /**
                 * {@link }
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSPEAKING) {
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
                 * {@link com.annie.annieforchild.ui.activity.speaking.SpeakingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = readingData;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES1) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.speaking.SpeakingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES2) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.speaking.SpeakingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES3) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.speaking.SpeakingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES4) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.speaking.SpeakingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES5) {
                List<SongClassify> lists = (List<SongClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.speaking.SpeakingActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETRELEASE) {
                Release release = (Release) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.book.BookPlayEndFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = release;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_RELEASEBOOK) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.book.BookPlayEndFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_RELEASESUCCESS) {
                Release release = (Release) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.ReleaseSuccessActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = release;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_ADDLIKES) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.PracticeActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.mains.HomePageActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELLIKES) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.PracticeActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.mains.HomePageActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_SHARECOIN) {
                ShareCoin shareCoin = (ShareCoin) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.ReleaseSuccessActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.pk.ReleaseSuccessActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.pk.ReleaseSuccessActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.pk.ReleaseSuccessActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.pk.ReleaseSuccessActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = shareCoin;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETRADIO) {
                List<Song> lists = (List<Song>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETLYRIC) {
                List<String> lists = (List<String>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.pk.MusicPlayActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETHOMEPAGE) {
                Hpbean hpbean = (Hpbean) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.mains.HomePageActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = hpbean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETPRODUCTIONLIST) {
                ProductionBean productionBean = (ProductionBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.mains.HomePageActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = productionBean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYRECORDINGS + 10000 + type) {
                RecordBean recordBean = (RecordBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.recording.MyReleaseFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = recordBean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_DELETERECORDING + 30000 + type) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.recording.MyReleaseFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELRELEASE + 20000 + type) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.recording.MyReleaseFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_UPLOADIMGH5) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.WebActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
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
        if (what == MethodCode.EVENT_RELOGIN) {
            if (!application.getSystemUtils().isReLogin()) {
                application.getSystemUtils().setReLogin(true);
                if (grindEarView != null) {
                    grindEarView.showInfo("该账号已在别处登陆");
                }
                if (songView != null) {
                    songView.showInfo("该账号已在别处登陆");
                }
                if (viewInfo != null) {
                    viewInfo.showInfo("该账号已在别处登陆");
                }

                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_RELOGIN;
                message.obj = 1;
                EventBus.getDefault().post(message);

                SharedPreferences preferences = context.getSharedPreferences("userInfo", MODE_PRIVATE | MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("phone");
                editor.remove("psd");
                editor.commit();
                application.getSystemUtils().getPhoneSN().setUsername(null);
                application.getSystemUtils().getPhoneSN().setLastlogintime(null);
                application.getSystemUtils().getPhoneSN().setSystem(null);
                application.getSystemUtils().getPhoneSN().setBitcode(null);
                application.getSystemUtils().setDefaultUsername(null);
                application.getSystemUtils().setToken(null);
                application.getSystemUtils().getPhoneSN().save();
                application.getSystemUtils().setOnline(false);
                ActivityCollector.finishAll();
                Intent intent2 = new Intent(context, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
                return;
            } else {
                return;
            }
        }
        if (what == MethodCode.EVENT_GETCARDDETAIL) {
            ClockIn clockIn = null;
            /**
             * {@link com.annie.annieforchild.ui.fragment.DakaFragment#onMainEventThread(JTMessage)}
             */
            JTMessage message = new JTMessage();
            message.what = what;
            message.obj = clockIn;
            EventBus.getDefault().post(message);
        } else if (what == MethodCode.EVENT_UNLOCKBOOK + 9000 + classId) {
            /**
             * {@link }
             */
            JTMessage message = new JTMessage();
            message.what = what;
            message.obj = error;
            EventBus.getDefault().post(message);
        } else if (what == MethodCode.EVENT_CLOCKINSHARE) {
            /**
             * {@link }
             */
            JTMessage message = new JTMessage();
            message.what = what;
            message.obj = error;
            EventBus.getDefault().post(message);
        } else if (what == MethodCode.EVENT_GETLISTENING) {
            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_ERROR;
            message.obj = error;
            EventBus.getDefault().post(message);
        } else if (what == MethodCode.EVENT_GETREADING) {
            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_ERROR;
            message.obj = error;
            EventBus.getDefault().post(message);
        } else if (what == MethodCode.EVENT_GETSPEAKING) {
            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_ERROR;
            message.obj = error;
            EventBus.getDefault().post(message);
        } else if (what == MethodCode.EVENT_SUBMITTASK + 60000 + taskid) {
            submitTask = true;
            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_ERROR;
            message.obj = error;
            EventBus.getDefault().post(message);
        }
    }
}

