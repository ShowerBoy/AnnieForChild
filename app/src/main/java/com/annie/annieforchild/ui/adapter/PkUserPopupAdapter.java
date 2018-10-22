package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.UserInfo2;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanglei on 2018/4/20.
 */

public class PkUserPopupAdapter extends BaseAdapter {
    private List<UserInfo2> lists;
    private Context context;
    private LayoutInflater inflater;

    public PkUserPopupAdapter(Context context, List<UserInfo2> lists) {
        this.context = context;
        this.lists = lists;
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
        PkUserViewHolder holder = null;
        if (convertView == null) {
            holder = new PkUserViewHolder();
            convertView = inflater.inflate(R.layout.activity_pk_user_item, parent, false);
            holder.headpic = convertView.findViewById(R.id.pkUser_image);
            holder.name = convertView.findViewById(R.id.pkUser_name);
            convertView.setTag(holder);
        } else {
            holder = (PkUserViewHolder) convertView.getTag();
        }
        if (lists.get(position).getAvatar() != null) {
            Glide.with(context).load(lists.get(position).getAvatar()).into(holder.headpic);
        }
        holder.name.setText(lists.get(position).getName());
        return convertView;
    }

    private static class PkUserViewHolder {
        public CircleImageView headpic;
        public TextView name;
    }
}
