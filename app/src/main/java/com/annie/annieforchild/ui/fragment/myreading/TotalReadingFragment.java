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
 * 累计阅读时长
 * Created by wanglei on 2018/6/1.
 */

public class TotalReadingFragment extends BaseFragment {
    private TextView tingerge, tingshige, tinggushi, zuoye, huiben, fenjiduwu, qiaoliangshu, zhangjieshu, diandubi, qita, total, tuijian;
    private List<GrindTime> lists;
    private MyGrindEarBean bean;

    {
        setRegister(true);
    }

    public static TotalReadingFragment instance() {
        TotalReadingFragment fragment = new TotalReadingFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
    }

    @Override
    protected void initView(View view) {
        tingerge = view.findViewById(R.id.total_read_tingerge_duration);
        tingshige = view.findViewById(R.id.total_read_tingshige_duration);
        zuoye = view.findViewById(R.id.total_read_zuoye_duration);
        tinggushi = view.findViewById(R.id.total_read_tingduwu_duration);
        huiben = view.findViewById(R.id.total_read_huiben_duration);
        fenjiduwu = view.findViewById(R.id.total_read_fenjiduwu_duration);
        qiaoliangshu = view.findViewById(R.id.total_read_qiaoliangshu_duration);
        zhangjieshu = view.findViewById(R.id.total_read_zhangjieshu_duration);
        tuijian = view.findViewById(R.id.total_read_tuijian_duration);

        diandubi = view.findViewById(R.id.total_read_diandubi_duration);
        qita = view.findViewById(R.id.total_read_qita_duration);
        total = view.findViewById(R.id.total_read_total_duration);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_total_reading_fragment;
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
            if (bean.getHistoryList() != null) {
                lists.addAll(bean.getHistoryList());
            } else {
                lists.addAll(new ArrayList<>());
            }

            refresh();
        }
    }

    private void refresh() {
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i) != null) {
                GrindTime grindTime = lists.get(i);
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
                        if (hour == 0) {
                            tuijian.setText(min + "分");
                        } else {
                            tuijian.setText(hour + "小时" + min + "分");
                        }
                        break;
                    case "1":
                        if (hour == 0) {
                            tingerge.setText(min + "分");
                        } else {
                            tingerge.setText(hour + "小时" + min + "分");
                        }
                        break;
                    case "2":
                        if (hour == 0) {
                            tingshige.setText(min + "分");
                        } else {
                            tingshige.setText(hour + "小时" + min + "分");
                        }
                        break;
                    case "12":
                        if (hour == 0) {
                            zuoye.setText(min + "分");
                        } else {
                            zuoye.setText(hour + "小时" + min + "分");
                        }
                        break;
                    case "4":
                        if (hour == 0) {
                            tinggushi.setText(min + "分");
                        } else {
                            tinggushi.setText(hour + "小时" + min + "分");
                        }
                        break;
                    case "5":
                        if (hour == 0) {
                            huiben.setText(min + "分");
                        } else {
                            huiben.setText(hour + "小时" + min + "分");
                        }
                        break;
                    case "6":
                        if (hour == 0) {
                            fenjiduwu.setText(min + "分");
                        } else {
                            fenjiduwu.setText(hour + "小时" + min + "分");
                        }
                        break;
                    case "7":
                        if (hour == 0) {
                            qiaoliangshu.setText(min + "分");
                        } else {
                            qiaoliangshu.setText(hour + "小时" + min + "分");
                        }
                        break;
                    case "8":
                        if (hour == 0) {
                            zhangjieshu.setText(min + "分");
                        } else {
                            zhangjieshu.setText(hour + "小时" + min + "分");
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
