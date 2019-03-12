package com.annie.annieforchild.ui.fragment.help;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.HelpBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.HelpAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 帮助
 * Created by WangLei on 2018/2/26 0026
 */

public class HelpFragment extends BaseFragment implements ViewInfo {
    private XRecyclerView helpRecycler;
    private List<HelpBean> lists;
    private HelpAdapter adapter;
    private MessagePresenter presenter;

    {
        setRegister(true);
    }

    public static HelpFragment instance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        presenter = new MessagePresenterImp(getContext(), this);
        presenter.initViewAndData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        helpRecycler.setLayoutManager(layoutManager);
        helpRecycler.setPullRefreshEnabled(false);
        helpRecycler.setLoadingMoreEnabled(false);
        helpRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lists = new ArrayList<>();
        adapter = new HelpAdapter(getContext(), lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = helpRecycler.getChildAdapterPosition(view);
                if (lists.get(position - 1).getUrl() != null && lists.get(position - 1).getUrl().length() != 0) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url", lists.get(position - 1).getUrl());
                    intent.putExtra("title", "");
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        helpRecycler.setAdapter(adapter);
        presenter.getDocumentations();
    }

    @Override
    protected void initView(View view) {
        helpRecycler = view.findViewById(R.id.help_recycler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_fragment;
    }

    /**
     * {@link MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETHELP) {
            lists.clear();
            lists.addAll((List<HelpBean>) message.obj);
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
