package com.annie.annieforchild.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.speech.util.Result;
import com.annie.annieforchild.Utils.speech.util.XmlResultParser;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.pk.ExerciseActivity2;
import com.annie.annieforchild.ui.adapter.viewHolder.ExerciseViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.example.lamemp3.PrivateInfo;
import com.google.gson.Gson;
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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by wanglei on 2018/3/30.
 */

public class Exercise_newAdapter extends RecyclerView.Adapter<ExerciseViewHolder> implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private Context context;
    private SongView songView;
    private List<Line> lists;
    private LayoutInflater inflater;
    private GrindEarPresenter presenter;
    private OnRecyclerItemClickListener listener;
    private SpeechEvaluator mIse;
    private String results;
    private MediaPlayer mediaPlayer;
    private int bookId;
    private MediaPlayer player;
    private DataInputStream dis = null;
    private boolean isSpeakReady = true; //我的录音播放控制
    private boolean isClick = true, isPlay = false, isRecordPlay = false;
    private int record_time = 0; //录音时长
    private int duration, homeworkid, homeworktype;
    private String imageUrl;
    public static boolean isRecording = false; //录音状态
    String fileName;
    private Handler handler = new Handler();
    Runnable runnable;
    private int audioType, audioSource;
    private String title;
    private ExerciseViewHolder holder;
    private int key; //0：书籍阅读 1：练习
    private Activity activity;
    private TAIOralEvaluation oral;

    public Exercise_newAdapter(Activity activity, Context context, SongView songView, String title, List<Line> lists, int bookId, GrindEarPresenter presenter, int audioType, int audioSource, String imageUrl, int key, int homeworkid, int homeworktype, OnRecyclerItemClickListener listener) {
        this.activity = activity;
        this.context = context;
        this.songView = songView;
        this.title = title;
        this.lists = lists;
        this.bookId = bookId;
        this.presenter = presenter;
        this.audioType = audioType;
        this.audioSource = audioSource;
        this.imageUrl = imageUrl;
        this.key = key;
        this.homeworkid = homeworkid;
        this.homeworktype = homeworktype;
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
//                fileName = lists.get(i).getPage() + "-" + lists.get(i).getLineId() + "-" + lists.get(i).getEnTitle().replace(".", "");
                if (isClick) {
                    listener.onItemClick(v);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isClick) {
                    listener.onItemLongClick(v);
                }
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        holder = exerciseViewHolder;
        if (key == 0) {
            exerciseViewHolder.exerciseLayout.setVisibility(View.GONE);
            if (lists.get(i).isSelect()) {
                exerciseViewHolder.textView.setTextColor(context.getResources().getColor(R.color.text_orange));
            } else {
                exerciseViewHolder.textView.setTextColor(context.getResources().getColor(R.color.text_color));
            }
            exerciseViewHolder.textView.setText(lists.get(i).getEnTitle());
        } else {
            if (lists.get(i).isSelect()) {
                exerciseViewHolder.exerciseLayout.setVisibility(View.VISIBLE);
                exerciseViewHolder.textView.setTextColor(context.getResources().getColor(R.color.text_black));
                String fileName2 = lists.get(i).getEnTitle().replace(".", "");
                if (fileName2.length() > 50) {
                    fileName2 = fileName2.substring(0, 50);
                }
                fileName = fileName2 + "-" + lists.get(i).getPageid() + "-" + lists.get(i).getLineId();

            } else {
                exerciseViewHolder.exerciseLayout.setVisibility(View.GONE);
                exerciseViewHolder.textView.setTextColor(context.getResources().getColor(R.color.text_color));
            }

            if (lists.get(i).getMyResourceUrl() == null || lists.get(i).getMyResourceUrl().length() == 0) {
                exerciseViewHolder.play.setImageResource(R.drawable.icon_play_big_f);
            } else {
                exerciseViewHolder.play.setImageResource(R.drawable.icon_play_big);
                exerciseViewHolder.exercise_score.setText(lists.get(i).getScore() + "");
            }
            exerciseViewHolder.textView.setText(lists.get(i).getEnTitle());

            exerciseViewHolder.preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isClick) {
                        if (isPlay) {
                            if (mediaPlayer != null) {
                                try {
                                    mediaPlayer.pause();
                                    mediaPlayer.stop();
                                    mediaPlayer.seekTo(0);
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                }
                                exerciseViewHolder.preview.setImageResource(R.drawable.icon_preview_big);
                            }
                            isPlay = false;
                        } else {
                            isPlay = true;
                            isClick = false;
                            exerciseViewHolder.preview.setImageResource(R.drawable.icon_stop_medium);
                            holder = exerciseViewHolder;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    playUrl(lists.get(i).getResourceUrl());
                                }
                            }).start();
                        }
                    } else {
                        if (isPlay) {
                            if (mediaPlayer != null) {
                                try {
                                    if (mediaPlayer.isPlaying()) {
                                        mediaPlayer.pause();
                                        mediaPlayer.stop();
                                        mediaPlayer.seekTo(0);
                                        exerciseViewHolder.preview.setImageResource(R.drawable.icon_preview_big);
                                    }
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                }
                            }
                            isPlay = false;
                            isClick = true;
                        }
                    }
                }
            });
            exerciseViewHolder.speak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isPlay && !isRecordPlay) {
                        onRecord(exerciseViewHolder, i);
                    }
                }
            });
            exerciseViewHolder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lists.get(i).getMyResourceUrl() != null && lists.get(i).getMyResourceUrl().length() > 0) {
                        if (isClick) {
                            if (!isPlay) {
                                if (isRecordPlay) {
                                    //停止播放
                                    isClick = true;
                                    isRecordPlay = false;
                                    isSpeakReady = false;
                                    if (player != null) {
                                        try {
                                            player.pause();
                                            player.stop();
                                            player.seekTo(0);
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        }
                                        exerciseViewHolder.play.setImageResource(R.drawable.icon_play_big);
                                    }
                                } else {
                                    //开始播放
                                    isClick = false;
                                    isRecordPlay = true;
                                    isSpeakReady = true;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            play(exerciseViewHolder, i);
                                        }
                                    }).start();
                                    exerciseViewHolder.play.setImageResource(R.drawable.icon_stop_medium);
                                }
                            }
                        } else {
                            //停止播放
                            isClick = true;
                            isRecordPlay = false;
                            isSpeakReady = false;
                            if (player != null) {
                                try {
                                    if (player.isPlaying()) {
                                        player.pause();
                                        player.stop();
                                        player.seekTo(0);
                                    }
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                }
                                exerciseViewHolder.play.setImageResource(R.drawable.icon_play_big);
                            }
                        }
                    }

                }
            });
        }
    }

    public void onRecord(ExerciseViewHolder viewHolder, int i) {
        if (oral == null) {
            oral = new TAIOralEvaluation();
        }
        if (oral.isRecording()) {
            oral.stopRecordAndEvaluation(new TAIOralEvaluationCallback() {
                @Override
                public void onResult(final TAIError error) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            songView.showLoad();
                            SystemUtils.show(context, "说话结束");
                            Log.e("说话结束", error.desc + "---" + error.code);
                        }
                    });
                }
            });
        } else {
            oral.setListener(new TAIOralEvaluationListener() {
                @Override
                public void onEvaluationData(final TAIOralEvaluationData data, final TAIOralEvaluationRet result, final TAIError error) {
                    SystemUtils.saveFile(data.audio, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/", fileName + ".mp3");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (error.code != TAIErrCode.SUCC) {
//                                SystemUtils.show(context, "说话结束");
                            }
                            isRecording = false;
                            isClick = true;
                            oral = null;
                            Log.e("口语评测", result + "///" + error.desc);
                            ExerciseActivity2.viewPager.setNoFocus(false);
                            viewHolder.speak.setImageResource(R.drawable.icon_speak_medium);
                            if (result != null) {
                                double num = (result.pronAccuracy) * (result.pronCompletion) * (2 - result.pronCompletion);
                                BigDecimal bg = new BigDecimal(num / 20);
                                double num1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                lists.get(i).setScore((float) num1);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        presenter.uploadAudioResource(bookId, Integer.parseInt(lists.get(i).getPageid()), audioType, audioSource, lists.get(i).getLineId(), Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + fileName + ".mp3", (float) num1, title + "（练习）", record_time, 0, "", imageUrl, 0, homeworkid, homeworktype);
                                    }
                                }, 1000);
                                Log.e("说话结束2", result.pronAccuracy + "");
//                                notifyDataSetChanged();
                            } else {
                                songView.dismissLoad();
//                                if(error.code==3){
                                SystemUtils.show(context, "上传失败，请稍后再试");
//                                }
                            }
                        }
                    });
                }
            });

            TAIOralEvaluationParam param = new TAIOralEvaluationParam();
            param.context = context;
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
            param.refText = lists.get(i).getEnTitle();
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
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (error.code == TAIErrCode.SUCC) {
                                ExerciseActivity2.viewPager.setNoFocus(true);
                                isRecording = true;
                                isClick = false;
                                isPlay = false;
                                isSpeakReady = false;
                                isRecordPlay = false;
                                record_time = 0;
                                SystemUtils.show(context, "说话开始");
                                Log.e("说话开始", error.desc + "---" + error.code);
                                viewHolder.speak.setImageResource(R.drawable.icon_stop_medium);
                            }
                        }
                    });
                }
            });
        }
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
        player = new MediaPlayer();//录音播放
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
//            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void play(ExerciseViewHolder exerciseViewHolder, int itemnum) {
        try {
            //从音频文件中读取声音
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + fileName + ".mp3");
//            if (!file.exists()) {
//                isClick = true;
//                return;
//            }
            player.setDataSource(lists.get(itemnum).getMyResourceUrl());
            player.prepare();
            if (!player.isPlaying()) {
                player.start();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {//表示播放完毕
                isSpeakReady = false;
                isRecordPlay = false;
                player.reset();//释放资源
                isClick = true;
                Message message = new Message();
                message.what = 1;
                message.obj = exerciseViewHolder;
                handler2.sendMessage(message);
            }
        });
    }

    public void stopMedia() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();
                }
            }
            mediaPlayer.release();
            mediaPlayer = null;
            player.release();
            player = null;
        }
    }

    //停止播放我的录音
    public void stopRecordPlay() {
        if (isRecordPlay) {
            isClick = true;
            isRecordPlay = false;
            isSpeakReady = false;
            for (int i = 0; i < lists.size(); i++) {
                lists.get(i).setSelect(false);
            }
            notifyDataSetChanged();
        }
    }

    public void stopRecord() {
        if (oral != null && oral.isRecording()) {
            oral.stopRecordAndEvaluation(new TAIOralEvaluationCallback() {
                @Override
                public void onResult(final TAIError error) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            songView.showLoad();
//                            SystemUtils.show(context, "说话结束");
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        duration = mediaPlayer.getDuration() / 1000;
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isClick = true;
        isPlay = false;
        mediaPlayer.reset();
        //TODO:
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 2;
                handler2.sendMessage(message);
            }
        }).start();
        presenter.uploadAudioTime(0, audioType, audioSource, bookId, duration);
    }

    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ExerciseViewHolder holders = (ExerciseViewHolder) msg.obj;
                holders.play.setImageResource(R.drawable.icon_play_big);
            } else if (msg.what == 2) {
                holder.preview.setImageResource(R.drawable.icon_preview_big);
            }
        }
    };

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

}
