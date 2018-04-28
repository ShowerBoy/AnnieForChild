package com.annie.annieforchild.ui.fragment.schedule;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.OnlineScheAdapter;
import com.annie.annieforchild.view.ScheduleView;
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

public class OnlineScheduleFragment extends BaseFragment implements ScheduleView {
    private XRecyclerView onlineRecycler;
    private List<Schedule> lists;
    private OnlineScheAdapter adapter;
    private SchedulePresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    public static OnlineScheduleFragment instance() {
        OnlineScheduleFragment fragment = new OnlineScheduleFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        lists = new ArrayList<>();
        presenter = new SchedulePresenterImp(getContext(), this);
        presenter.initViewAndData();
        adapter = new OnlineScheAdapter(getContext(), lists, presenter);
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

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
