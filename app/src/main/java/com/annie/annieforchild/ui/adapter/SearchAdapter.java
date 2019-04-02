package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.search.BookClassify;
import com.annie.annieforchild.bean.search.Books;
import com.annie.annieforchild.ui.adapter.viewHolder.SearchViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.util.List;

/**
 * Created by wanglei on 2018/5/4.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private Context context;
    private List<Books> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public SearchAdapter(Context context, List<Books> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
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
    public void onBindViewHolder(SearchViewHolder holder, int i) {
        holder.textView.setText(lists.get(i).getBookName());

        Glide.with(context).load(lists.get(i).getBookImageUrl()).error(R.drawable.image_error).into(holder.imageView);
        if (lists.get(i).getAnimationUrl() != null) {
            holder.grindTag.setVisibility(View.GONE);
            holder.readingTag.setVisibility(View.GONE);
            holder.speakingTag.setVisibility(View.GONE);
            holder.tag.setVisibility(View.GONE);
            if (lists.get(i).getJurisdiction() == 0) {
                holder.lock.setVisibility(View.VISIBLE);
                holder.lock.setImageResource(R.drawable.icon_lock_book_f);
            } else {
                holder.lock.setVisibility(View.GONE);
            }
        } else {
            holder.tag.setVisibility(View.VISIBLE);
            if (lists.get(i).getIsmoerduo() == 0) {
                holder.grindTag.setVisibility(View.GONE);
            } else {
                holder.grindTag.setVisibility(View.VISIBLE);
            }
            if (lists.get(i).getIsyuedu() == 0) {
                holder.readingTag.setVisibility(View.GONE);
            } else {
                holder.readingTag.setVisibility(View.VISIBLE);
            }
            if (lists.get(i).getIskouyu() == 0) {
                holder.speakingTag.setVisibility(View.GONE);
            } else {
                holder.speakingTag.setVisibility(View.VISIBLE);
            }
            if (lists.get(i).getJurisdiction() == 0) {
                holder.lock.setVisibility(View.VISIBLE);
                if (lists.get(i).getIsusenectar() == 0) {
                    holder.lock.setImageResource(R.drawable.icon_lock_book_f);
                } else {
                    holder.lock.setImageResource(R.drawable.icon_lock_book_t);
                }
            } else {
                holder.lock.setVisibility(View.GONE);
            }
        }
        if (lists.get(i).getTag() == null || lists.get(i).getTag().trim().length() == 0) {
            holder.tag.setVisibility(View.GONE);
        } else {
            holder.tag.setVisibility(View.VISIBLE);
            holder.tag.setText(lists.get(i).getTag());
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

}
