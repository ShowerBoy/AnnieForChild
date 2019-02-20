package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by zr on 2019/1/15.
 */

public class NetPreheatConsultViewHolder extends RecyclerView.ViewHolder {
    public TextView type, title;
    public ImageView video_img;
    public LinearLayout linear;
    public ConstraintLayout welcomeVideo;
    public TextView test_describe;

    public NetPreheatConsultViewHolder(View itemView) {
        super(itemView);
        type = itemView.findViewById(R.id.type);
        title = itemView.findViewById(R.id.title);
        test_describe = itemView.findViewById(R.id.test_describe);
        video_img = itemView.findViewById(R.id.video_img);
        linear = itemView.findViewById(R.id.preheatconsult_linear);
        welcomeVideo=itemView.findViewById(R.id.welcomeVideo);
    }
}
