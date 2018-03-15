package com.annie.baselibrary.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Mark.Han on 2017/8/15.
 */

public class ToastHelp {

    public static void show(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, @StringRes int stringId) {
        Toast.makeText(context, stringId, Toast.LENGTH_LONG).show();
    }

    public static void show(View view, CharSequence msg) {
        show(view.getContext(), msg);
    }

    public static void show(View view, @StringRes int stringId) {
        show(view.getContext(), stringId);
    }
}
