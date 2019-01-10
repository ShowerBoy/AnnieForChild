package com.annie.annieforchild.ui.fragment.recording;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.record.Record;
import com.annie.annieforchild.bean.record.RecordBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.MyReleaseAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的发布
 * Created by wanglei on 2019/1/2.
 */

public class MyReleaseFragment extends BaseFragment implements SongView {
    private XRecyclerView recycler;
    private ImageView empty;
    private GrindEarPresenter presenter;
    private MyReleaseAdapter adapter;
    private RecordBean recordBean;
    private List<Record> lists;
    private AlertHelper helper;
    private Dialog dialog;
    private int tag, page = 1, totalPage, cancelPosition;

    {
        setRegister(true);
    }

    public static MyReleaseFragment instance(int tag) {
        MyReleaseFragment fragment = new MyReleaseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        lists = new ArrayList<>();
        Bundle bundle = getArguments();
        tag = bundle.getInt("tag");
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        adapter = new MyReleaseAdapter(getContext(), lists, tag, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
//                if (adapter.isPlay()) {
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_STOPPLAY;
                message.obj = 0;
                EventBus.getDefault().post(message);
//                }
                int position = recycler.getChildAdapterPosition(view);
                Intent intent = new Intent(getContext(), PracticeActivity.class);
                Song song = new Song();
                song.setBookId(lists.get(position - 1).getRecordingId());
                song.setBookImageUrl(lists.get(position - 1).getImageUrl());
                song.setBookName(lists.get(position - 1).getTitle());
                if (lists.get(position - 1).getAudioType() == 0) {
                    song.setIsmoerduo(0);
                    song.setIsyuedu(1);
                    song.setIskouyu(1);
                } else if (lists.get(position - 1).getAudioType() == 1) {
                    song.setIsmoerduo(0);
                    song.setIsyuedu(1);
                    song.setIskouyu(0);
                } else {
                    song.setIsmoerduo(0);
                    song.setIsyuedu(0);
                    song.setIskouyu(1);
                }
                if (tag == 1) {
                    intent.putExtra("song", song);
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 1);
                    intent.putExtra("audioSource", lists.get(position - 1).getAudioSource());
                    intent.putExtra("collectType", 2);
                    intent.putExtra("bookType", 1);
                } else {
                    intent.putExtra("song", song);
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 0);
                    intent.putExtra("audioSource", lists.get(position - 1).getAudioSource());
                    intent.putExtra("collectType", 1);
                    intent.putExtra("bookType", 0);
                }
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                if (tag == 1) {
                    SystemUtils.GeneralDialog(getContext(), "取消发布").setMessage("是否取消发布？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPosition = position - 1;
                            presenter.cancelRelease(lists.get(position - 1).getRecordingId(), tag);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                } else {
                    SystemUtils.GeneralDialog(getContext(), "删除").setMessage("是否删除该录音？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPosition = position - 1;
                            presenter.deleteRecording(lists.get(position - 1).getRecordingId(), lists.get(position - 1).getOrigin(), tag);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        });
        recycler.setAdapter(adapter);
        presenter.myRecordings(tag, page);
    }

    @Override
    protected void initView(View view) {
        recycler = view.findViewById(R.id.my_release_recycler);
        empty = view.findViewById(R.id.my_release_empty);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.setNestedScrollingEnabled(false);
        recycler.setLoadingMoreProgressStyle(10);
        recycler.setPullRefreshEnabled(true);
        recycler.setLoadingMoreEnabled(true);
        recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (adapter.isPlay()) {
                    recycler.refreshComplete();
                    return;
                }
                page = 1;
                presenter.myRecordings(tag, page);
            }

            @Override
            public void onLoadMore() {
                if (adapter.isPlay()) {
                    recycler.loadMoreComplete();
                    return;
                }
                page++;
                if (page > totalPage) {
                    page--;
                    showInfo("没有更多了");
                    recycler.loadMoreComplete();
                } else {
                    presenter.myRecordings(tag, page);
                }
            }
        });
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYRECORDINGS + 10000 + tag) {
            if (page == 1) {
                lists.clear();
                recycler.refreshComplete();
            } else {
                recycler.loadMoreComplete();
            }
            recordBean = (RecordBean) message.obj;
            if (recordBean != null) {
                if (recordBean.getList() != null && recordBean.getList().size() != 0) {
                    empty.setVisibility(View.GONE);
//                    recycler.setVisibility(View.VISIBLE);
                    totalPage = recordBean.getTotalPage();
                    lists.addAll(recordBean.getList());
                    adapter.notifyDataSetChanged();
                } else {
                    empty.setVisibility(View.VISIBLE);
//                    recycler.setVisibility(View.GONE);
                }
            } else {
                empty.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
            }
        } else if (message.what == MethodCode.EVENT_DELETERECORDING + 30000 + tag) {
            showInfo((String) message.obj);
            lists.remove(cancelPosition);
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_CANCELRELEASE + 20000 + tag) {
            showInfo((String) message.obj);
            lists.remove(cancelPosition);
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_STOPPLAY) {
            if (adapter.isPlay()) {
                adapter.stopAudio();
                adapter.setPlay(false);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_release_fragment;
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

    @Override
    public void onDestroy() {
        if (adapter != null) {
            adapter.releaseAudio();
        }
        super.onDestroy();
    }
}

