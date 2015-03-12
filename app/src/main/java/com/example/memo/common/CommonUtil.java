package com.example.memo.common;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by kwz on 2015/03/05.
 */
public class CommonUtil {

    /**
     * コンストラクタ
     * インスタンス化禁止
     */
    private CommonUtil() {
    }

    /**
     * 互換性を考慮した背景画像を設定メソッド
     *
     * @param view
     * @param drawable
     * @return
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static View setBackground(View view, Drawable drawable) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
        return view;
    }

    /**
     * X方向にターゲットを移動する
     *
     * @param target
     * @time 秒数
     * @distance 距離
     */
    public static void animateTranslationX(ImageView target, int time, float distance) {

        // translationXプロパティを0fからdistanceに変化させます
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "translationX", 0f, distance);

        // time秒かけて実行させます
        objectAnimator.setDuration(time * 1000);

        // アニメーションを開始します
        objectAnimator.start();
    }

    /**
     * Y方向にターゲットを移動する
     *
     * @param target
     * @time 秒数
     * @distance 距離
     */
    public static void animateTranslationY(ImageView target, int time, float distance) {

        // translationYプロパティを0fからdistanceに変化させます
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "translationY", 0f, distance);

        // time秒かけて実行させます
        objectAnimator.setDuration(time * 1000);

        // アニメーションを開始します
        objectAnimator.start();
    }

    /**
     * time秒かけてターゲットをZ軸周りに360度回転させる
     *
     * @param target
     * @time 秒数
     */
    public static void animateRotation(ImageView target, int time) {

        // rotationプロパティを0fから360fに変化させます
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "rotation", 0f, 360f);

        // time秒かけて実行させます
        objectAnimator.setDuration(time * 1000);

        // アニメーションを開始します
        objectAnimator.start();
    }

    /**
     * time秒かけてターゲットを表示
     *
     * @param target
     * @time 秒数
     */
    public static void animateAlphaVisible(ImageView target, int time) {

        // alphaプロパティを0fから1fに変化させます
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "alpha", 0f, 1f);

        // time秒かけて実行させます
        objectAnimator.setDuration(time * 1000);

        // アニメーションを開始します
        objectAnimator.start();
    }

    /**
     * time秒かけてターゲットを非表示
     *
     * @param target
     * @time 秒数
     */
    public static void animateAlphaInVisible(ImageView target, int time) {

        // alphaプロパティを0fから1fに変化させます
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "alpha", 1f, 0f);

        // time秒かけて実行させます
        objectAnimator.setDuration(time * 1000);

        // アニメーションを開始します
        objectAnimator.start();
    }
}
