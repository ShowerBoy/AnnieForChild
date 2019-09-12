package com.annie.annieforchild.ui.fragment.song;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.book.Page;
import com.annie.annieforchild.interactor.imp.CrashHandlerInteractorImp;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.PhotoActivity;
import com.annie.annieforchild.ui.adapter.Exercise_newAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/10/15.
 */

public class ExerciseFragment extends BaseFragment implements SongView, View.OnClickListener {
    private ImageView pageImage;
    private XRecyclerView exerciseList;
    private Page page;
    private List<Line> lists;
    private Exercise_newAdapter adapter;
    private AlertHelper helper;
    private Dialog dialog;
    private GrindEarPresenter presenter;
    private MediaPlayer mediaPlayer;
    private int tag, audioType, audioSource, bookId, homeworkid, homeworktype;
    private String title;
    private CrashHandlerInteractorImp interactor;
    {
        setRegister(true);
    }

    public ExerciseFragment() {

    }

    @Override
    protected void initData() {
        interactor = new CrashHandlerInteractorImp(null,1);
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        lists = new ArrayList<>();
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getInt("tag");
            page = (Page) bundle.getSerializable("page");
            audioType = bundle.getInt("audioType");
            audioSource = bundle.getInt("audioSource");
            bookId = bundle.getInt("bookId");
            title = bundle.getString("title");
            homeworkid = bundle.getInt("homeworkid", 0);
            homeworktype = bundle.getInt("homeworktype", -1);
        }
        Glide.with(getContext()).load(page.getPageImage()).placeholder(R.drawable.book_image_loading).error(R.drawable.book_image_loadfail).fitCenter().into(pageImage);
        lists.addAll(page.getLineContent());

        adapter = new Exercise_newAdapter(interactor,getActivity(), getContext(), this, title, lists, bookId, presenter, audioType, audioSource, page.getPageImage(), 1, homeworkid, homeworktype, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int positon = exerciseList.getChildAdapterPosition(view);
                if (lists.get(positon - 1).isSelect()) {
                    lists.get(positon - 1).setSelect(false);
                } else {
                    for (int i = 0; i < lists.size(); i++) {
                        lists.get(i).setSelect(false);
                    }
                    lists.get(positon - 1).setSelect(true);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        exerciseList.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        pageImage = view.findViewById(R.id.exercise_image);
        exerciseList = view.findViewById(R.id.exercise_recycler);
        pageImage.setOnClickListener(this);

        RecyclerLinearLayoutManager manager = new RecyclerLinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setScrollEnabled(false);
        exerciseList.setLayoutManager(manager);
        exerciseList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        exerciseList.setPullRefreshEnabled(false);
        exerciseList.setLoadingMoreEnabled(false);
        exerciseList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise_fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_image:
                Intent intent = new Intent(getContext(), PhotoActivity.class);
                intent.putExtra("url", page.getPageImage());
                intent.putExtra("delete", "0");
                startActivity(intent);
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            AudioBean bean = (AudioBean) message.obj;
            if (page.getPage() == bean.getPage()) {
                page.getLineContent().get(bean.getLineId() - 1).setMyResourceUrl(bean.getResourceUrl());
                refresh();
            }
        } else if (message.what == MethodCode.EVENT_MUSICPLAY) {
            int sss = (int) message.obj;
            try {
                if (adapter.getMediaPlayer() != null) {
                    if (adapter.getMediaPlayer().isPlaying()) {
                        adapter.getMediaPlayer().pause();
                        adapter.getMediaPlayer().stop();
                        adapter.getMediaPlayer().seekTo(0);
                    }
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            adapter.stopRecordPlay();
        }
    }

    private void refresh() {
        lists.clear();
        lists.addAll(page.getLineContent());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.show();
            }
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.stopMedia();
            adapter.stopRecord();
            adapter.stopRecordPlay();
        }
    }

}
