package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.search.BookClassify;
import com.annie.annieforchild.ui.adapter.viewHolder.SearchViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.util.List;

/**
 * Created by wanglei on 2018/5/4.
 */

public class SearchAdapter extends SimpleSectionedAdapter<SearchViewHolder> {
    private Context context;
    private List<BookClassify> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public SearchAdapter(Context context, List<BookClassify> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected int getSectionCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    protected int getItemCountForSection(int i) {
        return lists != null ? lists.get(i).getBook().size() : 0;
    }

    @Override
    protected SearchViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int i) {
        SearchViewHolder holder;
        View view = inflater.inflate(R.layout.activity_search_item, viewGroup, false);
        holder = new SearchViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(v);
                return true;
            }
        });
        return holder;
    }

    @Override
    protected void onBindItemViewHolder(SearchViewHolder holder, int i, int i1) {
        Glide.with(context).load(lists.get(i).getBook().get(i1).getBookImageUrl()).into(holder.imageView);
        holder.textView.setText(lists.get(i).getBook().get(i1).getBookName());
    }

    @Override
    protected String getSectionHeaderTitle(int i) {
        return lists != null ? lists.get(i).getClassifyName() : "";
    }
}
