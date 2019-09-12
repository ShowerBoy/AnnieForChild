package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.AnimationData;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.adapter.viewHolder.AnimationViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/6/14.
 */

public class AnimationAdapter extends RecyclerView.Adapter<AnimationViewHolder> {
    private Context context;
    private List<AnimationData> lists;
    private LayoutInflater inflater;
    private GrindEarPresenter presenter;
    private int type, audioSource, classId;
    private OnRecyclerItemClickListener listener;

    public AnimationAdapter(Context context, List<AnimationData> lists, int type, int audioSource, int classId, GrindEarPresenter presenter, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.type = type;
        this.classId = classId;
        this.audioSource = audioSource;
        this.listener = listener;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AnimationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        AnimationViewHolder holder = null;
        View view = inflater.inflate(R.layout.activity_animation_fragment_item, viewGroup, false);
        holder = new AnimationViewHolder(view);
        holder.itemView.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                listener.onItemClick(view);
            }
        }));
        return holder;
    }

    @Override
    public void onBindViewHolder(AnimationViewHolder animationViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getAnimationImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(animationViewHolder.image);
        animationViewHolder.title.setText(lists.get(i).getAnimationName());
        if (lists.get(i).getIsCollected() == 0) {
            animationViewHolder.collect.setTextColor(context.getResources().getColor(R.color.text_color));
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_song_uncollect);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            animationViewHolder.collect.setCompoundDrawables(drawable, null, null, null);
        } else {
            animationViewHolder.collect.setTextColor(context.getResources().getColor(R.color.text_orange));
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_song_collect);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            animationViewHolder.collect.setCompoundDrawables(drawable, null, null, null);
        }

        if (lists.get(i).getJurisdiction() == 0) {
            animationViewHolder.lock.setVisibility(View.VISIBLE);
        } else {
            animationViewHolder.lock.setVisibility(View.GONE);
        }

        animationViewHolder.collect.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                if (lists.get(i).getJurisdiction() == 1) {
                    if (lists.get(i).getIsCollected() == 0) {
                        presenter.collectCourse(type, audioSource, lists.get(i).getAnimationId(), classId);
                    } else {
                        presenter.cancelCollection(type, audioSource, lists.get(i).getAnimationId(), classId);
                    }
                }
            }
        }));
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
