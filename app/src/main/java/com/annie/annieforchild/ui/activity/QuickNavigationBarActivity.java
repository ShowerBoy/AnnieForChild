package com.annie.annieforchild.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BaseFragment;
import com.annie.baselibrary.base.BasePresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


/**
 * 运用底部NavigationBar结合fragment的快速开发类
 * Created by WangLei on 2018/1/12
 * <p>
 * Tip:
 * 1.修改BottomNavigationBar 图标和文字的间距的方法:
 * 在value文件夹中的dimens.xml里面复写fixed_height_bottom_padding（默认是10dp，值越小，间距越大。适合的模式是BottomNavigationBar.MODE_FIXED）
 */

public abstract class QuickNavigationBarActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    //    @BindView(R.id.tabFrame)
    FrameLayout tabFrame;
    //    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    protected BaseFragment[] fragments;//对应的fragment集合
    protected int[] drawables;//底部对应图片
    protected String[] texts;//底部对应文字
    protected int[] active_colors;//选中时颜色
    protected int[] inactive_colors;//未选中时颜色
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

//    {
//        setFragments();
//        setDrawable();
//        setTexts();
//        setActive_Color();
//        setInactive_Color();
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quick_navigation_bar;
    }

    @Override
    protected void initView() {
        initialize();
        tabFrame = findViewById(R.id.tabFrame);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
//        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        if (fragments != null) {
            transaction = fragmentManager.beginTransaction();
            for (BaseFragment bf : fragments) {
                transaction.add(R.id.tabFrame, bf, bf.getFlagTag());
            }
            hideFragment();
            showFragment(0);
        }
        transaction.commit();
        if (drawables != null && texts != null && active_colors != null && inactive_colors != null) {
            bottomNavigationBar
                    .setMode(1)
                    .setBackgroundStyle(0)
                    .addItem(new BottomNavigationItem(drawables[0], texts[0]).setActiveColorResource(active_colors[0]).setInActiveColorResource(inactive_colors[0]))
                    .addItem(new BottomNavigationItem(drawables[1], texts[1]).setActiveColorResource(active_colors[1]).setInActiveColorResource(inactive_colors[1]))
                    .addItem(new BottomNavigationItem(drawables[2], texts[2]).setActiveColorResource(active_colors[2]).setInActiveColorResource(inactive_colors[2]))
                    .addItem(new BottomNavigationItem(drawables[3], texts[3]).setActiveColorResource(active_colors[3]).setInActiveColorResource(inactive_colors[3]))
                    .setFirstSelectedPosition(0)
                    .initialise();
            bottomNavigationBar.setTabSelectedListener(this);
        }
    }

    private void initialize() {
        setFragments();
        setDrawable();
        setTexts();
        setActive_Color();
        setInactive_Color();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onTabSelected(int position) {
        hideFragment();
        showFragment(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void hideFragment() {
        FragmentTransaction trans = fragmentManager.beginTransaction();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                trans.hide(fragment);
            }
        }
        trans.commit();
    }

    private void showFragment(int index) {
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        trans.show(fragments[index]);
        trans.commit();
    }

    protected void setFragments() {
        this.fragments = getFragments();
    }

    protected void setDrawable() {
        this.drawables = getDrawable();
    }

    protected void setTexts() {
        this.texts = getText();
    }

    protected void setActive_Color() {
        this.active_colors = getActive_Color();
    }

    protected void setInactive_Color() {
        this.inactive_colors = getInactive_Color();
    }

    protected abstract BaseFragment[] getFragments();

    protected abstract int[] getDrawable();//获取底部图片

    protected abstract String[] getText();//获取底部文字

    protected abstract int[] getActive_Color();//获取选中时颜色

    protected abstract int[] getInactive_Color();//获取未选中时颜色

}
