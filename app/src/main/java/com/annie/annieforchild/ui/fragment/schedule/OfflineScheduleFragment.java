package com.annie.annieforchild.ui.fragment.schedule;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.OfflineScheAdapter;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 线下课表
 * Created by WangLei on 2018/2/28 0028
 */

public class OfflineScheduleFragment extends BaseFragment {
    private XRecyclerView offlineRecycler;
    private RelativeLayout emptyLayout;
    private List<Schedule> lists;
    private OfflineScheAdapter adapter;

    {
        setRegister(true);
    }

    public static OfflineScheduleFragment instance() {
        OfflineScheduleFragment fragment = new OfflineScheduleFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        adapter = new OfflineScheAdapter(getContext(), lists);
        initRecycler();

    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        offlineRecycler.setLayoutManager(layoutManager);
        offlineRecycler.setPullRefreshEnabled(false);
        offlineRecycler.setLoadingMoreEnabled(false);
        offlineRecycler.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        offlineRecycler = view.findViewById(R.id.offline_recycler);
        emptyLayout = view.findViewById(R.id.empty_data_layout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_offline_sche_fragment;
    }

    /**
     * {@link SchedulePresenterImp#getScheduleDetails(String)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYSCHEDULE) {
            TotalSchedule totalSchedule = (TotalSchedule) message.obj;
            lists.clear();
            lists.addAll(totalSchedule.getOffline());
            adapter.notifyDataSetChanged();
            if (lists.size() == 0) {
                emptyLayout.setVisibility(View.VISIBLE);
            } else {
                emptyLayout.setVisibility(View.GONE);
            }
        }
    }
}
