package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.interactor.FourthInteractor;
import com.annie.annieforchild.interactor.imp.FourthInteractorImp;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.ui.adapter.MemberAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.FourthView;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的
 * Created by WangLei on 2018/2/8 0008
 */

public class FourthPresenterImp extends BasePresenterImp implements FourthPresenter {
    private Context context;
    private FourthView fourthView;
    private FourthInteractor interactor;
    private List<UserInfo2> lists;
    private MemberAdapter adapter;
    int position;
    private String tag; //身份标示

    public FourthPresenterImp(Context context, FourthView fourthView, String tag) {
        this.context = context;
        this.fourthView = fourthView;
        this.tag = tag;
    }

    @Override
    public void initViewAndData() {
        interactor = new FourthInteractorImp(context, this);
        lists = new ArrayList<>();
        adapter = new MemberAdapter(context, lists, tag, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                position = fourthView.getMemberRecycler().getChildAdapterPosition(view);
                SystemUtils.GeneralDialog(context, "切换默认学员")
                        .setMessage("切换当前学员为默认学员？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setDefaultUser(lists.get(position).getUsername());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        fourthView.getMemberRecycler().setAdapter(adapter);
    }

    /**
     * 获取学员信息(包含获取学员列表)
     */
    @Override
    public void getUserInfo() {
        fourthView.showLoad();
        interactor.getUserInfo();
    }

    /**
     * 设置默认学员
     *
     * @param defaultUser
     */
    @Override
    public void setDefaultUser(String defaultUser) {
        fourthView.showLoad();
        interactor.setDefaultUser(defaultUser);
    }

    @Override
    public void getUserList() {
//        interactor.getUserList();
    }

    @Override
    public void Success(int what, Object result) {
        if (result != null) {
            if (what == MethodCode.EVENT_USERINFO) {
                fourthView.dismissLoad();
                UserInfo info = (UserInfo) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.FourthFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.setWhat(what);
                message.setObj(info);
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_USERLIST) {
                fourthView.dismissLoad();
                lists.clear();
                lists.addAll((List<UserInfo2>) result);
                adapter.notifyDataSetChanged();
            } else if (what == MethodCode.EVENT_SETDEFAULEUSER) {
                SystemUtils.defaultUsername = lists.get(position).getUsername();
                getUserInfo();
                fourthView.showInfo((String) result);
            }
        } else {
            fourthView.dismissLoad();
        }
    }

    @Override
    public void Error(int what, String error) {
        fourthView.dismissLoad();
        fourthView.showInfo(error);
    }
}
