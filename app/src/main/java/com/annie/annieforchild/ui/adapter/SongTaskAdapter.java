package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.annie.annieforchild.R;

import java.util.List;

/**
 * 歌曲作业适配器
 * Created by wanglei on 2018/3/28.
 */

public class SongTaskAdapter extends BaseAdapter {
    private Context context;
    private List<String> lists;
    private LayoutInflater inflater;

    public SongTaskAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists.size();
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
        MyViewHolder holder;
        if (convertView == null) {
            holder = new MyViewHolder();
            convertView = inflater.inflate(R.layout.activity_song_task_item, parent, false);
            holder.text = convertView.findViewById(R.id.song_task_text);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.text.setText(lists.get(position));
        return convertView;
    }

    class MyViewHolder {
        TextView text;
    }
}
