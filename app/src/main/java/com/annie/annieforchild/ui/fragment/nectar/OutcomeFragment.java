package com.annie.annieforchild.ui.fragment.nectar;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.NectarBean;
import com.annie.annieforchild.presenter.NectarPresenter;
import com.annie.annieforchild.presenter.imp.NectarPresenterImp;
import com.annie.annieforchild.ui.adapter.MyNectarAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 花蜜——支出
 * Created by WangLei on 2018/3/7 0007
 */

public class OutcomeFragment extends BaseFragment implements ViewInfo {
    private XRecyclerView outcomeRecycler;
    private MyNectarAdapter adapter;
    private List<NectarBean> lists;
    private NectarPresenter presenter;

    {
        setRegister(true);
    }

    public static OutcomeFragment instance() {
        OutcomeFragment fragment = new OutcomeFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        presenter = new NectarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        outcomeRecycler.setLayoutManager(layoutManager);
        outcomeRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        outcomeRecycler.setPullRefreshEnabled(true);
        outcomeRecycler.setLoadingMoreEnabled(false);
        outcomeRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        outcomeRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.getNectar(1);
            }

            @Override
            public void onLoadMore() {

            }
        });
        lists = new ArrayList<>();
        adapter = new MyNectarAdapter(getContext(), lists);
        outcomeRecycler.setAdapter(adapter);
        presenter.getNectar(1);
    }

    @Override
    protected void initView(View view) {
        outcomeRecycler = view.findViewById(R.id.outcome_recycler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.outcome_fragment;
    }

    /**
     * {@link NectarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNECTAR2) {
            lists.clear();
            lists.addAll((List<NectarBean>) message.obj);
            adapter.notifyDataSetChanged();
            outcomeRecycler.refreshComplete();
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
