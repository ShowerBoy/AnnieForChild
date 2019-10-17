package com.annie.annieforchild.ui.activity.pk;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.airbnb.lottie.LottieAnimationView;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.pcm2mp3.RecorderAndPlayUtil;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.bean.ShareCoin;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.book.ReleaseBean;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity;
import com.annie.annieforchild.ui.activity.net.LessonActivity;
import com.annie.annieforchild.ui.adapter.BookEndAdapter;
import com.annie.annieforchild.ui.adapter.PkUserPopupAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseMusicActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 歌曲pk,练习,挑战
 * Created by wanglei on 2018/3/31.
 */

public class PracticeActivity extends BaseMusicActivity implements PlatformActionListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, OnCheckDoubleClick, SongView, PopupWindow.OnDismissListener {
    private ImageView back, menu, menuCollectImg, menuAddmaterialImg, practiceRecording, bookRead, bookBg, bookBehind, songBehind, pengyouquan, weixin, qq, qqzone, clarify;
    private LottieAnimationView animationView;
    private CircleImageView practiceImage;
    private TextView shareCancel, coinCount;
    private TextView practiceTitle, practiceBtn, challengeBtn, pkBtn, randomMatch, speak, play, menuCollectText, menuAddmaterialText, people;
    private LinearLayout practiceLayout, practice3btn, addTimetable, collect, addMaterial, practiceLine, practiceBackLayout;
    private FrameLayout musicFrame;
    private RecyclerView practiceRecycler;
    private List<UserInfo2> pkUserList;
    private PopupWindow popupWindow, popupWindow2, popupWindow3;
    private View popupView, popupView2, popupView3;
    private GridView popupGrid;
    private PkUserPopupAdapter popupAdapter;
    private GrindEarPresenter presenter;
    private Intent intent;
    private Song song;
    private Song song1;  //听儿歌或我要唱歌获取的播放链接
    private List<Song> resourUrl_list; //播放列表
    private int popupWidth;
    private AlertHelper helper;
    private Dialog dialog;
    private MediaPlayer mediaPlayer2;
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
    private List<Song> songList;
    private CheckDoubleClickListener listener;
    private int classId = 0;
    private BookEndAdapter adapter;
    private List<ReleaseBean> lists;
    private ShareUtils shareUtils;
    private String url;
    private int homeworkid, musicPosition, shareType, homeworktype;
    Runnable runnable;
    private int lessonTag;
    public static int homepage=0;
    public static int pagenum=0;
    private int flag=0;

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
        bookRead = findViewById(R.id.book_read);
        bookBg = findViewById(R.id.book_bg);
        people = findViewById(R.id.practice_people);
        practiceBackLayout = findViewById(R.id.practice_back_layout);
        practiceTitle = findViewById(R.id.practice_title);
        practiceBtn = findViewById(R.id.practice_btn);
        challengeBtn = findViewById(R.id.challenge_btn);
        practiceRecording = findViewById(R.id.practice_recording);
        pkBtn = findViewById(R.id.pk_btn);
        practiceLayout = findViewById(R.id.practice_layout);
        practice3btn = findViewById(R.id.practice_3btn);
        speak = findViewById(R.id.practice_speak);
        play = findViewById(R.id.practice_play);
        practiceLine = findViewById(R.id.practice_line);
        practiceRecycler = findViewById(R.id.practice_recycler);
        bookBehind = findViewById(R.id.book_behind);
        songBehind = findViewById(R.id.song_behind);
        clarify = findViewById(R.id.practice_clarity);
        animationView = findViewById(R.id.practice_animation);
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

        popupWidth = Math.min(application.getSystemUtils().getWindow_width(), application.getSystemUtils().getWindow_height()) * 3 / 4;
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_popup_item2, null, false);
        popupView2 = LayoutInflater.from(this).inflate(R.layout.activity_practice_menu_item, null, false);
        popupView3 = LayoutInflater.from(this).inflate(R.layout.activity_share_daka_item2, null, false);
        coinCount = popupView3.findViewById(R.id.coin_count);
        pengyouquan = popupView3.findViewById(R.id.share_daka_pengyouquan);
        weixin = popupView3.findViewById(R.id.share_daka_weixin);
        qq = popupView3.findViewById(R.id.share_daka_qq);
        qqzone = popupView3.findViewById(R.id.share_daka_qqzone);
        shareCancel = popupView3.findViewById(R.id.daka_share_cancel2);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        shareCancel.setOnClickListener(listener);
        coinCount.setText("分享+2金币");
        popupWindow = new PopupWindow(popupView, popupWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow2 = new PopupWindow(this);
        popupWindow2.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow2.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow3 = new PopupWindow(this);
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
                bundle.putString("pkName", pkUserList.get(position).getName());
                bundle.putString("avatar", pkUserList.get(position).getAvatar());
                bundle.putInt("homeworkid", homeworkid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        popupWindow3.setContentView(popupView3);
        popupWindow3.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow3.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow3.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow3.setOutsideTouchable(false);
        popupWindow3.setFocusable(true);
        popupWindow3.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });

        RecyclerLinearLayoutManager layoutManager = new RecyclerLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        practiceRecycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        shareUtils = new ShareUtils(this, this);
        mRecorderUtil = new RecorderAndPlayUtil(DIR);
//        mRecorderUtil.getRecorder().setHandle(new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case MP3Recorder.MSG_REC_STARTED:
//                        // 开始录音
//                        break;
//                    case MP3Recorder.MSG_REC_STOPPED:
//                        // 停止录音
////                        if (mIsSendVoice) {// 是否发送录音
////                            mIsSendVoice = false;
////                            audioRecordFinishListener.onFinish(mSecond, mRecorderUtil.getRecorderPath());
////                        }
////                        showInfo(mRecorderUtil.getRecorderPath());
//                        break;
//                    case MP3Recorder.MSG_ERROR_GET_MIN_BUFFERSIZE:
//                        initRecording();
//                        showInfo("采样率手机不支持");
//                        break;
//                    case MP3Recorder.MSG_ERROR_CREATE_FILE:
//                        initRecording();
//                        showInfo("创建音频文件出错");
//                        break;
//                    case MP3Recorder.MSG_ERROR_REC_START:
//                        initRecording();
//                        showInfo("初始化录音器出错");
//                        break;
//                    case MP3Recorder.MSG_ERROR_AUDIO_RECORD:
//                        initRecording();
//                        showInfo("录音的时候出错");
//                        break;
//                    case MP3Recorder.MSG_ERROR_AUDIO_ENCODE:
//                        initRecording();
//                        showInfo("编码出错");
//                        break;
//                    case MP3Recorder.MSG_ERROR_WRITE_FILE:
//                        initRecording();
//                        showInfo("文件写入出错");
//                        break;
//                    case MP3Recorder.MSG_ERROR_CLOSE_FILE:
//                        initRecording();
//                        showInfo("文件流关闭出错");
//                        break;
//                }
//            }
//        });
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
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        mediaPlayer2 = new MediaPlayer();
        mediaPlayer2.setOnPreparedListener(this);
        mediaPlayer2.setOnCompletionListener(this);
        /**
         * {@link com.annie.annieforchild.ui.activity.GlobalSearchActivity}
         * {@link com.annie.annieforchild.ui.adapter.RecommendAdapter}
         * {@link com.annie.annieforchild.ui.adapter.SongAdapter}
         * {@link com.annie.annieforchild.ui.adapter.CollectionAdapter}
         * {@link com.annie.annieforchild.ui.fragment.FirstFragment}
         * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity}
         * {@link com.annie.annieforchild.ui.activity.reading.ReadingActivity}
         * {@link com.annie.annieforchild.ui.fragment.recording.MyReleaseFragment}
         */
        intent = getIntent();
        if (intent != null) {
            song = (Song) intent.getSerializableExtra("song");
            songList = (List<Song>) intent.getSerializableExtra("songList");
            type = intent.getIntExtra("type", 0);
            audioType = intent.getIntExtra("audioType", 3);
            audioSource = intent.getIntExtra("audioSource", 0);
            collectType = intent.getIntExtra("collectType", 0);
            bookType = intent.getIntExtra("bookType", 0);
            homeworkid = intent.getIntExtra("homeworkid", 0);
            homeworktype = intent.getIntExtra("homeworktype", -1);
            musicPosition = intent.getIntExtra("musicPosition", 0);
            lessonTag = intent.getIntExtra("lessonTag", 0);
            homepage = intent.getIntExtra("homepage", 0);
            if (audioType == 2) {
                songList = null;
            } else {
                if (songList != null) {
                    resourUrl_list.clear();
                    resourUrl_list.addAll(songList);
                }
            }

        }

        if (bookType == 0) {
            practiceImage.setVisibility(View.VISIBLE);
            bookRead.setVisibility(View.GONE);
            Glide.with(this).load(song.getBookImageUrl()).into(practiceImage);
//            bookBg.setVisibility(View.GONE);
            practiceBackLayout.setVisibility(View.GONE);
//            practiceRecording.setImageResource(R.drawable.icon_recording_btn);
            practiceRecording.setImageResource(R.drawable.icon_moerduo_btn);
            practiceLine.setVisibility(View.GONE);
            bookBehind.setVisibility(View.GONE);
            songBehind.setVisibility(View.VISIBLE);
            if (audioType == 1) {
                practiceRecording.setImageResource(R.drawable.icon_liulidu_btn);
            } else if (audioType == 0) {
                practiceRecording.setImageResource(R.drawable.icon_moerduo_btn);
            } else {
                //强行把地道说样式变为流利读
                practiceImage.setVisibility(View.GONE);
                bookRead.setVisibility(View.VISIBLE);
                practiceBackLayout.setVisibility(View.VISIBLE);
                practiceRecording.setVisibility(View.VISIBLE);
                practiceRecording.setImageResource(R.drawable.icon_didaoshuo_btn);
                bookType = 1;
            }
        } else {
            practiceImage.setVisibility(View.GONE);
            bookRead.setVisibility(View.VISIBLE);
            practiceBackLayout.setVisibility(View.VISIBLE);
            practiceRecording.setVisibility(View.VISIBLE);
            Glide.with(this).load(song.getBookImageUrl()).into(bookRead);
//            bookBg.setVisibility(View.VISIBLE);
            if (audioType == 1) {
                practiceRecording.setImageResource(R.drawable.icon_liulidu_btn);
            } else if (audioType == 0) {
                practiceRecording.setImageResource(R.drawable.icon_moerduo_btn);
            } else {
                practiceRecording.setImageResource(R.drawable.icon_didaoshuo_btn);
            }
            practiceLine.setVisibility(View.VISIBLE);
            bookBehind.setVisibility(View.VISIBLE);
            songBehind.setVisibility(View.GONE);
        }

        if (audioSource == 7 || audioSource == 8 || audioSource == 9) {
            practice3btn.setVisibility(View.GONE);
        } else {
            practice3btn.setVisibility(View.VISIBLE);
        }

//        if (audioSource == 8) {
//            practiceRecording.setVisibility(View.GONE);
//        } else {
//            practiceRecording.setVisibility(View.VISIBLE);
//        }

        popupAdapter = new PkUserPopupAdapter(this, pkUserList);

        popupGrid.setAdapter(popupAdapter);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();

        practiceTitle.setText(song.getBookName());
        presenter.getBookScore(song.getBookId(), bookType, true);

//        presenter.accessBook(song.getBookId());
    }

    private void refresh() {
        if (song != null) {
            if (song.getBookImageUrl() == null || song.getBookImageUrl().length() == 0) {
                Glide.with(this).load(song1.getBookImageUrl()).into(practiceImage);
            } else {
                Glide.with(this).load(song.getBookImageUrl()).into(practiceImage);
            }
            practiceTitle.setText(song.getBookName());
            if (song1 != null) {
//                if (song1.getIsCollected() == 0) {
//                    menuCollectImg.setImageResource(R.drawable.icon_song_uncollect);
//                    menuCollectText.setText("收藏        ");
//                } else {
//                    menuCollectImg.setImageResource(R.drawable.icon_song_collect);
//                    menuCollectText.setText("已收藏      ");
//                }
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

    /**
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETBOOKSCORE) {
            song1 = (Song) message.obj;
            song.setLastScore(song1.getLastScore());
            song.setTotalPages(song1.getTotalPages());
            song.setTotalScore(song1.getTotalScore());
            song1.setBookId(song.getBookId());
            if (songList == null) {
                resourUrl_list.clear();
                resourUrl_list.add(song1);
            }

            refresh();
            if (song1.getRecordList() != null) {
                lists.clear();
                lists.addAll(song1.getRecordList());
            }

            if (song1.getRecordCount() == null || song1.getRecordCount().equals("0")) {
                people.setText("");
                practiceBackLayout.setVisibility(View.GONE);
            } else {
                people.setText("已有" + song1.getRecordCount() + "人完成录制");
                practiceBackLayout.setVisibility(View.VISIBLE);
            }

            adapter.notifyDataSetChanged();
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
        }
//        else if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
//            presenter.getBookScore(song.getBookId(), bookType, false);
//        }
        else if (message.what == MethodCode.EVENT_COLLECTCOURSE + 2000 + classId) {
            showInfo((String) message.obj);
            presenter.getBookScore(song.getBookId(), bookType, false);
        } else if (message.what == MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId) {
            showInfo((String) message.obj);
            presenter.getBookScore(song.getBookId(), bookType, false);
        } else if (message.what == MethodCode.EVENT_JOINMATERIAL + 4000 + classId) {
            showInfo((String) message.obj);
            presenter.getBookScore(song.getBookId(), bookType, false);
        } else if (message.what == MethodCode.EVENT_CANCELMATERIAL + 5000 + classId) {
            showInfo((String) message.obj);
            presenter.getBookScore(song.getBookId(), bookType, false);
        }else if (message.what == MethodCode.EVENT_ADDLIKES) {
            showInfo((String) message.obj);
//            presenter.getBookScore(song.getBookId(), bookType, true);
//            adapter.notifyDataSetChanged();
//            if(homepage==0){
//                song1.getRecordList().get(adapter.getPosition()).setIslike(1);
//                song1.getRecordList().get(adapter.getPosition()).setRecordLikes(Integer.parseInt(song1.getRecordList().get(adapter.getPosition()).getRecordLikes()) + 1 + "");
//                adapter.notifyDataSetChanged();
//            }else if(homepage==1){
//                if(pagenum==0){
//                    pagenum=1;
//                }else{
//                    song1.getRecordList().get(adapter.getPosition()).setIslike(1);
//                    song1.getRecordList().get(adapter.getPosition()).setRecordLikes(Integer.parseInt(song1.getRecordList().get(adapter.getPosition()).getRecordLikes()) + 1 + "");
//                    adapter.notifyDataSetChanged();
//                    pagenum=0;
//                }
//            }
//            presenter.getBookScore(song.getBookId(), bookType, false);
        } else if (message.what == MethodCode.EVENT_CANCELLIKES) {
            showInfo((String) message.obj);
//            adapter.notifyDataSetChanged();
//            if(homepage==0){
//                song1.getRecordList().get(adapter.getPosition()).setIslike(0);
//                song1.getRecordList().get(adapter.getPosition()).setRecordLikes(Integer.parseInt(song1.getRecordList().get(adapter.getPosition()).getRecordLikes()) - 1 + "");
//                adapter.notifyDataSetChanged();
//            }else if(homepage==1){
//                if(pagenum==0){
//                    pagenum=1;
//                }else {
//                    song1.getRecordList().get(adapter.getPosition()).setIslike(0);
//                    song1.getRecordList().get(adapter.getPosition()).setRecordLikes(Integer.parseInt(song1.getRecordList().get(adapter.getPosition()).getRecordLikes()) - 1 + "");
//                    adapter.notifyDataSetChanged();
//                    pagenum=0;
//                }
//            }
//            presenter.getBookScore(song.getBookId(), bookType, true);
        } else if (message.what == MethodCode.EVENT_PRACTICE) {
            presenter.getBookScore(song.getBookId(), bookType, false);
        } else if (message.what == MethodCode.EVENT_CLOCKINSHARE) {
            ShareBean shareBean = (ShareBean) message.obj;
            url = shareBean.getUrl();
        } else if (message.what == MethodCode.EVENT_SHARECOIN) {
            ShareCoin shareCoin = (ShareCoin) message.obj;
            if (shareCoin != null) {
                if (shareCoin.getIsGold() == 0) {
                    animationView.setVisibility(View.VISIBLE);
                    clarify.setVisibility(View.VISIBLE);
                    if (shareCoin.getGoldCount() == 1) {
                        animationView.setImageAssetsFolder("coin1/");
                        animationView.setAnimation("coin1.json");
                    } else if (shareCoin.getGoldCount() == 2) {
                        animationView.setImageAssetsFolder("coin2/");
                        animationView.setAnimation("coin2.json");
                    } else if (shareCoin.getGoldCount() == 3) {
                        animationView.setImageAssetsFolder("coin3/");
                        animationView.setAnimation("coin3.json");
                    } else if (shareCoin.getGoldCount() == 4) {
                        animationView.setImageAssetsFolder("coin4/");
                        animationView.setAnimation("coin4.json");
                    } else if (shareCoin.getGoldCount() == 5) {
                        animationView.setImageAssetsFolder("coin5/");
                        animationView.setAnimation("coin5.json");
                    } else if (shareCoin.getGoldCount() == 6) {
                        animationView.setImageAssetsFolder("coin6/");
                        animationView.setAnimation("coin6.json");
                    } else if (shareCoin.getGoldCount() == 7) {
                        animationView.setImageAssetsFolder("coin7/");
                        animationView.setAnimation("coin7.json");
                    } else if (shareCoin.getGoldCount() == 8) {
                        animationView.setImageAssetsFolder("coin8/");
                        animationView.setAnimation("coin8.json");
                    } else if (shareCoin.getGoldCount() == 9) {
                        animationView.setImageAssetsFolder("coin9/");
                        animationView.setAnimation("coin9.json");
                    } else {
                        animationView.setImageAssetsFolder("coin10/");
                        animationView.setAnimation("coin10.json");
                    }
                    animationView.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationView.setVisibility(View.GONE);
                            clarify.setVisibility(View.GONE);
                            animation.cancel();
                            animation.clone();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animationView.loop(false);
                    animationView.playAnimation();
                    SystemUtils.animPool.play(SystemUtils.animMusicMap.get(11), 1, 1, 0, 0, 1);
                } else {
                    showInfo("今日获得金币已达上限");
                }
            }
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
                CountEvent Event_602 = new CountEvent(MethodCode.A0602);
                JAnalyticsInterface.onEvent(this, Event_602);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (musicService != null) {
                    if (musicService.isPlaying()) {
                        musicService.stop();
                    }
                } else {
                    return;
                }
                if (adapter != null) {
                    adapter.stopMedia();
                }
                Intent intent = new Intent(this, ExerciseActivity2.class);
                intent.putExtra("bookId", song.getBookId());
                intent.putExtra("imageUrl", song.getBookImageUrl());
                intent.putExtra("audioType", audioType);
                intent.putExtra("audioSource", audioSource);
                intent.putExtra("title", song.getBookName());
                intent.putExtra("homeworkid", homeworkid);
                intent.putExtra("homeworktype", homeworktype);
                startActivity(intent);
                break;
            case R.id.challenge_btn:
                CountEvent Event_603 = new CountEvent(MethodCode.A0603);
                JAnalyticsInterface.onEvent(this, Event_603);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (musicService != null) {
                    if (musicService.isPlaying()) {
                        musicService.stop();
                    }
                } else {
                    return;
                }
                if (adapter != null) {
                    adapter.stopMedia();
                }
                Intent intent1 = new Intent(this, ChallengeActivity.class);
                intent1.putExtra("bookId", song.getBookId());
                intent1.putExtra("imageUrl", song.getBookImageUrl());
                intent1.putExtra("audioType", audioType);
                intent1.putExtra("audioSource", audioSource);
                intent1.putExtra("homeworkid", homeworkid);
                intent1.putExtra("homeworktype", homeworktype);
                startActivity(intent1);
                break;
            case R.id.pk_btn:
                CountEvent Event_604 = new CountEvent(MethodCode.A0604);
                JAnalyticsInterface.onEvent(this, Event_604);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (musicService != null) {
                    if (musicService.isPlaying()) {
                        musicService.stop();
                    }
                } else {
                    return;
                }
                if (adapter != null) {
                    adapter.stopMedia();
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
                    bundle.putString("pkName", pkUserList.get(position).getName());
                    bundle.putString("avatar", pkUserList.get(position).getAvatar());
                    bundle.putString("imageUrl", song.getBookImageUrl());
                    bundle.putString("title", song.getBookName());
                    bundle.putInt("audioType", audioType);
                    bundle.putInt("audioSource", audioSource);
                    bundle.putInt("homeworkid", homeworkid);
                    bundle.putInt("homeworktype", homeworktype);
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                } else {
                    showInfo("没有pk用户");
                }
                break;
            case R.id.practice_speak:
//                if (application.getSystemUtils().getTag().equals("游客")) {
//                    SystemUtils.toLogin(this);
//                    return;
//                }
//                if (application.getSystemUtils().getChildTag() == 0) {
//                    SystemUtils.toAddChild(this);
//                    return;
//                }
//                if (musicService != null) {
//                    if (musicService.isPlaying()) {
//                        musicService.stop();
//                    }
//                } else {
//                    return;
//                }
//                if (adapter != null) {
//                    adapter.stopMedia();
//                }
//                if (isClick) {
//                    if (!isPlay) {
//                        if (isRecord) {
//                            showInfo("录音结束");
//                            isRecord = false;
//                            speak.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_speak2), null, null);
//                            initRecording();
//                            mRecorderUtil.stopRecording();
//                            if (record_time <= 0) {
//                                showInfo("时长不能为零");
//                                break;
//                            }
//                            //延迟1秒上传
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    presenter.uploadAudioResource(song.getBookId(), 0, audioType, audioSource, 0, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + song1.getBookName() + ".mp3", 0f, song1.getBookName(), record_time, 3, "", song.getBookImageUrl(), 0, homeworkid, homeworktype);
//                                }
//                            }, 1000);
//                        } else {
//                            showInfo("录音开始");
//                            isRecord = true;
//                            record_time = 0;
//                            handler.postDelayed(runnable, 1000);
//                            speak.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_stop2), null, null);
//                            mRecorderUtil.startRecording(song.getBookName());
//                        }
//                    }
//                }
                break;
            case R.id.practice_play:
//                if (application.getSystemUtils().getTag().equals("游客")) {
//                    SystemUtils.toLogin(this);
//                    return;
//                }
//                if (application.getSystemUtils().getChildTag() == 0) {
//                    SystemUtils.toAddChild(this);
//                    return;
//                }
//                if (musicService != null) {
//                    if (musicService.isPlaying()) {
//                        musicService.stop();
//                    }
//                } else {
//                    return;
//                }
//                if (adapter != null) {
//                    adapter.stopMedia();
//                }
//                if (isClick) {
//                    if (!isRecord) {
//                        if (isPlay) {
//                            play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_play2_t), null, null);
//                            try {
//                                mediaPlayer2.pause();
//                                mediaPlayer2.stop();
//                                mediaPlayer2.seekTo(0);
//                            } catch (IllegalStateException e) {
//                                e.printStackTrace();
//                            }
//                            isPlay = false;
//                        } else {
//                            if (song1 != null && song1.getMyResourceUrl() != null) {
////                                isClick = false;
//                                isPlay = true;
//                                play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_stop2), null, null);
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
////                                        playUrl2(song1.getMyResourceUrl());
//                                        playUrl2("https://demoapi.anniekids.net/Public/Upload/123.aac");
//                                    }
//                                }).start();
//                            }
//                        }
//                    }
//                }
                break;
            case R.id.music_frame:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (adapter != null) {
                    adapter.stopMedia();
                }
                if (isClick) {
                    if (!isRecord) {
                        if (musicService == null) {
                            return;
                        }
                        if (song1 == null || resourUrl_list.size() == 0) {
                            return;
                        }
                        if (bookType == 0) {
                            SystemUtils.MusicType = 1;
                            Intent intent2 = new Intent(this, MusicPlayActivity2.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("list", (Serializable) resourUrl_list);
                            bundle.putSerializable("song", song1);
                            bundle.putSerializable("musicPosition", musicPosition);
                            bundle.putString("name", song1.getBookName());
                            bundle.putString("image", song1.getBookImageUrl());
                            bundle.putInt("collectType", collectType);
                            bundle.putString("myResourceUrl", song1.getMyResourceUrl());
                            bundle.putInt("isCollect", song1.getIsCollected());
                            bundle.putInt("origin", 3);
                            bundle.putInt("audioType", audioType);
                            bundle.putInt("audioSource", audioSource);
                            bundle.putInt("resourceId", song.getBookId());
                            bundle.putInt("homeworkid", homeworkid);
                            bundle.putInt("homeworktype", homeworktype);
                            intent2.putExtras(bundle);
                            startActivity(intent2);
                        } else {
                            if (musicService != null) {
                                if (musicService.isPlaying()) {
                                    musicService.stop();
                                }
                            } else {
                                return;
                            }
                            Intent intent3 = new Intent(this, BookPlayActivity2.class);
                            intent3.putExtra("bookId", song.getBookId());
                            intent3.putExtra("imageUrl", song.getBookImageUrl());
                            intent3.putExtra("audioType", audioType);
                            intent3.putExtra("audioSource", audioSource);
                            intent3.putExtra("title", song.getBookName());
                            intent3.putExtra("homeworkid", homeworkid);
                            intent3.putExtra("homeworktype", homeworktype);
                            startActivity(intent3);
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
                CountEvent Event_A090203 = new CountEvent(MethodCode.A090203);
                JAnalyticsInterface.onEvent(this, Event_A090203);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
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
                bundle.putInt("audioSource", audioSource);
                bundle.putInt("audioType", audioType);
                intent3.putExtras(bundle);
                startActivity(intent3);
                popupWindow2.dismiss();
                break;
            case R.id.menu_collect:
                //收藏
                CountEvent Event_A090201 = new CountEvent(MethodCode.A090201);
                JAnalyticsInterface.onEvent(this, Event_A090201);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                popupWindow2.dismiss();
                presenter.clockinShare(2, song.getBookId());
                getWindowGray(true);
                popupWindow3.showAtLocation(popupView2, Gravity.CENTER, 0, 0);

//                if (song1 != null) {
//                    if (song1.getIsCollected() == 0) {
//                        presenter.collectCourse(collectType, audioSource, song.getBookId(), classId);
//                        popupWindow2.dismiss();
//                    } else {
//                        presenter.cancelCollection(collectType, audioSource, song.getBookId(), classId);
//                        popupWindow2.dismiss();
//                    }
//                }
                break;
            case R.id.menu_add_material:
                //加入教材
                CountEvent Event_A090202 = new CountEvent(MethodCode.A090202);
                JAnalyticsInterface.onEvent(this, Event_A090202);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (song1 != null) {
                    if (song1.getIsJoinMaterial() == 0) {
                        presenter.joinMaterial(song.getBookId(), audioSource, audioType, classId);
                        popupWindow2.dismiss();
                    } else {
                        presenter.cancelMaterial(song.getBookId(), classId);
                        popupWindow2.dismiss();
                    }
                }
                break;
            case R.id.practice_recording:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (adapter != null) {
                    adapter.stopMedia();
                }
                if (isClick) {
                    if (!isRecord) {
                        if (musicService == null) {
                            return;
                        }
                        if (song1 == null ) {
                            return;
                        }
                        if (bookType == 0) {
                            if(resourUrl_list.size()==0){
                                return;
                            }

                            SystemUtils.MusicType = 1;
                            Intent intent2 = new Intent(this, MusicPlayActivity2.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putSerializable("list", (Serializable) resourUrl_list);
                            bundle2.putSerializable("song", song1);
                            bundle2.putSerializable("musicPosition", musicPosition);
                            bundle2.putString("name", song1.getBookName());
                            bundle2.putString("image", song1.getBookImageUrl());
                            bundle2.putInt("collectType", collectType);
                            bundle2.putString("myResourceUrl", song1.getMyResourceUrl());
                            bundle2.putInt("isCollect", song1.getIsCollected());
                            bundle2.putInt("origin", 3);
                            bundle2.putInt("audioType", audioType);
                            bundle2.putInt("audioSource", audioSource);
                            bundle2.putInt("resourceId", song.getBookId());
                            bundle2.putInt("homeworkid", homeworkid);
                            bundle2.putInt("homeworktype", homeworktype);
                            intent2.putExtras(bundle2);
                            startActivity(intent2);
                        } else {
                            if (musicService != null) {
                                if (musicService.isPlaying()) {
                                    musicService.stop();
                                }
                            } else {
                                return;
                            }
                            Intent intent4 = new Intent(this, BookPlayActivity2.class);
                            intent4.putExtra("bookId", song.getBookId());
                            intent4.putExtra("imageUrl", song.getBookImageUrl());
                            intent4.putExtra("audioType", audioType);
                            intent4.putExtra("audioSource", audioSource);
                            intent4.putExtra("title", song.getBookName());
                            intent4.putExtra("homeworkid", homeworkid);
                            intent4.putExtra("homeworktype", homeworktype);
                            startActivity(intent4);
                        }
                    }
                }
                break;
            case R.id.share_daka_pengyouquan:
                shareType = 0;
                if (url != null && url.length() != 0) {
                    if (bookType == 0) {
                        shareUtils.shareWechatMoments("我和我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在安娃电台听《" + song.getBookName() + "》", "安娃电台喊你磨耳朵啦...", song.getBookImageUrl(), url);
                    } else {
                        if (audioType == 1) {
                            shareUtils.shareWechatMoments("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + song.getBookName() + "》", "安妮花-磨耳朵 流利读 地道说", song.getBookImageUrl(), url);
                        } else {
                            shareUtils.shareWechatMoments("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + song.getBookName() + "》", "安妮花-磨耳朵 流利读 地道说", song.getBookImageUrl(), url);
                        }
                    }
                }
                break;
            case R.id.share_daka_weixin:
                shareType = 1;
                if (url != null && url.length() != 0) {
                    if (bookType == 0) {
                        shareUtils.shareWechat("我和我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在安娃电台听《" + song.getBookName() + "》", "安娃电台喊你磨耳朵啦...", song.getBookImageUrl(), url);
                    } else {
                        if (audioType == 1) {
                            shareUtils.shareWechat("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + song.getBookName() + "》", "安妮花-磨耳朵 流利读 地道说", song.getBookImageUrl(), url);
                        } else {
                            shareUtils.shareWechat("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + song.getBookName() + "》", "安妮花-磨耳朵 流利读 地道说", song.getBookImageUrl(), url);
                        }

                    }

                }
                break;
            case R.id.share_daka_qq:
                shareType = 2;
                if (url != null && url.length() != 0) {
                    if (bookType == 0) {
                        shareUtils.shareQQ("我和我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在安娃电台听《" + song.getBookName() + "》", "安娃电台喊你磨耳朵啦...", song.getBookImageUrl(), url);
                    } else {
                        if (audioType == 1) {
                            shareUtils.shareQQ("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + song.getBookName() + "》", "安妮花-磨耳朵 流利读 地道说", song.getBookImageUrl(), url);
                        } else {
                            shareUtils.shareQQ("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + song.getBookName() + "》", "安妮花-磨耳朵 流利读 地道说", song.getBookImageUrl(), url);
                        }
                    }
                }
                break;
            case R.id.share_daka_qqzone:
                shareType = 3;
                if (url != null && url.length() != 0) {
                    if (bookType == 0) {
                        shareUtils.shareQZone("我和我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在安娃电台听《" + song.getBookName() + "》", "安娃电台喊你磨耳朵啦...", song.getBookImageUrl(), url);
                    } else {
                        if (audioType == 1) {
                            shareUtils.shareQZone("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + song.getBookName() + "》", "安妮花-磨耳朵 流利读 地道说", song.getBookImageUrl(), url);
                        } else {
                            shareUtils.shareQZone("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + song.getBookName() + "》", "安妮花-磨耳朵 流利读 地道说", song.getBookImageUrl(), url);
                        }
                    }
                }
                break;
            case R.id.daka_share_cancel2:
                popupWindow3.dismiss();
                break;
        }
    }

    private void getWindowGray(boolean tag) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        if (tag) {
            layoutParams.alpha = 0.7f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        } else {
            layoutParams.alpha = 1f;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
//        if (mp == mediaPlayer) {
//            mediaPlayer.start();
//        } else {
        mediaPlayer2.start();
//        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
//        if (mp == mediaPlayer) {
//            isClick = true;
//            isPlay = false;
//            isFinish++;
//            tag2 = true;
//        } else {
        isClick = true;
        isPlay = false;
        play.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_play2_t), null, null);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        allowBindService();
        JAnalyticsInterface.onPageStart(getApplicationContext(),this.getClass().getCanonicalName());

    }

    @Override
    protected void onPause() {
        allowUnBindService();
        super.onPause();
        if (mediaPlayer2 != null) {
            if (mediaPlayer2.isPlaying()) {
                mediaPlayer2.pause();
                mediaPlayer2.stop();
            }
            mediaPlayer2.release();
            mediaPlayer2 = null;
        }
        isClick = true;
        isPlay = false;
        JAnalyticsInterface.onPageEnd(getApplicationContext(),this.getClass().getCanonicalName());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        if (adapter != null) {
            adapter.stopMedia();
        }
        if (lessonTag == 1) {
            /**
             * {@link LessonActivity#onMainEventThread(JTMessage)}
             */
            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_LESSON;
            message.obj = 1;
            EventBus.getDefault().post(message);
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
        popupWindow3.dismiss();
        presenter.shareCoin(1, shareType);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showInfo("分享取消");
        popupWindow3.dismiss();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        showInfo("分享取消");
        popupWindow3.dismiss();
    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onChange(int position) {
        adapter = new BookEndAdapter(this, lists, null, presenter, musicService, true);
        practiceRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
