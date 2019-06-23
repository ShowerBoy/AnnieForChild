package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import com.annie.annieforchild.Utils.GetJsonDataUtil;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.bean.net.ShengBean;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加收货地址
 * Created by wanglei on 2018/9/27.
 */

public class AddAddressActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private ImageView back, delete;
    private TextView btn;
    private TextView add_address_province;
    private EditText Addname, Addphone, Addaddress;
    private String name, phone, address,provinces;
    private NetWorkPresenter presenter;
    private CheckDoubleClickListener listener;
    private Intent intent;
    private boolean isEdit = false;
    private int addressId;
    private AlertHelper helper;
    private Dialog dialog;
    //  省
    private List<ShengBean> options1Items = new ArrayList<ShengBean>();
    //  市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


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
        add_address_province = findViewById(R.id.add_address_province);
        add_address_province.setOnClickListener(listener);
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
            add_address_province.setText(address.getProvinces());
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
            case R.id.add_address_province:
                // 解析数据
                parseData();
                // 展示省市区选择器
                showPickerView();
                break;
            case R.id.add_address_back:
                finish();
                break;
            case R.id.add_address_queding:
                if (isCorrect()) {
                    if (isEdit) {
                        presenter.addOrUpdateAddress(addressId, name, phone, address,provinces);
                    } else {
                        presenter.addOrUpdateAddress(-1,name, phone, address,provinces);
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
        provinces = add_address_province.getText().toString().trim();
        if (name != null && name.length() != 0 && phone != null && phone.length() != 0 && address != null && address.length() != 0
         && provinces!=null && provinces.length()!=0) {
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
    /**
     * 展示选择器
     */
    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).name +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);

                add_address_province.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
    /**
     * 解析数据并组装成自己想要的list
     */
    private void parseData(){
        String jsonStr = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
//     数据解析
        Gson gson =new Gson();
        java.lang.reflect.Type type =new TypeToken<List<ShengBean>>(){}.getType();
        List<ShengBean>shengList=gson.fromJson(jsonStr, type);
//     把解析后的数据组装成想要的list
        options1Items = shengList;
//     遍历省
        for(int i = 0; i <shengList.size() ; i++) {
//         存放城市
            ArrayList<String> cityList = new ArrayList<>();
//         存放区
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
//         遍历市
            for(int c = 0; c <shengList.get(i).city.size() ; c++) {
//        拿到城市名称
                String cityName = shengList.get(i).city.get(c).name;
                cityList.add(cityName);

                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                if (shengList.get(i).city.get(c).area == null || shengList.get(i).city.get(c).area.size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(shengList.get(i).city.get(c).area);
                }
                province_AreaList.add(city_AreaList);
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

    }
}
