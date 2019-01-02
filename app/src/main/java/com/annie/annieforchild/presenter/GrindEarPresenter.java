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

    void getBookScore(int bookId, int record);

    void getBookAudioData(int bookId, int pkType, String pkUsername);

    void uploadAudioResource(int resourseId, int page, int audioType, int audioSource, int lineId, String path, float score, String title, int duration, int origin, String pkUsername, String imageUrl, int animationCode, int homeworkid);

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

    void iWantListen(int type);

    void accessBook(int bookId);

    void uploadAudioTime(int origin, int audioType, int audioSource, int resourseId, int duration);

    void myPeriod();

    void suggestPeriod(int periodid);

    void myTask();

    void taskDetails(int taskid);

    void completeTask(int taskid, int likes, int listen, int homeworkid);

    void uploadTaskImage(int taskid, List<String> path);

    void submitTask(int taskid, String remarks);

    void clockinShare(int type, int bookid);

    void getCardDetail();

    void shareSuccess(int moerduo, int yuedu, int kouyu);

    void getMySpeaking();

    void commitSpeaking(String[] type, String[] duration);

    void unlockBook(int nectar, String bookname, int bookid, int classId);

    void getSpeaking();

    void getRelease(int bookId);

    void ReleaseBook(int bookId);

    void releaseSuccess(int bookId);

    void playTimes(int id);

    void addlikes(int id);

    void cancellikes(int id);

    void shareCoin(int record, int type);

    void getRadio(String type, int radioid);

    void getLyric(int bookid);

    void luckDraw(int nectar);
}
