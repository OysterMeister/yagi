package com.example.memo.common;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

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
}
