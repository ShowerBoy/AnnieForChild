package com.annie.annieforchild.ui.fragment.song;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
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
    private int classId;

    {
        setRegister(true);
    }

    public ListenSongFragment() {
    }

    public static ListenSongFragment instance(int classId) {
        ListenSongFragment fragment = new ListenSongFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("classId", classId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        bundle = getArguments();
        songsList = new ArrayList<>();
        classId = bundle.getInt("classId");
        songAdapter = new SongAdapter(getContext(), songsList);
        initRecycler();
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        presenter.getMusicList(classId);
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
        if (message.what == MethodCode.EVENT_GETMUSICLIST + 10000 + classId) {
            songsList.clear();
            songsList.addAll((List<Song>) message.obj);
            songAdapter.notifyDataSetChanged();
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
