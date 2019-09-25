package com.lebo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by Zippo on 2018/6/8.
 * Date: 2018/6/8
 * Time: 15:40:59
 */
public final class ToastUtil {

    private static final String TAG = "ToastUtil";
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Toast sToast;

    private ToastUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param text The text.
     */
    public static void show(final Context context, @NonNull final CharSequence text) {
        HANDLER.post(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                cancel();
                sToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                showToast(context);
            }
        });
    }

    /**
     * Cancel the sToast.
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
        }
    }

    private static void showToast(Context context) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            try {
                Field field = View.class.getDeclaredField("mContext");
                field.setAccessible(true);
                field.set(sToast.getView(), new ApplicationContextWrapperForApi25(context));
            } catch (Exception e) {
                Logger.w(TAG, e);
            }
        }
        sToast.show();
    }

    private static final class ApplicationContextWrapperForApi25 extends ContextWrapper {

        ApplicationContextWrapperForApi25(Context pContext) {
            super(pContext);
        }

        @Override
        public Context getApplicationContext() {
            return this;
        }

        @Override
        public Object getSystemService(@NonNull String name) {
            if (Context.WINDOW_SERVICE.equals(name)) {
                // noinspection ConstantConditions
                return new WindowManagerWrapper(
                        (WindowManager) getBaseContext().getSystemService(name));
            }
            return super.getSystemService(name);
        }

        private static final class WindowManagerWrapper implements WindowManager {

            private final WindowManager base;

            private WindowManagerWrapper(@NonNull WindowManager base) {
                this.base = base;
            }

            @Override
            public Display getDefaultDisplay() {
                return base.getDefaultDisplay();
            }

            @Override
            public void removeViewImmediate(View view) {
                base.removeViewImmediate(view);
            }

            @Override
            public void addView(View view, ViewGroup.LayoutParams params) {
                try {
                    base.addView(view, params);
                } catch (Exception e) {
                    Logger.w(TAG, e);
                }
            }

            @Override
            public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
                base.updateViewLayout(view, params);
            }

            @Override
            public void removeView(View view) {
                base.removeView(view);
            }
        }
    }

    public static String FormatRunTime(long runTime) {
        if(runTime < 0) return "00:00:00";

        long hour = runTime / 3600;
        long minute = (runTime % 3600) / 60;
        long second = runTime % 60;

        return unitTimeFormat(hour) + ":" + unitTimeFormat(minute) + ":" +
                unitTimeFormat(second);
    }
    /*
     * 将时分秒转为秒数
     * */
    public static long formatTurnSecond(String time) {
        String s = time;
        int index1 = s.indexOf(":");
        int index2 = s.indexOf(":", index1 + 1);
        int hh = Integer.parseInt(s.substring(0, index1));
        int mi = Integer.parseInt(s.substring(index1 + 1, index2));
        int ss = Integer.parseInt(s.substring(index2 + 1));

        return hh * 60 * 60 + mi * 60 + ss;
    }

    private static String unitTimeFormat(long number) {
        return String.format("%02d", number);
    }
}