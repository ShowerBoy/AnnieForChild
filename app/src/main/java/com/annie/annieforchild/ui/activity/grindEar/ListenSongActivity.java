package com.annie.annieforchild.ui.activity.grindEar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.SongAdapter;
import com.annie.annieforchild.ui.adapter.SongClassifyAdapter;
import com.annie.annieforchild.ui.fragment.song.Mobao1Fragment;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 听儿歌
 * Created by WangLei on 2018/3/13 0013
 */

public class ListenSongActivity extends BaseActivity implements SongView, View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView back;
    private TextView listenTitle;
    private RecyclerView classifyRecycler;
    private XRecyclerView songXRecycler;
    private GrindEarPresenter presenter;
    private List<SongClassify> lists;
    private List<Song> songsList;
    private SongClassifyAdapter topAdapter;
    private SongAdapter songAdapter;
    private Intent intent;
    private Bundle bundle;
    private AlertHelper helper;
    private Dialog dialog;
    private int type;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_song;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.song_back);
        classifyRecycler = findViewById(R.id.song_classify_recycler);
        songXRecycler = findViewById(R.id.song_xrecycler);
        listenTitle = findViewById(R.id.listen_title);
        back.setOnClickListener(this);

        intent = getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
            type = bundle.getInt("type");
            if (type == 1) {
                listenTitle.setText("听儿歌");
                lists = (List<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
            } else if (type == 2) {
                listenTitle.setText("听诗歌");
                lists = (List<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
            } else if (type == 3) {
                listenTitle.setText("听对话");
                lists = (List<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
            } else if (type == 4) {
                listenTitle.setText("听绘本");
                lists = (List<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
            }
        }

        if (lists.size() != 0) {
            lists.get(0).setSelected(true);
        }
        topAdapter = new SongClassifyAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = classifyRecycler.getChildAdapterPosition(view);
                for (int i = 0; i < lists.size(); i++) {
                    lists.get(i).setSelected(false);
                }
                lists.get(position).setSelected(true);
                topAdapter.notifyDataSetChanged();
                presenter.getMusicList(lists.get(position).getCalssId());
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        classifyRecycler.setLayoutManager(manager);
        classifyRecycler.setAdapter(topAdapter);
    }

    @Override
    protected void initData() {
        songsList = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        songAdapter = new SongAdapter(this, songsList);
        initRecycler();
        presenter.getMusicList(lists.get(0).getCalssId());
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songXRecycler.setLayoutManager(layoutManager);
        songXRecycler.setPullRefreshEnabled(false);
        songXRecycler.setLoadingMoreEnabled(false);
        songXRecycler.setAdapter(songAdapter);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.song_back:
                finish();
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMUSICLIST) {
            songsList.clear();
            songsList.addAll((List<Song>) message.obj);
            songAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
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
