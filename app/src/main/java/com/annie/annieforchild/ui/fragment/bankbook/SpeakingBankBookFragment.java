package com.annie.annieforchild.ui.fragment.bankbook;

import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.GrindTime;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/9/18.
 */

public class SpeakingBankBookFragment extends BaseFragment implements OnCheckDoubleClick, SongView {
    private TextView today_huiben, total_huiben, today_zhuti, total_zhuti, today_jiaoji, total_jiaoji, today_donghua, total_donghua, today_yanjiang, total_yanjiang, today_diandubi, total_diandubi, today_qita, total_qita, today_tuijian, total_tuijian, input, todayTotal, historyTotal;
    private LinearLayout diandubiLayout, otherLayout;
    private MyGrindEarBean bean;
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

    public static SpeakingBankBookFragment instance() {
        SpeakingBankBookFragment fragment = new SpeakingBankBookFragment();
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
        today_huiben = view.findViewById(R.id.today_huiben_speaking_dura);
        total_huiben = view.findViewById(R.id.total_huiben_speaking_dura);
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
        diandubiLayout = view.findViewById(R.id.speaking_diandubi);
        otherLayout = view.findViewById(R.id.speaking_other);
        listener = new CheckDoubleClickListener(this);
        input.setOnClickListener(listener);
        diandubiLayout.setOnClickListener(listener);
        otherLayout.setOnClickListener(listener);
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
                presenter.commitSpeaking(type, duration);
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_speaking_bank_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYSPEAKING) {
            bean = (MyGrindEarBean) message.obj;
            refresh(bean);
        } else if (message.what == MethodCode.EVENT_COMMITSPEAKING) {
            presenter.getMySpeaking();
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
                        case "13":
                            if (hour == 0) {
                                today_huiben.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_huiben.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "14":
                            if (hour == 0) {
                                today_zhuti.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_zhuti.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "15":
                            if (hour == 0) {
                                today_jiaoji.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_jiaoji.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "16":
                            if (hour == 0) {
                                today_donghua.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_donghua.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "17":
                            if (hour == 0) {
                                today_yanjiang.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_yanjiang.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "others":
                            if (hour == 0) {
                                today_qita.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_qita.setText("今日：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "readingpen":
                            if (hour == 0) {
                                today_diandubi.setText("今日：" + min + "分" + second + "秒");
                            } else {
                                today_diandubi.setText("今日：" + hour + "小时" + min + "分");
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
                        case "13":
                            if (hour == 0) {
                                total_huiben.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_huiben.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "14":
                            if (hour == 0) {
                                total_zhuti.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_zhuti.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "15":
                            if (hour == 0) {
                                total_jiaoji.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_jiaoji.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "16":
                            if (hour == 0) {
                                total_donghua.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_donghua.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "17":
                            if (hour == 0) {
                                total_yanjiang.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_yanjiang.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "others":
                            if (hour == 0) {
                                total_qita.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_qita.setText("累计：" + hour + "小时" + min + "分");
                            }
                            break;
                        case "readingpen":
                            if (hour == 0) {
                                total_diandubi.setText("累计：" + min + "分" + second + "秒");
                            } else {
                                total_diandubi.setText("累计：" + hour + "小时" + min + "分");
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
            case R.id.speaking_luru_btn:
//                Intent intent = new Intent(getContext(), InputActivity.class);
//                intent.putExtra("tag", "speaking");
//                startActivity(intent);
                break;
            case R.id.speaking_diandubi:
                type = "readingpen";
                timerPickerView.show();
                break;
            case R.id.speaking_other:
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
