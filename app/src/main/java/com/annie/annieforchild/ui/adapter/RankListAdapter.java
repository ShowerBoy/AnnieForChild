package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.serializer.ListSerializer;
import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.rank.Rank;
import com.annie.annieforchild.bean.rank.RankList;
import com.annie.annieforchild.ui.activity.mains.RankingActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.RankListViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/5/14.
 */

public class RankListAdapter extends RecyclerView.Adapter<RankListViewHolder> {
    private Context context;
    private List<RankList> list;
    private LayoutInflater inflater;

    public RankListAdapter(Context context, List<RankList> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RankListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RankListViewHolder holder;
        View view = inflater.inflate(R.layout.activity_rank_item, viewGroup, false);
        holder = new RankListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RankListViewHolder rankListViewHolder, int i) {
        if (i == 0) {
            rankListViewHolder.icon.setImageResource(R.drawable.icon_ranking_earphone);
            rankListViewHolder.title.setText("磨耳朵排行榜");
            if (list.get(0).getListeningList() != null && list.get(0).getListeningList().size() != 0) {
                List<Rank> lists = list.get(0).getListeningList();
                rankListViewHolder.linear.setVisibility(View.VISIBLE);
                rankListViewHolder.relative.setVisibility(View.GONE);
                if (lists.size() == 1) {
                    rankListViewHolder.gold.setVisibility(View.VISIBLE);
                    rankListViewHolder.silver.setVisibility(View.GONE);
                    rankListViewHolder.bronze.setVisibility(View.GONE);
                    rankListViewHolder.firstLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.secondLike.setVisibility(View.GONE);
                    rankListViewHolder.thirdLike.setVisibility(View.GONE);
                } else if (lists.size() == 2) {
                    rankListViewHolder.gold.setVisibility(View.VISIBLE);
                    rankListViewHolder.silver.setVisibility(View.VISIBLE);
                    rankListViewHolder.bronze.setVisibility(View.GONE);
                    rankListViewHolder.firstLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.secondLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.thirdLike.setVisibility(View.GONE);
                } else if (lists.size() == 3) {
                    rankListViewHolder.gold.setVisibility(View.VISIBLE);
                    rankListViewHolder.silver.setVisibility(View.VISIBLE);
                    rankListViewHolder.bronze.setVisibility(View.VISIBLE);
                    rankListViewHolder.firstLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.secondLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.thirdLike.setVisibility(View.VISIBLE);
                }
                for (int j = 0; j < lists.size(); j++) {
                    if (j == 0) {
                        Glide.with(context).load(lists.get(j).getAvatar()).into(rankListViewHolder.first);
                        rankListViewHolder.firstName.setText(lists.get(j).getName());
                        int min = Integer.parseInt(lists.get(j).getDuration()) / 60;
                        int hour = 0;
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (hour == 0) {
                            rankListViewHolder.firstTime.setText(min + "分钟");
                        } else {
                            rankListViewHolder.firstTime.setText(hour + "小时" + min + "分钟");
                        }

                        if (lists.get(j).getIsLiked() == 0) {
                            rankListViewHolder.firstLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
//                            rankListViewHolder.firstLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
                            rankListViewHolder.firstLike.setText(lists.get(j).getLikeCount() + "");
                        } else {
                            rankListViewHolder.firstLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
//                            rankListViewHolder.firstLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
                            rankListViewHolder.firstLike.setText(lists.get(j).getLikeCount() + "");
                        }
                    } else if (j == 1) {
                        Glide.with(context).load(lists.get(j).getAvatar()).into(rankListViewHolder.second);
                        rankListViewHolder.secondName.setText(lists.get(j).getName());
                        int min = Integer.parseInt(lists.get(j).getDuration()) / 60;
                        int hour = 0;
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (hour == 0) {
                            rankListViewHolder.secondTime.setText(min + "分钟");
                        } else {
                            rankListViewHolder.secondTime.setText(hour + "小时" + min + "分钟");
                        }
                        if (lists.get(j).getIsLiked() == 0) {
                            rankListViewHolder.secondLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
//                            rankListViewHolder.secondLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
                            rankListViewHolder.secondLike.setText(lists.get(j).getLikeCount() + "");
                        } else {
                            rankListViewHolder.secondLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
//                            rankListViewHolder.secondLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
                            rankListViewHolder.secondLike.setText(lists.get(j).getLikeCount() + "");
                        }
                    } else if (j == 2) {
                        Glide.with(context).load(lists.get(j).getAvatar()).into(rankListViewHolder.third);
                        rankListViewHolder.thirdName.setText(lists.get(j).getName());
                        int min = Integer.parseInt(lists.get(j).getDuration()) / 60;
                        int hour = 0;
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (hour == 0) {
                            rankListViewHolder.thirdTime.setText(min + "分钟");
                        } else {
                            rankListViewHolder.thirdTime.setText(hour + "小时" + min + "分钟");
                        }
                        if (lists.get(j).getIsLiked() == 0) {
                            rankListViewHolder.thirdLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
//                            rankListViewHolder.thirdLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
                            rankListViewHolder.thirdLike.setText(lists.get(j).getLikeCount() + "");
                        } else {
                            rankListViewHolder.thirdLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
//                            rankListViewHolder.thirdLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
                            rankListViewHolder.thirdLike.setText(lists.get(j).getLikeCount() + "");
                        }
                    }
                }
            } else {
                rankListViewHolder.linear.setVisibility(View.GONE);
                rankListViewHolder.relative.setVisibility(View.VISIBLE);
            }
        } else if (i == 1) {
            rankListViewHolder.icon.setImageResource(R.drawable.icon_ranking_book);
            rankListViewHolder.title.setText("阅读排行榜");
            if (list.get(0).getReadingList() != null && list.get(0).getReadingList().size() != 0) {
                List<Rank> lists = list.get(0).getReadingList();
                rankListViewHolder.linear.setVisibility(View.VISIBLE);
                rankListViewHolder.relative.setVisibility(View.GONE);
                if (lists.size() == 1) {
                    rankListViewHolder.gold.setVisibility(View.VISIBLE);
                    rankListViewHolder.silver.setVisibility(View.GONE);
                    rankListViewHolder.bronze.setVisibility(View.GONE);
                    rankListViewHolder.firstLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.secondLike.setVisibility(View.GONE);
                    rankListViewHolder.thirdLike.setVisibility(View.GONE);
                } else if (lists.size() == 2) {
                    rankListViewHolder.gold.setVisibility(View.VISIBLE);
                    rankListViewHolder.silver.setVisibility(View.VISIBLE);
                    rankListViewHolder.bronze.setVisibility(View.GONE);
                    rankListViewHolder.firstLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.secondLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.thirdLike.setVisibility(View.GONE);
                } else if (lists.size() == 3) {
                    rankListViewHolder.gold.setVisibility(View.VISIBLE);
                    rankListViewHolder.silver.setVisibility(View.VISIBLE);
                    rankListViewHolder.bronze.setVisibility(View.VISIBLE);
                    rankListViewHolder.firstLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.secondLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.thirdLike.setVisibility(View.VISIBLE);
                }
                for (int j = 0; j < lists.size(); j++) {
                    if (j == 0) {
                        Glide.with(context).load(lists.get(j).getAvatar()).into(rankListViewHolder.first);
                        rankListViewHolder.firstName.setText(lists.get(j).getName());
                        int min = Integer.parseInt(lists.get(j).getDuration()) / 60;
                        int hour = 0;
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (hour == 0) {
                            rankListViewHolder.firstTime.setText(min + "分钟");
                        } else {
                            rankListViewHolder.firstTime.setText(hour + "小时" + min + "分钟");
                        }
                        if (lists.get(j).getIsLiked() == 0) {
                            rankListViewHolder.firstLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
//                            rankListViewHolder.firstLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
                            rankListViewHolder.firstLike.setText(lists.get(j).getLikeCount() + "");
                        } else {
                            rankListViewHolder.firstLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
//                            rankListViewHolder.firstLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
                            rankListViewHolder.firstLike.setText(lists.get(j).getLikeCount() + "");
                        }
                    } else if (j == 1) {
                        Glide.with(context).load(lists.get(j).getAvatar()).into(rankListViewHolder.second);
                        rankListViewHolder.secondName.setText(lists.get(j).getName());
                        int min = Integer.parseInt(lists.get(j).getDuration()) / 60;
                        int hour = 0;
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (hour == 0) {
                            rankListViewHolder.secondTime.setText(min + "分钟");
                        } else {
                            rankListViewHolder.secondTime.setText(hour + "小时" + min + "分钟");
                        }
                        if (lists.get(j).getIsLiked() == 0) {
                            rankListViewHolder.secondLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
//                            rankListViewHolder.secondLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
                            rankListViewHolder.secondLike.setText(lists.get(j).getLikeCount() + "");
                        } else {
                            rankListViewHolder.secondLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
//                            rankListViewHolder.secondLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
                            rankListViewHolder.secondLike.setText(lists.get(j).getLikeCount() + "");
                        }
                    } else if (j == 2) {
                        Glide.with(context).load(lists.get(j).getAvatar()).into(rankListViewHolder.third);
                        rankListViewHolder.thirdName.setText(lists.get(j).getName());
                        int min = Integer.parseInt(lists.get(j).getDuration()) / 60;
                        int hour = 0;
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (hour == 0) {
                            rankListViewHolder.thirdTime.setText(min + "分钟");
                        } else {
                            rankListViewHolder.thirdTime.setText(hour + "小时" + min + "分钟");
                        }
                        if (lists.get(j).getIsLiked() == 0) {
                            rankListViewHolder.thirdLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
//                            rankListViewHolder.thirdLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
                            rankListViewHolder.thirdLike.setText(lists.get(j).getLikeCount() + "");
                        } else {
                            rankListViewHolder.thirdLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
//                            rankListViewHolder.thirdLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
                            rankListViewHolder.thirdLike.setText(lists.get(j).getLikeCount() + "");
                        }
                    }
                }
            } else {
                rankListViewHolder.linear.setVisibility(View.GONE);
                rankListViewHolder.relative.setVisibility(View.VISIBLE);
            }
        } else {
            rankListViewHolder.icon.setImageResource(R.drawable.icon_ranking_microphone);
            rankListViewHolder.title.setText("口语排行榜");
            if (list.get(0).getSpeakingList() != null && list.get(0).getSpeakingList().size() != 0) {
                List<Rank> lists = list.get(0).getSpeakingList();
                rankListViewHolder.linear.setVisibility(View.VISIBLE);
                rankListViewHolder.relative.setVisibility(View.GONE);
                if (lists.size() == 1) {
                    rankListViewHolder.gold.setVisibility(View.VISIBLE);
                    rankListViewHolder.silver.setVisibility(View.GONE);
                    rankListViewHolder.bronze.setVisibility(View.GONE);
                    rankListViewHolder.firstLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.secondLike.setVisibility(View.GONE);
                    rankListViewHolder.thirdLike.setVisibility(View.GONE);
                } else if (lists.size() == 2) {
                    rankListViewHolder.gold.setVisibility(View.VISIBLE);
                    rankListViewHolder.silver.setVisibility(View.VISIBLE);
                    rankListViewHolder.bronze.setVisibility(View.GONE);
                    rankListViewHolder.firstLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.secondLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.thirdLike.setVisibility(View.GONE);
                } else if (lists.size() == 3) {
                    rankListViewHolder.gold.setVisibility(View.VISIBLE);
                    rankListViewHolder.silver.setVisibility(View.VISIBLE);
                    rankListViewHolder.bronze.setVisibility(View.VISIBLE);
                    rankListViewHolder.firstLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.secondLike.setVisibility(View.VISIBLE);
                    rankListViewHolder.thirdLike.setVisibility(View.VISIBLE);
                }
                for (int j = 0; j < lists.size(); j++) {
                    if (j == 0) {
                        Glide.with(context).load(lists.get(j).getAvatar()).into(rankListViewHolder.first);
                        rankListViewHolder.firstName.setText(lists.get(j).getName());
                        int min = Integer.parseInt(lists.get(j).getDuration()) / 60;
                        int hour = 0;
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (hour == 0) {
                            rankListViewHolder.firstTime.setText(min + "分钟");
                        } else {
                            rankListViewHolder.firstTime.setText(hour + "小时" + min + "分钟");
                        }
                        if (lists.get(j).getIsLiked() == 0) {
                            rankListViewHolder.firstLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
//                            rankListViewHolder.firstLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
                            rankListViewHolder.firstLike.setText(lists.get(j).getLikeCount() + "");
                        } else {
                            rankListViewHolder.firstLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
//                            rankListViewHolder.firstLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
                            rankListViewHolder.firstLike.setText(lists.get(j).getLikeCount() + "");
                        }
                    } else if (j == 1) {
                        Glide.with(context).load(lists.get(j).getAvatar()).into(rankListViewHolder.second);
                        rankListViewHolder.secondName.setText(lists.get(j).getName());
                        int min = Integer.parseInt(lists.get(j).getDuration()) / 60;
                        int hour = 0;
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (hour == 0) {
                            rankListViewHolder.secondTime.setText(min + "分钟");
                        } else {
                            rankListViewHolder.secondTime.setText(hour + "小时" + min + "分钟");
                        }
                        if (lists.get(j).getIsLiked() == 0) {
                            rankListViewHolder.secondLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
//                            rankListViewHolder.secondLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
                            rankListViewHolder.secondLike.setText(lists.get(j).getLikeCount() + "");
                        } else {
                            rankListViewHolder.secondLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
//                            rankListViewHolder.secondLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
                            rankListViewHolder.secondLike.setText(lists.get(j).getLikeCount() + "");
                        }
                    } else if (j == 2) {
                        Glide.with(context).load(lists.get(j).getAvatar()).into(rankListViewHolder.third);
                        rankListViewHolder.thirdName.setText(lists.get(j).getName());
                        int min = Integer.parseInt(lists.get(j).getDuration()) / 60;
                        int hour = 0;
                        if (min > 60) {
                            hour = min / 60;
                            min = min % 60;
                        }
                        if (hour == 0) {
                            rankListViewHolder.thirdTime.setText(min + "分钟");
                        } else {
                            rankListViewHolder.thirdTime.setText(hour + "小时" + min + "分钟");
                        }
                        if (lists.get(j).getIsLiked() == 0) {
                            rankListViewHolder.thirdLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
//                            rankListViewHolder.thirdLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null, null);
                            rankListViewHolder.thirdLike.setText(lists.get(j).getLikeCount() + "");
                        } else {
                            rankListViewHolder.thirdLike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
//                            rankListViewHolder.thirdLike.setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null, null);
                            rankListViewHolder.thirdLike.setText(lists.get(j).getLikeCount() + "");
                        }
                    }
                }
            } else {
                rankListViewHolder.linear.setVisibility(View.GONE);
                rankListViewHolder.relative.setVisibility(View.VISIBLE);
            }
        }
        rankListViewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    Intent intent = new Intent(context, RankingActivity.class);
                    intent.putExtra("resourceType", 1);
                    context.startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(context, RankingActivity.class);
                    intent.putExtra("resourceType", 2);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, RankingActivity.class);
                    intent.putExtra("resourceType", 3);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
