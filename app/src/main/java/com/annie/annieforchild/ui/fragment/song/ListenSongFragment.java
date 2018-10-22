package com.annie.annieforchild.ui.fragment.song;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.SongAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 听儿歌
 * Created by wanglei on 2018/4/4.
 */

public class ListenSongFragment extends BaseFragment implements SongView {
    private XRecyclerView recycler;
    private Bundle bundle;
    private List<Song> songsList;
    private SongAdapter songAdapter;
    private GrindEarPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private String title;
    private int classId, audioType, audioSource, type;
    private int collectType; //收藏 1:磨耳朵 2：阅读 3：口语

    {
        setRegister(true);
    }

    public ListenSongFragment() {

    }

    public static ListenSongFragment instance(int classId, String title, int audioType, int audioSource, int type) {
        ListenSongFragment fragment = new ListenSongFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("classId", classId);
        bundle.putString("title", title);
        bundle.putInt("audioType", audioType);
        bundle.putInt("audioSource", audioSource);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        bundle = getArguments();
        songsList = new ArrayList<>();
        classId = bundle.getInt("classId");
        title = bundle.getString("title");
        audioType = bundle.getInt("audioType");
        audioSource = bundle.getInt("audioSource");
        type = bundle.getInt("type");
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        if (type < 6) {
            collectType = 1;
        } else if (type == 11) {
            collectType = 3;
        } else {
            collectType = 2;
        }

        if (type == 3) {
//            presenter.getAnimationList(title);
        } else if (type == 11) {
            presenter.getSpokenList(classId);
            if (title.equals("动画口语")) {
                audioSource = 13;
            } else if (title.equals("日常口语")) {
                audioSource = 14;
            }
        } else {
            if (type < 6) {
                presenter.getMusicList(classId);
            } else {
                presenter.getReadList(classId);
            }
        }
        songAdapter = new SongAdapter(getContext(), songsList, presenter, collectType, classId, audioType, audioSource, type);
        initRecycler();
    }

    @Override
    protected void initView(View view) {
        recycler = view.findViewById(R.id.listen_song_recycler);
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recycler.setPullRefreshEnabled(false);
        recycler.setLoadingMoreEnabled(false);
        recycler.setAdapter(songAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_listen_song_fragment;
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMUSICLIST + 1000 + classId) {
            songsList.clear();
            songsList.addAll((List<Song>) message.obj);
            songAdapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_GETREADLIST + 6000 + classId) {
            songsList.clear();
            songsList.addAll((List<Song>) message.obj);
            songAdapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_COLLECTCOURSE + 2000 + classId) {
            showInfo((String) message.obj);
            if (type < 6) {
                presenter.getMusicList(classId);
            } else if (type == 11) {
                presenter.getSpokenList(classId);
            } else {
                presenter.getReadList(classId);
            }
        } else if (message.what == MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId) {
            showInfo((String) message.obj);
            if (type < 6) {
                presenter.getMusicList(classId);
            } else if (type == 11) {
                presenter.getSpokenList(classId);
            } else {
                presenter.getReadList(classId);
            }
        } else if (message.what == MethodCode.EVENT_JOINMATERIAL + 4000 + classId) {
            showInfo((String) message.obj);
            if (type < 6) {
                presenter.getMusicList(classId);
            } else if (type == 11) {
                presenter.getSpokenList(classId);
            } else {
                presenter.getReadList(classId);
            }
        } else if (message.what == MethodCode.EVENT_CANCELMATERIAL + 5000 + classId) {
            showInfo((String) message.obj);
            if (type < 6) {
                presenter.getMusicList(classId);
            } else if (type == 11) {
                presenter.getSpokenList(classId);
            } else {
                presenter.getReadList(classId);
            }
        } else if (message.what == MethodCode.EVENT_GETSPOKENLIST + 8000 + classId) {
            songsList.clear();
            songsList.addAll((List<Song>) message.obj);
            songAdapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_UNLOCKBOOK + 9000 + classId) {
            showInfo((String) message.obj);
            if (type < 6) {
                presenter.getMusicList(classId);
            } else if (type == 11) {
                presenter.getSpokenList(classId);
            } else {
                presenter.getReadList(classId);
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
