package com.annie.annieforchild.ui.fragment.myspeaking;

import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

/**
 * 累计口语存折
 * Created by wanglei on 2018/9/11.
 */

public class TotalSpeakingFragment extends BaseFragment {
    private TextView xitong;
    private MyGrindEarBean bean;

    {
        setRegister(true);
    }

    public static TotalSpeakingFragment instance() {
        TotalSpeakingFragment fragment = new TotalSpeakingFragment();
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        xitong = view.findViewById(R.id.total_speaking_system_duration);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_total_speaking_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
//        if (message.what == MethodCode.EVENT_GETMYSPEAKING) {
//            bean = (MyGrindEarBean) message.obj;
//            refresh();
//        }
    }

    private void refresh() {
        if (bean != null) {
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
            if (hour == 0) {
                xitong.setText(min + "分");
            } else {
                xitong.setText(hour + "小时" + min + "分");
            }
        }
    }
}
