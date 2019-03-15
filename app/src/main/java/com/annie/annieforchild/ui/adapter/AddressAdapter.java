package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.ui.activity.net.AddAddressActivity;
import com.annie.annieforchild.ui.activity.net.MyAddressActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.AddressViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by wanglei on 2018/9/27.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressViewHolder> {
    private Context context;
    private List<Address> lists;
    private LayoutInflater inflater;
    private boolean isClick;

    public AddressAdapter(Context context, List<Address> lists) {
        this.context = context;
        this.lists = lists;
        isClick = true;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        AddressViewHolder holder = null;
        holder = new AddressViewHolder(inflater.inflate(R.layout.activity_address_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int i) {
        holder.name.setText(lists.get(i).getName());
        holder.phone.setText(lists.get(i).getPhone());
        holder.address.setText(lists.get(i).getAddress());
//        if(lists.get(i).getIsDefault()==0){//0不是默认地址
//            holder.checkbox.setChecked(false);
//        }else{
//            holder.checkbox.setChecked(true);
//        }
        holder.layout.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                if (isClick) {
                    isClick = false;
                    holder.checkbox.setChecked(true);
                    JTMessage message = new JTMessage();
                    message.what = MethodCode.EVENT_ADDRESS;
                    message.obj = lists.get(i).getAddressId();
                    EventBus.getDefault().post(message);
                    ((MyAddressActivity) context).finish();
                }
            }
        }));
//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.checkbox.setChecked(true);
//                JTMessage message = new JTMessage();
//                message.what = MethodCode.EVENT_ADDRESS;
//                message.obj = lists.get(i).getAddressId();
//                EventBus.getDefault().post(message);
//                ((MyAddressActivity) context).finish();
//            }
//        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("address", lists.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
