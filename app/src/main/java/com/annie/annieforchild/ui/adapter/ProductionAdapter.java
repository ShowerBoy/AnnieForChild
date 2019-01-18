package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.rank.Production;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.adapter.viewHolder.ProductionViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2019/1/4.
 */

public class ProductionAdapter extends RecyclerView.Adapter<ProductionViewHolder> implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private Context context;
    private List<Production> lists;
    private LayoutInflater inflater;
    private GrindEarPresenter presenter;
    private MediaPlayer mediaPlayer;
    private int totalSize, currentSize;
    private List<String> urlList;
    private Thread thread;
    private boolean threadOn_Off = true;
    private boolean isPlay = false;
    private int currentPlayPosition;
    private ProductionViewHolder holder;
    private OnRecyclerItemClickListener listener;
    private int position;

    public ProductionAdapter(Context context, List<Production> lists, GrindEarPresenter presenter, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
        urlList = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public ProductionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ProductionViewHolder holder = null;
        holder = new ProductionViewHolder(inflater.inflate(R.layout.activity_production_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductionViewHolder productionViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(productionViewHolder.image);
        productionViewHolder.name.setText(lists.get(i).getBookName());
        productionViewHolder.date.setText(lists.get(i).getDate());
        productionViewHolder.playTimes.setText(lists.get(i).getPlayCount());
        productionViewHolder.likeTimes.setText(lists.get(i).getLikeCount());
        if (lists.get(i).getIsLike() == 0) {
            productionViewHolder.like.setImageResource(R.drawable.icon_like_f);
        } else {
            productionViewHolder.like.setImageResource(R.drawable.icon_like_t);
        }
        productionViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay) {
                    return;
                }
                if (lists.get(i).getIsLike() == 0) {
                    position = i;
                    presenter.addlikes(lists.get(i).getId());
                } else {
                    position = i;
                    presenter.cancellikes(lists.get(i).getId());
                }
            }
        });
        productionViewHolder.play.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (isPlay) {
                    if (currentPlayPosition == i) {
                        try {
                            mediaPlayer.pause();
                            mediaPlayer.stop();
                            mediaPlayer.seekTo(0);
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
//                        threadOn_Off = false;
                        productionViewHolder.play.setImageResource(R.drawable.icon_practice_play);
                        isPlay = false;
                    }
                } else {
                    if (MusicService.isPlay) {
                        MusicService.stop();
                    }
                    holder = productionViewHolder;
                    urlList.clear();
                    urlList.addAll(lists.get(i).getUrl());
                    totalSize = urlList.size();

                    currentPlayPosition = i;
                    currentSize = 0;
//                    threadOn_Off = true;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            playUrl(urlList.get(currentSize));
                        }
                    });
                    thread.start();
                    productionViewHolder.play.setImageResource(R.drawable.icon_practice_stop);
                    isPlay = true;
                    presenter.playTimes(lists.get(i).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
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
            holder.play.setImageResource(R.drawable.icon_practice_play);
        }
    };

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
    }
}
