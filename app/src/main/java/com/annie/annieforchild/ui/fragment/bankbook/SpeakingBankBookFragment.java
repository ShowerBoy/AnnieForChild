package com.annie.annieforchild.ui.fragment.bankbook;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.GrindTime;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.ui.activity.grindEar.InputActivity;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/9/18.
 */

public class SpeakingBankBookFragment extends BaseFragment implements View.OnClickListener {
    private TextView today_huiben, total_huiben, today_zhuti, total_zhuti, today_jiaoji, total_jiaoji, today_donghua, total_donghua, today_yanjiang, total_yanjiang, today_diandubi, total_diandubi, today_qita, total_qita, today_tuijian, total_tuijian, input, todayTotal, historyTotal;
    private MyGrindEarBean bean;
    private List<GrindTime> todayList;
    private List<GrindTime> historyList;

    {
        setRegister(true);
    }

    public static SpeakingBankBookFragment instance() {
        SpeakingBankBookFragment fragment = new SpeakingBankBookFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        todayList = new ArrayList<>();
        historyList = new ArrayList<>();
    }

    @Override
    protected void initView(View view) {
        today_huiben = view.findViewById(R.id.today_donghuakouyu_dura);
        total_huiben = view.findViewById(R.id.total_donghuakouyu_dura);
        today_zhuti = view.findViewById(R.id.today_zhuti_speaking_dura);
        total_zhuti = view.findViewById(R.id.total_zhuti_speaking_dura);
        today_jiaoji = view.findViewById(R.id.today_jiaoji_speaking_dura);
        total_jiaoji = view.findViewById(R.id.total_jiaoji_speaking_dura);
        today_donghua = view.findViewById(R.id.today_donghua_speaking_dura);
        total_donghua = view.findViewById(R.id.total_donghua_speaking_dura);
        today_yanjiang = view.findViewById(R.id.today_yanjiang_speaking_dura);
        total_yanjiang = view.findViewById(R.id.total_yanjiang_speaking_dura);
        today_qita = view.findViewById(R.id.today_qita_speaking_dura);
        total_qita = view.findViewById(R.id.total_qita_speaking_dura);
        today_diandubi = view.findViewById(R.id.today_diandubi_speaking_dura);
        total_diandubi = view.findViewById(R.id.total_diandubi_speaking_dura);
        today_tuijian = view.findViewById(R.id.today_tuijian_speaking_dura);
        total_tuijian = view.findViewById(R.id.total_tuijian_speaking_dura);
        input = view.findViewById(R.id.speaking_luru_btn);
        todayTotal = view.findViewById(R.id.speaking_today_total_dura);
        historyTotal = view.findViewById(R.id.speaking_history_total_dura);
        input.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_speaking_bank_fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speaking_luru_btn:
                Intent intent = new Intent(getContext(), InputActivity.class);
                intent.putExtra("tag", "speaking");
                startActivity(intent);
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYSPEAKING) {
            bean = (MyGrindEarBean) message.obj;
            refresh(bean);
        }
    }

    private void refresh(MyGrindEarBean bean) {
        if (bean != null) {
            todayList.clear();
            historyList.clear();
            todayList.addAll(bean.getTodayList());
            historyList.addAll(bean.getHistoryList());
            if (todayList != null) {
                for (int i = 0; i < todayList.size(); i++) {
                    GrindTime grindTime = todayList.get(i);
                    int time = (int) Double.parseDouble(grindTime.getDuration());
                    int min = time / 60;
                    int remainder = time % 60;
                    int hour = 0;
                    if (min <= 0) {
                        if (remainder > 0) {
                            min = min + 1;
                        }
                    } else {
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (remainder > 0) {
                            min = min + 1;
                        }
                    }
                    switch (grindTime.getType()) {
                        case "0":
                            today_tuijian.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "13":
                            today_huiben.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "14":
                            today_zhuti.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "15":
                            today_jiaoji.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "16":
                            today_donghua.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "17":
                            today_yanjiang.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "others":
                            today_qita.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "readingpen":
                            today_diandubi.setText("今日：" + hour + "小时" + min + "分");
                            break;
                    }
                }
            }

            if (historyList != null) {
                for (int i = 0; i < historyList.size(); i++) {
                    GrindTime grindTime = historyList.get(i);
                    int time = (int) Double.parseDouble(grindTime.getDuration());
                    int min = time / 60;
                    int remainder = time % 60;
                    int hour = 0;
                    if (min <= 0) {
                        if (remainder > 0) {
                            min = min + 1;
                        }
                    } else {
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (remainder > 0) {
                            min = min + 1;
                        }
                    }
                    switch (grindTime.getType()) {
                        case "0":
                            total_tuijian.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "13":
                            total_huiben.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "14":
                            total_zhuti.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "15":
                            total_jiaoji.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "16":
                            total_donghua.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "17":
                            total_yanjiang.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "others":
                            total_qita.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "readingpen":
                            total_diandubi.setText("累计：" + hour + "小时" + min + "分");
                            break;
                    }
                }
            }

            int time = (int) Double.parseDouble(bean.getTodayTotalDuration());
            int min = time / 60;
            int remainder = time % 60;
            int hour = 0;
            if (min <= 0) {
                if (remainder > 0) {
                    min = min + 1;
                }
            } else {
                if (min > 60) {
                    hour = min / 60;
                    min = min % 60;
                }
                if (remainder > 0) {
                    min = min + 1;
                }
            }
            todayTotal.setText(hour + "小时" + min + "分");
            time = (int) Double.parseDouble(bean.getHistoryTotalDuration());
            min = time / 60;
            remainder = time % 60;
            hour = 0;
            if (min <= 0) {
                if (remainder > 0) {
                    min = min + 1;
                }
            } else {
                if (min > 60) {
                    hour = min / 60;
                    min = min % 60;
                }
                if (remainder > 0) {
                    min = min + 1;
                }
            }
            historyTotal.setText(hour + "小时" + min + "分");
        }
    }
}
