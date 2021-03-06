package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.service.MusicService2;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Release;
import com.annie.annieforchild.bean.book.ReleaseBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.mains.HomePageActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.BookEndViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.ExerciseViewHolder;
import com.annie.annieforchild.ui.fragment.book.BookPlayEndFragment;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/11/30.
 */

public class BookEndAdapter extends RecyclerView.Adapter<BookEndViewHolder> implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private Context context;
    private BookPlayEndFragment fragment;
    private List<ReleaseBean> lists;
    private LayoutInflater inflater;
    private MediaPlayer mediaPlayer;
    private Thread thread;
    private boolean threadOn_Off = true;
    private int totalSize, currentSize;
    private List<String> urlList;
    private boolean isPlay = false;
    private int currentPlayPosition;
    private boolean tag; //有赞 没有赞
    private BookEndViewHolder holder;
    private GrindEarPresenter presenter;
    private MusicService2 musicService;
    private int position;

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public BookEndAdapter(Context context, List<ReleaseBean> lists, BookPlayEndFragment fragment, GrindEarPresenter presenter, MusicService2 musicService, boolean tag) {
        this.context = context;
        this.lists = lists;
        this.fragment = fragment;
        this.presenter = presenter;
        this.musicService = musicService;
        this.tag = tag;
        inflater = LayoutInflater.from(context);
        urlList = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public BookEndViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        BookEndViewHolder holder = null;
        holder = new BookEndViewHolder(inflater.inflate(R.layout.activity_book_end_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BookEndViewHolder bookEndViewHolder, int i) {
//        holder = bookEndViewHolder;
        Glide.with(context).load(lists.get(i).getRecordImageUrl()).error(R.drawable.icon_system_headpic).into(bookEndViewHolder.headpic);
        bookEndViewHolder.name.setText(lists.get(i).getRecordName());
        bookEndViewHolder.age.setText(lists.get(i).getRecordAge() + "岁");
        bookEndViewHolder.date.setText(lists.get(i).getRecordDate());
        bookEndViewHolder.playTimes.setText(lists.get(i).getRecordPlayTimes());
        bookEndViewHolder.playLinear.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckDoubleClick(View view) {
                if (fragment != null) {
                    if (!fragment.isPlay()) {
                        if (isPlay) {
                            if (currentPlayPosition == i) {
                                try {
                                    mediaPlayer.pause();
                                    mediaPlayer.stop();
                                    mediaPlayer.seekTo(0);
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                }
                                threadOn_Off = false;
                                bookEndViewHolder.play.setImageResource(R.drawable.icon_practice_play);
                                isPlay = false;
                            }
                        } else {
                            holder = bookEndViewHolder;
                            urlList.clear();
                            urlList.addAll(lists.get(i).getRecordUrl());
                            totalSize = urlList.size();

                            currentPlayPosition = i;
                            currentSize = 0;
                            threadOn_Off = true;
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    playUrl(urlList.get(currentSize));
                                }
                            });
                            thread.start();
                            bookEndViewHolder.play.setImageResource(R.drawable.icon_practice_stop);
                            isPlay = true;
                            presenter.playTimes(lists.get(i).getId());
                        }
                    }
                } else {
                    //书籍页
                    if (isPlay) {
                        if (currentPlayPosition == i) {
                            try {
                                mediaPlayer.pause();
                                mediaPlayer.stop();
                                mediaPlayer.seekTo(0);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                            threadOn_Off = false;
                            bookEndViewHolder.play.setImageResource(R.drawable.icon_practice_play);
                            isPlay = false;
                        }
                    } else {
                        if (musicService != null) {
                            if (musicService.isPlaying()) {
                                musicService.stop();
                            }
                        }
                        holder = bookEndViewHolder;
                        urlList.clear();
                        urlList.addAll(lists.get(i).getRecordUrl());
                        totalSize = urlList.size();

                        currentPlayPosition = i;
                        currentSize = 0;
                        threadOn_Off = true;
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                playUrl(urlList.get(currentSize));
                            }
                        });
                        thread.start();
                        bookEndViewHolder.play.setImageResource(R.drawable.icon_practice_stop);
                        isPlay = true;
                        presenter.playTimes(lists.get(i).getId());
                    }
                }
            }
        }));
        if (tag) {
            bookEndViewHolder.likeLinear.setVisibility(View.VISIBLE);
            bookEndViewHolder.likeTimes.setText(lists.get(i).getRecordLikes());
            if (lists.get(i).getIslike() == 0) {
                bookEndViewHolder.like.setImageResource(R.drawable.icon_like_f);
                bookEndViewHolder.likeTimes.setTextColor(context.getResources().getColor(R.color.text_color));
            } else {
                bookEndViewHolder.like.setImageResource(R.drawable.icon_like_t);
                bookEndViewHolder.likeTimes.setTextColor(context.getResources().getColor(R.color.text_orange));
            }
            bookEndViewHolder.likeLinear.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
                @Override
                public void onCheckDoubleClick(View view) {
                    if (isPlay) {
                        if (mediaPlayer != null) {
                            try {
                                mediaPlayer.pause();
                                mediaPlayer.stop();
                                mediaPlayer.seekTo(0);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }
                        threadOn_Off = false;
                        holder.play.setImageResource(R.drawable.icon_practice_play);
                        isPlay = false;
                    }
                    if (lists.get(i).getIslike() == 0) {
                        position = i;
                        presenter.addlikes(lists.get(i).getId());
                    } else {
                        position = i;
                        presenter.cancellikes(lists.get(i).getId());
                    }
                }
            }));
            bookEndViewHolder.headpic.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
                @Override
                public void onCheckDoubleClick(View view) {
                    if (isPlay) {
                        if (mediaPlayer != null) {
                            try {
                                mediaPlayer.pause();
                                mediaPlayer.stop();
                                mediaPlayer.seekTo(0);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }
                        threadOn_Off = false;
                        holder.play.setImageResource(R.drawable.icon_practice_play);
                        isPlay = false;
                    }
                    Intent intent = new Intent(context, HomePageActivity.class);
                    intent.putExtra("username", lists.get(i).getUsername());
                    context.startActivity(intent);
                }
            }));
        } else {
            bookEndViewHolder.likeLinear.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private void playUrl(String url) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (threadOn_Off) {
            currentSize++;
            if (currentSize < totalSize) {
                playUrl(urlList.get(currentSize));
            } else {
                isPlay = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler2.sendEmptyMessage(0);
                    }
                }).start();
            }
        }
    }

    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (holder.play != null) {
                holder.play.setImageResource(R.drawable.icon_practice_play);
            }
        }
    };

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    public void stopMedia() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (holder != null) {
            holder.play.setImageResource(R.drawable.icon_practice_play);
            threadOn_Off = false;
            isPlay = false;
        }

    }

}
