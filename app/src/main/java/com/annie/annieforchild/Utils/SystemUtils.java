package com.annie.annieforchild.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.login.HistoryRecord;
import com.annie.annieforchild.bean.login.MainBean;
import com.annie.annieforchild.bean.login.PhoneSN;
import com.annie.annieforchild.bean.login.SigninBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.CameraActivity;
import com.annie.annieforchild.ui.activity.GlobalSearchActivity;
import com.annie.annieforchild.ui.activity.child.AddChildActivity;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.activity.pk.BookPlayActivity2;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.TbsVideo;
//import com.github.chrisbanes.photoview.PhotoView;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by WangLei on 2018/1/30 0030
 */

public class SystemUtils {
    //    public static String mainUrl = "https://appapi.anniekids.net/api/"; //获取接口对象地址（正式）
    public static String mainUrl = "https://demoapi.anniekids.net/api/"; //获取接口对象地址（测试）

    public static final String APP_ID = "wxcce6f37c8f2e3dc7"; //微信支付
    public static String recordPath = "/record/"; //录制音频地址
    final public static int SELECT_CAMER = 0;
    final public static int SELECT_PICTURE = 1;
    public static PopupWindow popupWindow;
    public static View popupView;
    public static HashMap<Integer, Integer> animMusicMap;
    public static SoundPool animPool;
    public static String weixinNum;
    private boolean isDrop = true; //流利读弹窗

    private MainBean mainBean; //第一次启动获取的接口对象
    private PhoneSN phoneSN; //登陆时产生的phoneSN
    private UserInfo userInfo; //用户对象
    private List<HistoryRecord> historyRecordList; //历史纪录
    private SigninBean signinBean; //在线得花蜜
    private String token; //token
    private String defaultUsername; //默认学员编号
    private String phone; //手机号
    private String sn; //设备sn号
    private String tag = "游客"; //会员标识
    private String netDate; //网络时间
    private Thread countDownThread; //倒计时两分钟线程
    private boolean playAll = false; //播放全文
    private int currentPage = 0; //当前播放页
    private int totalPage; //总播放页
    private int currentLine = 0; //当前播放行
    private boolean isPlaying = false; //是否播放
    private boolean isCurrentPage = false; //是否是当前页播放状态
    private Timer timer;
    private TimerTask task;
    private boolean isOnline = false; //是否在线
    //    public static boolean isGetNectar = false; //今天是否得到过花蜜
    private int childTag; //有无学员标识 0:无 1:有
    private int window_width;
    private int window_height;
    private Uri uri;
    private List<Song> playLists; //最近播放列表
    Context context;

    public SystemUtils(Context context) {
        this.context = context;
    }

//    public SystemUtils(Activity activity) {
//        this.activity = activity;
//    }

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("tag", "游客登陆");
        context.startActivity(intent);
    }

    public static void toAddChild(Context context) {
        Intent intent = new Intent(context, AddChildActivity.class);
        intent.putExtra("from", "other");
        context.startActivity(intent);
    }

    public static PopupWindow getPopup(Context context) {
        ImageView imageView = new ImageView(context);
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_popup_coundown, null, false);
        imageView = popupView.findViewById(R.id.i_see);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    public static Dialog CoundownDialog(Activity activity) {
        ImageView imageView = new ImageView(activity);
        Dialog dialog = new Dialog(activity, R.style.coundown_dialog);
        popupView = LayoutInflater.from(activity).inflate(R.layout.activity_popup_coundown, null, false);
        dialog.setContentView(popupView);
        imageView = popupView.findViewById(R.id.i_see);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SystemUtils.setBackGray(activity, false);
            }
        });
        dialog.setCancelable(false);
        return dialog;
    }

    /**
     * 作业点评
     *
     * @return
     */
    public static PopupWindow getTaskRemark(Context context, String taskscore, String average, String remark) {
        TextView title = new TextView(context);
        ImageView star1 = new ImageView(context);
        ImageView star2 = new ImageView(context);
        ImageView star3 = new ImageView(context);
        ImageView star4 = new ImageView(context);
        ImageView star5 = new ImageView(context);
        ImageView star6 = new ImageView(context);
        ImageView star7 = new ImageView(context);
        ImageView star8 = new ImageView(context);
        ImageView star9 = new ImageView(context);
        ImageView star10 = new ImageView(context);
        ImageView close = new ImageView(context);

        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_popup_task_remark, null, false);

        title = popupView.findViewById(R.id.task_remark_remark);
        star1 = popupView.findViewById(R.id.task_remark_star1);
        star2 = popupView.findViewById(R.id.task_remark_star2);
        star3 = popupView.findViewById(R.id.task_remark_star3);
        star4 = popupView.findViewById(R.id.task_remark_star4);
        star5 = popupView.findViewById(R.id.task_remark_star5);
        star6 = popupView.findViewById(R.id.task_remark_star6);
        star7 = popupView.findViewById(R.id.task_remark_star7);
        star8 = popupView.findViewById(R.id.task_remark_star8);
        star9 = popupView.findViewById(R.id.task_remark_star9);
        star10 = popupView.findViewById(R.id.task_remark_star10);
        close = popupView.findViewById(R.id.task_remark_close);

        title.setText(remark);

        int score = Integer.parseInt(taskscore);
        if (score >= 5) {
            star1.setImageResource(R.drawable.icon_starsmall);
            star2.setImageResource(R.drawable.icon_starsmall);
            star3.setImageResource(R.drawable.icon_starsmall);
            star4.setImageResource(R.drawable.icon_starsmall);
            star5.setImageResource(R.drawable.icon_starsmall);
        } else if (score == 4) {
            star1.setImageResource(R.drawable.icon_starsmall);
            star2.setImageResource(R.drawable.icon_starsmall);
            star3.setImageResource(R.drawable.icon_starsmall);
            star4.setImageResource(R.drawable.icon_starsmall);
            star5.setImageResource(R.drawable.icon_starsmall_un);
        } else if (score == 3) {
            star1.setImageResource(R.drawable.icon_starsmall);
            star2.setImageResource(R.drawable.icon_starsmall);
            star3.setImageResource(R.drawable.icon_starsmall);
            star4.setImageResource(R.drawable.icon_starsmall_un);
            star5.setImageResource(R.drawable.icon_starsmall_un);
        } else if (score == 2) {
            star1.setImageResource(R.drawable.icon_starsmall);
            star2.setImageResource(R.drawable.icon_starsmall);
            star3.setImageResource(R.drawable.icon_starsmall_un);
            star4.setImageResource(R.drawable.icon_starsmall_un);
            star5.setImageResource(R.drawable.icon_starsmall_un);
        } else if (score == 1) {
            star1.setImageResource(R.drawable.icon_starsmall);
            star2.setImageResource(R.drawable.icon_starsmall_un);
            star3.setImageResource(R.drawable.icon_starsmall_un);
            star4.setImageResource(R.drawable.icon_starsmall_un);
            star5.setImageResource(R.drawable.icon_starsmall_un);
        } else {
            star1.setImageResource(R.drawable.icon_starsmall_un);
            star2.setImageResource(R.drawable.icon_starsmall_un);
            star3.setImageResource(R.drawable.icon_starsmall_un);
            star4.setImageResource(R.drawable.icon_starsmall_un);
            star5.setImageResource(R.drawable.icon_starsmall_un);
        }

        String[] averageScore = average.split("\\.");
        int tenth = Integer.parseInt(averageScore[0]);
        int first = Integer.parseInt(averageScore[1]);
        if (tenth >= 5) {
            star6.setImageResource(R.drawable.icon_starsmall);
            star7.setImageResource(R.drawable.icon_starsmall);
            star8.setImageResource(R.drawable.icon_starsmall);
            star9.setImageResource(R.drawable.icon_starsmall);
            star10.setImageResource(R.drawable.icon_starsmall);
        } else if (tenth == 4) {
            star6.setImageResource(R.drawable.icon_starsmall);
            star7.setImageResource(R.drawable.icon_starsmall);
            star8.setImageResource(R.drawable.icon_starsmall);
            star9.setImageResource(R.drawable.icon_starsmall);
            if (first <= 3) {
                star10.setImageResource(R.drawable.icon_starsmall_un);
            } else if (first <= 7) {
                star10.setImageResource(R.drawable.icon_starsmall_half);
            } else {
                star10.setImageResource(R.drawable.icon_starsmall);
            }
        } else if (tenth == 3) {
            star6.setImageResource(R.drawable.icon_starsmall);
            star7.setImageResource(R.drawable.icon_starsmall);
            star8.setImageResource(R.drawable.icon_starsmall);
            if (first <= 3) {
                star9.setImageResource(R.drawable.icon_starsmall_un);
            } else if (first <= 7) {
                star9.setImageResource(R.drawable.icon_starsmall_half);
            } else {
                star9.setImageResource(R.drawable.icon_starsmall);
            }
            star10.setImageResource(R.drawable.icon_starsmall_un);
        } else if (tenth == 2) {
            star6.setImageResource(R.drawable.icon_starsmall);
            star7.setImageResource(R.drawable.icon_starsmall);
            if (first <= 3) {
                star8.setImageResource(R.drawable.icon_starsmall_un);
            } else if (first <= 7) {
                star8.setImageResource(R.drawable.icon_starsmall_half);
            } else {
                star8.setImageResource(R.drawable.icon_starsmall);
            }
            star9.setImageResource(R.drawable.icon_starsmall_un);
            star10.setImageResource(R.drawable.icon_starsmall_un);
        } else if (tenth == 1) {
            star6.setImageResource(R.drawable.icon_starsmall);
            if (first <= 3) {
                star7.setImageResource(R.drawable.icon_starsmall_un);
            } else if (first <= 7) {
                star7.setImageResource(R.drawable.icon_starsmall_half);
            } else {
                star7.setImageResource(R.drawable.icon_starsmall);
            }
            star8.setImageResource(R.drawable.icon_starsmall_un);
            star9.setImageResource(R.drawable.icon_starsmall_un);
            star10.setImageResource(R.drawable.icon_starsmall_un);
        } else {
            if (first <= 3) {
                star6.setImageResource(R.drawable.icon_starsmall_un);
            } else if (first <= 7) {
                star6.setImageResource(R.drawable.icon_starsmall_half);
            } else {
                star6.setImageResource(R.drawable.icon_starsmall);
            }
            star7.setImageResource(R.drawable.icon_starsmall_un);
            star8.setImageResource(R.drawable.icon_starsmall_un);
            star9.setImageResource(R.drawable.icon_starsmall_un);
            star10.setImageResource(R.drawable.icon_starsmall_un);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
            }
        });
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    /**
     * 花蜜弹出框
     *
     * @param context
     * @return
     */
    public static PopupWindow getNectarPopup(Context context, int type) {
        ImageView imageView = new ImageView(context);
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_popup_nectar, null, false);
        imageView = popupView.findViewById(R.id.nectar_image);
        if (type == 0) {
            imageView.setImageResource(R.drawable.luckydraw_pic_1);
        } else if (type == 1) {
            imageView.setImageResource(R.drawable.luckydraw_pic_2);
        } else if (type == 2) {
            imageView.setImageResource(R.drawable.luckydraw_pic_5);
        } else if (type == 3) {
            imageView.setImageResource(R.drawable.luckydraw_pic_10);
        } else if (type == 4) {
            imageView.setImageResource(R.drawable.luckydraw_pic_20);
        } else if (type == 5) {
            imageView.setImageResource(R.drawable.luckydraw_pic_again);
        } else if (type == 6) {
            imageView.setImageResource(R.drawable.luckydraw_pic_boom);
        }
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.pop_in_animation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_NECTAR;
                message.obj = type;
                EventBus.getDefault().post(message);
            }
        });
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    /**
     * 恭喜获得花蜜
     *
     * @return
     */
    public static PopupWindow getNectarCongratulation(Context context, int count) {
        TextView textView = new TextView(context);
        ImageView close = new ImageView(context);
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_nectar_congratulation, null, false);
        textView = popupView.findViewById(R.id.nectar_text);
        close = popupView.findViewById(R.id.nectar_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        textView.setText("获得 " + count + " 花蜜奖励");
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setAnimationStyle(R.style.pop_in_animation);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
            }
        });
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    /**
     * 课程结束
     *
     * @return
     */
    public static PopupWindow getLessonBack(Context context) {
        Button button = new Button(context);
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_popup_lesson_back, null, false);
        button = popupView.findViewById(R.id.lesson_back_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setAnimationStyle(R.style.pop_in_animation2);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
            }
        });
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    /**
     * 课时备注
     *
     * @param context
     * @param message
     * @return
     */
    public static PopupWindow getHintPopup(Context context, String message) {
        TextView textView = new TextView(context);
        TextView textView2 = new TextView(context);
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_popup_hint, null, false);
        textView = popupView.findViewById(R.id.popup_confirm_btn);
        textView2 = popupView.findViewById(R.id.period_remarks);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        textView2.setText(message);
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
            }
        });
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    /**
     * 网课礼包
     *
     * @param context
     * @return
     */
    public static PopupWindow getGiftPopup(Context context, String title, String content, String remarks) {
        TextView textView = new TextView(context);
        TextView textView2 = new TextView(context);
        TextView textView3 = new TextView(context);
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_gift_popup, null, false);
        textView = popupView.findViewById(R.id.gift_title);
        textView2 = popupView.findViewById(R.id.gift_content);
        textView3 = popupView.findViewById(R.id.gift_remarks);

        textView.setText(title);
        textView2.setText(content);
        textView3.setText(remarks);
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
            }
        });
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    /**
     * 课时提议
     *
     * @param context
     * @return
     */
    public static PopupWindow getSuggestPopup(Context context, String title, String text1, String text2, GrindEarPresenter presenter, int periodid, int nectar, String bookname, int bookid, int classId) {
        TextView textView = new TextView(context);
        TextView textView2 = new TextView(context);
        TextView titleText = new TextView(context);
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_popup_suggest, null, false);
        textView = popupView.findViewById(R.id.suggest_confirm_btn);
        textView2 = popupView.findViewById(R.id.suggest_cancel_btn);
        titleText = popupView.findViewById(R.id.suggest_text);
        titleText.setText(title);
        textView.setText(text1);
        textView2.setText(text2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (periodid == -1) {
                    presenter.unlockBook(nectar, bookname, bookid, classId);
                } else {
                    presenter.suggestPeriod(periodid);
                }
                popupWindow.dismiss();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
            }
        });
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    /**
     * 绑定微信
     *
     * @param context
     * @return
     */
    public static PopupWindow getBindWeixin(Context context, FourthPresenter presenter) {
        EditText editText = new EditText(context);
        TextView confirm = new TextView(context);
        TextView cancel = new TextView(context);
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_bind_weixin, null, false);
        editText = popupView.findViewById(R.id.bind_weixin);
        confirm = popupView.findViewById(R.id.bind_confirm_btn);
        cancel = popupView.findViewById(R.id.bind_cancel_btn);
        EditText finalEditText = editText;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strs = finalEditText.getText().toString();
                String str = SystemUtils.stringFilter(strs.toString());
                if (!strs.equals(str)) {
                    finalEditText.setText(str);
                    finalEditText.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalEditText.getText() != null && finalEditText.getText().toString().trim().length() != 0) {
                    weixinNum = finalEditText.getText().toString().trim();
                    presenter.bindWeixin(weixinNum);
                    popupWindow.dismiss();
                } else {
                    popupWindow.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
            }
        });
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    /**
     * practiceActivity分类
     *
     * @param context
     * @return
     */
    public static PopupWindow getBookPopup(Context context, Song song, int type, int audioType, int audioSource, int collectType, String tag) {
        TextView textView = new TextView(context);
        TextView textView2 = new TextView(context);
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_popup_search, null, false);
        textView = popupView.findViewById(R.id.iwantgrind_btn);
        textView2 = popupView.findViewById(R.id.iwantreading_btn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PracticeActivity.class);
                intent.putExtra("song", song);
                intent.putExtra("type", type);
                intent.putExtra("audioType", audioType);
                intent.putExtra("audioSource", audioSource);
                intent.putExtra("collectType", collectType);
                intent.putExtra("bookType", 0);
                context.startActivity(intent);
                popupWindow.dismiss();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (tag.equals("校园生活故事1") || tag.equals("校园生活故事2") || tag.equals("神奇树屋")) {
                    if (MusicService.isPlay) {
//                        MusicService.stop();
                    }
                    Intent intent = new Intent(context, BookPlayActivity2.class);
                    intent.putExtra("bookId", song.getBookId());
                    intent.putExtra("imageUrl", song.getBookImageUrl());
                    intent.putExtra("audioType", 3);
                    intent.putExtra("audioSource", 8);
                    intent.putExtra("title", song.getBookName());
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, PracticeActivity.class);
                    intent.putExtra("song", song);
                    intent.putExtra("type", type);
                    intent.putExtra("audioType", audioType);
                    intent.putExtra("audioSource", audioSource);
                    intent.putExtra("collectType", collectType);
                    intent.putExtra("bookType", 1);
                    context.startActivity(intent);
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
            }
        });
        popupWindow.setContentView(popupView);
        return popupWindow;
    }

    /**
     * 通用提示框
     *
     * @param context
     * @return
     */
    public static AlertDialog.Builder GeneralDialog(Context context, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setCancelable(false);
        return builder;
    }

    public static ProgressDialog getDownloadProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("下载中");
        progressDialog.setMessage("请耐心等候...");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    /**
     * 弹出选择框选择从相机拍照或从相册选择
     *
     * @return
     */
    public AlertDialog BuildCameraDialog(Activity activitys) {
        CharSequence[] items = {"相机拍照", "从相册选择"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activitys)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            //相机拍照
                            String state = Environment.getExternalStorageState();
                            if (state.equals(Environment.MEDIA_MOUNTED)) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 指定存储照片的路径
                                Uri imageUri = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                    imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", getTempImage());
                                    imageUri = FileProvider.getUriForFile(activitys, "com.annie.annieforchild.installapkdemo", getTempImage());
                                } else {
                                    imageUri = Uri.fromFile(getTempImage());
                                }
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                activitys.startActivityForResult(intent, SELECT_CAMER);
                            } else {
                                Toast.makeText(context, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //从相册选择
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("image/*");
                            activitys.startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                        }
                    }
                });
        return builder.create();
    }

    /**
     * 头像本地存储地址
     *
     * @return
     */
    public static File getTempImage() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
//            String date =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String path = Environment.getExternalStorageDirectory() + "/annie/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File tempFile = new File(path, "temp" + ".png");
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tempFile;
        }
        return null;
    }

    private static Uri getFileUri(Context context, String filePath) {
        Uri mUri = null;
        File mFile = new File(filePath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mUri = FileProvider.getUriForFile(context, "com.annie.annieforchild", mFile);
        } else {
            mUri = Uri.fromFile(mFile);
        }
        return mUri;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int version = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getVersionName(Context context) {
        String version = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 判断有没有网络连接
     */
    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return ((connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static File saveBitmapFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\]. <>/?~！@#￥%……&*（）——+|{}【】《》‘；：”“’。，、？\"]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    public static void setEditTextInhibitInputSpeChat2(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[||]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    //String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//只允许字母、数字和汉字
    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//只允许字母、数字和汉字
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String stringFilter2(String str) throws PatternSyntaxException {
        String regEx = "[^0-9]";//只允许数字
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static void setBackGray(Activity activity, boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            activity.getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
            layoutParams.alpha = 1f;
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            activity.getWindow().setAttributes(layoutParams);
        }
    }

    public static String secToHour(int second) {
        String time;
        int min = second / 60;
        int remainder = second % 60;
        int hour = min / 60;
        if (min <= 0) {
            if (second == 0) {
                //0s
                time = "0小时0分钟";
            } else {
                //0-59s
                time = "0小时1分钟";
            }
        } else {
            if (remainder > 0) {
                min = min + 1;
            }
            min = min - hour * 60;
            time = hour + "小时" + min + "分钟";
        }
        return time;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(today);
        return result;
    }


    public static int pixelToDp(Context context, int pixel) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return pixel < 0 ? pixel : Math.round(pixel / displayMetrics.density);
    }

    public static int dpToPixel(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp < 0 ? dp : Math.round(dp * displayMetrics.density);
    }

    public static float dp2px(Resources resources, float dpValue) {
        final float scale = resources.getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    public static void startVideo(Context context, String url) {
        //判断当前Tbs播放器是否已经可以使用。
        //public static boolean canUseTbsPlayer(Context context)
        //直接调用播放接口，传入视频流的url
        //public static void openVideo(Context context, String videoUrl)
        //extraData对象是根据定制需要传入约定的信息，没有需要可以传如null
        //public static void openVideo(Context context, String videoUrl, Bundle extraData)

        if ((TbsVideo.canUseTbsPlayer(context))) {
            //可以播放视频
            TbsVideo.openVideo(context, url);

        } else {
            Toast.makeText(context, "视频播放器没有准备好", Toast.LENGTH_SHORT).show();
        }

    }


    public MainBean getMainBean() {
        return mainBean;
    }

    public void setMainBean(MainBean mainBean) {
        this.mainBean = mainBean;
    }

    public PhoneSN getPhoneSN() {
        return phoneSN;
    }

    public void setPhoneSN(PhoneSN phoneSN) {
        this.phoneSN = phoneSN;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<HistoryRecord> getHistoryRecordList() {
        return historyRecordList;
    }

    public void setHistoryRecordList(List<HistoryRecord> historyRecordList) {
        this.historyRecordList = historyRecordList;
    }

    public SigninBean getSigninBean() {
        return signinBean;
    }

    public void setSigninBean(SigninBean signinBean) {
        this.signinBean = signinBean;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public void setDefaultUsername(String defaultUsername) {
        this.defaultUsername = defaultUsername;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNetDate() {
        return netDate;
    }

    public void setNetDate(String netDate) {
        this.netDate = netDate;
    }

    public Thread getCountDownThread() {
        return countDownThread;
    }

    public void setCountDownThread(Thread countDownThread) {
        this.countDownThread = countDownThread;
    }

    public boolean isPlayAll() {
        return playAll;
    }

    public void setPlayAll(boolean playAll) {
        this.playAll = playAll;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isCurrentPage() {
        return isCurrentPage;
    }

    public void setCurrentPage(boolean currentPage) {
        isCurrentPage = currentPage;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public TimerTask getTask() {
        return task;
    }

    public void setTask(TimerTask task) {
        this.task = task;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getChildTag() {
        return childTag;
    }

    public void setChildTag(int childTag) {
        this.childTag = childTag;
    }

    public int getWindow_width() {
        return window_width;
    }

    public void setWindow_width(int window_width) {
        this.window_width = window_width;
    }

    public int getWindow_height() {
        return window_height;
    }

    public void setWindow_height(int window_height) {
        this.window_height = window_height;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public List<Song> getPlayLists() {
        return playLists;
    }

    public void setPlayLists(List<Song> playLists) {
        this.playLists = playLists;
    }

    public boolean isDrop() {
        return isDrop;
    }

    public void setDrop(boolean drop) {
        isDrop = drop;
    }
}
