package com.annie.annieforchild.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.search.BookClassify;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.presenter.MainPresenter;
import com.annie.annieforchild.presenter.imp.LoginPresenterImp;
import com.annie.annieforchild.presenter.imp.MainPresenterImp;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.GlobalSearchAdapter;
import com.annie.annieforchild.ui.adapter.SearchAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.LoginView;
import com.annie.annieforchild.view.MainView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.daimajia.slider.library.SliderLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wanglei on 2018/5/4.
 */

public class GlobalSearchActivity extends BaseActivity implements LoginView, View.OnClickListener {
    private SearchView searchView;
    private ListView searchListView;
    private RecyclerView recycler;
    private LoginPresenter presenter;
    private List<BookClassify> lists;
    private SearchAdapter adapter;
    private AlertHelper helper;
    private Dialog dialog;
    private List<String> searchList;
    private GlobalSearchAdapter arrayAdapter;
    Filter filter;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_global_search;
    }

    @Override
    protected void initView() {
        searchView = findViewById(R.id.global_search);
        recycler = findViewById(R.id.search_recycler);
        searchListView = findViewById(R.id.search_listView);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        searchList = new ArrayList<>();
        lists = new ArrayList<>();
        adapter = new SearchAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);

                for (int i = 0; i < lists.size(); i++) {
                    if (position <= lists.get(i).getBook().size()) {
                        Song song = new Song();
                        song.setBookId(lists.get(i).getBook().get(position - 1).getBookId());
                        song.setBookImageUrl(lists.get(i).getBook().get(position - 1).getBookImageUrl());
                        song.setBookName(lists.get(i).getBook().get(position - 1).getBookName());
                        Intent intent = new Intent(GlobalSearchActivity.this, PracticeActivity.class);
                        intent.putExtra("song", song);
                        intent.putExtra("audioType", 3);
                        intent.putExtra("audioSource", 0);
                        startActivity(intent);
                        finish();
                        break;
                    } else {
                        position = position - lists.get(i).getBook().size() - 1;
                    }
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
        presenter = new LoginPresenterImp(this, this);
        presenter.initViewAndData();
        arrayAdapter = new GlobalSearchAdapter(searchList, this);
        filter = arrayAdapter.getFilter();
        searchListView.setAdapter(arrayAdapter);
        searchListView.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                searchListView.setVisibility(View.GONE);
//                searchList.add(query);
//                arrayAdapter.notifyDataSetChanged();
                presenter.globalSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                searchListView.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(newText)) {
                    // 清除ListView的过滤
//                    searchListView.clearTextFilter();
                } else {
                    // 使用用户输入的内容对ListView的列表项进行过滤
//                    filter.filter(newText);
//                    searchListView.setFilterText(newText);
                }
                return true;
            }
        });

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * {@link LoginPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GLOBALSEARCH) {
            lists.clear();
            lists.addAll((List<BookClassify>) message.obj);
            adapter.notifyDataSetChanged();
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
