package com.annie.annieforchild.ui.fragment.course;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.course.Course;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.course.OnlineCourse;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.OnlineCourseAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 线上课程
 * Created by wanglei on 2018/3/17.
 */

public class OnlineCourseFragment extends BaseFragment implements ScheduleView {
    private XRecyclerView onlineRecycler;
    private List<OnlineCourse> lists;
    private OnlineCourseAdapter adapter;

    {
        setRegister(true);
    }

    public static OnlineCourseFragment instance() {
        OnlineCourseFragment fragment = new OnlineCourseFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        adapter = new OnlineCourseAdapter(getContext(), lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = onlineRecycler.getChildAdapterPosition(view);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        onlineRecycler.setLayoutManager(layoutManager);
        onlineRecycler.setPullRefreshEnabled(false);
        onlineRecycler.setLoadingMoreEnabled(false);
        onlineRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
        onlineRecycler.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        onlineRecycler = view.findViewById(R.id.online_course_recycler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_course_fragment;
    }

    /**
     * {@link SchedulePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYCOURSESONLINE) {
            lists.clear();
            lists.addAll((List<OnlineCourse>) message.obj);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }
}
