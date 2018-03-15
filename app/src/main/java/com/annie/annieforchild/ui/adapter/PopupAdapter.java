package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.annie.annieforchild.R;

import java.util.List;

/**
 * Created by WangLei on 2018/1/19 0019
 */

public class PopupAdapter extends BaseAdapter {
    private List<String> popup_lists;
    private Context context;
    private LayoutInflater inflater;

    public PopupAdapter(Context context, List<String> popup_lists) {
        this.popup_lists = popup_lists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return popup_lists != null ? popup_lists.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return popup_lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new MyViewHolder();
            view = inflater.inflate(R.layout.activity_item_popup_list, viewGroup, false);
            viewHolder.popup_list_text = view.findViewById(R.id.popup_list_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        viewHolder.popup_list_text.setText(popup_lists.get(i));
        return view;
    }

    private static class MyViewHolder {
        public TextView popup_list_text;
    }


}
