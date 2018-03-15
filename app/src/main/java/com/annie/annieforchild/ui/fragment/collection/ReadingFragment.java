package com.annie.annieforchild.ui.fragment.collection;

import android.content.DialogInterface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
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
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.CollectionView;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏——阅读
 * Created by WangLei on 2018/2/23 0023
 */

public class ReadingFragment extends BaseFragment implements CollectionView {
    private XRecyclerView readingRecycler;
    private CollectionPresenter presenter;
    private List<Collection> lists;
    private CollectionAdapter adapter;
    private final int type = 2;

    {
        setRegister(true);
    }

    public static ReadingFragment instance() {
        ReadingFragment fragment = new ReadingFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        presenter = new CollectionPresenterImp(getContext(), this);
        presenter.initViewAndData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        readingRecycler.setLayoutManager(layoutManager);
        readingRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        readingRecycler.setPullRefreshEnabled(true);
        readingRecycler.setLoadingMoreEnabled(false);
        readingRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        readingRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.getMyCollections(type);
            }

            @Override
            public void onLoadMore() {

            }
        });
        lists = new ArrayList<>();
        adapter = new CollectionAdapter(getContext(), lists, type, new OnMyItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SystemUtils.GeneralDialog(getContext(), "取消收藏")
                        .setMessage("是否取消收藏？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                presenter.cancelCollection(type, lists.get(position).getCourseId());
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
        readingRecycler.setAdapter(adapter);
        presenter.getMyCollections(type);
    }

    @Override
    protected void initView(View view) {
        readingRecycler = view.findViewById(R.id.collection_reading_recycler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reading_fragment;
    }

    /**
     * {@link CollectionPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYCOLLECTIONS2) {
            lists.clear();
            lists.addAll((List<Collection>) message.obj);
            adapter.notifyDataSetChanged();
            readingRecycler.refreshComplete();
        } else if (message.what == MethodCode.EVENT_CANCELCOLLECTION2) {
            showInfo((String) message.obj);
            readingRecycler.refresh();
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
