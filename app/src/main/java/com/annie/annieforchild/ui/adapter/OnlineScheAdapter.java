package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Schedule;
import com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.MemberFooterViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.MemberViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.OnlineFooterViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.OnlineScheViewHolder;

import java.util.List;

/**
 * 线上课程适配器
 * Created by WangLei on 2018/3/1 0001
 */

public class OnlineScheAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ITEM_TYPE_CONTENT = 1;
    private int ITEM_TYPE_BOTTOM = 2;
    private int mBottomCount = 1;//底部View个数
    private Context context;
    private List<Schedule> lists;
    private LayoutInflater inflater;

    public OnlineScheAdapter(Context context, List<Schedule> lists) {
        this.context = context;
        this.lists = lists;
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
            return new OnlineFooterViewHolder(inflater.inflate(R.layout.activity_online_footer_item, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new OnlineScheViewHolder(inflater.inflate(R.layout.activity_online_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OnlineFooterViewHolder) {
            ((OnlineFooterViewHolder) holder).addSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddOnlineScheActivity.class);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof OnlineScheViewHolder) {
            ((OnlineScheViewHolder) holder).scheduleTime.setText(lists.get(position).getStart() + "-" + lists.get(position).getStop());
            ((OnlineScheViewHolder) holder).scheduleName.setText(lists.get(position).getMaterialName());
            ((OnlineScheViewHolder) holder).selectSpot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SystemUtils.show(context, "修改" + position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + mBottomCount;
    }
}
