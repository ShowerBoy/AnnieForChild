package com.annie.annieforchild.interactor;

/**
 * Created by wanglei on 2018/3/17.
 */

public interface ScheduleInteractor {
    void mySchedule(String date);

    void getMaterialClass(int type);

    void addSchedule(int materialId, String startDate, int totalDays, String start, String end);

    void totalSchedule(String startDate, String endDate);

    void editSchedule(int scheduleId, int materialId, String startDate, int totalDays, String start, String end);

    void deleteSchedule(int scheduleId);

    void myCoursesOnline();

    void myCoursesOffline();

    void myTeachingMaterials();
}
