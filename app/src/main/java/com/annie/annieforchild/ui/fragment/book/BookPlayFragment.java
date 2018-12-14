package com.annie.annieforchild.ui.fragment.book;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.pcm2mp3.RecorderAndPlayUtil;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.Utils.views.photoview.PhotoView;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.book.Page;
import com.annie.annieforchild.bean.book.ReleaseUrl;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.PhotoActivity;
import com.annie.annieforchild.ui.activity.pk.BookPlayActivity2;
import com.annie.annieforchild.ui.adapter.ExerciseAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.example.lamemp3.MP3Recorder;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by wanglei on 2018/10/9.
 */

public class BookPlayFragment extends BaseFragment implements View.OnClickListener, SongView, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private int tag;
    private Page page;
    private LinearLayout previewLayout, recordLayout, playLayout;
    private ImageView pageImage, preview, record, playRecord, animationPic;
    private XRecyclerView exerciseList;
    private List<Line> lists;
    private static final String DIR = "LAME/mp3/";
    private ExerciseAdapter adapter;
    private GrindEarPresenter presenter;
    private MediaPlayer mediaPlayer, mediaPlayer2;
    private RecorderAndPlayUtil mRecorderUtil = null;
    private boolean isClick = true;
    private Handler handler = new Handler();
    private Runnable runnable;
    public static boolean isRecord = false;
    public static boolean isPlay = false; //是否播放录音
    private int record_time = 0; //录音时长
    private int currentSign = 0, totalSign, duration;
    private int audioType, audioSource, bookId;
    private AlertHelper helper;
    private Dialog dialog;
    private String title, imageUrl;
    private int animationCode, homeworkid;
    private Random random;

    {
        setRegister(true);
    }

    public BookPlayFragment() {

    }

    @Override
    protected void initData() {
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        random = new Random();
        lists = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        mediaPlayer2 = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer2.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer2.setOnCompletionListener(this);
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
            imageUrl = bundle.getString("imageUrl");
            homeworkid = bundle.getInt("homeworkid");
        }
        Glide.with(getContext()).load(page.getPageImage()).placeholder(R.drawable.book_image_loading).error(R.drawable.book_image_loadfail).fitCenter().into(pageImage);
        lists.addAll(page.getLineContent());
        if (page.getMyResourceUrl() != null && page.getMyResourceUrl().length() != 0) {
            playRecord.setImageResource(R.drawable.icon_book_play2);
        } else {
            playRecord.setImageResource(R.drawable.icon_book_play2_f);
        }
        animationCode = page.getAnimationCode();
        initAnimation(animationCode);
        totalSign = lists.size();
        adapter = new ExerciseAdapter(getContext(), this, "", lists, 0, presenter, 0, 0, "", 0, homeworkid, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
//                if (isPlay) {
//                    return;
//                }
//                if (isRecord) {
//                    return;
//                }
//                if (SystemUtils.playAll) {
//                    return;
//                }
//                if (SystemUtils.isCurrentPage) {
//                    //如果是当前页在播放
//                    try {
//                        if (mediaPlayer.isPlaying()) {
//                            mediaPlayer.pause();
//                            mediaPlayer.stop();
//                            mediaPlayer.seekTo(0);
//                        }
//                    } catch (IllegalStateException e) {
//                        e.printStackTrace();
//                    }
//                    for (int i = 0; i < lists.size(); i++) {
//                        lists.get(i).setSelect(false);
//                    }
//                    SystemUtils.isCurrentPage = false;
//                    SystemUtils.isPlaying = false;
//                    BookPlayActivity2.viewPager.setNoFocus(false);
//                    adapter.notifyDataSetChanged();
//                    isClick = true;
//                    return;
//                }
//                currentSign = 0;
////                    int positon = exerciseList.getChildAdapterPosition(view);
//                for (int i = 0; i < lists.size(); i++) {
//                    lists.get(i).setSelect(false);
//                }
//                lists.get(currentSign).setSelect(true);
//                try {
//                    if (mediaPlayer.isPlaying()) {
//                        mediaPlayer.pause();
//                        mediaPlayer.stop();
//                        mediaPlayer.seekTo(0);
//                    }
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                }
//                isClick = false;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        playUrl(lists.get(currentSign).getResourceUrl());
//                    }
//                }).start();
//                BookPlayActivity2.viewPager.setNoFocus(true);
//                SystemUtils.isPlaying = true;
//                SystemUtils.isCurrentPage = true;
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        exerciseList.setAdapter(adapter);
        mRecorderUtil = new RecorderAndPlayUtil(DIR);
        mRecorderUtil.getRecorder().setHandle(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MP3Recorder.MSG_REC_STARTED:
                        // 开始录音
                        break;
                    case MP3Recorder.MSG_REC_STOPPED:
                        // 停止录音
//                        if (mIsSendVoice) {// 是否发送录音
//                            mIsSendVoice = false;
//                            audioRecordFinishListener.onFinish(mSecond, mRecorderUtil.getRecorderPath());
//                        }
//                        showInfo(mRecorderUtil.getRecorderPath());
                        break;
                    case MP3Recorder.MSG_ERROR_GET_MIN_BUFFERSIZE:
                        initRecording();
                        showInfo("采样率手机不支持");
                        break;
                    case MP3Recorder.MSG_ERROR_CREATE_FILE:
                        initRecording();
                        showInfo("创建音频文件出错");
                        break;
                    case MP3Recorder.MSG_ERROR_REC_START:
                        initRecording();
                        showInfo("初始化录音器出错");
                        break;
                    case MP3Recorder.MSG_ERROR_AUDIO_RECORD:
                        initRecording();
                        showInfo("录音的时候出错");
                        break;
                    case MP3Recorder.MSG_ERROR_AUDIO_ENCODE:
                        initRecording();
                        showInfo("编码出错");
                        break;
                    case MP3Recorder.MSG_ERROR_WRITE_FILE:
                        initRecording();
                        showInfo("文件写入出错");
                        break;
                    case MP3Recorder.MSG_ERROR_CLOSE_FILE:
                        initRecording();
                        showInfo("文件流关闭出错");
                        break;
                }
            }
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isRecord) {
                    record_time++;
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    private void initAnimation(int animationCode) {
        switch (animationCode) {
            case 0:
                animationPic.setImageResource(R.drawable.icon_book_bee);
                break;
            case 1:
                animationPic.setImageResource(R.drawable.icon_animation_amazing);
                break;
            case 2:
                animationPic.setImageResource(R.drawable.icon_animation_awesome);
                break;
            case 3:
                animationPic.setImageResource(R.drawable.icon_animation_bingo);
                break;
            case 4:
                animationPic.setImageResource(R.drawable.icon_animation_excellent);
                break;
            case 5:
                animationPic.setImageResource(R.drawable.icon_animation_good_observation);
                break;
            case 6:
                animationPic.setImageResource(R.drawable.icon_animation_good_try);
                break;
            case 7:
                animationPic.setImageResource(R.drawable.icon_animation_great);
                break;
            case 8:
                animationPic.setImageResource(R.drawable.icon_animation_great_job);
                break;
            case 9:
                animationPic.setImageResource(R.drawable.icon_animation_nice_going);
                break;
            case 10:
                animationPic.setImageResource(R.drawable.icon_animation_super);
                break;
            default:
                animationPic.setImageResource(R.drawable.icon_book_bee);
                break;
        }
    }

    @Override
    protected void initView(View view) {
        exerciseList = view.findViewById(R.id.book_play_list2);
        pageImage = view.findViewById(R.id.book_play_image2);
//        record = view.findViewById(R.id.bookplay_record);
//        playRecord = view.findViewById(R.id.bookplay_play_record);
        preview = view.findViewById(R.id.book_play_preview);
        record = view.findViewById(R.id.book_play_record);
        playRecord = view.findViewById(R.id.book_play_play);
        animationPic = view.findViewById(R.id.animation_pic);
        previewLayout = view.findViewById(R.id.book_preview_layout);
        recordLayout = view.findViewById(R.id.book_record_layout);
        playLayout = view.findViewById(R.id.book_play_layout);

        pageImage.setOnClickListener(this);
        animationPic.setOnClickListener(this);
        previewLayout.setOnClickListener(this);
        recordLayout.setOnClickListener(this);
        playLayout.setOnClickListener(this);

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
        return R.layout.activity_book_play_fragment;
    }

    private void playUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void playBook() {
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).setSelect(false);
        }
        lists.get(SystemUtils.currentLine).setSelect(true);
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.stop();
                mediaPlayer.seekTo(0);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                playUrl(lists.get(SystemUtils.currentLine).getResourceUrl());
            }
        }).start();
        adapter.notifyDataSetChanged();
    }

    private void initRecording() {
        mRecorderUtil.stopRecording();
        mRecorderUtil.getRecorderPath();
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

    /**
     * {@link BookPlayActivity2#onClick(View)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_PLAYING) {
            if (mediaPlayer != null) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        mediaPlayer.stop();
                        mediaPlayer.seekTo(0);
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                isClick = true;
            }
            for (int i = 0; i < lists.size(); i++) {
                lists.get(i).setSelect(false);
            }
            SystemUtils.currentLine = 0;
            SystemUtils.currentPage = 0;
            SystemUtils.playAll = false;
            BookPlayActivity2.viewPager.setNoFocus(false);
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            AudioBean bean = (AudioBean) message.obj;
            if (bean.getPage() == page.getPage()) {
                page.setMyResourceUrl(bean.getResourceUrl());
                page.setAnimationCode(animationCode);
                refresh(bean.getResourceUrl());

                BookPlayActivity2.setClarity(true);
                BookPlayActivity2.setLottieAnimation(animationCode, page.getPage());

//                final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
//                在整个视图中添加一个颜色过滤器
//                animationView.addColorFilter(colorFilter);
//                SystemUtils.setBackGray(getActivity(), true);
            }
        } else if (message.what == MethodCode.EVENT_PAGEPLAY) {
            if (mediaPlayer2 != null) {
                try {
                    if (mediaPlayer2.isPlaying()) {
                        mediaPlayer2.pause();
                        mediaPlayer2.stop();
                        mediaPlayer2.seekTo(0);
                        isPlay = false;
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        } else if (message.what == MethodCode.EVENT_GONE) {
            int g = (int) message.obj;
            if (g == page.getPage()) {
                animationPic.setVisibility(View.INVISIBLE);
            }
        } else if (message.what == MethodCode.EVENT_VISIBILITY) {
            int j = (int) message.obj;
            if (j == page.getPage()) {
                animationPic.setVisibility(View.VISIBLE);
                initAnimation(animationCode);
            }
        }
    }

    public void playAll() {
        if (SystemUtils.playAll) {
            if (SystemUtils.currentPage == tag) {
                playBook();
            }
        }
    }

    private void refresh(String resourceUrl) {
        playRecord.setImageResource(R.drawable.icon_book_play2);
        ReleaseUrl releaseUrl = BookPlayActivity2.releaseList.get(tag);
        releaseUrl.setTag(true);
        releaseUrl.getNectarList().add(0);
        releaseUrl.setMyResourseUrl(resourceUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_play_image2:
                Intent intent = new Intent(getContext(), PhotoActivity.class);
                intent.putExtra("url", page.getPageImage());
                intent.putExtra("delete", "0");
                startActivity(intent);
                break;
            case R.id.book_preview_layout:
                if (isPlay) {
                    return;
                }
                if (isRecord) {
                    return;
                }
                if (SystemUtils.playAll) {
                    return;
                }
                if (SystemUtils.isCurrentPage) {
                    //如果是当前页在播放
                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            mediaPlayer.stop();
                            mediaPlayer.seekTo(0);
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < lists.size(); i++) {
                        lists.get(i).setSelect(false);
                    }
                    SystemUtils.isCurrentPage = false;
                    SystemUtils.isPlaying = false;
                    BookPlayActivity2.viewPager.setNoFocus(false);
                    preview.setImageResource(R.drawable.icon_book_preview2);
                    adapter.notifyDataSetChanged();
                    isClick = true;
                    return;
                }
                currentSign = 0;
//                    int positon = exerciseList.getChildAdapterPosition(view);
                for (int i = 0; i < lists.size(); i++) {
                    lists.get(i).setSelect(false);
                }
                lists.get(currentSign).setSelect(true);
//                    if (isBookPlay) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        mediaPlayer.stop();
                        mediaPlayer.seekTo(0);
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
//                    }
//                    isBookPlay = true;
                isClick = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        playUrl(lists.get(currentSign).getResourceUrl());
                    }
                }).start();
                BookPlayActivity2.viewPager.setNoFocus(true);
                SystemUtils.isPlaying = true;
                SystemUtils.isCurrentPage = true;
                preview.setImageResource(R.drawable.icon_book_stop);
                adapter.notifyDataSetChanged();
                break;
            case R.id.book_record_layout:
                if (isClick) {
                    if (!SystemUtils.isPlaying) {
                        if (!SystemUtils.isCurrentPage) {
                            if (!SystemUtils.playAll) {
                                if (!isPlay) {
                                    if (isRecord) {
//                                        showInfo("录音结束");
                                        BookPlayActivity2.viewPager.setNoFocus(false);
                                        isRecord = false;
                                        record.setImageResource(R.drawable.icon_book_record2);
                                        initRecording();
                                        mRecorderUtil.stopRecording();
                                        if (record_time <= 0) {
                                            showInfo("时长不能为零");
                                            break;
                                        }
                                        showLoad();
                                        animationCode = random.nextInt(10) + 1;
                                        //延迟1秒上传
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                presenter.uploadAudioResource(bookId, page.getPage(), audioType, audioSource, 0, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + title + ".mp3", 0f, title + "(分页)", record_time, 4, "", imageUrl, animationCode, homeworkid);
                                            }
                                        }, 1000);
                                    } else {
                                        showInfo("录音开始");
                                        BookPlayActivity2.viewPager.setNoFocus(true);
                                        isRecord = true;
                                        record_time = 0;
                                        handler.postDelayed(runnable, 1000);
                                        record.setImageResource(R.drawable.icon_book_stop);
                                        mRecorderUtil.startRecording(title);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.book_play_layout:
                if (isClick) {
                    if (!SystemUtils.isPlaying) {
                        if (!SystemUtils.isCurrentPage) {
                            if (!SystemUtils.playAll) {
                                if (!isRecord) {
                                    if (isPlay) {
                                        playRecord.setImageResource(R.drawable.icon_book_play2);
                                        try {
                                            mediaPlayer2.pause();
                                            mediaPlayer2.stop();
                                            mediaPlayer2.seekTo(0);
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        }
                                        isPlay = false;
                                    } else {
                                        if (page.getMyResourceUrl() != null && page.getMyResourceUrl().length() != 0) {
                                            isPlay = true;
                                            playRecord.setImageResource(R.drawable.icon_book_stop);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    playUrl2(page.getMyResourceUrl());
                                                }
                                            }).start();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.animation_pic:
                switch (animationCode) {
                    case 1:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    case 2:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    case 3:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    case 4:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    case 5:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    case 6:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    case 7:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    case 8:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    case 9:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    case 10:
                        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void playUrl2(String url) {
        if (mediaPlayer2 == null) {
            mediaPlayer2 = new MediaPlayer();
            mediaPlayer2.setOnPreparedListener(this);
            mediaPlayer2.setOnCompletionListener(this);
        }
        try {
            mediaPlayer2.reset();
            mediaPlayer2.setDataSource(url);
            mediaPlayer2.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            isClick = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            isClick = true;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp == mediaPlayer) {
            presenter.uploadAudioTime(3, audioType, audioSource, bookId, duration);
            if (SystemUtils.playAll) {
                SystemUtils.currentLine++;
                if (SystemUtils.currentLine < lists.size()) {
                    //换行不翻页
                    playBook();
                } else {
                    //翻页
                    for (int i = 0; i < lists.size(); i++) {
                        lists.get(i).setSelect(false);
                    }
                    SystemUtils.currentLine = 0;
                    SystemUtils.currentPage++;
                    if (SystemUtils.currentPage < SystemUtils.totalPage) {
                        ((BookPlayActivity2) getActivity()).scrolltoPosition(SystemUtils.currentPage);
                    } else {
//                    showInfo("结束");
                        SystemUtils.currentPage = 0;
                        SystemUtils.currentLine = 0;
                        SystemUtils.playAll = false;
                        BookPlayActivity2.viewPager.setNoFocus(false);
//                        BookPlayActivity2.playTotal.setText("全文播放");
                        BookPlayActivity2.playTotal2.setImageResource(R.drawable.icon_full_reading_f);
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                if (SystemUtils.isCurrentPage) {
                    for (int i = 0; i < lists.size(); i++) {
                        lists.get(i).setSelect(false);
                    }
                    currentSign++;
                    if (currentSign < totalSign) {
                        //下一行
                        lists.get(currentSign).setSelect(true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                playUrl(lists.get(currentSign).getResourceUrl());
                            }
                        }).start();
                    } else {
                        //当前页播放结束
                        currentSign = 0;
                        isClick = true;
                        SystemUtils.isPlaying = false;
                        SystemUtils.isCurrentPage = false;
                        preview.setImageResource(R.drawable.icon_book_preview2);
                        BookPlayActivity2.viewPager.setNoFocus(false);
                    }
                } else {
                    for (int i = 0; i < lists.size(); i++) {
                        lists.get(i).setSelect(false);
                    }
                    BookPlayActivity2.viewPager.setNoFocus(false);
                }
                adapter.notifyDataSetChanged();
            }
        } else {
            isClick = true;
            isPlay = false;
            playRecord.setImageResource(R.drawable.icon_book_play2);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mp == mediaPlayer) {
            duration = mediaPlayer.getDuration() / 1000;
            mediaPlayer.start();
        } else {
            mediaPlayer2.start();
        }
//        isBookPlay = true;
//        isClick = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isRecord) {
            initRecording();
            mRecorderUtil.stopRecording();
            isRecord = false;
            record_time = 0;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.pause();
                mediaPlayer.stop();
                mediaPlayer.seekTo(0);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mediaPlayer.release();
            mediaPlayer = null;
            isClick = true;
        }
        if (mediaPlayer2 != null) {
            try {
                mediaPlayer2.pause();
                mediaPlayer2.stop();
                mediaPlayer2.seekTo(0);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mediaPlayer2.release();
            mediaPlayer2 = null;
            isClick = true;
        }
//        SystemUtils.playAll = false;
        SystemUtils.isCurrentPage = false;
        preview.setImageResource(R.drawable.icon_book_preview2);
    }
}
