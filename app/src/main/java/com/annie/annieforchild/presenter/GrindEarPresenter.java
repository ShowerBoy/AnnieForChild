package com.annie.annieforchild.presenter;

import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public interface GrindEarPresenter {
    void initViewAndData();

    void getListening();

    void getMyListening();

    void collectCourse(int type, int audioSource, int courseId, int classId);

    void cancelCollection(int type, int audioSource, int courseId, int classId);

    void getMusicClasses();

    void getReadingClasses();

    void getMusicList(int classId);

    void commitDuration(String[] type, String[] duration);

    void getBookScore(int bookId);

    void getBookAudioData(int bookId, int pkType, String pkUsername);

    void uploadAudioResource(int resourseId, int page, int audioType, int audioSource, int lineId, String path, float score, String title, int duration, int origin, String pkUsername);

    void getPkUsers(int bookId);

    void getPkResult(int bookId, String pkUsername, int pkType);

    void joinMaterial(int bookId, int classId);

    void cancelMaterial(int bookId, int classId);

    void getRank(int spaceType, int timeType);

    void getSquareRank();

    void getSquareRankList(int resourceType, int timeType, int locationType);

    void likeStudent(String likeUsername);

    void cancelLikeStudent(String cancelLikeUsername);

    void getReading();

    void getMyReading();

    void commitReading(String[] type, String[] duration, int books, int words);

    void getDurationStatistics(int timeType, int locationType);

    void getReadList(int classId);

    void getQrCode();

    void getAnimationList(String title, int classId);

    void getSpokenClasses();

    void getSpokenList(int classId);

    void commitBook(List<String> lists);

    void DailyPunch();
}
