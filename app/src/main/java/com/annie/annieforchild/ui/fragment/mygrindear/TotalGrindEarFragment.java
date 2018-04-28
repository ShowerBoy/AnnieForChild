package com.annie.annieforchild.ui.fragment.mygrindear;

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
            switch (grindTime.getType()) {
                case "song":
                    tingerge.setText(grindTime.getDuration() + "分");
                    break;
                case "poetry":
                    tingshige.setText(grindTime.getDuration() + "分");
                    break;
                case "communicate":
                    tingduihua.setText(grindTime.getDuration() + "分");
                    break;
                case "reading":
                    tingduwu.setText(grindTime.getDuration() + "分");
                    break;
                case "animation":
                    tingdonghua.setText(grindTime.getDuration() + "分");
                    break;
                case "mobao":
                    tingmobao.setText(grindTime.getDuration() + "分");
                    break;
                case "robot":
                    jiqiren.setText(grindTime.getDuration() + "分");
                    break;
                case "readingpen":
                    diandubi.setText(grindTime.getDuration() + "分");
                    break;
                case "others":
                    qita.setText(grindTime.getDuration() + "分");
                    break;
            }
        }
        total.setText(bean.getHistoryTotalDuration() + "分");
    }
}
