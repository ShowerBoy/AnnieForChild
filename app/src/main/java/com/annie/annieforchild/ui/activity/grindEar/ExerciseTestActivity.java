package com.annie.annieforchild.ui.activity.grindEar;

import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.PCMRecordUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.speech.util.Result;
import com.annie.annieforchild.Utils.speech.util.XmlResultParser;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 讯飞--测试
 * Created by wanglei on 2018/3/19.
 */

public class ExerciseTestActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = ExerciseTestActivity.class.getSimpleName();
    private TextView resultText;
    private EditText iseText;
    private Spinner spinner;
    private Button start, stop, parse, startRecord, stopRecord;
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
    AudioTrack player;
    DataInputStream dis = null;
    private boolean isPlay = false;
    Handler handler = new Handler();
    PCMRecordUtils pcmRecordUtils;
    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/record/test.pcm";
    File file;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise_test;
    }

    @Override
    protected void initView() {
        iseText = findViewById(R.id.iseText);
        resultText = findViewById(R.id.resultText);
        start = findViewById(R.id.startBtn);
        stop = findViewById(R.id.stopBtn);
        parse = findViewById(R.id.parseBtn);
        spinner = findViewById(R.id.spinner);
        startRecord = findViewById(R.id.record_btn);
        stopRecord = findViewById(R.id.record_stop_btn);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        parse.setOnClickListener(this);
        startRecord.setOnClickListener(this);
        stopRecord.setOnClickListener(this);
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
//                showInfo(lan);
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

        pcmRecordUtils = new PCMRecordUtils(filePath);

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
        mIse.setParameter(SpeechConstant.SAMPLE_RATE, "16000");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/ise.pcm");

        //通过writeaudio方式直接写入音频时才需要此设置
        mIse.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        mIse.setParameter(SpeechConstant.ISE_SOURCE_PATH, filePath);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_btn:
                pcmRecordUtils.startRecord();
                break;
            case R.id.record_stop_btn:
                pcmRecordUtils.stopRecord();
                break;
            case R.id.startBtn:
                if (mIse == null) {
                    return;
                }
                showInfo("评测开始");
                int ret = mIse.startEvaluating(iseText.getText().toString(), null, mEvaluatorListener);
                /**
                 * 通过writeaudio方式直接写入音频
                 */
                if (ret != ErrorCode.SUCCESS) {

                } else {
//                    byte[] audioData = FucUtil.readAudioFile(ExerciseTestActivity.this, "ise.pcm");
                    byte[] audioData = SystemUtils.getBytes(filePath);

                    if (audioData != null) {
                        try {
                            new Thread().sleep(100);
                        } catch (InterruptedException e) {
                            Log.d(TAG, "InterruptedException :" + e);
                        }
                        mIse.writeAudio(audioData, 0, audioData.length);
                        mIse.stopEvaluating();
                    }
                }

                break;
            case R.id.stopBtn:
                if (mIse.isEvaluating()) {
                    resultText.setHint("评测已停止，等待结果中...");
                    mIse.stopEvaluating();
                }
                break;
            case R.id.parseBtn:
                if (isPlay) {
                    isPlay = false;
                    parse.setText("回放");
                    player.stop();
                    player.release();
                } else {
                    isPlay = true;
                    parse.setText("停止");
                    play();

                }
//                File file = new File(Environment.getExternalStorageDirectory() + "/msc/ise.wav");
//                if (file.exists()) {
//                    try {
//                        mediaPlayer.reset();
//                        mediaPlayer.setDataSource(file.getPath());
//                        mediaPlayer.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
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

    public void play() {
        try {
            //从音频文件中读取声音
            File file = new File(filePath);

            if (!file.exists()) {
                return;
            }
//            ContentResolver resolver = getContentResolver();
//            InputStream inputStream = resolver.openInputStream(Uri.parse("http://appapi.anniekids.net/release/Public/Uploads/moerduorecord/20180411/5ace168250c2b.pcm"));
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
//            dis = new DataInputStream(new BufferedInputStream(inputStream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        //最小缓存区
        int bufferSizeInBytes = AudioTrack.getMinBufferSize(16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        //创建AudioTrack对象   依次传入 :流类型、采样率（与采集的要一致）、音频通道（采集是IN 播放时OUT）、量化位数、最小缓冲区、模式
        player = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes, AudioTrack.MODE_STREAM);

        byte[] data = new byte[bufferSizeInBytes];
        player.play();//开始播放
//        DataInputStream finalDis = dis;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isPlay) {
                    int i = 0;
                    try {
                        while (dis.available() > 0 && i < data.length) {
                            data[i] = dis.readByte();//录音时write Byte 那么读取时就该为readByte要相互对应
                            i++;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    player.write(data, 0, data.length);

                    if (i != bufferSizeInBytes) //表示读取完了
                    {
                        isPlay = false;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                parse.setText("回放");
                            }
                        });
                        player.stop();//停止播放
                        player.release();//释放资源
                        break;
                    }
                }
            }
        }).start();
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
