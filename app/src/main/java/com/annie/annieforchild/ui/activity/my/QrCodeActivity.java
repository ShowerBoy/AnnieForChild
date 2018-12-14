package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.FourthPresenterImp;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 二维码
 * Created by wanglei on 2018/6/13.
 */

public class QrCodeActivity extends BaseActivity implements View.OnClickListener, SongView {
    private ImageView back, qrImage;
    private CircleImageView headpic;
    private TextView name, username;
    private GrindEarPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private String qrCodeUrl;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.qr_code_back);
        qrImage = findViewById(R.id.qr_code_image);
        headpic = findViewById(R.id.qr_headpic);
        name = findViewById(R.id.qr_name);
        username = findViewById(R.id.qr_usename);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        init();
        presenter.getQrCode();
    }

    private void init() {
        Glide.with(this).load(SystemUtils.userInfo.getAvatar()).into(headpic);
        name.setText(SystemUtils.userInfo.getName());
        username.setText(SystemUtils.userInfo.getUsername());
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qr_code_back:
                finish();
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETQRCODE) {
            qrCodeUrl = (String) message.obj;
            Glide.with(this).load(qrCodeUrl).into(qrImage);
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
}
