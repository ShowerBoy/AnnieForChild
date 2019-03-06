package com.annie.annieforchild.ui.fragment.book;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Release;
import com.annie.annieforchild.bean.book.ReleaseBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.activity.pk.BookPlayActivity2;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.activity.pk.ReleaseSuccessActivity;
import com.annie.annieforchild.ui.adapter.BookEndAdapter;
import com.annie.annieforchild.ui.adapter.GrindEarAdapter;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 发布页面
 * Created by wanglei on 2018/11/28.
 */

public class BookPlayEndFragment extends BaseFragment implements OnCheckDoubleClick, SongView, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private ImageView back, bookImage;
    private TextView name, close, playBack, recordBack, releaseBack, releasePeople;
    private CircleImageView headpic;
    private RecyclerView bookEndRecycler, recommendRecycler;
    private GrindEarPresenter presenter;
    private BookEndAdapter adapter;
    private List<ReleaseBean> lists;
    private GrindEarAdapter recommendAdapter;
    private List<Song> recommendList;
    private CheckDoubleClickListener listener;
    private List<String> urlList;
    private int bookId, audioType;
    private AlertHelper helper;
    private Dialog dialog;
    private Release release;
    private String imageUrl, bookName;
    private boolean isClick = true, isPlay = false;
    private int totalSize, currentSize;
    private MediaPlayer mediaPlayer;
    private Thread thread;
    private boolean threadOn_Off = true;
    private MyApplication application;

    {
        setRegister(true);
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public BookPlayEndFragment() {

    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        recommendList = new ArrayList<>();
        urlList = new ArrayList<>();
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        Bundle bundle = getArguments();
        if (bundle != null) {
            imageUrl = bundle.getString("imageUrl");
            bookId = bundle.getInt("bookId");
            bookName = bundle.getString("bookName");
            audioType = bundle.getInt("audioType", 1);
            Glide.with(getContext()).load(imageUrl).into(bookImage);
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        adapter = new BookEndAdapter(getContext(), lists, this, presenter, false);
        bookEndRecycler.setAdapter(adapter);
        recommendAdapter = new GrindEarAdapter(getContext(), recommendList, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recommendRecycler.getChildAdapterPosition(view);
                Intent intent = new Intent(getContext(), PracticeActivity.class);
                intent.putExtra("song", recommendList.get(position));
                intent.putExtra("type", 0);
                intent.putExtra("audioType", audioType);
                intent.putExtra("audioSource", 0);
                intent.putExtra("bookType", 1);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recommendRecycler.setAdapter(recommendAdapter);
        presenter.getRelease(bookId);
    }

    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.photo_back);
        bookImage = view.findViewById(R.id.book_end_image);
        headpic = view.findViewById(R.id.release_image);

        name = view.findViewById(R.id.release_name);
        close = view.findViewById(R.id.release_close);
        playBack = view.findViewById(R.id.play_back);
        recordBack = view.findViewById(R.id.record_back);
        releaseBack = view.findViewById(R.id.release_back);
        releasePeople = view.findViewById(R.id.release_people);
        bookEndRecycler = view.findViewById(R.id.book_end_recycler);
        recommendRecycler = view.findViewById(R.id.recommend_book_end_recycler);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        close.setOnClickListener(listener);
        playBack.setOnClickListener(listener);
        recordBack.setOnClickListener(listener);
        releaseBack.setOnClickListener(listener);
        Glide.with(getContext()).load(application.getSystemUtils().getUserInfo().getAvatar()).into(headpic);
        name.setText(application.getSystemUtils().getUserInfo().getName());
        RecyclerLinearLayoutManager layoutManager = new RecyclerLinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        bookEndRecycler.setLayoutManager(layoutManager);

        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 3);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recommendRecycler.setLayoutManager(layoutManager2);
        recommendRecycler.setNestedScrollingEnabled(false);

        back.setFocusable(true);
        back.requestFocus();
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETRELEASE) {
            release = (Release) message.obj;
            releasePeople.setText("已有" + release.getRecordCount() + "人完成录制");
            if (release.getRecordList() != null) {
                lists.clear();
                lists.addAll(release.getRecordList());
                adapter.notifyDataSetChanged();
            }
            if (release.getRecommendList() != null) {
                recommendList.clear();
                recommendList.addAll(release.getRecommendList());
                recommendAdapter.notifyDataSetChanged();
            }
        } else if (message.what == MethodCode.EVENT_RELEASEBOOK) {
            int result = (int) message.obj;
            if (result == 1) {
                showInfo("发布成功!");
                Intent intent = new Intent(getContext(), ReleaseSuccessActivity.class);
                intent.putExtra("bookId", bookId);
                intent.putExtra("bookName", bookName);
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("audioType", audioType);
                startActivity(intent);
                getActivity().finish();
            } else {
                showInfo("发布失败!");
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_end_fragment;
    }

    private void playUrl(String url) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.photo_back:
                if (!adapter.isPlay()) {
                    BookPlayActivity2.viewPager.setCurrentItem(0);
                    BookPlayActivity2.viewPager.setNoFocus(false);
                    for (int i = 0; i < BookPlayActivity2.releaseList.size(); i++) {
                        BookPlayActivity2.releaseList.get(i).getNectarList().clear();
                    }
                }
                break;
            case R.id.release_close:
                getActivity().finish();
                break;
            case R.id.play_back:
                //回放
                if (!adapter.isPlay()) {
                    if (isPlay) {
                        try {
                            mediaPlayer.pause();
                            mediaPlayer.stop();
                            mediaPlayer.seekTo(0);
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                        threadOn_Off = false;
                        playBack.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getContext(), R.drawable.icon_play_playback), null, null);
                        isPlay = false;
                    } else {
                        urlList.clear();
                        for (int i = 0; i < BookPlayActivity2.releaseList.size(); i++) {
                            if (BookPlayActivity2.releaseList.get(i).getTag()) {
                                urlList.add(BookPlayActivity2.releaseList.get(i).getMyResourseUrl());
                            }
                        }
                        totalSize = urlList.size();
                        currentSize = 0;
                        if (totalSize != 0) {
                            threadOn_Off = true;
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    playUrl(urlList.get(currentSize));
                                }
                            });
                            thread.start();
                            playBack.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getContext(), R.drawable.icon_play_playback_stop), null, null);
                            isPlay = true;
                        } else {
                            showInfo("没有音频");
                        }
                    }
                }
                break;
            case R.id.record_back:
                if (!adapter.isPlay()) {
                    BookPlayActivity2.viewPager.setCurrentItem(0);
                    BookPlayActivity2.viewPager.setNoFocus(false);
                    for (int i = 0; i < BookPlayActivity2.releaseList.size(); i++) {
                        BookPlayActivity2.releaseList.get(i).getNectarList().clear();
                    }
                }
                break;
            case R.id.release_back:
                //发布
                boolean tag = true;
                for (int i = 0; i < BookPlayActivity2.releaseList.size(); i++) {
                    if (!BookPlayActivity2.releaseList.get(i).getTag()) {
                        tag = false;
                    }
                }
                if (tag) {
                    //发布
                    if (!adapter.isPlay()) {
                        presenter.ReleaseBook(bookId);
                    }
                } else {
                    showInfo("有页面缺少录音哦，请查检一下~");
                }
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (threadOn_Off) {
            currentSize++;
            if (currentSize < totalSize) {
                playUrl(urlList.get(currentSize));
            } else {
                isPlay = false;
                playBack.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getContext(), R.drawable.icon_play_playback), null, null);
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
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
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (adapter != null) {
            adapter.stopMedia();
        }
    }
}
