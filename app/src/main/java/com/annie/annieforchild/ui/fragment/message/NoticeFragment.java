package com.annie.annieforchild.ui.fragment.message;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.NetGift;
import com.annie.annieforchild.bean.tongzhi.MyNotice;
import com.annie.annieforchild.bean.tongzhi.Notice;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.presenter.imp.LoginPresenterImp;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.ui.adapter.NoticeAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知
 * Created by WangLei on 2018/2/26 0026
 */

public class NoticeFragment extends BaseFragment implements ViewInfo {
    private XRecyclerView noticeRecycler;
    private ImageView empty;
    private List<Notice> lists;
    private NoticeAdapter adapter;
    private LoginPresenter presenter;
    private int origin = 2;

    {
        setRegister(true);
    }

    public static NoticeFragment instance() {
        NoticeFragment fragment = new NoticeFragment();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        noticeRecycler = view.findViewById(R.id.notice_recycler);
        empty = view.findViewById(R.id.empty);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_fragment;
    }

    @Override
    protected void initData() {
        presenter = new LoginPresenterImp(getContext(), this);
        presenter.initViewAndData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noticeRecycler.setLayoutManager(layoutManager);
        noticeRecycler.setPullRefreshEnabled(false);
        noticeRecycler.setLoadingMoreEnabled(false);
        noticeRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lists = new ArrayList<>();
        adapter = new NoticeAdapter(getContext(), lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = noticeRecycler.getChildAdapterPosition(view);
                if (lists.get(position - 1).getIsChoose() == 0) {
                    //TODO:
//                    presenter.showGifts(2, lists.get(position - 1).getGiftRecordId());
//                    SystemUtils.setBackGray(getActivity(), true);
//                    application.getSystemUtils().getNetWorkGift(getContext(), netGift.getGiftRecordId(), netGift.getGiftList(), presenter).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                }

            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        noticeRecycler.setAdapter(adapter);
    }

    /**
     * {@link MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMESSAGESLIST) {
            List<Notice> list = (List<Notice>) message.obj;
            if (list != null) {
                lists.clear();
                lists.addAll(list);
                if (lists.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            } else {
                empty.setVisibility(View.VISIBLE);
            }
        } else if (message.what == MethodCode.EVENT_SHOWGIFTS + 110000 + origin) {
            NetGift netGift = (NetGift) message.obj;
            if (netGift != null) {
                if (netGift.getIsshow() == 1) {
                    //TODO:显示弹窗
                    SystemUtils.setBackGray(getActivity(), true);
                    application.getSystemUtils().getNetWorkGift(getContext(), netGift.getGiftRecordId(), netGift.getGiftList(), presenter).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                }
            }
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }
}
