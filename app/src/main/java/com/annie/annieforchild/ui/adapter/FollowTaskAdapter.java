package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.task.SongTask;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

/**
 * 作业列表
 * Created by wanglei on 2018/3/26.
 */

public class FollowTaskAdapter extends BaseExpandableListAdapter {
    public static final int TYPE_HEADER = -1;
    public static final int TYPE_CONTENT = 0;
    private int HeaderCount = 1;
    private Context context;
    private String[] groupList;
    private HashMap<String, SongTask> childList;

    public FollowTaskAdapter(Context context, String[] groupList, HashMap<String, SongTask> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupList[groupPosition]).getResourseList().size() + 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupList[groupPosition]).getResourseList().get(childPosition - 1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        if (childPosition < HeaderCount) {
            return TYPE_HEADER;
        } else {
            return TYPE_CONTENT;
        }
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
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_task_group_item, parent, false);
        holder = new GroupViewHolder(convertView);
        holder.textView.setText(groupList[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        int type = getChildType(groupPosition, childPosition);
        if (type == TYPE_HEADER) {
            ChildHeaderViewHolder headerViewHolder;
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_task_child_header, parent, false);
            headerViewHolder = new ChildHeaderViewHolder(convertView);
            headerViewHolder.requestText.setText(childList.get(groupList[groupPosition]).getRequest());
        } else {
            ChildViewHolder holder;
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_task_child_tiem, parent, false);
            holder = new ChildViewHolder(convertView);
            Glide.with(context).load(childList.get(groupList[groupPosition]).getResourseList().get(childPosition - 1).getSongImageUrl()).into(holder.taskImage);
            holder.taskTitle.setText(childList.get(groupList[groupPosition]).getResourseList().get(childPosition - 1).getSongName());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        TextView textView;

        public GroupViewHolder(View view) {
            textView = view.findViewById(R.id.group_text);
        }
    }

    class ChildViewHolder {
        ImageView taskImage;
        TextView taskTitle;

        public ChildViewHolder(View view) {
            taskImage = view.findViewById(R.id.follow_task_image);
            taskTitle = view.findViewById(R.id.follow_task_title);
        }
    }

    class ChildHeaderViewHolder {
        private TextView requestText;

        public ChildHeaderViewHolder(View view) {
            requestText = view.findViewById(R.id.request_text);
        }
    }
}
