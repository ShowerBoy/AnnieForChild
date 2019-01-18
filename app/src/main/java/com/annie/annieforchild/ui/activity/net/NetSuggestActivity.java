package com.annie.annieforchild.ui.activity.net;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.net.Gift;
import com.annie.annieforchild.bean.net.NetSuggest;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.my.MyCourseActivity;
import com.annie.annieforchild.ui.adapter.NetGiftAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * 网课介绍
 * Created by wanglei on 2018/9/22.
 */

public class NetSuggestActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick, GradientScrollView.ScrollViewListener {
    private ImageView back, banner;
    private TextView title, price, event, material, gotoBuy, present,net_suggest_summary;
    private RecyclerView giftRecycler;
    private LinearLayout netSuggestLinear, netSuggestLayout;
    private NetWorkPresenter presenter;
    private CheckDoubleClickListener listener;
    private NetGiftAdapter adapter;
    private List<Gift> lists;
    private AlertHelper helper;
    private Dialog dialog;
    private Intent intent;
    private int netid, isBuy;
    private String type,netimage;
    LinearLayout Linear_title;
    GradientScrollView scrollView;
    private int height;
    public static NetSuggestActivity netSuggestActivity;
    private NetSuggest netSuggest;
    private TextView netsuggest_title;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_netsuggest;
    }

    @Override
    protected void initView() {
        netsuggest_title=findViewById(R.id.netsuggest_title);
        netSuggestActivity=this;
        Linear_title=findViewById(R.id.ll_title);
        back = findViewById(R.id.net_back);
        banner = findViewById(R.id.net_banner);
        title = findViewById(R.id.net_suggest_title);
        price = findViewById(R.id.net_suggest_price);
        event = findViewById(R.id.net_suggest_event);
        material = findViewById(R.id.net_material);
        giftRecycler = findViewById(R.id.net_gift_recycler);
        gotoBuy = findViewById(R.id.goto_buy);
        present = findViewById(R.id.present_gift);
        netSuggestLinear = findViewById(R.id.net_suggest_layout);
        netSuggestLayout = findViewById(R.id.net_suggest_linear);
        net_suggest_summary = findViewById(R.id.net_suggest_summary);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        gotoBuy.setOnClickListener(listener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        giftRecycler.setLayoutManager(manager);

        scrollView=findViewById(R.id.scrollView);
        scrollView.setScrollViewListener(this);
        ViewTreeObserver vto = banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                banner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height = banner.getHeight() - Linear_title.getHeight();
            }
        });

    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        intent = getIntent();
        netid = intent.getIntExtra("netid", 0);
        isBuy = intent.getIntExtra("isBuy", 0);
        netimage = intent.getStringExtra("netimage");
        type = intent.getStringExtra("type");
        if (type.equals("体验课")) {
            netSuggestLayout.setVisibility(View.GONE);
        } else {
            netSuggestLayout.setVisibility(View.VISIBLE);
        }
        adapter = new NetGiftAdapter(this, lists, 0, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = giftRecycler.getChildAdapterPosition(view);
//                showInfo(lists.get(position).getText());
                SystemUtils.setBackGray(NetSuggestActivity.this, true);
                SystemUtils.getGiftPopup(NetSuggestActivity.this, lists.get(position).getName(), lists.get(position).getText(), lists.get(position).getRemarks()).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
            }
            @Override
            public void onItemLongClick(View view) {

            }
        });
        giftRecycler.setAdapter(adapter);
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getNetSuggest(netid);
        if (isBuy == 0) {
            gotoBuy.setText("立即购买");
        } else {
            gotoBuy.setText("去听课");
        }
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
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_PAY) {
            finish();
        }
    }

    private void refresh() {
        if (netSuggest != null) {
            if (netSuggest.getNetSuggestUrl() != null && netSuggest.getNetSuggestUrl().size() != 0) {
                netSuggestLinear.removeAllViews();
                for (int i = 0; i < netSuggest.getNetSuggestUrl().size(); i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    Glide.with(this).load(netSuggest.getNetSuggestUrl().get(i)).into(imageView);
                    netSuggestLinear.addView(imageView);
                }
            }
            Glide.with(this).load(netSuggest.getTitleImageUrl()).into(banner);
            title.setText(netSuggest.getNetName());
            netsuggest_title.setText(netSuggest.getNetName());
            price.setText("￥"+ netSuggest.getPrice());
            if(netSuggest.getEvent()!=null && netSuggest.getEvent().length() !=0){
                event.setText(netSuggest.getEvent());
                event.setVisibility(View.VISIBLE);
            }else {
                event.setVisibility(View.GONE);
            }
            net_suggest_summary.setText(netSuggest.getNetSummary());
            if (netSuggest.getMaterial() != null && netSuggest.getMaterial().length() != 0) {
                material.setText(netSuggest.getMaterial());
            } else {
                material.setText("无");
            }
            if (netSuggest.getGift() != null && netSuggest.getGift().size() != 0) {
                present.setText("赠送礼包：");
                lists.clear();
                lists.addAll(netSuggest.getGift());
            } else {
                present.setText("赠送礼包：无");
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.net_back:
                finish();
                break;
            case R.id.goto_buy:
                if (isBuy == 0) {
                    Intent intent = new Intent(this, ConfirmOrderActivity.class);
                    intent.putExtra("netid", netid);
                    intent.putExtra("netimage", netimage);
                    intent.putExtra("type", type);
                    intent.putExtra("netsummary", netSuggest.getNetSummary());
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(this, MyCourseActivity.class);
                    startActivity(intent1);
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
    private void createAlertDialog(Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,R.style.myDialog);
        View view = View.inflate(this, R.layout.activity_net_popupwindow, null);
        TextView net_material=view.findViewById(R.id.net_material);
        if(netSuggest.getMaterial()!=null && netSuggest.getMaterial().length()>0){
            net_material.setText(netSuggest.getMaterial()+netSuggest.getMaterialPrice());
        }
        RecyclerView net_gift=view.findViewById(R.id.net_gift);
        NetGiftAdapter adapter=new NetGiftAdapter(this, lists, 1, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onItemLongClick(View view) {

            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.gravity=Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setAttributes(layoutParams);

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        net_gift.setLayoutManager(manager);
        net_gift.setAdapter(adapter);
        TextView gotoBuy=view.findViewById(R.id.gotobuy);
        gotoBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(activity, ConfirmOrderActivity.class);
                    intent.putExtra("netid", netid);
                    intent.putExtra("type", type);
                    startActivity(intent);

            }
        });
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

    //滑动监听 控制状态栏变化
    @Override
    public void onScrollChanged(GradientScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            Linear_title.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            netsuggest_title.setTextColor(Color.argb((int) 0, 69, 69, 69));
            netsuggest_title.setAlpha(0);
        } else if (y > 0 && y <= height) {
            float scale = (float) y / height;
            float alpha = (255 * scale);
            Linear_title.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            netsuggest_title.setTextColor(Color.argb((int) alpha, 69, 69, 69));
            netsuggest_title.setAlpha(alpha);
        } else {
            Linear_title.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
            netsuggest_title.setTextColor(Color.argb((int) 255, 69, 69, 69));
            netsuggest_title.setAlpha(1);
        }
    }
}
