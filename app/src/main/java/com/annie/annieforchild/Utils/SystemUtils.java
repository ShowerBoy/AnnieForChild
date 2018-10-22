package com.annie.annieforchild.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.login.HistoryRecord;
import com.annie.annieforchild.bean.login.MainBean;
import com.annie.annieforchild.bean.login.PhoneSN;
import com.annie.annieforchild.bean.login.SigninBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.CameraActivity;
import com.annie.annieforchild.ui.activity.GlobalSearchActivity;
import com.annie.annieforchild.ui.activity.child.AddChildActivity;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.activity.pk.BookPlayActivity2;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.bumptech.glide.Glide;
//import com.github.chrisbanes.photoview.PhotoView;

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
import java.util.Calendar;
import java.util.Date;
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
    public static String mainUrl2 = "https://demoapi.anniekids.net/api/HomepageApi/test"; //（测试）

    public static String recordPath = "/record/"; //录制音频地址
    public static MainBean mainBean; //第一次启动获取的接口对象
    public static PhoneSN phoneSN; //登陆时产生的phoneSN
    public static UserInfo userInfo; //用户对象
    public static List<HistoryRecord> historyRecordList; //历史纪录
    public static SigninBean signinBean; //在线得花蜜
    public static String token; //token
    public static String defaultUsername; //默认学员编号
    public static String phone; //手机号
    public static String sn; //设备sn号
    public static String tag = "游客"; //会员标识
    public static String netDate; //网络时间
    public static Thread countDownThread; //倒计时两分钟线程
    public static boolean playAll = false; //播放全文
    public static int currentPage = 0; //当前播放页
    public static int totalPage; //总播放页
    public static int currentLine = 0; //当前播放行
    public static boolean isPlaying = false; //是否播放
    public static boolean isCurrentPage = false; //是否时当前页播放状态
    public static Timer timer;
    public static TimerTask task;
    public static boolean isOnline = false; //是否在线
    //    public static boolean isGetNectar = false; //今天是否得到过花蜜
    public static int childTag; //有无学员标识 0:无 1:有
    public static int window_width;
    public static int window_height;
    Activity activity;
    final public static int SELECT_CAMER = 0;
    final public static int SELECT_PICTURE = 1;
    public static PopupWindow popupWindow;
    public static View popupView;

    public SystemUtils(Activity activity) {
        this.activity = activity;
    }

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
     * photoView弹出框
     * @param context
     * @param url
     * @return
     */
//    public static PopupWindow getPhotoPopup(Context context, String url) {
//        PhotoView photoView = new PhotoView(context);
//        popupWindow = new PopupWindow(context);
//        popupView = LayoutInflater.from(context).inflate(R.layout.activity_photo, null, false);
//        photoView = popupView.findViewById(R.id.photoView);
//        if (url.equals("0")) {
//            Glide.with(context).load(R.drawable.route_pic_03).into(photoView);
//        } else {
//            Glide.with(context).load(url).into(photoView);
//        }
//        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                SystemUtils.setBackGray((Activity) context, false);
//            }
//        });
//        popupWindow.setContentView(popupView);
//        return popupWindow;
//    }

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
     * practiceActivity分类
     *
     * @param context
     * @return
     */
    public static PopupWindow getBookPopup(Context context, Song song, int type, int audioType, int audioSource, int collectType, String tag) {
        TextView textView = new TextView(context);
        TextView textView2 = new TextView(context);
        popupWindow = new PopupWindow(context);
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
                        MusicService.stop();
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

    /**
     * 弹出选择框选择从相机拍照或从相册选择
     *
     * @return
     */
    public AlertDialog BuildCameraDialog() {
        CharSequence[] items = {"相机拍照", "从相册选择"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
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
                                Uri imageUri = Uri.fromFile(getTempImage());
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                activity.startActivityForResult(intent, SELECT_CAMER);
                            } else {
                                Toast.makeText(activity, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //从相册选择
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("image/*");
                            activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
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

    /**
     * 获取网络时间
     */
    public static void getNetTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;//取得资源对象
                try {
                    url = new URL("http://www.baidu.com");
                    //url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
                    //url = new URL("http://www.bjtime.cn");
                    URLConnection uc = url.openConnection();//生成连接对象
                    uc.connect(); //发出连接
                    long ld = uc.getDate(); //取得网站日期时间
                    DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(ld);
                    SystemUtils.netDate = formatter.format(calendar.getTime());
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    tvNetTime.setText("当前网络时间为: \n" + format);
//                }
//            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void setBackGray(Activity activity, boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            activity.getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
            layoutParams.alpha = 1f;
            activity.getWindow().setAttributes(layoutParams);
        }
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

}
