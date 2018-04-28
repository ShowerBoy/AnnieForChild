package com.annie.annieforchild.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.login.MainBean;
import com.annie.annieforchild.bean.login.PhoneSN;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.application.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by WangLei on 2018/1/30 0030
 */

public class SystemUtils {
    //    public static String mainUrl = "https://appapi.anniekids.net/api/"; //获取接口对象地址（正式）
    public static String mainUrl = "https://demoapi.anniekids.net/api/"; //获取接口对象地址（测试）

    public static String recordPath = "/record/"; //录制音频地址
    public static MainBean mainBean; //第一次启动获取的接口对象
    public static PhoneSN phoneSN; //登陆时产生的phoneSN
    public static UserInfo userInfo;//用户对象
    public static String token; //token
    public static String defaultUsername; //默认学员编号
    public static String sn; //设备sn号
    public static String tag; //会员标识
    public static int window_width;
    public static int window_height;
    Activity activity;
    final public static int SELECT_CAMER = 0;
    final public static int SELECT_PICTURE = 1;

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
    public static String getVersionCode(Context context) {
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
    public static byte[] getBytes(String filePath){
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

}
