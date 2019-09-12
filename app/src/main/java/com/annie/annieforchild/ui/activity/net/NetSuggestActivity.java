package com.annie.annieforchild.ui.activity.net;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.GradientScrollView;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Gift;
import com.annie.annieforchild.bean.net.NetSuggest;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.my.MyCourseActivity;
import com.annie.annieforchild.ui.adapter.HeaderViewAdapter;
import com.annie.annieforchild.ui.adapter.NetSuggestAdapter;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.daimajia.slider.library.SliderLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 网课介绍
 * Created by wanglei on 2018/9/22.
 */

public class NetSuggestActivity extends BaseActivity implements GrindEarView, OnCheckDoubleClick{
    private ImageView back;
    private SliderLayout banner;
    private TextView title, price, event, gotoBuy, net_suggest_summary, goBuy, netCheck;
    //    private RecyclerView giftRecycler;
    private LinearLayout netSuggestLinear;
    private NetWorkPresenter presenter;
    private CheckDoubleClickListener listener;
    //    private NetGiftAdapter adapter;
    private List<Gift> lists;
    private AlertHelper helper;
    private Dialog dialog;
    private Intent intent;
    private int netid, isBuy, canbuy = 0;
    private String type, netimage;
    LinearLayout Linear_title;
    GradientScrollView scrollView;
    private int height;
    public static NetSuggestActivity netSuggestActivity;
    private NetSuggest netSuggest;
    private TextView netsuggest_title;
    private HashMap<Integer, String> file_maps;//轮播图图片map
    private String message;
    private int width;
    private PopupWindow popupWindow;
    private View popupView;
    private int flag = 0;//是否选择配套教材 0:否 1:是
    private RecyclerView recycler;
    private NetSuggestAdapter adapter;
    private List<String> imglist;
    private Context context;
    private RelativeLayout headview;
    private int status_height;//状态栏高度


    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_netsuggest;
    }

    @Override
    protected void initView() {
        recycler=findViewById(R.id.net_img_recycler);
        headview=(RelativeLayout) LayoutInflater.from(this).inflate(
                R.layout.activity_netsuggest_head, null);

        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        width = dm.widthPixels;
        netsuggest_title =findViewById(R.id.netsuggest_title);
        netSuggestActivity = this;
        context=this;
        Linear_title =  findViewById(R.id.ll_title);
        back = findViewById(R.id.net_back);
        banner =  headview.findViewById(R.id.net_banner);
        title =  headview.findViewById(R.id.net_suggest_title);
        price =  headview.findViewById(R.id.net_suggest_price);
        event =  headview.findViewById(R.id.net_suggest_event);
//        giftRecycler = findViewById(R.id.net_gift_recycler);
        gotoBuy = findViewById(R.id.goto_buy);
//        present = findViewById(R.id.present_gift);
//        netSuggestLinear = findViewById(R.id.net_suggest_layout);
//        netSuggestLayout = findViewById(R.id.net_suggest_linear);
        net_suggest_summary =  headview.findViewById(R.id.net_suggest_summary);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        gotoBuy.setOnClickListener(listener);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        giftRecycler.setLayoutManager(manager);

        ViewTreeObserver vto = banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int resourceId = getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    status_height = getApplicationContext().getResources().getDimensionPixelSize(resourceId);
                }
                banner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height = banner.getHeight()-50;
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recycler.setLayoutManager(linearLayoutManager);
//        recycler.setHasFixedSize(true);
        recycler.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        imglist=new ArrayList<>();
        file_maps = new HashMap<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        intent = getIntent();
        netid = intent.getIntExtra("netid", 0);
        isBuy = intent.getIntExtra("isBuy", 0);
        netimage = intent.getStringExtra("netimage");
        message = intent.getStringExtra("message");
        type = intent.getStringExtra("type");
        presenter = new NetWorkPresenterImp(this, this);
        if (isBuy == 0) {
            gotoBuy.setText("立即购买");
            gotoBuy.setTextColor(getResources().getColor(R.color.white));
            gotoBuy.setBackgroundColor(getResources().getColor(R.color.text_orange));
        } else if (isBuy == 1) {
            gotoBuy.setText("去听课");
            gotoBuy.setTextColor(getResources().getColor(R.color.white));
            gotoBuy.setBackgroundColor(getResources().getColor(R.color.text_orange));
        } else if (isBuy == 2) {
            gotoBuy.setText(netSuggest.getMessage());
            gotoBuy.setTextColor(getResources().getColor(R.color.navigation_bar_color));
            gotoBuy.setBackgroundColor(getResources().getColor(R.color.gray1));
        }
        presenter.initViewAndData();
        presenter.getNetSuggest(netid);

        adapter=new NetSuggestAdapter(this,imglist);
        HeaderViewAdapter headerViewAdapter = new HeaderViewAdapter(adapter);
        headerViewAdapter.addHeaderView(headview);
        recycler.setAdapter(headerViewAdapter);
        recycler.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int x, int y1) {
                super.onScrolled(recyclerView, x, y1);
                int[] position = new int[2];
                banner.getLocationOnScreen(position);
                int positiony=(position[1]-status_height)*(-1);
                if(position[1]<0){
                    if(positiony<=0){
                        Linear_title.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                        netsuggest_title.setTextColor(Color.argb((int) 0, 69, 69, 69));
                        netsuggest_title.setAlpha(0);
                    }else if(positiony>0 && positiony<=height){
                        float scale = (float) positiony / height;
                        float alpha = (255 * scale);
                        Linear_title.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        netsuggest_title.setTextColor(Color.argb((int) alpha, 69, 69, 69));
                        netsuggest_title.setAlpha(alpha);
                    }else{
                        Linear_title.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                        netsuggest_title.setTextColor(Color.argb((int) 255, 69, 69, 69));
                        netsuggest_title.setAlpha(1);
                    }
                }else if(position[1]>0){
                    Linear_title.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                    netsuggest_title.setTextColor(Color.argb((int) 0, 69, 69, 69));
                    netsuggest_title.setAlpha(0);
                }

            }
        });

    }
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    /**
     * {@link NetWorkPresenterImp#Success(int, Object)}
     *
     * @param message
     */

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETSUGGEST) {
            netSuggest = (NetSuggest) message.obj;
            refresh();
//            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_PAY) {
            finish();
        } else if (message.what == MethodCode.EVENT_BUYNUM) {
            canbuy = (int) message.obj;//0名额已满 1已购买 2可以买 3无法购买
            if (canbuy == 2) {
                gotoBuy();
            } else if (canbuy == 0) {
                showInfo("名额已满");
                presenter.getNetSuggest(netid);
            } else if (canbuy == 1) {
                showInfo("已购买该课程");
                presenter.getNetSuggest(netid);
                JTMessage message1 = new JTMessage();
                message1.what = MethodCode.EVENT_PAY;
                message1.obj = 3;
                EventBus.getDefault().post(message1);
            } else if (canbuy == 3) {
                showInfo("已购买过该类课程");
            }
        }
    }

    private void gotoBuy() {
        if (isBuy == 0) {
            Intent intent = new Intent(this, ConfirmOrderActivity.class);
            intent.putExtra("netid", netid);
            intent.putExtra("netimage", netimage);
            intent.putExtra("type", type);
            intent.putExtra("netsummary", netSuggest.getNetSummary());
            intent.putExtra("flag", flag);
            startActivity(intent);
//                EventBus.getDefault().unregister(this);
        } else if (isBuy == 1) {
            Intent intent1 = new Intent(this, MyCourseActivity.class);
            startActivity(intent1);
        } else {
        }
    }

    private void refresh() {
        if (netSuggest != null) {
//            if(netSuggest.getTitleImageUrl().size()>0){
//                file_maps.clear();
//                for (int i = 0; i < netSuggest.getTitleImageUrl().size(); i++) {
//                    file_maps.put(i,  netSuggest.getTitleImageUrl().get(i));
//                }
//            }
            if (netSuggest.getNetSuggestUrl() != null && netSuggest.getNetSuggestUrl().size() != 0) {
                imglist.clear();
                imglist.addAll(netSuggest.getNetSuggestUrl());
                adapter.notifyDataSetChanged();
            }
            isBuy = netSuggest.getIsBuy();
            if (isBuy == 0) {
                gotoBuy.setText("立即购买");
                gotoBuy.setTextColor(getResources().getColor(R.color.white));
                gotoBuy.setBackgroundColor(getResources().getColor(R.color.text_orange));
            } else if (isBuy == 1) {
                gotoBuy.setText("去听课");
                gotoBuy.setTextColor(getResources().getColor(R.color.white));
                gotoBuy.setBackgroundColor(getResources().getColor(R.color.text_orange));
            } else if (isBuy == 2) {
                gotoBuy.setText(netSuggest.getMessage());
                gotoBuy.setTextColor(getResources().getColor(R.color.navigation_bar_color));
                gotoBuy.setBackgroundColor(getResources().getColor(R.color.gray1));
            }

            title.setText(netSuggest.getNetName());
            netsuggest_title.setText(netSuggest.getNetName());
            price.setText("￥" + netSuggest.getPrice());
            if (netSuggest.getEvent() != null && netSuggest.getEvent().length() != 0) {
                event.setText(netSuggest.getEvent());
                event.setVisibility(View.VISIBLE);
            } else {
                event.setVisibility(View.GONE);
            }
            net_suggest_summary.setText(netSuggest.getNetSummary());
//            if (netSuggest.getPrice() != null && netSuggest.getMaterial().length() != 0) {
//                material.setText(netSuggest.getMaterial());
//            } else {
//                material.setText("无");
//            }
//            if (netSuggest.getGift() != null && netSuggest.getGift().size() != 0) {
//                present.setText("赠送礼包：");
//                lists.clear();
//                lists.addAll(netSuggest.getGift());
//            } else {
//                present.setText("赠送礼包：无");
//            }
            initSpecialPopup(this, netSuggest.getMaterialPrice() != null ? netSuggest.getMaterialPrice() : "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(!EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().register(this);
//        }
//        presenter.getNetSuggest(netid);
    }


    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.net_back:
                finish();
                break;
            case R.id.goto_buy:
                switch (isBuy) {
                    case 0:
//                        presenter.buynum(netid,1);//type:1:netsuggest页面请求 2:confirmorder请求
//                        createAlertDialog(this);
                        if (type.equals("体验课")) {
                            presenter.buynum(netid, 1);//type:1:netsuggest页面请求 2:confirmorder请求
                        } else {
                            //ismaterial  是否有教材    0：没有     1：有
                            if(netSuggest.getIsmaterial()==0){
                                presenter.buynum(netid, 1);
                            }else{
                                SystemUtils.setBackGray(this, true);
                                popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
                            }
                        }
                        break;
                    case 1:
                        Intent intent1 = new Intent(this, MyCourseActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        break;
                }
//                if(isBuy==0){
//                    createAlertDialog(this);
//                }else{
//                    Intent intent1 = new Intent(this, MyCourseActivity.class);
//                    startActivity(intent1);
//                }
                break;
        }
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

    private void initSpecialPopup(Context context, String price) {
        flag = 0;
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_net_popupwindow, null, false);
        netCheck = popupView.findViewById(R.id.net_pop_material);
        goBuy = popupView.findViewById(R.id.gotobuy);
        goBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                presenter.buynum(netid, 1);//type:1:netsuggest页面请求 2:confirmorder请求
            }
        });
        netCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    flag = 1;
                    netCheck.setTextColor(context.getResources().getColor(R.color.text_orange));
                } else {
                    flag = 0;
                    netCheck.setTextColor(context.getResources().getColor(R.color.text_color));
                }
            }
        });
        netCheck.setText("配套教材  ￥" + price);
        netCheck.setTextColor(context.getResources().getColor(R.color.text_color));
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray((Activity) context, false);
            }
        });
        popupWindow.setContentView(popupView);
    }

    @Override
    public SliderLayout getImageSlide() {
        return banner;
    }


    @Override
    public HashMap<Integer, String> getFile_maps() {
        return file_maps;
    }


}
