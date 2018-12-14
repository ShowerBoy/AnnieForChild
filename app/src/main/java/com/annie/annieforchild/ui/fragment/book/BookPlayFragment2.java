package com.annie.annieforchild.ui.fragment.book;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import com.annie.annieforchild.Utils.pcm2mp3.RecorderAndPlayUtil;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.book.Page;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.PhotoActivity;
import com.annie.annieforchild.ui.activity.pk.BookPlayActivity2;
import com.annie.annieforchild.ui.adapter.ExerciseAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.example.lamemp3.MP3Recorder;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 章节书
 * Created by wanglei on 2018/10/16.
 */

public class BookPlayFragment2 extends BaseFragment implements SongView, OnCheckDoubleClick, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private ImageView pageImage, record, playRecord;
    private Page page;
    private List<Line> lists;
    private static final String DIR = "LAME/mp3/";
    private AlertHelper helper;
    private Dialog dialog;
    private GrindEarPresenter presenter;
    private MediaPlayer mediaPlayer;
    private RecorderAndPlayUtil mRecorderUtil = null;
    private CheckDoubleClickListener listener;
    private Handler handler = new Handler();
    private Runnable runnable;
    private boolean isRecord = false;
    private int record_time = 0; //录音时长
    private boolean isClick = true;
    private boolean isPlay = false; //是否播放录音
    private int audioType, audioSource, bookId;
    private String title, imageUrl;
    private int homeworkid;

    {
        setRegister(true);
    }

    public BookPlayFragment2() {

    }

    @Override
    protected void initData() {
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        lists = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            page = (Page) bundle.getSerializable("page");
            audioType = bundle.getInt("audioType");
            audioSource = bundle.getInt("audioSource");
            bookId = bundle.getInt("bookId");
            title = bundle.getString("title");
            imageUrl = bundle.getString("imageUrl");
            homeworkid = bundle.getInt("homeworkid");
        }
        Glide.with(getContext()).load(page.getPageImage()).into(pageImage);
        lists.addAll(page.getLineContent());
        if (page.getLineContent().get(0).getMyResourceUrl() != null && page.getLineContent().get(0).getMyResourceUrl().length() != 0) {
            playRecord.setImageResource(R.drawable.icon_play_recording_t);
        } else {
            playRecord.setImageResource(R.drawable.icon_unplay_recording);
        }

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

    @Override
    protected void initView(View view) {
        pageImage = view.findViewById(R.id.exercise_image2);
        record = view.findViewById(R.id.exercise_record);
        playRecord = view.findViewById(R.id.exercise_play_record);
        listener = new CheckDoubleClickListener(this);
        pageImage.setOnClickListener(listener);
        record.setOnClickListener(listener);
        playRecord.setOnClickListener(listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise_fragment2;
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
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            AudioBean bean = (AudioBean) message.obj;
            if (bean.getPage() == page.getPage()) {
                page.getLineContent().get(0).setMyResourceUrl(bean.getResourceUrl());
                refresh();
            }
        } else if (message.what == MethodCode.EVENT_PAGEPLAY) {
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
            }
        }
    }

    private void refresh() {
        playRecord.setImageResource(R.drawable.icon_play_recording_t);
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.exercise_image2:
                Intent intent = new Intent(getContext(), PhotoActivity.class);
                intent.putExtra("url", page.getPageImage());
                intent.putExtra("delete", "0");
                startActivity(intent);
                break;
            case R.id.exercise_record:
                if (isClick) {
                    if (!isPlay) {
                        if (isRecord) {
                            showInfo("录音结束");
                            BookPlayActivity2.viewPager.setNoFocus(false);
                            isRecord = false;
                            record.setImageResource(R.drawable.icon_recording_t);
                            initRecording();
                            mRecorderUtil.stopRecording();
                            if (record_time <= 0) {
                                showInfo("时长不能为零");
                                break;
                            }
                            //延迟1秒上传
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    presenter.uploadAudioResource(bookId, page.getPage(), audioType, audioSource, 1, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + title + ".mp3", 0f, title + "（练习）", record_time, 0, "", imageUrl, 0, homeworkid);
                                }
                            }, 1000);
                        } else {
                            showInfo("录音开始");
                            BookPlayActivity2.viewPager.setNoFocus(true);
                            isRecord = true;
                            record_time = 0;
                            handler.postDelayed(runnable, 1000);
                            record.setImageResource(R.drawable.icon_recording_f);
                            mRecorderUtil.startRecording(title);
                        }
                    }
                }
                break;
            case R.id.exercise_play_record:
                if (isClick) {
                    if (!isRecord) {
                        if (isPlay) {
                            playRecord.setImageResource(R.drawable.icon_play_recording_t);
                            try {
                                mediaPlayer.pause();
                                mediaPlayer.stop();
                                mediaPlayer.seekTo(0);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                            isPlay = false;
                        } else {
                            if (page.getLineContent().get(0).getMyResourceUrl() != null && page.getLineContent().get(0).getMyResourceUrl().length() != 0) {
//                                isClick = false;
                                isPlay = true;
                                playRecord.setImageResource(R.drawable.icon_play_recording_f);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        playUrl2(page.getLineContent().get(0).getMyResourceUrl());
                                    }
                                }).start();
                            }
                        }
                    }
                }
                break;
        }
    }

    private void playUrl2(String url) {
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
            isClick = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            isClick = true;
        }
    }

    private void initRecording() {
        mRecorderUtil.stopRecording();
        mRecorderUtil.getRecorderPath();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isClick = true;
        isPlay = false;
        playRecord.setImageResource(R.drawable.icon_play_recording_t);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

//    @Override
//    public void onDestroyView() {
//        super1.onDestroyView();
//        if (mRecorderUtil != null) {
//            mRecorderUtil.stopRecording();
//        }
//        if (mediaPlayer != null) {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();
//            }
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//        if (handler != null && runnable != null) {
//            handler.removeCallbacks(runnable);
//        }
//        isClick = true;
//        isPlay = false;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecorderUtil != null) {
            mRecorderUtil.stopRecording();
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        isClick = true;
        isPlay = false;
    }
}
