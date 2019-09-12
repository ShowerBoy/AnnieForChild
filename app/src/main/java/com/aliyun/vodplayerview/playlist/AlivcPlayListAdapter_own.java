package com.aliyun.vodplayerview.playlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @author Mulberry
 *         create on 2018/5/17.
 */

public class AlivcPlayListAdapter_own extends RecyclerView.Adapter<AlivcPlayListAdapter_own.ViewHolder> {
    List<VideoList> videoLists;
    Context context;
    int isselect;
    int type;

    public AlivcPlayListAdapter_own(Context context, List<VideoList> videoLists,int type) {
        this.context = context;
        this.videoLists = videoLists;
        this.type=type;

    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImage;
        TextView title;
        TextView tvVideoDuration;
        LinearLayout alivcVideoInfoItemLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            alivcVideoInfoItemLayout = (LinearLayout)itemView.findViewById(R.id.alivc_video_info_item_layout);
            coverImage = (ImageView)itemView.findViewById(R.id.iv_video_cover);
            title = (TextView)itemView.findViewById(R.id.tv_video_title);
            tvVideoDuration = (TextView)itemView.findViewById(R.id.tv_video_duration);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alivc_play_list_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (videoLists.size() > 0) {
            //isselect为1表示高亮显示，正在播放。
           if(videoLists.size()>isselect){
               if(position==isselect){
                   holder.title.setTextColor(context.getResources().getColor(R.color.blue2));
               }else{
                   //type为1表示视频播放器，type为2表示投屏列表。
                   if(type==1){
                       holder.title.setTextColor(context.getResources().getColor(R.color.alivc_common_font_white_light));
                   }else{
                       holder.title.setTextColor(context.getResources().getColor(R.color.alivc_common_font_gray_333));
                   }
               }
           }
                holder.title.setText(videoLists.get(position).getTitle());
//                double dTime = Double.parseDouble(video.getDuration().toString());
//                holder.tvVideoDuration.setText(videoLists.get(position).get);
            Glide.with(context).load(videoLists.get(position).getPicurl()).error(R.drawable.image_error).into(holder.coverImage);

        }
        holder.alivcVideoInfoItemLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onVideoListItemClick != null) {
                    onVideoListItemClick.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoLists.size();
    }

    private OnVideoListItemClick onVideoListItemClick;

    public void setOnVideoListItemClick(
        OnVideoListItemClick onVideoListItemClick) {
        this.onVideoListItemClick = onVideoListItemClick;
    }

    public void setSelectVideo(int se){
        isselect=se;
    }

    public interface OnVideoListItemClick {
        void onItemClick(int position);
    }
}
