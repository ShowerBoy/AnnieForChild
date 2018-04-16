package com.annie.annieforchild.ui.activity.pk;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.Exercise;
import com.annie.annieforchild.ui.adapter.ExerciseAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * pk——练习
 * Created by wanglei on 2018/3/30.
 */

public class ExerciseActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private Button challenge;
    private XRecyclerView exerciseList;
    private List<Exercise> lists;
    private ExerciseAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.exercise_back);
        challenge = findViewById(R.id.go_to_challenge);
        exerciseList = findViewById(R.id.exercise_list);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        exerciseList.setLayoutManager(manager);
        exerciseList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        exerciseList.setPullRefreshEnabled(false);
        exerciseList.setLoadingMoreEnabled(false);
        exerciseList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lists = new ArrayList<>();
        lists.add(new Exercise("“I like cheese,”said Superhammy,The Yellow Cheese is on the “I like cheese,”said Superhammy1.", false));
        lists.add(new Exercise("“I like cheese,”said Superhammy,The Yellow Cheese is on the “I like cheese,”said Superhammy2.", false));
        lists.add(new Exercise("“I like cheese,”said Superhammy,The Yellow Cheese is on the “I like cheese,”said Superhammy3.", false));
        lists.add(new Exercise("“I like cheese,”said Superhammy,The Yellow Cheese is on the “I like cheese,”said Superhammy4.", false));
        lists.add(new Exercise("“I like cheese,”said Superhammy,The Yellow Cheese is on the “I like cheese,”said Superhammy5.", false));
        adapter = new ExerciseAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int positon = exerciseList.getChildAdapterPosition(view);
                if (lists.get(positon - 1).isSelect()) {
                    lists.get(positon - 1).setSelect(false);
                } else {
                    for (int i = 0; i < lists.size(); i++) {
                        lists.get(i).setSelect(false);
                    }
                    lists.get(positon - 1).setSelect(true);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        exerciseList.setAdapter(adapter);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_back:
                finish();
                break;
        }
    }
}
