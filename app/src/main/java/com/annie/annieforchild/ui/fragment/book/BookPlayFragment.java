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
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
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
import com.annie.annieforchild.ui.adapter.Exercise_newAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.example.lamemp3.MP3Recorder;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by wanglei on 2018/10/9.
 */

public class BookPlayFragment extends BaseFragment implements OnCheckDoubleClick, SongView, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private int tag;
    private Page page;
    private LinearLayout previewLayout, recordLayout, playLayout;
    private ImageView pageImage, preview, record, playRecord, animationPic;
    private XRecyclerView exerciseList;
    private List<Line> lists;
    private static final String DIR = "LAME/mp3/";
    private Exercise_newAdapter adapter;
    private GrindEarPresenter presenter;
    private MediaPlayer mediaPlayer, mediaPlayer2;
    private RecorderAndPlayUtil mRecorderUtil = null;
    private boolean isClick = true;
    private Handler handler = new Handler();
    private Runnable runnable, runnable2;
    public static boolean isRecord = false;
    public static boolean isPlay = false; //是否播放录音
    private int record_time = 0; //录音时长
    private int currentSign = 0, totalSign, duration = 0;
    private int audioType, audioSource, bookId, totalPage;
    private Timer mTimer;
    private TimerTask task;
    private AlertHelper helper;
    private Dialog dialog;
    private String title, imageUrl;
    private int animationCode, homeworkid, homeworktype;
    private CheckDoubleClickListener listener;
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
            totalPage = bundle.getInt("totalPage");
            audioType = bundle.getInt("audioType");
            audioSource = bundle.getInt("audioSource");
            bookId = bundle.getInt("bookId");
            title = bundle.getString("title").replace("\"", "").trim();
            imageUrl = bundle.getString("imageUrl");
            homeworkid = bundle.getInt("homeworkid", 0);
            homeworktype = bundle.getInt("homeworktype", -1);
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
        adapter = new Exercise_newAdapter(getActivity(), getContext(), this, "", lists, 0, presenter, 0, 0, "", 0, homeworkid, homeworktype, new OnRecyclerItemClickListener() {
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
//                        showInfo("录音的时候出错");
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
        runnable2 = new Runnable() {
            @Override
            public void run() {
                duration++;
                handler.postDelayed(this, 1000);
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
        listener = new CheckDoubleClickListener(this);
        pageImage.setOnClickListener(listener);
        animationPic.setOnClickListener(listener);
        previewLayout.setOnClickListener(listener);
        recordLayout.setOnClickListener(listener);
        playLayout.setOnClickListener(listener);

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
        lists.get(application.getSystemUtils().getCurrentLine()).setSelect(true);
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
                playUrl(lists.get(application.getSystemUtils().getCurrentLine()).getResourceUrl());
            }
        }).start();
        duration = 0;
        handler.postDelayed(runnable2, 1000);
        adapter.notifyDataSetChanged();
    }

    private void initRecording() {
        mRecorderUtil.stopRecording();
        mRecorderUtil.getRecorderPath();
    }

    @Override
    public void showInfo(String info) {
        if (mContext != null) {
            Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
        }
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

    /**
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
            application.getSystemUtils().setCurrentLine(0);
            application.getSystemUtils().setCurrentPage(0);
            application.getSystemUtils().setPlayAll(false);
            BookPlayActivity2.viewPager.setNoFocus(false);
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            AudioBean bean = (AudioBean) message.obj;
            if (bean.getPage() == page.getPage()) {
                page.setMyResourceUrl(bean.getResourceUrl());
                page.setAnimationCode(animationCode);
                refresh(bean.getResourceUrl(), bean);

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
                if (j == totalPage) {
                    if (application.getSystemUtils().isDrop()) {
                        application.getSystemUtils().setDrop(false);
                        /**
                         * {@link BookPlayActivity2#onMainEventThread(JTMessage)}
                         */
                        JTMessage message1 = new JTMessage();
                        message1.what = MethodCode.EVENT_ISDROP;
                        message1.obj = j;
                        EventBus.getDefault().post(message1);
                    }
                }
            }
        }
    }

    public void playAll() {
        if (application != null) {
            if (application.getSystemUtils() != null) {
                if (application.getSystemUtils().isPlayAll()) {
                    if (application.getSystemUtils().getCurrentPage() == tag) {
                        playBook();
                    }
                }
            }
        }
    }

    private void refresh(String resourceUrl, AudioBean bean) {
        //TODO:
        playRecord.setImageResource(R.drawable.icon_book_play2);
        if (bean.getIsNectar() == 0) {
            ReleaseUrl releaseUrl = BookPlayActivity2.releaseList.get(tag);
            releaseUrl.setTag(true);
            releaseUrl.getNectarList().add(bean.getNectarCount());
            releaseUrl.setMyResourseUrl(resourceUrl);
        } else if (bean.getIsNectar() == 1) {
            ReleaseUrl releaseUrl = BookPlayActivity2.releaseList.get(tag);
            releaseUrl.setTag(true);
            releaseUrl.setMyResourseUrl(resourceUrl);
        }

    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.book_play_image2:
                Intent intent = new Intent(getContext(), PhotoActivity.class);
                intent.putExtra("url", page.getPageImage());
                intent.putExtra("delete", "0");
                startActivity(intent);
                break;
            case R.id.book_preview_layout:
                if (isPlay) {
                    showInfo("播放中");
                    return;
                }
                if (isRecord) {
                    showInfo("录音中");
                    return;
                }
                if (application.getSystemUtils().isPlayAll()) {
                    return;
                }
                if (application.getSystemUtils().isCurrentPage()) {
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
                    application.getSystemUtils().setCurrentPage(false);
                    application.getSystemUtils().setPlaying(false);
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

                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        mediaPlayer.stop();
                        mediaPlayer.seekTo(0);
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }

                isClick = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        playUrl(lists.get(currentSign).getResourceUrl());
                    }
                }).start();
                BookPlayActivity2.viewPager.setNoFocus(true);
                application.getSystemUtils().setPlaying(true);
                application.getSystemUtils().setCurrentPage(true);
                preview.setImageResource(R.drawable.icon_book_stop);
                duration = 0;
                handler.postDelayed(runnable2, 1000);
                adapter.notifyDataSetChanged();
                break;
            case R.id.book_record_layout:
                if (isClick) {
                    if (!application.getSystemUtils().isPlaying()) {
                        if (!application.getSystemUtils().isCurrentPage()) {
                            if (!application.getSystemUtils().isPlayAll()) {
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
                                                presenter.uploadAudioResource(bookId, page.getPage(), audioType, audioSource, 0, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + title + ".mp3", 0f, title + "(分页)", record_time, 4, "", imageUrl, animationCode, homeworkid, homeworktype);
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
                                }else{
                                    showInfo("播放中");
                                }
                            }
                        }
                    } else {
                        showInfo("播放中");
                    }
                } else {
                    showInfo("播放中");
                }
                break;
            case R.id.book_play_layout:
                if (isClick) {
                    if (!application.getSystemUtils().isPlaying()) {
                        if (!application.getSystemUtils().isCurrentPage()) {
                            if (!application.getSystemUtils().isPlayAll()) {
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
                                } else {
                                    showInfo("录音中");
                                }
                            }
                        }
                    } else {
                        showInfo("播放中");
                    }
                } else {
                    showInfo("播放中");
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
            presenter.uploadAudioTime(4, audioType, audioSource, bookId, duration);
            if (handler != null && runnable2 != null) {
                handler.removeCallbacks(runnable2);
            }
            if (application.getSystemUtils().isPlayAll()) {
                application.getSystemUtils().setCurrentLine(application.getSystemUtils().getCurrentLine() + 1);
                if (application.getSystemUtils().getCurrentLine() < lists.size()) {
                    //换行不翻页
                    playBook();
                } else {
                    //翻页
                    for (int i = 0; i < lists.size(); i++) {
                        lists.get(i).setSelect(false);
                    }
                    application.getSystemUtils().setCurrentLine(0);
                    application.getSystemUtils().setCurrentPage(application.getSystemUtils().getCurrentPage() + 1);
                    if (application.getSystemUtils().getCurrentPage() < application.getSystemUtils().getTotalPage()) {
                        ((BookPlayActivity2) getActivity()).scrolltoPosition(application.getSystemUtils().getCurrentPage());
                    } else {
//                    showInfo("结束");
                        application.getSystemUtils().setCurrentPage(0);
                        application.getSystemUtils().setCurrentLine(0);
                        application.getSystemUtils().setPlayAll(false);
                        BookPlayActivity2.viewPager.setNoFocus(false);
//                        BookPlayActivity2.playTotal.setText("全文播放");
                        BookPlayActivity2.playTotal2.setImageResource(R.drawable.icon_full_reading_f);
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                if (application.getSystemUtils().isCurrentPage()) {
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
                        duration = 0;
                        handler.postDelayed(runnable2, 1000);
                    } else {
                        //当前页播放结束
                        currentSign = 0;
                        isClick = true;
                        application.getSystemUtils().setPlaying(false);
                        application.getSystemUtils().setCurrentPage(false);
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
//            duration = mediaPlayer.getDuration() / 1000;
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
        if (mRecorderUtil != null) {
            mRecorderUtil.release();
        }
        if (handler != null && runnable != null && runnable2 != null) {
            handler.removeCallbacks(runnable);
            handler.removeCallbacks(runnable2);
        }
//        application.getSystemUtils().isPlayAll() = false;
        application.getSystemUtils().setCurrentPage(false);
        preview.setImageResource(R.drawable.icon_book_preview2);
    }


}
