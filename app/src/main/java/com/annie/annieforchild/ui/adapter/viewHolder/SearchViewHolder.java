package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/5/4.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView, lock;
    public TextView textView, grindTag, readingTag, speakingTag, tag;

    public SearchViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.search_book_image);
        textView = itemView.findViewById(R.id.search_book_name);
        grindTag = itemView.findViewById(R.id.grind_tag);
        readingTag = itemView.findViewById(R.id.reading_tag);
        speakingTag = itemView.findViewById(R.id.speaking_tag);
        tag = itemView.findViewById(R.id.search_book_tag);
        lock = itemView.findViewById(R.id.search_lock);
    }
}
