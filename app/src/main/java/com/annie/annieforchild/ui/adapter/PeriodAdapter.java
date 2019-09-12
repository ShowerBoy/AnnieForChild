package com.annie.annieforchild.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.period.Period;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.adapter.viewHolder.PeriodViewHolder;

import java.util.List;

/**
 * Created by wanglei on 2018/8/22.
 */

public class PeriodAdapter extends RecyclerView.Adapter<PeriodViewHolder> {
    private Context context;
    private List<Period> lists;
    private LayoutInflater inflater;
    private GrindEarPresenter presenter;

    public PeriodAdapter(Context context, List<Period> lists, GrindEarPresenter presenter) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PeriodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        PeriodViewHolder holder = null;
        holder = new PeriodViewHolder(inflater.inflate(R.layout.activity_period_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(PeriodViewHolder periodViewHolder, int i) {
        periodViewHolder.date.setText(lists.get(i).getDate());
        periodViewHolder.consume.setText("-" + lists.get(i).getConsumeperiod());
        if (lists.get(i).getPeriodstate() == 1) {
            periodViewHolder.state.setText("已签到");
            periodViewHolder.help.setVisibility(View.GONE);
        } else {
            periodViewHolder.state.setText("缺勤");
            periodViewHolder.help.setVisibility(View.VISIBLE);
        }
        periodViewHolder.surplus.setText(lists.get(i).getSurplusperiod() + "");
        if (lists.get(i).getState() == 0) {
            periodViewHolder.status.setText("提议");
            periodViewHolder.status.setBackgroundResource(R.drawable.icon_orange_btn2);
        } else if (lists.get(i).getState() == 1) {
            periodViewHolder.status.setText("已提议");
            periodViewHolder.status.setBackgroundResource(R.drawable.icon_green_btn);
        } else {
            periodViewHolder.status.setText("已处理");
            periodViewHolder.status.setBackgroundResource(R.drawable.icon_gray_btn);
        }
        if (Integer.parseInt(lists.get(i).getDate()) < Integer.parseInt(SystemUtils.getPastDate(7))) {
            if (lists.get(i).getState() == 0) {
                periodViewHolder.status.setBackgroundResource(R.drawable.icon_gray_btn);
            } else {
                periodViewHolder.status.setBackgroundResource(R.drawable.icon_orange_btn2);
            }
        }

        periodViewHolder.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看备注
                if (lists.get(i).getRemarks() != null && lists.get(i).getRemarks().length() != 0) {
                    SystemUtils.setBackGray((Activity) context, true);
                    SystemUtils.getHintPopup(context, lists.get(i).getRemarks()).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                } else {
                    SystemUtils.show(context, "无备注");
                }
            }
        });
        periodViewHolder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getState() == 0) {
                    //发起提议
                    if (Integer.parseInt(lists.get(i).getDate()) >= Integer.parseInt(SystemUtils.getPastDate(7))) {
                        SystemUtils.setBackGray((Activity) context, true);
                        SystemUtils.getSuggestPopup(context, "您确定要提异吗？", "发出提异", "再想想", presenter, lists.get(i).getPeriodid(), 0, "", 0, 0).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
