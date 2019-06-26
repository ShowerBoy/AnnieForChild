package com.annie.annieforchild.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.ui.activity.lesson.SelectMaterialActivity;
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
    private SchedulePresenter presenter;
    private List<Schedule> lists;
    private LayoutInflater inflater;
    private String[] strings;
    private String date;

    public OnlineScheAdapter(Context context, List<Schedule> lists, SchedulePresenter presenter) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
        strings = new String[]{"编辑课表", "删除课表"};
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
            return new OnlineFooterViewHolder(inflater.inflate(R.layout.activity_online_sche_footer_item, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new OnlineScheViewHolder(inflater.inflate(R.layout.activity_online_sche_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OnlineFooterViewHolder) {
            ((OnlineFooterViewHolder) holder).addSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //添加课程
                    Intent intent = new Intent(context, SelectMaterialActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("date", date);
                    bundle.putSerializable("schedule", null);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof OnlineScheViewHolder) {
            ((OnlineScheViewHolder) holder).scheduleTime.setText(lists.get(position).getStart() + "-" + lists.get(position).getStop());
            ((OnlineScheViewHolder) holder).scheduleName.setText(lists.get(position).getDetail());
            ((OnlineScheViewHolder) holder).selectSpot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    SystemUtils.show(context, "修改" + lists.get(position).getScheduleId());
                    new AlertDialog.Builder(context).setTitle("编辑")
                            .setItems(strings, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    SystemUtils.show(context, strings[which]);
                                    if (which == 0) {
                                        Intent intent = new Intent(context, SelectMaterialActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("date", date);
                                        bundle.putSerializable("schedule", lists.get(position));
                                        intent.putExtras(bundle);
                                        context.startActivity(intent);
                                        dialog.dismiss();
                                    } else {
                                        presenter.deleteSchedule(lists.get(position).getScheduleid());
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + mBottomCount;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
