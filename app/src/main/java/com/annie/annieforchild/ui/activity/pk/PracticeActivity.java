package com.annie.annieforchild.ui.activity.pk;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;

/**
 * 歌曲pk,练习,挑战
 * Created by wanglei on 2018/3/31.
 */

public class PracticeActivity extends BaseActivity implements View.OnClickListener, SongView {
    private ImageView back, practiceImage, star1, star2, star3, star4, star5;
    private TextView practiceTitle, practiceBtn, challengeBtn, pkBtn;
    private GrindEarPresenter presenter;
    private Intent intent;
    private Song song;
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

    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        intent = getIntent();
        if (intent != null) {
            song = (Song) intent.getSerializableExtra("song");
        }
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
                startActivity(intent);
                break;
            case R.id.challenge_btn:
                Intent intent1 = new Intent(this, ChallengeActivity.class);
                startActivity(intent1);
                break;
            case R.id.pk_btn:
                Intent intent2 = new Intent(this, pkActivity.class);
                startActivity(intent2);
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
            song = (Song) message.obj;
            refresh();
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
