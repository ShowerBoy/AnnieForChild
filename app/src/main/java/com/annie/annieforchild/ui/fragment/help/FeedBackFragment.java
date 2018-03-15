package com.annie.annieforchild.ui.fragment.help;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * 反馈
 * Created by WangLei on 2018/2/26 0026
 */

public class FeedBackFragment extends BaseFragment implements ViewInfo, View.OnClickListener {
    private MaterialEditText editText;
    private Button commit;
    private MessagePresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;

    public static FeedBackFragment instance() {
        FeedBackFragment fragment = new FeedBackFragment();
        return fragment;
    }


    @Override
    protected void initData() {
        presenter = new MessagePresenterImp(getContext(), this);
        presenter.initViewAndData();
    }

    @Override
    protected void initView(View view) {
        editText = view.findViewById(R.id.feedback_text);
        commit = view.findViewById(R.id.commit_feedback);
        commit.setOnClickListener(this);
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback_fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit_feedback:
                if (editText.getText().toString() != null) {
                    presenter.feedback(editText.getText().toString());
                }
                break;
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
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
