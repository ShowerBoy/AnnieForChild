package com.annie.annieforchild.Utils;

import android.graphics.Bitmap;

import com.annie.annieforchild.R;
import com.mob.MobSDK;

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

    public ShareUtils(PlatformActionListener listener) {
        this.listener = listener;
    }

    public void shareWechat(String title,String url){
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        Platform.ShareParams shareParams = new  Platform.ShareParams();
        shareParams.setText("安妮花教育");
        shareParams.setTitle(title);
        shareParams.setUrl(url);
        shareParams.setImageData(null);
        shareParams.setImagePath(null);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(listener);
        platform.share(shareParams);
    }

    public void shareWechatMoments(String title,String url){
        Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
        Platform.ShareParams shareParams = new  Platform.ShareParams();
        shareParams.setText("安妮花教育");
        shareParams.setTitle(title);
        shareParams.setUrl(url);
        shareParams.setImageData(null);
        shareParams.setImagePath(null);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(listener);
        platform.share(shareParams);
    }

    public void shareQQ(String title,String url){
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new  Platform.ShareParams();
        shareParams.setText("安妮花教育");
        shareParams.setTitle(title);
        shareParams.setTitleUrl(url);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(listener);
        platform.share(shareParams);
    }

    public void shareQZone(String title,String url){
        Platform platform = ShareSDK.getPlatform(QZone.NAME);
        Platform.ShareParams shareParams = new  Platform.ShareParams();
        shareParams.setText("安妮花教育");
        shareParams.setTitle(title);
        shareParams.setUrl(url);
        shareParams.setTitleUrl(url);
        platform.setPlatformActionListener(listener);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.share(shareParams);
    }
}
