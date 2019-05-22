package com.annie.annieforchild.ui.activity.pk;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MusicManager;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.adapter.LyricAdapter;
import com.annie.annieforchild.ui.adapter.MusicListAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseMusicActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanglei on 2019/5/17.
 */

public class MusicPlayActivity2 extends BaseMusicActivity implements SongView, OnCheckDoubleClick, SeekBar.OnSeekBarChangeListener, PlatformActionListener, PopupWindow.OnDismissListener {
    private ImageView back, last, next, list, loop, share, pengyouquan, weixin, qq, qqzone, lyric, record, clarity;
    private RelativeLayout lyricLayout;
    private LottieAnimationView animationView;
    private ImageView play, collect;
    private TextView shareCancel, anwaRadio, coinCount, popupTitle, noLyric;
    public TextView start, end, name;
    private SeekBar seekBar;
    public static CircleImageView image;
    private ObjectAnimator animation;
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
    private int origin, audioType, resourceId, isCollect, musicPosition;
    public static int audioSource;
    public static final int STATE_PREPARE = 0;//准备中
    public static final int STATE_PLAYING = 1;//正在播放
    public static final int STATE_PAUSE = 2;//暂停
    public static final int STATE_STOP = 3;//停止
    public static int state = 3;//播放状态
    public static int args;
    private String musicTitle, musicImageUrl;
    private AlertHelper helper;
    public Dialog dialog;
    private boolean isClick = false;
    private CheckDoubleClickListener listener;
    public String TAG = "MusicPlayActivity2";
    private GrindEarPresenter presenter;
    private ShareUtils shareUtils;
    private PopupWindow popupWindow2;
    private View popupView2;
    private String url, myResourceUrl;
    private List<String> lyricList;
    private int homeworkid, homeworktype;
    private int classId = 0, collectType, shareType, radioDismiss;
    private boolean isPause;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_play2;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.music_play_back2);
        play = findViewById(R.id.music_play2);
        last = findViewById(R.id.music_last2);
        next = findViewById(R.id.music_next2);
        name = findViewById(R.id.music_title2);
        list = findViewById(R.id.music_list2);
        image = findViewById(R.id.music_image2);
        seekBar = findViewById(R.id.music_seekbar2);
        start = findViewById(R.id.start_time2);
        end = findViewById(R.id.end_time2);
        loop = findViewById(R.id.music_loop2);
        share = findViewById(R.id.music_share2);
        collect = findViewById(R.id.music_collected2);
        lyric = findViewById(R.id.music_lyric2);
        record = findViewById(R.id.music_record2);
        anwaRadio = findViewById(R.id.anwa_radio2);
        lyricLayout = findViewById(R.id.lyric_relative);
        noLyric = findViewById(R.id.no_lyric);
        clarity = findViewById(R.id.music_play_clarity);
        animationView = findViewById(R.id.music_play_animation);
        lyricRecycler = findViewById(R.id.lyric_recycler);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        list.setOnClickListener(listener);
        image.setOnClickListener(listener);
        play.setOnClickListener(listener);
        last.setOnClickListener(listener);
        next.setOnClickListener(listener);
        share.setOnClickListener(listener);
        loop.setOnClickListener(listener);
        collect.setOnClickListener(listener);
        lyric.setOnClickListener(listener);
        record.setOnClickListener(listener);
        anwaRadio.setOnClickListener(listener);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setEnabled(true);
    }

    @Override
    protected void initData() {
        musicList = new ArrayList<>();
        intent = getIntent();
        radioDismiss = intent.getIntExtra("radioDismiss", 0);
        bundle = intent.getExtras();
        setAnimation();
        initPopup();
        shareUtils = new ShareUtils(this, this);
        if (bundle != null) {
            musicTitle = bundle.getString("name");
            musicImageUrl = bundle.getString("image");
            myResourceUrl = bundle.getString("myResourceUrl");
            musicList = (List<Song>) bundle.getSerializable("list");
            musicPosition = bundle.getInt("musicPosition", 0);
            origin = bundle.getInt("origin");
            audioType = bundle.getInt("audioType", 0);
            audioSource = bundle.getInt("audioSource", 0);
            collectType = bundle.getInt("collectType", 0);
            homeworkid = bundle.getInt("homeworkid", 0);
            homeworktype = bundle.getInt("homeworktype", -1);
            //TODO:
            name.setText(musicTitle);
            Glide.with(this).load(musicImageUrl).into(image);
//            MusicManager.getInstance().updateMusicList(musicList);
        } else {
            //
//            Glide.with(this).load(R.drawable.image_music_empty).into(image);
//            name.setText("");
//            seekBar.setEnabled(false);
//            isClick = false;
//            popupTitle.setText("");
        }

        lyricList = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        adapter = new MusicListAdapter(this, MusicManager.getInstance().musicList, new OnRecyclerItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(View view) {
                int position = musicRecycler.getChildAdapterPosition(view);
                if (!MusicManager.getInstance().musicList.get(position).isPlaying()) {
//                    if (MusicService.isPlay) {
//                        MusicService.stop();
//                    }
                    musicService.play(position);
//                    if (musicService.isLyric()) {
//                        musicService.setLyric(false);
//                        image.setVisibility(View.VISIBLE);
//                        lyricLayout.setVisibility(View.GONE);
//                    }
//                    musicService.play();
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        musicRecycler.setAdapter(adapter);

        lyricAdapter = new LyricAdapter(this, lyricList);
        lyricRecycler.setAdapter(lyricAdapter);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    private void initPopup() {
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_popup_music_item, null, false);
        popupTitle = popupView.findViewById(R.id.popup_title);
        musicRecycler = popupView.findViewById(R.id.music_list_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        musicRecycler.setLayoutManager(manager);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        lyricRecycler.setLayoutManager(manager2);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, Math.max(application.getSystemUtils().getWindow_width(), application.getSystemUtils().getWindow_height()) * 2 / 5, false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(this);

        popupWindow2 = new PopupWindow(this);
        popupView2 = LayoutInflater.from(this).inflate(R.layout.activity_share_daka_item2, null, false);
        coinCount = popupView2.findViewById(R.id.coin_count);
        pengyouquan = popupView2.findViewById(R.id.share_daka_pengyouquan);
        weixin = popupView2.findViewById(R.id.share_daka_weixin);
        qq = popupView2.findViewById(R.id.share_daka_qq);
        qqzone = popupView2.findViewById(R.id.share_daka_qqzone);
        shareCancel = popupView2.findViewById(R.id.daka_share_cancel2);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        shareCancel.setOnClickListener(listener);
        coinCount.setText("分享+2金币");
        popupWindow2.setContentView(popupView2);
        popupWindow2.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow2.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow2.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow2.setOutsideTouchable(false);
        popupWindow2.setFocusable(true);
        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_CLOCKINSHARE) {
            ShareBean shareBean = (ShareBean) message.obj;
            url = shareBean.getUrl();
        } else if (message.what == MethodCode.EVENT_ISPLAYING) {
            //在播放 自动播放
            if (bundle != null) {
                MusicManager.getInstance().updateMusicList(musicList);
                musicService.play(musicPosition);
                isClick = true;
            } else {
                if (MusicManager.getInstance().musicList == null || MusicManager.getInstance().musicList.size() == 0) {
                    isClick = false;
                }
            }
        } else if (message.what == MethodCode.EVENT_UNPLAYING) {
            //不在播放 自动播放
            if (bundle != null) {
                MusicManager.getInstance().updateMusicList(musicList);
                //自动播放
                musicService.play(musicPosition);
                isClick = true;
            } else {
                if (MusicManager.getInstance().musicList == null || MusicManager.getInstance().musicList.size() == 0) {
                    isClick = false;
                }
            }
        } else if (message.what == MethodCode.EVENT_GETLYRIC) {
            List<String> list = (List<String>) message.obj;
            if (list != null && list.size() != 0) {
                lyricLayout.setVisibility(View.VISIBLE);
                noLyric.setVisibility(View.GONE);
                lyricList.clear();
                lyricList.addAll((List<String>) message.obj);
                lyricAdapter.notifyDataSetChanged();
            } else {
                lyricLayout.setVisibility(View.GONE);
                noLyric.setVisibility(View.VISIBLE);
            }
        } else if (message.what == MethodCode.EVENT_COLLECTCOURSE + 2000 + classId) {
            showInfo((String) message.obj);
            collect.setImageResource(R.drawable.icon_player_collection_t);
            MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).setIsCollected(1);
        } else if (message.what == MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId) {
            showInfo((String) message.obj);
            collect.setImageResource(R.drawable.icon_player_collection_f);
            MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).setIsCollected(0);
        } else if (message.what == MethodCode.EVENT_SHARECOIN) {
            animationView.setVisibility(View.VISIBLE);
            clarity.setVisibility(View.VISIBLE);
            if (shareType == 0) {
                animationView.setImageAssetsFolder("coin4/");
                animationView.setAnimation("coin4.json");
            } else {
                animationView.setImageAssetsFolder("coin2/");
                animationView.setAnimation("coin2.json");
            }
            animationView.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animationView.setVisibility(View.GONE);
                    clarity.setVisibility(View.GONE);
                    animation.cancel();
                    animation.clone();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animationView.loop(false);
            animationView.playAnimation();
            SystemUtils.animPool.play(SystemUtils.animMusicMap.get(11), 1, 1, 0, 0, 1);
        } else if (message.what == MethodCode.EVENT_MUSICSTOP) {
            if (musicService != null) {
                if (musicService.isPlaying()) {
                    musicService.stop();
                }
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.music_play_back2:
                finish();
                break;
            case R.id.music_play2:
                //播放
                if (musicService.isPlaying()) {
                    musicService.pause(); // 暂停
                    play.setImageResource(R.drawable.icon_music_play_big);
                    animation.pause();
                } else {
                    onPlay(musicService.resume()); // 播放
                }
                break;
            case R.id.music_last2:
                //上一曲
                musicService.last();
                break;
            case R.id.music_next2:
                //下一曲
                musicService.next();
                break;
            case R.id.music_list2:
                //播放列表
                if (!musicStart) {
                    return;
                }
                /*报错，出现内存泄漏问题，故加入界面退出加入onDestroy销毁popupwindow*/
                popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
                SystemUtils.setBackGray(this, true);
                break;
            case R.id.music_loop2:
                //循环模式
                if (!musicStart) {
                    return;
                }
                if (musicService.isSingleLoop()) {
                    musicService.setSingleLoop(false);
                    loop.setImageResource(R.drawable.icon_list_loop);
                    showInfo("列表循环");
                } else {
                    musicService.setSingleLoop(true);
                    loop.setImageResource(R.drawable.icon_single_loop);
                    showInfo("单曲循环");
                }
                break;
            case R.id.music_share2:
                //分享
                if (!musicStart) {
                    return;
                }
                presenter.clockinShare(2, MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).getBookId());
                getWindowGray(true);
                popupWindow2.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
                break;
            case R.id.music_lyric2:
                //歌词
                if (!musicStart) {
                    return;
                }
                if (musicService.isLyric()) {
                    musicService.setLyric(false);
                    image.setVisibility(View.VISIBLE);
                    lyricLayout.setVisibility(View.GONE);
                } else {
                    musicService.setLyric(true);
                    presenter.getLyric(MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).getBookId());
                    image.setVisibility(View.GONE);
                    lyricLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.music_collected2:
                //收藏
                if (!musicStart) {
                    return;
                }
                if (MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).getIsCollected() == 0) {
                    presenter.collectCourse(collectType, audioSource, MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).getBookId(), classId);
                } else {
                    presenter.cancelCollection(collectType, audioSource, MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).getBookId(), classId);
                }
                break;
            case R.id.music_record2:
                //录音
                if (!musicStart) {
                    return;
                }
                if (musicService.isPlaying()) {
                    animation.pause();
                    musicService.stop();
                }
                Intent intent = new Intent(this, RecordingActivity.class);
                intent.putExtra("bookId", MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).getBookId());
                intent.putExtra("bookName", MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).getBookName());
                intent.putExtra("bookImageUrl", MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).getBookImageUrl());
                intent.putExtra("audioType", audioType);
                intent.putExtra("audioSource", audioSource);
                intent.putExtra("resourceUrl", MusicManager.getInstance().musicList.get(musicService.getMusicIndex()).getPath());
                intent.putExtra("myResourceUrl", myResourceUrl);
                intent.putExtra("homeworkid", homeworkid);
                intent.putExtra("homeworktype", homeworktype);
                startActivity(intent);
                break;
            case R.id.anwa_radio2:
                Intent intent1 = new Intent(this, GrindEarActivity.class);
                startActivity(intent1);
                finishAffinity();
                break;
            case R.id.share_daka_pengyouquan:
                shareType = 0;
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechatMoments("我和我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在安娃电台听《" + MusicManager.getInstance().musicList.get(musicPosition).getBookName() + "》", "安娃电台喊你磨耳朵啦...", MusicManager.getInstance().musicList.get(musicPosition).getBookImageUrl(), url);
                }
                break;
            case R.id.share_daka_weixin:
                shareType = 1;
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechat("我和我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在安娃电台听《" + MusicManager.getInstance().musicList.get(musicPosition).getBookName() + "》", "安娃电台喊你磨耳朵啦...", MusicManager.getInstance().musicList.get(musicPosition).getBookImageUrl(), url);
                }
                break;
            case R.id.share_daka_qq:
                shareType = 2;
                if (url != null && url.length() != 0) {
                    shareUtils.shareQQ("我和我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在安娃电台听《" + MusicManager.getInstance().musicList.get(musicPosition).getBookName() + "》", "安娃电台喊你磨耳朵啦...", MusicManager.getInstance().musicList.get(musicPosition).getBookImageUrl(), url);
                }
                break;
            case R.id.share_daka_qqzone:
                shareType = 3;
                if (url != null && url.length() != 0) {
                    shareUtils.shareQZone("我和我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在安娃电台听《" + MusicManager.getInstance().musicList.get(musicPosition).getBookName() + "》", "安娃电台喊你磨耳朵啦...", MusicManager.getInstance().musicList.get(musicPosition).getBookImageUrl(), url);
                }
                break;
            case R.id.daka_share_cancel2:
                popupWindow2.dismiss();
                break;
        }
    }

    public void onPlay(int position) {
        if (MusicManager.getInstance().getMusicList() == null || MusicManager.getInstance().getMusicList().size() == 0) {
            return;
        }
        if (position == -1) {
            return;
        }
        seekBar.setMax(musicService.getDuration());
        seekBar.setProgress(musicService.getMusicPos());
        for (int i = 0; i < MusicManager.getInstance().musicList.size(); i++) {
            MusicManager.getInstance().musicList.get(i).setPlaying(false);
        }
        Song song = MusicManager.getInstance().musicList.get(position);
        if (song != null) {
            song.setPlaying(true);
            name.setText(song.getBookName());
            if (SystemUtils.isValidContextForGlide(this)) {
                Glide.with(this).load(song.getBookImageUrl()).into(image);
            }
            if (musicService.isSingleLoop()) {
                loop.setImageResource(R.drawable.icon_single_loop);
            } else {
                loop.setImageResource(R.drawable.icon_list_loop);
            }
            if (song.getIsCollected() == 0) {
                collect.setImageResource(R.drawable.icon_player_collection_f);
            } else {
                collect.setImageResource(R.drawable.icon_player_collection_t);
            }
            if (musicService.isLyric()) {
                image.setVisibility(View.GONE);
                lyricLayout.setVisibility(View.VISIBLE);
            } else {
                image.setVisibility(View.VISIBLE);
                lyricLayout.setVisibility(View.GONE);
            }
            if (musicService.isPlaying()) {
                play.setImageResource(R.drawable.icon_music_pause_big);
                animation.start();
            } else {
                play.setImageResource(R.drawable.icon_music_play_big);
            }
            seekBar.setProgress(musicService.getMusicPos());
            start.setText("0:00");
            end.setText(SystemUtils.mediaTimeTrans(musicService.getDuration()));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPublish(int progress) {
        if (isPause) {
            return;
        }
        seekBar.setProgress(progress);
        handler.sendEmptyMessage(0);
//        start.setText(SystemUtils.mediaTimeTrans(musicService.getCurrentDuration()));
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            try {
                int position = musicService.getCurrentDuration();
                start.setText(SystemUtils.mediaTimeTrans(position));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onChange(int position) {
        onPlay(position);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            args = progress;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        musicService.seekTo(args);
    }

    @Override
    protected void onResume() {
        musicStart = true;
        super.onResume();
        allowBindService();
        isPause = false;
    }

    @Override
    protected void onPause() {
        allowUnBindService();
        isPause = true;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.abandonAudioFocus(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bundle != null) {
            outState.putString("name", bundle.getString("name"));
            outState.putString("image", bundle.getString("image"));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            name.setText(savedInstanceState.getString("name"));
            Glide.with(this).load(savedInstanceState.getString("image")).into(image);
        }
    }

    private void setAnimation() {
        animation = ObjectAnimator.ofFloat(image, "rotation", 0f, 360f);//添加旋转动画，旋转中心默认为控件中点
        animation.setDuration(10000);//设置动画时间
        animation.setInterpolator(new LinearInterpolator());//动画时间线性渐变
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setRepeatMode(ObjectAnimator.RESTART);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
        popupWindow2.dismiss();
        presenter.shareCoin(1, shareType);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showInfo("分享取消");
        popupWindow2.dismiss();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        showInfo("分享取消");
        popupWindow2.dismiss();
    }

    @Override
    public void onDismiss() {
        SystemUtils.setBackGray(this, false);
    }

    private void getWindowGray(boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
