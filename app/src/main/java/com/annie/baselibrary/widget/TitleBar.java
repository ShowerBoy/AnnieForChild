package com.annie.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.utils.UIUtils;

/**
 * Created by Mark.Han on 2017/9/11.
 */

public class TitleBar extends RelativeLayout {

    private ImageView iv_titlebar_left;
    private ImageView iv_titlebar_right;
    private TextView tv_titlebar_title;
    private RelativeLayout rl_titlebar_layout;
    private int text_color, bg_color, left_image, right_image;
    private int text_size = 12;//文字大小 默认为12sp;


    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        text_color = typedArray.getColor(R.styleable.TitleBar_title_text_color, Color.WHITE);
        bg_color = typedArray.getColor(R.styleable.TitleBar_title_bg, Color.BLUE);
        text_size = typedArray.getDimensionPixelSize(R.styleable.TitleBar_title_text_size, UIUtils.sp2px(context, text_size));
        left_image = typedArray.getResourceId(R.styleable.TitleBar_left_image, -1);
        right_image = typedArray.getResourceId(R.styleable.TitleBar_right_image, -1);
        typedArray.recycle();

        checkValues();
        initView(context);
    }

    /**
     * 检查传入的值是否完善
     */
    private void checkValues() {
        if (left_image == -1) {
            throw new IllegalStateException("您还没有设置标题左侧图片，请指定left_image的图标");
        }

        if (right_image == -1) {
            throw new IllegalStateException("您还没有设置标题右侧图片，请指定right_image的图标");
        }
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_customtitle, this, true);
        iv_titlebar_left = findViewById(R.id.iv_left);
        iv_titlebar_right = findViewById(R.id.iv_right);
        tv_titlebar_title = findViewById(R.id.tv_title);
        rl_titlebar_layout = findViewById(R.id.rl_titlebar_layout);


        iv_titlebar_left.setImageResource(left_image);
        iv_titlebar_right.setImageResource(right_image);
        rl_titlebar_layout.setBackgroundColor(bg_color);
        tv_titlebar_title.setTextColor(text_color);
        tv_titlebar_title.setTextSize(text_size);
    }

    public void setTitleText(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_titlebar_title.setText(title);
        }
    }

    public void setLeftListener(OnClickListener leftListener) {
        if (leftListener != null) {
            iv_titlebar_left.setOnClickListener(leftListener);
        }
    }

    public void setRightListener(OnClickListener rightListener) {
        if (rightListener != null) {
            iv_titlebar_right.setOnClickListener(rightListener);
        }
    }

}
