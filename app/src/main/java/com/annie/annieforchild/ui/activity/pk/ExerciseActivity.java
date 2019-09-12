package com.annie.annieforchild.ui.activity.pk;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.AudioBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.interactor.imp.CrashHandlerInteractorImp;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.Exercise_newAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * pk——练习(弃用)
 * Created by wanglei on 2018/3/30.
 */

public class ExerciseActivity extends BaseActivity implements OnCheckDoubleClick, SongView {
    private ImageView back, pageImage, empty;
    private TextView challenge, last, next, page;
    private XRecyclerView exerciseList;
    private List<Line> lists;
    private Book book;
    private Exercise_newAdapter adapter;
    private GrindEarPresenter presenter;
    private Intent intent;
    private int bookId;
    private String imageUrl;
    private AlertHelper helper;
    private Dialog dialog;
    private int currentPage = 1;
    private int totalPage;
    private boolean isLast = false;
    private boolean isNext = true;
    public static boolean isPlay;
    private int audioType, audioSource, homeworkid, homeworktype;
    private String title;
    private CheckDoubleClickListener listener;
    private CrashHandlerInteractorImp interactor;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.exercise_back);
        challenge = findViewById(R.id.go_to_challenge);
        exerciseList = findViewById(R.id.exercise_list);
        last = findViewById(R.id.last_page);
        next = findViewById(R.id.next_page);
        page = findViewById(R.id.book_page);
        pageImage = findViewById(R.id.page_image);
        empty = findViewById(R.id.exercise_empty);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        challenge.setOnClickListener(listener);
        last.setOnClickListener(listener);
        next.setOnClickListener(listener);
        RecyclerLinearLayoutManager manager = new RecyclerLinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setScrollEnabled(false);
        exerciseList.setLayoutManager(manager);
        exerciseList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        exerciseList.setPullRefreshEnabled(false);
        exerciseList.setLoadingMoreEnabled(false);
        exerciseList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        intent = getIntent();
        bookId = intent.getIntExtra("bookId", 0);
        audioType = intent.getIntExtra("audioType", 0);
        audioSource = intent.getIntExtra("audioSource", 3);
        imageUrl = intent.getStringExtra("imageUrl");
        title = intent.getStringExtra("title");
        homeworkid = intent.getIntExtra("homeworkid", 0);
        homeworktype=intent.getIntExtra("homeworktype",-1);

        if (audioSource != 1 || audioSource != 3 || audioSource != 9) {
            pageImage.setVisibility(View.VISIBLE);
        } else {
            pageImage.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        interactor = new CrashHandlerInteractorImp(null,1);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        lists = new ArrayList<>();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();

        adapter = new Exercise_newAdapter(interactor,this,this, this, title, lists, bookId, presenter, audioType, audioSource, imageUrl, 1, homeworkid, homeworktype, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int positon = exerciseList.getChildAdapterPosition(view);
                if (lists.get(positon - 1).isSelect()) {
                    lists.get(positon - 1).setSelect(false);
                } else {
                    for (int i = 0; i < lists.size(); i++) {
                        lists.get(i).setSelect(false);
                    }
                    lists.get(positon - 1).setSelect(true);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        exerciseList.setAdapter(adapter);
        presenter.getBookAudioData(bookId, 0, null);
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
        if (message.what == MethodCode.EVENT_PK_EXERCISE) {
            book = (Book) message.obj;
            if (book != null) {
                initialize();
            }
        } else if (message.what == MethodCode.EVENT_UPLOADAUDIO) {
            AudioBean bean = (AudioBean) message.obj;
            book.getPageContent().get(currentPage).getLineContent().get(bean.getLineId()).setMyResourceUrl(bean.getResourceUrl());
            refresh();
        }
    }

    private void initialize() {
        if (book.getBookTotalPages() == 0 || book.getPageContent().size() == 0) {
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
        }
        totalPage = book.getBookTotalPages();
        if (audioSource != 1 || audioSource != 3 || audioSource != 9) {
            Glide.with(this).load(book.getPageContent().get(currentPage - 1).getPageImage()).into(pageImage);
        }
        currentPage = 1;
        page.setText(currentPage + "/" + totalPage);
        if (totalPage == 1) {
            isLast = false;
            isNext = false;
            last.setTextColor(getResources().getColor(R.color.text_black));
            next.setTextColor(getResources().getColor(R.color.text_black));
        } else {
            isLast = false;
            isNext = true;
            last.setTextColor(getResources().getColor(R.color.text_black));
            next.setTextColor(getResources().getColor(R.color.text_orange));
        }
        lists.clear();
        lists.addAll(book.getPageContent().get(currentPage - 1).getLineContent());
        adapter.notifyDataSetChanged();
    }

    private void refresh() {
        lists.clear();
        lists.addAll(book.getPageContent().get(currentPage - 1).getLineContent());
        adapter.notifyDataSetChanged();
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
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.stopMedia();
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.exercise_back:
                finish();
                break;
            case R.id.go_to_challenge:
                Intent intent = new Intent(this, ChallengeActivity.class);
                intent.putExtra("bookId", bookId);
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("audioType", audioType);
                intent.putExtra("audioSource", audioSource);
                intent.putExtra("homeworkid", homeworkid);
                intent.putExtra("homeworktype", homeworktype);
                startActivity(intent);
                finish();
                break;
            case R.id.last_page:
                if (isLast) {
                    currentPage--;
                    if (currentPage == 1) {
                        last.setTextColor(getResources().getColor(R.color.text_black));
                        next.setTextColor(getResources().getColor(R.color.text_orange));
                        isLast = false;
                        isNext = true;
                    } else {
                        last.setTextColor(getResources().getColor(R.color.text_orange));
                        next.setTextColor(getResources().getColor(R.color.text_orange));
                        isNext = true;
                    }
                    page.setText(currentPage + "/" + totalPage);
                    Glide.with(this).load(book.getPageContent().get(currentPage - 1).getPageImage()).into(pageImage);
                    refresh();
                } else {
                    break;
                }
                break;
            case R.id.next_page:
                if (isNext) {
                    currentPage++;
                    if (currentPage == totalPage) {
                        next.setTextColor(getResources().getColor(R.color.text_black));
                        last.setTextColor(getResources().getColor(R.color.text_orange));
                        isNext = false;
                        isLast = true;
                    } else {
                        next.setTextColor(getResources().getColor(R.color.text_orange));
                        last.setTextColor(getResources().getColor(R.color.text_orange));
                        isLast = true;
                    }
                    page.setText(currentPage + "/" + totalPage);
                    Glide.with(this).load(book.getPageContent().get(currentPage - 1).getPageImage()).into(pageImage);
                    refresh();
                } else {
                    break;
                }
                break;
        }
    }
}
