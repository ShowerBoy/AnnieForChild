package com.annie.annieforchild.ui.activity.grindEar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.MeiriyiAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by wanglei on 2018/6/12.
 */

public class MeiriyiActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private RecyclerView meiriyiRecycler;
    private MeiriyiAdapter adapter;
    private TextView title;
    private Intent intent;
    private List<Song> lists;
    private String titleText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meiriyi;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.meiriyi_back);
        title = findViewById(R.id.meiriyi_title);
        meiriyiRecycler = findViewById(R.id.meiriyi_recycler);
        back.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        meiriyiRecycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            titleText = bundle.getString("title");
            title.setText(titleText);
            lists.addAll((List<Song>) bundle.getSerializable("list"));
        }
        adapter = new MeiriyiAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = meiriyiRecycler.getChildAdapterPosition(view);
                Intent intent = new Intent(MeiriyiActivity.this, PracticeActivity.class);
                intent.putExtra("song", lists.get(position));
                if (titleText.equals("每日一诗")) {
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 0);
                    intent.putExtra("audioSource", 2);
                } else if (titleText.equals("每日一歌")) {
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 0);
                    intent.putExtra("audioSource", 1);
                } else {
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 1);
                    intent.putExtra("audioSource", 5);
                }
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        meiriyiRecycler.setAdapter(adapter);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meiriyi_back:
                finish();
                break;
        }
    }
}
