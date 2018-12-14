package com.annie.annieforchild.ui.activity.pk;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.pcm2mp3.RecorderAndPlayUtil;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.ChallengeAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.example.lamemp3.MP3Recorder;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 录音界面
 * Created by wanglei on 2018/9/10.
 */

public class RecordingActivity extends BaseActivity implements SongView, OnCheckDoubleClick, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, PlatformActionListener {
    private ImageView back, share, recordBtn, pengyouquan, weixin, qq, qqzone, recordPlay;
    private TextView title, shareCancel;
    private RecyclerView recycler;
    private GrindEarPresenter presenter;
    private CheckDoubleClickListener listener;
    private RecorderAndPlayUtil mRecorderUtil = null;
    private MediaPlayer mediaPlayer2;
    private static final String DIR = "LAME/mp3/";
    private boolean isRecord = false;
    private int record_time = 0; //录音时长
    private boolean isClick = true;
    private boolean isPlay = false; //是否播放录音
    private int bookId, audioSource, audioType;
    private String myresourceUrl, bookName, bookImageUrl, url;
    private Intent intent;
    private Book book;
    private List<Line> lists;
    private ChallengeAdapter adapter;
    private ShareUtils shareUtils;
    private AlertHelper helper;
    private Dialog dialog;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int homeworkid;
    private PopupWindow popupWindow;
    private View popupView;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recording;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.recording_back);
        share = findViewById(R.id.recording_share);
        title = findViewById(R.id.recording_title);
        recordBtn = findViewById(R.id.recording_record);
        recordPlay = findViewById(R.id.recording_play);
        recycler = findViewById(R.id.recording_recycler);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        share.setOnClickListener(listener);
        recordBtn.setOnClickListener(listener);
        recordPlay.setOnClickListener(listener);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);

        popupWindow = new PopupWindow(this);
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_share_daka_item, null, false);
        pengyouquan = popupView.findViewById(R.id.share_daka_pengyouquan);
        weixin = popupView.findViewById(R.id.share_daka_weixin);
        qq = popupView.findViewById(R.id.share_daka_qq);
        qqzone = popupView.findViewById(R.id.share_daka_qqzone);
        shareCancel = popupView.findViewById(R.id.daka_share_cancel);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        shareCancel.setOnClickListener(listener);
        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });
    }

    @Override
    protected void initData() {
        shareUtils = new ShareUtils(this, this);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        lists = new ArrayList<>();
        mediaPlayer2 = new MediaPlayer();
        mediaPlayer2.setOnPreparedListener(this);
        mediaPlayer2.setOnCompletionListener(this);
        intent = getIntent();
        if (intent != null) {
            bookId = intent.getIntExtra("bookId", 0);
            audioSource = intent.getIntExtra("audioSource", 0);
            audioType = intent.getIntExtra("audioType", 0);
            myresourceUrl = intent.getStringExtra("myresourceUrl");
            bookName = intent.getStringExtra("bookName");
            bookImageUrl = intent.getStringExtra("bookImageUrl");
            homeworkid = intent.getIntExtra("homeworkid", homeworkid);
            title.setText(bookName);
            if (myresourceUrl != null && myresourceUrl.length() != 0) {
                recordPlay.setImageResource(R.drawable.icon_play_recording_t);
            } else {
                recordPlay.setImageResource(R.drawable.icon_unplay_recording);
            }
        }
        adapter = new ChallengeAdapter(this, lists);
        recycler.setAdapter(adapter);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
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
        presenter.getBookAudioData(bookId, 1, null);
    }

    private void initRecording() {
        mRecorderUtil.stopRecording();
        mRecorderUtil.getRecorderPath();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
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

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            AudioBean bean = (AudioBean) message.obj;
            if (bean != null) {
                if (bean.getResourceUrl() != null) {
                    myresourceUrl = bean.getResourceUrl();
                    recordPlay.setImageResource(R.drawable.icon_play_recording_t);
                }
            }
        } else if (message.what == MethodCode.EVENT_PK_CHALLENGE) {
            book = (Book) message.obj;
            lists.clear();
            for (int i = 0; i < book.getPageContent().size(); i++) {
                lists.addAll(book.getPageContent().get(i).getLineContent());
            }
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_CLOCKINSHARE) {
            ShareBean shareBean = (ShareBean) message.obj;
            url = shareBean.getUrl();
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.recording_back:
                finish();
                break;
            case R.id.recording_share:
                //录音分享
                if (!isPlay) {
                    if (!isRecord) {
                        if (myresourceUrl != null && myresourceUrl.length() != 0) {
                            presenter.clockinShare(3, bookId);
                            getWindowGray(true);
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                        }
                    }
                }
                break;
            case R.id.recording_record:
                if (isClick) {
                    if (!isPlay) {
                        if (isRecord) {
                            showInfo("录音结束");
                            isRecord = false;
                            recordBtn.setImageResource(R.drawable.icon_recording_t);
//                            speak.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_speak2), null, null);
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
                                    presenter.uploadAudioResource(bookId, 0, audioType, audioSource, 0, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + bookName + ".mp3", 0f, bookName, record_time, 3, "", bookImageUrl, 0, homeworkid);
                                }
                            }, 1000);
                        } else {
                            showInfo("录音开始");
                            isRecord = true;
                            record_time = 0;
                            handler.postDelayed(runnable, 1000);
                            recordBtn.setImageResource(R.drawable.icon_recording_f);
//                            speak.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_stop2), null, null);
                            mRecorderUtil.startRecording(bookName);
                        }
                    }
                }
                break;
            case R.id.recording_play:
                if (isClick) {
                    if (!isRecord) {
                        if (isPlay) {
                            recordPlay.setImageResource(R.drawable.icon_play_recording_t);
//                            play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_play2_t), null, null);
                            try {
                                mediaPlayer2.pause();
                                mediaPlayer2.stop();
                                mediaPlayer2.seekTo(0);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                            isPlay = false;
                        } else {
                            if (myresourceUrl != null && myresourceUrl.length() != 0) {
//                                isClick = false;
                                isPlay = true;
                                recordPlay.setImageResource(R.drawable.icon_play_recording_f);
//                                play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_stop2), null, null);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        playUrl2(myresourceUrl);
                                    }
                                }).start();
                            }
                        }
                    }
                }
                break;
            case R.id.share_daka_pengyouquan:
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechatMoments("快来听，我家宝宝" + SystemUtils.userInfo.getName() + "在安妮花流利读" + bookName, "安妮花-定制专属于Ta的英语路线图", null, url);
                }
                break;
            case R.id.share_daka_weixin:
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechat("快来听，我家宝宝" + SystemUtils.userInfo.getName() + "在安妮花流利读" + bookName, "安妮花-定制专属于Ta的英语路线图", null, url);
                }
                break;
            case R.id.share_daka_qq:
                if (url != null && url.length() != 0) {
                    shareUtils.shareQQ("快来听，我家宝宝" + SystemUtils.userInfo.getName() + "在安妮花流利读" + bookName, "安妮花-定制专属于Ta的英语路线图", null, url);
                }
                break;
            case R.id.share_daka_qqzone:
                if (url != null && url.length() != 0) {
                    shareUtils.shareQZone("快来听，我家宝宝" + SystemUtils.userInfo.getName() + "在安妮花流利读" + bookName, "安妮花-定制专属于Ta的英语路线图", null, url);
                }
                break;
            case R.id.daka_share_cancel:
                popupWindow.dismiss();
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
        isClick = true;
        isPlay = false;
        if (mp == mediaPlayer2) {
            recordPlay.setImageResource(R.drawable.icon_play_recording_t);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer2.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer2 != null) {
            mediaPlayer2.pause();
        }
        if (mRecorderUtil != null) {
            mRecorderUtil.stopRecording();
        }
        isClick = true;
        isPlay = false;
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer2 != null) {
            if (mediaPlayer2.isPlaying()) {
                mediaPlayer2.stop();
            }
            mediaPlayer2.release();
            mediaPlayer2 = null;
        }
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        super.onDestroy();
    }

    private void getWindowGray(boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().setAttributes(layoutParams);
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
        popupWindow.dismiss();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showInfo("分享取消");
        popupWindow.dismiss();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        showInfo("分享取消");
        popupWindow.dismiss();
    }
}
