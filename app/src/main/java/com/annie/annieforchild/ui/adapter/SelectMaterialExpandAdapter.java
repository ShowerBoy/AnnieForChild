package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.material.MaterialGroup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wanglei on 2018/4/9.
 */

public class SelectMaterialExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<MaterialGroup> groupList;
    private HashMap<Integer, List<MaterialGroup>> childList;

    public SelectMaterialExpandAdapter(Context context, List<MaterialGroup> groupList, HashMap<Integer, List<MaterialGroup>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_select_material_group_item, parent, false);
        holder = new GroupViewHolder(convertView);
        holder.grouptextView.setText(groupList.get(groupPosition).getTitle());
        if (groupList.get(groupPosition).isSelect()) {
            holder.grouptextView.setTextColor(context.getResources().getColor(R.color.text_orange));
            holder.groupSelect.setBackgroundColor(context.getResources().getColor(R.color.text_orange));
        } else {
            holder.grouptextView.setTextColor(context.getResources().getColor(R.color.text_black));
            holder.groupSelect.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_select_material_child_item, parent, false);
        holder = new ChildViewHolder(convertView);
        holder.childtextView.setText(childList.get(groupPosition).get(childPosition).getTitle());
        if (childList.get(groupPosition).get(childPosition).isSelect()) {
            holder.childtextView.setTextColor(context.getResources().getColor(R.color.text_orange));
        } else {
            holder.childtextView.setTextColor(context.getResources().getColor(R.color.text_black));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        TextView grouptextView;
        TextView groupSelect;

        public GroupViewHolder(View view) {
            grouptextView = view.findViewById(R.id.select_group_text);
            groupSelect = view.findViewById(R.id.group_select);
        }
    }

    class ChildViewHolder {
        TextView childtextView;

        public ChildViewHolder(View view) {
            childtextView = view.findViewById(R.id.select_child_text);
        }
    }
}
