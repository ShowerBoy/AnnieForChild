package com.annie.annieforchild.ui.fragment.song;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.AnimationData;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.VideoActivity_new;
import com.annie.annieforchild.ui.adapter.AnimationAdapter;
import com.annie.annieforchild.ui.adapter.SongAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wanglei on 2018/6/14.
 */

public class AnimationFragment extends BaseFragment implements SongView {
    private XRecyclerView animationRecycler;
    private Bundle bundle;
    private List<AnimationData> lists;
    private AnimationAdapter adapter;
    private GrindEarPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private String title;
    private int classId, audioType, audioSource, type, position;
    private int sourceType; //收藏 1:磨耳朵 2：阅读 3：口语

    {
        setRegister(true);
    }

    public AnimationFragment() {
    }

    public static AnimationFragment instance(String title, int classId, int audioType, int audioSource, int type, int position) {
        AnimationFragment fragment = new AnimationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("classId", classId);
        bundle.putInt("audioType", audioType);
        bundle.putInt("audioSource", audioSource);
        bundle.putInt("type", type);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initData() {
        bundle = getArguments();
        lists = new ArrayList<>();
        title = bundle.getString("title");
        classId = bundle.getInt("classId");
        audioType = bundle.getInt("audioType");
        audioSource = bundle.getInt("audioSource");
        type = bundle.getInt("type");
        position = bundle.getInt("position");
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        adapter = new AnimationAdapter(getContext(), lists, 1, 100, classId, presenter, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = animationRecycler.getChildAdapterPosition(view);
                List<VideoList> list = new ArrayList<>();
                for (int j = 0; j < lists.size(); j++) {
                    VideoList videoList = new VideoList();
                    videoList.setTitle(lists.get(j).getAnimationName());
                    videoList.setPicurl(lists.get(j).getAnimationImageUrl());
                    videoList.setUrl(lists.get(j).getAnimationUrl());
                    list.add(videoList);
                }
                Intent intent = new Intent(getContext(), VideoActivity_new.class);
                intent.putExtra("isTime", true);
                intent.putExtra("isDefinition", false);

                intent.putExtra("animationId", lists.get(position - 1).getAnimationId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoList", (Serializable) list);
                bundle.putInt("videoPos", position - 1);
                intent.putExtras(bundle);


                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        initRecycler();
        if (position == 0) {
            presenter.getAnimationList(title, classId);
        }

    }

    @Override
    protected void initView(View view) {
        animationRecycler = view.findViewById(R.id.animation_recycler);
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        animationRecycler.setLayoutManager(layoutManager);
        animationRecycler.setPullRefreshEnabled(true);
        animationRecycler.setLoadingMoreEnabled(false);
        animationRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.getAnimationList(title, classId);
            }

            @Override
            public void onLoadMore() {

            }
        });
        animationRecycler.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_animation_fragment;
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETANIMATIONLIST + 7000 + classId) {
            animationRecycler.refreshComplete();
            lists.clear();
            lists.addAll((List<AnimationData>) message.obj);
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_COLLECTCOURSE + 2000 + classId) {
            showInfo((String) message.obj);
            presenter.getAnimationList(title, classId);
        } else if (message.what == MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId) {
            showInfo((String) message.obj);
            presenter.getAnimationList(title, classId);
        } else if (message.what == MethodCode.EVENT_DATA) {
            String clsId = (String) message.obj;
            if (Integer.parseInt(clsId) == classId) {
                presenter.getAnimationList(title, classId);
            }
        }
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
