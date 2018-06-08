package com.annie.annieforchild.ui.fragment.message;

import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.tongzhi.MyNotice;
import com.annie.annieforchild.bean.tongzhi.Notice;
import com.annie.annieforchild.ui.adapter.NoticeAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 群消息
 * Created by WangLei on 2018/2/26 0026
 */

public class GroupMsgFragment extends BaseFragment {
    private XRecyclerView groupmsgRecycler;
    private ImageView empty;
    private List<Notice> lists;
    private NoticeAdapter adapter;

    {
        setRegister(true);
    }

    public static GroupMsgFragment instance() {
        GroupMsgFragment fragment = new GroupMsgFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        groupmsgRecycler.setLayoutManager(layoutManager);
        groupmsgRecycler.setPullRefreshEnabled(false);
        groupmsgRecycler.setLoadingMoreEnabled(false);
        groupmsgRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lists = new ArrayList<>();
        adapter = new NoticeAdapter(getContext(), lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        groupmsgRecycler.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        groupmsgRecycler = view.findViewById(R.id.group_msg_recycler);
        empty = view.findViewById(R.id.empty);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_msg_fragment;
    }

    /**
     * {@link com.annie.annieforchild.presenter.imp.MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == -1) {
            MyNotice myNotice = (MyNotice) message.obj;
            lists.clear();
            lists.addAll(myNotice.getMessages());
            adapter.notifyDataSetChanged();
            if (lists.size() == 0) {
                groupmsgRecycler.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            } else {
                groupmsgRecycler.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
            }
        }
    }
}
