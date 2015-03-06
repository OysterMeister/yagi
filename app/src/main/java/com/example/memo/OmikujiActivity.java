package com.example.memo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    private ImageView mOmikujiBtn;
    private TextView mOmikujiTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omikuji);
        mOmikujiBtn = (ImageView) findViewById(R.id.myButton);
        mOmikujiTv = (TextView) findViewById(R.id.myText);
        mOmikujiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                // String result = Integer.toString(num);
                // if (num == 0) {
                // tv.setTextColor(Color.RED);
                // tv.setTextColor(Color.parseColor("#FF0000"));
                // mOmikujiTv.setTextColor(Color.parseColor("red"));
                // } else {
                //mOmikujiTv.setTextColor(Color.BLACK);
                // }
                //mOmikujiTv.setText(results[num]);

                ImageView imageView = (ImageView) findViewById(R.id.hituji);
                imageView.setImageResource(R.drawable.hituji_neru);
                switch (num) {
                    case 0:
                        imageView.setImageResource(R.drawable.hituji_daikiti);
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }
                mOmikujiTv.setText(results[num]);

                animateAnimatorSetSample(mOmikujiBtn,90,-1000);
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
    private void animateAnimatorSetSample( ImageView target, float degree, float distance ) {

        // AnimatorSetに渡すAnimatorのリストです
        List<Animator> animatorList= new ArrayList<Animator>();

        // alphaプロパティを0fから1fに変化させます
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat( target, "alpha", 0f, 1f );
        // 2秒かけて実行させます
        alphaAnimator.setDuration( 2000 );
        // リストに追加します
        animatorList.add( alphaAnimator );

        // 距離と半径から到達点となるX座標、Y座標を求めます
        float toX = (float) ( distance * Math.cos( Math.toRadians( degree ) ) );
        float toY = (float) ( distance * Math.sin( Math.toRadians( degree ) ) );

        // translationXプロパティを0fからtoXに変化させます
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat( "translationX", 0f, toX );
        // translationYプロパティを0fからtoYに変化させます
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat( "translationY", 0f, toY );
        // rotationプロパティを0fから360に変化させます
        PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat( "rotation", 0f, 360f );

        // targetに対してholderX, holderY, holderRotationを同時に実行します
        ObjectAnimator translationXYAnimator =
                ObjectAnimator.ofPropertyValuesHolder( target, holderX, holderY, holderRotaion );
        // 2秒かけて実行させます
        translationXYAnimator.setDuration( 2000 );
        // リストに追加します
        animatorList.add( translationXYAnimator );

        final AnimatorSet animatorSet = new AnimatorSet();
        // リストのAnimatorを順番に実行します
        animatorSet.playSequentially( animatorList );

        // アニメーションを開始します
        animatorSet.start();
    }
}
