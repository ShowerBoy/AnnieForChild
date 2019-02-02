package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.AnimationData;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.ClockIn;
import com.annie.annieforchild.bean.DurationStatis;
import com.annie.annieforchild.bean.PkResult;
import com.annie.annieforchild.bean.ReadingData;
import com.annie.annieforchild.bean.record.Record;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.Release;
import com.annie.annieforchild.bean.grindear.GrindEarData;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.bean.period.PeriodBean;
import com.annie.annieforchild.bean.rank.Hpbean;
import com.annie.annieforchild.bean.rank.ProductionBean;
import com.annie.annieforchild.bean.rank.RankList;
import com.annie.annieforchild.bean.rank.SquareRankList;
import com.annie.annieforchild.bean.record.RecordBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.bean.task.TaskBean;
import com.annie.annieforchild.bean.task.TaskContent;
import com.annie.annieforchild.bean.task.TaskDetails;
import com.annie.annieforchild.interactor.GrindEarInteractor;
import com.annie.annieforchild.ui.fragment.recording.MyReleaseFragment;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 磨耳朵
 * Created by wanglei on 2018/3/21.
 */

public class GrindEarInteractorImp extends NetWorkImp implements GrindEarInteractor {
    private Context context;
    private RequestListener listener;
    private int classId;
    private int type;
    private int taskid;
    private int classify;

    public GrindEarInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void getListening() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETLISTENING, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_GETLISTENING, request);
//        startQueue();
    }

    @Override
    public void getMyListening() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMYLISTENING, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_GETMYLISTENING, request);
//        startQueue();
    }

    @Override
    public void collectCourse(int type, int audioSource, int courseId, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.COLLECTCOURSE, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("type", type);
        request.add("audioSource", audioSource);
        request.add("courseId", courseId);
        addQueue(MethodCode.EVENT_COLLECTCOURSE + 2000 + classId, request);
//        startQueue();
    }

    @Override
    public void cancelCollection(int type, int audioSource, int courseId, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.CANCELCOLLECTION, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("audioSource", audioSource);
        request.add("type", type);
        request.add("courseId", courseId);
        addQueue(MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId, request);
//        startQueue();
    }

    @Override
    public void getMusicClasses() {
        FastJsonRequest request1 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request1.add("username", SystemUtils.defaultUsername);
        request1.add("token", SystemUtils.token);
        request1.add("type", 1);
        FastJsonRequest request2 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request2.add("username", SystemUtils.defaultUsername);
        request2.add("token", SystemUtils.token);
        request2.add("type", 2);
        FastJsonRequest request3 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request3.add("username", SystemUtils.defaultUsername);
        request3.add("token", SystemUtils.token);
        request3.add("type", 0);
        FastJsonRequest request4 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request4.add("username", SystemUtils.defaultUsername);
        request4.add("token", SystemUtils.token);
        request4.add("type", 4);
        FastJsonRequest request5 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request5.add("username", SystemUtils.defaultUsername);
        request5.add("token", SystemUtils.token);
        request5.add("type", 5);
        FastJsonRequest request6 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request6.add("username", SystemUtils.defaultUsername);
        request6.add("token", SystemUtils.token);
        request6.add("type", 3);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES1, request1);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES2, request2);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES3, request3);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES4, request4);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES5, request5);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES11, request6);
//        startQueue();
    }

    @Override
    public void getReadingClasses() {
        FastJsonRequest request1 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request1.add("username", SystemUtils.defaultUsername);
        request1.add("token", SystemUtils.token);
        request1.add("type", 6);
        FastJsonRequest request2 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request2.add("username", SystemUtils.defaultUsername);
        request2.add("token", SystemUtils.token);
        request2.add("type", 7);
        FastJsonRequest request3 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request3.add("username", SystemUtils.defaultUsername);
        request3.add("token", SystemUtils.token);
        request3.add("type", 8);
        FastJsonRequest request4 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request4.add("username", SystemUtils.defaultUsername);
        request4.add("token", SystemUtils.token);
        request4.add("type", 9);
        FastJsonRequest request5 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request5.add("username", SystemUtils.defaultUsername);
        request5.add("token", SystemUtils.token);
        request5.add("type", 10);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES6, request1);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES7, request2);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES8, request3);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES9, request4);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES10, request5);
//        startQueue();
    }

    @Override
    public void getMusicList(int classId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICLIST, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICLISTTEST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("classId", classId);
        this.classId = classId;
        addQueue(MethodCode.EVENT_GETMUSICLIST + 1000 + classId, request);
//        startQueue();
    }

    @Override
    public void commitDuration(String[] type, String[] duration) {
        for (int i = 0; i < type.length; i++) {
            if (!duration[i].equals("0")) {
                FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.COMMITDURATION, RequestMethod.POST);
                request.add("username", SystemUtils.defaultUsername);
                request.add("token", SystemUtils.token);

                request.add("type", type[i]);
                request.add("duration", Integer.parseInt(duration[i]) * 60 + "");
                addQueue(MethodCode.EVENT_COMMITDURATION, request);
            }
        }
//        startQueue();
    }

    @Override
    public void getBookScore(int bookId, int record, boolean accessBook) {
        if (accessBook) {
            FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.ACCESSBOOK, RequestMethod.POST);
            request.add("username", SystemUtils.defaultUsername);
            request.add("token", SystemUtils.token);
            request.add("bookId", bookId);
            FastJsonRequest request1 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETBOOKSCORE, RequestMethod.POST);
            request1.add("username", SystemUtils.defaultUsername);
            request1.add("token", SystemUtils.token);
            request1.add("bookId", bookId);
            request1.add("record", record);
            addQueue(MethodCode.EVENT_ACCESSBOOK, request);
            addQueue(MethodCode.EVENT_GETBOOKSCORE, request1);
//            startQueue();
        } else {
            FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETBOOKSCORE, RequestMethod.POST);
            request.add("username", SystemUtils.defaultUsername);
            request.add("token", SystemUtils.token);
            request.add("bookId", bookId);
            request.add("record", record);
            addQueue(MethodCode.EVENT_GETBOOKSCORE, request);
//            startQueue();
        }
    }

    @Override
    public void getBookAudioData(int bookId, int pkType, String pkUsername) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETBOOKAUDIODATA, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETBOOKAUDIODATATEST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookId", bookId);
        request.add("pkType", pkType);

        if (pkUsername != null) {
            request.add("pkUsername", pkUsername);
        }
        addQueue(MethodCode.EVENT_GETBOOKAUDIODATA, request);
//        startQueue();
    }

    /**
     * @param resourseId
     * @param page
     * @param audioType
     * @param audioSource
     * @param lineId
     * @param path
     * @param score
     * @param title
     * @param duration
     * @param origin
     * @param pkUsername
     * @param imageUrl
     * @param animationCode
     * @param homeworkid    0
     * @param homeworktype  -1
     */
    @Override
    public void uploadAudioResource(int resourseId, int page, int audioType, int audioSource, int lineId, String path, float score, String title, int duration, int origin, String pkUsername, String imageUrl, int animationCode, int homeworkid, int homeworktype) {
        File file = new File(path);
        FileBinary fileBinary = new FileBinary(file);
        //如果时长小于1秒算成1秒
        if (duration == 0) {
            duration = 1;
        }
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.UPLOADAUDIO, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("resourseId", resourseId);
        request.add("audioType", audioType);
        request.add("audioSource", audioSource);
        request.add("page", page);
        request.add("lineId", lineId);
        request.add("file", fileBinary);
        request.add("score", score);
        request.add("title", title);
        request.add("imageUrl", imageUrl);
        request.add("duration", duration);
        request.add("origin", origin);
        request.add("animationCode", animationCode);
        request.add("homeworkid", homeworkid);
        request.add("homeworktype", homeworktype);
        if (origin == 2) {
            request.add("pkUsername", pkUsername);
        }
        addQueue(MethodCode.EVENT_UPLOADAUDIO, request);
//        startQueue();
    }

    @Override
    public void getPkUsers(int bookId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETPKUSERS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("bookId", bookId);
        addQueue(MethodCode.EVENT_GETPKUSERS, request);
//        startQueue();
    }

    @Override
    public void getPkResult(int bookId, String pkUsername, int pkType) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETPKRESULT, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("bookId", bookId);
        request.add("pkUsername", pkUsername);
        request.add("pkType", pkType);
        addQueue(MethodCode.EVENT_GETPKRESULT, request);
//        startQueue();
    }

    @Override
    public void joinMaterial(int bookId, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.JOINMATERIAL, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("bookId", bookId);
        addQueue(MethodCode.EVENT_JOINMATERIAL + 4000 + classId, request);
//        startQueue();
    }

    @Override
    public void cancelMaterial(int bookId, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.CANCELMATERIAL, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("bookId", bookId);
        addQueue(MethodCode.EVENT_CANCELMATERIAL + 5000 + classId, request);
//        startQueue();
    }

    @Override
    public void getRank(int spaceType, int timeType) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETRANK, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("spaceType", spaceType);
        request.add("timeType", timeType);
        addQueue(MethodCode.EVENT_GETRANK, request);
//        startQueue();
    }

    @Override
    public void getSquareRank() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SQUAREAPI + MethodType.GETSQUARERANK, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_GETSQUARERANK, request);
//        startQueue();
    }

    @Override
    public void getSquareRankList(int resourceType, int timeType, int locationType) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SQUAREAPI + MethodType.GETSQUARERANKLIST, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("resourceType", resourceType);
        request.add("timeType", timeType);
        request.add("locationType", locationType);
        addQueue(MethodCode.EVENT_GETSQUARERANKLIST, request);
//        startQueue();
    }

    @Override
    public void likeStudent(String likeUsername) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SQUAREAPI + MethodType.LIKESTUDENT, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("likeUsername", likeUsername);
        addQueue(MethodCode.EVENT_LIKESTUDENT, request);
//        startQueue();
    }

    @Override
    public void cancelLikeStudent(String cancelLikeUsername) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SQUAREAPI + MethodType.CANCELLIKESTUDENT, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("cancelLikeUsername", cancelLikeUsername);
        addQueue(MethodCode.EVENT_CANCELLIKESTUDENT, request);
//        startQueue();
    }

    @Override
    public void getReading() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETREADING, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_GETREADING, request);
//        startQueue();
    }

    @Override
    public void getMyReading() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMYREADING, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_GETMYREADING, request);
        startQueue();
    }

    @Override
    public void commitReading(String[] type, String[] duration, int books, int words) {
        for (int i = 0; i < type.length; i++) {
            if (!duration[i].equals("0")) {
                FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.COMMITREADING, RequestMethod.POST);
                request.add("username", SystemUtils.defaultUsername);
                request.add("token", SystemUtils.token);
                request.add("type", type[i]);
                request.add("duration", Integer.parseInt(duration[i]) * 60 + "");
                request.add("books", books);
                request.add("words", words);
                addQueue(MethodCode.EVENT_COMMITREADING, request);
            }
        }
//        startQueue();
    }

    @Override
    public void getDurationStatistics(int timeType, int locationType) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SQUAREAPI + MethodType.GETDURATIONSTATISICS, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("timeType", timeType);
        request.add("locationType", locationType);
        addQueue(MethodCode.EVENT_GETDURATIONSTATISTICS, request);
//        startQueue();
    }

    @Override
    public void getReadList(int classId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETREADLIST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("classId", classId);
        this.classId = classId;
        addQueue(MethodCode.EVENT_GETREADLIST + 6000 + classId, request);
//        startQueue();
    }

    @Override
    public void getQrCode() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.GETQRCODE, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_GETQRCODE, request);
//        startQueue();
    }

    @Override
    public void getAnimationList(String title, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETANIMATIONLIST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("title", title);
        addQueue(MethodCode.EVENT_GETANIMATIONLIST + 7000 + classId, request);
//        startQueue();
    }

    @Override
    public void getSpokenClasses() {
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
//        request.add("username", SystemUtils.defaultUsername);
//        request.add("token", SystemUtils.token);
//        request.add("type", -1);
        FastJsonRequest request1 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request1.add("username", SystemUtils.defaultUsername);
        request1.add("token", SystemUtils.token);
        request1.add("type", 11);
        FastJsonRequest request2 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request2.add("username", SystemUtils.defaultUsername);
        request2.add("token", SystemUtils.token);
        request2.add("type", 12);
        FastJsonRequest request3 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request3.add("username", SystemUtils.defaultUsername);
        request3.add("token", SystemUtils.token);
        request3.add("type", 13);
        FastJsonRequest request4 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request4.add("username", SystemUtils.defaultUsername);
        request4.add("token", SystemUtils.token);
        request4.add("type", 14);
        FastJsonRequest request5 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request5.add("username", SystemUtils.defaultUsername);
        request5.add("token", SystemUtils.token);
        request5.add("type", 15);
//        addQueue(MethodCode.EVENT_GETSPOKENCLASSES, request);
        addQueue(MethodCode.EVENT_GETSPOKENCLASSES1, request1);
        addQueue(MethodCode.EVENT_GETSPOKENCLASSES2, request2);
        addQueue(MethodCode.EVENT_GETSPOKENCLASSES3, request3);
        addQueue(MethodCode.EVENT_GETSPOKENCLASSES4, request4);
        addQueue(MethodCode.EVENT_GETSPOKENCLASSES5, request5);
//        startQueue();
    }


    @Override
    public void getSpokenList(int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETSPOKENLSIST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("classId", classId);
        addQueue(MethodCode.EVENT_GETSPOKENLIST + 8000 + classId, request);
//        startQueue();
    }

    @Override
    public void commitBook(List<String> lists) {
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < lists.size(); i++) {
            if (i == 0) {
                stringBuffer.append(lists.get(i));
            } else {
                stringBuffer.append("||" + lists.get(i));
            }
        }
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.COMMITBOOK, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookName", stringBuffer.toString());
        addQueue(MethodCode.EVENT_COMMITBOOK, request);
//        startQueue();
    }

    @Override
    public void DailyPunch() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SIGNINAPI + MethodType.DAILYPUNCH, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_DAILYPUNCH, request);
//        startQueue();
    }

    @Override
    public void iWantListen(int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.IWANTLISTEN, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("type", type);
        addQueue(MethodCode.EVENT_IWANTLISTEN, request);
//        startQueue();
    }

    @Override
    public void accessBook(int bookId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.ACCESSBOOK, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookId", bookId);
        addQueue(MethodCode.EVENT_ACCESSBOOK, request);
//        startQueue();
    }

    @Override
    public void uploadAudioTime(int origin, int audioType, int audioSource, int resourseId, int duration) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.UPLOADAUDIOTIME, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("origin", origin);
        request.add("audioType", audioType);
        request.add("audioSource", audioSource);
        request.add("resourseId", resourseId);
        request.add("duration", duration);
        addQueue(MethodCode.EVENT_UPLOADAUDIOTIME, request);
//        startQueue();
    }

    @Override
    public void myPeriod() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.MYPERIOD, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_MYPERIOD, request);
//        startQueue();
    }

    @Override
    public void suggestPeriod(int periodid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.SUGGESTPERIOD, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("periodid", periodid);
        addQueue(MethodCode.EVENT_SUGGESTPERIOD, request);
//        startQueue();
    }

    @Override
    public void myTask() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.TASKAPI + MethodType.MYTASK, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_MYTASK, request);
//        startQueue();
    }

    @Override
    public void taskDetails(int classid, int type, String week, String taskTime, int classify) {
        this.classify = classify;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.TASKAPI + MethodType.TASKDETAILS, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("classid", classid);
        request.add("classify", classify);
        request.add("type", type);
        if (type == 0) {
            request.add("tasktime", taskTime);
        } else {
            request.add("week", week);
        }
        addQueue(MethodCode.EVENT_TASKDETAILS + 50000 + classify, request);
//        startQueue();
    }

    @Override
    public void completeTask(int taskid, int type, int likes, int listen, int homeworkid) {
        this.taskid = taskid;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.TASKAPI + MethodType.COMPLETETASK, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("type", type);
        request.add("likes", likes);
        request.add("listen", listen);
        request.add("homeworkid", homeworkid);
        addQueue(MethodCode.EVENT_COMPLETETASK + 70000 + taskid, request);
//        startQueue();
    }

    @Override
    public void uploadTaskImage(int taskid, List<String> path, int type) {
        this.taskid = taskid;
        for (int i = 0; i < path.size(); i++) {
            File file = new File(path.get(i));
            FileBinary fileBinary = new FileBinary(file);
            FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.TASKAPI + MethodType.UPLOADTASKIMAGE, RequestMethod.POST);
            request.add("token", SystemUtils.token);
            request.add("username", SystemUtils.defaultUsername);
            request.add("taskid", taskid);
            request.add("type", type);
            request.add("file", fileBinary);
            addQueue(MethodCode.EVENT_UPLOADTASKIMAGE + 40000 + taskid, request);
        }
//        startQueue();
    }

    @Override
    public void submitTask(int taskid, String remarks, int status, int type) {
        this.taskid = taskid;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.TASKAPI + MethodType.SUBMITTASK, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("status", status);
        request.add("type", type);
        request.add("taskid", taskid);
        request.add("remarks", remarks);
        addQueue(MethodCode.EVENT_SUBMITTASK + 60000 + taskid, request);
//        startQueue();
    }

    @Override
    public void clockinShare(int type, int bookid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SHAREAPI + MethodType.CLOCKINSHARE, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("type", type);
        if (bookid != -1) {
            request.add("bookid", bookid);
        }
        addQueue(MethodCode.EVENT_CLOCKINSHARE, request);
//        startQueue();
    }

    @Override
    public void getCardDetail() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETCARDDETAIL, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_GETCARDDETAIL, request);
//        startQueue();
    }

    @Override
    public void shareSuccess(int moerduo, int yuedu, int kouyu) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SHAREAPI + MethodType.SHARESUCCESS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("moerduo", moerduo);
        request.add("yuedu", yuedu);
        request.add("kouyu", kouyu);
        addQueue(MethodCode.EVENT_SHARESUCCESS, request);
//        startQueue();
    }

    @Override
    public void getMySpeaking() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMYSPEAKING, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_GETMYSPEAKING, request);
//        startQueue();
    }

    @Override
    public void commitSpeaking(String[] type, String[] duration) {
        for (int i = 0; i < type.length; i++) {
            if (!duration[i].equals("0")) {
                FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.COMMITSPEAKING, RequestMethod.POST);
                request.add("username", SystemUtils.defaultUsername);
                request.add("token", SystemUtils.token);
                request.add("type", type[i]);
                request.add("duration", Integer.parseInt(duration[i]) * 60 + "");
                addQueue(MethodCode.EVENT_COMMITSPEAKING, request);
            }
        }
//        startQueue();
    }

    @Override
    public void unlockBook(int nectar, String bookname, int bookid, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.UNLOCKBOOK, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("nectar", nectar);
        request.add("bookname", bookname);
        request.add("bookid", bookid);
        addQueue(MethodCode.EVENT_UNLOCKBOOK + 9000 + classId, request);
//        startQueue();
    }

    @Override
    public void getSpeaking() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETSPEAKING, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_GETSPEAKING, request);
//        startQueue();
    }

    @Override
    public void getRelease(int bookId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETRELEASE, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookid", bookId);
        addQueue(MethodCode.EVENT_GETRELEASE, request);
//        startQueue();
    }

    @Override
    public void ReleaseBook(int bookId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.RELEASEBOOK, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookid", bookId);
        addQueue(MethodCode.EVENT_RELEASEBOOK, request);
//        startQueue();
    }

    @Override
    public void releaseSuccess(int bookId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.RELEASESUCCESS, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookid", bookId);
        addQueue(MethodCode.EVENT_RELEASESUCCESS, request);
//        startQueue();
    }

    @Override
    public void playtimes(int id) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.PLAYTIMES, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("id", id);
        addQueue(MethodCode.EVENT_PLAYTIMES, request);
//        startQueue();
    }

    @Override
    public void addlikes(int id) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.ADDLIKES, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("id", id);
        addQueue(MethodCode.EVENT_ADDLIKES, request);
//        startQueue();
    }

    @Override
    public void cancellikes(int id) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.CANCELLIKES, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("id", id);
        addQueue(MethodCode.EVENT_CANCELLIKES, request);
//        startQueue();
    }

    @Override
    public void shareCoin(int record, int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SHAREAPI + MethodType.SHARECOIN, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("record", record);
        request.add("type", type);
        addQueue(MethodCode.EVENT_SHARECOIN, request);
//        startQueue();
    }

    @Override
    public void getRadio(String type, int radioid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETRADIO, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("radioid", radioid);
        request.add("type", type);
        addQueue(MethodCode.EVENT_GETRADIO, request);
//        startQueue();
    }

    @Override
    public void getLyric(int bookid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETLYRIC, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookid", bookid);
        addQueue(MethodCode.EVENT_GETLYRIC, request);
//        startQueue();
    }

    @Override
    public void luckDraw(int nectar) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.LUCKDRAW, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("nectar", nectar);
        addQueue(MethodCode.EVENT_LUCKDRAW, request);
//        startQueue();
    }

    @Override
    public void getHomepage(String otherusername) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETHOMEPAGE, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("otherusername", otherusername);
        addQueue(MethodCode.EVENT_GETHOMEPAGE, request);
//        startQueue();
    }

    @Override
    public void getProductionList(int page, String otherusername) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETPRODUCTIONLIST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("otherusername", otherusername);
        request.add("page", page);
        addQueue(MethodCode.EVENT_GETPRODUCTIONLIST, request);
//        startQueue();
    }

    @Override
    public void myRecordings(int type, int page) {
        this.type = type;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.MYRECORDINGS, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.MYRECORDINGSTEST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("type", type);
        request.add("page", page);
        addQueue(MethodCode.EVENT_MYRECORDINGS + 10000 + type, request);
//        startQueue();
    }

    @Override
    public void deleteRecording(int recordingId, int origin, int tag) {
        this.type = tag;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.DELETERECORDING, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.DELETERECORDINGTEST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("origin", origin);
        request.add("recordingId", recordingId);
        addQueue(MethodCode.EVENT_DELETERECORDING + 30000 + type, request);
//        startQueue();
    }

    @Override
    public void cancelRelease(int bookid, int tag) {
        this.type = tag;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.CANCELRELEASE, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookid", bookid);
        addQueue(MethodCode.EVENT_CANCELRELEASE + 20000 + type, request);
//        startQueue();
    }


    @Override
    protected void onNetWorkStart(int what) {

    }

    @Override
    protected void onNetWorkFinish(int what) {

    }

    @Override
    protected void onSuccess(int what, Object response) {
        String jsonString = response.toString();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        int errorType = jsonObject.getInteger(MethodCode.ERRTYPE);
        String errorInfo = jsonObject.getString(MethodCode.ERRINFO);
        String data = jsonObject.getString(MethodCode.DATA);
        if (errorType == 3) {
            listener.Error(what, errorInfo);
        } else {
            if (what == MethodCode.EVENT_GETLISTENING) {
                GrindEarData grindEarData = JSON.parseObject(data, GrindEarData.class);
                listener.Success(what, grindEarData);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES1) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES2) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES3) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES4) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES5) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES6) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES7) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES8) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES9) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES10) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES11) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICLIST + 1000 + classId) {
                List<Song> songList;
                if (data != null) {
                    songList = JSON.parseArray(data, Song.class);
                } else {
                    songList = new ArrayList<>();
                }
                listener.Success(what, songList);
            } else if (what == MethodCode.EVENT_GETREADLIST + 6000 + classId) {
                List<Song> songList;
                if (data != null) {
                    songList = JSON.parseArray(data, Song.class);
                } else {
                    songList = new ArrayList<>();
                }
                listener.Success(what, songList);
            } else if (what == MethodCode.EVENT_GETMYLISTENING) {
                MyGrindEarBean bean = JSON.parseObject(data, MyGrindEarBean.class);
                listener.Success(what, bean);
            } else if (what == MethodCode.EVENT_COMMITDURATION) {
                listener.Success(what, "提交成功");
            } else if (what == MethodCode.EVENT_GETBOOKSCORE) {
                Song song = JSON.parseObject(data, Song.class);
                listener.Success(what, song);
            } else if (what == MethodCode.EVENT_GETBOOKAUDIODATA) {
                Book book = JSON.parseObject(data, Book.class);
                listener.Success(what, book);
            } else if (what == MethodCode.EVENT_UPLOADAUDIO) {
                AudioBean audioBean = JSON.parseObject(data, AudioBean.class);
                listener.Success(what, audioBean);
            } else if (what == MethodCode.EVENT_COLLECTCOURSE + 2000 + classId) {
                listener.Success(what, "收藏成功");
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId) {
                listener.Success(what, "取消收藏成功");
            } else if (what == MethodCode.EVENT_GETPKUSERS) {
                List<UserInfo2> lists = JSON.parseArray(data, UserInfo2.class);
                if (lists == null) {
                    lists = new ArrayList<>();
                }
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_GETPKRESULT) {
                PkResult pkResult = JSON.parseObject(data, PkResult.class);
                listener.Success(what, pkResult);
            } else if (what == MethodCode.EVENT_JOINMATERIAL + 4000 + classId) {
                listener.Success(what, "加入成功");
            } else if (what == MethodCode.EVENT_CANCELMATERIAL + 5000 + classId) {
                listener.Success(what, "取消加入成功");
            } else if (what == MethodCode.EVENT_GETRANK) {
                listener.Success(what, jsonString);
            } else if (what == MethodCode.EVENT_GETSQUARERANK) {
                RankList rankList = JSON.parseObject(data, RankList.class);
                listener.Success(what, rankList);
            } else if (what == MethodCode.EVENT_GETSQUARERANKLIST) {
                SquareRankList squareRankList = JSON.parseObject(data, SquareRankList.class);
                listener.Success(what, squareRankList);
            } else if (what == MethodCode.EVENT_LIKESTUDENT) {
                listener.Success(what, "已点赞");
            } else if (what == MethodCode.EVENT_CANCELLIKESTUDENT) {
                listener.Success(what, "取消点赞");
            } else if (what == MethodCode.EVENT_GETMYREADING) {
                MyGrindEarBean bean = JSON.parseObject(data, MyGrindEarBean.class);
                listener.Success(what, bean);
            } else if (what == MethodCode.EVENT_COMMITREADING) {
                listener.Success(what, "提交成功");
            } else if (what == MethodCode.EVENT_GETREADING) {
                ReadingData readingData = JSON.parseObject(data, ReadingData.class);
                listener.Success(what, readingData);
            } else if (what == MethodCode.EVENT_GETDURATIONSTATISTICS) {
                DurationStatis durationStatis = JSON.parseObject(data, DurationStatis.class);
                listener.Success(what, durationStatis);
            } else if (what == MethodCode.EVENT_GETQRCODE) {
                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                String qrCodeUrl = dataobj.getString("qrCodeUrl");
                listener.Success(what, qrCodeUrl);
            } else if (what == MethodCode.EVENT_GETANIMATIONLIST + 7000 + classId) {
                List<AnimationData> lists = JSON.parseArray(data, AnimationData.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETSPOKENLIST + 8000 + classId) {
                List<Song> songList;
                if (data != null) {
                    songList = JSON.parseArray(data, Song.class);
                } else {
                    songList = new ArrayList<>();
                }
                listener.Success(what, songList);
            } else if (what == MethodCode.EVENT_COMMITBOOK) {
                listener.Success(what, "录入成功");
            } else if (what == MethodCode.EVENT_DAILYPUNCH) {
                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                int result = dataobj.getInteger("result");
                listener.Success(what, result);
            } else if (what == MethodCode.EVENT_IWANTLISTEN) {
                List<Song> songList = JSON.parseArray(data, Song.class);
                listener.Success(what, songList);
            } else if (what == MethodCode.EVENT_ACCESSBOOK) {
                listener.Success(what, "");
            } else if (what == MethodCode.EVENT_UPLOADAUDIOTIME) {
                listener.Success(what, "");
            } else if (what == MethodCode.EVENT_MYPERIOD) {
                PeriodBean periodBean;
                if (data != null) {
                    periodBean = JSON.parseObject(data, PeriodBean.class);
                } else {
                    periodBean = new PeriodBean();
                }
                listener.Success(what, periodBean);
            } else if (what == MethodCode.EVENT_SUGGESTPERIOD) {
                listener.Success(what, "提异成功");
            } else if (what == MethodCode.EVENT_MYTASK) {
                TaskBean taskBean = JSON.parseObject(data, TaskBean.class);
                if (taskBean == null) {
                    taskBean = new TaskBean();
                }
                listener.Success(what, taskBean);
            } else if (what == MethodCode.EVENT_TASKDETAILS + 50000 + classify) {
                TaskDetails taskDetails = JSON.parseObject(data, TaskDetails.class);
                if (taskDetails == null) {
                    taskDetails = new TaskDetails();
                }
                listener.Success(what, taskDetails);
            } else if (what == MethodCode.EVENT_COMPLETETASK + 70000 + taskid) {
                int result;
                if (data != null) {
                    JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                    result = dataobj.getInteger("result");
                } else {
                    result = 1;
                }
                listener.Success(what, result);
            } else if (what == MethodCode.EVENT_UPLOADTASKIMAGE + 40000 + taskid) {
                listener.Success(what, "");
            } else if (what == MethodCode.EVENT_SUBMITTASK + 60000 + taskid) {
                listener.Success(what, data);
            } else if (what == MethodCode.EVENT_CLOCKINSHARE) {
                ShareBean shareBean = JSON.parseObject(data, ShareBean.class);
//                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
//                String url = dataobj.getString("url");
                listener.Success(what, shareBean);
            } else if (what == MethodCode.EVENT_GETCARDDETAIL) {
                ClockIn clockIn = JSON.parseObject(data, ClockIn.class);
                listener.Success(what, clockIn);
            } else if (what == MethodCode.EVENT_SHARESUCCESS) {

            } else if (what == MethodCode.EVENT_GETMYSPEAKING) {
                MyGrindEarBean bean = JSON.parseObject(data, MyGrindEarBean.class);
                listener.Success(what, bean);
            } else if (what == MethodCode.EVENT_COMMITSPEAKING) {
                listener.Success(what, "提交成功");
            } else if (what == MethodCode.EVENT_UNLOCKBOOK + 9000 + classId) {
                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                String result = dataobj.getString("result");
                listener.Success(what, result);
            } else if (what == MethodCode.EVENT_GETSPEAKING) {
                ReadingData readingData = JSON.parseObject(data, ReadingData.class);
                listener.Success(what, readingData);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES1) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES2) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES3) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES4) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETSPOKENCLASSES5) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETRELEASE) {
                Release release = JSON.parseObject(data, Release.class);
                listener.Success(what, release);
            } else if (what == MethodCode.EVENT_RELEASEBOOK) {
                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                int result = dataobj.getInteger("result");
                listener.Success(what, result);
            } else if (what == MethodCode.EVENT_RELEASESUCCESS) {
                Release release = JSON.parseObject(data, Release.class);
                listener.Success(what, release);
            } else if (what == MethodCode.EVENT_PLAYTIMES) {

            } else if (what == MethodCode.EVENT_ADDLIKES) {
                listener.Success(what, "点赞成功");
            } else if (what == MethodCode.EVENT_CANCELLIKES) {
                listener.Success(what, "取消点赞");
            } else if (what == MethodCode.EVENT_SHARECOIN) {
                listener.Success(what, jsonString);
            } else if (what == MethodCode.EVENT_GETRADIO) {
                List<Song> lists = JSON.parseArray(data, Song.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_GETLYRIC) {
                List<String> lists;
                if (data == null) {
                    lists = new ArrayList<>();
                } else {
                    lists = JSON.parseArray(data, String.class);
                }
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_LUCKDRAW) {

            } else if (what == MethodCode.EVENT_GETHOMEPAGE) {
                Hpbean hpbean = JSON.parseObject(data, Hpbean.class);
                listener.Success(what, hpbean);
            } else if (what == MethodCode.EVENT_GETPRODUCTIONLIST) {
                ProductionBean productionBean = JSON.parseObject(data, ProductionBean.class);
                listener.Success(what, productionBean);
            } else if (what == MethodCode.EVENT_MYRECORDINGS + 10000 + type) {
                RecordBean recordBean = JSON.parseObject(data, RecordBean.class);
                if (recordBean == null) {
                    recordBean = new RecordBean();
                }
                listener.Success(what, recordBean);
            } else if (what == MethodCode.EVENT_DELETERECORDING + 30000 + type) {
                listener.Success(what, "删除成功");
            } else if (what == MethodCode.EVENT_CANCELRELEASE + 20000 + type) {
                listener.Success(what, "取消发布成功");
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        String error = response.getException().getMessage();
        listener.Error(what, error);
    }
}
