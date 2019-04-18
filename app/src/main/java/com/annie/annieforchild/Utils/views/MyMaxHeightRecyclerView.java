package com.annie.annieforchild.Utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/4/18.
 */

public class MyMaxHeightRecyclerView extends RecyclerView{
    private int mMaxHeight;

    public MyMaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MyMaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public MyMaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView);
        mMaxHeight = arr.getLayoutDimension(R.styleable.MaxHeightRecyclerView_maxHeight, mMaxHeight);
        arr.recycle();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (mMaxHeight > 0) {
            heightSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}
