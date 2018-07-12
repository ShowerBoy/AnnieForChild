package com.annie.annieforchild.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
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
import com.annie.annieforchild.view.LoginView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.annie.baselibrary.utils.NetUtils.NoHttpUtils;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;

import org.greenrobot.eventbus.Subscribe;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private LoginPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private SQLiteDatabase db;


    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        NoHttpUtils.init(this);
    }

    @Override
    protected void initData() {
        db = LitePal.getDatabase();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new LoginPresenterImp(this, this);
        presenter.initViewAndData();
        calendar = Calendar.getInstance();
        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = preferences.edit();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
//                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        signin();
                    }
                });
//                signin();
            }
        };
        timer.schedule(task, 2 * 1000);
    }

    private void signin() {
        if (preferences.getString("phone", null) != null && preferences.getString("psd", null) != null) {
            List<PhoneSN> list = DataSupport.findAll(PhoneSN.class);
            if (list != null && list.size() != 0) {
                SystemUtils.phoneSN = list.get(list.size() - 1);
                SystemUtils.sn = list.get(list.size() - 1).getSn();
            }
            phone = preferences.getString("phone", null);
            psd = preferences.getString("psd", null);
            logintime = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + 1 + "" + calendar.get(Calendar.DATE) + "" + calendar.get(Calendar.HOUR) + "" + calendar.get(Calendar.MINUTE) + "" + calendar.get(Calendar.SECOND);
            presenter.login(phone, psd, logintime);
            SystemUtils.getNetTime();
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
            SystemUtils.phoneSN.setUsername(bean.getDefaultUsername());
            SystemUtils.phoneSN.setLastlogintime(logintime);
            SystemUtils.phoneSN.setSystem("android");
            SystemUtils.phoneSN.setBitcode(SystemUtils.getVersionName(this));
            SystemUtils.phoneSN.save();

            editor.putString("phone", phone);
            editor.putString("psd", psd);
            editor.commit();

            SystemUtils.phone = phone;

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("tag", "会员");
            SystemUtils.tag = "会员";
            startActivity(intent);
            finish();
        } else if (message.what == MethodCode.EVENT_ERROR) {
            editor.putString("phone", null);
            editor.putString("psd", null);
            editor.commit();

            Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
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
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
        task.cancel();
        task = null;
    }
}
