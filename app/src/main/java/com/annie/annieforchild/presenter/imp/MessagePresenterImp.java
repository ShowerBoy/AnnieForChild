package com.annie.annieforchild.presenter.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.tongzhi.MyNotice;
import com.annie.annieforchild.bean.tongzhi.Notice;
import com.annie.annieforchild.interactor.MessageInteractor;
import com.annie.annieforchild.interactor.imp.MessageInteractorImp;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 消息和帮助,录音,兑换金币
 * 分享
 * Created by WangLei on 2018/3/7 0007
 */

public class MessagePresenterImp extends BasePresenterImp implements MessagePresenter {
    private Context context;
    private ViewInfo viewInfo;
    private MessageInteractor interactor;

    public MessagePresenterImp(Context context, ViewInfo viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
    }

    @Override
    public void initViewAndData() {
        interactor = new MessageInteractorImp(context, this);
    }

    @Override
    public void getMyMessages() {
        viewInfo.showLoad();
        interactor.getMyMessages();
    }

    @Override
    public void getDocumentations() {
        interactor.getDocumentations();
    }

    @Override
    public void myRecordings() {
        viewInfo.showLoad();
        interactor.myRecordings();
    }

    @Override
    public void deleteRecording(int recordingId, int origin) {
        viewInfo.showLoad();
        interactor.deleteRecording(recordingId, origin);
    }

    @Override
    public void feedback(String content) {
        viewInfo.showLoad();
        interactor.feedback(content);
    }

    /**
     * 兑换金币
     *
     * @param nectar
     */
    @Override
    public void exchangeGold(int nectar) {
        viewInfo.showLoad();
        interactor.exchangeGold(nectar);
    }

    /**
     * 分享
     */
    @Override
    public void shareTo() {
        interactor.shareTo();
    }

    /**
     * @param what
     * @param result
     */
    @Override
    public void Success(int what, Object result) {
        viewInfo.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_GETMYMESSAGES) {
                MyNotice myNotice = (MyNotice) result;
                //
                /**
                 * {@link com.annie.annieforchild.ui.fragment.message.NoticeFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.fragment.message.GroupMsgFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = -1;
                message.obj = myNotice;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_FEEDBACK) {
                viewInfo.showInfo((String) result);
            } else if (what == MethodCode.EVENT_GETHELP) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.help.HelpFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message2 = new JTMessage();
                message2.what = what;
                message2.obj = result;
                EventBus.getDefault().post(message2);
            } else if (what == MethodCode.EVENT_MYRECORDINGS) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.MyRecordActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message3 = new JTMessage();
                message3.what = what;
                message3.obj = result;
                EventBus.getDefault().post(message3);
            } else if (what == MethodCode.EVENT_DELETERECORDING) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.MyRecordActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message5 = new JTMessage();
                message5.what = what;
                message5.obj = result;
                EventBus.getDefault().post(message5);
            } else if (what == MethodCode.EVENT_EXCHANGEGOLD) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.ExchangeActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_SHARETO) {
                /**
                 * {@link }
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
        viewInfo.dismissLoad();
        viewInfo.showInfo(error);
    }
}
