package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.experience.EveryDetail;
import com.annie.annieforchild.bean.net.experience.EveryTaskBean;
import com.annie.annieforchild.bean.net.experience.EveryTaskList;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.adapter.EveryDayTaskAdapter;
import com.annie.annieforchild.ui.adapter.EveryDayTaskTopAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.tencent.smtt.sdk.WebSettings;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import wendu.dsbridge.DWebView;

/**
 * 每日任务
 * Created by wanglei on 2019/5/8.
 */

public class EveryDayTaskActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private ImageView back, empty, emptyImage;
    private DWebView webView;
    private TextView title;
    private RecyclerView topRecycler;
    private LinearLayout imageLayout;
    private EveryDayTaskTopAdapter topAdapter;
    //    private EveryDayTaskAdapter taskAdapter;
    private NetWorkPresenter presenter;
    private Dialog dialog;
    private AlertHelper helper;
    private int netid;
    private String netName;
    private Intent intent;
    private CheckDoubleClickListener listener;
    private EveryTaskList everyTaskList;
    private EveryDetail everyDetail;
    private List<EveryTaskBean> lists;
    private List<String> imageLists;
    private String url;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_every_day_task;
    }

    @Override
    protected void initView() {
        listener = new CheckDoubleClickListener(this);
        back = findViewById(R.id.everyday_back);
        title = findViewById(R.id.everyday_title);
        topRecycler = findViewById(R.id.every_day_top_recycler);
//        taskRecycler = findViewById(R.id.every_day_task_recycler);
        empty = findViewById(R.id.empty_task);
        emptyImage = findViewById(R.id.empty_task_image);
        imageLayout = findViewById(R.id.every_day_image_layout);
        webView = findViewById(R.id.dwebView);
        back.setOnClickListener(listener);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 7);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topRecycler.setLayoutManager(layoutManager);
        topRecycler.setNestedScrollingEnabled(false);
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
//        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
//        taskRecycler.setLayoutManager(layoutManager1);

        intent = getIntent();
        if (intent != null) {
            netid = intent.getIntExtra("netid", 0);
            netName = intent.getStringExtra("netName");
            title.setText("今日任务");
        }

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(false);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webView.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        webView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        webView.canGoForward();
        webView.canGoBack();

        Bundle data = new Bundle();
        data.putBoolean("standardFullScreen", false);
        //true表示标准全屏，false表示X5全屏；不设置默认false，
        data.putBoolean("supportLiteWnd", false);
        //false：关闭小窗；true：开启小窗；不设置默认true，
        data.putInt("DefaultVideoScreen", 1);
        //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
        if (webView.getX5WebViewExtension() != null) {
            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        imageLists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        topAdapter = new EveryDayTaskTopAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = topRecycler.getChildAdapterPosition(view);
                if (lists.get(position).getStatus() != 3) {
                    if (!lists.get(position).getIsSelect()) {
                        for (int i = 0; i < lists.size(); i++) {
                            lists.get(i).setIsSelect(false);
                        }
                        lists.get(position).setIsSelect(true);
                        url = lists.get(position).getUrl();
                        webView.loadUrl(url);
//                        presenter.taskDetail(netid, lists.get(position).getNum());
                        topAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
//        taskAdapter = new EveryDayTaskAdapter(this, imageLists);
        topRecycler.setAdapter(topAdapter);
//        taskRecycler.setAdapter(taskAdapter);
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();

        presenter.taskList(netid);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_TASKLIST) {
            everyTaskList = (EveryTaskList) message.obj;
            if (everyTaskList != null) {
                if (everyTaskList.getDayList() != null && everyTaskList.getDayList().size() > 0) {
                    empty.setVisibility(View.GONE);
                    lists.clear();
                    lists.addAll(everyTaskList.getDayList());
                    for (int i = 0; i < lists.size(); i++) {
                        if (lists.get(i).getStatus() == 2) {
                            lists.get(i).setIsSelect(true);
                            url = lists.get(i).getUrl();
                        } else {
                            lists.get(i).setIsSelect(false);
                        }
                    }
                    topAdapter.notifyDataSetChanged();

                    webView.loadUrl(url);
//                    presenter.taskDetail(netid, everyTaskList.getDayNow());
                } else {
                    empty.setVisibility(View.VISIBLE);
                }
            }
        } else if (message.what == MethodCode.EVENT_TASKDETAIL) {
//            everyDetail = (EveryDetail) message.obj;
//            if (everyDetail != null) {
//                if (everyDetail.getIsshow() == 1) {
//                    if (everyDetail.getTaskDetail() != null) {
//                        emptyImage.setVisibility(View.GONE);
//                        taskRecycler.setVisibility(View.VISIBLE);
//                        imageLists.clear();
//                        imageLists.addAll(everyDetail.getTaskDetail().getImages());
//                        taskAdapter.notifyDataSetChanged();
//                    } else {
//                        taskRecycler.setVisibility(View.GONE);
//                        emptyImage.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    taskRecycler.setVisibility(View.GONE);
//                    emptyImage.setVisibility(View.VISIBLE);
//                }
//            } else {
//                taskRecycler.setVisibility(View.GONE);
//                emptyImage.setVisibility(View.VISIBLE);
//            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.everyday_back:
                finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.getSettings().setLightTouchEnabled(false);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
//            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
