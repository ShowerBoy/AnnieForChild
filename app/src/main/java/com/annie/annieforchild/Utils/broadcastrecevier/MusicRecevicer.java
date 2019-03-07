package com.annie.annieforchild.Utils.broadcastrecevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.annie.annieforchild.Utils.service.MusicService;

/**
 * Created by wanglei on 2018/7/20.
 */

public class MusicRecevicer extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        String ctrl_code = intent.getAction();
        if ("play".equals(ctrl_code)) {
            //通知栏音乐播放操作
//            MusicService.play();
        } else if ("pause".equals(ctrl_code)) {
            //通知栏音乐暂停操作
//            MusicService.pause();
        } else if ("stop".equals(ctrl_code)) {
            //通知栏音乐停止操作
//            MusicService.stop();
        }
    }
}
