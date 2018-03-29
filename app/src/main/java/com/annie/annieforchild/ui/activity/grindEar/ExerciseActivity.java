package com.annie.annieforchild.ui.activity.grindEar;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.speech.util.Result;
import com.annie.annieforchild.Utils.speech.util.XmlResultParser;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 练习
 * Created by wanglei on 2018/3/19.
 */

public class ExerciseActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = ExerciseActivity.class.getSimpleName();
    private TextView resultText;
    private EditText iseText;
    private Spinner spinner;
    private Button start, stop, parse;
    private SpeechEvaluator mIse;
    // 评测语种
    private String language;
    // 评测题型
    private String category;
    // 结果等级
    private String result_level;

    private String results;
    private final static String PREFER_NAME = "ise_settings";
    private ArrayAdapter adapter;
    private List<String> spinnerList;
    String lan = "en_us";
    MediaPlayer mediaPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise;
    }

    @Override
    protected void initView() {
        iseText = findViewById(R.id.iseText);
        resultText = findViewById(R.id.resultText);
        start = findViewById(R.id.startBtn);
        stop = findViewById(R.id.stopBtn);
        parse = findViewById(R.id.parseBtn);
        spinner = findViewById(R.id.spinner);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        parse.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        spinnerList = new ArrayList<>();
        spinnerList.add("zh_cn");
        spinnerList.add("en_us");
        mIse = SpeechEvaluator.createEvaluator(this, null);
        setParams();
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lan = spinnerList.get(position);
                showInfo(lan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
    }

    private void setParams() {
        SharedPreferences pref = getSharedPreferences(PREFER_NAME, MODE_PRIVATE);
        // 设置评测语言
        language = pref.getString(SpeechConstant.LANGUAGE, lan);
        // 设置需要评测的类型
        category = pref.getString(SpeechConstant.ISE_CATEGORY, "read_sentence");
        // 设置结果等级（中文仅支持complete）
        result_level = pref.getString(SpeechConstant.RESULT_LEVEL, "complete");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_bos = pref.getString(SpeechConstant.VAD_BOS, "5000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String vad_eos = pref.getString(SpeechConstant.VAD_EOS, "1800");
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        String speech_timeout = pref.getString(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");

        mIse.setParameter(SpeechConstant.LANGUAGE, language);
        mIse.setParameter(SpeechConstant.ISE_CATEGORY, category);
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mIse.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        mIse.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, result_level);
//        mIse.setParameter(SpeechConstant.SAMPLE_RATE, "16000");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/ise.wav");
        //通过writeaudio方式直接写入音频时才需要此设置
        //mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                if (mIse == null) {
                    return;
                }
                showInfo("评测开始");
                int ret = mIse.startEvaluating(iseText.getText().toString(), null, mEvaluatorListener);
                break;
            case R.id.stopBtn:
                if (mIse.isEvaluating()) {
                    resultText.setHint("评测已停止，等待结果中...");
                    mIse.stopEvaluating();
                }
                break;
            case R.id.parseBtn:
//                if (!TextUtils.isEmpty(results)) {
//                    XmlResultParser resultParser = new XmlResultParser();
//                    Result result = resultParser.parse(results);
//
//                    if (null != result) {
//                        resultText.setText(result.toString());
//                    } else {
//                        showInfo("解析结果为空");
//                    }
//                }
                File file = new File(Environment.getExternalStorageDirectory() + "/msc/ise.wav");
                if (file.exists()) {
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(file.getPath());
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    // 评测监听接口
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {

        @Override
        public void onResult(EvaluatorResult result, boolean isLast) {
//            Log.d(TAG, "evaluator result :" + isLast);

            if (isLast) {
                StringBuilder builder = new StringBuilder();
                builder.append(result.getResultString());

//                if (!TextUtils.isEmpty(builder)) {
//                    resultText.setText(builder.toString());
//                }
//                mIseStartButton.setEnabled(true);
                results = builder.toString();

                showInfo("评测结束");
                if (!TextUtils.isEmpty(results)) {
                    XmlResultParser resultParser = new XmlResultParser();
                    Result finalResult = resultParser.parse(results);

                    if (null != finalResult) {
                        resultText.setText(finalResult.toString());
                    } else {
                        showInfo("解析结果为空");
                    }
                }
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
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Log.d(TAG, "evaluator begin");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            Log.d(TAG, "evaluator stoped");
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

    private void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mIse) {
            mIse.destroy();
            mIse = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
