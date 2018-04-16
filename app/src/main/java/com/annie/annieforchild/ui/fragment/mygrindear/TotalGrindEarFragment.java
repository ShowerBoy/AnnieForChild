package com.annie.annieforchild.ui.fragment.mygrindear;

import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

/**
 * 累计磨耳朵
 * Created by wanglei on 2018/4/11.
 */

public class TotalGrindEarFragment extends BaseFragment {
    private TextView tingerge, tingshige, tingduihua, tingduwu, tingdonghua, tingmobao, jiqiren, diandubi, qita, total;

    {
        setRegister(true);
    }

    public static TotalGrindEarFragment instance() {
        TotalGrindEarFragment fragment = new TotalGrindEarFragment();
        return fragment;
    }

    @Override
    protected void initData() {

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
            MyGrindEarBean bean = (MyGrindEarBean) message.obj;
            refresh(bean);
        }
    }

    private void refresh(MyGrindEarBean bean) {

    }
}
