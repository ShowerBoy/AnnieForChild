package com.example.lamemp3;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.widget.Filterable;

import com.annie.annieforchild.Utils.PCMRecordUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.pcm2mp3.StorageUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MP3Recorder {

    private String mDir = null;
    private String mFilePath = null;
    private int sampleRate = 0;
    private boolean isRecording = false;
    private boolean isPause = false;
    private Handler handler = null;
    private int mVolume = 1;
    private boolean isClick = true;

    private File pcmFile;
    private byte[] pcmByte;
    AudioTrack player;
    DataInputStream dis = null;
    private boolean isPlay = true;
    int minBufferSize;
    //    private String mp3FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LAME/mp3/" + "ceshiMp3.mp3";
    private String pcmFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LAME/mp3/" + "ceshi.pcm";

    private Thread thread = null;
    private Runnable runnable;
    private PCMRecordUtils pcmRecordUtils;
//    Handler handler = new Handler();

    /**
     * 开始录音
     */
    public static final int MSG_REC_STARTED = 1;

    /**
     * 结束录音
     */
    public static final int MSG_REC_STOPPED = 2;

    /**
     * 暂停录音
     */
    public static final int MSG_REC_PAUSE = 3;

    /**
     * 继续录音
     */
    public static final int MSG_REC_RESTORE = 4;

    /**
     * 缓冲区挂了,采样率手机不支持
     */
    public static final int MSG_ERROR_GET_MIN_BUFFERSIZE = -1;

    /**
     * 创建文件时扑街了
     */
    public static final int MSG_ERROR_CREATE_FILE = -2;

    /**
     * 初始化录音器时扑街了
     */
    public static final int MSG_ERROR_REC_START = -3;

    /**
     * 录音的时候出错
     */
    public static final int MSG_ERROR_AUDIO_RECORD = -4;

    /**
     * 编码时挂了
     */
    public static final int MSG_ERROR_AUDIO_ENCODE = -5;

    /**
     * 写文件时挂了
     */
    public static final int MSG_ERROR_WRITE_FILE = -6;

    /**
     * 没法关闭文件流
     */
    public static final int MSG_ERROR_CLOSE_FILE = -7;

    public MP3Recorder(String dir) {
        this.sampleRate = 16000;
        this.mDir = dir;

        pcmRecordUtils = new PCMRecordUtils(pcmFilePath);
//        pcmFile = new File(pcmFilePath);
//        thread = new Thread();
    }

    /**
     * 开片
     */
    public void start(final String name) {
        if (isRecording) {
            return;
        }


        thread = new Thread() {
            @Override
            public void run() {
//                String fileDir = mDir;
//
//                File dir = new File(fileDir);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }


                mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + name + ".mp3";
                File file = new File(mFilePath);
                if (file.exists()) {
                    file.delete();
                }
//                mFilePath = mp3FilePath;

                Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
                // 根据定义好的几个配置，来获取合适的缓冲大小
                minBufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
                if (minBufferSize < 0) {
                    if (handler != null) {
                        handler.sendEmptyMessage(MSG_ERROR_GET_MIN_BUFFERSIZE);
                    }
                    return;
                }
                AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 2);

                // 5秒的缓冲
                final short[] buffer = new short[sampleRate * (16 / 8) * 1 * 5];
//                short[] buffer = new short[sampleRate];
                byte[] mp3buffer = new byte[(int) (7200 + buffer.length * 2 * 1.25)];
//                byte[] mp3buffer = new byte[sampleRate];
//                byte[] pcmbuffer = new byte[sampleRate];


                //pcm的缓冲
//                pcmByte = new byte[minBufferSize];

//                FileOutputStream pcmOutput = null;
                FileOutputStream output = null;

                try {
                    output = new FileOutputStream(file);
//                    pcmOutput = new FileOutputStream(pcmFile);
//                    dataOut = new DataOutputStream(new BufferedOutputStream(pcmOutput));
                } catch (FileNotFoundException e) {
                    if (handler != null) {
                        handler.sendEmptyMessage(MSG_ERROR_CREATE_FILE);
                    }
                    return;
                }
                MP3Recorder.init(sampleRate, 1, sampleRate, 32);
                isRecording = true; // 录音状态
                isPause = false; // 录音状态
                try {
                    try {
                        audioRecord.startRecording(); // 开启录音获取音频数据

                    } catch (IllegalStateException e) {
                        // 不给录音...
                        if (handler != null) {
                            handler.sendEmptyMessage(MSG_ERROR_REC_START);
                        }
                        return;
                    }

                    try {
                        // 开始录音
                        if (handler != null) {
                            handler.sendEmptyMessage(MSG_REC_STARTED);
                        }

                        int readSize = 0;

                        boolean pause = false;
                        while (isRecording) {
                            // 暂停
                            if (isPause) {
                                if (!pause) {
                                    handler.sendEmptyMessage(MSG_REC_PAUSE);
                                    pause = true;
                                }
                                continue;
                            }
                            if (pause) {
                                handler.sendEmptyMessage(MSG_REC_RESTORE);
                                pause = false;
                            }

                            // 实时录音写数据
                            readSize = audioRecord.read(buffer, 0, minBufferSize);


                            // 计算分贝值
                            long v = 0;
                            // 将 buffer 内容取出，进行平方和运算
                            for (int i = 0; i < buffer.length; i++) {
                                v += buffer[i] * buffer[i];
                            }
                            // 平方和除以数据总长度，得到音量大小。
                            double mean = v / (double) readSize;
                            double volume = 10 * Math.log10(mean);
                            volume = volume * readSize / 32768 - 1;
                            if (volume > 0) {
                                mVolume = (int) Math.abs(volume);
                            } else {
                                mVolume = 1;
                            }

                            if (readSize < 0) {
                                if (handler != null) {
                                    handler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
                                }
                                break;
                            } else if (readSize == 0) {
                                ;
                            } else {
                                //写入mp3
                                int encResult = MP3Recorder.encode(buffer, buffer, readSize, mp3buffer);
                                if (encResult < 0) {
                                    if (handler != null) {
                                        handler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
                                    }
                                    break;
                                }
                                if (encResult != 0) {
                                    try {
                                        output.write(mp3buffer, 0, encResult);
                                    } catch (IOException e) {
                                        if (handler != null) {
                                            handler.sendEmptyMessage(MSG_ERROR_WRITE_FILE);
                                        }
                                        break;
                                    }
                                }
                                calc1(buffer, 0, minBufferSize);
                            }
                        }
                        // 录音完
                        int flushResult = MP3Recorder.flush(mp3buffer);
                        if (flushResult < 0) {
                            if (handler != null) {
                                handler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
                            }
                        } else if (flushResult != 0) {

                            try {
                                output.write(mp3buffer, 0, flushResult);
                            } catch (IOException e) {
                                if (handler != null) {
                                    handler.sendEmptyMessage(MSG_ERROR_WRITE_FILE);
                                }
                            }
                        }
                        try {
                            output.close();
//                            pcmOutput.close();
//                            dataOut.close();

                        } catch (IOException e) {
                            if (handler != null) {
                                handler.sendEmptyMessage(MSG_ERROR_CLOSE_FILE);
                            }
                        }
                    } finally {
                        audioRecord.stop();
                        audioRecord.release();
                    }
                } finally {
                    MP3Recorder.close();
                    isRecording = false;
                }
                if (handler != null) {
                    handler.sendEmptyMessage(MSG_REC_STOPPED);
                }
            }
        };
        thread.start();

//        pcmRecordUtils.startRecord();
    }

    void calc1(short[] lin, int off, int len) {
        int i, j;
        for (i = 0; i < len; i++) {
            j = lin[i + off];
            lin[i + off] = (short) (j >> 2);
        }
    }

    public void playPCM() {
        isClick = true;
        try {
            //从音频文件中读取声音
//            SystemUtils.show(context, fileName);
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/record/ceshi.pcm");
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/LAME/mp3/" + "ceshi.pcm");

            if (!file.exists()) {
                isClick = true;
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
        final int bufferSizeInBytes = AudioTrack.getMinBufferSize(16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        //创建AudioTrack对象   依次传入 :流类型、采样率（与采集的要一致）、音频通道（采集是IN 播放时OUT）、量化位数、最小缓冲区、模式
        player = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes, AudioTrack.MODE_STREAM);
        final byte[] data = new byte[bufferSizeInBytes];
        player.play();//开始播放
//        DataInputStream finalDis = dis;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isClick) {
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
                        player.stop();//停止播放
                        player.release();//释放资源
                        isClick = false;
                        break;
                    }
                }
            }
        }).start();
    }


    public void stop() {
        pcmRecordUtils.stopRecord();
        isRecording = false;
    }

    public void pause() {
        isPause = true;
    }

    public void restore() {
        isPause = false;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public boolean isPaus() {
        if (!isRecording) {
            return false;
        }
        return isPause;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setIsPlay(boolean isPlay) {
        this.isPlay = isPlay;
    }

    public byte[] Shorts2Bytes(short[] src) {
        int count = src.length;
        byte[] dest = new byte[count << 1];
        for (int i = 0; i < count; i++) {
            dest[i * 2] = (byte) (src[i] >> 8);
            dest[i * 2 + 1] = (byte) (src[i] >> 0);
        }
        return dest;
    }

    public short[] toShortArray(byte[] src) {

        int count = src.length >> 1;
        short[] dest = new short[count];
        for (int i = 0; i < count; i++) {
            dest[i] = (short) (src[i * 2] << 8 | src[2 * i + 1] & 0xff);
        }
        return dest;
    }

    /**
     * 获取分贝
     *
     * @return
     */
    public int getVolume() {
        return mVolume;
    }

    /**
     * 录音状态管理
     *
     * @see RecMicToMp3#MSG_REC_STARTED
     * @see RecMicToMp3#MSG_REC_STOPPED
     * @see RecMicToMp3#MSG_REC_PAUSE
     * @see RecMicToMp3#MSG_REC_RESTORE
     * @see RecMicToMp3#MSG_ERROR_GET_MIN_BUFFERSIZE
     * @see RecMicToMp3#MSG_ERROR_CREATE_FILE
     * @see RecMicToMp3#MSG_ERROR_REC_START
     * @see RecMicToMp3#MSG_ERROR_AUDIO_RECORD
     * @see RecMicToMp3#MSG_ERROR_AUDIO_ENCODE
     * @see RecMicToMp3#MSG_ERROR_WRITE_FILE
     * @see RecMicToMp3#MSG_ERROR_CLOSE_FILE
     */
    public void setHandle(Handler handler) {
        this.handler = handler;
    }

    // 以下为Native部分
    static {
        System.loadLibrary("mp3lame");
    }

    /**
     * 初始化录制参数
     */
    public static void init(int inSamplerate, int outChannel,
                            int outSamplerate, int outBitrate) {
        init(inSamplerate, outChannel, outSamplerate, outBitrate, 7);
    }

    /**
     * 初始化录制参数 quality:0=很好很慢 9=很差很快
     */
    public native static void init(int inSamplerate, int outChannel,
                                   int outSamplerate, int outBitrate, int quality);

    /**
     * 音频数据编码(PCM左进,PCM右进,MP3输出)
     */
    public native static int encode(short[] buffer_l, short[] buffer_r,
                                    int samples, byte[] mp3buf);

    /**
     * 刷干净缓冲区
     */
    public native static int flush(byte[] mp3buf);

    /**
     * 结束编码
     */
    public native static void close();
}
