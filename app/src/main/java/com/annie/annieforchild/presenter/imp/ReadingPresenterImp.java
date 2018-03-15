package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.presenter.ReadingPresenter;
import com.annie.annieforchild.ui.adapter.PopupAdapter;
import com.annie.annieforchild.view.ReadingView;
import com.annie.baselibrary.base.BasePresenterImp;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * 阅读
 * Created by WangLei on 2018/1/19 0019
 */

public class ReadingPresenterImp extends BasePresenterImp implements ReadingPresenter {
    private Context context;
    private ReadingView readingView;
    private List<String> popup_lists;
    private PopupAdapter adapter;

    public ReadingPresenterImp(Context context, ReadingView readingView) {
        this.context = context;
        this.readingView = readingView;
    }


    @Override
    public void initViewAndData() {
        popup_lists = new ArrayList<>();
        adapter = new PopupAdapter(context, popup_lists);
        readingView.getPopupListView().setAdapter(adapter);
        readingView.getPopupListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SystemUtils.show(context, popup_lists.get(position));
                readingView.getPopupWindow().dismiss();
            }
        });
    }

    @Override
    public void setPopupWindow(String type, View view) {
        switch (type) {
            case "难度":
                popup_lists.clear();
                popup_lists.add("1");
                popup_lists.add("2");
                popup_lists.add("3");
                popup_lists.add("4");
                adapter.notifyDataSetChanged();
                readingView.getPopupWindow().showAsDropDown(view);
                break;
            case "类型":
                popup_lists.clear();
                popup_lists.add("aaa");
                popup_lists.add("bbb");
                popup_lists.add("ccc");
                popup_lists.add("ddd");
                popup_lists.add("eee");
                adapter.notifyDataSetChanged();
                readingView.getPopupWindow().showAsDropDown(view);
                break;
        }
    }

    private void reSetPopupWindow() {

    }

    @Override
    public void Success(int what, Object result) {

    }
}
