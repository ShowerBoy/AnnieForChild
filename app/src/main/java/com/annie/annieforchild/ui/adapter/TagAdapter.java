package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.SearchTag;
import com.annie.annieforchild.bean.Tags;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.ui.adapter.viewHolder.TagViewHolder;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * Created by wanglei on 2018/9/14.
 */

public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {
    private Context context;
    private List<SearchTag> lists;
    private LoginPresenter presenter;
    private LayoutInflater inflater;

    public TagAdapter(Context context, LoginPresenter presenter, List<SearchTag> lists) {
        this.context = context;
        this.presenter = presenter;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        TagViewHolder holder;
        holder = new TagViewHolder(inflater.inflate(R.layout.activity_tag_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(TagViewHolder tagViewHolder, int i) {
        tagViewHolder.tag.setText(lists.get(i).getName());
        tagViewHolder.flexboxLayout.removeAllViews();
        for (int j = 0; j < lists.get(i).getList().size(); j++) {
            tagViewHolder.flexboxLayout.addView(createFlexItem(i, j, lists.get(i).getList().get(j)));
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private TextView createFlexItem(int i, int j, Tags tags) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(tags.getName());
        textView.setTextSize(14);
        if (tags.isSelect()) {
            textView.setBackgroundResource(R.drawable.sing_button);
            textView.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            textView.setBackgroundResource(R.drawable.relative_gray_back);
            textView.setTextColor(context.getResources().getColor(R.color.text_color));
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                presenter.getTagBook(tags.getTid());
                if (lists.get(i).getList().get(j).isSelect()) {
                    lists.get(i).getList().get(j).setSelect(false);
                } else {
                    lists.get(i).getList().get(j).setSelect(true);
                }
                presenter.addTags(i, tags);
                notifyDataSetChanged();
            }
        });
        int padding = SystemUtils.dpToPixel(context, 4);
        int paddingLeftAndRight = SystemUtils.dpToPixel(context, 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = SystemUtils.dpToPixel(context, 6);
        int marginTop = SystemUtils.dpToPixel(context, 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }
}
