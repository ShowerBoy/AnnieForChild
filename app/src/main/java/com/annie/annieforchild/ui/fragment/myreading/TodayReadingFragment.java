package com.annie.annieforchild.ui.fragment.myreading;

import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.GrindTime;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 今日阅读时长
 * Created by wanglei on 2018/6/1.
 */

public class TodayReadingFragment extends BaseFragment {
    private TextView ergebuiben, xugougushi, feixugou, zhangjietushu, diandubi, qita, total;
    private List<GrindTime> lists;
    private MyGrindEarBean bean;

    {
        setRegister(true);
    }

    public static TodayReadingFragment instance() {
        TodayReadingFragment fragment = new TodayReadingFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
    }

    @Override
    protected void initView(View view) {
        ergebuiben = view.findViewById(R.id.today_ergehuiben_duration);
        xugougushi = view.findViewById(R.id.today_xugougushi_duration);
        feixugou = view.findViewById(R.id.today_feixugou_duration);
        zhangjietushu = view.findViewById(R.id.today_zhangjietushu_duration);
        diandubi = view.findViewById(R.id.today_read_diandubi_duration);
        qita = view.findViewById(R.id.today_read_qita_duration);
        total = view.findViewById(R.id.today_read_total_duration);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_today_reading_fragment;
    }

    /**
     * {@link com.annie.annieforchild.presenter.imp.GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYREADING) {
            bean = (MyGrindEarBean) message.obj;
            lists.clear();
            lists.addAll(bean.getTodayList());
            refresh();
        }
    }

    private void refresh() {
        for (int i = 0; i < lists.size(); i++) {
            GrindTime grindTime = lists.get(i);
            int min = (int) Double.parseDouble(grindTime.getDuration()) / 60;
            int hour = 0;
            if (min > 60) {
                hour = min / 60;
                min = min % 60;
            }
            switch (grindTime.getType()) {
                case "pictureBook":
                    if (hour == 0) {
                        ergebuiben.setText(min + "分");
                    } else {
                        ergebuiben.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "fictionStory":
                    if (hour == 0) {
                        xugougushi.setText(min + "分");
                    } else {
                        xugougushi.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "nonfictionStory":
                    if (hour == 0) {
                        feixugou.setText(min + "分");
                    } else {
                        feixugou.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "chapterBook":
                    if (hour == 0) {
                        zhangjietushu.setText(min + "分");
                    } else {
                        zhangjietushu.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "readingpen":
                    if (hour == 0) {
                        diandubi.setText(min + "分");
                    } else {
                        diandubi.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "others":
                    if (hour == 0) {
                        qita.setText(min + "分");
                    } else {
                        qita.setText(hour + "小时" + min + "分");
                    }
                    break;
            }
        }
        int t_min = (int) Double.parseDouble(bean.getTodayTotalDuration()) / 60;
        int t_hour = 0;
        if (t_min > 60) {
            t_hour = t_min / 60;
            t_min = t_min % 60;
        }
        if (t_hour == 0) {
            total.setText(t_min + "分");
        } else {
            total.setText(t_hour + "小时" + t_min + "分");
        }
    }
}
