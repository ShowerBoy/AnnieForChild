package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Record;
import com.annie.annieforchild.ui.adapter.viewHolder.MyRecordViewHolder;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NoHttpUtils;
import com.bumptech.glide.Glide;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.RequestQueue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * 我的录音适配器
 * Created by WangLei on 2018/3/8 0008
 */

public class MyRecordAdapter extends BaseAdapter implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private Context context;
    private List<Record> lists;
    private List<String> urlList;
    private LayoutInflater inflater;
    private boolean isClick = true;
    private AudioTrack player;
    private MediaPlayer mediaPlayer;
    private DownloadRequest downloadRequest;
    private DownloadQueue requestQueue;
    private boolean isPlay = false; //是否播放录音
    private DataInputStream dis = null;
    private boolean tag = true;
    private String fileName;
    private int currentSize = 0, totalSize;
    int bufferSizeInBytes;
    File file;

    public MyRecordAdapter(Context context, List<Record> lists) {
        this.context = context;
        this.lists = lists;
        urlList = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        inflater = LayoutInflater.from(context);
        requestQueue = NoHttp.newDownloadQueue();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyRecordViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_my_record_item, parent, false);
            holder = new MyRecordViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyRecordViewHolder) convertView.getTag();
        }
        if (lists.get(position).getOrigin() == 0) {
            holder.myRecordContent.setText(lists.get(position).getTitle() + "（" + lists.get(position).getDuration() + "秒）");
        } else {
            holder.myRecordContent.setText(lists.get(position).getTitle() + "（" + lists.get(position).getDuration() + "秒）");
        }
        holder.myRecordDate.setText(lists.get(position).getTime().substring(0, 4) + "-" + lists.get(position).getTime().substring(4, 6) + "-" + lists.get(position).getTime().substring(6, 8));
//        holder.myRecordTime.setText("（" + lists.get(position).getDuration() + "秒）");
        Glide.with(context).load(lists.get(position).getImageUrl()).error(R.drawable.image_recording_empty).into(holder.myRecordImage);
        holder.myRecordPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick) {
                    isClick = false;
                    if (lists.get(position).getOrigin() == 0) {
                        String url = lists.get(position).getUrl().get(0);
                        if (url.contains(".mp3")) {
                            if (!isPlay) {
                                urlList.clear();
                                urlList.addAll(lists.get(position).getUrl());
                                totalSize = urlList.size();
                                currentSize = 0;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        playUrl2(urlList.get(currentSize));
                                    }
                                }).start();
                                isPlay = true;
                            }
                        } else {
                            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + lists.get(position).getTitle() + ".pcm");
                            if (!file.exists()) {
                                fileName = lists.get(position).getTitle();
                                downloadRequest = NoHttp.createDownloadRequest(lists.get(position).getUrl().get(0), Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/", lists.get(position).getTitle() + ".pcm", true, true);
                                requestQueue.add(123, downloadRequest, downloadListener);
                                requestQueue.start();
                                return;
                            } else {
                                tag = true;
                                play(lists.get(position).getTitle(), file);
                            }
                        }

                    } else if (lists.get(position).getOrigin() == 3) {
                        if (!isPlay) {
                            //
                            urlList.clear();
                            urlList.addAll(lists.get(position).getUrl());
                            totalSize = urlList.size();
                            currentSize = 0;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    playUrl2(urlList.get(currentSize));
                                }
                            }).start();
                            isPlay = true;
                        }
                    } else {
                        isClick = true;
                    }
//                    else if (lists.get(position).getOrigin() == 1) {
//                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "challenge/" + lists.get(position).getTitle() + ".pcm");
//                        if (!file.exists()) {
//                            isClick = true;
//                            return;
//                        } else {
//                            tag = true;
//                            play(lists.get(position).getTitle(), file);
//                        }
//                    } else if (lists.get(position).getOrigin() == 2) {
//                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/" + lists.get(position).getTitle() + ".pcm");
//                        if (!file.exists()) {
//                            isClick = true;
//                            return;
//                        } else {
//                            tag = true;
//                            play(lists.get(position).getTitle(), file);
//                        }
//                    }
                }
            }
        });
        return convertView;
    }

    public void play(String fileName, File file) {
        try {
            //从音频文件中读取声音
//            SystemUtils.show(context, fileName);
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + fileName + ".pcm");
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/record/" + "ceshi.pcm");
//
//            if (!file.exists()) {
//                isClick = true;
//                return;
//            }
//            ContentResolver resolver = getContentResolver();
//            InputStream inputStream = resolver.openInputStream(Uri.parse("http://appapi.anniekids.net/release/Public/Uploads/moerduorecord/20180411/5ace168250c2b.pcm"));
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
//            dis = new DataInputStream(new BufferedInputStream(inputStream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //最小缓存区
        bufferSizeInBytes = AudioTrack.getMinBufferSize(16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        //创建AudioTrack对象   依次传入 :流类型、采样率（与采集的要一致）、音频通道（采集是IN 播放时OUT）、量化位数、最小缓冲区、模式
        player = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes, AudioTrack.MODE_STREAM);

        byte[] data = new byte[bufferSizeInBytes];
        player.play();//开始播放
//        DataInputStream finalDis = dis;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (tag) {
                    int i = 0;
                    try {
                        while (dis.available() > 0 && i < data.length) {
                            data[i] = dis.readByte();//录音时write Byte 那么读取时就该为readByte要相互对应
                            i++;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (tag) {
                        player.write(data, 0, data.length);
                    }

                    if (i != bufferSizeInBytes) //表示读取完了
                    {
                        tag = false;
                        player.stop();//停止播放
                        player.release();//释放资源
                        isClick = true;
                        break;
                    }
                }
            }
        }).start();
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
            isClick = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            isClick = true;
        }
    }


    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onDownloadError(int i, Exception e) {
            isClick = true;
        }

        @Override
        public void onStart(int i, boolean b, long l, Headers headers, long l1) {

        }

        @Override
        public void onProgress(int i, int i1, long l, long l1) {

        }

        @Override
        public void onFinish(int i, String s) {
            isClick = true;
            tag = true;
            play(fileName, file);
        }

        @Override
        public void onCancel(int i) {
            isClick = true;
        }
    };

    public void stopAudio() {
        if (player != null) {
            tag = false;
            isClick = true;
            try {
                player.stop();
            } catch (IllegalStateException e) {
                tag = false;
                player = null;
                player = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes, AudioTrack.MODE_STREAM);
                player.stop();
            }
            player.release();
        }
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        isPlay = false;
    }

    public void destoryAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        currentSize++;
        if (currentSize < totalSize) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    playUrl2(urlList.get(currentSize));
                }
            }).start();
        } else {
            isClick = true;
            isPlay = false;
        }
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }
}
