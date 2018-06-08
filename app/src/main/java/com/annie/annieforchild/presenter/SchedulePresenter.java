package com.annie.annieforchild.presenter;

/**
 * 课表
 * Created by WangLei on 2018/2/28 0028
 */

public interface SchedulePresenter {
    void initViewAndData();

    void getScheduleDetails(String date);

    void getMaterialClass(int type);

    void addSchedule(int materialId, String startDate, int totalDays, String start, String end);

    void totalSchedule(String startDate, String endDate);

    void editSchedule(int scheduleId, int materialId, String startDate, int totalDays, String start, String end);

    void deleteSchedule(int scheduleId);

    void myCoursesOnline();

    void myCoursesOffline();

    void myTeachingMaterials();
}
