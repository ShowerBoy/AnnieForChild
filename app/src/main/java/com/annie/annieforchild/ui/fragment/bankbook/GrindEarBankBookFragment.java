package com.annie.annieforchild.ui.fragment.bankbook;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.GrindTime;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.InputActivity;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/9/18.
 */

public class GrindEarBankBookFragment extends BaseFragment implements OnCheckDoubleClick, SongView {
    private TextView today_erge, total_erge, today_huiben, total_huiben, today_shige, total_shige, today_zhangjieshu, total_zhangjieshu, today_fenji, total_fenji, today_qiaoliangshu, total_qiaoliangshu, today_gushi, total_gushi, today_duihua, total_duihua, today_donghua, total_donghua, today_zuoye, total_zuoye, today_tuijian, total_tuijian, today_diandubi, total_diandubi, today_xiaomobao, total_xiaomobao, today_mobao, total_mobao, today_qita, total_qita, today_huibenkouyu, total_huibenkouyu, today_zhutikouyu, total_zhutikouyu, today_jiaojikouyu, total_jiaojikouyu, today_donghuakouyu, total_donghuakouyu, today_xiangmuyanjiang, total_xiangmuyanjiang, input, todayTotal, historyTotal;
    private MyGrindEarBean bean;
    private LinearLayout readingpanLayout, xiaomobaoLayout, mobaoLayout, otherLayout;
    private List<GrindTime> todayList;
    private List<GrindTime> historyList;
    private List<Integer> hourList, minList;
    private OptionsPickerView timerPickerView;
    private CheckDoubleClickListener listener;
    private GrindEarPresenter presenter;
    private String type, duration;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    public static GrindEarBankBookFragment instance() {
        GrindEarBankBookFragment fragment = new GrindEarBankBookFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        todayList = new ArrayList<>();
        historyList = new ArrayList<>();
        hourList = new ArrayList<>();
        minList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            hourList.add(i);
        }
        for (int i = 0; i < 60; i++) {
            minList.add(i);
        }
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        initNoLinkOptionsPicker();
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
        readingpanLayout = view.findViewById(R.id.grind_readingpan);
        xiaomobaoLayout = view.findViewById(R.id.grind_xiaomobao);
        mobaoLayout = view.findViewById(R.id.grind_mobao);
        otherLayout = view.findViewById(R.id.grind_other);
        listener = new CheckDoubleClickListener(this);
        input.setOnClickListener(listener);
        readingpanLayout.setOnClickListener(listener);
        xiaomobaoLayout.setOnClickListener(listener);
        mobaoLayout.setOnClickListener(listener);
        otherLayout.setOnClickListener(listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grind_bank_fragment;
    }

    private void initNoLinkOptionsPicker() {
        timerPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (hourList.get(options1) == 0 && minList.get(options3) == 0) {
                    return;
                }
                int hour = hourList.get(options1) * 60 * 60;
                int min = minList.get(options3) * 60;
                duration = (hour + min) + "";
                presenter.commitDuration(type, duration);
//                SystemUtils.show(getContext(), hourList.get(options1) + "小时" + minList.get(options3) + "分" + "  type=" + type);
            }
        })
                .setLayoutRes(R.layout.activity_timepicker, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.picker_finish);
                        final TextView ivCancel = (TextView) v.findViewById(R.id.picker_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                timerPickerView.returnData();
                                timerPickerView.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                timerPickerView.dismiss();
                            }
                        });
                    }
                })
                .setSelectOptions(0, 0, 0)
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();
//        pvCustomOptions.setPicker(cardItem);//添加数据
//        pvCustomOptions.setPicker(options1Items, options2Items);//添加数据
        timerPickerView.setNPicker(hourList, null, minList);
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYLISTENING) {
            bean = (MyGrindEarBean) message.obj;
            refresh(bean);
        } else if (message.what == MethodCode.EVENT_COMMITDURATION) {
            presenter.getMyListening();
        }
    }

    private void refresh(MyGrindEarBean bean) {
        if (bean != null) {
            todayList.clear();
            historyList.clear();
            todayList.addAll(bean.getTodayList() != null ? bean.getTodayList() : new ArrayList<>());
            historyList.addAll(bean.getHistoryList() != null ? bean.getHistoryList() : new ArrayList<>());
            if (todayList != null) {
                for (int i = 0; i < todayList.size(); i++) {
                    GrindTime grindTime = todayList.get(i);
                    int time = (int) Double.parseDouble(grindTime.getDuration());
                    int min = time / 60;
                    int second = time % 60;
                    int hour = 0;
                    if (min >= 60) {
                        hour = min / 60;
                        min = min % 60;
                    }
                    switch (grindTime.getType()) {
                        case "0":
                            if (hour == 0) {
                                today_tuijian.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_tuijian.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "1":
                            if (hour == 0) {
                                today_erge.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_erge.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "2":
                            if (hour == 0) {
                                today_shige.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_shige.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "3":
                            if (hour == 0) {
                                today_duihua.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_duihua.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "4":
                            if (hour == 0) {
                                today_gushi.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_gushi.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "5":
                            if (hour == 0) {
                                today_huiben.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_huiben.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "6":
                            if (hour == 0) {
                                today_fenji.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_fenji.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "7":
                            if (hour == 0) {
                                today_qiaoliangshu.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_qiaoliangshu.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "8":
                            if (hour == 0) {
                                today_zhangjieshu.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_zhangjieshu.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "12":
                            if (hour == 0) {
                                today_zuoye.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_zuoye.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "13":
                            if (hour == 0) {
                                today_huibenkouyu.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_huibenkouyu.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "14":
                            if (hour == 0) {
                                today_zhutikouyu.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_zhutikouyu.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "15":
                            if (hour == 0) {
                                today_jiaojikouyu.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_jiaojikouyu.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "16":
                            if (hour == 0) {
                                today_donghuakouyu.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_donghuakouyu.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "17":
                            if (hour == 0) {
                                today_xiangmuyanjiang.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_xiangmuyanjiang.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "100":
                            if (hour == 0) {
                                today_donghua.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_donghua.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "mobao":
                            if (hour == 0) {
                                today_mobao.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_mobao.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "readingpen":
                            if (hour == 0) {
                                today_diandubi.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_diandubi.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "others":
                            if (hour == 0) {
                                today_qita.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_qita.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "xiaomobao":
                            if (hour == 0) {
                                today_xiaomobao.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_xiaomobao.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                    }
                }
            }

            if (historyList != null) {
                for (int i = 0; i < historyList.size(); i++) {
                    GrindTime grindTime = historyList.get(i);
                    int time = (int) Double.parseDouble(grindTime.getDuration());
                    int min = time / 60;
                    int second = time % 60;
                    int hour = 0;
                    if (min >= 60) {
                        hour = min / 60;
                        min = min % 60;
                    }
                    switch (grindTime.getType()) {
                        case "0":
                            if (hour == 0) {
                                total_tuijian.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_tuijian.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "1":
                            if (hour == 0) {
                                total_erge.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_erge.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "2":
                            if (hour == 0) {
                                total_shige.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_shige.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "3":
                            if (hour == 0) {
                                total_duihua.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_duihua.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "4":
                            if (hour == 0) {
                                total_gushi.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_gushi.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "5":
                            if (hour == 0) {
                                total_huiben.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_huiben.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "6":
                            if (hour == 0) {
                                total_fenji.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_fenji.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "7":
                            if (hour == 0) {
                                total_qiaoliangshu.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_qiaoliangshu.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "8":
                            if (hour == 0) {
                                total_zhangjieshu.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_zhangjieshu.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "12":
                            if (hour == 0) {
                                total_zuoye.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_zuoye.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "13":
                            if (hour == 0) {
                                total_huibenkouyu.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_huibenkouyu.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "14":
                            if (hour == 0) {
                                total_zhutikouyu.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_zhutikouyu.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "15":
                            if (hour == 0) {
                                total_jiaojikouyu.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_jiaojikouyu.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "16":
                            if (hour == 0) {
                                total_donghuakouyu.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_donghuakouyu.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "17":
                            if (hour == 0) {
                                total_xiangmuyanjiang.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_xiangmuyanjiang.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "100":
                            if (hour == 0) {
                                total_donghua.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_donghua.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "mobao":
                            if (hour == 0) {
                                total_mobao.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_mobao.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "readingpen":
                            if (hour == 0) {
                                total_diandubi.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_diandubi.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "others":
                            if (hour == 0) {
                                total_qita.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_qita.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "xiaomobao":
                            if (hour == 0) {
                                total_xiaomobao.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_xiaomobao.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                    }
                }
            }

            int time = (int) Double.parseDouble(bean.getTodayTotalDuration());
            int min = time / 60;
            int second = time % 60;
            int hour = 0;
            if (min >= 60) {
                hour = min / 60;
                min = min % 60;
            }
            if (hour == 0) {
                todayTotal.setText(min + "分" + second + "秒");
            } else {
                todayTotal.setText(hour + "小时" + min + "分");
            }

            time = (int) Double.parseDouble(bean.getHistoryTotalDuration());
            min = time / 60;
            second = time % 60;
            hour = 0;
            if (min > 60) {
                hour = min / 60;
                min = min % 60;
            }
            if (hour == 0) {
                historyTotal.setText(min + "分" + second + "秒");
            } else {
                historyTotal.setText(hour + "小时" + min + "分");
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.grind_ear_luru_btn:
//                Intent intent = new Intent(getContext(), InputActivity.class);
//                intent.putExtra("tag", "grindear");
//                startActivity(intent);
                break;
            case R.id.grind_readingpan:
                type = "readingpen";
                timerPickerView.show();
                break;
            case R.id.grind_xiaomobao:
                type = "xiaomobao";
                timerPickerView.show();
                break;
            case R.id.grind_mobao:
                type = "mobao";
                timerPickerView.show();
                break;
            case R.id.grind_other:
                type = "others";
                timerPickerView.show();
                break;
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.show();
            }
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.dismiss();
            }
        }
    }

}
