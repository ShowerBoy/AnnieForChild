package com.annie.annieforchild.ui.activity.pk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.pcm2mp3.RecorderAndPlayUtil;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.PkUserPopupAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.ashokvarma.bottomnavigation.utils.Utils;
import com.bumptech.glide.Glide;
import com.example.lamemp3.MP3Recorder;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * 歌曲pk,练习,挑战
 * Created by wanglei on 2018/3/31.
 */

public class PracticeActivity extends BaseActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, View.OnClickListener, SongView, PopupWindow.OnDismissListener {
    private ImageView back, practiceImage, star1, star2, star3, star4, star5;
    private TextView practiceTitle, practiceBtn, challengeBtn, pkBtn, randomMatch, preview, speak, play;
    private LinearLayout practiceLayout, practice3btn;
    private List<UserInfo2> pkUserList;
    private PopupWindow popupWindow;
    private View popupView;
    private GridView popupGrid;
    private PkUserPopupAdapter popupAdapter;
    private GrindEarPresenter presenter;
    private Intent intent;
    private Song song;
    private Song song1;  //听儿歌或我要唱歌获取的播放链接
    private List<String> resourUrl_list;
    private int popupWidth;
    private AlertHelper helper;
    private Dialog dialog;
    private MediaPlayer mediaPlayer;
    private float star;
    private int audioType, audioSource, type;
    private boolean isClick = true;
    private RecorderAndPlayUtil mRecorderUtil = null;
    private static final String DIR = "LAME/mp3/";
    private boolean isRecord = false;
    private int record_time = 0; //录音时长
    private boolean isPlay = false; //是否播放录音
    private Handler handler = new Handler();
    private int isFinish = 0; //播放列表标识
    private boolean tag = true; //播放列表是否播放完毕
    private boolean tag2 = true;
    Runnable runnable;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_practice;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.practice_back);
        practiceImage = findViewById(R.id.practice_image);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        practiceTitle = findViewById(R.id.practice_title);
        practiceBtn = findViewById(R.id.practice_btn);
        challengeBtn = findViewById(R.id.challenge_btn);
        pkBtn = findViewById(R.id.pk_btn);
        practiceLayout = findViewById(R.id.practice_layout);
        practice3btn = findViewById(R.id.practice_3btn);
        preview = findViewById(R.id.practice_preview);
        speak = findViewById(R.id.practice_speak);
        play = findViewById(R.id.practice_play);
        back.setOnClickListener(this);
        practiceBtn.setOnClickListener(this);
        challengeBtn.setOnClickListener(this);
        pkBtn.setOnClickListener(this);
        preview.setOnClickListener(this);
        speak.setOnClickListener(this);
        play.setOnClickListener(this);

        popupWidth = Math.min(SystemUtils.window_width, SystemUtils.window_height) * 3 / 4;
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_popup_item2, null, false);
        popupWindow = new PopupWindow(popupView, popupWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupGrid = popupView.findViewById(R.id.popup_grid);
        randomMatch = popupView.findViewById(R.id.random_match);
        randomMatch.setOnClickListener(this);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setOnDismissListener(this);

        popupGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                Intent intent = new Intent(PracticeActivity.this, pkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("bookId", song.getBookId());
                bundle.putString("pkUserName", pkUserList.get(position).getUsername());
                bundle.putString("avatar", pkUserList.get(position).getAvatar());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
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
        pkUserList = new ArrayList<>();
        resourUrl_list = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        /**
         * {@link com.annie.annieforchild.ui.activity.GlobalSearchActivity}
         * {@link com.annie.annieforchild.ui.adapter.RecommendAdapter}
         * {@link com.annie.annieforchild.ui.adapter.SongAdapter}
         * {@link com.annie.annieforchild.ui.adapter.CollectionAdapter}
         */
        intent = getIntent();
        if (intent != null) {
            song = (Song) intent.getSerializableExtra("song");
            type = intent.getIntExtra("type", 0);
            audioType = intent.getIntExtra("audioType", 3);
            audioSource = intent.getIntExtra("audioSource", 0);
        }

        if (audioSource == 9) {
            practice3btn.setVisibility(View.GONE);
        } else {
            practice3btn.setVisibility(View.VISIBLE);
        }

        if (audioSource == 7 || audioSource == 8) {
            practice3btn.setVisibility(View.GONE);
        } else {
            practice3btn.setVisibility(View.VISIBLE);
        }

        popupAdapter = new PkUserPopupAdapter(this, pkUserList);
        popupGrid.setAdapter(popupAdapter);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        Glide.with(this).load(song.getBookImageUrl()).into(practiceImage);
        practiceTitle.setText(song.getBookName());
        presenter.getBookScore(song.getBookId());
    }

    private void refresh() {
        if (song != null) {
            Glide.with(this).load(song.getBookImageUrl()).into(practiceImage);
            practiceTitle.setText(song.getBookName());
            float a = song.getLastScore();
            float b = song.getTotalScore();
            star = a / b * 5f;

            BigDecimal bigDecimal = new BigDecimal(star);
            star = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            String abc = star + "";
            String[] ddd = abc.split("\\.");
            if (ddd[0].equals("0")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_f);
                    star3.setImageResource(R.drawable.icon_star_f);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                } else {
                    if (v == 0) {
                        star1.setImageResource(R.drawable.icon_star_f);
                    } else {
                        star1.setImageResource(R.drawable.icon_star_h);
                    }
                    star2.setImageResource(R.drawable.icon_star_f);
                    star3.setImageResource(R.drawable.icon_star_f);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                }
            } else if (ddd[0].equals("1")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_f);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                } else {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_h);
                    star3.setImageResource(R.drawable.icon_star_f);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                }
            } else if (ddd[0].equals("2")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                } else {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_h);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                }
            } else if (ddd[0].equals("3")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_t);
                    star5.setImageResource(R.drawable.icon_star_f);
                } else {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_h);
                    star5.setImageResource(R.drawable.icon_star_f);
                }
            } else if (ddd[0].equals("4")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_t);
                    star5.setImageResource(R.drawable.icon_star_t);
                } else {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_t);
                    star5.setImageResource(R.drawable.icon_star_h);
                }
            } else if (ddd[0].equals("5")) {
                star1.setImageResource(R.drawable.icon_star_t);
                star2.setImageResource(R.drawable.icon_star_t);
                star3.setImageResource(R.drawable.icon_star_t);
                star4.setImageResource(R.drawable.icon_star_t);
                star5.setImageResource(R.drawable.icon_star_t);
            }
            if (song1 != null) {
                if (song1.getMyResourceUrl() != null && !song1.getMyResourceUrl().equals("")) {
                    play.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.icon_play2_t), null, null, null);
                } else {
                    play.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.icon_play2_f), null, null, null);
                }
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.practice_back:
                finish();
                break;
            case R.id.practice_btn:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                Intent intent = new Intent(this, ExerciseActivity.class);
                intent.putExtra("bookId", song.getBookId());
                intent.putExtra("type", type);
                intent.putExtra("audioType", audioType);
                intent.putExtra("audioSource", audioSource);
                startActivity(intent);
                break;
            case R.id.challenge_btn:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                Intent intent1 = new Intent(this, ChallengeActivity.class);
                intent1.putExtra("bookId", song.getBookId());
                intent1.putExtra("audioType", audioType);
                intent1.putExtra("audioSource", audioSource);
                startActivity(intent1);
                break;
            case R.id.pk_btn:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                presenter.getPkUsers(song.getBookId());
//                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                break;
            case R.id.random_match:
                if (pkUserList != null && pkUserList.size() != 0) {
                    Random random = new Random();
                    int position = random.nextInt(pkUserList.size());
                    popupWindow.dismiss();
                    Intent intent2 = new Intent(PracticeActivity.this, pkActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("bookId", song.getBookId());
                    bundle.putString("pkUserName", pkUserList.get(position).getUsername());
                    bundle.putString("avatar", pkUserList.get(position).getAvatar());
                    bundle.putInt("type", type);
                    bundle.putInt("audioType", audioType);
                    bundle.putInt("audioSource", audioSource);
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                } else {
                    showInfo("没有pk用户");
                }
                break;
            case R.id.practice_preview:
                if (isClick) {
                    if (!isRecord) {
                        if (isPlay) {
                            preview.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.icon_preview2), null, null, null);
                            preview.setText("听歌");
                            try {
                                mediaPlayer.pause();
                                mediaPlayer.stop();
                                mediaPlayer.seekTo(0);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                            isPlay = false;
                            tag = false;
                        } else {
                            if (song1 != null && song1.getBookResourceUrl() != null) {
//                                isClick = false;
                                isPlay = true;
                                preview.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.icon_stop2), null, null, null);
                                preview.setText("停止");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tag = true;
                                        isFinish = 0;
                                        while (tag) {
                                            if (tag2) {
                                                if (isFinish < resourUrl_list.size()) {
                                                    playUrl(resourUrl_list.get(isFinish));
                                                    tag2 = false;
                                                } else {
                                                    tag2 = true;
                                                    tag = false;
                                                    Message message = new Message();
                                                    myHandler.sendMessage(message);
                                                }
                                            }
                                        }
                                    }
                                }).start();
                            }
                        }
                    }
                }
                break;
            case R.id.practice_speak:
                if (isClick) {
                    if (!isPlay) {
                        if (isRecord) {
                            showInfo("录音结束");
                            isRecord = false;
                            speak.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.icon_speak2), null, null, null);
                            initRecording();
                            mRecorderUtil.stopRecording();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    presenter.uploadAudioResource(song.getBookId(), 0, audioType, audioSource, 0, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + song1.getBookName() + ".mp3", 0f, song1.getBookName(), record_time, 3, "");
                                }
                            }, 1000);
                        } else {
                            showInfo("录音开始");
                            isRecord = true;
                            record_time = 0;
                            handler.postDelayed(runnable, 1000);
                            speak.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.icon_stop2), null, null, null);
                            mRecorderUtil.startRecording(song.getBookName());
                        }
                    }
                }
                break;
            case R.id.practice_play:
                if (isClick) {
                    if (!isRecord) {
                        if (song1 != null && song1.getMyResourceUrl() != null) {
                            isClick = false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    playUrl(song1.getMyResourceUrl());
                                }
                            }).start();
                        }
                    }
                }
                break;
        }
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            preview.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(PracticeActivity.this, R.drawable.icon_preview2), null, null, null);
            preview.setText("听歌");
        }
    };

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETBOOKSCORE) {
            song1 = (Song) message.obj;
            song.setLastScore(song1.getLastScore());
            song.setTotalPages(song1.getTotalPages());
            song.setTotalScore(song1.getTotalScore());
            resourUrl_list.clear();
            resourUrl_list.addAll(song1.getBookResourceUrl());
            refresh();
        } else if (message.what == MethodCode.EVENT_GETPKUSERS) {
            pkUserList.clear();
            pkUserList.addAll((List<UserInfo2>) message.obj);
            popupAdapter.notifyDataSetChanged();
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            getWindowGray(true);
        } else if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            presenter.getBookScore(song.getBookId());
        }
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
            isClick = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            isClick = true;
        }
    }

    private void getWindowGray(boolean tag) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        if (tag) {
            layoutParams.alpha = 0.7f;
            getWindow().setAttributes(layoutParams);
        } else {
            layoutParams.alpha = 1f;
            getWindow().setAttributes(layoutParams);
        }
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

    @Override
    public void onDismiss() {
        getWindowGray(false);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isClick = true;
        isPlay = false;
        isFinish++;
        tag2 = true;
    }

    private void initRecording() {
//        mDialogManager.dismissDialog();
        mRecorderUtil.stopRecording();
        mRecorderUtil.getRecorderPath();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        isClick = true;
        isPlay = false;
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
