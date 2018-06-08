package com.annie.annieforchild.ui.fragment.mygrindear;

import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.GrindTime;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 累计磨耳朵
 * Created by wanglei on 2018/4/11.
 */

public class TotalGrindEarFragment extends BaseFragment {
    private TextView tingerge, tingshige, tingduihua, tingduwu, tingdonghua, tingmobao, jiqiren, diandubi, qita, total;
    private List<GrindTime> lists;
    MyGrindEarBean bean;

    {
        setRegister(true);
    }

    public static TotalGrindEarFragment instance() {
        TotalGrindEarFragment fragment = new TotalGrindEarFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
    }

    @Override
    protected void initView(View view) {
        tingerge = view.findViewById(R.id.total_tingerge_duration);
        tingshige = view.findViewById(R.id.total_tingshige_duration);
        tingduihua = view.findViewById(R.id.total_tingduihua_duration);
        tingduwu = view.findViewById(R.id.total_tingduwu_duration);
        tingdonghua = view.findViewById(R.id.total_tingdonghua_duration);
        tingmobao = view.findViewById(R.id.total_tingmobao_duration);
        jiqiren = view.findViewById(R.id.total_jiqiren_duration);
        diandubi = view.findViewById(R.id.total_diandubi_duration);
        qita = view.findViewById(R.id.total_qita_duration);
        total = view.findViewById(R.id.total_total_duration);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_total_grind_ear_fragment;
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYLISTENING) {
            bean = (MyGrindEarBean) message.obj;
            lists.clear();
            lists.addAll(bean.getHistoryList());
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
                case "song":
                    if (hour == 0) {
                        tingerge.setText(min + "分");
                    } else {
                        tingerge.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "poetry":
                    if (hour == 0) {
                        tingshige.setText(min + "分");
                    } else {
                        tingshige.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "communicate":
                    if (hour == 0) {
                        tingduihua.setText(min + "分");
                    } else {
                        tingduihua.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "reading":
                    if (hour == 0) {
                        tingduwu.setText(min + "分");
                    } else {
                        tingduwu.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "animation":
                    if (hour == 0) {
                        tingdonghua.setText(min + "分");
                    } else {
                        tingdonghua.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "mobao":
                    if (hour == 0) {
                        tingmobao.setText(min + "分");
                    } else {
                        tingmobao.setText(hour + "小时" + min + "分");
                    }
                    break;
                case "robot":
                    if (hour == 0) {
                        jiqiren.setText(min + "分");
                    } else {
                        jiqiren.setText(hour + "小时" + min + "分");
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
        int t_min = (int) Double.parseDouble(bean.getHistoryTotalDuration()) / 60;
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
