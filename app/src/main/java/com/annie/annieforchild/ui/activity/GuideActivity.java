package com.annie.annieforchild.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UpdateBean;
import com.annie.annieforchild.bean.login.LoginBean;
import com.annie.annieforchild.bean.login.PhoneSN;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.presenter.imp.LoginPresenterImp;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.LoginView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.annie.baselibrary.utils.NetUtils.NoHttpUtils;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.Subscribe;
import org.litepal.LitePal;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * 引导页
 * Created by wanglei on 2018/4/23.
 */

public class GuideActivity extends BaseActivity implements LoginView {
    TimerTask task;
    Timer timer;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String phone, psd, logintime;
    private Calendar calendar;
    private LoginPresenterImp presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private SQLiteDatabase db;
    public String TAG = "GuideActivity";
    private TelephonyManager tm;
    private MyApplication application;

    {
        setRegister(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        NoHttpUtils.init(this);
        application = (MyApplication) getApplicationContext();
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Uri uri = getIntent().getData();
        if (uri != null) {
            application.getSystemUtils().setUri(uri);
            // 完整的url信息
            String url = uri.toString();
            Log.i(TAG, "url:" + uri);


            // scheme部分
            String scheme = uri.getScheme();
            Log.i(TAG, "scheme:" + scheme);

            // host部分
            String host = uri.getHost();
            Log.i(TAG, "host:" + host);

            // port部分
            int port = uri.getPort();
            Log.i(TAG, "port:" + port);

            // 访问路劲
            String path = uri.getPath();
            Log.i(TAG, "path:" + path);

            List<String> pathSegments = uri.getPathSegments();

            // Query部分
            String query = uri.getQuery();
            Log.i(TAG, "query:" + query);

            //获取指定参数值
            String success = uri.getQueryParameter("success");
            Log.i(TAG, "success:" + success);

            String bookid = uri.getQueryParameter("bookid");
            String bookType = uri.getQueryParameter("booktype");
            if (bookid != null) {

            }
//            SystemUtils.show(this, "url:" + url + "=== bookid:" + bookid + "=== booktype:" + bookType);
        } else {
            application.getSystemUtils().setUri(null);
        }
    }

    @Override
    protected void initData() {
        db = LitePal.getDatabase();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new LoginPresenterImp(this, this);
        presenter.initViewAndData();
        calendar = Calendar.getInstance();
        db = LitePal.getDatabase();
        doit();
    }

    private void doit() {
         /*sharepreference设置多进程访问*/
        preferences = getSharedPreferences("userInfo", MODE_PRIVATE | MODE_MULTI_PROCESS);
        editor = preferences.edit();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        signin();
                    }
                });
            }
        };
        timer.schedule(task, 2 * 1000);
    }


    @SuppressLint("MissingPermission")
    private void signin() {
        if (preferences.getString("phone", null) != null && preferences.getString("psd", null) != null) {
            List<PhoneSN> list = LitePal.findAll(PhoneSN.class);
            if (list != null && list.size() != 0) {
                if (list.get(list.size() - 1).getSn() == null) {
                    PhoneSN phoneSN = list.get(list.size() - 1);
                    if (tm.getSimSerialNumber() != null) {
                        phoneSN.setSn(tm.getSimSerialNumber());
                    } else {
                        phoneSN.setSn(UUID.randomUUID().toString());
                    }
                    phoneSN.save();
                }
                application.getSystemUtils().setPhoneSN(list.get(list.size() - 1));
                application.getSystemUtils().setSn(list.get(list.size() - 1).getSn());
            } else {
                if (tm.getSimSerialNumber() != null) {
                    application.getSystemUtils().setSn(tm.getSimSerialNumber());
                } else {
                    application.getSystemUtils().setSn(UUID.randomUUID().toString());
                }
                application.getSystemUtils().setPhoneSN(new PhoneSN());
                application.getSystemUtils().getPhoneSN().setSn(application.getSystemUtils().getSn());
                application.getSystemUtils().getPhoneSN().save();
            }
            phone = preferences.getString("phone", null);
            psd = preferences.getString("psd", null);
            logintime = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + 1 + "" + calendar.get(Calendar.DATE) + "" + calendar.get(Calendar.HOUR) + "" + calendar.get(Calendar.MINUTE) + "" + calendar.get(Calendar.SECOND);
            application.getNetTime();
            presenter.login(phone, psd, logintime);
        } else {
            Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * {@link LoginPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_LOGIN) {
            LoginBean bean = (LoginBean) message.obj;
//            List<PhoneSN> list = DataSupport.findAll(PhoneSN.class);
//            PhoneSN phoneSN = list.get(list.size() - 1);
//            phoneSN.setLastlogintime(logintime);
//            phoneSN.setUsername(bean.getDefaultUsername());
            application.getSystemUtils().getPhoneSN().setUsername(bean.getDefaultUsername());
            application.getSystemUtils().getPhoneSN().setLastlogintime(logintime);
            application.getSystemUtils().getPhoneSN().setSystem("android");
            application.getSystemUtils().getPhoneSN().setBitcode(SystemUtils.getVersionName(this));
            application.getSystemUtils().getPhoneSN().save();
//            SystemUtils.phoneSN.setUsername(bean.getDefaultUsername());
//            SystemUtils.phoneSN.setLastlogintime(logintime);
//            SystemUtils.phoneSN.setSystem("android");
//            SystemUtils.phoneSN.setBitcode(SystemUtils.getVersionName(this));
//            SystemUtils.phoneSN.save();
            /*添加token等本地保存*/
            editor.putString("phone", phone);
            editor.putString("psd", psd);
            editor.putInt("childTag", application.getSystemUtils().getChildTag());
            editor.putString("token", bean.getToken());
            editor.putString("defaultUsername", bean.getDefaultUsername());
            editor.commit();

            application.getSystemUtils().setPhone(phone);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("tag", "会员");
            application.getSystemUtils().setTag("会员");
            startActivity(intent);
            finish();
        } else if (message.what == MethodCode.EVENT_ERROR) {
            editor.putString("phone", null);
            editor.putString("psd", null);
            /**/
            editor.putInt("childTag", 0);
            editor.putString("token", null);
            editor.putString("defaultUsername", null);
            editor.commit();

            Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
        task.cancel();
        task = null;
        if (db != null) {
            db.close();
        }
    }
}
