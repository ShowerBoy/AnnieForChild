package com.annie.annieforchild.ui.activity.grindEar;

import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.rank.RankingBean;
import com.annie.annieforchild.ui.adapter.RankingRecyclerAdapter;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 榜单
 * Created by WangLei on 2018/1/31 0031
 */

public class RankingListActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private RadioGroup timeRankingList;
    private XRecyclerView rankingRecycler;
    private ImageView rankingBack;
    private List<RankingBean> lists;
    private RankingRecyclerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ranking_list;
    }

    @Override
    protected void initView() {
        rankingBack = findViewById(R.id.ranking_back);
        rankingRecycler = findViewById(R.id.ranking_recycler);
        timeRankingList = findViewById(R.id.time_ranking_list);
        timeRankingList.setOnCheckedChangeListener(this);
        timeRankingList.check(R.id.this_week_btn);
        rankingBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RankingBean bean = new RankingBean(i + 1, R.drawable.photo, "姓名" + i, "10小时" + 20 + i + "分");
            lists.add(bean);
        }
        adapter = new RankingRecyclerAdapter(this, lists);
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rankingRecycler.setLayoutManager(layoutManager);
        rankingRecycler.setLoadingMoreEnabled(false);
        rankingRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rankingRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rankingRecycler.setAdapter(adapter);
        rankingRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankingRecycler.refreshComplete();
                        adapter.notifyDataSetChanged();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.this_week_btn:
                SystemUtils.show(this, "this_week_btn");
                break;
            case R.id.this_month_btn:
                SystemUtils.show(this, "this_month_btn");
                break;
            case R.id.half_year_btn:
                SystemUtils.show(this, "half_year_btn");
                break;
            case R.id.one_year_btn:
                SystemUtils.show(this, "one_year_btn");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ranking_back:
                finish();
                break;
        }
    }
}
