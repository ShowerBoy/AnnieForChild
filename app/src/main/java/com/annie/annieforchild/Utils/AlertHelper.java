package com.annie.annieforchild.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.CountDownDialog;
import com.annie.annieforchild.ui.interfaces.OnCountFinishListener;

/**
 * 提示框工具类
 * Created by WangLei on 2018/3/1 0001
 */

public class AlertHelper {
    Context context;
    Activity activity;

    public AlertHelper(Activity context) {
        this.context = context;
        this.activity = context;
    }

    public ProgressDialog LoadingAlert() {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setTitle("加载中");
        dialog.setMessage("努力加载中，请稍后...");
        dialog.setCancelable(false);
        return dialog;
    }

    public Dialog LoadingDialog() {
        Dialog dialog = new Dialog(activity, R.style.new_circle_progress);
        dialog.setContentView(R.layout.layout_progressbar);
        dialog.setCancelable(false);
        return dialog;
    }

    public Dialog getCountDownDialog(OnCountFinishListener listener) {
        CountDownDialog dialog = new CountDownDialog(activity, R.style.new_circle_progress, listener);
        return dialog;
    }
}
