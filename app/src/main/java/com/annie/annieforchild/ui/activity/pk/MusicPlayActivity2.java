package com.annie.annieforchild.ui.activity.pk;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.adapter.LyricAdapter;
import com.annie.annieforchild.ui.adapter.MusicListAdapter;
import com.annie.baselibrary.base.BaseMusicActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanglei on 2019/5/17.
 */

public class MusicPlayActivity2 extends BaseMusicActivity {
    private ImageView back, last, next, list, loop, addList, share, pengyouquan, weixin, qq, qqzone, lyric, record, clarity;
    private RelativeLayout lyricLayout;
    private LottieAnimationView animationView;
    public static ImageView play, collect;
    private TextView shareCancel, anwaRadio, coinCount, popupTitle, noLyric;
    public static TextView start, end, name;
    public static SeekBar seekBar;
    public static CircleImageView image;
    public static ObjectAnimator animation;
    private Intent intent;
    private Bundle bundle;
    private List<Song> musicList; //默认播放列表
    //    private List<MusicPart> musicPartList;
    private PopupWindow popupWindow;
    private View popupView;
    private RecyclerView musicRecycler;
    public static RecyclerView lyricRecycler;
    private LyricAdapter lyricAdapter;
    public static MusicListAdapter adapter;
    private int origin, audioType, audioSource, resourceId, isCollect, musicPosition;
    public static final int STATE_PREPARE = 0;//准备中
    public static final int STATE_PLAYING = 1;//正在播放
    public static final int STATE_PAUSE = 2;//暂停
    public static final int STATE_STOP = 3;//停止
    public static int state = 3;//播放状态
    public static int args;
    private String musicTitle, musicImageUrl;
    private AlertHelper helper;
    public Dialog dialog;
    private boolean isClick = true;
    private CheckDoubleClickListener listener;
    public String TAG = "MusicPlayActivity2";
    private GrindEarPresenter presenter;
    private ShareUtils shareUtils;
    private PopupWindow popupWindow2;
    private View popupView2;
    private String url, myResourceUrl;
    private List<String> lyricList;
    private int homeworkid, homeworktype;
    public static boolean isLyric = false;
    private int classId = 0, collectType, shareType;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_play2;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MUSICSERVICE) {
            if (bundle != null) {
                //自动播放
                musicService.play(musicPosition);
            }
        } else if (message.what == MethodCode.EVENT_MUSICSERVICE2) {
            if (bundle != null) {
                //自动播放
                musicService.play(musicPosition);
            }
        }
    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onChange(int position) {

    }
}
