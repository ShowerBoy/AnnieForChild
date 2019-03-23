package com.annie.annieforchild.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.SearchTag;
import com.annie.annieforchild.bean.Tags;
import com.annie.annieforchild.bean.login.HistoryRecord;
import com.annie.annieforchild.bean.search.Books;
import com.annie.annieforchild.bean.search.SearchContent;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.presenter.imp.LoginPresenterImp;
import com.annie.annieforchild.ui.activity.pk.BookPlayActivity2;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.SearchAdapter;
import com.annie.annieforchild.ui.adapter.TagAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.LoginView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.google.android.flexbox.FlexboxLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.lang.invoke.MethodHandle;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/5/4.
 */

public class GlobalSearchActivity extends BaseActivity implements LoginView, View.OnClickListener {
    private SearchView searchView;
    private EditText editText, hintText;
    private LinearLayout historyLinear;
    private RelativeLayout highSearchLayout;
    private FlexboxLayout historyFlexbox;
    private TextView clearHistory, confirm, chongzhi, back;
    private ImageView empty;
    private XRecyclerView recycler;
    private RecyclerView tagRecycler;
    private LoginPresenter presenter;
    private GrindEarPresenter presenter2;
    private PopupWindow popupWindow;
    private View popupView, underLine;
    private List<Books> lists;
    private List<SearchTag> tagLists;
    private TagAdapter tagAdapter;
    private SearchAdapter adapter;
    private AlertHelper helper;
    private Dialog dialog;
    private SearchContent searchContent;
    private String keyword;
    private int page = 1;
    private int tag, startLeft; //0:关键字搜索 1:高级搜索
    Filter filter;
    private int classId = -11000;

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
        back = findViewById(R.id.global_back);
        historyFlexbox = findViewById(R.id.history_flexbox);
        historyLinear = findViewById(R.id.history_linear);
        clearHistory = findViewById(R.id.clear_history);
//        highSearch = findViewById(R.id.global_high_search);
        underLine = searchView.findViewById(R.id.search_plate);

        highSearchLayout = findViewById(R.id.high_search_layout);
        empty = findViewById(R.id.empty_data);
        editText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setTextSize(16);
        back.setOnClickListener(this);
//        highSearch.setOnClickListener(this);
        clearHistory.setOnClickListener(this);
        highSearchLayout.setOnClickListener(this);
        underLine.setBackgroundColor(getResources().getColor(R.color.divider));
//        underLine.setVisibility(View.GONE);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setPullRefreshEnabled(false);
        recycler.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                if (page > searchContent.getPageCount()) {
                    page--;
                    recycler.loadMoreComplete();
                } else {
                    if (tag == 0) {
                        presenter.globalSearch(keyword, page);
                    } else {
                        presenter.getTagBook(presenter.getAgeList(), presenter.getFunctionList(), presenter.getThemeList(), presenter.getTypeList(), presenter.getSeriesList(), page);
                    }
                }
            }
        });
        recycler.setLayoutManager(manager);

        startLeft = Math.min(application.getSystemUtils().getWindow_width(), application.getSystemUtils().getWindow_height()) * 1 / 4;
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_popup_global_search, null, false);
        confirm = popupView.findViewById(R.id.global_search_queding);
        chongzhi = popupView.findViewById(R.id.global_search_chongzhi);
        confirm.setOnClickListener(this);
        chongzhi.setOnClickListener(this);
        tagRecycler = popupView.findViewById(R.id.tag_recycler2);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        tagRecycler.setLayoutManager(manager1);

        popupWindow = new PopupWindow(popupView, Math.min(application.getSystemUtils().getWindow_width(), application.getSystemUtils().getWindow_height()) * 4 / 5, LinearLayout.LayoutParams.MATCH_PARENT, false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtils.setBackGray(GlobalSearchActivity.this, false);
            }
        });
        popupWindow.setContentView(popupView);
        SQLiteDatabase db = LitePal.getDatabase();
        try {
//        DataSupport.deleteAll(HistoryRecord.class);
            List<HistoryRecord> historyList = LitePal.findAll(HistoryRecord.class);
            if (historyList != null && historyList.size() != 0) {
                if (application.getSystemUtils().getHistoryRecordList() == null) {
                    application.getSystemUtils().setHistoryRecordList(new ArrayList<>());
                } else {
                    application.getSystemUtils().getHistoryRecordList().clear();
                }
                application.getSystemUtils().getHistoryRecordList().addAll(historyList);
                historyFlexbox.removeAllViews();
                int listSize = application.getSystemUtils().getHistoryRecordList().size();
                for (int i = 0; i < listSize; i++) {
                    historyFlexbox.addView(createFlexItem(application.getSystemUtils().getHistoryRecordList().get(listSize - 1 - i)));
                }
                historyLinear.setVisibility(View.VISIBLE);
            } else {
                application.getSystemUtils().setHistoryRecordList(new ArrayList<>());
                historyLinear.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        tagLists = new ArrayList<>();
        presenter = new LoginPresenterImp(this, this);
        presenter2 = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter2.initViewAndData();
        adapter = new SearchAdapter(this, lists, new OnRecyclerItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view) - 1;
                if (lists.get(position).getAnimationUrl() == null) {
                    if (lists.get(position).getJurisdiction() == 0) {
                        if (lists.get(position).getIsusenectar() == 1) {
                            SystemUtils.setBackGray(GlobalSearchActivity.this, true);
                            SystemUtils.getSuggestPopup(GlobalSearchActivity.this, "需要" + lists.get(position).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, lists.get(position).getNectar(), lists.get(position).getBookName(), lists.get(position).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                        }
                    } else {
                        Song song = new Song();
                        song.setBookId(lists.get(position).getBookId());
                        song.setBookImageUrl(lists.get(position).getBookImageUrl());
                        song.setBookName(lists.get(position).getBookName());
                        song.setIsmoerduo(lists.get(position).getIsmoerduo());
                        song.setIsyuedu(lists.get(position).getIsyuedu());
                        song.setIskouyu(lists.get(position).getIskouyu());
                        if (lists.get(position).getIsmoerduo() == 1 && lists.get(position).getIsyuedu() == 1) {
                            SystemUtils.setBackGray(GlobalSearchActivity.this, true);
                            SystemUtils.getBookPopup(GlobalSearchActivity.this, song, 0, 3, 0, 0, lists.get(position).getTag()).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                        } else {
                            if (lists.get(position).getIsyuedu() == 1) {
                                if (lists.get(position).getTag().equals("校园生活故事1") || lists.get(position).getTag().equals("校园生活故事2") || lists.get(position).getTag().equals("神奇树屋")) {
                                    if (MusicService.isPlay) {
//                                        if (musicService != null) {
//                                            musicService.stop();
//                                        }
                                        MusicService.stop();
                                    }
                                    Intent intent = new Intent(GlobalSearchActivity.this, BookPlayActivity2.class);
                                    intent.putExtra("bookId", song.getBookId());
                                    intent.putExtra("imageUrl", song.getBookImageUrl());
                                    intent.putExtra("audioType", 2);
                                    intent.putExtra("audioSource", 8);
                                    intent.putExtra("title", song.getBookName());
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(GlobalSearchActivity.this, PracticeActivity.class);
                                    intent.putExtra("song", song);
                                    intent.putExtra("type", 0);
                                    intent.putExtra("audioType", 1);
                                    intent.putExtra("collectType", 2);
                                    intent.putExtra("bookType", 1);
                                    intent.putExtra("audioSource", 0);
                                    startActivity(intent);
                                }
                            } else if (lists.get(position).getIskouyu() == 1) {
                                Intent intent = new Intent(GlobalSearchActivity.this, PracticeActivity.class);
                                intent.putExtra("song", song);
                                intent.putExtra("type", 0);
                                intent.putExtra("audioType", 2);
                                intent.putExtra("audioSource", 0);
                                intent.putExtra("collectType", 0);
                                intent.putExtra("bookType", 1);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(GlobalSearchActivity.this, PracticeActivity.class);
                                intent.putExtra("song", song);
                                intent.putExtra("type", 0);
                                intent.putExtra("audioType", 0);
                                intent.putExtra("audioSource", 0);
                                intent.putExtra("collectType", 1);
                                intent.putExtra("bookType", 0);
                                startActivity(intent);
                            }
                        }
                    }
                } else {
                    if (lists.get(position).getJurisdiction() == 1) {
                        SystemUtils.startVideo(GlobalSearchActivity.this, lists.get(position).getAnimationUrl());
//                        Intent intent = new Intent(GlobalSearchActivity.this, VideoActivity.class);
//                        intent.putExtra("url", lists.get(position).getAnimationUrl());
//                        intent.putExtra("imageUrl", lists.get(position).getBookImageUrl());
//                        intent.putExtra("name", lists.get(position).getBookName());
//                        intent.putExtra("id", lists.get(position).getBookId());
//                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
        tagAdapter = new TagAdapter(this, presenter, tagLists);
        tagRecycler.setAdapter(tagAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                searchListView.setVisibility(View.GONE);
//                searchList.add(query);
//                arrayAdapter.notifyDataSetChanged();

                if (application.getSystemUtils().getHistoryRecordList() != null) {
                    if (application.getSystemUtils().getHistoryRecordList().size() == 0) {
                        HistoryRecord historyRecord = new HistoryRecord();
                        historyRecord.setName(query);
                        historyRecord.save();
                        application.getSystemUtils().getHistoryRecordList().add(historyRecord);
                    } else {
                        int j = application.getSystemUtils().getHistoryRecordList().size();
                        boolean same = false;
                        for (int i = 0; i < j; i++) {
                            if (application.getSystemUtils().getHistoryRecordList().get(i).getName().equals(query)) {
                                same = true;
                            }
                        }
                        if (!same) {
                            HistoryRecord historyRecord = new HistoryRecord();
                            historyRecord.setName(query);
                            historyFlexbox.addView(createFlexItem(historyRecord), 0);
                            if (historyFlexbox.getFlexLines().size() > 3) {
                                historyFlexbox.removeViewAt(0);
                                historyRecord.save();
                                application.getSystemUtils().getHistoryRecordList().remove(0);
                                application.getSystemUtils().getHistoryRecordList().add(historyRecord);
                            } else {
                                historyRecord.save();
                                application.getSystemUtils().getHistoryRecordList().add(historyRecord);
                            }
                        }
                    }
                }
                page = 1;
                keyword = query;
                tag = 0;
                presenter.globalSearch(keyword, page);
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
        presenter.getTags();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_back:
                finish();
                break;
            case R.id.clear_history:
                //清空历史记录
                SystemUtils.GeneralDialog(this, "清空")
                        .setMessage("确定清空历史记录？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                application.getSystemUtils().getHistoryRecordList().clear();
                                LitePal.deleteAll(HistoryRecord.class);
                                historyLinear.setVisibility(View.GONE);
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
//            case R.id.global_high_search:
//                for (int i = 0; i < tagLists.size(); i++) {
//                    List<Tags> list = tagLists.get(i).getList();
//                    for (int j = 0; j < list.size(); j++) {
//                        list.get(j).setSelect(false);
//                    }
//                }
//                presenter.clearTags();
//                tagAdapter.notifyDataSetChanged();
//                SystemUtils.setBackGray(GlobalSearchActivity.this, true);
//                popupWindow.showAtLocation(popupView, Gravity.CENTER, startLeft, 0);
//                break;
            case R.id.global_search_queding:
                if (presenter.getAgeList().size() == 0
                        && presenter.getFunctionList().size() == 0
                        && presenter.getThemeList().size() == 0
                        && presenter.getTypeList().size() == 0
                        && presenter.getSeriesList().size() == 0) {
                    popupWindow.dismiss();
                } else {
                    page = 1;
                    tag = 1;
                    presenter.getTagBook(presenter.getAgeList(), presenter.getFunctionList(), presenter.getThemeList(), presenter.getTypeList(), presenter.getSeriesList(), page);
                    popupWindow.dismiss();
                }
                break;
            case R.id.global_search_chongzhi:
                for (int i = 0; i < tagLists.size(); i++) {
                    List<Tags> list = tagLists.get(i).getList();
                    for (int j = 0; j < list.size(); j++) {
                        list.get(j).setSelect(false);
                    }
                }
                presenter.clearTags();
                tagAdapter.notifyDataSetChanged();
                break;
            case R.id.high_search_layout:
                for (int i = 0; i < tagLists.size(); i++) {
                    List<Tags> list = tagLists.get(i).getList();
                    for (int j = 0; j < list.size(); j++) {
                        list.get(j).setSelect(false);
                    }
                }
                presenter.clearTags();
                tagAdapter.notifyDataSetChanged();
                SystemUtils.setBackGray(GlobalSearchActivity.this, true);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, startLeft, 0);
                break;
        }
    }

    private void refreshFlexBoxLayout() {
//        historyLinear.setVisibility(View.VISIBLE);
        historyLinear.setVisibility(View.GONE);
        historyFlexbox.removeAllViews();
        int listSize = application.getSystemUtils().getHistoryRecordList().size();
        for (int i = 0; i < listSize; i++) {
            historyFlexbox.addView(createFlexItem(application.getSystemUtils().getHistoryRecordList().get(listSize - 1 - i)));
        }
    }

    /**
     * {@link LoginPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GLOBALSEARCH) {
            refreshFlexBoxLayout();
            searchContent = (SearchContent) message.obj;
            if (searchContent.getContent() != null) {
                recycler.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                if (page == 1) {
                    lists.clear();
                }
                lists.addAll(searchContent.getContent());
                recycler.loadMoreComplete();
            } else {
                recycler.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
//            if (lists.size() == 0) {
//                empty.setVisibility(View.VISIBLE);
//            } else {
//                empty.setVisibility(View.GONE);
//            }
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_GETTAGS) {
            List<SearchTag> lists = (List<SearchTag>) message.obj;
            tagLists.clear();
            tagLists.addAll(lists);
            tagAdapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_GETTAGBOOK) {
//            List<Books> lists = (List<SearchTag>) message.obj;
            searchContent = (SearchContent) message.obj;
            historyLinear.setVisibility(View.GONE);
            if (searchContent.getContent() != null) {
                recycler.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                if (page == 1) {
                    lists.clear();
                }
                lists.addAll(searchContent.getContent());
                recycler.loadMoreComplete();
            } else {
                recycler.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_UNLOCKBOOK + 9000 + classId) {
            showInfo((String) message.obj);
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        searchView.clearFocus();
//        back.setFocusable(true);
    }

    private TextView createFlexItem(HistoryRecord historyRecord) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(historyRecord.getName());
        textView.setTextSize(14);
        textView.setBackgroundResource(R.drawable.relative_gray_back);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                keyword = historyRecord.getName();
                tag = 0;
                editText.setText(historyRecord.getName());
                presenter.globalSearch(historyRecord.getName(), page);
            }
        });
        int padding = SystemUtils.dpToPixel(this, 4);
        int paddingLeftAndRight = SystemUtils.dpToPixel(this, 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = SystemUtils.dpToPixel(this, 6);
        int marginTop = SystemUtils.dpToPixel(this, 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }
}
