package com.annie.annieforchild.ui.activity.mains;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.rank.Hpbean;
import com.annie.annieforchild.bean.rank.Production;
import com.annie.annieforchild.bean.rank.ProductionBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.ProductionAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人首页
 * Created by wanglei on 2019/1/3.
 */

public class HomePageActivity extends BaseActivity implements SongView, OnCheckDoubleClick {
    private NestedScrollView nestedScrollView;
    private LinearLayout levelLayout;
    private ImageView back, likeIcon, empty;
    private TextView name, age, nectar, like, level, school, totalTime, grindearTime, readingTime, speakingTime;
    private CircleImageView headpic;
    private XRecyclerView recycler;
    private GrindEarPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private CheckDoubleClickListener listener;
    private ProductionAdapter adapter;
    private Intent intent;
    private Hpbean hpbean;
    private ProductionBean productionBean;
    private List<Production> lists;
    private String otherUsername;
    private XRecyclerView.LoadingListener loadingListener;
    private int page = 1, totalPage;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_homepage;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.homepage_back);
        likeIcon = findViewById(R.id.homepage_like_icon);
        name = findViewById(R.id.other_name);
        age = findViewById(R.id.other_age);
        like = findViewById(R.id.homepage_like);
        nectar = findViewById(R.id.homepage_nectar);
        headpic = findViewById(R.id.other_image);
        level = findViewById(R.id.homepage_level);
        school = findViewById(R.id.homepage_school);
        totalTime = findViewById(R.id.homepage_total_time);
        grindearTime = findViewById(R.id.homepage_grindear_time);
        readingTime = findViewById(R.id.homepage_reading_time);
        speakingTime = findViewById(R.id.homepage_speaking_time);
        recycler = findViewById(R.id.homepage_recycler);
        levelLayout = findViewById(R.id.homepage_level_layout);
        nestedScrollView = findViewById(R.id.homepage_nested);
        empty = findViewById(R.id.homepage_empty);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        likeIcon.setOnClickListener(listener);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.setNestedScrollingEnabled(false);
        recycler.setLoadingMoreProgressStyle(10);
//        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setPullRefreshEnabled(false);
        recycler.setLoadingMoreEnabled(true);
        loadingListener = new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                if (page > totalPage) {
                    page--;
                    showInfo("没有更多了");
                    recycler.loadMoreComplete();
                } else {
                    presenter.getProdutionList(page, otherUsername);
                }
            }
        };
        recycler.setLoadingListener(loadingListener);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    loadingListener.onLoadMore();
                }
            }
        });


    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        intent = getIntent();
        if (intent != null) {
            otherUsername = intent.getStringExtra("username");
        }
        adapter = new ProductionAdapter(this, lists, presenter, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                Intent intent = new Intent(HomePageActivity.this, PracticeActivity.class);
                Song song = new Song();
                song.setBookId(lists.get(position - 1).getBookId());
                song.setBookImageUrl(lists.get(position - 1).getBookImageUrl());
                song.setBookName(lists.get(position - 1).getBookName());
                intent.putExtra("song", song);
                intent.putExtra("type", 0);
                intent.putExtra("audioType", 1);
                intent.putExtra("audioSource", 0);
                intent.putExtra("collectType", 2);
                intent.putExtra("bookType", 1);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
        presenter.getHomepage(otherUsername);
        presenter.getProdutionList(page, otherUsername);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.homepage_back:
                finish();
                break;
            case R.id.homepage_like_icon:
                //点赞
                if (adapter.isPlay()) {
                    adapter.stopMedia();
//                    return;
                }
                if (hpbean != null) {
                    if (hpbean.getIsLike() == 0) {
                        presenter.likeStudent(otherUsername);
                    } else {
                        presenter.cancelLikeStudent(otherUsername);
                    }
                }
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETHOMEPAGE) {
            hpbean = (Hpbean) message.obj;
            refresh();
        } else if (message.what == MethodCode.EVENT_GETPRODUCTIONLIST) {
            recycler.loadMoreComplete();
            productionBean = (ProductionBean) message.obj;
            totalPage = Integer.parseInt(productionBean.getTotalPage());
            if (productionBean.getProduction() != null && productionBean.getProduction().size() != 0) {
                recycler.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                lists.addAll(productionBean.getProduction());
                adapter.notifyDataSetChanged();
            } else {
                recycler.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
        } else if (message.what == MethodCode.EVENT_ADDLIKES) {
            showInfo((String) message.obj);
            lists.get(adapter.getPosition()).setIsLike(1);
            lists.get(adapter.getPosition()).setLikeCount(Integer.parseInt(lists.get(adapter.getPosition()).getLikeCount()) + 1 + "");
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_CANCELLIKES) {
            showInfo((String) message.obj);
            lists.get(adapter.getPosition()).setIsLike(0);
            lists.get(adapter.getPosition()).setLikeCount(Integer.parseInt(lists.get(adapter.getPosition()).getLikeCount()) - 1 + "");
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_LIKESTUDENT) {
//            showInfo((String) message.obj);
            presenter.getHomepage(otherUsername);
        } else if (message.what == MethodCode.EVENT_CANCELLIKESTUDENT) {
//            showInfo((String) message.obj);
            presenter.getHomepage(otherUsername);
        }
    }

    private void refresh() {
        if (hpbean != null) {
            name.setText(hpbean.getName());
            age.setText(hpbean.getAge() + "岁");
            level.setText(hpbean.getLevel());
            like.setText(hpbean.getLikeCount());
            if (hpbean.getIsLike() == 0) {
                likeIcon.setImageResource(R.drawable.icon_homepage_like_f);
            } else {
                likeIcon.setImageResource(R.drawable.icon_homepage_like_t);
            }
            nectar.setText(hpbean.getNectar() != null ? hpbean.getNectar() : "0");
            Glide.with(this).load(hpbean.getAvatar()).error(R.drawable.icon_system_photo).into(headpic);
            school.setText(hpbean.getSchool());

            totalTime.setText(SystemUtils.secToHour(hpbean.getTotalTime()));
            grindearTime.setText(SystemUtils.secToHour(hpbean.getGrindearTime()));
            readingTime.setText(SystemUtils.secToHour(hpbean.getReadingTime()));
            speakingTime.setText(SystemUtils.secToHour(hpbean.getSpeakingTime()));

            levelLayout.removeAllViews();
            int experience = Integer.parseInt(hpbean.getExperience() != null ? hpbean.getExperience() : "0");

            if (experience < 500) {
                getLevel(0, 0, 0, 0);
            } else if (experience < 1200) {
                getLevel(0, 0, 0, 1);
            } else if (experience < 2100) {
                getLevel(0, 0, 0, 2);
            } else if (experience < 3200) {
                getLevel(0, 0, 0, 3);
            } else if (experience < 4700) {
                getLevel(0, 0, 1, 0);
            } else if (experience < 6600) {
                getLevel(0, 0, 1, 1);
            } else if (experience < 8900) {
                getLevel(0, 0, 1, 2);
            } else if (experience < 11600) {
                getLevel(0, 0, 1, 3);
            } else if (experience < 15100) {
                getLevel(0, 0, 2, 0);
            } else if (experience < 19400) {
                getLevel(0, 0, 2, 1);
            } else if (experience < 24500) {
                getLevel(0, 0, 2, 2);
            } else if (experience < 30400) {
                getLevel(0, 0, 2, 3);
            } else if (experience < 37900) {
                getLevel(0, 0, 3, 0);
            } else if (experience < 47000) {
                getLevel(0, 0, 3, 1);
            } else if (experience < 57700) {
                getLevel(0, 0, 3, 2);
            } else if (experience < 70000) {
                getLevel(0, 0, 3, 3);
            } else if (experience < 83900) {
                getLevel(0, 1, 0, 0);
            } else if (experience < 99400) {
                getLevel(0, 1, 0, 1);
            } else if (experience < 116500) {
                getLevel(0, 1, 0, 2);
            } else if (experience < 135200) {
                getLevel(0, 1, 0, 3);
            } else if (experience < 155500) {
                getLevel(0, 1, 1, 0);
            } else if (experience < 177400) {
                getLevel(0, 1, 1, 1);
            } else if (experience < 200900) {
                getLevel(0, 1, 1, 2);
            } else if (experience < 226000) {
                getLevel(0, 1, 1, 3);
            } else if (experience < 252700) {
                getLevel(0, 1, 2, 0);
            } else if (experience < 281000) {
                getLevel(0, 1, 2, 1);
            } else if (experience < 310900) {
                getLevel(0, 1, 2, 2);
            } else if (experience < 342400) {
                getLevel(0, 1, 2, 3);
            } else if (experience >= 342400) {
                getLevel(1, 0, 0, 0);
            }
        }
    }

    private void getLevel(int crown, int sun, int moon, int star) {
        if (crown != 0) {
            for (int i = 0; i < crown; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.icon_level_crown);
                levelLayout.addView(imageView);
            }
        }
        if (sun != 0) {
            for (int i = 0; i < sun; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.icon_level_sun);
                levelLayout.addView(imageView);
            }
        }
        if (moon != 0) {
            for (int i = 0; i < moon; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.icon_level_moon);
                levelLayout.addView(imageView);
            }
        }
        if (star != 0) {
            for (int i = 0; i < star; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.icon_level_star);
                levelLayout.addView(imageView);
            }
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.show();
            }
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (adapter != null) {
            adapter.stopMedia();
        }
        super.onDestroy();
    }
}
