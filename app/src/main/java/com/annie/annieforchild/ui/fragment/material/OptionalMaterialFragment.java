package com.annie.annieforchild.ui.fragment.material;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.ui.adapter.MaterialAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 自选教材
 * Created by wanglei on 2018/4/4.
 */

public class OptionalMaterialFragment extends BaseFragment {
    private RecyclerView optionalRecycler;
    private RelativeLayout emptyLayout;
    private List<Material> lists;
    private MaterialAdapter adapter;

    {
        setRegister(true);
    }

    public static OptionalMaterialFragment instance() {
        OptionalMaterialFragment fragment = new OptionalMaterialFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();

        adapter = new MaterialAdapter(getContext(), lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        optionalRecycler.setAdapter(adapter);

    }

    @Override
    protected void initView(View view) {
        optionalRecycler = view.findViewById(R.id.optional_recycler);
        emptyLayout = view.findViewById(R.id.optional_empty_layout);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        optionalRecycler.setLayoutManager(manager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_optional_material_fragment;
    }

    /**
     * {@link com.annie.annieforchild.presenter.imp.SchedulePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYTEACHINGMATERIALS) {
            lists.clear();
            lists.addAll((List<Material>) message.obj);
            if (lists.size() == 0) {
                emptyLayout.setVisibility(View.VISIBLE);
            } else {
                emptyLayout.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
