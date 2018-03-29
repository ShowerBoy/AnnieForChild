package com.annie.annieforchild.ui.fragment.course;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.Course;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.OnlineCourseAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 寒暑假
 * Created by wanglei on 2018/3/19.
 */

public class VacationCourseFragment extends BaseFragment implements ScheduleView {
    private XRecyclerView vacationRecycler;
    private List<Course> lists;
//    private OnlineCourseAdapter adapter;
    private SchedulePresenter presenter;

    {
        setRegister(true);
    }

    public static VacationCourseFragment instance() {
        VacationCourseFragment fragment = new VacationCourseFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        presenter = new SchedulePresenterImp(getContext(), this);
        presenter.initViewAndData();
        lists = new ArrayList<>();
//        adapter = new OnlineCourseAdapter(getContext(), lists, new OnRecyclerItemClickListener() {
//            @Override
//            public void onItemClick(View view) {
//                int position = vacationRecycler.getChildAdapterPosition(view);
//            }
//
//            @Override
//            public void onItemLongClick(View view) {
//
//            }
//        });
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        vacationRecycler.setLayoutManager(layoutManager);
        vacationRecycler.setPullRefreshEnabled(true);
        vacationRecycler.setLoadingMoreEnabled(false);
        vacationRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
//        vacationRecycler.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        vacationRecycler=view.findViewById(R.id.vacation_course_recycler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vacation_course_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {

    }

    @Override
    public void showInfo(String info) {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }
}
