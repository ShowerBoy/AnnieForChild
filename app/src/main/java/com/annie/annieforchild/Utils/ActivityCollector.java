package com.annie.annieforchild.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/4/25.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

//    public static <T extends Activity> boolean isActivityExist(Class<T> clz) {
//        boolean res;
//        Activity activity = getActivity(clz);
//        if (activity == null) {
//            res = false;
//        } else {
//            if (activity.isFinishing() || activity.isDestroyed()) {
//                res = false;
//            } else {
//                res = true;
//            }
//        }
//        return res;
//    }
//
//    public static <T extends Activity> T getActivity(Class<T> clazz) {
//        return (T) activities.get(clazz);
//    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
