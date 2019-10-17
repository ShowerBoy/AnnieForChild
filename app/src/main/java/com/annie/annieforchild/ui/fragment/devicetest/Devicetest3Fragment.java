package com.annie.annieforchild.ui.fragment.devicetest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.presenter.imp.FourthPresenterImp;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.ui.activity.my.DevicetestingActivity;
import com.annie.annieforchild.view.FourthView;
import com.annie.baselibrary.base.BaseFragment;
import com.example.lamemp3.PrivateInfo;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * 设备检测
 */

public class Devicetest3Fragment extends BaseFragment implements FourthView {
    private ImageView device_test_3_record_iv;
    private FourthPresenter presenter;
    private TextView device_test_3_score_text, device_test_3_score;
    public static boolean ischeck;
    AnimationDrawable animationDrawable, animationDrawableplay;
    public static String Url = "";
    public String Url_zhiling = "";
    LinearLayout play;
    private boolean isplay = false;
    private MediaPlayer player;
    private ImageView device_test_3_play_iv;
    public Handler uiHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    device_test_3_score_text.setVisibility(View.VISIBLE);
                    device_test_3_score.setVisibility(View.VISIBLE);
                    device_test_3_score.setText("" + num1);
                    break;
                case 1:
                    DevicetestingActivity.device_test_nextButton.setEnabled(true);
                    DevicetestingActivity.device_test_nextButton.setBackgroundResource(R.drawable.devicetest_next_bt_orange);
                    DevicetestingActivity.device_test_nextButton.setTextColor(getResources().getColor(R.color.text_orange));
                    break;
                case 2:
                    device_test_3_play_iv.setImageResource(R.drawable.device_play_animal); //图片资源
                    animationDrawableplay = (AnimationDrawable) device_test_3_play_iv.getDrawable();
                    animationDrawableplay.start();
                default:
                    break;

            }
            return false;
        }
    });

    private TAIOralEvaluation oral;

    {
        setRegister(true);
    }

    public static Devicetest3Fragment instance() {
        Devicetest3Fragment fragment = new Devicetest3Fragment();
        return fragment;
    }

    @Override
    protected void initData() {
        presenter = new FourthPresenterImp(getActivity(), this, "");
        presenter.initViewAndData(1);
        ischeck = false;
        player = new MediaPlayer();
    }

    @Override
    protected void initView(View view) {
        device_test_3_play_iv = view.findViewById(R.id.device_test_3_play_iv);
        play = view.findViewById(R.id.play);
        device_test_3_record_iv = view.findViewById(R.id.device_test_3_record_iv);
        device_test_3_score = view.findViewById(R.id.device_test_3_score);
        device_test_3_score_text = view.findViewById(R.id.device_test_3_score_text);
        device_test_3_record_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermission(getContext(),Manifest.permission.RECORD_AUDIO)){
                    // TODO: Consider calling
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        SystemUtils.hasPermission(getContext(),Manifest.permission.RECORD_AUDIO);
//                        MPermissions.requestPermissions(Devicetest3Fragment.this, 0, new String[]{
//                                Manifest.permission.RECORD_AUDIO
//                        });
                    }
                    Toast.makeText(getContext(), "请打开麦克风权限", Toast.LENGTH_LONG).show();
                } else {
                    onRecord();


                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Url != null && Url.length() > 0) {
                    if (!isplay) {//没有播放，需要播放
                        uiHandler.sendEmptyMessage(2);
                        if (player != null) {
                            isplay = true;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    play(Url);
                                }
                            }).start();
                        }
                    } else {
                        if (play != null) {
                            isplay = false;
                            try {
                                player.pause();
                                player.stop();
                                player.seekTo(0);
                                if(animationDrawableplay!=null){
                                    animationDrawableplay.stop();
                                }
                                device_test_3_play_iv.setImageResource(R.drawable.device_play); //图片资源
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return;
                } else if (Url_zhiling != null && Url_zhiling.length() > 0) {
                    if (!isplay) {//没有播放，需要播放
                        uiHandler.sendEmptyMessage(2);
                        if (player != null) {
                            isplay = true;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    play(Url_zhiling);
                                }
                            }).start();
                        }
                    } else {
                        if (play != null) {
                            isplay = false;
                            try {
                                player.pause();
                                player.stop();
                                player.seekTo(0);
                                if(animationDrawableplay!=null){
                                    animationDrawableplay.stop();
                                }
                                device_test_3_play_iv.setImageResource(R.drawable.device_play); //图片资源
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

//                    Toast.makeText(getActivity(), "请先录音测试", Toast.LENGTH_SHORT).show();
            }

        });
    }
    public static boolean hasPermission(Context context, String permission){
        if(context!=null){
            int perm = context.checkCallingOrSelfPermission(permission);
            return perm == PackageManager.PERMISSION_GRANTED;
        }else{
            return false;
        }


    }
    public void play(String uu) {
        try {
            //从音频文件中读取声音
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + fileName + ".mp3");
//            if (!file.exists()) {
//                isClick = true;
//                return;
//            }
            player.setDataSource(uu);
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
                player.reset();//释放资源
                if(animationDrawableplay!=null){
                    animationDrawableplay.stop();
                }
                device_test_3_play_iv.setImageResource(R.drawable.device_play); //图片资源
                isplay = false;

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_devicetest_3_fragment;
    }

    /**
     * {@link MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_UPLOADING) {
            Url = (String) message.obj;
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }

    public static int num1 = 0;

    public void onRecord() {
        if (oral == null) {
            oral = new TAIOralEvaluation();
        }
        if (oral.isRecording()) {
            oral.stopRecordAndEvaluation(new TAIOralEvaluationCallback() {
                @Override
                public void onResult(final TAIError error) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("说话结束", error.desc + "---" + error.code);
                            animationDrawable.stop();
                            device_test_3_record_iv.setImageResource(R.drawable.device_test_record); //图片资源
                        }
                    });
                }
            });
        } else {
            oral.setListener(new TAIOralEvaluationListener() {
                @Override
                public void onEvaluationData(final TAIOralEvaluationData data, final TAIOralEvaluationRet result, final TAIError error) {

                    if (SystemUtils.saveFile(data.audio, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/", "hello,how are you?" + ".mp3")
                    ) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (error.code != TAIErrCode.SUCC) {
//                                SystemUtils.show(context, "说话结束");
                                }
                                oral = null;
                                if (result != null) {
                                    Url_zhiling = result.audioUrl;
                                    double num = (result.pronAccuracy) * (result.pronCompletion) * (2 - result.pronCompletion);
                                    BigDecimal bg = new BigDecimal(num);
                                    num1 =  bg.setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
                                    uiHandler.sendEmptyMessage(0);


                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            presenter.uploadIng(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + "hello,how are you?" + ".mp3");
                                        }
                                    }, 1500);
//                                notifyDataSetChanged();
                                } else {
                                    Log.e("errrr", error.code + error.desc);
//                                if(error.code==3){
//                                    SystemUtils.show(getActivity(), "上传失败，请稍后再试");
                                    ischeck = true;
                                    uiHandler.sendEmptyMessage(1);
//                                }
                                }
                            }
                        });
                        ischeck = true;
                        uiHandler.sendEmptyMessage(1);
                    } else {
                        SystemUtils.show(getActivity(), "录音保存失败，请稍后再试");
                    }


                }
            });

            TAIOralEvaluationParam param = new TAIOralEvaluationParam();
            param.context = getActivity();
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
            param.refText = "Hello,How are you";
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (error.code == TAIErrCode.SUCC) {
                                SystemUtils.show(getActivity(), "说话开始");
                                device_test_3_record_iv.setImageResource(R.drawable.device_record_animal); //图片资源
                                animationDrawable = (AnimationDrawable) device_test_3_record_iv.getDrawable();
                                animationDrawable.start();


                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
        }
        player.release();
        player = null;
    }

    @Override
    public RecyclerView getMemberRecycler() {
        return null;
    }
}
