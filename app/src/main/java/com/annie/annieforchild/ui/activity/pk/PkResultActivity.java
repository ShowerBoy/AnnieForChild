package com.annie.annieforchild.ui.activity.pk;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.PkResult;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * pk结果页
 * Created by wanglei on 2019/2/18.
 */

public class PkResultActivity extends BaseActivity implements SongView, OnCheckDoubleClick, PlatformActionListener {
    private RelativeLayout bgLayout;
    private TextView close, name1, name2, score1, score2, coinCount, shareCancel;
    private ImageView crown1, crown2, backImage, tenCoin, pengyouquan, weixin, qq, qqzone, clarity;
    private Button tryAgain, toExercise, share, oneMore;
    private CircleImageView headpic1, headpic2;
    private CheckDoubleClickListener listener;
    private PkResult pkResult;
    private String pkName, pkImageUrl;
    private Intent intent;
    private Bundle bundle;
    private int bookId, audioType, audioSource, homeworkid, homeworktype, shareType;
    private String imageUrl, title, url;
    private ShareUtils shareUtils;
    private PopupWindow popupWindow;
    private View popupView;
    private GrindEarPresenter presenter;
    private LottieAnimationView coinAnimationView;
    private boolean isShow = true;
    private boolean isGoldGet = true;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pk_result;
    }

    @Override
    protected void initView() {
        close = findViewById(R.id.pk_result_close);
        name1 = findViewById(R.id.pk_result_name1);
        name2 = findViewById(R.id.pk_result_name2);
        score1 = findViewById(R.id.pk_result_score1);
        score2 = findViewById(R.id.pk_result_score2);
        crown1 = findViewById(R.id.pk_crown1);
        crown2 = findViewById(R.id.pk_crown2);
        tryAgain = findViewById(R.id.pk_result_try_again);
        toExercise = findViewById(R.id.pk_result_to_exercise);
        headpic1 = findViewById(R.id.pk_result_headpic1);
        headpic2 = findViewById(R.id.pk_result_headpic2);
        backImage = findViewById(R.id.image_pk_try_again);
        bgLayout = findViewById(R.id.pk_result_bg_layout);
        tenCoin = findViewById(R.id.pk_result_ten_coin);
        share = findViewById(R.id.pk_result_share);
        oneMore = findViewById(R.id.pk_result_one_more);
        clarity = findViewById(R.id.pk_result_clarity);
        coinAnimationView = findViewById(R.id.pk_coin_animation);
        listener = new CheckDoubleClickListener(this);
        close.setOnClickListener(listener);
        tryAgain.setOnClickListener(listener);
        toExercise.setOnClickListener(listener);
        share.setOnClickListener(listener);
        oneMore.setOnClickListener(listener);

        popupWindow = new PopupWindow(this);
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_share_daka_item2, null, false);
        pengyouquan = popupView.findViewById(R.id.share_daka_pengyouquan);
        weixin = popupView.findViewById(R.id.share_daka_weixin);
        qq = popupView.findViewById(R.id.share_daka_qq);
        qqzone = popupView.findViewById(R.id.share_daka_qqzone);
        shareCancel = popupView.findViewById(R.id.daka_share_cancel2);
        coinCount = popupView.findViewById(R.id.coin_count);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        shareCancel.setOnClickListener(listener);
        coinCount.setText("分享+5金币");
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        intent = getIntent();
        bundle = intent.getExtras();
        pkResult = (PkResult) bundle.getSerializable("pkResult");
        pkName = bundle.getString("pkName");
        pkImageUrl = bundle.getString("pkImageUrl");

        bookId = bundle.getInt("bookId");
        imageUrl = bundle.getString("imageUrl");
        title = bundle.getString("title");
        audioType = bundle.getInt("audioType", 0);
        audioSource = bundle.getInt("audioSource", 3);
        homeworkid = bundle.getInt("homeworkid", 0);
        homeworktype = bundle.getInt("homeworktype", -1);
        if (pkResult != null) {
            float a = Float.parseFloat(pkResult.getMyscore()) * 20f;
            float b = Float.parseFloat(pkResult.getPkscore()) * 20f;
            if (pkResult.getResult() == 0) {
                //失败
                bgLayout.setBackgroundColor(getResources().getColor(R.color.pk_lose_bg));
                backImage.setImageResource(R.drawable.pk_pic_try_again);
                close.setTextColor(getResources().getColor(R.color.text_black));
                Glide.with(this).load(application.getSystemUtils().getUserInfo().getAvatar()).error(R.drawable.icon_system_photo).into(headpic1);
                Glide.with(this).load(pkImageUrl).error(R.drawable.icon_system_photo).into(headpic2);
                crown1.setVisibility(View.GONE);
                crown2.setVisibility(View.VISIBLE);
                name1.setTextColor(getResources().getColor(R.color.white));
                name2.setTextColor(getResources().getColor(R.color.white));
                score1.setTextColor(getResources().getColor(R.color.white));
                score2.setTextColor(getResources().getColor(R.color.white));
                name1.setText(application.getSystemUtils().getUserInfo().getName());
                name2.setText(pkName);
                String s1 = a + "";
                String s2 = b + "";
                score1.setText(s1.split("\\.")[0] + "分");
                score2.setText(s2.split("\\.")[0] + "分");

                tryAgain.setVisibility(View.VISIBLE);
                toExercise.setVisibility(View.VISIBLE);
                share.setVisibility(View.GONE);
                oneMore.setVisibility(View.GONE);
                tenCoin.setVisibility(View.GONE);
            } else {
                //成功
                bgLayout.setBackgroundColor(getResources().getColor(R.color.pk_win_bg));
                backImage.setImageResource(R.drawable.pk_pic_bg_win);
                close.setTextColor(getResources().getColor(R.color.white));
                Glide.with(this).load(application.getSystemUtils().getUserInfo().getAvatar()).error(R.drawable.icon_system_photo).into(headpic1);
                Glide.with(this).load(pkImageUrl).error(R.drawable.icon_system_photo).into(headpic2);
                crown1.setVisibility(View.VISIBLE);
                crown2.setVisibility(View.GONE);
                name1.setTextColor(getResources().getColor(R.color.pk_result_color));
                name2.setTextColor(getResources().getColor(R.color.white));
                score1.setTextColor(getResources().getColor(R.color.pk_result_color));
                score2.setTextColor(getResources().getColor(R.color.white));
                name1.setText(application.getSystemUtils().getUserInfo().getName());
                name2.setText(pkName);
                String s1 = a + "";
                String s2 = b + "";
                score1.setText(s1.split("\\.")[0] + "分");
                score2.setText(s2.split("\\.")[0] + "分");

                tryAgain.setVisibility(View.GONE);
                toExercise.setVisibility(View.GONE);
                share.setVisibility(View.VISIBLE);
                oneMore.setVisibility(View.VISIBLE);
                tenCoin.setVisibility(View.VISIBLE);
            }
        }
        shareUtils = new ShareUtils(this, this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isShow) {
            isShow = false;
            if (pkResult != null) {
                if (pkResult.getIsNectar() == 0) {
                    if (pkResult.getResult() == 0) {
                        SystemUtils.setBackGray(PkResultActivity.this, true);
                        SystemUtils.getNectarCongratulation(PkResultActivity.this, pkResult.getNectarCount()).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    } else {
                        SystemUtils.setBackGray(PkResultActivity.this, true);
                        SystemUtils.getNectarCongratulation(PkResultActivity.this, pkResult.getNectarCount()).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else if (pkResult.getIsNectar() == 1) {
                    SystemUtils.show(this, "今日获得花蜜数已达上限");
                }

            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.pk_result_close:
                finish();
                break;
            case R.id.pk_result_try_again:
                finish();
                break;
            case R.id.pk_result_to_exercise:
                Intent intent = new Intent(this, ExerciseActivity2.class);
                intent.putExtra("bookId", bookId);
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("audioType", audioType);
                intent.putExtra("audioSource", audioSource);
                intent.putExtra("title", title);
                intent.putExtra("homeworkid", homeworkid);
                intent.putExtra("homeworktype", homeworktype);
                startActivity(intent);
                finish();
                break;
            case R.id.pk_result_share:
                presenter.clockinShare(5, bookId);
                getWindowGray(true);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                break;
            case R.id.pk_result_one_more:
                finish();
                break;
            case R.id.share_daka_pengyouquan:
                shareType = 0;
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechatMoments("快来听！我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的PK中打败对手赢得胜利！", "安妮花-磨耳朵 流利读 地道说", null, url);
                }
                break;
            case R.id.share_daka_weixin:
                shareType = 1;
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechat("快来听！我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的PK中打败对手赢得胜利！", "安妮花-磨耳朵 流利读 地道说", null, url);
                }
                break;
            case R.id.share_daka_qq:
                shareType = 2;
                if (url != null && url.length() != 0) {
                    shareUtils.shareQQ("快来听！我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的PK中打败对手赢得胜利！", "安妮花-磨耳朵 流利读 地道说", null, url);
                }
                break;
            case R.id.share_daka_qqzone:
                shareType = 3;
                if (url != null && url.length() != 0) {
                    shareUtils.shareQZone("快来听！我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的PK中打败对手赢得胜利！", "安妮花-磨耳朵 流利读 地道说", null, url);
                }
                break;
            case R.id.luckdraw_close:
//                setClarity(false);
//                showLuckDrawLayout(false);
                break;
            case R.id.daka_share_cancel2:
                popupWindow.dismiss();
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_CLOCKINSHARE) {
            ShareBean shareBean = (ShareBean) message.obj;
            url = shareBean.getUrl();
        } else if (message.what == MethodCode.EVENT_SHARECOIN) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            if (shareType == 0) {
                coinAnimationView.setImageAssetsFolder("coin10/");
                coinAnimationView.setAnimation("coin10.json");
//                animationView.loop(false);
//                animationView.playAnimation();
            } else {
                coinAnimationView.setImageAssetsFolder("coin5/");
                coinAnimationView.setAnimation("coin5.json");
//                animationView.loop(false);
//                animationView.playAnimation();
            }
            coinAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    coinAnimationView.setVisibility(View.GONE);
                    setClarity(false);
                    animation.cancel();
                    animation.clone();
//                    animationView.clearColorFilters();
//                    animationView.reverseAnimation();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
//                    animationView.setVisibility(View.GONE);
//                    showLuckDrawLayout(false);
//                    setClarity(false);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            coinAnimationView.loop(false);
            coinAnimationView.playAnimation();
            coinAnimationView.setVisibility(View.VISIBLE);
            SystemUtils.animPool.play(SystemUtils.animMusicMap.get(11), 1, 1, 0, 0, 1);
        }
    }

    private void setClarity(boolean tag) {
        if (tag) {
            clarity.setVisibility(View.VISIBLE);
//            isClick = false;
        } else {
            clarity.setVisibility(View.GONE);
//            isClick = true;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
        if (isGoldGet) {
            isGoldGet = false;
            presenter.shareCoin(2, shareType);
        } else {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
//        popupWindow.dismiss();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showInfo("分享取消");
//        popupWindow.dismiss();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        showInfo("分享取消");
//        popupWindow.dismiss();
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
