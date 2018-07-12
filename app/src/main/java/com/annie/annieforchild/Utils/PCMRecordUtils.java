package com.annie.annieforchild.Utils;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wanglei on 2018/4/27.
 */

public class PCMRecordUtils {
    private String filePath;
    private int sampleRate = 16000;
    private AudioRecord record;
    private int bufferSizeInBytes;
    boolean isRecording = false;
    AudioTrack player;
    DataInputStream dis = null;
    private boolean isPlay = true;
    int readSize;
    FileOutputStream output;
    File file;
    byte[] bytes;

    public PCMRecordUtils(String filePath) {
        this.filePath = filePath;
        init();
    }

    private void init() {
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        record = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes);
        bytes = new byte[bufferSizeInBytes];
        file = new File(filePath);
        bytes = new byte[bufferSizeInBytes];
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startRecord() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                record.startRecording();
                isRecording = true;
                try {
                    output = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    while (isRecording) {
                        readSize = record.read(bytes, 0, bufferSizeInBytes);
                        output.write(bytes, 0, readSize);
                    }
                    record.stop();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopRecord() {
        isRecording = false;
    }

    public void playRecord() {
        try {
            //从音频文件中读取声音
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/record/ceshi.pcm");
//
            if (!file.exists()) {
                return;
            }
//            ContentResolver resolver = getContentResolver();
//            InputStream inputStream = resolver.openInputStream(Uri.parse("http://appapi.anniekids.net/release/Public/Uploads/moerduorecord/20180411/5ace168250c2b.pcm"));
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
//            dis = new DataInputStream(new BufferedInputStream(inputStream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        //最小缓存区
//        final int bufferSizeInBytes = AudioTrack.getMinBufferSize(16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        //创建AudioTrack对象   依次传入 :流类型、采样率（与采集的要一致）、音频通道（采集是IN 播放时OUT）、量化位数、最小缓冲区、模式
        player = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes, AudioTrack.MODE_STREAM);

        final byte[] data = new byte[bufferSizeInBytes];
        player.play();//开始播放
//        DataInputStream finalDis = dis;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isPlay) {
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
                    player.write(data, 0, data.length);

                    if (i != bufferSizeInBytes) //表示读取完了
                    {
                        isPlay = false;
                        player.stop();//停止播放
                        player.release();//释放资源
                        break;
                    }
                }
            }
        }).start();
    }

    public void setIsPlay(boolean isPlay) {
        this.isPlay = isPlay;
    }
}
