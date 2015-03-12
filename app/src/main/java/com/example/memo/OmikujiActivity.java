package com.example.memo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memo.common.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * おみくじ.
 *
 * @author kwz
 */
public class OmikujiActivity extends ActionBarActivity {

    private Context mContext;
    private Button mOmikujiBtn;
    private ImageView mOmikujiIv;
    private ImageView mHitujiKakuIv;
    private ImageView mHitujiOdoruFukidasiIv;
    private LinearLayout mResultLl;
    private TextView mResult1Tv;
    private TextView mResult2Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omikuji);
        initialization();
        onClickListeners();
    }

    /**
     * 初期化
     */
    private void initialization() {
        mContext = this;
        mOmikujiBtn = (Button) findViewById(R.id.omikuji_hiku_btn);
        mOmikujiIv = (ImageView) findViewById(R.id.omikuji_iv);
        mHitujiKakuIv = (ImageView) findViewById(R.id.hituji_kaku_iv);
        mHitujiOdoruFukidasiIv = (ImageView) findViewById(R.id.hituji_odoru_fukidasi_iv);
        mResultLl = (LinearLayout) findViewById(R.id.result_ll);
        mResult1Tv = (TextView) findViewById(R.id.result_msg_1_tv);
        mResult2Tv = (TextView) findViewById(R.id.result_msg_2_tv);

        // 一旦非表示
        setGone();
    }

    /**
     * 非表示
     */
    private void setGone() {
        mOmikujiIv.setVisibility(View.INVISIBLE);
        mHitujiKakuIv.setVisibility(View.INVISIBLE);
        mHitujiOdoruFukidasiIv.setVisibility(View.INVISIBLE);
        mResultLl.setVisibility(View.INVISIBLE);
        mResult1Tv.setVisibility(View.INVISIBLE);
        mResult2Tv.setVisibility(View.INVISIBLE);
    }

    /**
     * クリックイベント
     */
    private void onClickListeners() {

        /**
         * おみくじを引く
         */
        mOmikujiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGone();

                // おみくじにアニメーションを設定
                // 表示
                mOmikujiIv.setVisibility(View.VISIBLE);
                // フェードイン表示
                CommonUtil.animateAlphaVisible(mOmikujiIv, 1);
                // 移動
                CommonUtil.animateTranslationY(mOmikujiIv, 2, -550f);
                // 回転
                CommonUtil.animateRotation(mOmikujiIv, 3);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // フェードアウト表示
                        CommonUtil.animateAlphaInVisible(mOmikujiIv, 1);

                        // 結果を表示
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 表示
                                mHitujiOdoruFukidasiIv.setVisibility(View.VISIBLE);
                                mResultLl.setVisibility(View.VISIBLE);
                                mResult1Tv.setVisibility(View.VISIBLE);
                                mResult2Tv.setVisibility(View.VISIBLE);

                                Random randomGenerator = new Random();
                                String[] results = {
                                        "大吉",
                                        "中吉",
                                        "小吉",
                                        "吉",
                                        "半吉",
                                        "末吉",
                                        "末小吉",
                                        "凶",
                                        "小凶",
                                        "半凶",
                                        "末凶",
                                        "大凶"
                                };
                                int num = randomGenerator.nextInt(results.length);
                                String sResult2 = "";
                                switch (num) {
                                    case 0:
                                        sResult2 = "やったね";
                                        break;
                                    case 12:
                                        sResult2 = "ざんねん";
                                        break;
                                    default:
                                        sResult2 = "ふつうです";
                                        break;
                                }
                                mResult1Tv.setText(results[num]);
                                mResult2Tv.setText(sResult2);
                            }
                        }, 1500);

//                        // ひつじが結果を書く
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                // 表示
//                                mHitujiKakuIv.setVisibility(View.VISIBLE);
//
//
//                                // フェードイン表示
//                                CommonUtil.animateAlphaVisible(mHitujiKakuIv, 2);
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        // フェードアウト表示
//                                        CommonUtil.animateAlphaInVisible(mHitujiKakuIv, 2);
//
//
//                                    }
//                                }, 1500);
//                            }
//                        }, 1000);
                    }
                }, 2000);
            }
        });
    }


    /**
     * 2秒かけてターゲットを表示した後に、2秒かけて引数に与えた角度と距離の位置に回転させながら移動させる
     *
     * @param target
     * @param degree
     * @param distance
     */
    private void animateAnimatorSetSample(ImageView target, float degree, float distance) {

        // AnimatorSetに渡すAnimatorのリストです
        List<Animator> animatorList = new ArrayList<Animator>();

        // alphaプロパティを0fから1fに変化させます
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(target, "alpha", 0f, 1f);
        // 2秒かけて実行させます
        alphaAnimator.setDuration(2000);
        // リストに追加します
        animatorList.add(alphaAnimator);

        // 距離と半径から到達点となるX座標、Y座標を求めます
        float toX = (float) (distance * Math.cos(Math.toRadians(degree)));
        float toY = (float) (distance * Math.sin(Math.toRadians(degree)));

        // translationXプロパティを0fからtoXに変化させます
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", 0f, toX);
        // translationYプロパティを0fからtoYに変化させます
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", 0f, toY);
        // rotationプロパティを0fから360に変化させます
        PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat("rotation", 0f, 360f);

        // targetに対してholderX, holderY, holderRotationを同時に実行します
        ObjectAnimator translationXYAnimator =
                ObjectAnimator.ofPropertyValuesHolder(target, holderX, holderY, holderRotaion);
        // 2秒かけて実行させます
        translationXYAnimator.setDuration(2000);
        // リストに追加します
        animatorList.add(translationXYAnimator);

        final AnimatorSet animatorSet = new AnimatorSet();
        // リストのAnimatorを順番に実行します
        animatorSet.playSequentially(animatorList);

        // アニメーションを開始します
        animatorSet.start();
    }
}


//        mOmikujiBtn = (ImageView) findViewById(R.id.myButton);
//        mOmikujiTv = (TextView) findViewById(R.id.myText);
//        mOmikujiBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Random randomGenerator = new Random();
//                String[] results = {
//                        "大吉",
//                        "中吉",
//                        "小吉",
//                        "吉",
//                        "半吉",
//                        "末吉",
//                        "末小吉",
//                        "凶",
//                        "小凶",
//                        "半凶",
//                        "末凶",
//                        "大凶"
//                };
//                int num = randomGenerator.nextInt(results.length);
//                // String result = Integer.toString(num);
//                // if (num == 0) {
//                // tv.setTextColor(Color.RED);
//                // tv.setTextColor(Color.parseColor("#FF0000"));
//                // mOmikujiTv.setTextColor(Color.parseColor("red"));
//                // } else {
//                //mOmikujiTv.setTextColor(Color.BLACK);
//                // }
//                //mOmikujiTv.setText(results[num]);
//
//                ImageView imageView = (ImageView) findViewById(R.id.hituji);
//                imageView.setImageResource(R.drawable.hituji_neru);
//                switch (num) {
//                    case 0:
//                        imageView.setImageResource(R.drawable.hituji_daikiti);
//                        break;
//                    case 1:
//
//                        break;
//                    case 2:
//
//                        break;
//                }
//                mOmikujiTv.setText(results[num]);
//
//                animateAnimatorSetSample(mOmikujiBtn,90,-1000);
//            }
//        });