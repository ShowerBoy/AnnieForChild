package com.annie.annieforchild.presenter.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.Notice;
import com.annie.annieforchild.interactor.MessageInteractor;
import com.annie.annieforchild.interactor.imp.MessageInteractorImp;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 消息和帮助,录音,兑换金币
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
    public void getExchangeRecording() {
        viewInfo.showLoad();
        interactor.getExchangeRecording();
    }

    @Override
    public void feedback(String content) {
        viewInfo.showLoad();
        interactor.feedback(content);
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
                String notis = ((JSONObject) result).getString("notis");
                String messsages = ((JSONObject) result).getString("messages");
                List<Notice> notisLists = JSON.parseArray(notis, Notice.class);
                List<Notice> messageLists = JSON.parseArray(messsages, Notice.class);
                //TODO:
                Notice notice = new Notice();
                notice.setTag("系统");
                notice.setTitle("欢迎登陆安妮花");
                notice.setContent("家长你好，欢迎注册安妮花，有何质问可查看帮助或者联系客服，客服电话:400-888-9988!");
                notice.setTime("20180308");
                Notice notice1 = new Notice();
                notice1.setTag("课程提醒");
                notice1.setTitle("欢迎登陆安妮花");
                notice1.setContent("家长你好，欢迎注册安妮花，有何质问可查看帮助或者联系客服，客服电话:400-888-9988!");
                notice1.setTime("20181003");
                notisLists.add(notice);
                notisLists.add(notice1);
                messageLists.add(notice1);
                //
                /**
                 * {@link com.annie.annieforchild.ui.fragment.message.NoticeFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = -1;
                message.obj = notisLists;
                EventBus.getDefault().post(message);
                /**
                 * {@link com.annie.annieforchild.ui.fragment.message.GroupMsgFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message1 = new JTMessage();
                message1.what = -2;
                message1.obj = messageLists;
                EventBus.getDefault().post(message1);
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
            } else if (what == MethodCode.EVENT_EXCHANGERECORDING) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.MyExchangeActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message4 = new JTMessage();
                message4.what = what;
                message4.obj = result;
                EventBus.getDefault().post(message4);
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        viewInfo.dismissLoad();
        viewInfo.showInfo(error);
    }
}
