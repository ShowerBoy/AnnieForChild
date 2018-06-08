package com.annie.annieforchild.interactor.imp;

import android.content.Context;
import android.provider.MediaStore;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.DurationStatis;
import com.annie.annieforchild.bean.PkResult;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.grindear.GrindEarData;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.bean.rank.RankList;
import com.annie.annieforchild.bean.rank.SquareRankList;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.interactor.GrindEarInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.List;

/**
 * 磨耳朵
 * Created by wanglei on 2018/3/21.
 */

public class GrindEarInteractorImp extends NetWorkImp implements GrindEarInteractor {
    private Context context;
    private RequestListener listener;
    private int classId;

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
        startQueue();
    }

    @Override
    public void getMyListening() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMYLISTENING, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_GETMYLISTENING, request);
        startQueue();
    }

    @Override
    public void collectCourse(int type, int courseId, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.COLLECTCOURSE, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("type", type);
        request.add("courseId", courseId);
        addQueue(MethodCode.EVENT_COLLECTCOURSE + 2000 + classId, request);
        startQueue();
    }

    @Override
    public void cancelCollection(int type, int courseId, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.CANCELCOLLECTION, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);

        request.add("type", type);
        request.add("courseId", courseId);
        addQueue(MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId, request);
        startQueue();
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
        request3.add("type", 3);
        FastJsonRequest request4 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request4.add("username", SystemUtils.defaultUsername);
        request4.add("token", SystemUtils.token);
        request4.add("type", 4);
        FastJsonRequest request5 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request5.add("username", SystemUtils.defaultUsername);
        request5.add("token", SystemUtils.token);
        request5.add("type", 5);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES1, request1);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES2, request2);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES3, request3);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES4, request4);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES5, request5);
        startQueue();
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
        startQueue();
    }

    @Override
    public void getMusicList(int classId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETMUSICLIST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("classId", classId);
        this.classId = classId;
        addQueue(MethodCode.EVENT_GETMUSICLIST + 1000 + classId, request);
        startQueue();
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
        startQueue();
    }

    @Override
    public void getBookScore(int bookId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETBOOKSCORE, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookId", bookId);
        addQueue(MethodCode.EVENT_GETBOOKSCORE, request);
        startQueue();
    }

    @Override
    public void getBookAudioData(int bookId, int pkType, String pkUsername) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETBOOKAUDIODATA, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("bookId", bookId);
        request.add("pkType", pkType);

        if (pkUsername != null) {
            request.add("pkUsername", pkUsername);
        }
        addQueue(MethodCode.EVENT_GETBOOKAUDIODATA, request);
        startQueue();
    }

    @Override
    public void uploadAudioResource(int resourseId, int page, int audioType, int audioSource, int lineId, String path, float score, String title, int duration, int origin) {
        File file = new File(path);
        FileBinary fileBinary = new FileBinary(file);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.UPLOADAUDIO, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("resourseId", resourseId);
        request.add("audioType", 0);
        request.add("audioSource", audioSource);
        request.add("page", page);
        request.add("lineId", lineId);
        request.add("file", fileBinary);
        request.add("score", score);
        request.add("title", title);
        request.add("duration", duration);
        request.add("origin", origin);
        addQueue(MethodCode.EVENT_UPLOADAUDIO, request);
        startQueue();
    }

    @Override
    public void getPkUsers(int bookId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETPKUSERS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("bookId", bookId);
        addQueue(MethodCode.EVENT_GETPKUSERS, request);
        startQueue();
    }

    @Override
    public void getPkResult(int bookId, String pkUsername, int pkType) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETPKRESULT, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("bookId", bookId);
        request.add("pkUsername", pkUsername);
        request.add("pkType", pkType);
        addQueue(MethodCode.EVENT_GETPKRESULT, request);
        startQueue();
    }

    @Override
    public void joinMaterial(int bookId, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.JOINMATERIAL, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("bookId", bookId);
        addQueue(MethodCode.EVENT_JOINMATERIAL + 4000 + classId, request);
        startQueue();
    }

    @Override
    public void cancelMaterial(int bookId, int classId) {
        this.classId = classId;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.CANCELMATERIAL, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("bookId", bookId);
        addQueue(MethodCode.EVENT_CANCELMATERIAL + 5000 + classId, request);
        startQueue();
    }

    @Override
    public void getRank(int spaceType, int timeType) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETRANK, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("spaceType", spaceType);
        request.add("timeType", timeType);
        addQueue(MethodCode.EVENT_GETRANK, request);
        startQueue();
    }

    @Override
    public void getSquareRank() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SQUAREAPI + MethodType.GETSQUARERANK, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_GETSQUARERANK, request);
        startQueue();
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
        startQueue();
    }

    @Override
    public void likeStudent(String likeUsername) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SQUAREAPI + MethodType.LIKESTUDENT, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("likeUsername", likeUsername);
        addQueue(MethodCode.EVENT_LIKESTUDENT, request);
        startQueue();
    }

    @Override
    public void cancelLikeStudent(String cancelLikeUsername) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SQUAREAPI + MethodType.CANCELLIKESTUDENT, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("cancelLikeUsername", cancelLikeUsername);
        addQueue(MethodCode.EVENT_CANCELLIKESTUDENT, request);
        startQueue();
    }

    @Override
    public void getReading() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.GETREADING, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_GETREADING, request);
        startQueue();
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
    public void commitReading(String[] type, String[] duration) {
        for (int i = 0; i < type.length; i++) {
            if (!duration[i].equals("0")) {
                FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.COMMITREADING, RequestMethod.POST);
                request.add("username", SystemUtils.defaultUsername);
                request.add("token", SystemUtils.token);
                request.add("type", type[i]);
                request.add("duration", Integer.parseInt(duration[i]) * 60 + "");
                addQueue(MethodCode.EVENT_COMMITREADING, request);
            }
        }
        startQueue();
    }

    @Override
    public void getDurationStatistics(int timeType, int locationType) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SQUAREAPI + MethodType.GETDURATIONSTATISICS, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("timeType", timeType);
        request.add("locationType", locationType);
        addQueue(MethodCode.EVENT_GETDURATIONSTATISTICS, request);
        startQueue();
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
            } else if (what == MethodCode.EVENT_GETMUSICLIST + 1000 + classId) {
                List<Song> songList = JSON.parseArray(data, Song.class);
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
                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                List<Banner> lists = JSON.parseArray(dataobj.getString("bannerList"), Banner.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_GETDURATIONSTATISTICS) {
                DurationStatis durationStatis = JSON.parseObject(data, DurationStatis.class);
                listener.Success(what, durationStatis);
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        String error = response.getException().getMessage();
        listener.Error(what, error);
    }
}
