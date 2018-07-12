package com.annie.annieforchild.ui.activity.reading;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ReadingData;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.ListenSongActivity;
import com.annie.annieforchild.ui.activity.grindEar.MeiriyiActivity;
import com.annie.annieforchild.ui.activity.grindEar.MyGrindEarActivity;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.annieforchild.view.ReadingView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.daimajia.slider.library.SliderLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 阅读
 * Created by WangLei on 2018/1/19 0019
 */

public class ReadingActivity extends BaseActivity implements View.OnClickListener, GrindEarView {
    private ImageView readingBack, myLevel, letsReading, meiriyidu;
    private SliderLayout readingSlide;
    private LinearLayout huibenLayout, xugouLayout, feixugouLayout, zhangjieLayout;
    private HashMap<Integer, String> file_maps;//轮播图图片map
    private GrindEarPresenter presenter;
    private List<SongClassify> huibenClassifyList;
    private List<SongClassify> xugouClassifyList;
    private List<SongClassify> feixugouClassifyList;
    private List<SongClassify> zhangjieClassifyList;
    private List<SongClassify> readClassifyList;
    private List<Song> meiriyiduList;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reading;
    }

    @Override
    protected void initView() {
        readingBack = findViewById(R.id.reading_back);
        huibenLayout = findViewById(R.id.huiben_layout);
        xugouLayout = findViewById(R.id.xugou_layout);
        feixugouLayout = findViewById(R.id.feixugou_layout);
        zhangjieLayout = findViewById(R.id.zhangjie_layout);
        readingSlide = findViewById(R.id.reading_slide);
        myLevel = findViewById(R.id.my_level);
        letsReading = findViewById(R.id.lets_reading);
        meiriyidu = findViewById(R.id.meiriyidu);
        readingBack.setOnClickListener(this);
        huibenLayout.setOnClickListener(this);
        xugouLayout.setOnClickListener(this);
        feixugouLayout.setOnClickListener(this);
        zhangjieLayout.setOnClickListener(this);
        myLevel.setOnClickListener(this);
        letsReading.setOnClickListener(this);
        meiriyidu.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        meiriyiduList = new ArrayList<>();
        huibenClassifyList = new ArrayList<>();
        xugouClassifyList = new ArrayList<>();
        feixugouClassifyList = new ArrayList<>();
        zhangjieClassifyList = new ArrayList<>();
        readClassifyList = new ArrayList<>();
        file_maps = new HashMap<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getReading();
        presenter.getReadingClasses();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.reading_back:
                finish();
                break;
            case R.id.huiben_layout:
                //绘本
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 6);
                bundle1.putSerializable("ClassifyList", (Serializable) huibenClassifyList);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.xugou_layout:
                //分级读物
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", 7);
                bundle2.putSerializable("ClassifyList", (Serializable) xugouClassifyList);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;
            case R.id.feixugou_layout:
                //桥梁书
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type", 8);
                bundle3.putSerializable("ClassifyList", (Serializable) feixugouClassifyList);
                intent.putExtras(bundle3);
                startActivity(intent);
                break;
            case R.id.zhangjie_layout:
                //章节书
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("type", 9);
                bundle4.putSerializable("ClassifyList", (Serializable) zhangjieClassifyList);
                intent.putExtras(bundle4);
                startActivity(intent);
                break;
            case R.id.my_level:
                //阅读存折
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(this, MyReadingActivity.class);
                startActivity(intent);
                break;
            case R.id.lets_reading:
                //我要朗读
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle5 = new Bundle();
                bundle5.putInt("type", 10);
                bundle5.putSerializable("ClassifyList", (Serializable) readClassifyList);
                intent.putExtras(bundle5);
                startActivity(intent);
                break;
            case R.id.meiriyidu:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(this, MeiriyiActivity.class);
                Bundle bundle6 = new Bundle();
                bundle6.putString("title", "每日一读");
                bundle6.putSerializable("list", (Serializable) meiriyiduList);
                intent.putExtras(bundle6);
                startActivity(intent);
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMUSICCLASSES6) {
            huibenClassifyList.clear();
            huibenClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES7) {
            xugouClassifyList.clear();
            xugouClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES8) {
            feixugouClassifyList.clear();
            feixugouClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES9) {
            zhangjieClassifyList.clear();
            zhangjieClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES10) {
            readClassifyList.clear();
            readClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETREADING) {
            ReadingData readingData = (ReadingData) message.obj;
            meiriyiduList.clear();
            meiriyiduList.addAll(readingData.getMeiriyidu());
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public SliderLayout getImageSlide() {
        return readingSlide;
    }

    @Override
    public HashMap<Integer, String> getFile_maps() {
        return file_maps;
    }

    @Override
    public void setPictureUrl(String listening, String animation) {

    }
}
