package com.annie.annieforchild.ui.activity.grindEar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.material.MaterialGroup;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.PopupAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.ashokvarma.bottomnavigation.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 录入
 * Created by wanglei on 2018/7/9.
 */

public class InputBookActivity extends BaseActivity implements SongView, OnCheckDoubleClick {
    private TextView time, book, word;
    private ImageView back;
    private Button input;
    private PopupWindow popupWindow;
    private PopupAdapter adapter;
    private View popup_contentView;
    private ListView popupList;
    private List<MaterialGroup> popup_lists;
    private GrindEarPresenter presenter;
    private String duration;
    private int books = 0, words = 0;
    private EditText editText1, editText2;
    private AlertHelper helper;
    private Dialog dialog;
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_book;
    }

    @Override
    protected void initView() {
        time = findViewById(R.id.input_time_duration);
        book = findViewById(R.id.input_book_duration);
        word = findViewById(R.id.input_word_duration);
        input = findViewById(R.id.input_book_btn);
        back = findViewById(R.id.input_book_back);
        listener = new CheckDoubleClickListener(this);
        time.setOnClickListener(listener);
        book.setOnClickListener(listener);
        word.setOnClickListener(listener);
        input.setOnClickListener(listener);
        back.setOnClickListener(listener);
        popup_contentView = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_item, null);
        popupList = popup_contentView.findViewById(R.id.popup_lists1);
        popupWindow = new PopupWindow(popup_contentView, ViewGroup.LayoutParams.WRAP_CONTENT, Utils.dp2px(this, 200), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
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
                duration = popup_lists.get(position).getTitle();
                time.setText(popup_lists.get(position).getTitle() + "分钟");
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

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_COMMITREADING) {
            Intent intent = new Intent(this, InputSuccessActivity.class);
            startActivity(intent);
            finish();
        }
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
            case R.id.input_time_duration:
                setBackGray(true);
                popupWindow.showAtLocation(popup_contentView, Gravity.CENTER, 0, 0);
                break;
            case R.id.input_book_duration:
                editText1 = new EditText(this);
                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strs = editText1.getText().toString();
                        String str = SystemUtils.stringFilter2(strs.toString());
                        if (!strs.equals(str)) {
                            editText1.setText(str);
                            editText1.setSelection(str.length());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                SystemUtils.GeneralDialog(this, "请输入本数")
                        .setView(editText1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText1.getText().toString().length() != 0) {
                                    books = Integer.parseInt(editText1.getText().toString());
                                    book.setText(books + "本");
                                } else {
                                    books = 0;
                                    book.setText("未录入");
                                }
                                dialog.dismiss();
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
            case R.id.input_word_duration:
                editText2 = new EditText(this);
                editText2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strs = editText2.getText().toString();
                        String str = SystemUtils.stringFilter2(strs.toString());
                        if (!strs.equals(str)) {
                            editText2.setText(str);
                            editText2.setSelection(str.length());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                SystemUtils.GeneralDialog(this, "请输入字数")
                        .setView(editText2)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText2.getText().toString().length() != 0) {
                                    words = Integer.parseInt(editText2.getText().toString());
                                    word.setText(words + "字");
                                } else {
                                    words = 0;
                                    word.setText("未录入");
                                }
                                dialog.dismiss();
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
            case R.id.input_book_btn:
                if (duration != null && duration.length() != 0 && books != 0 && words != 0) {
                    presenter.commitReading("readingpen", duration, books, words);
                } else {
                    showInfo("输入不完整，请重新输入");
                }
                break;
            case R.id.input_book_back:
                finish();
                break;
        }
    }
}
