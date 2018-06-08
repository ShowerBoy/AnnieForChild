package com.annie.annieforchild.ui.fragment.selectmaterial;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.ClassList;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.material.SubClassList;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity;
import com.annie.annieforchild.ui.adapter.MaterialAdapter;
import com.annie.annieforchild.ui.adapter.SelectMaterialExpandAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wanglei on 2018/4/23.
 */

public class SelectSpokenFragment extends BaseFragment implements ScheduleView {
    private ExpandableListView expandableListView;
    private XRecyclerView recyclerView;
    private List<ClassList> groupList;
    private HashMap<Integer, List<SubClassList>> childList;
    private List<Material> lists;
    private SchedulePresenter presenter;
    private SelectMaterialExpandAdapter expandAdapter;
    private MaterialAdapter adapter;
    private AlertHelper helper;
    private Dialog dialog;
    private Schedule schedule;
    private int currentGroup = 0;
    private int currentChild = 0;
    private String date;

    {
        setRegister(true);
    }

    public static SelectSpokenFragment instance(Schedule schedule, String date) {
        SelectSpokenFragment fragment = new SelectSpokenFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("schedule", schedule);
        bundle.putString("date", date);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();

        if (getArguments() != null) {
            schedule = (Schedule) getArguments().getSerializable("schedule");
            date = getArguments().getString("date");
        }

        presenter = new SchedulePresenterImp(getContext(), this);
        presenter.initViewAndData();

        initExpendList();
        adapter = new MaterialAdapter(getContext(), lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view) - 1;
                Intent intent = new Intent(getContext(), AddOnlineScheActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("material", lists.get(position));
                bundle.putString("date", date);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recyclerView.setAdapter(adapter);
        presenter.getMaterialClass(2);

    }

    @Override
    protected void initView(View view) {
        expandableListView = view.findViewById(R.id.select_spoken_list);
        recyclerView = view.findViewById(R.id.select_spoken_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    }

    private void initExpendList() {
        groupList = new ArrayList<>();
        childList = new HashMap<>();
        lists = new ArrayList<>();
        expandAdapter = new SelectMaterialExpandAdapter(getContext(), groupList, childList);
        expandableListView.setAdapter(expandAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (!childList.get(groupPosition).get(childPosition).isSelected()) {
                    for (int i = 0; i < childList.size(); i++) {
                        for (int j = 0; j < childList.get(i).size(); j++) {
                            childList.get(i).get(j).setSelected(false);
                        }
                    }
                    childList.get(groupPosition).get(childPosition).setSelected(true);
                    currentChild = childPosition;
                    refresh();
                    expandAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (!groupList.get(groupPosition).isSelected()) {
                    for (int i = 0; i < groupList.size(); i++) {
                        groupList.get(i).setSelected(false);
                    }
                    groupList.get(groupPosition).setSelected(true);
                    currentGroup = groupPosition;
                    expandAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < expandAdapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
        expandableListView.expandGroup(0);
    }

    private void refresh() {
        lists.clear();
        lists.addAll(childList.get(currentGroup).get(currentChild).getMaterialList());
        adapter.notifyDataSetChanged();
    }

    /**
     * {@link SchedulePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETALLMATERIALLIST3) {
            groupList.clear();
            childList.clear();
            lists.clear();
            groupList.addAll((List<ClassList>) message.obj);
            for (int i = 0; i < groupList.size(); i++) {
                if (i == 0) {
                    groupList.get(i).getSubClassList().get(i).setSelected(true);
                }
                childList.put(i, groupList.get(i).getSubClassList());
            }
            //初始化选中第一项
            groupList.get(0).setSelected(true);
            lists.addAll(childList.get(0).get(0).getMaterialList());
            expandAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_spoken_fragment;
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
