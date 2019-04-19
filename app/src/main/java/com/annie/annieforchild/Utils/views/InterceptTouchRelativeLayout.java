package com.annie.annieforchild.Utils.views;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by wanglei on 2019/4/19.
 */

public class InterceptTouchRelativeLayout extends RelativeLayout{

    public InterceptTouchRelativeLayout(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
