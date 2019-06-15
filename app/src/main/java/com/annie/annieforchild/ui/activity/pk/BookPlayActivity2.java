package com.annie.annieforchild.ui.activity.pk;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ShareBean;
import com.annie.annieforchild.bean.ShareCoin;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.book.ReleaseUrl;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.fragment.book.BookPlayEndFragment;
import com.annie.annieforchild.ui.fragment.book.BookPlayFragment;
import com.annie.annieforchild.ui.fragment.book.BookPlayFragment2;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BaseMusicActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by wanglei on 2018/10/9.
 */

public class BookPlayActivity2 extends BaseMusicActivity implements PlatformActionListener, SongView, OnCheckDoubleClick {
    public static APSTSViewPager viewPager;
    private RelativeLayout bookPlay2Layout, pageLayout;
    private ImageView back, share, pengyouquan, weixin, qq, qqzone, nextDrop;
    private Button iknow;
    private LinearLayout dropRecording, dropSong;
    private static LottieAnimationView animationView;
    private static ImageView clarityBack;
    public static ImageView playTotal2;
    private TextView page, bookTitle, shareCancel, coinCount;
    public static TextView playTotal;
    private GrindEarPresenter presenter;
    private BookPlayFragmentAdapter fragmentAdapter;
    private Intent intent;
    private PopupWindow popupWindow, popupWindow2;
    private View popupView, popupView2;
    private List<BookPlayFragment> lists;
    private List<BookPlayFragment2> lists2; //针对章节书
    private Book book;
    private int bookId, totalPage, shareType;
    private String imageUrl, title;
    private int audioType, audioSource, homeworkid, homeworktype, record, type;
    private AlertHelper helper;
    private Dialog dialog;
    private ShareUtils shareUtils;
    private String url;
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
        share = findViewById(R.id.play_share);
        nextDrop = findViewById(R.id.pic_prompt_next);
        iknow = findViewById(R.id.i_know);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        share.setOnClickListener(listener);
        iknow.setOnClickListener(listener);
        playTotal.setOnClickListener(listener);
        playTotal2.setOnClickListener(listener);
        clarityBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
//        playTotal2.setAlpha(0.5f);

        intent = getIntent();
        bookId = intent.getIntExtra("bookId", 0);
        audioType = intent.getIntExtra("audioType", 0);
        audioSource = intent.getIntExtra("audioSource", 3);
        imageUrl = intent.getStringExtra("imageUrl");
        title = intent.getStringExtra("title");
        homeworkid = intent.getIntExtra("homeworkid", 0);
        homeworktype = intent.getIntExtra("homeworktype", -1);

        playTotal2.setVisibility(View.VISIBLE);

        popupView = LayoutInflater.from(this).inflate(R.layout.activity_share_daka_item2, null, false);
        popupView2 = LayoutInflater.from(this).inflate(R.layout.activity_reading_drop_item, null, false);
        dropRecording = popupView2.findViewById(R.id.drop_share_recording);
        dropSong = popupView2.findViewById(R.id.drop_share_song);
        dropRecording.setOnClickListener(listener);
        dropSong.setOnClickListener(listener);

        coinCount = popupView.findViewById(R.id.coin_count);
        pengyouquan = popupView.findViewById(R.id.share_daka_pengyouquan);
        weixin = popupView.findViewById(R.id.share_daka_weixin);
        qq = popupView.findViewById(R.id.share_daka_qq);
        qqzone = popupView.findViewById(R.id.share_daka_qqzone);
        shareCancel = popupView.findViewById(R.id.daka_share_cancel2);
        pengyouquan.setOnClickListener(listener);
        weixin.setOnClickListener(listener);
        qq.setOnClickListener(listener);
        qqzone.setOnClickListener(listener);
        shareCancel.setOnClickListener(listener);

        popupWindow = new PopupWindow(this);
        popupWindow2 = new PopupWindow(this);
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });

        popupWindow2.setContentView(popupView2);
        popupWindow2.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        popupWindow2.setOutsideTouchable(true);
        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });
    }

    @Override
    protected void initData() {
        shareUtils = new ShareUtils(this, this);
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
        bundle.putInt("homeworktype", homeworktype);
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
        } else if (message.what == MethodCode.EVENT_CLOCKINSHARE) {
            ShareBean shareBean = (ShareBean) message.obj;
            url = shareBean.getUrl();
        } else if (message.what == MethodCode.EVENT_SHARECOIN) {
            ShareCoin shareCoin = (ShareCoin) message.obj;
            if (shareCoin != null) {
                if (shareCoin.getIsGold() == 0) {
                    animationView.setVisibility(View.VISIBLE);
                    clarityBack.setVisibility(View.VISIBLE);
                    if (shareCoin.getGoldCount() == 1) {
                        animationView.setImageAssetsFolder("coin1/");
                        animationView.setAnimation("coin1.json");
                    } else if (shareCoin.getGoldCount() == 2) {
                        animationView.setImageAssetsFolder("coin2/");
                        animationView.setAnimation("coin2.json");
                    } else if (shareCoin.getGoldCount() == 3) {
                        animationView.setImageAssetsFolder("coin3/");
                        animationView.setAnimation("coin3.json");
                    } else if (shareCoin.getGoldCount() == 4) {
                        animationView.setImageAssetsFolder("coin4/");
                        animationView.setAnimation("coin4.json");
                    } else if (shareCoin.getGoldCount() == 5) {
                        animationView.setImageAssetsFolder("coin5/");
                        animationView.setAnimation("coin5.json");
                    } else if (shareCoin.getGoldCount() == 6) {
                        animationView.setImageAssetsFolder("coin6/");
                        animationView.setAnimation("coin6.json");
                    } else if (shareCoin.getGoldCount() == 7) {
                        animationView.setImageAssetsFolder("coin7/");
                        animationView.setAnimation("coin7.json");
                    } else if (shareCoin.getGoldCount() == 8) {
                        animationView.setImageAssetsFolder("coin8/");
                        animationView.setAnimation("coin8.json");
                    } else if (shareCoin.getGoldCount() == 9) {
                        animationView.setImageAssetsFolder("coin9/");
                        animationView.setAnimation("coin9.json");
                    } else {
                        animationView.setImageAssetsFolder("coin10/");
                        animationView.setAnimation("coin10.json");
                    }

                    animationView.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationView.setVisibility(View.GONE);
                            clarityBack.setVisibility(View.GONE);
                            animation.cancel();
                            animation.clone();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animationView.loop(false);
                    animationView.playAnimation();
                    SystemUtils.animPool.play(SystemUtils.animMusicMap.get(11), 1, 1, 0, 0, 1);
                } else {
                    showInfo("今日获得金币已达上限");
                }
            }
        } else if (message.what == MethodCode.EVENT_ISDROP) {
            clarityBack.setVisibility(View.VISIBLE);
            nextDrop.setVisibility(View.VISIBLE);
            iknow.setVisibility(View.VISIBLE);
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
            bundle.putInt("totalPage", totalPage);
            bundle.putString("imageUrl", imageUrl);
            bundle.putString("title", title);
            bundle.putInt("homeworkid", homeworkid);
            bundle.putInt("homeworktype", homeworktype);
            fragment.setArguments(bundle);
            lists.add(fragment);
        }
//        }

        fragmentAdapter = new BookPlayFragmentAdapter(getSupportFragmentManager(), lists);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == lists.size() - 1) {
//                    bookPlay2Layout.getBackground().setAlpha((int) (positionOffset * 255));
                    bookPlay2Layout.setAlpha(1f - positionOffset);
                    pageLayout.setAlpha(1f - positionOffset);

//                    Log.v("BookPlayActivity2", position + "===" + positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                /**
                 * {@link BookPlayFragment#onMainEventThread(JTMessage)}
                 */
                page.setText((position + 1) + "/" + (totalPage + 1));
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
                        for (int j = 0; j < lists.size(); j++) {
                            nectarCount = nectarCount + lists.get(j);
                        }
//                        nectarCount = nectarCount + lists.size();
                    }
                    if (nectarCount == 0) {

                    } else {
                        application.getSystemUtils().setBackGray(BookPlayActivity2.this, true);
                        application.getSystemUtils().getNectarCongratulation(BookPlayActivity2.this, nectarCount).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
//                        SystemUtils.getNectarCongratulation(BookPlayActivity2.this, nectarCount).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    bookPlay2Layout.setVisibility(View.VISIBLE);
                    pageLayout.setVisibility(View.VISIBLE);
                    bookPlay2Layout.setAlpha(1f);
                    pageLayout.setAlpha(1f);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        page.setText(1 + "/" + (totalPage + 1));
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
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.show();
            }
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.dismiss();
            }
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
                if (!application.getSystemUtils().isPlaying()) {
                    if (!application.getSystemUtils().isPlayAll()) {
//                        playTotal.setText("停止播放");
                        playTotal2.setImageResource(R.drawable.icon_full_reading_t);
                        application.getSystemUtils().setPlayAll(true);
                        application.getSystemUtils().setCurrentPage(0);
                        application.getSystemUtils().setCurrentLine(0);
                        application.getSystemUtils().setTotalPage(totalPage);
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
            case R.id.play_share:
                if (BookPlayFragment.isRecord) {
                    return;
                }
                if (BookPlayFragment.isPlay) {
                    return;
                }
                if (application.getSystemUtils().isPlaying()) {
                    return;
                }
                //TODO:
                getWindowGray(true);
                popupWindow2.showAsDropDown(share);


//                presenter.clockinShare(2, bookId);
//                getWindowGray(true);
//                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                break;
            case R.id.share_daka_pengyouquan:
                shareType = 0;
                if (type == 2) {
                    //系统
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareWechatMoments("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + title + "》", "安妮花数字图书馆喊你来读书啦", imageUrl, url);
                        } else {
                            shareUtils.shareWechatMoments("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + title + "》", "安妮花-磨耳朵 流利读 地道说", imageUrl, url);
                        }
                    }
                } else {
                    //录音
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareWechatMoments("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花流利读《" + title + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        } else {
                            shareUtils.shareWechatMoments("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花地道说《" + title + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        }
                    }
                }
                break;
            case R.id.share_daka_weixin:
                shareType = 1;
                if (type == 2) {
                    //系统
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareWechat("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + title + "》", "安妮花数字图书馆喊你来读书啦", imageUrl, url);
                        } else {
                            shareUtils.shareWechat("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + title + "》", "安妮花-磨耳朵 流利读 地道说", imageUrl, url);
                        }
                    }
                } else {
                    //录音
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareWechat("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花流利读《" + title + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        } else {
                            shareUtils.shareWechat("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花地道说《" + title + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        }
                    }
                }
                break;
            case R.id.share_daka_qq:
                shareType = 2;
                if (type == 2) {
                    //系统
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareQQ("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + title + "》", "安妮花数字图书馆喊你来读书啦", imageUrl, url);
                        } else {
                            shareUtils.shareQQ("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + title + "》", "安妮花-磨耳朵 流利读 地道说", imageUrl, url);
                        }
                    }
                } else {
                    //录音
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareQQ("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花流利读《" + title + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        } else {
                            shareUtils.shareQQ("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花地道说《" + title + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        }
                    }
                }
                break;
            case R.id.share_daka_qqzone:
                shareType = 3;
                if (type == 2) {
                    //系统
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareQZone("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + title + "》", "安妮花数字图书馆喊你来读书啦", imageUrl, url);
                        } else {
                            shareUtils.shareQZone("我和宝宝" + application.getSystemUtils().getUserInfo().getName() + "正在听《" + title + "》", "安妮花-磨耳朵 流利读 地道说", imageUrl, url);
                        }
                    }
                } else {
                    //录音
                    if (url != null && url.length() != 0) {
                        if (audioType == 1) {
                            shareUtils.shareQZone("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花流利读《" + title + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        } else {
                            shareUtils.shareQZone("快来听，我家宝宝" + application.getSystemUtils().getUserInfo().getName() + "在安妮花地道说《" + title + "》", "安妮花-磨耳朵 流利读 地道说", null, url);
                        }
                    }
                }
                break;
            case R.id.daka_share_cancel2:
                popupWindow.dismiss();
                break;
            case R.id.i_know:
                clarityBack.setVisibility(View.GONE);
                iknow.setVisibility(View.GONE);
                nextDrop.setVisibility(View.GONE);
                break;
            case R.id.drop_share_song:
                record = 1;
                type = 2;
                popupWindow2.dismiss();
                coinCount.setText("分享+2金币");
                presenter.clockinShare(2, bookId);
                getWindowGray(true);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                break;
            case R.id.drop_share_recording:
                popupWindow2.dismiss();
                boolean tag = true;
                for (int i = 0; i < releaseList.size(); i++) {
                    if (!releaseList.get(i).getTag()) {
                        tag = false;
                    }
                }
                if (tag) {
                    record = 0;
                    type = 4;
                    coinCount.setText("分享+5金币");
                    presenter.clockinShare(4, bookId);
                    getWindowGray(true);
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                } else {
                    getWindowGray(false);
                    showInfo("需要全部录完后才可以分享哦");
                }
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        showInfo("分享成功");
        popupWindow.dismiss();
        presenter.shareCoin(record, shareType);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        showInfo("分享取消");
        popupWindow.dismiss();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        showInfo("分享取消");
        popupWindow.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        allowBindService();
    }

    @Override
    protected void onPause() {
        allowUnBindService();
        super.onPause();
    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onChange(int position) {
        if (musicService.isPlaying()) {
            musicService.stop();
        }
    }

    class BookPlayFragmentAdapter extends FragmentStatePagerAdapter {
        private List<BookPlayFragment> fragmentLists;

        public BookPlayFragmentAdapter(FragmentManager fm, List<BookPlayFragment> fragmentLists) {
            super(fm);
            this.fragmentLists = fragmentLists;
        }

        @Override
        public Fragment getItem(int position) {
//            if (audioSource == 8) {
//                return lists2.get(position);
//            } else {
            if (position < fragmentLists.size()) {
                return fragmentLists.get(position);
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
            return fragmentLists.size() + 1;
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

    private void getWindowGray(boolean tag) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        if (tag) {
            layoutParams.alpha = 0.7f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        } else {
            layoutParams.alpha = 1f;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        application.getSystemUtils().setPlayAll(false);
        application.getSystemUtils().setPlaying(false);
        viewPager.setNoFocus(false);
    }
}
