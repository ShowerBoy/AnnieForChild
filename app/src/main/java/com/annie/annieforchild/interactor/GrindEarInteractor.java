package com.annie.annieforchild.interactor;

/**
 * Created by wanglei on 2018/3/21.
 */

public interface GrindEarInteractor {
    void getListening();

    void getMyListening();

    void collectCourse(int type, int courseId, int classId);

    void cancelCollection(int type, int courseId, int classId);

    void getMusicClasses();

    void getReadingClasses();

    void getMusicList(int calssId);

    void commitDuration(String[] type, String[] duration);

    void getBookScore(int bookId);

    void getBookAudioData(int bookId, int pkType, String pkUsername);

    void uploadAudioResource(int resourseId, int page, int lineId, String path, float score,String title,int duration);

    void getPkUsers(int bookId);
}
