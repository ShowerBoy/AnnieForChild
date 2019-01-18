package com.annie.annieforchild.ui.activity.pk;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.ReleaseUrl;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.fragment.book.BookPlayEndFragment;
import com.annie.annieforchild.ui.fragment.book.BookPlayFragment;
import com.annie.annieforchild.ui.fragment.book.BookPlayFragment2;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglei on 2018/10/9.
 */

public class BookPlayActivity2 extends BaseActivity implements SongView, OnCheckDoubleClick {
    public static APSTSViewPager viewPager;
    private RelativeLayout bookPlay2Layout, pageLayout;
    private ImageView back;
    private static LottieAnimationView animationView;
    private static ImageView clarityBack;
    public static ImageView playTotal2;
    private TextView page, bookTitle;
    public static TextView playTotal;
    private GrindEarPresenter presenter;
    private BookPlayFragmentAdapter fragmentAdapter;
    private Intent intent;
    private List<BookPlayFragment> lists;
    private List<BookPlayFragment2> lists2; //针对章节书
    private Book book;
    private int bookId, totalPage;
    private String imageUrl, title;
    private int audioType, audioSource, homeworkid;
    private AlertHelper helper;
    private Dialog dialog;
    private BookPlayEndFragment bookPlayEndFragment;
    public static List<ReleaseUrl> releaseList; //判断是否发布
    private CheckDoubleClickListener listener;
    public static int nectarCount = 0;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_play2;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewPager);
        back = findViewById(R.id.book_play_back2);
        playTotal = findViewById(R.id.play_total);
        playTotal2 = findViewById(R.id.play_total2);
        page = findViewById(R.id.books_page);
        clarityBack = findViewById(R.id.clarity_back);
        bookPlay2Layout = findViewById(R.id.book_play2_layout);
        pageLayout = findViewById(R.id.book_play_relative);
        animationView = findViewById(R.id.animation_view);
        bookTitle = findViewById(R.id.book_play_title);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        playTotal.setOnClickListener(listener);
        playTotal2.setOnClickListener(listener);

        intent = getIntent();
        bookId = intent.getIntExtra("bookId", 0);
        audioType = intent.getIntExtra("audioType", 0);
        audioSource = intent.getIntExtra("audioSource", 3);
        imageUrl = intent.getStringExtra("imageUrl");
        title = intent.getStringExtra("title");
        homeworkid = intent.getIntExtra("homeworkid", 0);

        playTotal2.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        lists2 = new ArrayList<>();
        releaseList = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        bookPlayEndFragment = new BookPlayEndFragment();

        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", imageUrl);
        bundle.putInt("bookId", bookId);
        bundle.putString("bookName", title);
        bundle.putInt("homeworkid", homeworkid);
        bundle.putInt("audioType", audioType);
        bookTitle.setText(title);
        bookPlayEndFragment.setArguments(bundle);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getBookAudioData(bookId, 4, null);
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
        }
    }

    private void initialize() {
        lists.clear();
        lists2.clear();
        totalPage = book.getBookTotalPages();
//        if (audioSource == 8) {
//            for (int i = 0; i < totalPage; i++) {
//                BookPlayFragment2 fragment = new BookPlayFragment2();
//                Bundle bundle = new Bundle();
//                bundle.putInt("tag", i);
//                bundle.putSerializable("page", book.getPageContent().get(i));
//                bundle.putInt("audioType", audioType);
//                bundle.putInt("audioSource", audioSource);
//                bundle.putInt("bookId", bookId);
//                bundle.putString("imageUrl", imageUrl);
//                bundle.putString("title", title);
//                fragment.setArguments(bundle);
//                lists2.add(fragment);
//            }
//        } else {
        for (int i = 0; i < totalPage; i++) {
            BookPlayFragment fragment = new BookPlayFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tag", i);
            bundle.putSerializable("page", book.getPageContent().get(i));
            bundle.putInt("audioType", audioType);
            bundle.putInt("audioSource", audioSource);
            bundle.putInt("bookId", bookId);
            bundle.putString("imageUrl", imageUrl);
            bundle.putString("title", title);
            fragment.setArguments(bundle);
            lists.add(fragment);
        }
//        }

        fragmentAdapter = new BookPlayFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /**
                 * {@link BookPlayFragment#onMainEventThread(JTMessage)}
                 */
                page.setText((position + 1) + "/" + totalPage);
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_PAGEPLAY;
                message.obj = position;
                EventBus.getDefault().post(message);
                if (position == lists.size()) {
                    bookPlay2Layout.setVisibility(View.GONE);
                    pageLayout.setVisibility(View.GONE);
                    viewPager.setNoFocus(true);
                    nectarCount = 0;
                    for (int i = 0; i < releaseList.size(); i++) {
                        List<Integer> lists = releaseList.get(i).getNectarList();
                        nectarCount = nectarCount + lists.size();
                    }
                    if (nectarCount == 0) {

                    } else {
                        SystemUtils.setBackGray(BookPlayActivity2.this, true);
                        SystemUtils.getNectarCongratulation(BookPlayActivity2.this, nectarCount).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    bookPlay2Layout.setVisibility(View.VISIBLE);
                    pageLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        page.setText(1 + "/" + totalPage);
        releaseList.clear();
        for (int i = 0; i < lists.size(); i++) {
            if (book.getPageContent().get(i).getMyResourceUrl() != null && book.getPageContent().get(i).getMyResourceUrl().length() != 0) {
                releaseList.add(new ReleaseUrl(true, new ArrayList<>(), book.getPageContent().get(i).getMyResourceUrl()));
            } else {
                releaseList.add(new ReleaseUrl(false, new ArrayList<>(), ""));
            }
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
            case R.id.book_play_back2:
                if (BookPlayFragment.isRecord) {
                    return;
                }
                finish();
                break;
//            case R.id.play_total:
//                if (BookPlayFragment.isRecord) {
//                    return;
//                }
//                if (BookPlayFragment.isPlay) {
//                    return;
//                }
//                if (!SystemUtils.isPlaying) {
//                    if (!SystemUtils.playAll) {
////                        playTotal.setText("停止播放");
//                        playTotal2.setImageResource(R.drawable.icon_full_reading_t);
//                        SystemUtils.playAll = true;
//                        SystemUtils.currentPage = 0;
//                        SystemUtils.currentLine = 0;
//                        SystemUtils.totalPage = totalPage;
//                        scrolltoPosition(0);
//                        viewPager.setNoFocus(true);
//                    } else {
////                        playTotal.setText("全文播放");
//                        playTotal2.setImageResource(R.drawable.icon_full_reading_f);
//                        /**
//                         * {@link BookPlayFragment#onMainEventThread(JTMessage)}
//                         */
//                        JTMessage message = new JTMessage();
//                        message.what = MethodCode.EVENT_PLAYING;
//                        message.obj = 1;
//                        EventBus.getDefault().post(message);
//                    }
//                }
//                break;
            case R.id.play_total2:
                if (BookPlayFragment.isRecord) {
                    return;
                }
                if (BookPlayFragment.isPlay) {
                    return;
                }
                if (!SystemUtils.isPlaying) {
                    if (!SystemUtils.playAll) {
//                        playTotal.setText("停止播放");
                        playTotal2.setImageResource(R.drawable.icon_full_reading_t);
                        SystemUtils.playAll = true;
                        SystemUtils.currentPage = 0;
                        SystemUtils.currentLine = 0;
                        SystemUtils.totalPage = totalPage;
                        scrolltoPosition(0);
                        viewPager.setNoFocus(true);
                    } else {
//                        playTotal.setText("全文播放");
                        playTotal2.setImageResource(R.drawable.icon_full_reading_f);
                        /**
                         * {@link BookPlayFragment#onMainEventThread(JTMessage)}
                         */
                        JTMessage message = new JTMessage();
                        message.what = MethodCode.EVENT_PLAYING;
                        message.obj = 1;
                        EventBus.getDefault().post(message);
                    }
                }
                break;
        }
    }

    class BookPlayFragmentAdapter extends FragmentStatePagerAdapter {

        public BookPlayFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            if (audioSource == 8) {
//                return lists2.get(position);
//            } else {
            if (position < lists.size()) {
                return lists.get(position);
            } else {
                return bookPlayEndFragment;
            }
//            }
        }

        @Override
        public int getCount() {
//            if (audioSource == 8) {
//                return lists2.size();
//            } else {
            return lists.size() + 1;
//            }
        }
    }

    public void scrolltoPosition(int position) {
        viewPager.setCurrentItem(position);
        lists.get(position).playAll();
    }

    public static void setLottieAnimation(int animationCode, int page) {
        animationView.setVisibility(View.VISIBLE);
        switch (animationCode) {
            case 1:
                animationView.setImageAssetsFolder("amazing/");
                animationView.setAnimation("amazing.json");
                break;
            case 2:
                animationView.setImageAssetsFolder("awesome/");
                animationView.setAnimation("awesome.json");
                break;
            case 3:
                animationView.setImageAssetsFolder("bingo/");
                animationView.setAnimation("bingo.json");
                break;
            case 4:
                animationView.setImageAssetsFolder("excellent/");
                animationView.setAnimation("excellent.json");
                break;
            case 5:
                animationView.setImageAssetsFolder("good_observation/");
                animationView.setAnimation("good_observation.json");
                break;
            case 6:
                animationView.setImageAssetsFolder("good_try/");
                animationView.setAnimation("good_try.json");
                break;
            case 7:
                animationView.setImageAssetsFolder("great/");
                animationView.setAnimation("great.json");
                break;
            case 8:
                animationView.setImageAssetsFolder("great_job/");
                animationView.setAnimation("great_job.json");
                break;
            case 9:
                animationView.setImageAssetsFolder("nice_going/");
                animationView.setAnimation("nice_going.json");
                break;
            case 10:
                animationView.setImageAssetsFolder("super/");
                animationView.setAnimation("super.json");
                break;
        }

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                viewPager.setNoFocus(true);
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_GONE;
                message.obj = page;
                EventBus.getDefault().post(message);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setClarity(false);
                animationView.setVisibility(View.GONE);
                viewPager.setNoFocus(false);
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_VISIBILITY;
                message.obj = page;
                EventBus.getDefault().post(message);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                setClarity(false);
                animationView.setVisibility(View.GONE);
                viewPager.setNoFocus(false);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animationView.loop(false);
        animationView.playAnimation();
        SystemUtils.animPool.play(SystemUtils.animMusicMap.get(animationCode), 1, 1, 0, 0, 1);

    }

    public static void setClarity(boolean tag) {
        if (tag) {
            clarityBack.setVisibility(View.VISIBLE);
        } else {
            clarityBack.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemUtils.playAll = false;
        SystemUtils.isPlaying = false;
        viewPager.setNoFocus(false);
    }
}
