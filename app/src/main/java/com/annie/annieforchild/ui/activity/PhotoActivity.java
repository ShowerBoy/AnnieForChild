package com.annie.annieforchild.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.photoview.PhotoView;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

//import com.github.chrisbanes.photoview.PhotoView;
//import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * Created by wanglei on 2018/8/28.
 */

public class PhotoActivity extends BaseActivity implements View.OnClickListener {
    private PhotoView photoView;
    private ImageView back, delete;
    private Intent intent;
    private String url, isDelete;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initView() {
        photoView = findViewById(R.id.photoView);
        delete = findViewById(R.id.photo_delete);
        back = findViewById(R.id.photo_back);
        intent = getIntent();
        url = intent.getStringExtra("url");
        isDelete = intent.getStringExtra("delete");
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        if (isDelete.equals("0")) {
            delete.setVisibility(View.GONE);
        } else {
            delete.setVisibility(View.VISIBLE);
            position = intent.getIntExtra("position", 0);
        }
    }

    @Override
    protected void initData() {
        if (url.equals("0")) {
            Glide.with(this).load(R.drawable.route_pic_03).into(photoView);
        } else {
            Glide.with(this).load(url).into(photoView);
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_back:
                finish();
                break;
            case R.id.photo_delete:
                SystemUtils.GeneralDialog(this, "删除")
                        .setMessage("确定取消选择该图片吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /**
                                 * {@link com.annie.annieforchild.ui.activity.lesson.TaskDetailsActivity#onMainEventThread(JTMessage)}
                                 */
                                JTMessage message = new JTMessage();
                                message.what = MethodCode.EVENT_SELECT;
                                message.obj = position;
                                EventBus.getDefault().post(message);
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }
}
