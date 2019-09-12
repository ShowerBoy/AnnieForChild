package com.lebo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.netexpclass.VideoDefiniList;

import java.util.List;

/**
 */
public class QualityAdapter extends RecyclerView.Adapter<QualityAdapter.RecyclerHolder> {

    private Context mContext;
    private List<VideoDefiniList> mDatas;
    private LayoutInflater mInflater;
    private String currentQuality;

    public QualityAdapter(Context context, List<VideoDefiniList> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = list;
    }


    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.quality_item, parent, false);
        return new RecyclerHolder(itemView);
    }
    public void setcurrentQuality(String s){
        currentQuality=s;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        VideoDefiniList info = mDatas.get(position);

        if (mDatas != null) {
            VideoDefiniList trackInfo = mDatas.get(position);
            String quality = trackInfo.getTitle();
            holder.textView.setText(trackInfo.getTitle());
            //默认白色，当前清晰度为主题色。
            if (quality.equals(currentQuality)) {
                holder.textView.setTextColor(mContext.getResources().getColor(R.color.text_orange));
            } else {
                holder.textView.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick1 != null) {
                    onItemClick1.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    public void clearDatas() {
            mDatas.clear();
            notifyDataSetChanged();

    }


    class RecyclerHolder extends RecyclerView.ViewHolder {

        TextView textView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textview);
        }

    }
    private OnItemClick onItemClick1;

    public void setOnItemClick(
            OnItemClick onItemClick) {
        this.onItemClick1 = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(int position);
    }

}
