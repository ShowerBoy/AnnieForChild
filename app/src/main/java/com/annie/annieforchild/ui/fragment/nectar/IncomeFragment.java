package com.annie.annieforchild.ui.fragment.nectar;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.Collection;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.NectarBean;
import com.annie.annieforchild.presenter.NectarPresenter;
import com.annie.annieforchild.presenter.imp.NectarPresenterImp;
import com.annie.annieforchild.ui.adapter.CollectionAdapter;
import com.annie.annieforchild.ui.adapter.MyNectarAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 花蜜——收入
 * Created by WangLei on 2018/3/7 0007
 */

public class IncomeFragment extends BaseFragment implements ViewInfo {
    private XRecyclerView incomeRecycler;
    private MyNectarAdapter adapter;
    private List<NectarBean> lists;
    private NectarPresenter presenter;

    {
        setRegister(true);
    }

    public static IncomeFragment instance() {
        IncomeFragment fragment = new IncomeFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        presenter = new NectarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        incomeRecycler.setLayoutManager(layoutManager);
        incomeRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        incomeRecycler.setPullRefreshEnabled(true);
        incomeRecycler.setLoadingMoreEnabled(false);
        incomeRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        incomeRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.getNectar(0);
            }

            @Override
            public void onLoadMore() {

            }
        });
        lists = new ArrayList<>();
        adapter = new MyNectarAdapter(getContext(), lists);
        incomeRecycler.setAdapter(adapter);
        presenter.getNectar(0);
    }

    @Override
    protected void initView(View view) {
        incomeRecycler = view.findViewById(R.id.income_recycler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_income_fragment;
    }

    /**
     * {@link NectarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNECTAR1) {
            lists.clear();
            lists.addAll((List<NectarBean>) message.obj);
            adapter.notifyDataSetChanged();
            incomeRecycler.refreshComplete();
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
