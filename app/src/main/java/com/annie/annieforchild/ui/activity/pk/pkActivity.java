package com.annie.annieforchild.ui.activity.pk;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
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
import com.example.lamemp3.PrivateInfo;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;
import com.tencent.taisdk.TAIErrCode;
import com.tencent.taisdk.TAIError;
import com.tencent.taisdk.TAIOralEvaluation;
import com.tencent.taisdk.TAIOralEvaluationCallback;
import com.tencent.taisdk.TAIOralEvaluationData;
import com.tencent.taisdk.TAIOralEvaluationEvalMode;
import com.tencent.taisdk.TAIOralEvaluationFileType;
import com.tencent.taisdk.TAIOralEvaluationListener;
import com.tencent.taisdk.TAIOralEvaluationParam;
import com.tencent.taisdk.TAIOralEvaluationRet;
import com.tencent.taisdk.TAIOralEvaluationServerType;
import com.tencent.taisdk.TAIOralEvaluationStorageMode;
import com.tencent.taisdk.TAIOralEvaluationTextMode;
import com.tencent.taisdk.TAIOralEvaluationWorkMode;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * pk——pk
 * Created by wanglei on 2018/4/4.
 */

public class pkActivity extends BaseActivity implements OnCheckDoubleClick, SongView, PlatformActionListener, PopupWindow.OnDismissListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, OnCountFinishListener {
    private TextView quit, player1Name, player2Name;
    private ProgressBar progressBar;
    private CircleImageView player1, player2, character1, character2;
    private CircleProgressBar circleProgressBar;
    private RecyclerView pkList;
    private ImageView pkSpeak, pengyouquan, weixin, qq, qqzone, close, close2, star1, star2, star3, star4, star5, empty, mic1, mic2;
    private Button tryAgain1, tryAgain2;
    private FrameLayout pkFrameLayout;
    private CountDownTimer timer;
    private int width, height;
    private GrindEarPresenter presenter;
    private Intent intent;
    private Bundle bundle;
    private MediaPlayer mediaPlayer;
    private SpeechEvaluator mIse;
    private ChallengeAdapter adapter;
    private File file;
    private String results;
    private Book book;
    private List<Line> lists;
    private int bookId;
    private String pkUserName, avatar, pkName;
    private float score; //得分
    private int currentPage = 1; //当前页码
    private int currentLine = 1; //当前行号
    private int totalLines; //当前页总行数
    private String fileName;
    private String imageUrl;
    private AlertHelper helper;
    private Dialog dialog;
    private Dialog countDownDialog;
    private boolean isPlay = false;
    private boolean isRecord = false;
    private boolean isClick = false;
    private boolean isRecording = false; //录音状态
    private int record_time = 0; //录音时长
    Runnable runnable;
    private Handler handler;
    private PopupWindow popupWindow1, popupWindow2, popupWindow3;
    private View popupView1, popupView2, popupView3;
    private int popupWidth, popupHeight;
    private float star;
    private ShareUtils shareUtils;
    private int audioType, audioSource;
    private boolean isEnd = false;
    private int round = 0; //轮数
    private int homeworkid, homeworktype;
    private String title;
    private Animation leftToRight, rightToLeft;
    private CheckDoubleClickListener listener;
    private TAIOralEvaluation oral;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pk;
    }

    @Override
    protected void initView() {
        quit = findViewById(R.id.pk_quit);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        pkSpeak = findViewById(R.id.pk_speak);
        empty = findViewById(R.id.pk_empty);
        player1Name = findViewById(R.id.pk_player1);
        player2Name = findViewById(R.id.pk_player2);
        progressBar = findViewById(R.id.pk_progress);
        pkFrameLayout = findViewById(R.id.pk_frameLayout);
        circleProgressBar = findViewById(R.id.pk_circleProgressBar);
        mic1 = findViewById(R.id.player1_mic);
        mic2 = findViewById(R.id.player2_mic);
        pkList = findViewById(R.id.pk_list);
        listener = new CheckDoubleClickListener(this);
        quit.setOnClickListener(listener);
        circleProgressBar.setOnClickListener(listener);

        initTalkBtn();
        intent = getIntent();
        bundle = intent.getExtras();
        bookId = bundle.getInt("bookId");
        pkUserName = bundle.getString("pkUserName");
        pkName = bundle.getString("pkName");
        imageUrl = bundle.getString("imageUrl");
        title = bundle.getString("title");
        avatar = bundle.getString("avatar");
        audioType = bundle.getInt("audioType", 0);
        audioSource = bundle.getInt("audioSource", 3);
        homeworkid = bundle.getInt("homeworkid", 0);
        homeworktype = bundle.getInt("homeworktype", -1);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        pkList.setLayoutManager(manager);

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

        popupWidth = Math.min(application.getSystemUtils().getWindow_width(), application.getSystemUtils().getWindow_height()) * 3 / 4;
        popupHeight = Math.max(application.getSystemUtils().getWindow_width(), application.getSystemUtils().getWindow_height()) * 3 / 5;
        popupView1 = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_pk_win, null, false);
        popupView2 = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_pk_lose, null, false);
        popupView3 = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_change, null, false);
        character1 = popupView3.findViewById(R.id.character_1);
        character2 = popupView3.findViewById(R.id.character_2);

        initAnimation();

        Glide.with(this).load(application.getSystemUtils().getUserInfo().getAvatar()).error(R.drawable.icon_system_photo).into(character1);
        Glide.with(this).load(avatar).error(R.drawable.icon_system_photo).into(character2);
        pengyouquan = popupView1.findViewById(R.id.share_pengyouquan);
        weixin = popupView1.findViewById(R.id.share_weixin);
        qq = popupView1.findViewById(R.id.share_qq);
        qqzone = popupView1.findViewById(R.id.share_qqzone);
        close = popupView1.findViewById(R.id.close);
        close2 = popupView2.findViewById(R.id.close2);
        tryAgain1 = popupView1.findViewById(R.id.try_again);
        tryAgain2 = popupView2.findViewById(R.id.try_again2);
        star1 = popupView1.findViewById(R.id.pkstar_1);
        star2 = popupView1.findViewById(R.id.pkstar_2);
        star3 = popupView1.findViewById(R.id.pkstar_3);
        star4 = popupView1.findViewById(R.id.pkstar_4);
        star5 = popupView1.findViewById(R.id.pkstar_5);
        star1.setVisibility(View.GONE);
        star2.setVisibility(View.GONE);
        star3.setVisibility(View.GONE);
        star4.setVisibility(View.GONE);
        star5.setVisibility(View.GONE);
        popupWindow1 = new PopupWindow(popupView1, popupWidth, popupHeight, false);
        popupWindow2 = new PopupWindow(popupView2, popupWidth, popupHeight, false);

        popupWindow1.setBackgroundDrawable(new ColorDrawable());
        popupWindow1.setOutsideTouchable(false);
        popupWindow1.setOnDismissListener(this);
        popupWindow2.setBackgroundDrawable(new ColorDrawable());
        popupWindow2.setOutsideTouchable(false);
        popupWindow2.setOnDismissListener(this);
        close.setOnClickListener(listener);
        close2.setOnClickListener(listener);
        tryAgain1.setOnClickListener(listener);
        tryAgain2.setOnClickListener(listener);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
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
        pkFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = pkFrameLayout.getWidth();
                height = pkFrameLayout.getHeight();
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
                pkSpeak.setImageResource(R.drawable.icon_speak_big_f);
                circleProgressBar.setProgress(0);
                isRecord = false;

                onRecord(fileName, currentLine);

                if (round == 0) {
                    float progresses = (float) currentLine / totalLines;
                    progressBar.setProgress((int) (progresses * 50));
                } else {
                    float progresses = (float) currentLine / totalLines;
                    progressBar.setProgress((int) (progresses * 50) + 50);
                }

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
                }
//                mIse.stopEvaluating();
            }
        };
    }

    private void Round2() {
        currentLine = 1;
        isClick = true;
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).setSelect(false);
        }
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).setScoreShow(false);
        }
        for (int i = 0; i < lists.size(); i++) {
            if (i % 2 == 0) {
                //偶数
                lists.get(i).setSelfLine(true);
            } else {
                //奇数
                lists.get(i).setSelfLine(false);
            }
        }
        lists.get(currentLine - 1).setSelect(true);
//        challengeList.scrollToPosition(currentLine - 1);
        pkList.smoothScrollToPosition(currentLine - 1);
        adapter.notifyDataSetChanged();
        pkSpeak.setImageResource(R.drawable.icon_speak_big);

        Glide.with(this).load(application.getSystemUtils().getUserInfo().getAvatar()).error(R.drawable.icon_system_photo).into(player2);
        Glide.with(this).load(avatar).error(R.drawable.icon_system_photo).into(player1);
        player1Name.setText(pkName);
        player2Name.setText(application.getSystemUtils().getUserInfo().getName());
//        play(lists.get(currentLine - 1).getResourceUrl());
    }

    @Override
    protected void initData() {
        shareUtils = new ShareUtils(this, this);
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        countDownDialog = helper.getCountDownDialog(this);
        adapter = new ChallengeAdapter(this, lists, 0);
        pkList.setAdapter(adapter);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);

        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/");
        if (!file.exists()) {
            file.mkdirs();
        }

        mIse = SpeechEvaluator.createEvaluator(this, null);

        Glide.with(this).load(application.getSystemUtils().getUserInfo().getAvatar()).error(R.drawable.icon_system_photo).into(player1);
        Glide.with(this).load(avatar).error(R.drawable.icon_system_photo).into(player2);
        player1Name.setText(application.getSystemUtils().getUserInfo().getName());
        player2Name.setText(pkName);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getBookAudioData(bookId, 2, pkUserName);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

//    private void nextPage() {
//        lists.clear();
//        lists.addAll(book.getPageContent().get(currentPage - 1).getLineContent());
//        totalLines = lists.size();
//        refresh();
//    }

    private void startRecord(int position) {
        fileName = lists.get(currentLine - 1).getEnTitle().replace(".", "");
        setParams(fileName);
        if (mIse == null) {
            return;
        }
        EvaluatorListener evaluatorListener = getEvaluatorListener(position);
        int ret = mIse.startEvaluating(lists.get(currentLine - 1).getEnTitle(), null, evaluatorListener);
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
        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/" + fileName + ".pcm");
        //通过writeaudio方式直接写入音频时才需要此设置
        //mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }

    private EvaluatorListener getEvaluatorListener(int position) {
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
                            lists.get(position - 1).setScore(score);
                            lists.get(position - 1).setScoreShow(true);
                            adapter.notifyDataSetChanged();
//                            showInfo(score + "");
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
                            presenter.uploadAudioResource(bookId, Integer.parseInt(lists.get(position - 1).getPageid()), audioType, audioSource, lists.get(position - 1).getLineId(), Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/" + fileName + ".pcm", score, fileName, record_time, 2, pkUserName, imageUrl, 0, homeworkid, homeworktype);
//                                }
//                            }, 5000);
//                            presenter.uploadAudioResource(bookId, Integer.parseInt(lists.get(position - 1).getPageid()), audioType, audioSource, lists.get(position - 1).getLineId(), Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/" + fileName + ".pcm", score, fileName, record_time, 2, pkUserName, imageUrl, 0, homeworkid, homeworktype);
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

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_PK_PK) {
            book = (Book) message.obj;
            if (book.getPageContent() == null || book.getPageContent().size() == 0) {
                empty.setVisibility(View.VISIBLE);
                pkList.setVisibility(View.GONE);
            } else {
                empty.setVisibility(View.GONE);
                pkList.setVisibility(View.VISIBLE);
                lists.clear();
                totalLines = 0;
                for (int i = 0; i < book.getPageContent().size(); i++) {
                    lists.addAll(book.getPageContent().get(i).getLineContent());
                    totalLines = totalLines + book.getPageContent().get(i).getLineContent().size();
                }
                for (int i = 0; i < lists.size(); i++) {
                    if (i % 2 == 0) {
                        //偶数
                        lists.get(i).setSelfLine(false);
                    } else {
                        //奇数
                        lists.get(i).setSelfLine(true);
                    }
                }
                lists.get(0).setSelect(true);
                adapter.notifyDataSetChanged();
                countDownDialog.show();
            }
        } else if (message.what == MethodCode.EVENT_GETPKRESULT) {
            PkResult pkResult = (PkResult) message.obj;
            Intent intent = new Intent(this, PkResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("pkResult", pkResult);
            bundle.putString("pkName", pkName);
            bundle.putString("pkImageUrl", avatar);
            bundle.putInt("bookId", bookId);
            bundle.putString("imageUrl", imageUrl);
            bundle.putInt("audioType", audioType);
            bundle.putInt("audioSource", audioSource);
            bundle.putString("title", title);
            bundle.putInt("homeworkid", homeworkid);
            bundle.putInt("homeworktype", homeworktype);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
//            initPopupData(pkResult);
//            presenter.fullrecording(pkResult.getMyscore(), bookId);
        } else if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            if (isEnd) {
                isEnd = false;
                presenter.getPkResult(bookId, pkUserName, 2);
            }
        }
    }

    private void initPopupData(PkResult pkResult) {
        float a = Float.parseFloat(pkResult.getMyscore());
        float b = Float.parseFloat(pkResult.getPkscore());

        if (pkResult.getResult() == 0) {
            //失败
            popupWindow2.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
            getWindowGray(true);
        } else {
            //成功
            popupWindow1.showAtLocation(popupView1, Gravity.CENTER, 0, 0);
            getWindowGray(true);
        }
    }

    private void refresh() {
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).setSelect(false);
        }
        lists.get(currentLine - 1).setSelect(true);
        adapter.notifyDataSetChanged();
        if (lists.get(currentLine - 1).getPkResourceUrl() == null || lists.get(currentLine - 1).getPkResourceUrl().length() == 0) {
            play(lists.get(currentLine - 1).getResourceUrl());
        } else {
            play(lists.get(currentLine - 1).getPkResourceUrl());
        }
    }

    private void play(String url) {
        if (round == 0) {
            mic2.setVisibility(View.VISIBLE);
            mic1.setVisibility(View.GONE);
        } else {
            mic1.setVisibility(View.VISIBLE);
            mic2.setVisibility(View.GONE);
        }
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
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
    public void onCompletion(MediaPlayer mp) {
        isClick = true;
        if (round == 0) {
            float progresses = (float) currentLine / totalLines;
            progressBar.setProgress((int) (progresses * 50));
        } else {
            float progresses = (float) currentLine / totalLines;
            progressBar.setProgress((int) (progresses * 50) + 50);
        }
        lists.get(currentLine - 1).setScoreShow(true);
        adapter.notifyDataSetChanged();

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
                    presenter.getPkResult(bookId, pkUserName, 2);
                }
            }
        } else {
            for (int i = 0; i < lists.size(); i++) {
                lists.get(i).setSelect(false);
            }
            lists.get(currentLine - 1).setSelect(true);
//            challengeList.scrollToPosition(currentLine - 1);

            pkList.smoothScrollToPosition(currentLine - 1);
            adapter.notifyDataSetChanged();
            pkSpeak.setImageResource(R.drawable.icon_speak_big);
            if (round == 0) {
                mic1.setVisibility(View.VISIBLE);
                mic2.setVisibility(View.GONE);
            } else {
                mic2.setVisibility(View.VISIBLE);
                mic1.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    public void onCountDownFinish() {
        if (lists.get(currentLine - 1).getPkResourceUrl() == null || lists.get(currentLine - 1).getPkResourceUrl().length() == 0) {
            play(lists.get(currentLine - 1).getResourceUrl());
        } else {
            play(lists.get(currentLine - 1).getPkResourceUrl());
        }
    }

    @Override
    public void onDismiss() {
        getWindowGray(false);
        isRecord = false;
        if (isEnd) {
            finish();
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
            case R.id.pk_quit:
                isRecord = false;
                finish();
                break;
            case R.id.pk_circleProgressBar:
                if (isClick) {
                    if (isRecord) {
                        timer.cancel();
                        pkSpeak.setImageResource(R.drawable.icon_speak_big_f);
                        circleProgressBar.setProgress(0);
                        isRecord = false;
                        isClick = false;

                        onRecord(fileName, currentLine);

                        if (round == 0) {
                            float progresses = (float) currentLine / totalLines;
                            progressBar.setProgress((int) (progresses * 50));
                        } else {
                            float progresses = (float) currentLine / totalLines;
                            progressBar.setProgress((int) (progresses * 50) + 50);
                        }

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
                        }
//                        mIse.stopEvaluating();
                    } else {
                        pkSpeak.setImageResource(R.drawable.icon_stop_big);
                        timer.start();
                        isRecord = true;

                        String fileName2 = lists.get(currentLine - 1).getEnTitle().replace(".", "").trim();
                        if (fileName2.length() > 50) {
                            fileName2 = fileName2.substring(0, 50);
                        }
                        fileName = fileName2;


                        onRecord(fileName, currentLine);
                    }
                }
                break;
            case R.id.close:
                popupWindow1.dismiss();
                finish();
                break;
            case R.id.close2:
                popupWindow2.dismiss();
                finish();
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
            case R.id.try_again:
                finish();
                break;
            case R.id.try_again2:
                finish();
                break;
        }
    }

    public void onRecord(String name, int i) {
        if (oral == null) {
            oral = new TAIOralEvaluation();
        }
        if (oral.isRecording()) {
            oral.stopRecordAndEvaluation(new TAIOralEvaluationCallback() {
                @Override
                public void onResult(final TAIError error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            songView.showLoad();
//                            SystemUtils.show(ChallengeActivity.this, "说话结束");
                            isRecording = false;
                            Log.e("说话结束", error.desc + "---" + error.code);
                        }
                    });
                }
            });
        } else {
            oral.setListener(new TAIOralEvaluationListener() {
                @Override
                public void onEvaluationData(final TAIOralEvaluationData data, final TAIOralEvaluationRet result, final TAIError error) {
                    SystemUtils.saveFile(data.audio, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/", name + ".mp3");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (error.code != TAIErrCode.SUCC) {
//                                SystemUtils.show(ChallengeActivity.this, "说话结束");
                            }
                            oral = null;
                            double num = (result.pronAccuracy) * (result.pronCompletion) * (2 - result.pronCompletion);
                            BigDecimal bg = new BigDecimal(num / 20);
                            double num1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            lists.get(i - 1).setScore((float) num1);
                            lists.get(i - 1).setScoreShow(true);
                            adapter.notifyDataSetChanged();
//                            presenter.uploadAudioResource(bookId, Integer.parseInt(lists.get(i).getPageid()), audioType, audioSource, lists.get(i).getLineId(), Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "challenge/" + name + ".mp3", (float) num1, name + "（练习）", record_time, 0, "", imageUrl, 0, homeworkid, homeworktype);
                            presenter.uploadAudioResource(bookId, Integer.parseInt(lists.get(i - 1).getPageid()), audioType, audioSource, lists.get(i - 1).getLineId(), Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/" + fileName + ".mp3", (float) num1, fileName, record_time, 2, "", imageUrl, 0, homeworkid, homeworktype);
                            Log.e("说话结束2", result.pronAccuracy + "");
                        }
                    });
                }
            });

            TAIOralEvaluationParam param = new TAIOralEvaluationParam();
            param.context = this;
            param.sessionId = UUID.randomUUID().toString();
            param.appId = PrivateInfo.appId;
            param.soeAppId = PrivateInfo.soeAppId;
            param.secretId = PrivateInfo.secretId;
            param.secretKey = PrivateInfo.secretKey;
            param.token = PrivateInfo.token;
            //流式传输0，一次性传输1，
            param.workMode = TAIOralEvaluationWorkMode.ONCE;
            param.evalMode = TAIOralEvaluationEvalMode.PARAGRAPH;//单词模式0，句子模式1，段落模式2，自由说模式3
            //是否存储 1
            param.storageMode = TAIOralEvaluationStorageMode.ENABLE;
            param.fileType = TAIOralEvaluationFileType.MP3;
            param.serverType = TAIOralEvaluationServerType.ENGLISH;
            param.textMode = TAIOralEvaluationTextMode.NORMAL;
            //苛刻指数1.0-4.0
            param.scoreCoeff = 1.0;
            param.refText = lists.get(i - 1).getEnTitle();
            if (param.workMode == TAIOralEvaluationWorkMode.STREAM) {
                param.timeout = 5;
                param.retryTimes = 5;
            } else {
                param.timeout = 30;
                param.retryTimes = 0;
            }
            //分片大小
            oral.setFragSize(10 * 1024);
            oral.startRecordAndEvaluation(param, new TAIOralEvaluationCallback() {
                @Override
                public void onResult(final TAIError error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (error.code == TAIErrCode.SUCC) {
//                                SystemUtils.show(ChallengeActivity.this, "说话开始");
                                record_time = 0;
                                isRecording = true;
                                handler.postDelayed(runnable, 1000);
                                Log.e("说话开始", error.desc + "---" + error.code);
                            }
                        }
                    });
                }
            });
        }
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
        isClick = false;
        isRecord = false;
        isPlay = false;
    }


}
