package com.annie.annieforchild.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.ui.activity.PhotoActivity;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.UploadHeaderViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.UploadViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/8/24.
 */

public class UploadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ITEM_TYPE_HEADER = 0;
    private int ITEM_TYPE_CONTENT = 1;
    private int HeaderCount = 1;
    private Context context;
    private LayoutInflater inflater;
    private List<String> lists;
    private int state;
    private int tag;
    private OnRecyclerItemClickListener listener;
    private SystemUtils systemUtils;
    private Activity activity;

    public UploadAdapter(Activity activity, Context context, List<String> lists, int state, int tag, OnRecyclerItemClickListener listener) {
        this.activity = activity;
        this.context = context;
        this.lists = lists;
        this.tag = tag;
        this.listener = listener;
        this.state = state;
        systemUtils = new SystemUtils(context);
        inflater = LayoutInflater.from(context);
        if (state == 0) {
            HeaderCount = 1;
        } else {
            HeaderCount = 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            UploadHeaderViewHolder headerViewHolder = null;
            headerViewHolder = new UploadHeaderViewHolder(inflater.inflate(R.layout.activity_upload_header_item, viewGroup, false));
            return headerViewHolder;
        } else if (viewType == ITEM_TYPE_CONTENT) {
            UploadViewHolder holder = null;
            holder = new UploadViewHolder(inflater.inflate(R.layout.activity_upload_item, viewGroup, false));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v);
                }
            });
            return holder;
        }
        return null;
    }

    //内容长度
    public int getContentItemCount() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (state == 0) {
            if (HeaderCount != 0 && position == 0) {
                return ITEM_TYPE_HEADER;
            } else {
                return ITEM_TYPE_CONTENT;
            }
        } else {
            return ITEM_TYPE_CONTENT;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof UploadHeaderViewHolder) {
            ((UploadHeaderViewHolder) holder).addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tag == 0) {
                        if (TaskContentActivity.imageNum1 < 5) {
                            systemUtils.BuildCameraDialog(activity).show();
                        } else {
                            SystemUtils.show(context, "最多上传5张图片");
                        }
                    } else if (tag == 1) {
                        if (TaskContentActivity.imageNum2 < 5) {
                            systemUtils.BuildCameraDialog(activity).show();
                        } else {
                            SystemUtils.show(context, "最多上传5张图片");
                        }
                    } else if (tag == 2) {
                        if (TaskContentActivity.imageNum3 < 5) {
                            systemUtils.BuildCameraDialog(activity).show();
                        } else {
                            SystemUtils.show(context, "最多上传5张图片");
                        }
                    } else if (tag == 3) {
                        if (TaskContentActivity.imageNum4 < 5) {
                            systemUtils.BuildCameraDialog(activity).show();
                        } else {
                            SystemUtils.show(context, "最多上传5张图片");
                        }
                    }
                }
            });
        } else if (holder instanceof UploadViewHolder) {
            Glide.with(context).load(lists.get(i - HeaderCount)).placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(((UploadViewHolder) holder).taskImage);
            ((UploadViewHolder) holder).taskImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (state == 0) {
                        Intent intent = new Intent(context, PhotoActivity.class);
                        intent.putExtra("url", lists.get(i - HeaderCount));
                        intent.putExtra("delete", "1");
                        intent.putExtra("position", i - HeaderCount);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, PhotoActivity.class);
                        intent.putExtra("url", lists.get(i - HeaderCount));
                        intent.putExtra("delete", "0");
                        intent.putExtra("position", i - HeaderCount);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size() + HeaderCount;
    }
}
