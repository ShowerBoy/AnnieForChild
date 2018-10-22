package com.annie.annieforchild.ui.activity.pk;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.speech.util.Result;
import com.annie.annieforchild.Utils.speech.util.XmlResultParser;
import com.annie.annieforchild.Utils.views.CircleProgressBar;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.PkResult;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.ChallengeAdapter;
import com.annie.annieforchild.ui.interfaces.OnCountFinishListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * pk——挑战
 * Created by wanglei on 2018/4/3.
 */

public class ChallengeActivity extends BaseActivity implements OnCheckDoubleClick, SongView, PlatformActionListener, PopupWindow.OnDismissListener, OnCountFinishListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private CircleProgressBar circleProgressBar;
    private CircleImageView challengeImage, computer, character1, character2;
    private RecyclerView challengeList;
    private RelativeLayout pkResultLayout;
    private ImageView challengeSpeak, pengyouquan, weixin, qq, qqzone, close, close2, star1, star2, star3, star4, star5;
    private Button tryAgain, tryAgain2;
    private FrameLayout frameLayout;
    private TextView quit;
    private Book book;
    private List<Line> lists;
    private ChallengeAdapter adapter;
    private AlertHelper helper;
    private Dialog dialog;
    private Dialog countDownDialog;
    private CountDownTimer timer;
    private Intent intent;
    private SpeechEvaluator mIse;
    private String results;
    private File file;
    private int bookId;
    private float score; //得分
    private int currentPage = 1; //当前页码
    private int currentLine = 1; //当前行号
    private int totalLines = 0; //当前页总行数
    private GrindEarPresenter presenter;
    private MediaPlayer mediaPlayer;
    private int width, height;
    private String fileName;
    private boolean isPlay = false;
    private boolean isClick = false;
    private boolean isRecording = false; //录音状态
    private int record_time = 0; //录音时长
    Runnable runnable;
    private Handler handler;
    private PopupWindow popupWindow, popupWindow2, popupWindow3;
    private View popupView, popupView2, popupView3;
    private int popupWidth, popupHeight;
    private float star;
    private ShareUtils shareUtils;
    private String imageUrl;
    private int audioType, audioSource;
    private boolean isEnd = false;
    private int round = 0; //轮数
    private Animation leftToRight, rightToLeft;
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_challenge;
    }

    @Override
    protected void initView() {
        frameLayout = findViewById(R.id.challenge_frameLayout);
        challengeSpeak = findViewById(R.id.challenge_speak);
        circleProgressBar = findViewById(R.id.challenge_circleProgressBar);
        challengeImage = findViewById(R.id.challenge_image);
        computer = findViewById(R.id.computer);
        challengeList = findViewById(R.id.challenge_list);
        quit = findViewById(R.id.challenge_quit);
        listener = new CheckDoubleClickListener(this);
        circleProgressBar.setOnClickListener(listener);
        quit.setOnClickListener(listener);
        initTalkBtn();

        intent = getIntent();
        bookId = intent.getIntExtra("bookId", 0);
        audioType = intent.getIntExtra("audioType", 0);
        audioSource = intent.getIntExtra("audioSource", 3);
        imageUrl = intent.getStringExtra("imageUrl");
        Glide.with(this).load(SystemUtils.userInfo.getAvatar()).error(R.drawable.icon_system_photo).into(challengeImage);
        computer.setImageResource(R.drawable.icon_system_photo);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        challengeList.setLayoutManager(manager);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isRecording) {
                    record_time++;
                    handler.postDelayed(this, 1000);
                }
            }
        };
        popupWidth = Math.min(SystemUtils.window_width, SystemUtils.window_height) * 3 / 4;
        popupHeight = Math.max(SystemUtils.window_width, SystemUtils.window_height) * 3 / 5;
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_pk_win, null, false);
        popupView2 = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_pk_lose, null, false);
        popupView3 = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_change, null, false);
        character1 = popupView3.findViewById(R.id.character_1);
        character2 = popupView3.findViewById(R.id.character_2);

        initAnimation();

        Glide.with(this).load(SystemUtils.userInfo.getAvatar()).error(R.drawable.icon_system_photo).into(character1);
        Glide.with(this).load(R.drawable.icon_system_photo).into(character2);
        pengyouquan = popupView.findViewById(R.id.share_pengyouquan);
        weixin = popupView.findViewById(R.id.share_weixin);
        qq = popupView.findViewById(R.id.share_qq);
        qqzone = popupView.findViewById(R.id.share_qqzone);
        close = popupView.findViewById(R.id.close);
        close2 = popupView2.findViewById(R.id.close2);
        tryAgain = popupView.findViewById(R.id.try_again);
        tryAgain2 = popupView2.findViewById(R.id.try_again2);
        star1 = popupView.findViewById(R.id.pkstar_1);
        star2 = popupView.findViewById(R.id.pkstar_2);
        star3 = popupView.findViewById(R.id.pkstar_3);
        star4 = popupView.findViewById(R.id.pkstar_4);
        star5 = popupView.findViewById(R.id.pkstar_5);
        pkResultLayout = popupView.findViewById(R.id.pkResult_layout);
        popupWindow = new PopupWindow(popupView, popupWidth, popupHeight, false);
        popupWindow2 = new PopupWindow(popupView2, popupWidth, popupHeight, false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setOnDismissListener(this);
        popupWindow2.setBackgroundDrawable(new ColorDrawable());
        popupWindow2.setOutsideTouchable(false);
        popupWindow2.setOnDismissListener(this);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        close.setOnClickListener(listener);
        close2.setOnClickListener(listener);
        tryAgain.setOnClickListener(listener);
        tryAgain2.setOnClickListener(listener);
        //
        popupWindow3 = new PopupWindow(popupView3, popupWidth, popupHeight, false);
        popupWindow3.setBackgroundDrawable(new ColorDrawable());
        popupWindow3.setOutsideTouchable(false);
        popupWindow3.setOnDismissListener(this);
    }

    private void initAnimation() {
        leftToRight = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 3f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        rightToLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -3f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        leftToRight.setDuration(2000);
        rightToLeft.setDuration(2000);
        leftToRight.setFillEnabled(true);
        leftToRight.setFillAfter(true);
        rightToLeft.setFillEnabled(true);
        rightToLeft.setFillAfter(true);
    }

    private void initTalkBtn() {
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = frameLayout.getWidth();
                height = frameLayout.getHeight();
                circleProgressBar.setCircleWidth(5);
                circleProgressBar.setFirstColor(getResources().getColor(R.color.white));
                circleProgressBar.setSecondColor(getResources().getColor(R.color.text_orange));
                circleProgressBar.setWidthAndHeight(width, height);
            }
        });
        timer = new CountDownTimer(10 * 1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                circleProgressBar.setProgress((10.0f - millisUntilFinished / 1000.0f) * 100.0f / 10.0f);
            }

            @Override
            public void onFinish() {
                challengeSpeak.setImageResource(R.drawable.icon_speak_big_f);
                circleProgressBar.setProgress(0);
                isPlay = false;
                mIse.stopEvaluating();
                currentLine++;
                if (currentLine <= totalLines) {
                    refresh();
                } else {
                    if (round == 0) {
                        round++;
                        //TODO:互换角色
                        popupWindow3.showAtLocation(popupView3, Gravity.CENTER, 0, 0);
                        character1.startAnimation(leftToRight);
                        character2.startAnimation(rightToLeft);
                        getWindowGray(true);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                popupWindow3.dismiss();
                                getWindowGray(false);
                                Round2();
                            }
                        }, 2000);
                    } else {
                        //挑战结束
                        isEnd = true;
                        round = 0;
                    }
//                    currentLine = 1;
//                    if (currentPage == totalPages) {


//                        presenter.getPkResult(bookId, "", 1);
//                    } else {
//                        continueBtn.setVisibility(View.VISIBLE);
//                    }
                }
            }
        };
    }

    private void Round2() {
        currentLine = 1;
        isClick = true;
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).setSelect(false);
        }
        lists.get(currentLine - 1).setSelect(true);
//        challengeList.scrollToPosition(currentLine - 1);
        challengeList.smoothScrollToPosition(currentLine - 1);
        adapter.notifyDataSetChanged();
        challengeSpeak.setImageResource(R.drawable.icon_speak_big);

        Glide.with(this).load(SystemUtils.userInfo.getAvatar()).error(R.drawable.icon_system_photo).into(computer);
        challengeImage.setImageResource(R.drawable.icon_system_photo);
//        play(lists.get(currentLine - 1).getResourceUrl());
    }

    @Override
    protected void initData() {
        shareUtils = new ShareUtils(this, this);
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        countDownDialog = helper.getCountDownDialog(this);
        adapter = new ChallengeAdapter(this, lists);
        challengeList.setAdapter(adapter);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);

        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "challenge/");
        if (!file.exists()) {
            file.mkdirs();
        }
        mIse = SpeechEvaluator.createEvaluator(this, null);

//        bufferSizeInBytes = AudioRecord.getMinBufferSize(16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
//        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes);

        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getBookAudioData(bookId, 1, null);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private void nextPage() {
        lists.clear();
        lists.addAll(book.getPageContent().get(currentPage - 1).getLineContent());
        totalLines = lists.size();
        refresh();
    }

    private void startRecord() {
        fileName = lists.get(currentLine - 1).getEnTitle().replace(".", "");
        setParams(fileName);
        if (mIse == null) {
            return;
        }
        EvaluatorListener evaluatorListener = getEvaluatorListener();
        int ret = mIse.startEvaluating(lists.get(currentLine - 1).getEnTitle(), null, evaluatorListener);
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_PK_CHALLENGE) {
            book = (Book) message.obj;
            lists.clear();
            totalLines = 0;
            for (int i = 0; i < book.getPageContent().size(); i++) {
                lists.addAll(book.getPageContent().get(i).getLineContent());
                totalLines = totalLines + book.getPageContent().get(i).getLineContent().size();
            }

//            lists.addAll(book.getPageContent().get(currentPage - 1).getLineContent());
            lists.get(0).setSelect(true);

//            totalLines = lists.size();
//            totalPages = book.getBookTotalPages();
            adapter.notifyDataSetChanged();
            countDownDialog.show();

        } else if (message.what == MethodCode.EVENT_GETPKRESULT) {
            PkResult pkResult = (PkResult) message.obj;
            initPopupData(pkResult);
//            presenter.fullrecording(pkResult.getMyscore(), bookId);
        } else if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            if (isEnd) {
                isEnd = false;
                presenter.getPkResult(bookId, "", 1);
            }
        }
    }

    private void initPopupData(PkResult pkResult) {
        float a = Float.parseFloat(pkResult.getMyscore());
        float b = Float.parseFloat(pkResult.getPkscore());
        star = a / b * 5f;

        BigDecimal bigDecimal = new BigDecimal(star);
        star = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        String abc = star + "";
        String[] ddd = abc.split("\\.");
        if (ddd[0].equals("0")) {
            int v = Integer.parseInt(ddd[1]);
            if (v > 4) {
                star1.setImageResource(R.drawable.icon_pkstar_t);
                star2.setImageResource(R.drawable.icon_pkstar_f);
                star3.setImageResource(R.drawable.icon_pkstar_f);
                star4.setImageResource(R.drawable.icon_pkstar_f);
                star5.setImageResource(R.drawable.icon_pkstar_f);
            } else {
                if (v == 0) {
                    star1.setImageResource(R.drawable.icon_pkstar_f);
                } else {
                    star1.setImageResource(R.drawable.icon_pkstar_h);
                }
                star2.setImageResource(R.drawable.icon_pkstar_f);
                star3.setImageResource(R.drawable.icon_pkstar_f);
                star4.setImageResource(R.drawable.icon_pkstar_f);
                star5.setImageResource(R.drawable.icon_pkstar_f);
            }
        } else if (ddd[0].equals("1")) {
            int v = Integer.parseInt(ddd[1]);
            if (v > 4) {
                star1.setImageResource(R.drawable.icon_pkstar_t);
                star2.setImageResource(R.drawable.icon_pkstar_t);
                star3.setImageResource(R.drawable.icon_pkstar_f);
                star4.setImageResource(R.drawable.icon_pkstar_f);
                star5.setImageResource(R.drawable.icon_pkstar_f);
            } else {
                star1.setImageResource(R.drawable.icon_pkstar_t);
                star2.setImageResource(R.drawable.icon_pkstar_h);
                star3.setImageResource(R.drawable.icon_pkstar_f);
                star4.setImageResource(R.drawable.icon_pkstar_f);
                star5.setImageResource(R.drawable.icon_pkstar_f);
            }
        } else if (ddd[0].equals("2")) {
            int v = Integer.parseInt(ddd[1]);
            if (v > 4) {
                star1.setImageResource(R.drawable.icon_pkstar_t);
                star2.setImageResource(R.drawable.icon_pkstar_t);
                star3.setImageResource(R.drawable.icon_pkstar_t);
                star4.setImageResource(R.drawable.icon_pkstar_f);
                star5.setImageResource(R.drawable.icon_pkstar_f);
            } else {
                star1.setImageResource(R.drawable.icon_pkstar_t);
                star2.setImageResource(R.drawable.icon_pkstar_t);
                star3.setImageResource(R.drawable.icon_pkstar_h);
                star4.setImageResource(R.drawable.icon_pkstar_f);
                star5.setImageResource(R.drawable.icon_pkstar_f);
            }
        } else if (ddd[0].equals("3")) {
            int v = Integer.parseInt(ddd[1]);
            if (v > 4) {
                star1.setImageResource(R.drawable.icon_pkstar_t);
                star2.setImageResource(R.drawable.icon_pkstar_t);
                star3.setImageResource(R.drawable.icon_pkstar_t);
                star4.setImageResource(R.drawable.icon_pkstar_t);
                star5.setImageResource(R.drawable.icon_pkstar_f);
            } else {
                star1.setImageResource(R.drawable.icon_pkstar_t);
                star2.setImageResource(R.drawable.icon_pkstar_t);
                star3.setImageResource(R.drawable.icon_pkstar_t);
                star4.setImageResource(R.drawable.icon_pkstar_h);
                star5.setImageResource(R.drawable.icon_pkstar_f);
            }
        } else if (ddd[0].equals("4")) {
            int v = Integer.parseInt(ddd[1]);
            if (v > 4) {
                star1.setImageResource(R.drawable.icon_pkstar_t);
                star2.setImageResource(R.drawable.icon_pkstar_t);
                star3.setImageResource(R.drawable.icon_pkstar_t);
                star4.setImageResource(R.drawable.icon_pkstar_t);
                star5.setImageResource(R.drawable.icon_pkstar_t);
            } else {
                star1.setImageResource(R.drawable.icon_pkstar_t);
                star2.setImageResource(R.drawable.icon_pkstar_t);
                star3.setImageResource(R.drawable.icon_pkstar_t);
                star4.setImageResource(R.drawable.icon_pkstar_t);
                star5.setImageResource(R.drawable.icon_pkstar_h);
            }
        } else if (ddd[0].equals("5")) {
            star1.setImageResource(R.drawable.icon_pkstar_t);
            star2.setImageResource(R.drawable.icon_pkstar_t);
            star3.setImageResource(R.drawable.icon_pkstar_t);
            star4.setImageResource(R.drawable.icon_pkstar_t);
            star5.setImageResource(R.drawable.icon_pkstar_t);
        }
        if (pkResult.getResult() == 0) {
            //失败
            popupWindow2.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
        } else {
            //成功
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        }
        getWindowGray(true);
    }

    private void refresh() {
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).setSelect(false);
        }
        lists.get(currentLine - 1).setSelect(true);
//        challengeList.scrollToPosition(currentLine - 1);
        challengeList.smoothScrollToPosition(currentLine - 1);
        adapter.notifyDataSetChanged();
        play(lists.get(currentLine - 1).getResourceUrl());
    }

    private void play(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCountDownFinish() {
        play(lists.get(currentLine - 1).getResourceUrl());
    }

    private void setParams(String fileName) {
        mIse.setParameter(SpeechConstant.LANGUAGE, "en_us"); // 设置评测语言
        mIse.setParameter(SpeechConstant.ISE_CATEGORY, "read_sentence"); // 设置需要评测的类型
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mIse.setParameter(SpeechConstant.VAD_BOS, "12000"); // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIse.setParameter(SpeechConstant.VAD_EOS, "12000"); // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1"); // 语音输入超时时间，即用户最多可以连续说多长时间；
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, "complete");// 设置结果等级（中文仅支持complete）
        mIse.setParameter(SpeechConstant.SAMPLE_RATE, "16000");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "challenge/" + fileName + ".pcm");
        //通过writeaudio方式直接写入音频时才需要此设置
        //mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }

    private EvaluatorListener getEvaluatorListener() {
        EvaluatorListener evaluatorListener = null;
        evaluatorListener = new EvaluatorListener() {
            @Override
            public void onResult(EvaluatorResult result, boolean isLast) {
                if (isLast) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(result.getResultString());

                    results = builder.toString();

                    if (!TextUtils.isEmpty(results)) {
                        XmlResultParser resultParser = new XmlResultParser();
                        Result finalResult = resultParser.parse(results);
//                        int time = finalResult.time_len;
                        if (null != finalResult) {
                            score = finalResult.total_score;
                            BigDecimal bigDecimal = new BigDecimal(score);
                            score = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                            presenter.uploadAudioResource(bookId, currentPage, audioType, audioSource, currentLine, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "challenge/" + fileName + ".pcm", score, fileName, record_time, 1, "", imageUrl);
                        } else {
//                        showInfo("解析结果为空");
                        }
                    }
//                    isClick = false;
                }
            }

            @Override
            public void onError(SpeechError error) {
//            mIseStartButton.setEnabled(true);
                if (error != null) {
//                showTip("error:"+ error.getErrorCode() + "," + error.getErrorDescription());
//                mResultEditText.setText("");
//                mResultEditText.setHint("请点击“开始评测”按钮");
                } else {
//                Log.d(TAG, "evaluator over");
                }
                isClick = true;
            }

            @Override
            public void onBeginOfSpeech() {
                // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
                record_time = 0;
                isRecording = true;
                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void onEndOfSpeech() {
                // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
                isRecording = false;
            }

            @Override
            public void onVolumeChanged(int volume, byte[] data) {
//            showInfo("当前音量：" + volume);
//            Log.d(TAG, "返回音频数据：" + data.length);
            }

            @Override
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
                // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
                //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                //		Log.d(TAG, "session id =" + sid);
                //	}
            }
        };
        return evaluatorListener;
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
    public void onCompletion(MediaPlayer mp) {
        isClick = true;
        currentLine++;
        if (currentLine > totalLines) {
            if (round == 0) {
                round++;
                //TODO:互换角色
                popupWindow3.showAtLocation(popupView3, Gravity.CENTER, 0, 0);
                character1.startAnimation(leftToRight);
                character2.startAnimation(rightToLeft);
                getWindowGray(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow3.dismiss();
                        getWindowGray(false);
                        Round2();
                    }
                }, 2000);
            } else {
                //挑战结束
                isEnd = true;
                round = 0;
                if (isEnd) {
                    isEnd = false;
                    presenter.getPkResult(bookId, "", 1);
                }
            }
        } else {
            for (int i = 0; i < lists.size(); i++) {
                lists.get(i).setSelect(false);
            }
            lists.get(currentLine - 1).setSelect(true);
//            challengeList.scrollToPosition(currentLine - 1);
            challengeList.smoothScrollToPosition(currentLine - 1);
            adapter.notifyDataSetChanged();
            challengeSpeak.setImageResource(R.drawable.icon_speak_big);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
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
    public void onDismiss() {
        getWindowGray(false);
        isPlay = false;
        if (isEnd) {
            finish();
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showInfo("分享失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        showInfo("取消分享");
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.challenge_circleProgressBar:
                if (isClick) {
                    if (isPlay) {
                        timer.cancel();
                        challengeSpeak.setImageResource(R.drawable.icon_speak_big_f);
                        circleProgressBar.setProgress(0);
                        isPlay = false;
                        isClick = false;
                        mIse.stopEvaluating();
                        currentLine++;
                        if (currentLine <= totalLines) {
                            refresh();
                        } else {
//                            currentLine = 1;
//                            if (currentPage == totalPages) {
                            if (round == 0) {
                                round++;
                                //TODO:互换角色
                                popupWindow3.showAtLocation(popupView3, Gravity.CENTER, 0, 0);
                                character1.startAnimation(leftToRight);
                                character2.startAnimation(rightToLeft);
                                getWindowGray(true);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        popupWindow3.dismiss();
                                        getWindowGray(false);
                                        Round2();
                                    }
                                }, 2000);
                            } else {
                                //挑战结束
                                isEnd = true;
                                round = 0;
                            }
//                                presenter.getPkResult(bookId, "", 1);
//                            } else {
//                                continueBtn.setVisibility(View.VISIBLE);
//                            }
                        }
                    } else {
                        challengeSpeak.setImageResource(R.drawable.icon_stop_big);
                        timer.start();
                        isPlay = true;
                        startRecord();
                    }
                }
                break;
            case R.id.challenge_quit:
                isPlay = false;
                finish();
                break;
            case R.id.continue_btn:
//                currentPage++;
//                if (currentPage > totalPages) {
//                    showInfo("结束");
//                } else {
//                    continueBtn.setVisibility(View.GONE);
//                    nextPage();
//                }
                break;
            case R.id.share_pengyouquan:
                shareUtils.shareWechatMoments("我得到了更高的成绩，你也一起来吧", "安妮花教育", null, "https://demoapi.anniekids.net/api/searchApi/index");
                break;
            case R.id.share_weixin:
                shareUtils.shareWechat("我得到了更高的成绩，你也一起来吧", "安妮花教育", null, "https://demoapi.anniekids.net/api/searchApi/index");
                break;
            case R.id.share_qq:
                shareUtils.shareQQ("我得到了更高的成绩，你也一起来吧", "安妮花教育", null, "https://demoapi.anniekids.net/api/searchApi/index");
                break;
            case R.id.share_qqzone:
                shareUtils.shareQZone("我得到了更高的成绩，你也一起来吧", "安妮花教育", null, "https://demoapi.anniekids.net/api/searchApi/index");
                break;
            case R.id.close:
                popupWindow.dismiss();
                finish();
                break;
            case R.id.close2:
                popupWindow2.dismiss();
                finish();
                break;
            case R.id.try_again:
//                Intent intent = new Intent(this, ChallengeActivity.class);
//                intent.putExtra("bookId", bookId);
//                startActivity(intent);
                finish();
                break;
            case R.id.try_again2:
                finish();
                break;
        }
    }
}
