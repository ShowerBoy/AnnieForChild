package com.annie.annieforchild.ui.activity.my;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 推荐好友
 * Created by WangLei on 2018/1/17 0017
 */

public class ToFriendActivity extends BaseActivity implements ViewInfo, View.OnClickListener, PlatformActionListener {
    private ImageView toFriendBack, pengyouquan, weixin, qq, qqzone;
    private Button share;
    private TextView explain;
    private MessagePresenter presenter;
    private ShareUtils shareUtils;
    private PopupWindow popupWindow;
    private String shareUrl;
    private View v;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_friend;
    }

    @Override
    protected void initView() {
        toFriendBack = findViewById(R.id.to_friend_back);
        explain = findViewById(R.id.explain);
        share = findViewById(R.id.share);
        toFriendBack.setOnClickListener(this);
        share.setOnClickListener(this);
        popupWindow = new PopupWindow(this);
        v = LayoutInflater.from(this).inflate(R.layout.activity_share_popup, null);
        pengyouquan = v.findViewById(R.id.tofriend_pengyouquan);
        weixin = v.findViewById(R.id.tofriend_weixin);
        qq = v.findViewById(R.id.tofriend_qq);
        qqzone = v.findViewById(R.id.tofriend_qqzone);
        pengyouquan.setOnClickListener(this);
        weixin.setOnClickListener(this);
        qq.setOnClickListener(this);
        qqzone.setOnClickListener(this);
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
        explain.setText("规则说明：" + "\n" + "分享“安妮花”给你的朋友，一起来阅读，" + "\n" + "与朋友各得50花蜜。");
        presenter = new MessagePresenterImp(this, this);
        presenter.initViewAndData();
        presenter.shareTo();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.to_friend_back:
                finish();
                break;
            case R.id.tofriend_pengyouquan:
                shareUtils.shareWechatMoments("推荐给你的朋友：中国孩子的英语启蒙路线图", "安妮花教育", null, shareUrl);
                break;
            case R.id.tofriend_weixin:
                shareUtils.shareWechat("推荐给你的朋友：中国孩子的英语启蒙路线图", "安妮花教育", null, shareUrl);
                break;
            case R.id.tofriend_qq:
                shareUtils.shareQQ("推荐给你的朋友：中国孩子的英语启蒙路线图", "安妮花教育", null, shareUrl);
                break;
            case R.id.tofriend_qqzone:
                shareUtils.shareQZone("推荐给你的朋友：中国孩子的英语启蒙路线图", "安妮花教育", null, shareUrl);
                break;
            case R.id.share:
                getWindowGray(true);
                popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showInfo("分享失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        showInfo("取消分享");
    }

    /**
     * {@link MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_SHARETO) {
            shareUrl = (String) message.obj;
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

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
}
