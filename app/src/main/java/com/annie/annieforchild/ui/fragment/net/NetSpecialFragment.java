package com.annie.annieforchild.ui.fragment.net;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.ui.adapter.NetBeanAdapter;
import com.annie.baselibrary.base.BaseFragment;

import java.util.List;

/**
 * 专项课
 * Created by wanglei on 2018/11/14.
 */

public class NetSpecialFragment extends BaseFragment implements OnCheckDoubleClick {
    private List<NetClass> list;
    private NetBeanAdapter adapter;
    private CheckDoubleClickListener listener;
    private RecyclerView recycler_;

    public static NetSpecialFragment instance() {
        NetSpecialFragment fragment = new NetSpecialFragment();
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_special_fragment;
    }

    @Override
    public void onCheckDoubleClick(View view) {

    }
}
