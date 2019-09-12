package com.annie.annieforchild.ui.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.ui.activity.net.NetSuggestActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetSpecialViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 网课专项课
 */

public class NetSpecialAdapter1 extends RecyclerView.Adapter {
    private Context context;
    private List<NetClass> lists;//专项课列表
    private List<String> lists_bottom;//底部图片列表
    private List<String> list_center;//微信号
    private LayoutInflater inflater;
    private final static int FOOT_COUNT = 1;

    private final static int TYPE_CONTENT = 1;
    private final static int TYPE_CENTER = 2;
    private final static int TYPE_FOOTER = 3;

    public NetSpecialAdapter1(Context context, List<NetClass> lists, List<String> lists_bottom, List<String> list_center) {
        this.context = context;
        this.lists = lists;
        this.list_center = list_center;
        this.lists_bottom = lists_bottom;
        inflater = LayoutInflater.from(context);
    }

    public int getContentSize() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        int contentSize = getContentSize();
        if (position < contentSize) {//内容
            return TYPE_CONTENT;
        } else if (position == contentSize) {//中间部分
            return TYPE_CENTER;
        } else {
            return TYPE_FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_CONTENT) {
            NetSpecialViewHolder holder = null;
            holder = new NetSpecialViewHolder(inflater.inflate(R.layout.activity_netspecial_item, viewGroup, false));
            return holder;
        } else if (viewType == TYPE_CENTER) {
            CenterHolder centerHolder = null;
            centerHolder = new CenterHolder(inflater.inflate(R.layout.activity_net_exp_fragment_center, viewGroup, false));
            return centerHolder;
        } else {
            BottonHolder bottonHolder = null;
            bottonHolder = new BottonHolder(inflater.inflate(R.layout.activity_net_suggest_fragment_item, viewGroup, false));
            return bottonHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
       if(holder instanceof NetSpecialViewHolder){
           NetSpecialViewHolder netSpecialViewHolder = (NetSpecialViewHolder) holder;
           Glide.with(context).load(lists.get(i).getNetImageUrl()).error(R.drawable.image_error).into(netSpecialViewHolder.image);
           netSpecialViewHolder.title.setText(lists.get(i).getNetName());
           netSpecialViewHolder.summary.setText(lists.get(i).getNetSummary());
           if (lists.get(i).getEvent() == null) {
               netSpecialViewHolder.event.setVisibility(View.GONE);
           } else {
               netSpecialViewHolder.event.setVisibility(View.VISIBLE);
               netSpecialViewHolder.event.setText(lists.get(i).getEvent());
           }
           netSpecialViewHolder.price.setText("￥" + lists.get(i).getPrice());
           if (lists.get(i).getIsBuy() == 0) {
               netSpecialViewHolder.hadbuy.setVisibility(View.GONE);
           } else {
               netSpecialViewHolder.hadbuy.setVisibility(View.VISIBLE);
           }
           netSpecialViewHolder.netSpec_content_layout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent();
                   intent.setClass(context, NetSuggestActivity.class);
                   intent.setClass(context, NetSuggestActivity.class);
                   intent.putExtra("netid", lists.get(i).getNetId());
                   intent.putExtra("netimage", lists.get(i).getNetImageUrl());
                   intent.putExtra("isBuy", lists.get(i).getIsBuy());
                   intent.putExtra("message", lists.get(i).getMessage());
                   intent.putExtra("type", "综合课");
                   context.startActivity(intent);
               }
           });
       }else if(holder instanceof CenterHolder){
           CenterHolder centerHolder=(CenterHolder)holder;
           centerHolder.network_teacher1.setText("加入安妮花指导群");
           centerHolder.network_teacher_wx.setText((list_center.size()>0) ? list_center.get(0) : "");
           centerHolder.card.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   //获取剪贴板管理器：
                   ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                   // 创建普通字符型ClipData
                   ClipData mClipData = ClipData.newPlainText("Label", centerHolder.network_teacher_wx.getText());
                   // 将ClipData内容放到系统剪贴板里。
                   cm.setPrimaryClip(mClipData);
                   Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
                   return false;
               }
           });
       }else{
           BottonHolder bottonHolder=(BottonHolder)holder;
           Glide.with(context).load(lists_bottom.get(i-lists.size()-1)).into(bottonHolder.image);
       }
    }

    @Override
    public int getItemCount() {
        return lists.size() + 1 + lists_bottom.size();
    }

    // 底部图片
    class BottonHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public BottonHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.net_suggest_image);
        }
    }

    // 中间微信号
    class CenterHolder extends RecyclerView.ViewHolder {
        public TextView network_teacher_wx;
        public TextView network_teacher1;
        public ConstraintLayout card;

        public CenterHolder(View itemView) {
            super(itemView);
            network_teacher_wx = itemView.findViewById(R.id.network_teacher_wx);
            network_teacher1 = itemView.findViewById(R.id.network_teacher1);
            card = itemView.findViewById(R.id.card);
        }
    }


}



