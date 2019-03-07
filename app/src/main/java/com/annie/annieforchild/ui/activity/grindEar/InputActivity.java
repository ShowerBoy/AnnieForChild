package com.annie.annieforchild.ui.activity.grindEar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.material.MaterialGroup;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.my.HelpActivity;
import com.annie.annieforchild.ui.adapter.PopupAdapter;
import com.annie.annieforchild.view.ReadingView;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.ashokvarma.bottomnavigation.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 录入界面
 * Created by wanglei on 2018/4/11.
 */

public class InputActivity extends BaseActivity implements OnCheckDoubleClick, SongView {
    private TextView inputTingmobao, inputDiandubi, inputQita, feedback, diandubi, inputXiaomobao;
    private ImageView back, diandubiImage;
    private RelativeLayout tingdonghuaLayout, tingmobaoLayout, xiaomobaoLayout;
    private Button inputBtn;
    private PopupWindow popupWindow;
    private List<MaterialGroup> popup_lists;
    private PopupAdapter adapter;
    private ListView popupList;
    private View popup_contentView;
    private GrindEarPresenter presenter;
    private String[] types, durations;
    private String type;
    private AlertHelper helper;
    private Dialog dialog;
    private Intent intent;
    private String tag; // grindear:磨耳朵  reading:阅读  speaking:口语
    private boolean input = false; //true:录入书名
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input;
    }

    @Override
    protected void initView() {
        inputTingmobao = findViewById(R.id.input_tingmobao_duration);
        inputDiandubi = findViewById(R.id.input_diandubi_duration);
        inputXiaomobao = findViewById(R.id.input_xiaomobao_duration);
        xiaomobaoLayout = findViewById(R.id.xiaomobao_layout);
        diandubi = findViewById(R.id.diandubi);
        inputQita = findViewById(R.id.input_qita_duration);
        inputBtn = findViewById(R.id.input_btn);
        feedback = findViewById(R.id.input_feedback);
        diandubiImage = findViewById(R.id.input_diandubi);

        back = findViewById(R.id.input_back);
        tingmobaoLayout = findViewById(R.id.tingmobao_layout);
        listener = new CheckDoubleClickListener(this);
        inputTingmobao.setOnClickListener(listener);
        inputDiandubi.setOnClickListener(listener);
        inputXiaomobao.setOnClickListener(listener);
        inputQita.setOnClickListener(listener);
        inputBtn.setOnClickListener(listener);
        feedback.setOnClickListener(listener);
        back.setOnClickListener(listener);
        tingmobaoLayout.setOnClickListener(listener);
        popup_contentView = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_item, null);
        popupList = popup_contentView.findViewById(R.id.popup_lists1);
        popupWindow = new PopupWindow(popup_contentView, ViewGroup.LayoutParams.WRAP_CONTENT, Utils.dp2px(this, 200), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }

    @Override
    protected void initData() {
        intent = getIntent();
        tag = intent.getStringExtra("tag");
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
//        types = new String[]{"mobao", "xiaomobao", "readingpen", "others"};
//        durations = new String[]{"0", "0", "0", "0"};
        if (tag.equals("grindear")) {
            input = false;
            types = new String[]{"mobao", "xiaomobao", "readingpen", "others"};
            durations = new String[]{"0", "0", "0", "0"};
            tingmobaoLayout.setVisibility(View.VISIBLE);
            xiaomobaoLayout.setVisibility(View.VISIBLE);
            diandubi.setText("点读笔");
            inputDiandubi.setText("0分钟");
            diandubiImage.setImageResource(R.drawable.icon_diandubi);
        } else if (tag.equals("reading")) {
            input = true;
            types = new String[]{"others"};
            durations = new String[]{"0"};
            tingmobaoLayout.setVisibility(View.GONE);
            xiaomobaoLayout.setVisibility(View.GONE);
            diandubi.setText("纸质书");
            inputDiandubi.setText("请录入");
            diandubiImage.setImageResource(R.drawable.icon_zhizhishu);
        } else {
            input = false;
            types = new String[]{"readingpen", "others"};
            durations = new String[]{"0", "0"};
            tingmobaoLayout.setVisibility(View.GONE);
            xiaomobaoLayout.setVisibility(View.GONE);
            diandubi.setText("点读笔");
            inputDiandubi.setText("0分钟");
            diandubiImage.setImageResource(R.drawable.icon_diandubi);
        }

        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();

        popup_lists = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            popup_lists.add(new MaterialGroup(i + 1 + "", false));
        }
        adapter = new PopupAdapter(this, popup_lists);
        popupList.setAdapter(adapter);
        popupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tag.equals("grindear")) {
                    if (type.equals("听磨宝")) {
                        durations[0] = popup_lists.get(position).getTitle();
                        inputTingmobao.setText(popup_lists.get(position).getTitle() + "分钟");
                    } else if (type.equals("小磨宝")) {
                        durations[1] = popup_lists.get(position).getTitle();
                        inputXiaomobao.setText(popup_lists.get(position).getTitle() + "分钟");
                    } else if (type.equals("点读笔")) {
                        durations[2] = popup_lists.get(position).getTitle();
                        inputDiandubi.setText(popup_lists.get(position).getTitle() + "分钟");
                    } else if (type.equals("其他")) {
                        durations[3] = popup_lists.get(position).getTitle();
                        inputQita.setText(popup_lists.get(position).getTitle() + "分钟");
                    }
                } else if (tag.equals("reading")) {
                    if (type.equals("其他")) {
                        durations[0] = popup_lists.get(position).getTitle();
                        inputQita.setText(popup_lists.get(position).getTitle() + "分钟");
                    }
                } else {
                    if (type.equals("点读笔")) {
                        durations[0] = popup_lists.get(position).getTitle();
                        inputDiandubi.setText(popup_lists.get(position).getTitle() + "分钟");
                    } else if (type.equals("其他")) {
                        durations[1] = popup_lists.get(position).getTitle();
                        inputQita.setText(popup_lists.get(position).getTitle() + "分钟");
                    }
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGray(false);
            }
        });
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private void setBackGray(boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().setAttributes(layoutParams);
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_COMMITDURATION) {
            finish();
        } else if (message.what == MethodCode.EVENT_COMMITREADING) {
            finish();
        } else if (message.what == MethodCode.EVENT_COMMITSPEAKING) {
            finish();
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
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.input_tingmobao_duration:
                type = "听磨宝";
                setBackGray(true);
                popupWindow.showAtLocation(popup_contentView, Gravity.CENTER, 0, 0);
                break;
            case R.id.input_xiaomobao_duration:
                type = "小磨宝";
                setBackGray(true);
                popupWindow.showAtLocation(popup_contentView, Gravity.CENTER, 0, 0);
                break;
            case R.id.input_diandubi_duration:
                if (input) {
                    Intent intent = new Intent(this, InputBookActivity.class);
                    startActivity(intent);
                } else {
                    type = "点读笔";
                    setBackGray(true);
                    popupWindow.showAtLocation(popup_contentView, Gravity.CENTER, 0, 0);
                }
                break;
            case R.id.input_qita_duration:
                type = "其他";
                setBackGray(true);
                popupWindow.showAtLocation(popup_contentView, Gravity.CENTER, 0, 0);
                break;
            case R.id.input_btn:
                boolean b = false;
                for (int i = 0; i < durations.length; i++) {
                    if (!durations[i].equals("0")) {
                        b = true;
                    }
                }
                if (b) {
                    if (tag.equals("grindear")) {
//                        presenter.commitDuration(types, durations);
                        presenter.commitDuration("", "");
                    } else if (tag.equals("reading")) {
                        presenter.commitReading("", "", 0, 0);
                    } else {
                        presenter.commitSpeaking("", "");
                    }
                }
                break;
            case R.id.input_feedback:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.input_back:
                finish();
                break;
        }
    }
}
