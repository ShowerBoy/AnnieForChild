package com.annie.annieforchild.ui.activity.child;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.ChildPresenter;
import com.annie.annieforchild.presenter.imp.ChildPresenterImp;
import com.annie.annieforchild.ui.activity.CameraActivity;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.view.AddChildView;
import com.annie.baselibrary.base.BasePresenter;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 添加学员
 * Created by WangLei on 2018/1/23 0023
 */

public class AddChildActivity extends CameraActivity implements AddChildView, View.OnClickListener, RadioGroup.OnCheckedChangeListener, OnDateSetListener {
    private CircleImageView childHeadPic;
    private FrameLayout headPic_layout;
    private ImageView addChild_back;
    private TextView pass, bindStudent;
    private EditText childName, childBirth;
    private Button addChild;
    private RadioGroup childSex;
    private RadioButton boy, girl;
    private TimePickerDialog datePickerDialog;
    SimpleDateFormat sf;
    private String sex = null, birth = null, headpic = null;
    private PopupWindow popupWindow;
    private SystemUtils systemUtils;
    private Bitmap headbitmap = null;
    private ChildPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private Intent intent;
    private int tag; //来源标示附符 0：注册 1：个人中心添加
    long tenYears = 30L * 365 * 1000 * 60 * 60 * 24L;
    long oneYears = 5L * 365 * 1000 * 60 * 60 * 24L;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_child;
    }

    @Override
    protected void initView() {
        headPic_layout = findViewById(R.id.headPic_layout);
        childHeadPic = findViewById(R.id.child_headpic);
        childName = findViewById(R.id.name_add_child);
        childBirth = findViewById(R.id.age_add_child);
        bindStudent = findViewById(R.id.bind_student);
        pass = findViewById(R.id.pass);
        childSex = findViewById(R.id.sex_group);
        boy = findViewById(R.id.sex_boy);
        girl = findViewById(R.id.sex_girl);
        addChild = findViewById(R.id.add_child_btn);
        addChild_back = findViewById(R.id.add_child_back);
        childSex.setOnCheckedChangeListener(this);
        childBirth.setOnClickListener(this);
        headPic_layout.setOnClickListener(this);
        pass.setOnClickListener(this);
        addChild.setOnClickListener(this);
        addChild_back.setOnClickListener(this);
        bindStudent.setOnClickListener(this);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        intent = getIntent();
        if (intent.getStringExtra("from").equals("register")) {
            pass.setVisibility(View.VISIBLE);
            tag = 0;
        } else {
            pass.setVisibility(View.GONE);
            tag = 1;
        }
    }

    /**
     * 1980: 318332715660
     */
    @Override
    protected void initData() {
        systemUtils = new SystemUtils(this);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        datePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setThemeColor(R.color.black)
                .setTitleStringId("日期选择")
                .setMinMillseconds(System.currentTimeMillis() - tenYears)
                .setMaxMillseconds(System.currentTimeMillis() + oneYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setWheelItemTextSize(16)
                .setCallBack(this)
                .build();
        popupWindow = new PopupWindow(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            MPermissions.requestPermissions(this, 2, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            });
        }
        presenter = new ChildPresenterImp(this, this);
        presenter.initViewAndData();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pass:
                //跳过
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.age_add_child:
                //学员年龄
                datePickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.headPic_layout:
                //头像
                systemUtils.BuildCameraDialog().show();
                break;
            case R.id.add_child_back:
                finish();
                break;
            case R.id.add_child_btn:
                //添加
                if (isCorrect()) {
                    presenter.addChild(headpic, childName.getText().toString(), sex, birth.replace("-", ""));
                } else {
                    SystemUtils.show(this, "输入有误，请重新输入");
                }
                break;
            case R.id.bind_student:
                //绑定已有学员
//                Intent intent1 = new Intent(this, BindStudentActivity.class);
//                startActivity(intent1);
                break;
        }
    }

    private boolean isCorrect() {
        if (childName.getText().toString().equals("") || childName.getText().toString().contains(" ") || birth == null || sex == null || headpic == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (boy.getId() == i) {
            sex = "男";
        } else {
            sex = "女";
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long l) {
        birth = sf.format(new Date(l));
        childBirth.setText(birth);
    }

    @Override
    protected void onImageSelect(Bitmap bitmap, String path) {
        headbitmap = bitmap;
        presenter.uploadHeadpic(10000, path);
        SystemUtils.show(this, path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(2)
    public void requsetSuccess() {
    }

    @PermissionDenied(2)
    public void requestDenied() {
        Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    /**
     * {@link ChildPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onEventMainThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_ADDCHILD) {
            if (message.obj instanceof String) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } else if (message.what == MethodCode.EVENT_ADDCHILD2) {
            finish();
        } else if (message.what == MethodCode.EVENT_UPLOADAVATAR + 10000) {
            if ((String) message.obj != null) {
                headpic = (String) message.obj;
                childHeadPic.setImageBitmap(headbitmap);
            }
        }
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
    public int getTag() {
        return tag;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
