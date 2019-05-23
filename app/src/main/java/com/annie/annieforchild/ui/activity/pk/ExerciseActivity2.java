package com.annie.annieforchild.ui.activity.pk;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.ExerciseAdapter;
import com.annie.annieforchild.ui.adapter.Exercise_newAdapter;
import com.annie.annieforchild.ui.fragment.song.ExerciseFragment;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 练习——滑动
 * Created by wanglei on 2018/10/15.
 */

public class ExerciseActivity2 extends BaseActivity implements OnCheckDoubleClick, SongView {
    public static APSTSViewPager viewPager;
    private RelativeLayout pageLayout;
    private ImageView back, empty;
    private TextView page, gotoChallenge;
    private Book book;
    private int bookId, totalPage;
    private String imageUrl, title;
    private int audioType, audioSource, homeworkid, homeworktype;
    private Intent intent;
    private AlertHelper helper;
    private Dialog dialog;
    private List<ExerciseFragment> lists;
    private GrindEarPresenter presenter;
    private ExerciseFragmentAdapter fragmentAdapter;
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise2;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.exercise_viewPager);
        back = findViewById(R.id.exercise_back2);
        page = findViewById(R.id.exercise_page);
        empty = findViewById(R.id.exercise2_empty);
        pageLayout = findViewById(R.id.exercise_relative);
        gotoChallenge = findViewById(R.id.go_to_challenge2);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        gotoChallenge.setOnClickListener(listener);

        intent = getIntent();
        if (intent != null) {
            bookId = intent.getIntExtra("bookId", 0);
            audioType = intent.getIntExtra("audioType", 0);
            audioSource = intent.getIntExtra("audioSource", 3);
            imageUrl = intent.getStringExtra("imageUrl");
            title = intent.getStringExtra("title");
            homeworkid = intent.getIntExtra("homeworkid", 0);
            homeworktype = intent.getIntExtra("homeworktype", -1);
        }
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();

        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getBookAudioData(bookId, 0, null);
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
            } else {
                empty.setVisibility(View.VISIBLE);
                pageLayout.setVisibility(View.GONE);
            }
        }
    }

    private void initialize() {
        totalPage = book.getBookTotalPages();
        if (totalPage == 0) {
            empty.setVisibility(View.VISIBLE);
            pageLayout.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.GONE);
            pageLayout.setVisibility(View.VISIBLE);
            lists.clear();
            for (int i = 0; i < totalPage; i++) {
                ExerciseFragment fragment = new ExerciseFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("tag", i);
                bundle.putSerializable("page", book.getPageContent().get(i));
                bundle.putInt("audioType", audioType);
                bundle.putInt("audioSource", audioSource);
                bundle.putInt("bookId", bookId);
                bundle.putString("title", title);
                bundle.putInt("homeworkid", homeworkid);
                bundle.putInt("homeworktype", homeworktype);
                fragment.setArguments(bundle);
                lists.add(fragment);
            }

            fragmentAdapter = new ExerciseFragmentAdapter(getSupportFragmentManager());
            viewPager.setAdapter(fragmentAdapter);
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    /**
                     * {@link ExerciseFragment#onMainEventThread(JTMessage)}
                     */
                    page.setText((position + 1) + "/" + totalPage);
                    JTMessage message = new JTMessage();
                    message.what = MethodCode.EVENT_MUSICPLAY;
                    message.obj = position;
                    EventBus.getDefault().post(message);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            page.setText(1 + "/" + totalPage);
        }

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.exercise_back2:
                if (Exercise_newAdapter.isRecording) {
                    return;
                }
                finish();
                break;
            case R.id.go_to_challenge2:
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
        }
    }

    class ExerciseFragmentAdapter extends FragmentStatePagerAdapter {

        public ExerciseFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return lists.get(position);
        }

        @Override
        public int getCount() {
            return lists.size();
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
