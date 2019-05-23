package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.ChildViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class NetSuggestAdapter1 extends RecyclerView.Adapter{

    private final static int FOOT_COUNT = 1;

    private final static int TYPE_CONTENT = 1;
    private final static int TYPE_FOOTER = 2;

    private Context context;
    private List<String> lists,list_middle;
    private LayoutInflater inflater;
    private int width,heigth;
    private float density;

    public NetSuggestAdapter1(Context context, List<String> lists, List<String> list_middle) {
        this.context = context;
        this.lists = lists;
        this.list_middle=list_middle;
        inflater = LayoutInflater.from(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        heigth = dm.heightPixels;
        width = dm.widthPixels;

         density = dm.density;

    }

    public int getContentSize(){
        return lists.size();
    }


    public boolean isFoot(int position){
        return FOOT_COUNT != 0 && position == getContentSize();
    }

    @Override
    public int getItemViewType(int position) {
        int contentSize = getContentSize();
        if(FOOT_COUNT != 0 && position ==  contentSize){ // 尾部
            return TYPE_FOOTER;
        }else{
            return TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_CONTENT){
            View itemView = inflater.inflate(R.layout.activity_net_suggest_fragment_item,parent,false);
            return new ContentHolder(itemView);
        }else{
            View itemView =inflater.inflate(R.layout.activity_net_suggestfragment_item2,parent,false);
            return new FootHolder(itemView);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ContentHolder){ // 内容
            ContentHolder myHolder = (ContentHolder) holder;

            Glide.with(context).load(lists.get(position)).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int imageWidth = resource.getWidth();
                    int imageHeight = resource.getHeight();
                    int height = width * imageHeight / imageWidth;
                    ViewGroup.LayoutParams para =  myHolder.image.getLayoutParams();
                    para.height = height;
                    para.width = width;
                    myHolder.image.setImageBitmap(resource);
                }
            });

//            Glide.with(context).load(lists.get(position)).into(myHolder.image);

        }else{ // 尾部
            FootHolder footHolder=(FootHolder)holder;

            ViewGroup.LayoutParams linearParams =footHolder.viewpager_layout.getLayoutParams();
            linearParams.height = (int) (420*density);
            footHolder.viewpager_layout.setLayoutParams(linearParams);
            if(list_middle.size()<1){
                footHolder.viewpager_layout.setVisibility(View.GONE);
            }else{
                footHolder.viewpager_layout.setVisibility(View.VISIBLE);
                footHolder.viewPager.setOffscreenPageLimit(3);
                footHolder.viewPager.setPageMargin(40);
                pagerAdapter pageradapter=new pagerAdapter();
                footHolder.viewPager.setAdapter(pageradapter);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lists.size()  + FOOT_COUNT;
    }


    // 内容
    private class ContentHolder extends RecyclerView.ViewHolder{
        public ImageView image;

        public ContentHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.net_suggest_image);


        }
    }

    // 尾部
    private class FootHolder extends RecyclerView.ViewHolder{
        public ChildViewPager viewPager;
        public RelativeLayout viewpager_layout;
        public FootHolder(View itemView) {
            super(itemView);
            viewPager=itemView.findViewById(R.id.viewpager);
            viewpager_layout=itemView.findViewById(R.id.viewpager_layout);
        }
    }


    class pagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return list_middle.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(context).load(list_middle.get(position)).fitCenter().into(imageView);
            container.addView(imageView);
            //最后要返回的是控件本身
            return imageView;
        }
        //因为它默认是看三张图片，第四张图片的时候就会报错，还有就是不要返回父类的作用
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            //         super.destroyItem(container, position, object);
        }


    }
}