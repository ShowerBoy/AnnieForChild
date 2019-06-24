package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.record.Record;
import com.annie.annieforchild.ui.activity.my.MyRecordActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.MyReleaseViewHolder;
import com.annie.annieforchild.ui.fragment.recording.MyReleaseFragment;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2019/1/8.
 */

public class MyReleaseAdapter extends RecyclerView.Adapter<MyReleaseViewHolder> implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private Context context;
    private List<Record> lists;
    private List<String> urlList;
    private LayoutInflater inflater;
    private MediaPlayer mediaPlayer;
    private boolean isPlay = false; //是否播放录音
    private OnRecyclerItemClickListener listener;
    private MyReleaseViewHolder holder;
    private int currentSize = 0, totalSize, currentPos = -2;
    private int tag;
    private Thread thread;

    public MyReleaseAdapter(Context context, List<Record> lists, int tag, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.tag = tag;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
        urlList = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public MyReleaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyReleaseViewHolder holder = null;
        holder = new MyReleaseViewHolder(inflater.inflate(R.layout.activity_my_record_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(v);
                return true;
            }
        });
        this.holder = holder;
        return holder;
    }

    @Override
    public void onBindViewHolder(MyReleaseViewHolder myReleaseViewHolder, int position) {
        if (tag == 1) {
            myReleaseViewHolder.myReleaseLayout.setVisibility(View.VISIBLE);
            myReleaseViewHolder.playTimes.setText(lists.get(position).getPlayCount() + "次播放");
            myReleaseViewHolder.likeTimes.setText(lists.get(position).getLikeCount() + "次赞");
        } else {
            myReleaseViewHolder.myReleaseLayout.setVisibility(View.GONE);
        }
        if (lists.get(position).getAudioType() == 0) {
            myReleaseViewHolder.target.setText("磨耳朵");
        } else if (lists.get(position).getAudioType() == 1) {
            myReleaseViewHolder.target.setText("流利读");
        } else {
            myReleaseViewHolder.target.setText("地道说");
        }
        myReleaseViewHolder.myRecordContent.setText(lists.get(position).getTitle() + "（" + lists.get(position).getDuration() + "秒）");
        myReleaseViewHolder.myRecordDate.setText(lists.get(position).getTime());
        Glide.with(context).load(lists.get(position).getImageUrl()).error(R.drawable.image_loading).into(myReleaseViewHolder.myRecordImage);
        MyReleaseViewHolder finalHolder1 = myReleaseViewHolder;
        myReleaseViewHolder.myRecordPlay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (isPlay) {
                    if (currentPos != position) {
                        return;
                    }
                }
//                if (isClick) {

                if (!isPlay) {

                    /**
                     * {@link MyRecordActivity#onMainEventThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = MethodCode.EVENT_MUSICSTOP;
                    message.obj = 0;
                    EventBus.getDefault().post(message);

                    holder = finalHolder1;
                    urlList.clear();
                    urlList.addAll(lists.get(position).getUrl());
                    totalSize = urlList.size();
                    currentSize = 0;
                    currentPos = position;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            playUrl2(urlList.get(currentSize));
                        }
                    });
                    thread.start();
                    MyRecordActivity.mVP.setNoFocus(true);
                    holder.myRecordPlay.setImageResource(R.drawable.icon_stop);
                    isPlay = true;
                } else {
                    try {
//                        if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        mediaPlayer.stop();
                        mediaPlayer.seekTo(0);
//                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    holder.myRecordPlay.setImageResource(R.drawable.icon_replay);
                    MyRecordActivity.mVP.setNoFocus(false);
                    isPlay = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private void playUrl2(String url) {
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
//            isClick = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
//            isClick = true;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        currentSize++;
        if (currentSize < totalSize) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
            playUrl2(urlList.get(currentSize));
//                }
//            }).start();
        } else {
//            isClick = true;
            isPlay = false;
            if (holder != null) {
                holder.myRecordPlay.setImageResource(R.drawable.icon_replay);
                MyRecordActivity.mVP.setNoFocus(false);
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (isPlay) {
            mediaPlayer.start();
        }
    }

    public void resetAdapter() {
        stopAudio();
        isPlay = false;
        currentPos = -2;
        MyRecordActivity.mVP.setNoFocus(false);
    }


    public void stopAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.stop();
                if (holder != null) {
                    holder.myRecordPlay.setImageResource(R.drawable.icon_replay);
                }
            }
        }
    }

    public void releaseAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.stop();
                if (holder != null) {
                    holder.myRecordPlay.setImageResource(R.drawable.icon_replay);
                }
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }
}
