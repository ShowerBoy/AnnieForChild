package com.annie.annieforchild.presenter.imp;

import android.content.Context;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.Collection;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.interactor.CollectionInteractor;
import com.annie.annieforchild.interactor.imp.CollectionInteractorImp;
import com.annie.annieforchild.presenter.CollectionPresenter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏
 * Created by WangLei on 2018/3/6 0006
 */

public class CollectionPresenterImp extends BasePresenterImp implements CollectionPresenter {
    private Context context;
    private ViewInfo collectionView;
    private int classId;
    private CollectionInteractor interactor;

    public CollectionPresenterImp(Context context, ViewInfo collectionView) {
        this.context = context;
        this.collectionView = collectionView;
    }

    @Override
    public void initViewAndData() {
        interactor = new CollectionInteractorImp(context, this);
    }

    /**
     * 我的收藏  1:磨耳朵 2:阅读 3:口语
     *
     * @param type
     */
    @Override
    public void getMyCollections(int type) {
        interactor.getMyCollections(type);
    }

    /**
     * 取消收藏
     *
     * @param type
     * @param courseId
     */
    @Override
    public void cancelCollection(int type, int audioSource, int courseId) {
        collectionView.showLoad();
        interactor.cancelCollection(type, audioSource, courseId);
    }

    @Override
    public void Success(int what, Object result) {
        collectionView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_MYCOLLECTIONS1) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.GrindEarFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS2) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.ReadingFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS3) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.SpokenFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS0) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.OtherFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION1) {
                collectionView.dismissLoad();
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.GrindEarFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION2) {
                collectionView.dismissLoad();
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.ReadingFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION3) {
                collectionView.dismissLoad();
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.SpokenFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION0) {
                collectionView.dismissLoad();
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.OtherFragment#onMainEventThread(JTMessage)}
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
        collectionView.dismissLoad();
        collectionView.showInfo(error);
    }
}
