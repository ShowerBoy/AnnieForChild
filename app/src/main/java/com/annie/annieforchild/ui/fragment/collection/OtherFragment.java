package com.annie.annieforchild.ui.fragment.collection;

import android.content.DialogInterface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Collection;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.CollectionPresenter;
import com.annie.annieforchild.presenter.imp.CollectionPresenterImp;
import com.annie.annieforchild.ui.adapter.CollectionAdapter;
import com.annie.annieforchild.ui.interfaces.OnMyItemClickListener;
import com.annie.annieforchild.view.CollectionView;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/8/6.
 */

public class OtherFragment extends BaseFragment implements CollectionView {
    private XRecyclerView otherRecycler;
    private RelativeLayout emptyLayout;
    private CollectionPresenter presenter;
    private List<Collection> lists;
    private CollectionAdapter adapter;
    private final int type = 0;

    {
        setRegister(true);
    }

    public static OtherFragment instance() {
        OtherFragment fragment = new OtherFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        presenter = new CollectionPresenterImp(getContext(), this);
        presenter.initViewAndData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        otherRecycler.setLayoutManager(layoutManager);
        otherRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        otherRecycler.setPullRefreshEnabled(true);
        otherRecycler.setLoadingMoreEnabled(false);
        otherRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        otherRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.getMyCollections(type);
            }

            @Override
            public void onLoadMore() {

            }
        });
        lists = new ArrayList<>();
        adapter = new CollectionAdapter(getContext(), lists, type, 0, 1, 0, new OnMyItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SystemUtils.GeneralDialog(getContext(), "取消收藏")
                        .setMessage("是否取消收藏？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                presenter.cancelCollection(type, lists.get(position).getAudioSource(), lists.get(position).getCourseId());
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
        otherRecycler.setAdapter(adapter);
        presenter.getMyCollections(type);
    }

    @Override
    protected void initView(View view) {
        otherRecycler = view.findViewById(R.id.collection_other_recycler);
        emptyLayout = view.findViewById(R.id.other_empty_layout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYCOLLECTIONS0) {
            lists.clear();
            lists.addAll((List<Collection>) message.obj);
            adapter.notifyDataSetChanged();
            if (lists.size() == 0) {
                emptyLayout.setVisibility(View.VISIBLE);
            } else {
                emptyLayout.setVisibility(View.GONE);
            }
            otherRecycler.refreshComplete();
        } else if (message.what == MethodCode.EVENT_CANCELCOLLECTION0) {
            showInfo((String) message.obj);
            otherRecycler.refresh();
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
