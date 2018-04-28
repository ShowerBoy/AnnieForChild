package com.annie.annieforchild.ui.activity.pk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.PkUserPopupAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.ashokvarma.bottomnavigation.utils.Utils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 歌曲pk,练习,挑战
 * Created by wanglei on 2018/3/31.
 */

public class PracticeActivity extends BaseActivity implements View.OnClickListener, SongView, PopupWindow.OnDismissListener {
    private ImageView back, practiceImage, star1, star2, star3, star4, star5;
    private TextView practiceTitle, practiceBtn, challengeBtn, pkBtn;
    private List<UserInfo2> pkUserList;
    private PopupWindow popupWindow;
    private View popupView;
    private GridView popupGrid;
    private PkUserPopupAdapter popupAdapter;
    private GrindEarPresenter presenter;
    private Intent intent;
    private Song song;
    private int popupWidth;
    private AlertHelper helper;
    private Dialog dialog;
    private float star;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_practice;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.practice_back);
        practiceImage = findViewById(R.id.practice_image);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        practiceTitle = findViewById(R.id.practice_title);
        practiceBtn = findViewById(R.id.practice_btn);
        challengeBtn = findViewById(R.id.challenge_btn);
        pkBtn = findViewById(R.id.pk_btn);
        back.setOnClickListener(this);
        practiceBtn.setOnClickListener(this);
        challengeBtn.setOnClickListener(this);
        pkBtn.setOnClickListener(this);

        popupWidth = Math.min(SystemUtils.window_width, SystemUtils.window_height) * 3 / 4;
        popupView = LayoutInflater.from(this).inflate(R.layout.activity_popup_item2, null, false);
        popupWindow = new PopupWindow(popupView, popupWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupGrid = popupView.findViewById(R.id.popup_grid);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setOnDismissListener(this);
        popupGrid.setAdapter(popupAdapter);
        popupGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                Intent intent = new Intent(PracticeActivity.this, pkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("bookId", song.getBookId());
                bundle.putString("pkUserName", pkUserList.get(position).getUsername());
                bundle.putString("avatar", pkUserList.get(position).getAvatar());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        pkUserList = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        intent = getIntent();
        if (intent != null) {
            song = (Song) intent.getSerializableExtra("song");
        }
        popupAdapter = new PkUserPopupAdapter(this, pkUserList);

        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        Glide.with(this).load(song.getBookImageUrl()).into(practiceImage);
        practiceTitle.setText(song.getBookName());
        presenter.getBookScore(song.getBookId());
    }

    private void refresh() {
        if (song != null) {
            Glide.with(this).load(song.getBookImageUrl()).into(practiceImage);
            practiceTitle.setText(song.getBookName());
            float a = song.getLastScore();
            float b = song.getTotalScore();
            star = a / b * 5f;

            BigDecimal bigDecimal = new BigDecimal(star);
            star = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            String abc = star + "";
            String[] ddd = abc.split("\\.");
            if (ddd[0].equals("0")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_f);
                    star3.setImageResource(R.drawable.icon_star_f);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                } else {
                    if (v == 0) {
                        star1.setImageResource(R.drawable.icon_star_f);
                    } else {
                        star1.setImageResource(R.drawable.icon_star_h);
                    }
                    star2.setImageResource(R.drawable.icon_star_f);
                    star3.setImageResource(R.drawable.icon_star_f);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                }
            } else if (ddd[0].equals("1")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_f);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                } else {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_h);
                    star3.setImageResource(R.drawable.icon_star_f);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                }
            } else if (ddd[0].equals("2")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                } else {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_h);
                    star4.setImageResource(R.drawable.icon_star_f);
                    star5.setImageResource(R.drawable.icon_star_f);
                }
            } else if (ddd[0].equals("3")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_t);
                    star5.setImageResource(R.drawable.icon_star_f);
                } else {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_h);
                    star5.setImageResource(R.drawable.icon_star_f);
                }
            } else if (ddd[0].equals("4")) {
                int v = Integer.parseInt(ddd[1]);
                if (v > 4) {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_t);
                    star5.setImageResource(R.drawable.icon_star_t);
                } else {
                    star1.setImageResource(R.drawable.icon_star_t);
                    star2.setImageResource(R.drawable.icon_star_t);
                    star3.setImageResource(R.drawable.icon_star_t);
                    star4.setImageResource(R.drawable.icon_star_t);
                    star5.setImageResource(R.drawable.icon_star_h);
                }
            } else if (ddd[0].equals("5")) {
                star1.setImageResource(R.drawable.icon_star_t);
                star2.setImageResource(R.drawable.icon_star_t);
                star3.setImageResource(R.drawable.icon_star_t);
                star4.setImageResource(R.drawable.icon_star_t);
                star5.setImageResource(R.drawable.icon_star_t);
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.practice_back:
                finish();
                break;
            case R.id.practice_btn:
                Intent intent = new Intent(this, ExerciseActivity.class);
                intent.putExtra("bookId", song.getBookId());
                startActivity(intent);
                break;
            case R.id.challenge_btn:
                Intent intent1 = new Intent(this, ChallengeActivity.class);
                intent1.putExtra("bookId", song.getBookId());
                startActivity(intent1);
                break;
            case R.id.pk_btn:
                presenter.getPkUsers(song.getBookId());
//                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETBOOKSCORE) {
            Song song1 = (Song) message.obj;
            song.setLastScore(song1.getLastScore());
            song.setTotalPages(song1.getTotalPages());
            song.setTotalScore(song1.getTotalScore());
            refresh();
        } else if (message.what == MethodCode.EVENT_GETPKUSERS) {
            pkUserList.clear();
            pkUserList.addAll((List<UserInfo2>) message.obj);
            popupAdapter.notifyDataSetChanged();
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            getWindowGray(true);
        }
    }

    private void getWindowGray(boolean tag) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        if (tag) {
            layoutParams.alpha = 0.7f;
            getWindow().setAttributes(layoutParams);
        } else {
            layoutParams.alpha = 1f;
            getWindow().setAttributes(layoutParams);
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
    public void onDismiss() {
        getWindowGray(false);
    }
}
