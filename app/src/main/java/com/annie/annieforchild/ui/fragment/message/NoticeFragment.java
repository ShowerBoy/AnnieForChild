package com.annie.annieforchild.ui.fragment.message;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.tongzhi.MyNotice;
import com.annie.annieforchild.bean.tongzhi.Notice;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.ui.adapter.NoticeAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知
 * Created by WangLei on 2018/2/26 0026
 */

public class NoticeFragment extends BaseFragment {
    private XRecyclerView noticeRecycler;
    private List<Notice> lists;
    private NoticeAdapter adapter;

    {
        setRegister(true);
    }

    public static NoticeFragment instance() {
        NoticeFragment fragment = new NoticeFragment();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        noticeRecycler = view.findViewById(R.id.notice_recycler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_fragment;
    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noticeRecycler.setLayoutManager(layoutManager);
        noticeRecycler.setPullRefreshEnabled(false);
        noticeRecycler.setLoadingMoreEnabled(false);
        noticeRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lists = new ArrayList<>();
        adapter = new NoticeAdapter(getContext(), lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        noticeRecycler.setAdapter(adapter);
    }

    /**
     * {@link MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == -1) {
            MyNotice myNotice = (MyNotice) message.obj;
            lists.clear();
            lists.addAll(myNotice.getNotis());
            adapter.notifyDataSetChanged();
        }
    }

}
