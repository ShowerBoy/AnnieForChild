package com.annie.annieforchild.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.ClockIn;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.activity.mains.BankBookActivity;
import com.annie.annieforchild.ui.activity.reading.ReadingActivity;
import com.annie.annieforchild.ui.activity.speaking.SpeakingActivity;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by wanglei on 2018/9/6.
 */

public class DakaFragment extends BaseFragment implements SongView, OnCheckDoubleClick, PlatformActionListener {
    private SwipeRefreshLayout refresh_layout;
    private TextView shareCancel, openaccount, accounttotal, totaldays, todayMoerduo, totalMoerduo, todayReading, totalReading, todaySpeaking, totalSpeaking, addMoerduo, addReading, addSpeaking, gotoMoerduo, gotoReading, gotoSpeaking, dakaBtn;
    private ImageView moerduoClockIn, readingClockIn, speakingClockIn, pengyouquan, weixin, qq, qqzone;
    private CheckDoubleClickListener listener;
    private GrindEarPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private ClockIn clockIn;
    private ShareUtils shareUtils;
    private PopupWindow popupWindow;
    private View popupView;
    private String url;
    private int totalHour = 0, totalMin = 0;

    {
        setRegister(true);
    }

    public DakaFragment() {

    }

    @Override
    protected void initData() {
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        if (application.getSystemUtils().getTag().equals("游客") || application.getSystemUtils().getChildTag() == 0) {

        } else {
            presenter.getCardDetail();
        }
        shareUtils = new ShareUtils(getContext(), this);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (application.getSystemUtils().getTag().equals("游客") || application.getSystemUtils().getChildTag() == 0) {
                    refresh_layout.setRefreshing(false);

                } else {
                    presenter.getCardDetail();
                }
            }
        });
    }

    @Override
    protected void initView(View view) {
        refresh_layout = view.findViewById(R.id.daka_refresh_layout);
        openaccount = view.findViewById(R.id.openaccount);
        accounttotal = view.findViewById(R.id.accounttotal);
        totaldays = view.findViewById(R.id.totaldays);
        todayMoerduo = view.findViewById(R.id.today_moerduo);
        totalMoerduo = view.findViewById(R.id.total_moerduo);
        todayReading = view.findViewById(R.id.today_reading);
        totalReading = view.findViewById(R.id.total_reading);
        todaySpeaking = view.findViewById(R.id.today_speaking);
        totalSpeaking = view.findViewById(R.id.total_speaking);
        moerduoClockIn = view.findViewById(R.id.moerduo_yidaka);
        readingClockIn = view.findViewById(R.id.reading_yidaka);
        speakingClockIn = view.findViewById(R.id.speaking_yidaka);
        addMoerduo = view.findViewById(R.id.add_moerduo);
        addReading = view.findViewById(R.id.add_reading);
        addSpeaking = view.findViewById(R.id.add_speaking);
        gotoMoerduo = view.findViewById(R.id.goto_moerduo);
        gotoReading = view.findViewById(R.id.goto_reading);
        gotoSpeaking = view.findViewById(R.id.goto_speaking);
        dakaBtn = view.findViewById(R.id.daka_btn);
        listener = new CheckDoubleClickListener(this);
        addMoerduo.setOnClickListener(listener);
        addReading.setOnClickListener(listener);
        addSpeaking.setOnClickListener(listener);
        gotoMoerduo.setOnClickListener(listener);
        gotoReading.setOnClickListener(listener);
        gotoSpeaking.setOnClickListener(listener);
        dakaBtn.setOnClickListener(listener);

        popupWindow = new PopupWindow(getContext());
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.activity_share_daka_item, null, false);
        pengyouquan = popupView.findViewById(R.id.share_daka_pengyouquan);
        weixin = popupView.findViewById(R.id.share_daka_weixin);
        qq = popupView.findViewById(R.id.share_daka_qq);
        qqzone = popupView.findViewById(R.id.share_daka_qqzone);
        shareCancel = popupView.findViewById(R.id.daka_share_cancel);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        shareCancel.setOnClickListener(listener);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.clarity)));
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
    protected int getLayoutId() {
        return R.layout.activity_daka_fragment;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.add_moerduo:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), BankBookActivity.class);
                startActivity(intent);
                break;
            case R.id.add_reading:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), BankBookActivity.class);
                startActivity(intent);
                break;
            case R.id.add_speaking:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), BankBookActivity.class);
                startActivity(intent);
                break;
            case R.id.goto_moerduo:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), GrindEarActivity.class);
                startActivity(intent);
                break;
            case R.id.goto_reading:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), ReadingActivity.class);
                startActivity(intent);
                break;
            case R.id.goto_speaking:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), SpeakingActivity.class);
                startActivity(intent);
                break;
            case R.id.daka_btn:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                presenter.clockinShare(1, -1);
                getWindowGray(true);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                break;
            case R.id.share_daka_pengyouquan:
                if (clockIn != null && url != null && url.length() != 0) {
                    shareUtils.shareWechatMoments("我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的英语成就，快来看", "坚持打卡，积累成就", null, url);
                }
                break;
            case R.id.share_daka_weixin:
                if (clockIn != null && url != null && url.length() != 0) {
                    shareUtils.shareWechat("我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的英语成就，快来看", "坚持打卡，积累成就", null, url);
                }
                break;
            case R.id.share_daka_qq:
                if (clockIn != null && url != null && url.length() != 0) {
                    shareUtils.shareQQ("我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的英语成就，快来看", "坚持打卡，积累成就", null, url);
                }
                break;
            case R.id.share_daka_qqzone:
                if (clockIn != null && url != null && url.length() != 0) {
                    shareUtils.shareQZone("我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的英语成就，快来看", "坚持打卡，积累成就", null, url);
                }
                break;
            case R.id.daka_share_cancel:
                popupWindow.dismiss();
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_CLOCKINSHARE) {
            ShareBean shareBean = (ShareBean) message.obj;
            url = shareBean.getUrl();
//            showInfo(url);
        } else if (message.what == MethodCode.EVENT_GETCARDDETAIL) {
            if (refresh_layout.isRefreshing()) {
                refresh_layout.setRefreshing(false);
            }
            clockIn = (ClockIn) message.obj;
            if (clockIn != null) {
                refresh();
            }
        } else if (message.what == MethodCode.EVENT_SETDEFAULEUSER) {
            if (presenter != null) {
                presenter.getCardDetail();
            }
        }
    }

    private void refresh() {
        openaccount.setText(clockIn.getOpenaccount() + "");
        accounttotal.setText(clockIn.getClockindays() + "");
        int min = clockIn.getTotaldays() / 60;
        int second = clockIn.getTotaldays() % 60;
        int hour = 0;
//        int hour = min / 60;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        if (hour == 0) {
            totaldays.setText(min + "分钟" + second + "秒");
        } else {
            totaldays.setText(hour + "小时" + min + "分钟");
        }
        totalHour = hour;
        totalMin = min;

        min = clockIn.getMoerduotoday() / 60;
        second = clockIn.getMoerduotoday() % 60;
        hour = 0;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        if (hour == 0) {
            todayMoerduo.setText(min + "分钟" + second + "秒");
        } else {
            todayMoerduo.setText(hour + "小时" + min + "分钟");
        }

        min = clockIn.getReadingtoday() / 60;
        second = clockIn.getReadingtoday() % 60;
        hour = 0;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        if (hour == 0) {
            todayReading.setText(min + "分钟" + second + "秒");
        } else {
            todayReading.setText(hour + "小时" + min + "分钟");
        }

        min = clockIn.getSpeakingtoday() / 60;
        second = clockIn.getSpeakingtoday() % 60;
        hour = 0;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        if (hour == 0) {
            todaySpeaking.setText(min + "分钟" + second + "秒");
        } else {
            todaySpeaking.setText(hour + "小时" + min + "分钟");
        }

        min = clockIn.getMoerduototal() / 60;
        second = clockIn.getMoerduototal() % 60;
        hour = 0;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        if (hour == 0) {
            totalMoerduo.setText("累计时长：" + min + "分钟" + second + "秒");
        } else {
            totalMoerduo.setText("累计时长：" + hour + "小时" + min + "分钟");
        }

        min = clockIn.getReadingtotal() / 60;
        second = clockIn.getReadingtotal() % 60;
        hour = 0;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        if (hour == 0) {
            totalReading.setText("累计时长：" + min + "分钟" + second + "秒");
        } else {
            totalReading.setText("累计时长：" + hour + "小时" + min + "分钟");
        }

        min = clockIn.getSpeakingtotal() / 60;
        second = clockIn.getSpeakingtotal() % 60;
        hour = 0;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        if (hour == 0) {
            totalSpeaking.setText("累计时长：" + min + "分钟" + second + "秒");
        } else {
            totalSpeaking.setText("累计时长：" + hour + "小时" + min + "分钟");
        }

        if (clockIn.getIsmoerduoclockin() == 0) {
            moerduoClockIn.setVisibility(View.GONE);
        } else {
            moerduoClockIn.setVisibility(View.VISIBLE);
        }
        if (clockIn.getIsreadingclockin() == 0) {
            readingClockIn.setVisibility(View.GONE);
        } else {
            readingClockIn.setVisibility(View.VISIBLE);
        }
        if (clockIn.getIsspeakingclockin() == 0) {
            speakingClockIn.setVisibility(View.GONE);
        } else {
            speakingClockIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
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
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
        presenter.shareSuccess(clockIn.getMoerduotoday(), clockIn.getReadingtoday(), clockIn.getSpeakingtoday());
        presenter.getCardDetail();
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

    private void getWindowGray(boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getActivity().getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getActivity().getWindow().setAttributes(layoutParams);
        }
    }
}
