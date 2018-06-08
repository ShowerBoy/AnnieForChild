package com.annie.annieforchild.ui.activity.child;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.presenter.ChildPresenter;
import com.annie.annieforchild.presenter.imp.ChildPresenterImp;
import com.annie.annieforchild.ui.activity.CameraActivity;
import com.annie.annieforchild.view.AddChildView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 修改孩子信息
 * Created by WangLei on 2018/3/6 0006
 */

public class ModifyChildActivity extends CameraActivity implements AddChildView, View.OnClickListener, OnDateSetListener {
    private ImageView modifyChildBack;
    private CircleImageView modify_headpic;
    private RelativeLayout detailHeadpicLayout, detailNameLayout, detailSexLayout, detailBirthdayLayout, detailQrcodeLayout;
    private TextView detailName, detailSex, detailBirthday, edit;
    private EditText editText;
    private String[] strings;
    private String childSex, birth, childName, avatar, today;
    private UserInfo userInfo;
    private SystemUtils systemUtils;
    private Bitmap headbitmap;
    private TimePickerDialog datePickerDialog;
    private ChildPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    SimpleDateFormat sf;
    int index = 0;
    private boolean isEdit = false;

    long tenYears = 30L * 365 * 1000 * 60 * 60 * 24L;
    long oneYears = 5L * 365 * 1000 * 60 * 60 * 24L;


    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_child;
    }

    @Override
    protected void initView() {
        modifyChildBack = findViewById(R.id.modify_child_back);
        modify_headpic = findViewById(R.id.detail_headpic);
        detailBirthday = findViewById(R.id.detail_birthday);
        detailHeadpicLayout = findViewById(R.id.detail_headpic_layout);
        detailNameLayout = findViewById(R.id.detail_name_layout);
        detailSexLayout = findViewById(R.id.detail_sex_layout);
        detailBirthdayLayout = findViewById(R.id.detail_birthday_layout);
        detailQrcodeLayout = findViewById(R.id.detail_qrcode_layout);
        detailName = findViewById(R.id.detail_name);
        detailSex = findViewById(R.id.detail_sex);
        edit = findViewById(R.id.edit);
        detailHeadpicLayout.setOnClickListener(this);
        detailNameLayout.setOnClickListener(this);
        detailSexLayout.setOnClickListener(this);
        detailBirthdayLayout.setOnClickListener(this);
        detailQrcodeLayout.setOnClickListener(this);
        modifyChildBack.setOnClickListener(this);
        edit.setOnClickListener(this);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
    }

    @Override
    protected void initData() {
        presenter = new ChildPresenterImp(this, this);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        today = sf.format(new Date());
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
        systemUtils = new SystemUtils(this);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            userInfo = (UserInfo) bundle.getSerializable("userinfo");
        }
        initialize();
        presenter.initViewAndData();
    }

    private void initialize() {
        if (userInfo != null) {
            childName = userInfo.getName();
            childSex = userInfo.getSex();
            avatar = userInfo.getAvatar();
            birth = userInfo.getBirthday();

            if (userInfo.getSex().equals("男")) {
                index = 0;
                detailSex.setText(childSex);
            } else {
                index = 1;
                detailSex.setText(childSex);
            }
            detailName.setText(userInfo.getName());
            Glide.with(this).load(userInfo.getAvatar()).into(modify_headpic);
            if (userInfo.getBirthday() != null) {
                if (!userInfo.getBirthday().equals("") && userInfo.getBirthday().length() != 0) {
                    detailBirthday.setText(userInfo.getBirthday().substring(0, 4) + "-" + userInfo.getBirthday().substring(4, 6) + "-" + userInfo.getBirthday().substring(6, 8));
                }
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modify_child_back:
                finish();
                break;
            case R.id.detail_headpic_layout:
                if (isEdit) {
                    systemUtils.BuildCameraDialog().show();
                }
                break;
            case R.id.detail_name_layout:
                if (isEdit) {
                    editText = new EditText(this);
                    editText.setText(userInfo.getName());
                    SystemUtils.GeneralDialog(this, "修改姓名")
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setView(editText)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    childName = editText.getText().toString();
                                    detailName.setText(childName);
//                                presenter.motifyChild(userInfo.getAvatar(), childName, userInfo.getSex(), userInfo.getBirthday());
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
                break;
            case R.id.detail_sex_layout:
                if (isEdit) {
                    strings = new String[]{"男", "女"};
                    SystemUtils.GeneralDialog(this, "修改性别")
                            .setSingleChoiceItems(strings, index, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0) {
                                        childSex = strings[i];
                                    } else {
                                        childSex = strings[i];
                                    }
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                        presenter.motifyChild(userInfo.getAvatar(), childSex, userInfo.getSex(), userInfo.getBirthday());
                            detailSex.setText(childSex);
                            dialogInterface.dismiss();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                }
                break;
            case R.id.detail_birthday_layout:
                if (isEdit) {
                    datePickerDialog.show(getSupportFragmentManager(), "year_month_day");
                }
                break;
            case R.id.detail_qrcode_layout:

                break;
            case R.id.edit:
                if (isEdit) {
                    int todayDate = Integer.parseInt(today.replace("-", ""));
                    int birthDate = Integer.parseInt(birth.replace("-", ""));
                    if (birthDate > todayDate) {
                        showInfo("日期选择有误，请重新选择");
                        return;
                    }
                    isEdit = false;
                    edit.setText("编辑");
                    presenter.motifyChild(avatar, childName, childSex, birth);
                } else {
                    isEdit = true;
                    edit.setText("完成");
                }
                break;
        }
    }

    /**
     * {@link ChildPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_UPDATEUSER) {
            if (message.obj instanceof String) {
                if (message.obj.equals("修改成功")) {
                    refresh();
                } else {
                    initialize();

                }
            }
        } else if (message.what == MethodCode.EVENT_UPLOADAVATAR + 10001) {
            avatar = (String) message.obj;
//            userInfo.setAvatar(avatar);
            Glide.with(this).load(avatar).into(modify_headpic);
//            presenter.motifyChild(userInfo.getAvatar(), childName, userInfo.getSex(), userInfo.getBirthday());
        }
    }

    private void refresh() {
        userInfo.setName(childName);
        userInfo.setSex(childSex);
        userInfo.setBirthday(birth);
        userInfo.setAvatar(avatar);
        initialize();
    }

    @Override
    protected void onImageSelect(Bitmap bitmap, String path) {
        headbitmap = bitmap;
        presenter.uploadHeadpic(10001, path);
        SystemUtils.show(this, path);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long l) {
        birth = sf.format(new Date(l)).replace("-", "");

        int todayDate = Integer.parseInt(today.replace("-", ""));
        int birthDate = Integer.parseInt(birth.replace("-", ""));
        if (birthDate > todayDate) {
            showInfo("日期选择有误，请重新选择");
            birth = userInfo.getBirthday();
        } else {
            detailBirthday.setText(sf.format(new Date(l)));
        }

//        presenter.motifyChild(userInfo.getAvatar(), userInfo.getName(), userInfo.getSex(), birth);
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
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
