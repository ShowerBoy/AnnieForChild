package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/2/7 0007
 */

public class BindStudentViewHolder extends RecyclerView.ViewHolder {
    private TextView student_name, student_number, student_class;
    private ImageView bind_btn;

    public BindStudentViewHolder(View itemView) {
        super(itemView);
        student_name = itemView.findViewById(R.id.bind_student_name);
        student_number = itemView.findViewById(R.id.bind_student_number);
        student_class = itemView.findViewById(R.id.bind_student_class);
        bind_btn = itemView.findViewById(R.id.bind_btn);
    }
}
