package com.annie.annieforchild.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.presenter.imp.FourthPresenterImp;
import com.annie.annieforchild.ui.activity.child.ModifyChildActivity;
import com.annie.annieforchild.ui.activity.my.AboutActivity;
import com.annie.annieforchild.ui.activity.my.GradeAchievementActivity;
import com.annie.annieforchild.ui.activity.my.HelpActivity;
import com.annie.annieforchild.ui.activity.my.MyCoinActivity;
import com.annie.annieforchild.ui.activity.my.MyCollectionActivity;
import com.annie.annieforchild.ui.activity.my.MyExchangeActivity;
import com.annie.annieforchild.ui.activity.my.MyMessageActivity;
import com.annie.annieforchild.ui.activity.my.MyNectarActivity;
import com.annie.annieforchild.ui.activity.my.MyRecordActivity;
import com.annie.annieforchild.ui.activity.my.SettingsActivity;
import com.annie.annieforchild.ui.activity.my.ToFriendActivity;
import com.annie.annieforchild.ui.adapter.MemberAdapter;
import com.annie.annieforchild.view.FourthView;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 我的
 * Created by WangLei on 2018/1/12 0012
 */

public class FourthFragment extends BaseFragment implements FourthView, View.OnClickListener {
    private RelativeLayout myMsgLayout, toFriendLayout, myExchangeLayout, helpLayout, aboutLayout, collectionLayout;
    private LinearLayout nectarLayout, coinLayout, recordLayout;
    private ImageView settings, sexIcon, headpicBack;
    private CircleImageView userHeadpic;
    private TextView userId, userName, userLevel, userOld, nectarNum, coinNum, recordNum;
    private RecyclerView member_layout;
    private FourthPresenter presenter;
    private UserInfo userInfo;
    private AlertHelper helper;
    private Dialog dialog;
    private String tag; //身份标示

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
    }

    @Override
    protected void initView(View view) {
        userHeadpic = view.findViewById(R.id.user_headpic);
        settings = view.findViewById(R.id.settings);
        sexIcon = view.findViewById(R.id.sex_icon);
        userId = view.findViewById(R.id.user_id);
        userName = view.findViewById(R.id.user_name);
        userLevel = view.findViewById(R.id.user_level);
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
        coinLayout = view.findViewById(R.id.coin_layout);
        recordLayout = view.findViewById(R.id.record_layout);
        headpicBack = view.findViewById(R.id.headpic_back);
        myMsgLayout.setOnClickListener(this);
        toFriendLayout.setOnClickListener(this);
        myExchangeLayout.setOnClickListener(this);
        helpLayout.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);
        userHeadpic.setOnClickListener(this);
        settings.setOnClickListener(this);
        userLevel.setOnClickListener(this);
        collectionLayout.setOnClickListener(this);
        nectarLayout.setOnClickListener(this);
        coinLayout.setOnClickListener(this);
        recordLayout.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.user_headpic:
                //头像
                if (tag.equals("游客")) {
                    showInfo("请登录");
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
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.my_msg_layout:
                //我的信息
                if (tag.equals("游客")) {
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), MyMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.to_friend_layout:
                //推荐好友
                if (tag.equals("游客")) {
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), ToFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.my_exchange_layout:
                //我的兑换
                if (tag.equals("游客")) {
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), MyExchangeActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("userinfo", userInfo);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.collection_layout:
                //我的收藏
                if (tag.equals("游客")) {
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), MyCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.help_layout:
                //帮助与反馈
                if (tag.equals("游客")) {
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.about_layout:
                //关于
                intent.setClass(getContext(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.user_level:
                //等级成就
                if (tag.equals("游客")) {
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), GradeAchievementActivity.class);
                startActivity(intent);
                break;
            case R.id.nectar_layout:
                //我的花蜜
                if (tag.equals("游客")) {
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), MyNectarActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("userinfo", userInfo);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;
            case R.id.coin_layout:
                //我的金币
                if (tag.equals("游客")) {
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), MyCoinActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("userinfo", userInfo);
                intent.putExtras(bundle3);
                startActivity(intent);
                break;
            case R.id.record_layout:
                //我的录音
                if (tag.equals("游客")) {
                    showInfo("请登录");
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

    /**
     * {@link FourthPresenterImp#Success(int, Object)}
     * {@link com.annie.annieforchild.presenter.imp.ChildPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message != null) {
            if (message.what == MethodCode.EVENT_USERINFO) {
                userInfo = (UserInfo) message.obj;
                refresh(userInfo);
            } else if (message.what == MethodCode.EVENT_ADDCHILD2) {
//                presenter.initViewAndData();
                presenter.getUserInfo();
            } else if (message.what == MethodCode.EVENT_UPDATEUSER) {
//                presenter.initViewAndData();
                presenter.getUserInfo();
            } else if (message.what == MethodCode.EVENT_DELETEUSERNAME) {
                showInfo((String) message.obj);
                presenter.getUserInfo();
            }
        }
    }

    public void refresh(UserInfo userInfo) {
        userId.setText("学员编号：" + userInfo.getUsername());
        Glide.with(getContext()).load(userInfo.getAvatar()).into(userHeadpic);
        userName.setText(userInfo.getName());
        userLevel.setText(userInfo.getLevel());
        userOld.setText(getOld(userInfo.getBirthday()) + "岁");
        if (userInfo.getSex().equals("男")) {
            sexIcon.setImageResource(R.drawable.icon_sex_boy);
        } else {
            sexIcon.setImageResource(R.drawable.icon_sex_girl);
        }
        nectarNum.setText(userInfo.getNectar() != null ? userInfo.getNectar() : "0");
        coinNum.setText(userInfo.getGold() != null ? userInfo.getGold() : "0");
        recordNum.setText(userInfo.getRecorderCount() != null ? userInfo.getRecorderCount() : "0");
        Glide.with(getContext()).load(userInfo.getAvatar()).bitmapTransform(new BlurTransformation(getContext(), 5, 1)).into(headpicBack);
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
        int month2 = calendar.get(Calendar.MONTH);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        int old = year2 - year;
        if (month < month2) {
            return old - 1;
        } else if (month > month2) {
            return old;
        } else {
            if (day < day2) {
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


}
