package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.adapter.viewHolder.MyFooterRecyclerViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.RecyclerFooterHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.RecyclerHeaderHolder;

/**
 * Created by WangLei on 2018/2/11 0011
 */

public class MyFooterRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    private Context context;
    private LayoutInflater inflater;
    public String[] texts = new String[]{};
    private int mHeaderCount = 0;//头部View个数
    private int mBottomCount = 1;//底部View个数

    public MyFooterRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //内容长度
    public int getContentItemCount() {
        return texts.length;
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new RecyclerHeaderHolder(inflater.inflate(R.layout.recyclerview_header, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new MyFooterRecyclerViewHolder(inflater.inflate(R.layout.myfooterrecycler_item, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new RecyclerFooterHolder(inflater.inflate(R.layout.recyclerview_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerHeaderHolder) {

        } else if (holder instanceof MyFooterRecyclerViewHolder) {
            ((MyFooterRecyclerViewHolder) holder).myfooterItemText.setText(texts[position - mHeaderCount]);
        } else if (holder instanceof RecyclerFooterHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }
}
