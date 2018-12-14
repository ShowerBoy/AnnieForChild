package com.annie.annieforchild.ui.fragment.bankbook;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
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

public class GrindEarBankBookFragment extends BaseFragment implements View.OnClickListener {
    private TextView today_erge, total_erge, today_huiben, total_huiben, today_shige, total_shige, today_zhangjieshu, total_zhangjieshu, today_fenji, total_fenji, today_qiaoliangshu, total_qiaoliangshu, today_gushi, total_gushi, today_duihua, total_duihua, today_donghua, total_donghua, today_zuoye, total_zuoye, today_tuijian, total_tuijian, today_diandubi, total_diandubi, today_xiaomobao, total_xiaomobao, today_mobao, total_mobao, today_qita, total_qita, today_huibenkouyu, total_huibenkouyu, today_zhutikouyu, total_zhutikouyu, today_jiaojikouyu, total_jiaojikouyu, today_donghuakouyu, total_donghuakouyu, today_xiangmuyanjiang, total_xiangmuyanjiang, input, todayTotal, historyTotal;
    private MyGrindEarBean bean;
    private List<GrindTime> todayList;
    private List<GrindTime> historyList;

    {
        setRegister(true);
    }

    public static GrindEarBankBookFragment instance() {
        GrindEarBankBookFragment fragment = new GrindEarBankBookFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        todayList = new ArrayList<>();
        historyList = new ArrayList<>();
    }

    @Override
    protected void initView(View view) {
        today_erge = view.findViewById(R.id.today_erge_dura);
        total_erge = view.findViewById(R.id.total_erge_dura);
        today_huiben = view.findViewById(R.id.today_huiben_dura);
        total_huiben = view.findViewById(R.id.total_huiben_dura);
        today_shige = view.findViewById(R.id.today_shige_dura);
        total_shige = view.findViewById(R.id.total_shige_dura);
        today_zhangjieshu = view.findViewById(R.id.today_zhangjieshu_dura);
        total_zhangjieshu = view.findViewById(R.id.total_zhangjieshu_dura);
        today_fenji = view.findViewById(R.id.today_fenjiduwu_dura);
        total_fenji = view.findViewById(R.id.total_fenjiduwu_dura);
        today_qiaoliangshu = view.findViewById(R.id.today_qiaoliangshu_dura);
        total_qiaoliangshu = view.findViewById(R.id.total_qiaoliangshu_dura);
        today_gushi = view.findViewById(R.id.today_gushi_dura);
        total_gushi = view.findViewById(R.id.total_gushi_dura);
        today_duihua = view.findViewById(R.id.today_duihua_dura);
        total_duihua = view.findViewById(R.id.total_duihua_dura);
        today_donghua = view.findViewById(R.id.today_donghua_dura);
        total_donghua = view.findViewById(R.id.total_donghua_dura);
        today_zuoye = view.findViewById(R.id.today_zuoye_dura);
        total_zuoye = view.findViewById(R.id.total_zuoye_dura);
        today_tuijian = view.findViewById(R.id.today_tuijian_dura);
        total_tuijian = view.findViewById(R.id.total_tuijian_dura);
        today_diandubi = view.findViewById(R.id.today_diandubi_dura);
        total_diandubi = view.findViewById(R.id.total_diandubi_dura);
        today_xiaomobao = view.findViewById(R.id.today_xiaomobao_dura);
        total_xiaomobao = view.findViewById(R.id.total_xiaomobao_dura);
        today_mobao = view.findViewById(R.id.today_mobao_dura);
        total_mobao = view.findViewById(R.id.total_mobao_dura);
        today_qita = view.findViewById(R.id.today_qita_dura);
        total_qita = view.findViewById(R.id.total_qita_dura);
        today_huibenkouyu = view.findViewById(R.id.today_huibenkouyu_dura);
        total_huibenkouyu = view.findViewById(R.id.total_huibenkouyu_dura);
        today_zhutikouyu = view.findViewById(R.id.today_zhutikouyu_dura);
        total_zhutikouyu = view.findViewById(R.id.total_zhutikouyu_dura);
        today_jiaojikouyu = view.findViewById(R.id.today_jiaojikouyu_dura);
        total_jiaojikouyu = view.findViewById(R.id.total_jiaojikouyu_dura);
        today_donghuakouyu = view.findViewById(R.id.today_donghuakouyu_dura);
        total_donghuakouyu = view.findViewById(R.id.total_donghuakouyu_dura);
        today_xiangmuyanjiang = view.findViewById(R.id.today_xiangmuyanjiang_dura);
        total_xiangmuyanjiang = view.findViewById(R.id.total_xiangmuyanjiang_dura);
        input = view.findViewById(R.id.grind_ear_luru_btn);
        todayTotal = view.findViewById(R.id.grind_today_total_dura);
        historyTotal = view.findViewById(R.id.grind_history_total_dura);
        input.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grind_bank_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYLISTENING) {
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
                        case "1":
                            today_erge.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "2":
                            today_shige.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "3":
                            today_duihua.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "4":
                            today_gushi.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "5":
                            today_huiben.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "6":
                            today_fenji.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "7":
                            today_qiaoliangshu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "8":
                            today_zhangjieshu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "12":
                            today_zuoye.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "13":
                            today_huibenkouyu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "14":
                            today_zhutikouyu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "15":
                            today_jiaojikouyu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "16":
                            today_donghuakouyu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "17":
                            today_xiangmuyanjiang.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "100":
                            today_donghua.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "mobao":
                            today_mobao.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "readingpen":
                            today_diandubi.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "others":
                            today_qita.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "xiaomobao":
                            today_xiaomobao.setText("今日：" + hour + "小时" + min + "分");
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
                        case "1":
                            total_erge.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "2":
                            total_shige.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "3":
                            total_donghua.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "4":
                            total_gushi.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "5":
                            total_huiben.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "6":
                            total_fenji.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "7":
                            total_qiaoliangshu.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "8":
                            total_zhangjieshu.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "12":
                            total_zuoye.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "13":
                            total_huibenkouyu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "14":
                            total_zhutikouyu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "15":
                            total_jiaojikouyu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "16":
                            total_donghuakouyu.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "17":
                            total_xiangmuyanjiang.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "100":
                            total_donghua.setText("今日：" + hour + "小时" + min + "分");
                            break;
                        case "mobao":
                            total_mobao.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "readingpen":
                            total_diandubi.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "others":
                            total_qita.setText("累计：" + hour + "小时" + min + "分");
                            break;
                        case "xiaomobao":
                            total_xiaomobao.setText("累计：" + hour + "小时" + min + "分");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grind_ear_luru_btn:
                Intent intent = new Intent(getContext(), InputActivity.class);
                intent.putExtra("tag", "grindear");
                startActivity(intent);
                break;
        }
    }
}
