package com.annie.annieforchild.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.presenter.imp.FourthPresenterImp;
import com.annie.annieforchild.ui.activity.child.ModifyChildActivity;
import com.annie.annieforchild.ui.activity.my.AboutActivity;
import com.annie.annieforchild.ui.activity.my.GradeAchievementActivity;
import com.annie.annieforchild.ui.activity.my.HelpActivity;
import com.annie.annieforchild.ui.activity.my.MyCollectionActivity;
import com.annie.annieforchild.ui.activity.my.MyExchangeActivity;
import com.annie.annieforchild.ui.activity.my.MyMessageActivity;
import com.annie.annieforchild.ui.activity.my.MyNectarActivity;
import com.annie.annieforchild.ui.activity.my.MyPeriodActivity;
import com.annie.annieforchild.ui.activity.my.MyRecordActivity;
import com.annie.annieforchild.ui.activity.my.SettingsActivity;
import com.annie.annieforchild.ui.activity.my.ToFriendActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.view.FourthView;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 我的
 * Created by WangLei on 2018/1/12 0012
 */

public class FourthFragment extends BaseFragment implements FourthView, OnCheckDoubleClick {
    private RelativeLayout myMsgLayout, toFriendLayout, myExchangeLayout, helpLayout, aboutLayout, collectionLayout, periodLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout nectarLayout, levelLayout, recordLayout;
    private ImageView settings, sexIcon, headpic_back;
    private CircleImageView userHeadpic;
    private TextView userId, userName, userOld, nectarNum, coinNum, recordNum;
    private RecyclerView member_layout;
    private FourthPresenter presenter;
    private UserInfo userInfo;
    private AlertHelper helper;
    private Dialog dialog;
    private String tag; //身份标示
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    public FourthFragment() {

    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }
        presenter = new FourthPresenterImp(getContext(), this, tag);
        presenter.initViewAndData();
        if (tag.equals("游客")) {

        } else {
            presenter.getUserInfo();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (tag.equals("游客")) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    presenter.getUserInfo();
                }
            }
        });
    }

    @Override
    protected void initView(View view) {
        userHeadpic = view.findViewById(R.id.user_headpic);
        settings = view.findViewById(R.id.settings);
        sexIcon = view.findViewById(R.id.sex_icon);
        userId = view.findViewById(R.id.user_id);
        userName = view.findViewById(R.id.user_name);
        userOld = view.findViewById(R.id.user_old);
        nectarNum = view.findViewById(R.id.nectar_num);
        coinNum = view.findViewById(R.id.coin_num);
        recordNum = view.findViewById(R.id.record_num);
        myMsgLayout = view.findViewById(R.id.my_msg_layout);
        toFriendLayout = view.findViewById(R.id.to_friend_layout);
        myExchangeLayout = view.findViewById(R.id.my_exchange_layout);
        helpLayout = view.findViewById(R.id.help_layout);
        aboutLayout = view.findViewById(R.id.about_layout);
        collectionLayout = view.findViewById(R.id.collection_layout);
        member_layout = view.findViewById(R.id.member_layout);
        nectarLayout = view.findViewById(R.id.nectar_layout);
        levelLayout = view.findViewById(R.id.user_level_layout);
        recordLayout = view.findViewById(R.id.record_layout);
        headpic_back = view.findViewById(R.id.headpic_back);
        swipeRefreshLayout = view.findViewById(R.id.fourth_swipeRefresh);
        periodLayout = view.findViewById(R.id.my_period_layout);
        listener = new CheckDoubleClickListener(this);
        myMsgLayout.setOnClickListener(listener);
        toFriendLayout.setOnClickListener(listener);
        myExchangeLayout.setOnClickListener(listener);
        helpLayout.setOnClickListener(listener);
        aboutLayout.setOnClickListener(listener);
        userHeadpic.setOnClickListener(listener);
        settings.setOnClickListener(listener);
        collectionLayout.setOnClickListener(listener);
        nectarLayout.setOnClickListener(listener);
        recordLayout.setOnClickListener(listener);
        levelLayout.setOnClickListener(listener);
        periodLayout.setOnClickListener(listener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        member_layout.setLayoutManager(manager);
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fourth_fragment;
    }

    /**
     * {@link FourthPresenterImp#Success(int, Object)}
     * {@link com.annie.annieforchild.presenter.imp.ChildPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_USERINFO) {
            userInfo = (UserInfo) message.obj;
            refresh(userInfo);
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        } else if (message.what == MethodCode.EVENT_ADDCHILD2) {
            if (SystemUtils.childTag == 1) {
                presenter.getUserInfo();
            }
        } else if (message.what == MethodCode.EVENT_UPDATEUSER) {
            presenter.getUserInfo();
        } else if (message.what == MethodCode.EVENT_DELETEUSERNAME) {
            presenter.getUserInfo();
        } else if (message.what == MethodCode.EVENT_EXCHANGEGOLD) {
            presenter.getUserInfo();
        } else if (message.what == MethodCode.EVENT_COMMITDURATION) {
            presenter.getUserInfo();
        } else if (message.what == MethodCode.EVENT_COMMITREADING) {
            presenter.getUserInfo();
        }
//        else if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
//            presenter.getUserInfo();
//        }
    }

    public void refresh(UserInfo userInfo) {
        if (SystemUtils.childTag == 0) {
            userHeadpic.setImageResource(R.drawable.photo);
            userName.setText("请添加学员");
        } else {
            userId.setText(userInfo.getUsername());
            Glide.with(this).load(userInfo.getAvatar()).error(R.drawable.icon_system_photo).into(userHeadpic);
//            Glide.with(this).load(userInfo.getAvatar()).bitmapTransform(new BlurTransformation(getContext(), 5)).into(headpic_back);
            userName.setText(userInfo.getName());
            if (userInfo.getBirthday() != null) {
                if (!userInfo.getBirthday().equals("") && userInfo.getBirthday().length() != 0) {
                    userOld.setText(getOld(userInfo.getBirthday()) + "岁");
                }
            }
            String sex = userInfo.getSex();
            if ((sex != null ? sex : "").equals("男")) {
                sexIcon.setImageResource(R.drawable.icon_sex_boy);
            } else {
                sexIcon.setImageResource(R.drawable.icon_sex_girl);
            }
            nectarNum.setText(userInfo.getNectar() != null ? userInfo.getNectar() : "0");
            coinNum.setText(userInfo.getGold() != null ? userInfo.getGold() : "0");
            recordNum.setText(userInfo.getRecorderCount() != null ? userInfo.getRecorderCount() : "0");
            //
            levelLayout.removeAllViews();
            int experience = Integer.parseInt(userInfo.getExperience() != null ? userInfo.getExperience() : "0");

//        experience = 3300;

            if (experience < 500) {
                getLevel(0, 0, 0, 0);
            } else if (experience < 1200) {
                getLevel(0, 0, 0, 1);
            } else if (experience < 2100) {
                getLevel(0, 0, 0, 2);
            } else if (experience < 3200) {
                getLevel(0, 0, 0, 3);
            } else if (experience < 4700) {
                getLevel(0, 0, 1, 0);
            } else if (experience < 6600) {
                getLevel(0, 0, 1, 1);
            } else if (experience < 8900) {
                getLevel(0, 0, 1, 2);
            } else if (experience < 11600) {
                getLevel(0, 0, 1, 3);
            } else if (experience < 15100) {
                getLevel(0, 0, 2, 0);
            } else if (experience < 19400) {
                getLevel(0, 0, 2, 1);
            } else if (experience < 24500) {
                getLevel(0, 0, 2, 2);
            } else if (experience < 30400) {
                getLevel(0, 0, 2, 3);
            } else if (experience < 37900) {
                getLevel(0, 0, 3, 0);
            } else if (experience < 47000) {
                getLevel(0, 0, 3, 1);
            } else if (experience < 57700) {
                getLevel(0, 0, 3, 2);
            } else if (experience < 70000) {
                getLevel(0, 0, 3, 3);
            } else if (experience < 83900) {
                getLevel(0, 1, 0, 0);
            } else if (experience < 99400) {
                getLevel(0, 1, 0, 1);
            } else if (experience < 116500) {
                getLevel(0, 1, 0, 2);
            } else if (experience < 135200) {
                getLevel(0, 1, 0, 3);
            } else if (experience < 155500) {
                getLevel(0, 1, 1, 0);
            } else if (experience < 177400) {
                getLevel(0, 1, 1, 1);
            } else if (experience < 200900) {
                getLevel(0, 1, 1, 2);
            } else if (experience < 226000) {
                getLevel(0, 1, 1, 3);
            } else if (experience < 252700) {
                getLevel(0, 1, 2, 0);
            } else if (experience < 281000) {
                getLevel(0, 1, 2, 1);
            } else if (experience < 310900) {
                getLevel(0, 1, 2, 2);
            } else if (experience < 342400) {
                getLevel(0, 1, 2, 3);
            } else if (experience >= 342400) {
                getLevel(1, 0, 0, 0);
            }
        }
    }

    private void getLevel(int crown, int sun, int moon, int star) {
        if (crown != 0) {
            for (int i = 0; i < crown; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.icon_level_crown);
                levelLayout.addView(imageView);
            }
        }
        if (sun != 0) {
            for (int i = 0; i < sun; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.icon_level_sun);
                levelLayout.addView(imageView);
            }
        }
        if (moon != 0) {
            for (int i = 0; i < moon; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.icon_level_moon);
                levelLayout.addView(imageView);
            }
        }
        if (star != 0) {
            for (int i = 0; i < star; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.icon_level_star);
                levelLayout.addView(imageView);
            }
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

    private int getOld(String birthday) {
        int year = Integer.parseInt(birthday.substring(0, 4));
        int month = Integer.parseInt(birthday.substring(4, 6));
        int day = Integer.parseInt(birthday.substring(6, 8));
        Calendar calendar = Calendar.getInstance();
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH) + 1;
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        int old = year2 - year;
        if (month > month2) {
            return old - 1;
        } else if (month < month2) {
            return old;
        } else {
            if (day > day2) {
                return old - 1;
            } else {
                return old;
            }
        }
    }

    @Override
    public RecyclerView getMemberRecycler() {
        return member_layout;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.user_headpic:
                //头像
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
//                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), ModifyChildActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userinfo", userInfo);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.settings:
                //设置
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                intent.setClass(getContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.my_period_layout:
                //课时核对
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), MyPeriodActivity.class);
                startActivity(intent);
                break;
            case R.id.my_msg_layout:
                //我的信息
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), MyMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.to_friend_layout:
                //推荐好友
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), ToFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.my_exchange_layout:
                //我的兑换
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), MyExchangeActivity.class);
                startActivity(intent);
                break;
            case R.id.collection_layout:
                //我的收藏
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), MyCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.help_layout:
                //帮助与反馈
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.about_layout:
                //关于
                intent.setClass(getContext(), AboutActivity.class);
                startActivity(intent);
//                intent.setClass(getContext(), WebActivity.class);
//                intent.putExtra("url", "http://m.anniekids.org/");
//                intent.putExtra("title", "关于");
//                startActivity(intent);
                break;
            case R.id.user_level_layout:
                //等级成就
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), GradeAchievementActivity.class);
                intent.putExtra("userinfo", userInfo);
                startActivity(intent);
                break;
            case R.id.nectar_layout:
                //我的花蜜
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), MyNectarActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("userinfo", userInfo);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;
//            case R.id.coin_layout:
//                //我的金币
//                if (tag.equals("游客")) {
//                    SystemUtils.toLogin(getContext());
//                    return;
//                }
//                intent.setClass(getContext(), MyCoinActivity.class);
//                Bundle bundle3 = new Bundle();
//                bundle3.putSerializable("userinfo", userInfo);
//                intent.putExtras(bundle3);
//                startActivity(intent);
//                break;
            case R.id.record_layout:
                //我的录音
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), MyRecordActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putSerializable("userinfo", userInfo);
                intent.putExtras(bundle4);
                startActivity(intent);
                break;
        }
    }
}

