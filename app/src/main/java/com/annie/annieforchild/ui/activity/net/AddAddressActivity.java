package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.lang.invoke.MethodHandle;

/**
 * 添加收货地址
 * Created by wanglei on 2018/9/27.
 */

public class AddAddressActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private ImageView back, delete;
    private TextView btn;
    private EditText Addname, Addphone, Addaddress;
    private String name, phone, address;
    private NetWorkPresenter presenter;
    private CheckDoubleClickListener listener;
    private Intent intent;
    private boolean isEdit = false;
    private int addressId;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.add_address_back);
        btn = findViewById(R.id.add_address_queding);
        delete = findViewById(R.id.add_address_delete);
        Addname = findViewById(R.id.add_address_name);
        Addphone = findViewById(R.id.add_address_phone);
        Addaddress = findViewById(R.id.add_address_address);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        btn.setOnClickListener(listener);
        delete.setOnClickListener(listener);
        Addname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strs = Addname.getText().toString();
                String str = SystemUtils.stringFilter(strs.toString());
                if (!strs.equals(str)) {
                    Addname.setText(str);
                    Addname.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Addaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strs = Addaddress.getText().toString();
                String str = SystemUtils.stringFilter(strs.toString());
                if (!strs.equals(str)) {
                    Addaddress.setText(str);
                    Addaddress.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        intent = getIntent();
        Address address = (Address) intent.getSerializableExtra("address");
        if (address != null) {
            isEdit = true;
            delete.setVisibility(View.VISIBLE);
            addressId = address.getAddressId();
            Addname.setText(address.getName());
            Addphone.setText(address.getPhone());
            Addaddress.setText(address.getAddress());
            btn.setText("修改");
        } else {
            isEdit = false;
            delete.setVisibility(View.GONE);
            btn.setText("确定");
        }
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.add_address_back:
                finish();
                break;
            case R.id.add_address_queding:
                if (isCorrect()) {
                    if (isEdit) {
                        presenter.editAddress(addressId, name, phone, address);
                    } else {
                        presenter.addAddress(name, phone, address);
                    }
                } else {
                    showInfo("请填写完整信息");
                }
                break;
            case R.id.add_address_delete:
                SystemUtils.GeneralDialog(this, "删除")
                        .setMessage("确定删除收货地址吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.deleteAddress(addressId);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .show();
                break;
        }
    }

    private boolean isCorrect() {
        name = Addname.getText().toString().trim();
        phone = Addphone.getText().toString().trim();
        address = Addaddress.getText().toString().trim();
        if (name != null && name.length() != 0 && phone != null && phone.length() != 0 && address != null && address.length() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_ADDADDRESS) {
            showInfo((String) message.obj);
            finish();
        } else if (message.what == MethodCode.EVENT_EDITADDRESS) {
            showInfo((String) message.obj);
            finish();
        } else if (message.what == MethodCode.EVENT_DELETEADDRESS) {
            showInfo((String) message.obj);
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
}
