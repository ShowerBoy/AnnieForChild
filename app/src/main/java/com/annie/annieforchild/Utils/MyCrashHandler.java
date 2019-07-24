package com.annie.annieforchild.Utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.annie.annieforchild.interactor.imp.CrashHandlerInteractorImp;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by wanglei on 2019/2/25.
 */

public class MyCrashHandler implements Thread.UncaughtExceptionHandler, RequestListener {
    private CrashHandlerInteractorImp interactor;
    private Context context;
    private MyApplication application;

    public MyCrashHandler(Context context,int type) {
        this.context = context;
        application = (MyApplication) context.getApplicationContext();
        interactor = new CrashHandlerInteractorImp(this,type);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e("程序出现异常了", "Thread = " + t.getName() + "\nThrowable = " + e.getMessage());
        String stackTraceInfo = getStackTraceInfo(e);
        Log.e("stackTraceInfo", stackTraceInfo);
        saveThrowableMessage(stackTraceInfo);
//        if (t == Looper.getMainLooper().getThread()) {
//            ActivityCollector.finishAll();
//            android.os.Process.killProcess(android.os.Process.myPid());
//        } else {
//
//        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
        //退出程序
        //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
        ActivityCollector.finishAll();
        System.exit(0);
        //从操作系统中结束掉当前程序的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 获取错误的信息
     *
     * @param throwable
     * @return
     */
    private String getStackTraceInfo(final Throwable throwable) {
        PrintWriter pw = null;
        Writer writer = new StringWriter();
        try {
            pw = new PrintWriter(writer);
            throwable.printStackTrace(pw);
        } catch (Exception e) {
            return "";
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return writer.toString();
    }

    private String logFilePath = Environment.getExternalStorageDirectory() + File.separator + "Android" +
            File.separator + "data" + File.separator + MyApplication.getContext().getPackageName() + File.separator + "crashLog";

    private void saveThrowableMessage(String errorMessage) {
        if (TextUtils.isEmpty(errorMessage)) {
            return;
        }

        if (interactor != null) {
            interactor.sendCrashMessage(application.getSystemUtils().getDefaultUsername() != null ? application.getSystemUtils().getDefaultUsername() : "", application.getSystemUtils().getPhone() != null ? application.getSystemUtils().getPhone() : "", Build.BRAND, Build.VERSION.RELEASE, SystemUtils.getVersionName(context), errorMessage);
        }

//        File file = new File(logFilePath);
//        if (!file.exists()) {
//            boolean mkdirs = file.mkdirs();
//            if (mkdirs) {
//                writeStringToFile(errorMessage, file);
//            }
//        } else {
//            writeStringToFile(errorMessage, file);
//        }
    }

    private void writeStringToFile(final String errorMessage, final File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream outputStream = null;
                try {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(errorMessage.getBytes());
                    outputStream = new FileOutputStream(new File(file, System.currentTimeMillis() + ".txt"));
                    int len = 0;
                    byte[] bytes = new byte[1024];
                    while ((len = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, len);
                    }
                    outputStream.flush();
                    Log.e("程序出异常了", "写入本地文件成功：" + file.getAbsolutePath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void Success(int what, Object result) {

    }

    @Override
    public void Error(int what, int status, String error) {

    }

    @Override
    public void Fail(int what, String error) {

    }
}
