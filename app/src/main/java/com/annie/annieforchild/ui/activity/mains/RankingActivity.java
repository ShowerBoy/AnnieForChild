package com.annie.annieforchild.ui.activity.mains;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.rank.Rank;
import com.annie.annieforchild.bean.rank.RankList;
import com.annie.annieforchild.bean.rank.SquareRankList;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.RankingAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 排行榜界面
 * Created by wanglei on 2018/5/28.
 */

public class RankingActivity extends BaseActivity implements View.OnClickListener, SongView {
    private TextView title;
    private ImageView back;
    private CircleImageView headpic;
    private RelativeLayout myLayout;
    private TextView name, rank, address;
    private RecyclerView recycler;
    private Spinner rangeSpinner, timeSpinner;
    private GrindEarPresenter presenter;
    private List<String> rangeList, timeList;
    private Intent intent;
    private AlertHelper helper;
    private Dialog dialog;
    private RankingAdapter adapter;
    private SquareRankList squareRankList;
    private List<Rank> lists;
    private int resourceType, timeType, locationType;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ranking;
    }

    @Override
    protected void initView() {
        title = findViewById(R.id.rankingList_title);
        back = findViewById(R.id.rankingList_back);
        rangeSpinner = findViewById(R.id.range_spinner);
        timeSpinner = findViewById(R.id.time_spinner);
        recycler = findViewById(R.id.rankingList_recycler);
        headpic = findViewById(R.id.rankingList_headpic);
        name = findViewById(R.id.rankingList_name);
        rank = findViewById(R.id.rankingList_rank);
        address = findViewById(R.id.rankingList_address);
        myLayout = findViewById(R.id.my_ranklist_layout);
        back.setOnClickListener(this);
        myLayout.setOnClickListener(this);
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        adapter = new RankingAdapter(this, lists, presenter);
        recycler.setAdapter(adapter);
        squareRankList = new SquareRankList();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        intent = getIntent();
        resourceType = intent.getIntExtra("resourceType", 1);
        timeType = 1;
        locationType = 4;
        rangeList = new ArrayList<>();
        timeList = new ArrayList<>();
        timeList.add("本周");
        timeList.add("本月");
        timeList.add("本季");
        rangeList.add("全国");
        rangeList.add("全班");
        rangeList.add("全校");
        rangeList.add("全市");
        rangeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rangeList));
        timeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, timeList));
        rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (locationType != 4) {
                        locationType = 4;
                        presenter.getSquareRankList(resourceType, timeType, locationType);
                    }
                } else {
                    if (locationType != position) {
                        locationType = position;
                        presenter.getSquareRankList(resourceType, timeType, locationType);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (timeType != position + 1) {
                    timeType = position + 1;
                    presenter.getSquareRankList(resourceType, timeType, locationType);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        presenter.getSquareRankList(resourceType, timeType, locationType);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rankingList_back:
                finish();
                break;
            case R.id.my_ranklist_layout:
                Intent intent = new Intent(this, HomePageActivity.class);
                intent.putExtra("username", SystemUtils.defaultUsername);
                startActivity(intent);
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
        if (message.what == MethodCode.EVENT_GETSQUARERANKLIST) {
            squareRankList = (SquareRankList) message.obj;
            lists.clear();
            lists.addAll(squareRankList.getAllRankInfoList());
            fresh();
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_LIKESTUDENT) {
            showInfo((String) message.obj);
            presenter.getSquareRankList(resourceType, timeType, locationType);
        } else if (message.what == MethodCode.EVENT_CANCELLIKESTUDENT) {
            showInfo((String) message.obj);
            presenter.getSquareRankList(resourceType, timeType, locationType);
        }
    }

    private void fresh() {
        Glide.with(this).load(SystemUtils.userInfo.getAvatar()).into(headpic);
        name.setText(SystemUtils.userInfo.getName());
        if (squareRankList.getMyRankInfo().getRow_number() != 0) {
            rank.setText("第" + squareRankList.getMyRankInfo().getRow_number() + "名");
        } else {
            rank.setText("暂未上榜，请继续努力！");
        }
        address.setText(squareRankList.getMyRankInfo().getSquare() != null ? squareRankList.getMyRankInfo().getSquare() : "");
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
