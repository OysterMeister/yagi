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
    private Boolean mRunFlg;
    private Integer mResultNum;
    private String mResultTitle;
    private String mResultDetail;

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
        mRunFlg = false;
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

                // 抽選中か判定
                if (mRunFlg) {
                    return;
                }
                mRunFlg = true;

                // 一旦非表示
                setGone();

                // 抽選を行う
                runFortune();

                // おみくじにアニメーションを設定
                // 表示
                mOmikujiIv.setVisibility(View.VISIBLE);

                // フェードイン表示
                CommonUtil.animateAlphaVisible(mOmikujiIv, 1);

                // 移動
                CommonUtil.animateTranslationY(mOmikujiIv, 2, -550f);

                // 回転
                CommonUtil.animateRotation(mOmikujiIv, 5);

                switch (mResultNum) {
                    case 0:
                    case 11:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
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

                                        // 結果
                                        mResult1Tv.setText(mResultTitle);
                                        mResult2Tv.setText(mResultDetail);

                                        // 終了
                                        mRunFlg = false;
                                    }
                                }, 0000);
                            }
                        }, 5000);

                        break;
                    default:
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

                                        // 結果
                                        mResult1Tv.setText(mResultTitle);
                                        mResult2Tv.setText(mResultDetail);

                                        // 終了
                                        mRunFlg = false;
                                    }
                                }, 1500);

                            }
                        }, 2000);
                        break;
                }
            }
        });
    }

    /**
     * おみくじ抽選
     */
    private void runFortune() {
        Random randomGenerator = new Random();
        String[] sResultTitle = {
                "超大吉",
                "超超大吉",
                "まぁまぁ大吉",
                "ぎりぎり大吉",
                "あまりよくない大吉",
                "ほどほど大吉",
                "ぼちぼち大吉",
                "あんまり大吉",
                "かろうじて大吉",
                "ほどよく大吉",
                "なんとなく大吉",
                "大凶"
        };
        mResultNum = randomGenerator.nextInt(sResultTitle.length);
        mResultNum = 11;

        String sResultDetail = "";
        switch (mResultNum) {
            case 0:
                sResultDetail = "全体的な金運は非常に良く勝負運もあります。謙虚な姿勢が成功のカギ。";
                break;
            case 1:
                sResultDetail = "ラッキースポットは大勢の人が集まる場所。笑顔であいさつをすると運気上昇。";
                break;
            case 2:
                sResultDetail = "握手やハグなどまめなスキンシップを心がけよう！自然と運気も上昇。";
                break;
            case 3:
                sResultDetail = "家族や愛しい人への思いやりで運気がアップ！恋愛運も少しずつ上昇します。";
                break;
            case 4:
                sResultDetail = "即断、即決の行動力とバランス感覚が人気運とお金の流れを呼び込みます。";
                break;
            case 5:
                sResultDetail = "アイデアや感性が直接お金に結びつく強運の時ですがお金の管理には注意。";
                break;
            case 6:
                sResultDetail = "先見の明が働く時です。流行の一歩先を行くセンスが金運に結びつきます。";
                break;
            case 7:
                sResultDetail = "金運向上のキーワードは社会貢献。社会に役立つ行動は自分に戻ります。";
                break;
            case 8:
                sResultDetail = "ラッキーカラーはシルバー。芸術的な趣味を始めると全体運がアップしそう。";
                break;
            case 9:
                sResultDetail = "道を切りひらいていくパワーが満ちる時です。周囲の意見をよく聞いて。";
                break;
            case 10:
                sResultDetail = "自分よりも弱い者の世話や面倒を見ることで全体運が大幅にアップします。";
                break;
            case 11:
                sResultDetail = "何をやってもうまくいかない。今日は最悪です。";
                break;
            default:
                sResultDetail = "ふつうです";
                break;
        }

        mResultTitle = sResultTitle[mResultNum];
        mResultDetail = sResultDetail;
    }
}