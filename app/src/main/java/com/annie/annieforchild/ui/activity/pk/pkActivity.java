package com.annie.annieforchild.ui.activity.pk;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.speech.util.Result;
import com.annie.annieforchild.Utils.speech.util.XmlResultParser;
import com.annie.annieforchild.Utils.views.CircleProgressBar;
import com.annie.annieforchild.bean.JTMessage;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * pk——pk
 * Created by wanglei on 2018/4/4.
 */

public class pkActivity extends BaseActivity implements View.OnClickListener, SongView, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, OnCountFinishListener {
    private TextView quit;
    private CircleImageView player1, player2;
    private CircleProgressBar circleProgressBar;
    private RecyclerView pkList;
    private ImageView pkSpeak;
    private FrameLayout pkFrameLayout, pkFinishBtn;
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
    private String pkUserName, avatar;
    private float score; //得分
    private int currentPage = 1; //当前页码
    private int currentLine = 1; //当前行号
    private int totalLines; //当前页总行数
    private int totalPages; //总页数
    private String fileName;
    private AlertHelper helper;
    private Dialog dialog;
    private Dialog countDownDialog;
    private boolean isPlay = false;
    private boolean isClick = false;
    private boolean isRecording = false; //录音状态
    private int record_time = 0; //录音时长
    Runnable runnable;
    private Handler handler;

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
        pkFrameLayout = findViewById(R.id.pk_frameLayout);
        circleProgressBar = findViewById(R.id.pk_circleProgressBar);
        pkList = findViewById(R.id.pk_list);
        pkFinishBtn = findViewById(R.id.pk_finish_btn);
        quit.setOnClickListener(this);
        circleProgressBar.setOnClickListener(this);
        pkFinishBtn.setOnClickListener(this);

        initTalkBtn();
        intent = getIntent();
        bundle = intent.getExtras();
        bookId = bundle.getInt("bookId");
        pkUserName = bundle.getString("pkUserName");
        avatar = bundle.getString("avatar");

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
                isPlay = false;
            }
        };
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        countDownDialog = helper.getCountDownDialog(this);
        adapter = new ChallengeAdapter(this, lists);
        pkList.setAdapter(adapter);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);

        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/");
        if (!file.exists()) {
            file.mkdirs();
        }

        mIse = SpeechEvaluator.createEvaluator(this, null);

        Glide.with(this).load(SystemUtils.userInfo.getAvatar()).into(player1);
        Glide.with(this).load(avatar).into(player2);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getBookAudioData(bookId, 2, pkUserName);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pk_quit:
                isPlay = false;
                finish();
                break;
            case R.id.pk_circleProgressBar:
                if (isClick) {
                    if (isPlay) {
                        timer.cancel();
                        pkSpeak.setImageResource(R.drawable.icon_speak_big_f);
                        circleProgressBar.setProgress(0);
                        isPlay = false;
                        isClick = false;
                        mIse.stopEvaluating();
                        currentLine++;
                        if (currentLine <= totalLines) {
                            refresh();
                        } else {
                            currentLine = 1;
                            pkFinishBtn.setVisibility(View.VISIBLE);
                        }
                    } else {
                        pkSpeak.setImageResource(R.drawable.icon_stop_big);
                        timer.start();
                        isPlay = true;
                        startRecord();
                    }
                }
                break;
            case R.id.pk_finish_btn:
                currentPage++;
                if (currentPage > totalPages) {
                    showInfo("结束");
                } else {
                    pkFinishBtn.setVisibility(View.GONE);
                    nextPage();
                }
                break;
        }
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
//                            showInfo(score + "");
                            presenter.uploadAudioResource(bookId, currentPage, currentLine, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "challenge/" + fileName + ".pcm", score, fileName, record_time);
                        } else {
//                        showInfo("解析结果为空");
                        }
                    }
                    isClick = false;
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
            lists.clear();
            lists.addAll(book.getPageContent().get(currentPage - 1).getLineContent());
            lists.get(0).setSelect(true);
            totalLines = lists.size();
            totalPages = book.getBookTotalPages();
            adapter.notifyDataSetChanged();
            countDownDialog.show();
        }
    }

    private void refresh() {
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).setSelect(false);
        }
        lists.get(currentLine - 1).setSelect(true);
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
        pkSpeak.setImageResource(R.drawable.icon_speak_big);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    public void onCountDownFinish() {
        play(lists.get(currentLine - 1).getResourceUrl());
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
}
