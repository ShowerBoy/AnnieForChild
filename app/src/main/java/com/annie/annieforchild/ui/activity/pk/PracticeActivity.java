package com.annie.annieforchild.ui.activity.pk;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
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
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.pcm2mp3.RecorderAndPlayUtil;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity;
import com.annie.annieforchild.ui.adapter.PkUserPopupAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.example.lamemp3.MP3Recorder;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 歌曲pk,练习,挑战
 * Created by wanglei on 2018/3/31.
 */

public class PracticeActivity extends BaseActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, OnCheckDoubleClick, SongView, PopupWindow.OnDismissListener {
    private ImageView back, menu, menuCollectImg, menuAddmaterialImg, practiceRecording, bookReadBtn, bookRead, bookBg;
    private CircleImageView practiceImage;
    private TextView practiceTitle, practiceBtn, challengeBtn, pkBtn, randomMatch, speak, play, menuCollectText, menuAddmaterialText;
    private LinearLayout practiceLayout, practice3btn, addTimetable, collect, addMaterial;
    private FrameLayout musicFrame;
    private List<UserInfo2> pkUserList;
    private PopupWindow popupWindow, popupWindow2;
    private View popupView, popupView2;
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
    private MediaPlayer mediaPlayer, mediaPlayer2;
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
    private int collectType; //收藏 1：磨耳朵 2：阅读 3：口语 0：其他
    private boolean tag = true; //播放列表是否播放完毕
    private boolean tag2 = true;
    private int bookType; //视频0或书籍1
    private boolean is_click = false;
    private CheckDoubleClickListener listener;
    private int classId = 0;
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
        menu = findViewById(R.id.practice_menu);
        musicFrame = findViewById(R.id.music_frame);
        bookReadBtn = findViewById(R.id.book_type);
        bookRead = findViewById(R.id.book_read);
        bookBg = findViewById(R.id.book_bg);
//        star1 = findViewById(R.id.star1);
//        star2 = findViewById(R.id.star2);
//        star3 = findViewById(R.id.star3);
//        star4 = findViewById(R.id.star4);
//        star5 = findViewById(R.id.star5);
        practiceTitle = findViewById(R.id.practice_title);
        practiceBtn = findViewById(R.id.practice_btn);
        challengeBtn = findViewById(R.id.challenge_btn);
        practiceRecording = findViewById(R.id.practice_recording);
        pkBtn = findViewById(R.id.pk_btn);
        practiceLayout = findViewById(R.id.practice_layout);
        practice3btn = findViewById(R.id.practice_3btn);
        speak = findViewById(R.id.practice_speak);
        play = findViewById(R.id.practice_play);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        practiceBtn.setOnClickListener(listener);
        challengeBtn.setOnClickListener(listener);
        pkBtn.setOnClickListener(listener);
        speak.setOnClickListener(listener);
        play.setOnClickListener(listener);
        musicFrame.setOnClickListener(listener);
        menu.setOnClickListener(listener);
        practiceRecording.setOnClickListener(listener);

        popupWidth = Math.min(SystemUtils.window_width, SystemUtils.window_height) * 3 / 4;
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_popup_item2, null, false);
        popupView2 = LayoutInflater.from(this).inflate(R.layout.activity_practice_menu_item, null, false);
        popupWindow = new PopupWindow(popupView, popupWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow2 = new PopupWindow(this);
        popupGrid = popupView.findViewById(R.id.popup_grid);
        randomMatch = popupView.findViewById(R.id.random_match);
        randomMatch.setOnClickListener(listener);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setOnDismissListener(this);
        popupWindow2.setContentView(popupView2);
        popupWindow2.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow2.setOutsideTouchable(true);
        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });

        addTimetable = popupView2.findViewById(R.id.menu_add_timetable);
        collect = popupView2.findViewById(R.id.menu_collect);
        addMaterial = popupView2.findViewById(R.id.menu_add_material);
        menuCollectImg = popupView2.findViewById(R.id.menu_collect_image);
        menuCollectText = popupView2.findViewById(R.id.menu_collect_text);
        menuAddmaterialImg = popupView2.findViewById(R.id.menu_add_material_image);
        menuAddmaterialText = popupView2.findViewById(R.id.menu_add_material_text);
        addTimetable.setOnClickListener(listener);
        collect.setOnClickListener(listener);
        addMaterial.setOnClickListener(listener);

        popupGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                Intent intent = new Intent(PracticeActivity.this, pkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("bookId", song.getBookId());
                bundle.putString("imageUrl", song.getBookImageUrl());
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
        mediaPlayer2 = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer2.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer2.setOnCompletionListener(this);
        /**
         * {@link com.annie.annieforchild.ui.activity.GlobalSearchActivity}
         * {@link com.annie.annieforchild.ui.adapter.RecommendAdapter}
         * {@link com.annie.annieforchild.ui.adapter.SongAdapter}
         * {@link com.annie.annieforchild.ui.adapter.CollectionAdapter}
         * {@link com.annie.annieforchild.ui.fragment.FirstFragment}
         * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity}
         * {@link com.annie.annieforchild.ui.activity.reading.ReadingActivity}
         */
        intent = getIntent();
        if (intent != null) {
            song = (Song) intent.getSerializableExtra("song");
            type = intent.getIntExtra("type", 0);
            audioType = intent.getIntExtra("audioType", 3);
            audioSource = intent.getIntExtra("audioSource", 0);
            collectType = intent.getIntExtra("collectType", 0);
            bookType = intent.getIntExtra("bookType", 0);
        }

        if (bookType == 0) {
            practiceImage.setVisibility(View.VISIBLE);
            bookReadBtn.setVisibility(View.VISIBLE);
            bookRead.setVisibility(View.GONE);
            Glide.with(this).load(song.getBookImageUrl()).into(practiceImage);
            bookBg.setVisibility(View.GONE);
        } else {
            practiceImage.setVisibility(View.GONE);
            bookReadBtn.setVisibility(View.GONE);
            bookRead.setVisibility(View.VISIBLE);
            Glide.with(this).load(song.getBookImageUrl()).into(bookRead);
            bookBg.setVisibility(View.VISIBLE);
        }

        if (audioSource == 7 || audioSource == 8 || audioSource == 9) {
            practice3btn.setVisibility(View.GONE);
        } else {
            practice3btn.setVisibility(View.VISIBLE);
        }

        if (audioSource == 8) {
            practiceRecording.setVisibility(View.GONE);
        } else {
            practiceRecording.setVisibility(View.VISIBLE);
        }

        popupAdapter = new PkUserPopupAdapter(this, pkUserList);

        popupGrid.setAdapter(popupAdapter);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        practiceTitle.setText(song.getBookName());
        presenter.getBookScore(song.getBookId());

        presenter.accessBook(song.getBookId());
    }

    private void refresh() {
        if (song != null) {
            Glide.with(this).load(song.getBookImageUrl()).into(practiceImage);
            practiceTitle.setText(song.getBookName());
            if (song1 != null) {
                if (song1.getMyResourceUrl() != null && !song1.getMyResourceUrl().equals("")) {
//                    play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_play2_t), null, null);
//                    play.setTextColor(getResources().getColor(R.color.text_orange));
                } else {
//                    play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_play2_f), null, null);
//                    play.setTextColor(getResources().getColor(R.color.text_color));
                }
            }

            if (song1 != null) {
                if (song1.getIsCollected() == 0) {
                    menuCollectImg.setImageResource(R.drawable.icon_song_uncollect);
                    menuCollectText.setText("收藏        ");
                } else {
                    menuCollectImg.setImageResource(R.drawable.icon_song_collect);
                    menuCollectText.setText("已收藏      ");
                }
                if (song1.getIsJoinMaterial() == 0) {
                    menuAddmaterialImg.setImageResource(R.drawable.icon_add_material_f);
                    menuAddmaterialText.setText("加入教材");
                } else {
                    menuAddmaterialImg.setImageResource(R.drawable.icon_add_material_t);
                    menuAddmaterialText.setText("已加入教材");
                }
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
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
            is_click = true;
        } else if (message.what == MethodCode.EVENT_GETPKUSERS) {
            pkUserList.clear();
            pkUserList.addAll((List<UserInfo2>) message.obj);
            if (pkUserList.size() == 0) {
                showInfo("没有pk用户");
            } else {
                popupAdapter.notifyDataSetChanged();
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                getWindowGray(true);
            }
        } else if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            presenter.getBookScore(song.getBookId());
        } else if (message.what == MethodCode.EVENT_COLLECTCOURSE + 2000 + classId) {
            showInfo((String) message.obj);
            presenter.getBookScore(song.getBookId());
        } else if (message.what == MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId) {
            showInfo((String) message.obj);
            presenter.getBookScore(song.getBookId());
        } else if (message.what == MethodCode.EVENT_JOINMATERIAL + 4000 + classId) {
            showInfo((String) message.obj);
            presenter.getBookScore(song.getBookId());
        } else if (message.what == MethodCode.EVENT_CANCELMATERIAL + 5000 + classId) {
            showInfo((String) message.obj);
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

    private void initRecording() {
//        mDialogManager.dismissDialog();
        mRecorderUtil.stopRecording();
        mRecorderUtil.getRecorderPath();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.practice_back:
                finish();
                break;
            case R.id.practice_btn:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (MusicService.isPlay) {
                    MusicService.stop();
                }
                Intent intent = new Intent(this, ExerciseActivity2.class);
                intent.putExtra("bookId", song.getBookId());
                intent.putExtra("imageUrl", song.getBookImageUrl());
                intent.putExtra("audioType", audioType);
                intent.putExtra("audioSource", audioSource);
                intent.putExtra("title", song.getBookName());
                startActivity(intent);
                break;
            case R.id.challenge_btn:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (MusicService.isPlay) {
                    MusicService.stop();
                }
                Intent intent1 = new Intent(this, ChallengeActivity.class);
                intent1.putExtra("bookId", song.getBookId());
                intent1.putExtra("imageUrl", song.getBookImageUrl());
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
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (MusicService.isPlay) {
                    MusicService.stop();
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
                    bundle.putString("imageUrl", song.getBookImageUrl());
                    bundle.putInt("audioType", audioType);
                    bundle.putInt("audioSource", audioSource);
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                } else {
                    showInfo("没有pk用户");
                }
                break;
            case R.id.practice_speak:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (MusicService.isPlay) {
                    MusicService.stop();
                }
                if (isClick) {
                    if (!isPlay) {
                        if (isRecord) {
                            showInfo("录音结束");
                            isRecord = false;
                            speak.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_speak2), null, null);
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
                                    presenter.uploadAudioResource(song.getBookId(), 0, audioType, audioSource, 0, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + song1.getBookName() + ".mp3", 0f, song1.getBookName(), record_time, 3, "", song.getBookImageUrl());
                                }
                            }, 1000);
                        } else {
                            showInfo("录音开始");
                            isRecord = true;
                            record_time = 0;
                            handler.postDelayed(runnable, 1000);
                            speak.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_stop2), null, null);
                            mRecorderUtil.startRecording(song.getBookName());
                        }
                    }
                }
                break;
            case R.id.practice_play:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (MusicService.isPlay) {
                    MusicService.stop();
                }
                if (isClick) {
                    if (!isRecord) {
                        if (isPlay) {
                            play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_play2_t), null, null);
                            try {
                                mediaPlayer2.pause();
                                mediaPlayer2.stop();
                                mediaPlayer2.seekTo(0);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                            isPlay = false;
                        } else {
                            if (song1 != null && song1.getMyResourceUrl() != null) {
//                                isClick = false;
                                isPlay = true;
                                play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_stop2), null, null);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        playUrl2(song1.getMyResourceUrl());
                                    }
                                }).start();
                            }
                        }
                    }
                }
                break;
            case R.id.music_frame:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (isClick) {
                    if (!isRecord) {
                        if (MusicService.isPlay) {
                            if (bookType == 0) {
                                if (song1 != null && song1.getBookName() != null) {
                                    if (MusicService.musicTitle.equals(song1.getBookName())) {
                                        Intent intent2 = new Intent(this, MusicPlayActivity.class);
                                        startActivity(intent2);
                                    } else {
                                        if (resourUrl_list.size() != 0) {
                                            MusicService.stop();
                                            Intent intent2 = new Intent(this, MusicPlayActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("list", (Serializable) resourUrl_list);
                                            bundle.putString("name", song1.getBookName());
                                            bundle.putString("image", song1.getBookImageUrl());
                                            bundle.putInt("origin", 3);
                                            bundle.putInt("audioType", audioType);
                                            bundle.putInt("audioSource", audioSource);
                                            bundle.putInt("resourceId", song.getBookId());
                                            intent2.putExtras(bundle);
                                            startActivity(intent2);
                                        }
                                    }
                                }
                            } else {
                                MusicService.stop();
                                Intent intent3 = new Intent(this, BookPlayActivity2.class);
                                intent3.putExtra("bookId", song.getBookId());
                                intent3.putExtra("imageUrl", song.getBookImageUrl());
                                intent3.putExtra("audioType", audioType);
                                intent3.putExtra("audioSource", audioSource);
                                intent3.putExtra("title", song.getBookName());
                                startActivity(intent3);
                            }
                        } else {
                            if (bookType == 0) {
                                if (song1 != null && resourUrl_list.size() != 0) {
                                    Intent intent2 = new Intent(this, MusicPlayActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("list", (Serializable) resourUrl_list);
                                    bundle.putString("name", song1.getBookName());
                                    bundle.putString("image", song1.getBookImageUrl());
                                    bundle.putInt("origin", 3);
                                    bundle.putInt("audioType", audioType);
                                    bundle.putInt("audioSource", audioSource);
                                    bundle.putInt("resourceId", song.getBookId());
                                    intent2.putExtras(bundle);
                                    startActivity(intent2);
                                }
                            } else {
                                Intent intent3 = new Intent(this, BookPlayActivity2.class);
                                intent3.putExtra("bookId", song.getBookId());
                                intent3.putExtra("imageUrl", song.getBookImageUrl());
                                intent3.putExtra("audioType", audioType);
                                intent3.putExtra("audioSource", audioSource);
                                intent3.putExtra("title", song.getBookName());
                                startActivity(intent3);
                            }
                        }
                    }
                }
                break;
            case R.id.practice_menu:
                if (is_click) {
                    getWindowGray(true);
                    popupWindow2.showAsDropDown(menu);
                }
                break;
            case R.id.menu_add_timetable:
                //加入课表
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
//                if (MusicService.isPlay) {
//                    MusicService.stop();
//                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String date = simpleDateFormat.format(new Date());
                Material material = new Material();
                material.setImageUrl(song.getBookImageUrl());
                material.setMaterialId(song.getBookId());
                material.setName(song.getBookName());
                Intent intent3 = new Intent(this, AddOnlineScheActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("material", material);
                bundle.putString("date", date);
                intent3.putExtras(bundle);
                startActivity(intent3);
                popupWindow2.dismiss();
                break;
            case R.id.menu_collect:
                //收藏
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (song1 != null) {
                    if (song1.getIsCollected() == 0) {
                        presenter.collectCourse(collectType, audioSource, song.getBookId(), classId);
                        popupWindow2.dismiss();
                    } else {
                        presenter.cancelCollection(collectType, audioSource, song.getBookId(), classId);
                        popupWindow2.dismiss();
                    }
                }
                break;
            case R.id.menu_add_material:
                //加入教材
                if (SystemUtils.tag.equals("游客")) {

                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (song1 != null) {
                    if (song1.getIsJoinMaterial() == 0) {
                        presenter.joinMaterial(song.getBookId(), classId);
                        popupWindow2.dismiss();
                    } else {
                        presenter.cancelMaterial(song.getBookId(), classId);
                        popupWindow2.dismiss();
                    }
                }
                break;
            case R.id.practice_recording:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (MusicService.isPlay) {
                    MusicService.stop();
                }
                Intent intent4 = new Intent(this, RecordingActivity.class);
                intent4.putExtra("bookId", song.getBookId());
                intent4.putExtra("bookName", song1.getBookName());
                intent4.putExtra("bookImageUrl", song.getBookImageUrl());
                intent4.putExtra("audioType", audioType);
                intent4.putExtra("audioSource", audioSource);
                intent4.putExtra("myresourceUrl", song1.getMyResourceUrl());
                startActivity(intent4);
                break;
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
        if (mp == mediaPlayer) {
            mediaPlayer.start();
        } else {
            mediaPlayer2.start();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp == mediaPlayer) {
            isClick = true;
            isPlay = false;
            isFinish++;
            tag2 = true;
        } else {
            isClick = true;
            isPlay = false;
            play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_play2_t), null, null);
        }
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
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        super.onDestroy();
    }
}
