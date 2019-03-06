package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.imp.MainPresenterImp;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.RecommendViewHolder;
import com.annie.annieforchild.ui.application.MyApplication;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by WangLei on 2018/1/16 0016
 * {@link MainPresenterImp#initViewAndData()}
 */

public class RecommendAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Song> lists;
    private MyApplication application;

    public RecommendAdapter(Context context, List<Song> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
        application = (MyApplication) context.getApplicationContext();
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
        RecommendViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item_recommend, null);
            holder = new RecommendViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RecommendViewHolder) convertView.getTag();
        }
        Glide.with(context).load(lists.get(position).getBookImageUrl()).into(holder.image_recommend);
        holder.name_recommend.setText(lists.get(position).getBookName());
        holder.image_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(context);
                    return;
                }
                Intent intent = new Intent(context, PracticeActivity.class);
                intent.putExtra("song", lists.get(position));
                intent.putExtra("type", 0);
                intent.putExtra("audioType", 3);
                intent.putExtra("audioSource", 0);
                intent.putExtra("collectType", 0);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
