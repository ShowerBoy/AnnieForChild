package com.annie.annieforchild.ui.activity.pk;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.bean.ShareCoin;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.ChallengeAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.example.lamemp3.MP3Recorder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 录音界面
 * Created by wanglei on 2018/9/10.
 */

public class RecordingActivity extends BaseActivity implements SongView, OnCheckDoubleClick, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, PlatformActionListener {
    private ImageView back, share, pengyouquan, weixin, qq, qqzone, animationPic, preview, record, play, luckStart, clarity, shareCancel, luckdrawBg, pengyouquan2, weixin2, qq2, qqzone2;
    private TextView title, shareCancel2, coinCount;
    private LinearLayout recordPreview, recordBtn, recordPlay, recordingLayout;
    private AnimationDrawable animationDrawable;
    private RelativeLayout luckDrawLayout;
    private RecyclerView recycler;
    private LottieAnimationView animationView, coinAnimationView;
    private GrindEarPresenter presenter;
    private CheckDoubleClickListener listener;
    private RecorderAndPlayUtil mRecorderUtil = null;
    private MediaPlayer mediaPlayer, mediaPlayer2;
    private static final String DIR = "LAME/mp3/";
    private boolean isRecord = false;
    private int record_time = 0; //录音时长
    private int duration = 0;
    private boolean isClick = true;
    private boolean isRecordPlay = false; //是否播放录音
    private boolean isPlay = false; //是否播放
    private int bookId, audioSource, audioType;
    private String resourceUrl = "", myResourceUrl = "", bookName, bookImageUrl, url;
    private Intent intent;
    private Book book;
    private List<Line> lists;
    private ChallengeAdapter adapter;
    private ShareUtils shareUtils;
    private AlertHelper helper;
    private Dialog dialog;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int homeworkid, homeworktype;
    private Random random;
    private PopupWindow popupWindow;
    private View popupView;
    private int animationCode = -1, shareType, type;
    private boolean tag = true;
    private PopupWindow popupWindow2;

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
        recordPreview = findViewById(R.id.recording_preview);
        recordBtn = findViewById(R.id.recording_record);
        recordPlay = findViewById(R.id.recording_play);
        animationPic = findViewById(R.id.record_animation);
        recycler = findViewById(R.id.recording_recycler);
        preview = findViewById(R.id.recording_record_preview);
        record = findViewById(R.id.recording_record_record);
        play = findViewById(R.id.recording_record_play);
        clarity = findViewById(R.id.recording_clarity);
        animationView = findViewById(R.id.lottie_animation);
        luckDrawLayout = findViewById(R.id.luckdraw_layout);
        luckdrawBg = findViewById(R.id.luckdraw_bg);
        recordingLayout = findViewById(R.id.recording_layout);
        coinAnimationView = findViewById(R.id.coin_animation);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        share.setOnClickListener(listener);
        recordBtn.setOnClickListener(listener);
        recordPlay.setOnClickListener(listener);
        recordPreview.setOnClickListener(listener);
        animationPic.setOnClickListener(listener);

        pengyouquan = findViewById(R.id.share_daka_pengyouquan);
        weixin = findViewById(R.id.share_daka_weixin);
        qq = findViewById(R.id.share_daka_qq);
        qqzone = findViewById(R.id.share_daka_qqzone);
        shareCancel = findViewById(R.id.luckdraw_close);
        luckStart = findViewById(R.id.luckdraw_start);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        shareCancel.setOnClickListener(listener);
        luckStart.setOnClickListener(listener);

//        animationDrawable = (AnimationDrawable) luckdrawBg.getDrawable();
//        animationDrawable.setOneShot(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);

        popupWindow = new PopupWindow(this);
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_share_daka_item2, null, false);
        pengyouquan2 = popupView.findViewById(R.id.share_daka_pengyouquan);
        weixin2 = popupView.findViewById(R.id.share_daka_weixin);
        qq2 = popupView.findViewById(R.id.share_daka_qq);
        qqzone2 = popupView.findViewById(R.id.share_daka_qqzone);
        shareCancel2 = popupView.findViewById(R.id.daka_share_cancel2);
        coinCount = popupView.findViewById(R.id.coin_count);
        pengyouquan2.setOnClickListener(listener);
        weixin2.setOnClickListener(listener);
        qq2.setOnClickListener(listener);
        qqzone2.setOnClickListener(listener);
        shareCancel2.setOnClickListener(listener);
        coinCount.setText("分享+5金币");
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
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
        mediaPlayer = new MediaPlayer();
        mediaPlayer2 = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer2.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer2.setOnCompletionListener(this);
        intent = getIntent();
        if (intent != null) {
            bookId = intent.getIntExtra("bookId", 0);
            audioSource = intent.getIntExtra("audioSource", 0);
            audioType = intent.getIntExtra("audioType", 0);
//            resourceUrl = intent.getStringExtra("resourceUrl");
//            myResourceUrl = intent.getStringExtra("myResourceUrl");
            bookName = intent.getStringExtra("bookName");
            bookImageUrl = intent.getStringExtra("bookImageUrl");
            homeworkid = intent.getIntExtra("homeworkid", homeworkid);
            homeworktype = intent.getIntExtra("homeworktype", -1);
            title.setText(bookName);
//            if (myResourceUrl != null && myResourceUrl.length() != 0) {
//                play.setImageResource(R.drawable.icon_book_play2);
//            } else {
//                play.setImageResource(R.drawable.icon_book_play2_f);
//            }
        }
        random = new Random();
//        animationCode = random.nextInt(10) + 1;
//        initAnimation(animationCode);
        adapter = new ChallengeAdapter(this, lists, 1);
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

                break;
        }
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
            isRecord = false;
            AudioBean bean = (AudioBean) message.obj;
            if (bean != null) {
                if (bean.getResourceUrl() != null) {
                    myResourceUrl = bean.getResourceUrl();
                    play.setImageResource(R.drawable.icon_book_play2);
                }
                presenter.clockinShare(3, bookId);
                animationCode = random.nextInt(10) + 1;
                setClarity(true);
                setLottieAnimation(animationCode);
            }
        } else if (message.what == MethodCode.EVENT_PK_CHALLENGE) {
            book = (Book) message.obj;
            if (book.getPath() != null) {
                resourceUrl = book.getPath();
            }
            if (book.getMyResourceUrl() != null) {
                myResourceUrl = book.getMyResourceUrl();
            }
            if (myResourceUrl != null && myResourceUrl.length() != 0) {
                play.setImageResource(R.drawable.icon_book_play2);
            } else {
                play.setImageResource(R.drawable.icon_book_play2_f);
            }
            lists.clear();
            for (int i = 0; i < book.getPageContent().size(); i++) {
                lists.addAll(book.getPageContent().get(i).getLineContent());
            }
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_CLOCKINSHARE) {
            ShareBean shareBean = (ShareBean) message.obj;
            url = shareBean.getUrl();
        } else if (message.what == MethodCode.EVENT_NECTAR) {
//            popupView.setVisibility(View.VISIBLE);
            int type = (int) message.obj;
            if (type == 5) {
                luckStart.setVisibility(View.VISIBLE);
            } else if (type == 6) {
//                luckStart.setVisibility(View.GONE);
            } else if (type == 0) {
//                luckStart.setVisibility(View.GONE);
                presenter.luckDraw(1);
            } else if (type == 1) {
//                luckStart.setVisibility(View.GONE);
                presenter.luckDraw(2);
            } else if (type == 2) {
//                luckStart.setVisibility(View.GONE);
                presenter.luckDraw(5);
            } else if (type == 3) {
//                luckStart.setVisibility(View.GONE);
                presenter.luckDraw(10);
            } else if (type == 4) {
//                luckStart.setVisibility(View.GONE);
                presenter.luckDraw(20);
            }
        } else if (message.what == MethodCode.EVENT_SHARECOIN) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            showLuckDrawLayout(false);

            ShareCoin shareCoin = (ShareCoin) message.obj;
            if (shareCoin != null) {
                if (shareCoin.getIsGold() == 0) {
                    if (shareCoin.getGoldCount() == 1) {
                        coinAnimationView.setImageAssetsFolder("coin1/");
                        coinAnimationView.setAnimation("coin1.json");
                    } else if (shareCoin.getGoldCount() == 2) {
                        coinAnimationView.setImageAssetsFolder("coin2/");
                        coinAnimationView.setAnimation("coin2.json");
                    } else if (shareCoin.getGoldCount() == 3) {
                        coinAnimationView.setImageAssetsFolder("coin3/");
                        coinAnimationView.setAnimation("coin3.json");
                    } else if (shareCoin.getGoldCount() == 4) {
                        coinAnimationView.setImageAssetsFolder("coin4/");
                        coinAnimationView.setAnimation("coin4.json");
                    } else if (shareCoin.getGoldCount() == 5) {
                        coinAnimationView.setImageAssetsFolder("coin5/");
                        coinAnimationView.setAnimation("coin5.json");
                    } else if (shareCoin.getGoldCount() == 6) {
                        coinAnimationView.setImageAssetsFolder("coin6/");
                        coinAnimationView.setAnimation("coin6.json");
                    } else if (shareCoin.getGoldCount() == 7) {
                        coinAnimationView.setImageAssetsFolder("coin7/");
                        coinAnimationView.setAnimation("coin7.json");
                    } else if (shareCoin.getGoldCount() == 8) {
                        coinAnimationView.setImageAssetsFolder("coin8/");
                        coinAnimationView.setAnimation("coin8.json");
                    } else if (shareCoin.getGoldCount() == 9) {
                        coinAnimationView.setImageAssetsFolder("coin9/");
                        coinAnimationView.setAnimation("coin9.json");
                    } else {
                        coinAnimationView.setImageAssetsFolder("coin10/");
                        coinAnimationView.setAnimation("coin10.json");
                    }
                    coinAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            coinAnimationView.setVisibility(View.GONE);
                            setClarity(false);
                            showLuckDrawLayout(false);
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
                    coinAnimationView.loop(false);
                    coinAnimationView.playAnimation();
                    coinAnimationView.setVisibility(View.VISIBLE);
                    SystemUtils.animPool.play(SystemUtils.animMusicMap.get(11), 1, 1, 0, 0, 1);
                } else {
                    showInfo("今日获得金币已达上限");
                }
            }

        } else if (message.what == MethodCode.EVENT_LUCKDRAW) {
            AudioBean audioBean = (AudioBean) message.obj;
            if (audioBean != null) {
                if (audioBean.getIsNectar() == 1) {
                    showInfo("今日获得花蜜已达上限");
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.recording_back:
                if (!isClick) {
                    return;
                }
                finish();
                break;
            case R.id.recording_share:
                //录音分享
                if (!isClick) {
                    return;
                }
                if (!isPlay) {
                    if (!isRecord) {
                        if (myResourceUrl != null && myResourceUrl.length() != 0) {
                            presenter.clockinShare(3, bookId);
                            getWindowGray(true);
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                        } else {
                            showInfo("请先录音");
                        }
                    }
                }
                break;
            case R.id.recording_preview:
                if (!isClick) {
                    return;
                }
                if (isRecordPlay) {
                    return;
                }
                if (isRecord) {
                    return;
                }
                if (!isPlay) {
                    if (resourceUrl == null || resourceUrl.length() == 0) {
                        return;
                    }
                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            mediaPlayer.stop();
                            mediaPlayer.seekTo(0);
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    preview.setImageResource(R.drawable.icon_book_stop);
                    isPlay = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            playUrl(resourceUrl);
                        }
                    }).start();
                } else {
                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            mediaPlayer.stop();
                            mediaPlayer.seekTo(0);
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    preview.setImageResource(R.drawable.icon_book_preview2);
                    isPlay = false;
                }
                break;
            case R.id.recording_record:
                if (isRecordPlay) {
                    return;
                }
                if (isClick) {
                    if (!isPlay) {
                        if (isRecord) {
                            showInfo("录音结束");
//                            isRecord = false;
                            record.setImageResource(R.drawable.icon_book_record2);
//                            speak.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.icon_speak2), null, null);
                            initRecording();
                            mRecorderUtil.stopRecording();
                            if (record_time <= 20) {
                                showInfo("录音时长过短，至少超过20s");
                                isRecord = false;
                                break;
                            }
                            //延迟1秒上传
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    presenter.uploadAudioResource(bookId, 0, audioType, audioSource, 0, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + bookName + ".mp3", 0f, bookName, record_time, 3, "", bookImageUrl, animationCode, homeworkid, homeworktype);
                                }
                            }, 1000);
                        } else {
                            showInfo("录音开始");
                            isRecord = true;
                            record_time = 0;
                            handler.postDelayed(runnable, 1000);
                            record.setImageResource(R.drawable.icon_book_stop);
                            mRecorderUtil.startRecording(bookName);
                        }
                    }
                }
                break;
            case R.id.recording_play:
                if (isPlay) {
                    return;
                }
                if (isClick) {
                    if (!isRecord) {
                        if (isRecordPlay) {
                            play.setImageResource(R.drawable.icon_book_play2);
                            try {
                                mediaPlayer2.pause();
                                mediaPlayer2.stop();
                                mediaPlayer2.seekTo(0);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                            isRecordPlay = false;
                        } else {
                            if (myResourceUrl != null && myResourceUrl.length() != 0) {
                                isRecordPlay = true;
                                play.setImageResource(R.drawable.icon_book_stop);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        playUrl2(myResourceUrl);
                                    }
                                }).start();
                            }
                        }
                    }
                }
                break;
            case R.id.luckdraw_start:
                luckStart.setVisibility(View.GONE);
                luckdrawBg.setImageResource(R.drawable.luck_draw);
                animationDrawable = (AnimationDrawable) luckdrawBg.getDrawable();
                animationDrawable.setOneShot(true);
                animationDrawable.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        type = random.nextInt(7);
                        SystemUtils.setBackGray(RecordingActivity.this, true);
                        popupWindow2 = SystemUtils.getNectarPopup(RecordingActivity.this, type);
                        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                SystemUtils.setBackGray(RecordingActivity.this, false);
                                JTMessage message = new JTMessage();
                                message.what = MethodCode.EVENT_NECTAR;
                                message.obj = type;
                                EventBus.getDefault().post(message);
                            }
                        });
                        popupWindow2.showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                popupWindow2.dismiss();
                            }
                        }, 2000);
                    }
                }, 1500);
                break;
            case R.id.share_daka_pengyouquan:
                shareType = 0;
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechatMoments("分享我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的英文录音《" + bookName + "》，一起加入磨耳朵", "安妮花-磨耳朵 流利读 地道说", null, url);
                }
                break;
            case R.id.share_daka_weixin:
                shareType = 1;
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechat("分享我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的英文录音《" + bookName + "》，一起加入磨耳朵", "安妮花-磨耳朵 流利读 地道说", null, url);
                }
                break;
            case R.id.share_daka_qq:
                shareType = 2;
                if (url != null && url.length() != 0) {
                    shareUtils.shareQQ("分享我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的英文录音《" + bookName + "》，一起加入磨耳朵", "安妮花-磨耳朵 流利读 地道说", null, url);
                }
                break;
            case R.id.share_daka_qqzone:
                shareType = 3;
                if (url != null && url.length() != 0) {
                    shareUtils.shareQZone("分享我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花的英文录音《" + bookName + "》，一起加入磨耳朵", "安妮花-磨耳朵 流利读 地道说", null, url);
                }
                break;
            case R.id.luckdraw_close:
                setClarity(false);
                showLuckDrawLayout(false);
                break;
            case R.id.daka_share_cancel2:
                popupWindow.dismiss();
                break;
            case R.id.record_animation:
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

    private void showLuckDrawLayout(boolean tag) {
        if (tag) {
//            setClarity(true);
            clarity.setVisibility(View.VISIBLE);
            luckDrawLayout.setVisibility(View.VISIBLE);
        } else {
            luckDrawLayout.setVisibility(View.GONE);
        }
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
        if (mp == mediaPlayer2) {
            isRecordPlay = false;
            play.setImageResource(R.drawable.icon_book_play2);
        } else {
            isPlay = false;
            preview.setImageResource(R.drawable.icon_book_preview2);
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
    }

    private void setClarity(boolean tag) {
        if (tag) {
            clarity.setVisibility(View.VISIBLE);
            isClick = false;
//            recordingLayout.setOnClickListener(null);
        } else {
            clarity.setVisibility(View.GONE);
            isClick = true;
//            recordingLayout.setOnClickListener(listener);
        }
    }

    private void setLottieAnimation(int animationCode) {
//        if (animationView.isAnimating()) {
//            return;
//        }

        switch (animationCode) {
            case 1:
                animationView.setImageAssetsFolder("amazing/");
                animationView.setAnimation("amazing.json");
                break;
            case 2:
                animationView.setImageAssetsFolder("awesome/");
                animationView.setAnimation("awesome.json");
                break;
            case 3:
                animationView.setImageAssetsFolder("bingo/");
                animationView.setAnimation("bingo.json");
                break;
            case 4:
                animationView.setImageAssetsFolder("excellent/");
                animationView.setAnimation("excellent.json");
                break;
            case 5:
                animationView.setImageAssetsFolder("good_observation/");
                animationView.setAnimation("good_observation.json");
                break;
            case 6:
                animationView.setImageAssetsFolder("good_try/");
                animationView.setAnimation("good_try.json");
                break;
            case 7:
                animationView.setImageAssetsFolder("great/");
                animationView.setAnimation("great.json");
                break;
            case 8:
                animationView.setImageAssetsFolder("great_job/");
                animationView.setAnimation("great_job.json");
                break;
            case 9:
                animationView.setImageAssetsFolder("nice_going/");
                animationView.setAnimation("nice_going.json");
                break;
            case 10:
                animationView.setImageAssetsFolder("super/");
                animationView.setAnimation("super.json");
                break;
        }

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                if (isLuckDraw) {
                setClarity(true);
                initAnimation(animationCode);
                animationView.setVisibility(View.GONE);
//                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                showLuckDrawLayout(true);
//                animationView.cancelAnimation();
                animation.cancel();
                animation.clone();
//                    isLuckDraw = false;
//                } else {
//                    setClarity(false);
//                    animationView.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
//                setClarity(false);
//                animationView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animationView.loop(false);
        animationView.playAnimation();
        animationView.setVisibility(View.VISIBLE);
        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);
    }


    private void getWindowGray(boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
        presenter.shareCoin(0, shareType);
//        popupWindow.dismiss();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showInfo("分享取消");
//        popupWindow.dismiss();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        showInfo("分享取消");
//        popupWindow.dismiss();
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
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        isClick = true;
        isPlay = false;
        isRecordPlay = false;
        SystemUtils.MusicType = 0;
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
