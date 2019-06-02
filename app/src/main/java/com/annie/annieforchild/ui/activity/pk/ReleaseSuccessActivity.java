package com.annie.annieforchild.ui.activity.pk;

import android.animation.Animator;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.bean.ShareCoin;
import com.annie.annieforchild.bean.book.Release;
import com.annie.annieforchild.bean.book.ReleaseBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.BookEndAdapter;
import com.annie.annieforchild.ui.adapter.viewHolder.MeiriyiViewHolder;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 发布成功
 * Created by wanglei on 2018/11/30.
 */

public class ReleaseSuccessActivity extends BaseActivity implements OnCheckDoubleClick, SongView, PlatformActionListener {
    private ImageView back, pengyouquan, weixin, qq, qqzone;
    private LottieAnimationView animationView;
    private ImageView clarityBack;
    private TextView close, shareRecord, shareBook, shareCancel, coinCount;
    private RecyclerView recycler;
    private GrindEarPresenter presenter;
    private CheckDoubleClickListener listener;
    private BookEndAdapter adapter;
    private List<ReleaseBean> lists;
    private Release release;
    private int bookId, audioType;
    private AlertHelper helper;
    private Dialog dialog;
    private ShareUtils shareUtils;
    private PopupWindow popupWindow;
    private View popupView;
    private String url, bookName, imageUrl;
    private int type, record, shareType, isShare; //type  2:系统  4:录音

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_success;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.release_success_back);
        close = findViewById(R.id.release_success_close);
        shareRecord = findViewById(R.id.share_recording);
        shareBook = findViewById(R.id.share_book);
        recycler = findViewById(R.id.release_success_recycler);
        clarityBack = findViewById(R.id.success_clarity_back);
        animationView = findViewById(R.id.success_animation_view);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        close.setOnClickListener(listener);
        shareRecord.setOnClickListener(listener);
        shareBook.setOnClickListener(listener);

        RecyclerLinearLayoutManager layoutManager = new RecyclerLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        recycler.setLayoutManager(layoutManager);

        popupWindow = new PopupWindow(this);
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_share_daka_item2, null, false);
        coinCount = popupView.findViewById(R.id.coin_count);
        pengyouquan = popupView.findViewById(R.id.share_daka_pengyouquan);
        weixin = popupView.findViewById(R.id.share_daka_weixin);
        qq = popupView.findViewById(R.id.share_daka_qq);
        qqzone = popupView.findViewById(R.id.share_daka_qqzone);
        shareCancel = popupView.findViewById(R.id.daka_share_cancel2);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        shareCancel.setOnClickListener(listener);
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
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
        bookId = getIntent().getIntExtra("bookId", 0);
        bookName = getIntent().getStringExtra("bookName");
        imageUrl = getIntent().getStringExtra("imageUrl");
        audioType = getIntent().getIntExtra("audioType", 1);
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        adapter = new BookEndAdapter(this, lists, null, presenter, null, false);
        recycler.setAdapter(adapter);

        presenter.releaseSuccess(bookId);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_RELEASESUCCESS) {
            release = (Release) message.obj;
            lists.clear();
            lists.addAll(release.getRecordList());
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_CLOCKINSHARE) {
            ShareBean shareBean = (ShareBean) message.obj;
            url = shareBean.getUrl();
            isShare = shareBean.getIsshare();
        } else if (message.what == MethodCode.EVENT_SHARECOIN) {
            if (isShare == 1) {
                return;
            }

            ShareCoin shareCoin = (ShareCoin) message.obj;
            if (shareCoin != null) {
                if (shareCoin.getIsGold() == 0) {
                    animationView.setVisibility(View.VISIBLE);
                    clarityBack.setVisibility(View.VISIBLE);
                    if (shareCoin.getGoldCount() == 1) {
                        animationView.setImageAssetsFolder("coin1/");
                        animationView.setAnimation("coin1.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    } else if (shareCoin.getGoldCount() == 2) {
                        animationView.setImageAssetsFolder("coin2/");
                        animationView.setAnimation("coin2.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    } else if (shareCoin.getGoldCount() == 3) {
                        animationView.setImageAssetsFolder("coin3/");
                        animationView.setAnimation("coin3.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    } else if (shareCoin.getGoldCount() == 4) {
                        animationView.setImageAssetsFolder("coin4/");
                        animationView.setAnimation("coin4.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    } else if (shareCoin.getGoldCount() == 5) {
                        animationView.setImageAssetsFolder("coin5/");
                        animationView.setAnimation("coin5.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    } else if (shareCoin.getGoldCount() == 6) {
                        animationView.setImageAssetsFolder("coin6/");
                        animationView.setAnimation("coin6.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    } else if (shareCoin.getGoldCount() == 7) {
                        animationView.setImageAssetsFolder("coin7/");
                        animationView.setAnimation("coin7.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    } else if (shareCoin.getGoldCount() == 8) {
                        animationView.setImageAssetsFolder("coin8/");
                        animationView.setAnimation("coin8.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    } else if (shareCoin.getGoldCount() == 9) {
                        animationView.setImageAssetsFolder("coin9/");
                        animationView.setAnimation("coin9.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    } else {
                        animationView.setImageAssetsFolder("coin10/");
                        animationView.setAnimation("coin10.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                    }
                    SystemUtils.animPool.play(SystemUtils.animMusicMap.get(11), 1, 1, 0, 0, 1);
                    animationView.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationView.setVisibility(View.GONE);
                            clarityBack.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                } else {
                    showInfo("今日获得金币已达上限");
                }
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
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.release_success_back:
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_PRACTICE;
                message.obj = "";
                EventBus.getDefault().post(message);
                finish();
                break;
            case R.id.release_success_close:
                JTMessage message1 = new JTMessage();
                message1.what = MethodCode.EVENT_PRACTICE;
                message1.obj = "";
                EventBus.getDefault().post(message1);
                finish();
                break;
            case R.id.share_recording:
                record = 0;
                type = 4;
                coinCount.setText("分享+5金币");
                presenter.clockinShare(type, bookId);
                getWindowGray(true);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                break;
            case R.id.share_book:
                record = 1;
                type = 2;
                coinCount.setText("分享+2金币");
                presenter.clockinShare(type, bookId);
                getWindowGray(true);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                break;
            case R.id.share_daka_pengyouquan:
                shareType = 0;
                if (type == 2) {
                    //系统
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareWechatMoments("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + bookName + "》", "安妮花数字图书馆喊你来读书啦", imageUrl, url);
                        } else {
                            shareUtils.shareWechatMoments("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", imageUrl, url);
                        }
                    }
                } else {
                    //录音
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareWechatMoments("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花流利读《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        } else {
                            shareUtils.shareWechatMoments("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花地道说《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        }
                    }
                }
                break;
            case R.id.share_daka_weixin:
                shareType = 1;
                if (type == 2) {
                    //系统
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareWechat("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + bookName + "》", "安妮花数字图书馆喊你来读书啦", imageUrl, url);
                        } else {
                            shareUtils.shareWechat("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", imageUrl, url);
                        }
                    }
                } else {
                    //录音
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareWechat("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花流利读《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        } else {
                            shareUtils.shareWechat("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花地道说《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        }
                    }
                }
                break;
            case R.id.share_daka_qq:
                shareType = 2;
                if (type == 2) {
                    //系统
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareQQ("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + bookName + "》", "安妮花数字图书馆喊你来读书啦", imageUrl, url);
                        } else {
                            shareUtils.shareQQ("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", imageUrl, url);
                        }
                    }
                } else {
                    //录音
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareQQ("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花流利读《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        } else {
                            shareUtils.shareQQ("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花地道说《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        }
                    }
                }
                break;
            case R.id.share_daka_qqzone:
                shareType = 3;
                if (type == 2) {
                    //系统
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareQZone("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + bookName + "》", "安妮花数字图书馆喊你来读书啦", imageUrl, url);
                        } else {
                            shareUtils.shareQZone("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", imageUrl, url);
                        }
                    }
                } else {
                    //录音
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareQZone("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花流利读《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        } else {
                            shareUtils.shareQZone("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花地道说《" + bookName + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        }
                    }
                }
                break;
            case R.id.daka_share_cancel2:
                popupWindow.dismiss();
                break;
        }

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
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
        presenter.shareCoin(record, shareType);
        popupWindow.dismiss();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showInfo("分享取消");
        popupWindow.dismiss();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        showInfo("分享取消");
        popupWindow.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.stopMedia();
        }
    }
}
