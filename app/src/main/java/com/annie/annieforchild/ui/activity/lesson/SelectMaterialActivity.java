package com.annie.annieforchild.ui.activity.lesson;

import android.support.annotation.IntRange;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.material.MaterialGroup;
import com.annie.annieforchild.ui.adapter.SelectMaterialExpandAdapter;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 选择教材
 * Created by wanglei on 2018/4/9.
 */

public class SelectMaterialActivity extends BaseActivity implements View.OnClickListener {
    private TextView grindEar, reading, spoken;
    private ImageView back;
    private ExpandableListView secondClassifyList;
    private RecyclerView selectMaterialRecycler;
    private List<MaterialGroup> groupList;
    private HashMap<Integer, List<MaterialGroup>> childList;
    private SelectMaterialExpandAdapter expandAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_material;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.select_material_back);
        grindEar = findViewById(R.id.grindear_text);
        reading = findViewById(R.id.reading_text);
        spoken = findViewById(R.id.spoken_text);
        secondClassifyList = findViewById(R.id.second_classify_list);
        selectMaterialRecycler = findViewById(R.id.material_recycler);
        back.setOnClickListener(this);
        grindEar.setOnClickListener(this);
        reading.setOnClickListener(this);
        spoken.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        selectMaterialRecycler.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        groupList = new ArrayList<>();
        childList = new HashMap<>();
        groupList.add(new MaterialGroup("儿歌", true));
        groupList.add(new MaterialGroup("诗歌", false));
        groupList.add(new MaterialGroup("对话", false));
        groupList.add(new MaterialGroup("绘本", false));
        for (int i = 0; i < groupList.size(); i++) {
            List<MaterialGroup> lists = new ArrayList<>();
            if (i == 0) {
                lists.add(new MaterialGroup("磨宝1", true));
            } else {
                lists.add(new MaterialGroup("磨宝1", false));
            }
            lists.add(new MaterialGroup("磨宝2", false));
            lists.add(new MaterialGroup("磨宝3", false));
            lists.add(new MaterialGroup("磨1", false));
            lists.add(new MaterialGroup("磨2", false));
            lists.add(new MaterialGroup("磨3", false));
            childList.put(i, lists);
        }
        expandAdapter = new SelectMaterialExpandAdapter(this, groupList, childList);
        secondClassifyList.setAdapter(expandAdapter);
        secondClassifyList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (!childList.get(groupPosition).get(childPosition).isSelect()) {
                    for (int i = 0; i < childList.size(); i++) {
                        for (int j = 0; j < childList.get(i).size(); j++) {
                            childList.get(i).get(j).setSelect(false);
                        }
                    }
                    childList.get(groupPosition).get(childPosition).setSelect(true);
                    expandAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
        secondClassifyList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (!groupList.get(groupPosition).isSelect()) {
                    for (int i = 0; i < groupList.size(); i++) {
                        groupList.get(i).setSelect(false);
                    }
                    groupList.get(groupPosition).setSelect(true);
                    expandAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        secondClassifyList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < expandAdapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        secondClassifyList.collapseGroup(i);
                    }
                }
            }
        });
        secondClassifyList.expandGroup(0);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_material_back:
                finish();
                break;
            case R.id.grindear_text:
                grindEar.setTextColor(getResources().getColor(R.color.text_orange));
                reading.setTextColor(getResources().getColor(R.color.text_black));
                spoken.setTextColor(getResources().getColor(R.color.text_black));
                break;
            case R.id.reading_text:
                grindEar.setTextColor(getResources().getColor(R.color.text_black));
                reading.setTextColor(getResources().getColor(R.color.text_orange));
                spoken.setTextColor(getResources().getColor(R.color.text_black));
                break;
            case R.id.spoken_text:
                grindEar.setTextColor(getResources().getColor(R.color.text_black));
                reading.setTextColor(getResources().getColor(R.color.text_black));
                spoken.setTextColor(getResources().getColor(R.color.text_orange));
                break;
        }
    }
}
