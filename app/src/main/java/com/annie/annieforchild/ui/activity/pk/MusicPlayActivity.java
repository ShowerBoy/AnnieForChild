package com.annie.annieforchild.ui.activity.pk;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.ClockIn;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.bean.song.MusicPart;
import com.annie.annieforchild.bean.song.MusicSong;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.CollectionPresenter;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.CollectionPresenterImp;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.adapter.LyricAdapter;
import com.annie.annieforchild.ui.adapter.MusicListAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 播放器界面
 * Created by wanglei on 2018/7/19.
 */

public class MusicPlayActivity extends BaseActivity implements SongView, OnCheckDoubleClick, SeekBar.OnSeekBarChangeListener, PopupWindow.OnDismissListener, PlatformActionListener {
    private ImageView back, last, next, list, loop, addList, share, pengyouquan, weixin, qq, qqzone, lyric, record, clarity;
    private LottieAnimationView animationView;
    public static ImageView play, collect;
    private TextView shareCancel, anwaRadio, coinCount, popupTitle;
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
    public static final int STATE_PLAYING = 1;//正在播放
    public static final int STATE_PAUSE = 2;//暂停
    public static final int STATE_STOP = 3;//停止
    private int state = 3;//播放状态
    public static int args;
    private String musicTitle, musicImageUrl;
    private AlertHelper helper;
    public Dialog dialog;
    private boolean isClick = true;
    private CheckDoubleClickListener listener;
    public String TAG = "MusicPlayActivity";
    private GrindEarPresenter presenter;
    private ShareUtils shareUtils;
    private PopupWindow popupWindow2;
    private View popupView2;
    private String url, myResourceUrl;
    private List<String> lyricList;
    private int homeworkid;
    public static boolean isLyric = false;
    private int classId = 0, collectType, shareType;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_play;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.music_play_back);
        play = findViewById(R.id.music_play);
        last = findViewById(R.id.music_last);
        next = findViewById(R.id.music_next);
        name = findViewById(R.id.music_title);
        list = findViewById(R.id.music_list);
        image = findViewById(R.id.music_image);
        seekBar = findViewById(R.id.music_seekbar);
        start = findViewById(R.id.start_time);
        end = findViewById(R.id.end_time);
        loop = findViewById(R.id.music_loop);
        share = findViewById(R.id.music_share);
        addList = findViewById(R.id.music_add_list);
        collect = findViewById(R.id.music_collected);
        lyric = findViewById(R.id.music_lyric);
        record = findViewById(R.id.music_record);
        anwaRadio = findViewById(R.id.anwa_radio);
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
        addList.setOnClickListener(listener);
        collect.setOnClickListener(listener);
        lyric.setOnClickListener(listener);
        record.setOnClickListener(listener);
        anwaRadio.setOnClickListener(listener);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setEnabled(true);
        setAnimation();
        intent = getIntent();
        bundle = intent.getExtras();
        musicList = new ArrayList<>();


        popupView = LayoutInflater.from(this).inflate(R.layout.activity_popup_music_item, null, false);
        popupTitle = popupView.findViewById(R.id.popup_title);
        musicRecycler = popupView.findViewById(R.id.music_list_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        musicRecycler.setLayoutManager(manager);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        lyricRecycler.setLayoutManager(manager2);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, Math.max(SystemUtils.window_width, SystemUtils.window_height) * 2 / 5, false);
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
        popupWindow2.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow2.setOutsideTouchable(false);
        popupWindow2.setFocusable(true);
        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });
        shareUtils = new ShareUtils(this, this);

        if (bundle != null) {
            musicTitle = bundle.getString("name");
            musicImageUrl = bundle.getString("image");
            myResourceUrl = bundle.getString("myResourceUrl");
            musicList = (List<Song>) bundle.getSerializable("list");
            origin = bundle.getInt("origin");
            audioType = bundle.getInt("audioType", 0);
            audioSource = bundle.getInt("audioSource", 0);
            resourceId = bundle.getInt("resourceId");
            isCollect = bundle.getInt("isCollect");
            musicPosition = bundle.getInt("musicPosition");
            collectType = bundle.getInt("collectType", 0);
            homeworkid = bundle.getInt("homeworkid");
            MusicService.type = bundle.getString("type");
            name.setText(musicTitle);
            if (isCollect == 0) {
                collect.setImageResource(R.drawable.icon_player_collection_f);
            } else {
                collect.setImageResource(R.drawable.icon_player_collection_t);
            }
            Glide.with(this).load(musicImageUrl).into(image);
            MusicService.setMusicTitle(musicTitle, musicImageUrl, origin, audioType, audioSource, resourceId, isCollect, collectType, this);
            if (musicList != null) {
                MusicService.setMusicList(musicList);
            }
            MusicService.setMusicPosition(musicPosition);
            lyricRecycler.setVisibility(View.GONE);
            if (MusicService.type != null) {
                if (MusicService.type.equals("radio")) {
                    popupTitle.setText("电台");
                } else if (MusicService.type.equals("collection")) {
                    popupTitle.setText("收藏");
                } else if (MusicService.type.equals("recently")) {
                    popupTitle.setText("最近");
                }
            } else {
                popupTitle.setText("");
            }

        } else {
            name.setText(MusicService.musicTitle);
            if (MusicService.musicIsCollect == 0) {
                collect.setImageResource(R.drawable.icon_player_collection_f);
            } else {
                collect.setImageResource(R.drawable.icon_player_collection_t);
            }
            Glide.with(this).load(MusicService.musicImageUrl).into(image);
            if (MusicService.end != null) {
                end.setText(MusicService.end);
            }
            if (MusicService.start != null) {
                start.setText(MusicService.start);
            }
            if (MusicService.type != null) {
                if (MusicService.type.equals("radio")) {
                    popupTitle.setText("电台");
                } else if (MusicService.type.equals("collection")) {
                    popupTitle.setText("收藏");
                } else if (MusicService.type.equals("recently")) {
                    popupTitle.setText("最近");
                }
            } else {
                popupTitle.setText("");
            }
        }
        if (MusicService.musicTitle == null) {
            Glide.with(this).load(R.drawable.image_music_empty).into(image);
            seekBar.setEnabled(false);
            isClick = false;
            popupTitle.setText("");
        }
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
    protected void initData() {
        lyricList = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        adapter = new MusicListAdapter(this, MusicService.musicPartList, new OnRecyclerItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(View view) {
                int position = musicRecycler.getChildAdapterPosition(view);
                if (!MusicService.musicPartList.get(position).isPlaying()) {
                    if (MusicService.isPlay) {
                        MusicService.stop();
                    }
                    MusicService.listTag = position;
                    if (isLyric) {
                        isLyric = false;
                        image.setVisibility(View.VISIBLE);
                        lyricRecycler.setVisibility(View.GONE);
                    }
                    MusicService.play();
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        musicRecycler.setAdapter(adapter);

        lyricAdapter = new LyricAdapter(this, lyricList);
        lyricRecycler.setAdapter(lyricAdapter);
        if (MusicService.isPlay) {
            play.setImageResource(R.drawable.icon_music_pause_big);
            animation.start();
            state = STATE_PLAYING;
        } else {
            if (MusicService.musicList != null && MusicService.musicList.size() != 0) {
                //自动播放
                play.setImageResource(R.drawable.icon_music_pause_big);
//                if (animation.isRunning()){
//
//                }
//                animation.start();
                musicService.play();
                state = STATE_PLAYING;
                //TODO:
//                for (int i = 0; i < MusicService.musicPartList.size(); i++) {
//                    MusicService.musicPartList.get(i).setPlaying(false);
//                }
//                MusicService.musicPartList.get(MusicService.listTag).setPlaying(true);
//                adapter.notifyDataSetChanged();
            }
        }
        if (MusicService.singleLoop) {
            loop.setImageResource(R.drawable.icon_single_loop);
        } else {
            loop.setImageResource(R.drawable.icon_list_loop);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void Complete() {
        args = 0;
        animation.resume();
        animation.pause();
        play.setImageResource(R.drawable.icon_music_play_big);
        start.setText(MusicService.start);
        seekBar.setProgress(MusicService.pos);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!isClick) {
            return;
        }
        if (fromUser) {
            args = progress;
        }

        long pos = (args * MusicService.musicDuration * 1000) / seekBar.getMax();
        MusicService.pos = (int) pos;
//        MusicService.mediaPlayer.seekTo((int) pos);
        int min = (int) pos / 1000 / 60;
        int second = (int) pos / 1000 % 60;
        if (second < 10) {
            MusicService.start = min + ":0" + second;
        } else {
            MusicService.start = min + ":" + second;
        }
        start.setText(MusicService.start);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (!isClick) {
            return;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (!isClick) {
            return;
        }
        long pos = (args * MusicService.musicDuration * 1000) / seekBar.getMax();
        MusicService.pos = (int) pos;
        MusicService.mediaPlayer.seekTo((int) pos);
    }

    @Override
    public void onDismiss() {
        SystemUtils.setBackGray(this, false);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.music_play_back:
                finish();
                break;
            case R.id.music_image:
                if (!isClick) {
                    return;
                }
//                if (state == STATE_STOP) {
//                    animation.start();//动画开始
//                    state = STATE_PLAYING;
//                } else if (state == STATE_PAUSE) {
//                    animation.resume();//动画重新开始
//                    state = STATE_PLAYING;
//                } else if (state == STATE_PLAYING) {
//                    animation.pause();//动画暂停
//                    state = STATE_PAUSE;
//                }
                break;
            case R.id.music_list:
//                if (!isClick) {
//                    return;
//                }
                popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
                SystemUtils.setBackGray(this, true);
                break;
            case R.id.music_add_list:
                if (MusicService.musicTitle != null) {
                    if (SystemUtils.defaultUsername != null) {
//                        List<MusicSong> lists = DataSupport.findAll(MusicSong.class);
                        List<Song> song_list = new ArrayList<>();
                        Song song = new Song();
                        song.setBookId(resourceId);
                        song.setBookName(musicTitle);
                        song.setBookImageUrl(musicImageUrl);
                        song.setPath(musicList.get(MusicService.listTag).getPath());
                        song_list.add(song);
                        MusicSong musicSong = new MusicSong();
                        musicSong.setUsername(SystemUtils.defaultUsername);
                        musicSong.setList(song_list);
                        musicSong.save();
                    }
                }
                break;
            case R.id.music_play:
                if (!isClick) {
                    return;
                }
                if (state == STATE_STOP) {
                    //播放
                    play.setImageResource(R.drawable.icon_music_pause_big);
//                    animation.start();
                    musicService.play();
                    state = STATE_PLAYING;
                    //TODO:
//                    for (int i = 0; i < MusicService.musicPartList.size(); i++) {
//                        MusicService.musicPartList.get(i).setPlaying(false);
//                    }
//                    MusicService.musicPartList.get(MusicService.listTag).setPlaying(true);
//                    adapter.notifyDataSetChanged();
                } else if (state == STATE_PLAYING) {
                    //暂停播放
                    play.setImageResource(R.drawable.icon_music_play_big);
                    animation.pause();
                    musicService.pause();
                    state = STATE_PAUSE;
                } else if (state == STATE_PAUSE) {
                    play.setImageResource(R.drawable.icon_music_pause_big);
                    animation.resume();
                    musicService.play();
                    state = STATE_PLAYING;
                }
                break;
            case R.id.music_last:
                //上一曲
                if (!isClick) {
                    return;
                }
                if (MusicService.musicNum != 1) {
                    if (MusicService.listTag != 0) {
                        if (MusicService.isPlay) {
                            MusicService.stop();
                        }
                        MusicService.listTag--;
                        MusicService.pos = 0;
                        MusicService.play();
                        if (isLyric) {
                            isLyric = false;
                            image.setVisibility(View.VISIBLE);
                            lyricRecycler.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case R.id.music_next:
                //下一曲
                if (!isClick) {
                    return;
                }
                if (MusicService.musicNum != 1) {
                    if (MusicService.listTag + 1 != MusicService.musicNum) {
                        if (MusicService.isPlay) {
                            MusicService.stop();
                        }
                        MusicService.listTag++;
                        MusicService.pos = 0;
                        MusicService.play();
                        if (isLyric) {
                            isLyric = false;
                            image.setVisibility(View.VISIBLE);
                            lyricRecycler.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case R.id.music_loop:
                //循环
                if (MusicService.singleLoop) {
                    MusicService.singleLoop = false;
                    loop.setImageResource(R.drawable.icon_list_loop);
                    showInfo("列表循环");
                } else {
                    MusicService.singleLoop = true;
                    loop.setImageResource(R.drawable.icon_single_loop);
                    showInfo("单曲循环");
                }
                break;
            case R.id.music_share:
                //分享
                if (!isClick) {
                    return;
                }
                presenter.clockinShare(2, MusicService.musicResourceId);
                getWindowGray(true);
                popupWindow2.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
                break;
            case R.id.music_collected:
                //收藏
                if (MusicService.musicTitle == null || MusicService.musicTitle.length() == 0) {
                    return;
                }
                if (MusicService.musicIsCollect == 0) {
                    presenter.collectCourse(collectType, MusicService.musicAudioSource, MusicService.musicResourceId, classId);
                } else {
                    presenter.cancelCollection(collectType, MusicService.musicAudioSource, MusicService.musicResourceId, classId);
                }
                break;
            case R.id.music_lyric:
                //歌词
                if (MusicService.musicTitle == null || MusicService.musicTitle.length() == 0) {
                    return;
                }
                if (isLyric) {
                    isLyric = false;
                    image.setVisibility(View.VISIBLE);
                    lyricRecycler.setVisibility(View.GONE);
                } else {
                    isLyric = true;
                    presenter.getLyric(MusicService.musicList.get(MusicService.listTag).getBookId());
                    image.setVisibility(View.GONE);
                    lyricRecycler.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.music_record:
                //录音
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (MusicService.isPlay) {
                    MusicService.stop();
                }
                if (MusicService.musicTitle == null || MusicService.musicTitle.length() == 0) {
                    return;
                }
                Intent intent = new Intent(this, RecordingActivity.class);
                intent.putExtra("bookId", MusicService.musicResourceId);
                intent.putExtra("bookName", MusicService.musicTitle);
                intent.putExtra("bookImageUrl", MusicService.musicImageUrl);
                intent.putExtra("audioType", MusicService.musicAudioType);
                intent.putExtra("audioSource", MusicService.musicAudioSource);
                intent.putExtra("resourceUrl", MusicService.musicList.get(MusicService.listTag).getPath());
                intent.putExtra("myResourceUrl", myResourceUrl);
                intent.putExtra("homeworkid", homeworkid);
                startActivity(intent);
                break;
            case R.id.anwa_radio:
                Intent intent1 = new Intent(this, GrindEarActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.share_daka_pengyouquan:
                shareType = 0;
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechatMoments("我和我家宝宝" + SystemUtils.userInfo.getName() + "正在安娃电台听《" + MusicService.musicTitle + "》", "安娃电台喊你磨耳朵啦...", MusicService.musicImageUrl, url);
                }
                break;
            case R.id.share_daka_weixin:
                shareType = 1;
                if (url != null && url.length() != 0) {
                    shareUtils.shareWechat("我和我家宝宝" + SystemUtils.userInfo.getName() + "正在安娃电台听《" + MusicService.musicTitle + "》", "安娃电台喊你磨耳朵啦...", MusicService.musicImageUrl, url);
                }
                break;
            case R.id.share_daka_qq:
                shareType = 2;
                if (url != null && url.length() != 0) {
                    shareUtils.shareQQ("我和我家宝宝" + SystemUtils.userInfo.getName() + "正在安娃电台听《" + MusicService.musicTitle + "》", "安娃电台喊你磨耳朵啦...", MusicService.musicImageUrl, url);
                }
                break;
            case R.id.share_daka_qqzone:
                shareType = 3;
                if (url != null && url.length() != 0) {
                    shareUtils.shareQZone("我和我家宝宝" + SystemUtils.userInfo.getName() + "正在安娃电台听《" + MusicService.musicTitle + "》", "安娃电台喊你磨耳朵啦...", MusicService.musicImageUrl, url);
                }
                break;
            case R.id.daka_share_cancel2:
                popupWindow2.dismiss();
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_CLOCKINSHARE) {
            ShareBean shareBean = (ShareBean) message.obj;
            url = shareBean.getUrl();
        } else if (message.what == MethodCode.EVENT_GETLYRIC) {
            lyricList.clear();
            lyricList.addAll((List<String>) message.obj);
            lyricAdapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_COLLECTCOURSE + 2000 + classId) {
            showInfo((String) message.obj);
            collect.setImageResource(R.drawable.icon_player_collection_t);
            MusicService.setMusicCollect(1);
        } else if (message.what == MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId) {
            showInfo((String) message.obj);
            collect.setImageResource(R.drawable.icon_player_collection_f);
            MusicService.setMusicCollect(0);
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
        }
    }

    private void getWindowGray(boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().setAttributes(layoutParams);
        }
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
}
