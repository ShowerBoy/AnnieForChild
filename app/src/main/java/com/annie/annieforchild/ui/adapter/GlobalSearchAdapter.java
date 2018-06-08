package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.annie.annieforchild.R;

import java.util.List;

/**
 * Created by wanglei on 2018/5/16.
 */

public class GlobalSearchAdapter extends BaseAdapter implements Filterable{
    private List<String> lists;
    private Context context;
    private LayoutInflater inflater;

    public GlobalSearchAdapter(List<String> lists, Context context) {
        this.lists = lists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            holder = new MyViewHolder();
            convertView = inflater.inflate(R.layout.activity_global_search_item, null);
            holder.textView = convertView.findViewById(R.id.global_search_text);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.textView.setText(lists.get(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private class MyViewHolder {
        TextView textView;
    }
}
