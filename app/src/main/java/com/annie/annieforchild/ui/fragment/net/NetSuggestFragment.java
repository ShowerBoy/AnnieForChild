package com.annie.annieforchild.ui.fragment.net;

import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.NetBean;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.bean.net.SuggestList;
import com.annie.annieforchild.ui.activity.net.NetSuggestActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.activity.pk.BookPlayActivity2;
import com.annie.annieforchild.ui.adapter.NetSuggestAdapter;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 网课介绍
 * Created by wanglei on 2018/11/14.
 */

public class NetSuggestFragment extends BaseFragment implements OnCheckDoubleClick {
    private RecyclerView recycler;
    private LinearLayout recycler_bottom;
    private ImageView empty;
    private NetSuggestAdapter adapter;
    private Button to_NetExperience,to_NetTest;
    List<String> list_top,list_middle,list_bottom;
    CheckDoubleClickListener listener;
    pageradapter pageradapter;

    ViewPager viewpager;

    {
        setRegister(true);
    }

    public static NetSuggestFragment instance() {
        NetSuggestFragment fragment = new NetSuggestFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        list_top=new ArrayList<>();
        list_middle=new ArrayList<>();
        list_bottom=new ArrayList<>();
        adapter = new NetSuggestAdapter(getContext(), list_top);
        recycler.setAdapter(adapter);

        pageradapter=new pageradapter();

        viewpager.setOffscreenPageLimit(3);
        viewpager.setPageMargin(40);
        viewpager.setAdapter(pageradapter);
    }

    @Override
    protected void initView(View view) {

        recycler = view.findViewById(R.id.net_suggest_recycler);
        recycler_bottom = view.findViewById(R.id.net_suggest_recycler_bottom);
        empty = view.findViewById(R.id.net_suggest_empty);
        to_NetExperience=view.findViewById(R.id.to_NetExperience);
        to_NetTest=view.findViewById(R.id.to_NetTest);
        listener = new CheckDoubleClickListener(this);
        to_NetExperience.setOnClickListener(listener);
        to_NetTest.setOnClickListener(listener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
//        recycler.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setHasFixedSize(true);
        recycler.setNestedScrollingEnabled(false);

        viewpager=view.findViewById(R.id.viewpager);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_suggest_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETHOMEDATA) {
            NetWork netWork = (NetWork) message.obj;
            if (netWork != null) {
                list_top.clear();
                list_top.addAll(netWork.getSuggestList().getTop());
                list_middle.clear();
                list_middle.addAll(netWork.getSuggestList().getMiddle());
                list_bottom.clear();
                list_bottom.addAll(netWork.getSuggestList().getBottom());
                if (list_top.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                pageradapter.notifyDataSetChanged();

                recycler_bottom.removeAllViews();

                for(int i=0;i<list_bottom.size();i++){
                    ImageView imageView = new ImageView(getContext());
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    Glide.with(this).load(list_bottom.get(i)).into(imageView);
                    recycler_bottom.addView(imageView);
                }
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.to_NetExperience:
                NetWorkActivity.mVP.setCurrentItem(1);
                break;
            case R.id.to_NetTest://跳转到H5页面
                Toast.makeText(getContext(), "敬请期待", Toast.LENGTH_SHORT).show();
//                NetWorkActivity.mVP.setCurrentItem(2);
//                NetWorkActivity.mVP.setNoFocus(false);
                break;
        }
    }

    class pageradapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list_middle.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(getContext()).load(list_middle.get(position)).into(imageView);
            container.addView(imageView);
            //最后要返回的是控件本身
            return imageView;
        }
        //因为它默认是看三张图片，第四张图片的时候就会报错，还有就是不要返回父类的作用
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            //         super.destroyItem(container, position, object);
        }


    }
}
