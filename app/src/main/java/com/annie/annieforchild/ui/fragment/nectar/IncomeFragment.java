package com.annie.annieforchild.ui.fragment.nectar;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.nectar.MyNectar;
import com.annie.annieforchild.bean.nectar.NectarBean;
import com.annie.annieforchild.presenter.imp.NectarPresenterImp;
import com.annie.annieforchild.ui.adapter.MyNectarAdapter;
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

public class IncomeFragment extends BaseFragment {
    private XRecyclerView incomeRecycler;
    private MyNectarAdapter adapter;
    private List<NectarBean> lists;

    {
        setRegister(true);
    }

    public static IncomeFragment instance() {
        IncomeFragment fragment = new IncomeFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        incomeRecycler.setLayoutManager(layoutManager);
        incomeRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        incomeRecycler.setPullRefreshEnabled(false);
        incomeRecycler.setLoadingMoreEnabled(false);
        incomeRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lists = new ArrayList<>();
        adapter = new MyNectarAdapter(getContext(), lists);
        incomeRecycler.setAdapter(adapter);
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
        if (message.what == MethodCode.EVENT_GETNECTAR) {
            MyNectar myNectar = (MyNectar) message.obj;
            List<NectarBean> list = myNectar.getNectarExchanges();
            lists.clear();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getType() == 0) {
                    lists.add(list.get(i));
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
