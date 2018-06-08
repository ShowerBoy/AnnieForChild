package com.annie.annieforchild.presenter.imp;

import android.content.Context;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.interactor.NectarInteractor;
import com.annie.annieforchild.interactor.imp.NectarInteractorImp;
import com.annie.annieforchild.presenter.NectarPresenter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;

/**
 * 花蜜
 * Created by WangLei on 2018/3/7 0007
 */

public class NectarPresenterImp extends BasePresenterImp implements NectarPresenter {
    private Context context;
    private NectarInteractor interactor;
    private ViewInfo viewInfo;

    public NectarPresenterImp(Context context, ViewInfo viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
    }

    @Override
    public void initViewAndData() {
        interactor = new NectarInteractorImp(context, this);
    }

    /**
     * 我的花蜜
     */
    @Override
    public void getNectar() {
        interactor.getNectar();
    }

    @Override
    public void Success(int what, Object result) {
        if (result != null) {
            if (what == MethodCode.EVENT_GETNECTAR) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.nectar.IncomeFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.fragment.nectar.OutcomeFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        viewInfo.showInfo(error);
    }
}
