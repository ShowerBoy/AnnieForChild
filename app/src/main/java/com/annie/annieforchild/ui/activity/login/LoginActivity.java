package com.annie.annieforchild.ui.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UpdateBean;
import com.annie.annieforchild.bean.login.LoginBean;
import com.annie.annieforchild.bean.login.MainBean;
import com.annie.annieforchild.bean.login.PhoneSN;
import com.annie.annieforchild.bean.login.SigninBean;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.presenter.imp.LoginPresenterImp;
import com.annie.annieforchild.ui.activity.MainActivity;
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
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * 登陆界面
 * Created by WangLei on 2018/1/22 0022
 */

public class LoginActivity extends BaseActivity implements LoginView, OnCheckDoubleClick {
    private EditText phoneNumber, password;
    private TextView register, youke, forgetPsd;
    private Button loginBtn;
    private TelephonyManager tm;
    private LoginPresenter presenter;
    private SQLiteDatabase db;
    private Intent intent;
    private String tag;
    private AlertHelper helper;
    private Dialog dialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Calendar calendar;
    String logintime, phone, psd;
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        register = findViewById(R.id.register);
        youke = findViewById(R.id.youke);
        loginBtn = findViewById(R.id.login_btn);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        forgetPsd = findViewById(R.id.forget_psd);
        listener = new CheckDoubleClickListener(this);
        register.setOnClickListener(listener);
        youke.setOnClickListener(listener);
        loginBtn.setOnClickListener(listener);
        forgetPsd.setOnClickListener(listener);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();

        intent = getIntent();
        if (intent != null) {
            tag = intent.getStringExtra("tag");
        }
        if (tag != null && tag.equals("游客登陆")) {
            youke.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        calendar = Calendar.getInstance();
        presenter = new LoginPresenterImp(this, this);
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        presenter.initViewAndData();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            MPermissions.requestPermissions(this, 0, new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        } else {
            doit();
        }
    }

    @SuppressLint("MissingPermission")
    private void doit() {
//        LitePal.deleteDatabase("annie");
        db = LitePal.getDatabase();
//        DataSupport.deleteAll(SigninBean.class);
        List<PhoneSN> list = DataSupport.findAll(PhoneSN.class);
        if (list != null && list.size() != 0) {
            SystemUtils.phoneSN = list.get(list.size() - 1);
            SystemUtils.sn = list.get(list.size() - 1).getSn();
        } else {
            if (tm.getSimSerialNumber() != null) {
                SystemUtils.sn = tm.getSimSerialNumber();
            } else {
                SystemUtils.sn = UUID.randomUUID().toString();
            }
            SystemUtils.phoneSN = new PhoneSN();
            SystemUtils.phoneSN.setSn(SystemUtils.sn);
            SystemUtils.phoneSN.save();
        }

        List<MainBean> lists = DataSupport.findAll(MainBean.class);
        if (lists != null && lists.size() != 0) {
            SystemUtils.mainBean = lists.get(lists.size() - 1);
//            showInfo("数据库里存在mainBean:" + SystemUtils.mainBean.toString());
        } else {
//            presenter.getMainAddress();
        }
        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = preferences.edit();

        if (preferences.getString("phone", null) != null && preferences.getString("psd", null) != null) {
            phone = preferences.getString("phone", null);
            psd = preferences.getString("psd", null);
            logintime = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + 1 + "" + calendar.get(Calendar.DATE) + "" + calendar.get(Calendar.HOUR) + "" + calendar.get(Calendar.MINUTE) + "" + calendar.get(Calendar.SECOND);
            SystemUtils.getNetTime();
            presenter.login(phone, psd, logintime);
        }
    }

    /**
     * {@link LoginPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onEventMainThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MAIN) {
//            showInfo("EventBus:" + message.getObj().toString());
        } else if (message.what == MethodCode.EVENT_LOGIN) {
//            showInfo("登陆成功:" + SystemUtils.token);
            doit();
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
            if (bean.getDefaultUsername().equals("")) {
                SystemUtils.childTag = 0;
            } else {
                SystemUtils.childTag = 1;
            }
            startActivity(intent);
            if (tag != null && tag.equals("游客登陆")) {
                ActivityCollector.finishAll();
            }
            finish();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private boolean check() {
        if (phoneNumber.getText().toString().equals("") && phoneNumber.getText().toString().contains(" ") || password.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(0)
    public void requsetSuccess() {
        doit();
    }

    @PermissionDenied(0)
    public void requestDenied() {
        Toast.makeText(this, "缺少权限!", Toast.LENGTH_SHORT).show();
    }

    @PermissionGrant(1)
    public void requestLoginSuccess() {
        logintime = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + 1 + "" + calendar.get(Calendar.DATE) + "" + calendar.get(Calendar.HOUR) + "" + calendar.get(Calendar.MINUTE) + "" + calendar.get(Calendar.SECOND);
        phone = phoneNumber.getText().toString();
        psd = password.getText().toString();
        presenter.login(phone, psd, logintime);
    }

    @PermissionDenied(1)
    public void requestLoginDenied() {
        Toast.makeText(this, "缺少权限!", Toast.LENGTH_SHORT).show();
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
    protected void onResume() {
        super.onResume();
        phoneNumber.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                Intent intent1 = new Intent(this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.youke:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("tag", "游客");
                SystemUtils.tag = "游客";
                SystemUtils.childTag = 0;
                startActivity(intent);
                finish();
                break;
            case R.id.login_btn:
                if (check()) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                            MPermissions.requestPermissions(this, 1, new String[]{
                                    Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                            });
                        } else {
                            showInfo("无法正常使用安妮花，请开通相关权限！请设置");
                            Intent localIntent = new Intent();
                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (Build.VERSION.SDK_INT >= 9) {
                                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                            } else if (Build.VERSION.SDK_INT <= 8) {
                                localIntent.setAction(Intent.ACTION_VIEW);
                                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                                localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                            }
                            startActivity(localIntent);
                        }
                    } else {
                        logintime = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + 1 + "" + calendar.get(Calendar.DATE) + "" + calendar.get(Calendar.HOUR) + "" + calendar.get(Calendar.MINUTE) + "" + calendar.get(Calendar.SECOND);
                        phone = phoneNumber.getText().toString();
                        psd = password.getText().toString();
                        presenter.login(phone, psd, logintime);
                    }
                } else {
                    showInfo("手机号或密码填写有误");
                }
                break;
            case R.id.forget_psd:
                Intent intent2 = new Intent(this, ModifyPsdActivity.class);
                intent2.putExtra("title", "忘记密码");
                startActivity(intent2);
                break;
        }
    }
}
