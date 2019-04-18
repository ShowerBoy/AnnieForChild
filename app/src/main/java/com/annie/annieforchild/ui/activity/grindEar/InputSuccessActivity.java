package com.annie.annieforchild.ui.activity.grindEar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by wanglei on 2018/7/10.
 */

public class InputSuccessActivity extends BaseActivity implements OnCheckDoubleClick, PlatformActionListener {
    private Button share, addBook;
    private ImageView close, pengyouquan, weixin, qq, qqzone;
    private ShareUtils shareUtils;
    private PopupWindow popupWindow;
    private View v;
    private String shareUrl;
    private CheckDoubleClickListener listener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_success;
    }

    @Override
    protected void initView() {
        share = findViewById(R.id.book_success_share);
        addBook = findViewById(R.id.book_add_book);
        close = findViewById(R.id.input_close);
        listener = new CheckDoubleClickListener(this);
        share.setOnClickListener(listener);
        addBook.setOnClickListener(listener);
        close.setOnClickListener(listener);
        popupWindow = new PopupWindow(this);
        v = LayoutInflater.from(this).inflate(R.layout.activity_share_popup, null);
        pengyouquan = v.findViewById(R.id.tofriend_pengyouquan);
        weixin = v.findViewById(R.id.tofriend_weixin);
        qq = v.findViewById(R.id.tofriend_qq);
        qqzone = v.findViewById(R.id.tofriend_qqzone);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        popupWindow.setContentView(v);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });
    }

    @Override
    protected void initData() {
        shareUtils = new ShareUtils(this, this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    private void getWindowGray(boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.book_success_share:
                getWindowGray(true);
                popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.book_add_book:
                Intent intent = new Intent(this, CommitBookActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.input_close:
                finish();
                break;
            case R.id.tofriend_pengyouquan:
                shareUtils.shareWechatMoments("参加阅读打卡闯关，Reading is Fun！", "我读了**本绘本，你也一起来 ", null, shareUrl);
                break;
            case R.id.tofriend_weixin:
                shareUtils.shareWechat("参加阅读打卡闯关，Reading is Fun！", "我读了**本绘本，你也一起来 ", null, shareUrl);
                break;
            case R.id.tofriend_qq:
                shareUtils.shareQQ("参加阅读打卡闯关，Reading is Fun！", "我读了**本绘本，你也一起来 ", null, shareUrl);
                break;
            case R.id.tofriend_qqzone:
                shareUtils.shareQZone("参加阅读打卡闯关，Reading is Fun！", "我读了**本绘本，你也一起来 ", null, shareUrl);
                break;
        }
    }
}
