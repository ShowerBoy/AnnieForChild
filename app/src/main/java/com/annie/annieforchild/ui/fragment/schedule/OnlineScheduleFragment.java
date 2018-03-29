package com.annie.annieforchild.ui.fragment.schedule;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.Schedule;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.OnlineScheAdapter;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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
        //
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
        //
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
        if (message.what == MethodCode.EVENT_SCHEDULE) {
            String time = (String) message.obj;
            //TODO:刷新数据
        }
    }

}
