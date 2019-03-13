package com.annie.annieforchild.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UpdateBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.presenter.imp.LoginPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.activity.reading.ReadingActivity;
import com.annie.annieforchild.ui.activity.speaking.SpeakingActivity;
import com.annie.annieforchild.ui.fragment.DakaFragment;
import com.annie.annieforchild.ui.fragment.FirstFragment;
import com.annie.annieforchild.ui.fragment.FourthFragment;
import com.annie.annieforchild.ui.fragment.SecondFragment;
import com.annie.annieforchild.ui.fragment.ThirdFragment;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseFragment;
import com.annie.baselibrary.base.BasePresenter;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends QuickNavigationBarActivity implements ViewInfo {
    TelephonyManager tm;
    Intent intent;
    String tag;
    private LoginPresenter presenter;
    private DownloadRequest downloadRequest;
    private DownloadQueue requestQueue;
    private AlertHelper helper;
    private Dialog dialog;
    private ProgressDialog progressDialog;

    {
        setRegister(true);
    }

    @Override
    protected void initView() {
        if (getIntent() != null) {
            intent = getIntent();
            tag = intent.getStringExtra("tag");
        }
        super.initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new LoginPresenterImp(this, this);
        presenter.initViewAndData();
        requestQueue = NoHttp.newDownloadQueue();
        MPermissions.requestPermissions(this, 1, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        presenter.checkUpdate(SystemUtils.getVersionCode(this), SystemUtils.getVersionName(this));
//        SystemUtils.show(this, tm.getPhoneCount() + "==" + tm.getDeviceId(0) + "==" + tm.getDeviceId(1));
        File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath);
        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/");
        File file3 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "challenge/");
        File file4 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/");
        if (!file1.exists()) {
            file1.mkdirs();
        }
        if (!file2.exists()) {
            file2.mkdirs();
        }
        if (!file3.exists()) {
            file3.mkdirs();
        }
        if (!file4.exists()) {
            file4.mkdirs();
        }
        initSoundPool(this);
        if (application.getSystemUtils().getUri() != null && application.getSystemUtils().getDefaultUsername() != null) {
            String tag = application.getSystemUtils().getUri().getQueryParameter("tag");
            if (tag != null) {
                if (tag.equals("moerduo")) {
                    Intent intent = new Intent(this, GrindEarActivity.class);
                    startActivity(intent);
                } else if (tag.equals("yuedu")) {
                    Intent intent = new Intent(this, ReadingActivity.class);
                    startActivity(intent);
                } else if (tag.equals("kouyu")) {
                    Intent intent = new Intent(this, SpeakingActivity.class);
                    startActivity(intent);
                }
            } else {
                String bookid = application.getSystemUtils().getUri().getQueryParameter("bookid");
                //0:磨耳朵 1： 流利读 2：动画
                String bookType = application.getSystemUtils().getUri().getQueryParameter("booktype");
                String bookname = application.getSystemUtils().getUri().getQueryParameter("bookname");
                String bookurl = application.getSystemUtils().getUri().getQueryParameter("bookurl");
                String animationurl = application.getSystemUtils().getUri().getQueryParameter("animationurl");
//            SystemUtils.show(this, bookid + "===" + bookType + "===" + bookname + "===" + bookurl);
                if (bookid != null && bookType != null) {
                    Song song = new Song();
                    song.setBookId(Integer.parseInt(bookid));
                    song.setBookName(bookname);
                    song.setBookImageUrl(bookurl);
                    if (bookType.equals("0")) {
                        Intent intent = new Intent(this, PracticeActivity.class);
                        intent.putExtra("song", song);
                        intent.putExtra("type", 0);
                        intent.putExtra("audioType", 0);
                        intent.putExtra("audioSource", 0);
                        intent.putExtra("bookType", 0);
                        startActivity(intent);
                    } else if (bookType.equals("1")) {
                        Intent intent = new Intent(this, PracticeActivity.class);
                        intent.putExtra("song", song);
                        intent.putExtra("type", 0);
                        intent.putExtra("audioType", 1);
                        intent.putExtra("audioSource", 0);
                        intent.putExtra("bookType", 1);
                        startActivity(intent);
                    } else if (bookType.equals("2")) {
                        Intent intent = new Intent(this, VideoActivity.class);
                        intent.putExtra("url", animationurl);
                        intent.putExtra("imageUrl", bookurl);
                        intent.putExtra("name", bookname);
                        intent.putExtra("id", bookid);
                        startActivity(intent);
                    }
                }
            }
        }
    }

    private void initSoundPool(Context context) {
        application.getSystemUtils().setPlayLists(new ArrayList<>());
        application.getSystemUtils().animMusicMap = new HashMap<>();
        application.getSystemUtils().animPool = new SoundPool(11, AudioManager.STREAM_MUSIC, 0);
        application.getSystemUtils().animMusicMap.put(1, application.getSystemUtils().animPool.load(context, R.raw.amazing, 1));
        application.getSystemUtils().animMusicMap.put(2, application.getSystemUtils().animPool.load(context, R.raw.awesome, 1));
        application.getSystemUtils().animMusicMap.put(3, application.getSystemUtils().animPool.load(context, R.raw.bingo, 1));
        application.getSystemUtils().animMusicMap.put(4, application.getSystemUtils().animPool.load(context, R.raw.excellent, 1));
        application.getSystemUtils().animMusicMap.put(5, application.getSystemUtils().animPool.load(context, R.raw.good_observation, 1));
        application.getSystemUtils().animMusicMap.put(6, application.getSystemUtils().animPool.load(context, R.raw.good_try, 1));
        application.getSystemUtils().animMusicMap.put(7, application.getSystemUtils().animPool.load(context, R.raw.great, 1));
        application.getSystemUtils().animMusicMap.put(8, application.getSystemUtils().animPool.load(context, R.raw.great_job, 1));
        application.getSystemUtils().animMusicMap.put(9, application.getSystemUtils().animPool.load(context, R.raw.nice_going, 1));
        application.getSystemUtils().animMusicMap.put(10, application.getSystemUtils().animPool.load(context, R.raw.super1, 1));
        application.getSystemUtils().animMusicMap.put(11, application.getSystemUtils().animPool.load(context, R.raw.coin, 1));
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected BaseFragment[] getFragments() {
        FirstFragment firstFragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        firstFragment.setArguments(bundle);
        SecondFragment secondFragment = new SecondFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tag", tag);
        secondFragment.setArguments(bundle2);
        ThirdFragment thirdFragment = new ThirdFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("tag", tag);
        thirdFragment.setArguments(bundle3);
        FourthFragment fourthFragment = new FourthFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("tag", tag);
        fourthFragment.setArguments(bundle4);
        DakaFragment dakaFragment = new DakaFragment();
        Bundle bundle5 = new Bundle();
        bundle5.putString("tag", tag);
        dakaFragment.setArguments(bundle5);
        return new BaseFragment[]{firstFragment, secondFragment, dakaFragment, thirdFragment, fourthFragment};
    }

//    @Override
//    protected int[] getDrawable() {
//        return new int[]{R.drawable.icon_main, R.drawable.icon_lesson, R.drawable.icon_discover, R.drawable.icon_my};
//    }

    @Override
    protected String[] getText() {
        return new String[]{"首页", "课程", "打卡", "发现", "我的"};
    }

    @Override
    protected int[] getActive_icons() {
        return new int[]{R.drawable.icon_main_t, R.drawable.icon_lesson_t, R.drawable.icon_daka_t, R.drawable.icon_discover_t, R.drawable.icon_my_t};
    }

    @Override
    protected int[] getInactive_icons() {
        return new int[]{R.drawable.icon_main_f, R.drawable.icon_lesson_f, R.drawable.icon_daka_f, R.drawable.icon_discover_f, R.drawable.icon_my_f};
    }

    @Override
    protected int[] getActive_Color() {
        return new int[]{R.color.text_orange, R.color.text_orange, R.color.text_orange, R.color.text_orange, R.color.text_orange};
    }

    @Override
    protected int[] getInactive_Color() {
        return new int[]{R.color.navigation_bar_color, R.color.navigation_bar_color, R.color.navigation_bar_color, R.color.navigation_bar_color, R.color.navigation_bar_color};
    }

    @Override
    protected int getPlan() {
        return 1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(1)
    public void requsetSuccess() {
    }

    @PermissionDenied(1)
    public void requestDenied() {
        Toast.makeText(this, "缺少权限!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        application.getSystemUtils().setWindow_width(wm.getDefaultDisplay().getWidth());
        application.getSystemUtils().setWindow_height(wm.getDefaultDisplay().getHeight());
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_CHECKUPDATE) {
            UpdateBean updateBean = (UpdateBean) message.obj;
            if (updateBean.getType() == 0) {
                //暂无更新
            } else {
                //更新
                progressDialog = SystemUtils.getDownloadProgressDialog(MainActivity.this);
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "anniekids.apk");
                if (file.exists()) {
                    file.delete();
                }
                SystemUtils.GeneralDialog(this, "更新")
                        .setMessage("检测到更新，请下载最新版本")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadRequest = NoHttp.createDownloadRequest(updateBean.getUrl(), Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath, "anniekids.apk", true, true);
                                requestQueue.add(456, downloadRequest, downloadListener);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        } else if (message.what == MethodCode.EVENT_MUSICSTOP) {
//            if (musicService != null) {
                MusicService.stop();
//            }
        }
    }

    private static void installNormal(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(apkPath);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.annie.annieforchild.installapkdemo", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onDownloadError(int i, Exception e) {
            progressDialog.dismiss();
            SystemUtils.show(MainActivity.this, "下载失败");
        }

        @Override
        public void onStart(int i, boolean b, long l, Headers headers, long l1) {
            SystemUtils.show(MainActivity.this, "开始下载");
            progressDialog.show();
        }

        @Override
        public void onProgress(int i, int i1, long l, long l1) {
            System.out.println("i1:" + i1);
            progressDialog.setProgress(i1);
//            SystemUtils.getDownloadProgressDialog(MainActivity.this).setProgress();
        }

        @Override
        public void onFinish(int i, String s) {
            progressDialog.dismiss();
            SystemUtils.show(MainActivity.this, "下载结束" + s);
            installNormal(MainActivity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "anniekids.apk");
        }

        @Override
        public void onCancel(int i) {
            progressDialog.dismiss();
            SystemUtils.show(MainActivity.this, "取消下载");
        }
    };

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
    protected void onDestroy() {
        super.onDestroy();
        if (application.getSystemUtils().getTimer() != null) {
            application.getSystemUtils().getTimer().cancel();
            application.getSystemUtils().setTimer(null);
        }
        if (application.getSystemUtils().getTask() != null) {
            application.getSystemUtils().getTask().cancel();
            application.getSystemUtils().setTask(null);
        }
    }
}
