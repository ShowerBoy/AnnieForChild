package com.annie.annieforchild.ui.fragment.schedule;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.Schedule;
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
        Schedule schedule = new Schedule();
        schedule.setMaterialName("教材1");
        schedule.setStart("10:00");
        schedule.setStop("15:00");
        Schedule schedule2 = new Schedule();
        schedule2.setMaterialName("教材2");
        schedule2.setStart("13:30");
        schedule2.setStop("17:03");
        Schedule schedule3 = new Schedule();
        schedule3.setMaterialName("教材3");
        schedule3.setStart("15:36");
        schedule3.setStop("22:40");
        lists.add(schedule);
        lists.add(schedule2);
        lists.add(schedule3);
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
    public void onEventMainThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_SCHEDULE) {
            String time = (String) message.obj;
            //TODO:刷新数据

        }
    }
}
