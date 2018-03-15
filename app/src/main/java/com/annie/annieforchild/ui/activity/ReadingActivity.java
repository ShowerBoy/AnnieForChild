package com.annie.annieforchild.ui.activity;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.presenter.ReadingPresenter;
import com.annie.annieforchild.presenter.imp.ReadingPresenterImp;
import com.annie.annieforchild.view.ReadingView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 阅读
 * Created by WangLei on 2018/1/19 0019
 */

public class ReadingActivity extends BaseActivity implements ReadingView, View.OnClickListener {
    private ImageView readingBack;
    private Button difficultButton, typeButton;
    private ListView popup_listView;
    private TextView filter_text;
    private CheckBox advice;
    private ReadingPresenter presenter;
    private PopupWindow popupWindow;
    private View popup_contentView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reading;
    }

    @Override
    protected void initView() {
        readingBack = findViewById(R.id.reading_back);
        difficultButton = findViewById(R.id.difficult_button);
        typeButton = findViewById(R.id.type_button);
        advice = findViewById(R.id.advice);
        filter_text = findViewById(R.id.filter_text);
        readingBack = findViewById(R.id.reading_back);
        readingBack.setOnClickListener(this);
        difficultButton.setOnClickListener(this);
        typeButton.setOnClickListener(this);
        popup_contentView = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_item, null);
        popup_listView = popup_contentView.findViewById(R.id.popup_lists1);
        popupWindow = new PopupWindow(popup_contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(popup_contentView);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        advice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SystemUtils.show(ReadingActivity.this, b + "");
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new ReadingPresenterImp(this, this);
        presenter.initViewAndData();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.difficult_button:
                presenter.setPopupWindow("难度", difficultButton);
//                popupWindow.showAsDropDown(difficultButton);
                break;
            case R.id.type_button:
                presenter.setPopupWindow("类型", typeButton);
                break;
            case R.id.reading_back:
                finish();
                break;
        }
    }

    @Override
    public void showInfo(String info) {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }

    @Override
    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    @Override
    public ListView getPopupListView() {
        return popup_listView;
    }
}
