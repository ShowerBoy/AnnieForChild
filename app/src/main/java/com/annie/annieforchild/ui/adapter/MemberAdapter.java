package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.ui.activity.child.AddStudentActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.MemberFooterViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.MemberViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by WangLei on 2018/2/26 0026
 */

public class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ITEM_TYPE_CONTENT = 1;
    private int ITEM_TYPE_BOTTOM = 2;
    private int mBottomCount = 1;//底部View个数
    private List<UserInfo2> lists;
    private Context context;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;
    private String tag;

    public MemberAdapter(Context context, List<UserInfo2> lists, String tag, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.tag = tag;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    //内容长度
    public int getContentItemCount() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mBottomCount != 0 && position >= dataItemCount) {
            return ITEM_TYPE_BOTTOM;
        } else {
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_BOTTOM) {
            MemberFooterViewHolder holder = new MemberFooterViewHolder(inflater.inflate(R.layout.activity_member_footer_item, parent, false));
            return holder;
        } else if (viewType == ITEM_TYPE_CONTENT) {
            MemberViewHolder holder = new MemberViewHolder(inflater.inflate(R.layout.activity_member_item, parent, false));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(view);
                    return true;
                }
            });
            return holder;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MemberFooterViewHolder) {
            ((MemberFooterViewHolder) holder).addMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (tag.equals("游客")) {
//                        SystemUtils.show(context, "请登录");
//                        return;
//                    }
                    if (tag.equals("游客")) {
                        SystemUtils.toLogin(context);
                        return;
                    }
                    CountEvent Event_0406 = new CountEvent(MethodCode.A0406);
                    JAnalyticsInterface.onEvent(context, Event_0406);
//                    Intent intent = new Intent(context, AddChildActivity.class);
                    Intent intent = new Intent(context, AddStudentActivity.class);
                    intent.putExtra("from", "addMember");
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof MemberViewHolder) {
            Glide.with(context).load(lists.get(position).getAvatar()).error(R.drawable.icon_system_photo).into(((MemberViewHolder) holder).headpic);
            ((MemberViewHolder) holder).name.setText(lists.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + mBottomCount;
    }
}
