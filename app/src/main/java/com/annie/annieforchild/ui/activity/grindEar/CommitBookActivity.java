package com.annie.annieforchild.ui.activity.grindEar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.CommitBookAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/7/10.
 */

public class CommitBookActivity extends BaseActivity implements OnCheckDoubleClick, SongView {
    private RecyclerView recycler;
    private ImageView back;
    private TextView addBook;
    private Button commit;
    private EditText editText;
    private String bookName;
    private CommitBookAdapter adapter;
    private List<String> lists;
    private GrindEarPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commit_book;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.commit_book_back);
        recycler = findViewById(R.id.commit_book_recycler);
        commit = findViewById(R.id.commit_book_btn);
        addBook = findViewById(R.id.add_book);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        commit.setOnClickListener(listener);
        addBook.setOnClickListener(listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        lists = new ArrayList<>();
        adapter = new CommitBookAdapter(this, lists);
        recycler.setAdapter(adapter);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.commit_book_back:
//                finish();
//                break;
//            case R.id.commit_book_btn:
//                if (lists.size() != 0) {
//                    presenter.commitBook(lists);
//                }
//                break;
//            case R.id.add_book:
//                editText = new EditText(this);
//                SystemUtils.setEditTextInhibitInputSpeChat2(editText);
//                SystemUtils.GeneralDialog(this, "请输入书名")
//                        .setView(editText)
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (editText.getText().toString().trim().length() != 0) {
//                                    bookName = editText.getText().toString().trim();
//                                    lists.add(bookName);
//                                    adapter.notifyDataSetChanged();
//                                }
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .show();
//                break;
//        }
//    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_COMMITBOOK) {
//            showInfo((String) message.obj);
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
            case R.id.commit_book_back:
                finish();
                break;
            case R.id.commit_book_btn:
                if (lists.size() != 0) {
                    presenter.commitBook(lists);
                }
                break;
            case R.id.add_book:
                editText = new EditText(this);
                SystemUtils.setEditTextInhibitInputSpeChat2(editText);
                SystemUtils.GeneralDialog(this, "请输入书名")
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText().toString().trim().length() != 0) {
                                    bookName = editText.getText().toString().trim();
                                    lists.add(bookName);
                                    adapter.notifyDataSetChanged();
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
        }
    }
}
