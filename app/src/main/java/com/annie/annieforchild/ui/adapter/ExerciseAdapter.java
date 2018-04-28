package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.speech.util.Result;
import com.annie.annieforchild.Utils.speech.util.XmlResultParser;
import com.annie.annieforchild.bean.Exercise;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.grindEar.ExerciseTestActivity;
import com.annie.annieforchild.ui.activity.pk.ExerciseActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.ExerciseViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wanglei on 2018/3/30.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private Context context;
    private List<Line> lists;
    private LayoutInflater inflater;
    private GrindEarPresenter presenter;
    private OnRecyclerItemClickListener listener;

    private static String TAG = ExerciseActivity.class.getSimpleName();
    private SpeechEvaluator mIse;
    private String results;
    private MediaPlayer mediaPlayer;
    private int bookId;
    private AudioTrack player;
    private DataInputStream dis = null;
    private boolean isSpeakReady = true;
    private boolean isClick = true;
    private int record_time = 0; //录音时长
    private boolean isRecording = false; //录音状态
    String fileName;
    private Handler handler = new Handler();
    Runnable runnable;

    public ExerciseAdapter(Context context, List<Line> lists, int bookId, GrindEarPresenter presenter, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.bookId = bookId;
        this.presenter = presenter;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
        initData();

    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ExerciseViewHolder holder;
        holder = new ExerciseViewHolder(inflater.inflate(R.layout.activity_exercise_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(v);
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        if (lists.get(i).isSelect()) {
            exerciseViewHolder.exerciseLayout.setVisibility(View.VISIBLE);
            exerciseViewHolder.textView.setTextColor(context.getResources().getColor(R.color.text_black));
        } else {
            exerciseViewHolder.exerciseLayout.setVisibility(View.GONE);
            exerciseViewHolder.textView.setTextColor(context.getResources().getColor(R.color.text_color));
        }
        if (lists.get(i).getMyResourceUrl() != null && lists.get(i).getMyResourceUrl().length() != 0 && !lists.get(i).getMyResourceUrl().equals("")) {
            exerciseViewHolder.play.setImageResource(R.drawable.icon_play_big);
        } else {
            exerciseViewHolder.play.setImageResource(R.drawable.icon_play_big_f);
        }
        exerciseViewHolder.textView.setText(lists.get(i).getEnTitle());
        exerciseViewHolder.exercise_score.setText(lists.get(i).getScore() + "分");
        exerciseViewHolder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick) {
                    isClick = false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            playUrl(lists.get(i).getResourceUrl());
                        }
                    }).start();
                }
            }
        });
        exerciseViewHolder.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick) {
                    isClick = false;
                    SystemUtils.show(context, "说话开始");
                    fileName = lists.get(i).getPage() + "-" + lists.get(i).getLineId() + "-" + lists.get(i).getEnTitle().replace(".", "");
                    setParams(fileName);
                    if (mIse == null) {
                        isClick = true;
                        return;
                    }
                    EvaluatorListener evaluatorListener = getEvaluatorListener(exerciseViewHolder, i);
                    int ret = mIse.startEvaluating(lists.get(i).getEnTitle(), null, evaluatorListener);
                }
            }
        });
        exerciseViewHolder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick) {
                    isClick = false;
//                    if (lists.get(i).getMyResourceUrl() != null && lists.get(i).getMyResourceUrl().length() != 0) {
//                        playUrl(lists.get(i).getMyResourceUrl());
//                    } else {
//                        isClick = true;
//                    }
                    isSpeakReady = true;
                    play();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

    private void initData() {
        mIse = SpeechEvaluator.createEvaluator(context, null);
//        setParams();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
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

    private void playUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setParams(String fileName) {
        mIse.setParameter(SpeechConstant.LANGUAGE, "en_us"); // 设置评测语言
        mIse.setParameter(SpeechConstant.ISE_CATEGORY, "read_sentence"); // 设置需要评测的类型
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mIse.setParameter(SpeechConstant.VAD_BOS, "5000"); // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIse.setParameter(SpeechConstant.VAD_EOS, "1800"); // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1"); // 语音输入超时时间，即用户最多可以连续说多长时间；
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, "complete");// 设置结果等级（中文仅支持complete）
        mIse.setParameter(SpeechConstant.SAMPLE_RATE, "16000");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + fileName + ".pcm");
        //通过writeaudio方式直接写入音频时才需要此设置
        //mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }

    private EvaluatorListener getEvaluatorListener(ExerciseViewHolder viewHolder, int i) {
        EvaluatorListener evaluatorListener = null;
        evaluatorListener = new EvaluatorListener() {
            @Override
            public void onResult(EvaluatorResult result, boolean isLast) {
                if (isLast) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(result.getResultString());

                    results = builder.toString();

                    SystemUtils.show(context, "说话结束");
                    if (!TextUtils.isEmpty(results)) {
                        XmlResultParser resultParser = new XmlResultParser();
                        Result finalResult = resultParser.parse(results);
//                        int time = finalResult.time_len;
                        if (null != finalResult) {
//                        resultText.setText(finalResult.toString());
                            float score = finalResult.total_score;
                            BigDecimal bigDecimal = new BigDecimal(score);
                            score = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                            presenter.uploadAudioResource(bookId, lists.get(i).getPage(), lists.get(i).getLineId(), Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + fileName + ".pcm", score, fileName, record_time);
                        } else {
//                        showInfo("解析结果为空");
                        }
                    }
                    isClick = true;
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
                Log.d(TAG, "evaluator begin");
                record_time = 0;
                isRecording = true;
                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void onEndOfSpeech() {
                // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
                Log.d(TAG, "evaluator stoped");
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

    public void play() {
        try {
            //从音频文件中读取声音
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + fileName + ".pcm");
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/record/" + "ceshi.pcm");

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
                while (isSpeakReady) {
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
                        isSpeakReady = false;
                        player.stop();//停止播放
                        player.release();//释放资源
                        isClick = true;
                        break;
                    }
                }
            }
        }).start();
    }

    public void stopMedia() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isClick = true;
    }
}
