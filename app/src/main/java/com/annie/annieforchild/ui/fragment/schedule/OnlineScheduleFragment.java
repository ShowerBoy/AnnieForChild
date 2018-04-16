package com.annie.annieforchild.ui.fragment.schedule;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.OnlineScheAdapter;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 线上课表
 * Created by WangLei on 2018/2/28 0028
 */

public class OnlineScheduleFragment extends BaseFragment {
    private XRecyclerView onlineRecycler;
    private List<Schedule> lists;
    private OnlineScheAdapter adapter;

    {
        setRegister(true);
    }

    public static OnlineScheduleFragment instance() {
        OnlineScheduleFragment fragment = new OnlineScheduleFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        adapter = new OnlineScheAdapter(getContext(), lists);
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        onlineRecycler.setLayoutManager(layoutManager);
        onlineRecycler.setPullRefreshEnabled(false);
        onlineRecycler.setLoadingMoreEnabled(false);
        onlineRecycler.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        onlineRecycler = view.findViewById(R.id.online_recycler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_sche_fragment;
    }

    /**
     * {@link SchedulePresenterImp#getScheduleDetails(String)}
     *
     * @param message
     */
    @Subscribe
    public void onEventMainThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYSCHEDULE) {
            TotalSchedule totalSchedule = (TotalSchedule) message.obj;
            lists.clear();
            lists.addAll(totalSchedule.getOnline());
            adapter.notifyDataSetChanged();
        }
    }

}
