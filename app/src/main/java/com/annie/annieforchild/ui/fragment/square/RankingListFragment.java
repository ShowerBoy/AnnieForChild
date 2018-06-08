package com.annie.annieforchild.ui.fragment.square;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.rank.Rank;
import com.annie.annieforchild.bean.rank.RankList;
import com.annie.annieforchild.bean.rank.RankingBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.RankListAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行榜
 * Created by wanglei on 2018/5/11.
 */

public class RankingListFragment extends BaseFragment implements SongView {
    private RecyclerView recycler;
    private RankListAdapter adapter;
    private AlertHelper helper;
    private Dialog dialog;
    private List<RankList> lists;
    private RankList rankList;
    private GrindEarPresenter presenter;

    {
        setRegister(true);
    }

    public static RankingListFragment instance() {
        RankingListFragment fragment = new RankingListFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        rankList = new RankList();
        lists.add(rankList);
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        adapter = new RankListAdapter(getContext(), lists);
        recycler.setAdapter(adapter);
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        presenter.getSquareRank();
    }

    @Override
    protected void initView(View view) {
        recycler = view.findViewById(R.id.ranking_list_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
    }

    /**
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETSQUARERANK) {
            rankList = (RankList) message.obj;
            lists.clear();
            lists.add(rankList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ranking_list_fragment;
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
