package com.annie.annieforchild.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.annie.annieforchild.R;
import com.mob.MobSDK;

import java.io.File;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * "https://demoapi.anniekids.net/api/searchApi/index"
 * Created by wanglei on 2018/5/9.
 */

public class ShareUtils {
    private PlatformActionListener listener;
    private Context context;
    private Bitmap bitmap;
    private File file;

    public ShareUtils(Context context, PlatformActionListener listener) {
        this.context = context;
        this.listener = listener;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon);
        File files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath);
        if (!files.exists()) {
            files.mkdirs();
        }
        file = SystemUtils.saveBitmapFile(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "app_icon.jpg");
    }

    public void shareWechat(String title, String text, String url) {
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(text);
        shareParams.setTitle(title);
        shareParams.setUrl(url);
        shareParams.setImagePath(file.getAbsolutePath());
        shareParams.setImagePath(null);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(listener);
        platform.share(shareParams);
    }

    public void shareWechatMoments(String title, String text, String url) {
        Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(text);
        shareParams.setTitle(title);
        shareParams.setUrl(url);
        shareParams.setImagePath(file.getAbsolutePath());
        shareParams.setImagePath(null);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(listener);
        platform.share(shareParams);
    }

    public void shareQQ(String title, String text, String url) {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(text);
        shareParams.setTitle(title);
        shareParams.setTitleUrl(url);
        shareParams.setImagePath(file.getAbsolutePath());
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(listener);
        platform.share(shareParams);
    }

    public void shareQZone(String title, String text, String url) {
        Platform platform = ShareSDK.getPlatform(QZone.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(text);
        shareParams.setTitle(title);
        shareParams.setUrl(url);
        shareParams.setTitleUrl(url);
        shareParams.setImagePath(file.getAbsolutePath());
        platform.setPlatformActionListener(listener);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.share(shareParams);
    }
}
