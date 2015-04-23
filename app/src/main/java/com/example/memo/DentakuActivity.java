package com.example.memo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.format.Time;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.memo.common.CommonUtil;

/**
 * 自動計算機（MyCalc）
 */
public class DentakuActivity extends ActionBarActivity {

    /**
     * ** 定数 ****
     */
    private static final String STA_DOT = ".";
    private static final String STA_TASU = "+";
    private static final String STA_HIKU = "-";
    private static final String STA_KAKERU = "*";
    private static final String STA_WARU = "/";
    private static final String STA_CMP = "=";
    private static final String STA_DISP_TASU = "＋";
    private static final String STA_DISP_HIKU = "－";
    private static final String STA_DISP_KAKERU = " × ";
    private static final String STA_DISP_WARU = " ÷ ";
    private static final String STA_ERR_1 = "Err: Overflow!";
    private static final String STA_ERR_2 = "Err: Division By Zero!";

    /**
     * ** 変数 ****
     */
    /* コンテキスト */
    private Context mContext;
    /* 表示ビュー */
    private TextView mNumberTv;     // 数値表示ビュー
    private TextView mEnzanshiTv;   // 演算子表示ビュー
    /* ボタン行 */
    private ImageView mSecletLongIv;
    private LinearLayout mKey789Ll;
    private LinearLayout mKey456Ll;
    private LinearLayout mKey123Ll;
    private LinearLayout mKey000Ll;
    /* 数値ボタン0～9 */
    private Button mNum0Btn;
    private Button mNum1Btn;
    private Button mNum2Btn;
    private Button mNum3Btn;
    private Button mNum4Btn;
    private Button mNum5Btn;
    private Button mNum6Btn;
    private Button mNum7Btn;
    private Button mNum8Btn;
    private Button mNum9Btn;
    /* 演算子ボタン */
    private Button mDelBtn;     // AC（削除）
    private Button mClearBtn;   // C（クリア）
    private Button mDotBtn;     // .（小数点）
    private Button mTasuBtn;    // +（足す）
    private Button mHikuBtn;    // -（引く）
    private Button mKakeruBtn;  // *（掛ける）
    private Button mWaruBtn;    // /（割る）
    private Button mCmpBtn;     // =（結果）
    /* 演算情報 */
    private String sEnzanshi;   // 演算子
    private String sZenkou;     // 前項
    private String sKoukou;     // 後項
    /* シークレット */
    private Boolean mSecretFlag;
    private ImageView mSecretIv;
    private Integer mSecretCnt;
    /* トースト */
    private Toast mToast;
    /* ドロワーサイドメニュー */
    private DrawerLayout mDrawerLayout;             // ドロワーレイアウト
    private ActionBarDrawerToggle mDrawerToggle;    // ドロワートグル
    private ListView mSideMenuLv;                   // サイドメニューリストビュー
    private ArrayAdapter mSideMenuAdapter;          // サイドメニューアダプター
    /* ディスプレイ */
    private RelativeLayout mDisplayRl;
    /* ロゴ */
    private ImageView mLogoOystarsIv;
    private ImageView mLogoPakutasoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentaku);

        // 初期化
        init();
        initDrawer();

        // 画面サイズを基に、表示ビュー（数値、演算子）の文字サイズを設定。
        Display disp = getWindowManager().getDefaultDisplay();
        mNumberTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, disp.getWidth() / 11);
        mEnzanshiTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, disp.getWidth() / 11);

        // シークレットモードOFF初期化
        initSecretOff();
        // シークレットモード
        setSeacredMode();

        // クリックイベント
        click();
    }

    /**
     * 初期化処理だよ
     */
    private void init() {
        // コンテキスト初期化
        mContext = this;
        // 電卓部分
        mNumberTv = (TextView) findViewById(R.id.number_tv);
        mNumberTv.setText("0");
        mEnzanshiTv = (TextView) findViewById(R.id.enzanshi_tv);
        mEnzanshiTv.setText(" ");
        mSecletLongIv = (ImageView) findViewById(R.id.secret_long_iv);
        mKey789Ll = (LinearLayout) findViewById(R.id.key_789_ll);
        mKey456Ll = (LinearLayout) findViewById(R.id.key_456_ll);
        mKey123Ll = (LinearLayout) findViewById(R.id.key_123_ll);
        mKey000Ll = (LinearLayout) findViewById(R.id.key_000_ll);
        mNum0Btn = (Button) findViewById(R.id.num0_bt);
        mNum1Btn = (Button) findViewById(R.id.num1_bt);
        mNum2Btn = (Button) findViewById(R.id.num2_bt);
        mNum3Btn = (Button) findViewById(R.id.num3_bt);
        mNum4Btn = (Button) findViewById(R.id.num4_bt);
        mNum5Btn = (Button) findViewById(R.id.num5_bt);
        mNum6Btn = (Button) findViewById(R.id.num6_bt);
        mNum7Btn = (Button) findViewById(R.id.num7_bt);
        mNum8Btn = (Button) findViewById(R.id.num8_bt);
        mNum9Btn = (Button) findViewById(R.id.num9_bt);
        mDelBtn = (Button) findViewById(R.id.del_bt);
        mDotBtn = (Button) findViewById(R.id.dot_bt);
        mTasuBtn = (Button) findViewById(R.id.tasu_bt);
        mHikuBtn = (Button) findViewById(R.id.hiku_bt);
        mKakeruBtn = (Button) findViewById(R.id.kakeru_bt);
        mWaruBtn = (Button) findViewById(R.id.waru_bt);
        mCmpBtn = (Button) findViewById(R.id.cmp_bt);
        mClearBtn = (Button) findViewById(R.id.clear_bt);
        sEnzanshi = "";
        sZenkou = "";
        sKoukou = "";
        // シークレット
        mSecretIv = (ImageView) findViewById(R.id.secret_iv);
        // ディスプレイ
        mDisplayRl = (RelativeLayout) findViewById(R.id.display_rl);
        // ロゴ
        mLogoOystarsIv = (ImageView) findViewById(R.id.oystars_logo_iv);
        mLogoPakutasoIv = (ImageView) findViewById(R.id.pakutaso_logo_iv);
    }

    /**
     * シークレットモードON初期化
     */
    private void initSecretOn() {
        // シークレットモードON
        mSecretFlag = true;
        mSecretCnt = 0;
        // ディスプレイ透過背景（透過率MAX）
        mDisplayRl.setBackgroundColor(Color.parseColor("#00000000"));
    }

    /**
     * シークレットモードOFF初期化
     */
    private void initSecretOff() {
        // シークレットモードOFF
        mSecretFlag = false;
        mSecretCnt = 0;
        // ディスプレイ透過背景
        mDisplayRl.setBackgroundColor(Color.parseColor("#BBFFFFFF"));
    }

    /**
     * ドロワーサイドメニュー初期化
     */
    private void initDrawer() {
        // ドロワーレイアウト
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 切り替えボタン（3本線）の生成
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // サイドメニューの設定
        mSideMenuLv = (ListView) findViewById(R.id.side_menu_lv);
        mSideMenuAdapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1);
        // ListViewとAdapterを結びつける
        mSideMenuLv.setAdapter(mSideMenuAdapter);
        mSideMenuAdapter.add(this.getString(R.string.title_activity_daily_pad_calender));
        mSideMenuAdapter.add("おみくじ");
        mSideMenuAdapter.add("電卓");
    }

    /**
     * 処理判定
     */
    private void judgment(String sClickVal) {
        // 計算表示部の値を取得
        String sCalc = mNumberTv.getText().toString();
        // カンマ除去
        sCalc = sCalc.replaceAll(",", "");

        // 入力値によって処理分岐
        switch (sClickVal) {
            case STA_DOT:
                //◆小数点
                if (sCalc.contains(STA_DOT)) {
                    // 既に計算表示部に少数点あり
                    // シークレットモードON
                    if (sCalc.equals("7777.")) {
                        // シークレットモードON初期化
                        initSecretOn();
                        // シークレットモード
                        setSeacredMode();
                    }
                    // シークレットモードOFF
                    if (sCalc.equals("3333.")) {
                        // シークレットモードOFF初期化
                        initSecretOff();
                        // シークレットモード
                        setSeacredMode();
                    }
                } else {
                    // 未だ計算表示部に少数点なし
                    // 少数点を後ろに結合
                    sCalc = sCalc.concat(sClickVal);
                }
                break;
            case STA_TASU:
            case STA_HIKU:
            case STA_KAKERU:
            case STA_WARU:
                //◆足す、引く、掛ける、割る
                // 前項と後項があれば計算処理
                if (!sZenkou.equals("") && !sKoukou.equals("")) {
                    sCalc = calc(sCalc);
                }
                sEnzanshi = sClickVal;
                sZenkou = sCalc;
                break;
            case STA_CMP:
                //◆結果
                // 計算処理
                sCalc = calc(sCalc);
                break;
            default:
                //◆上記以外（数値）の場合
                // 表示部クリア
                if (!sZenkou.equals("") && sKoukou.equals("")) {
                    sCalc = "0";
                    sKoukou = sClickVal;
                }
                // 表示部に設定
                if (sCalc.equals("0")) {
                    // 0だと置き換え
                    sCalc = sClickVal;
                } else {
                    // 0以外は後ろに結合
                    sCalc = sCalc.concat(sClickVal);
                }
        }

        // 結果の整形
        sCalc = seikeiEx(sCalc);
        // 計算表示部にセット
        mNumberTv.setText(sCalc);
        // 演算子表示部にセット
        if (sEnzanshi.equals("")) {
            // ロリポップ対応
            mEnzanshiTv.setText(" ");
        } else {
            if (sEnzanshi.equals(STA_TASU)) {
                mEnzanshiTv.setText(STA_DISP_TASU);
            } else if (sEnzanshi.equals(STA_HIKU)) {
                mEnzanshiTv.setText(STA_DISP_HIKU);
            } else if (sEnzanshi.equals(STA_KAKERU)) {
                mEnzanshiTv.setText(STA_DISP_KAKERU);
            } else if (sEnzanshi.equals(STA_WARU)) {
                mEnzanshiTv.setText(STA_DISP_WARU);
            }
        }
    }

    /**
     * クリックイベントだよ
     */
    private void click() {
        mNum0Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("0");
            }
        });
        mNum1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("1");
            }
        });
        mNum2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("2");
            }
        });
        mNum3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("3");
            }
        });
        mNum4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("4");
            }
        });
        mNum5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("5");
            }
        });
        mNum6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("6");
            }
        });
        mNum7Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("7");
            }
        });
        mNum8Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("8");
            }
        });
        mNum9Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment("9");
            }
        });
        /* 削除ボタン */
        mDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 初期化
                init();
                // シークレットモード
                setSeacredMode();
            }
        });
        /* クリアボタン */
        mClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 入力中をクリア
                mNumberTv.setText("0");
            }
        });
        /* 小数点ボタン */
        mDotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment(STA_DOT);
            }
        });
        /* 足す（+）ボタン */
        mTasuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment(STA_TASU);
            }
        });
        /* 引く（-）ボタン */
        mHikuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment(STA_HIKU);
            }
        });
        /* 掛ける（*）ボタン */
        mKakeruBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment(STA_KAKERU);
            }
        });
        /* 割る（/）ボタン */
        mWaruBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment(STA_WARU);
            }
        });
        /* 結果（=）ボタン */
        mCmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgment(STA_CMP);
            }
        });
        /* ドロワーサイドメニュー */
        mSideMenuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent daily_pad_calendar_intent = new Intent();
                        daily_pad_calendar_intent.setClassName(mContext.getPackageName(), mContext.getPackageName() + ".DailyPadCalenderActivity");
                        mContext.startActivity(daily_pad_calendar_intent);
                        break;
                    case 1:
                        // おみくじ画面に遷移する
                        Intent mIntent = new Intent(mContext, OmikujiActivity.class);
                        mContext.startActivity(mIntent);
                        break;
                    case 2:
                        // 電卓に遷移する
                        Intent mDentakuIntent = new Intent(mContext, DentakuActivity.class);
                        mContext.startActivity(mDentakuIntent);
                        break;
                    default:
                }
            }
        });
    }

    /**
     * 数値の整形
     */
    private String seikeiEx(String sVal) {
        String sResult = "";
        // 小数点以下が5桁超えたら末尾カット
        if (sVal.contains(STA_DOT)) {
            String[] sVals = sVal.split("\\.", -1);
            if (sVals[1].length() > 5) {
                sVal = sVal.substring(0, sVal.length() - 1);
            }
        }
        // Double型にキャスト
        Double dWork = Double.parseDouble(sVal);
        // オーバーフロー対応
        if (dWork > 9999999999.99999) {
            dWork = 9999999999.0;
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(mContext, STA_ERR_1, Toast.LENGTH_SHORT);
            mToast.show();
        }
        if (dWork < -9999999999.99999) {
            dWork = -9999999999.0;
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(mContext, STA_ERR_1, Toast.LENGTH_SHORT);
            mToast.show();
        }
        // 整形
        DecimalFormat format = new DecimalFormat("#,##0.#####;-#,##0.#####");
        sResult = format.format(dWork);
        // 小数点対応
        if (sVal.contains(STA_DOT)) {
            String[] sVals = sVal.split("\\.", -1);
            if (!sVals[1].equals("") && Integer.parseInt(sVals[1]) == 0) {
                sResult = sResult.concat(STA_DOT);
                sResult = sResult.concat(sVals[1]);
            } else if (sVals[1].equals("")) {
                sResult = sResult.concat(STA_DOT);
            }
        }
        return sResult;
    }

    /**
     * 計算処理
     */
    private String calc(String sCalc) {
        sKoukou = sCalc;
        if (sEnzanshi.equals(STA_TASU)) {
            // 足し算
            BigDecimal tmpX = new BigDecimal(sZenkou);
            BigDecimal tmpY = new BigDecimal(sKoukou);
            BigDecimal result = tmpX.add(tmpY);
            sCalc = result.toString();
        } else if (sEnzanshi.equals(STA_HIKU)) {
            // 引き算
            BigDecimal tmpX = new BigDecimal(sZenkou);
            BigDecimal tmpY = new BigDecimal(sKoukou);
            BigDecimal result = tmpX.subtract(tmpY);
            sCalc = result.toString();
        } else if (sEnzanshi.equals(STA_KAKERU)) {
            // 掛け算
            BigDecimal tmpX = new BigDecimal(sZenkou);
            BigDecimal tmpY = new BigDecimal(sKoukou);
            BigDecimal result = tmpX.multiply(tmpY).setScale(3, BigDecimal.ROUND_HALF_UP);
            sCalc = result.toString();
        } else if (sEnzanshi.equals(STA_WARU)) {
            // 割り算
            if (sKoukou.equals("0")) {
                // ゼロ割対応
                sCalc = "0";
                Toast.makeText(mContext, STA_ERR_2, Toast.LENGTH_SHORT).show();
            } else {
                BigDecimal tmpX = new BigDecimal(sZenkou);
                BigDecimal tmpY = new BigDecimal(sKoukou);
                BigDecimal result = tmpX.divide(tmpY, 3, BigDecimal.ROUND_HALF_UP);
                sCalc = result.toString();
            }
        }
        // 小数点以下オールゼロ（1.0やら1.00）は小数値以下を切り捨て
        if (sCalc.contains(STA_DOT)) {
            String sVals[] = sCalc.split("\\.", -1);
            if (!sVals[1].equals("") && Integer.parseInt(sVals[1]) == 0) {
                sCalc = sVals[0];
            }
        }
        // 演算子、前項、後項をクリア
        sEnzanshi = "";
        sZenkou = "";
        sKoukou = "";
        return sCalc;
    }

    /**
     * オプションメニュー
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dentaku, menu);
        return true;
    }

    /**
     * オプションメニュー選択
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Activityの開始が完了したとき
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // DrawerToggleの同期
        mDrawerToggle.syncState();
    }

    /**
     * シークレットモード
     */
    private void setSeacredMode() {
        // 強制非表示
        Time time = new Time("Asia/Tokyo");
        time.setToNow();
        if (time.year > 2014 && time.month + 1 > 4) {
            mSecretFlag = false;
        }
        // モード切替
        if (mSecretFlag) {
            /* シークレットモード */
            // ボタン透過率UP
            mSecretCnt++;
            Float fAlpha = (float) 1.0;
            if (mSecretCnt > 5) {
                fAlpha = (float) 0.9;
            }
            if (mSecretCnt > 10) {
                fAlpha = (float) 0.8;
            }
            if (mSecretCnt > 15) {
                fAlpha = (float) 0.7;
            }
            if (mSecretCnt > 20) {
                fAlpha = (float) 0.6;
            }
            if (mSecretCnt > 25) {
                fAlpha = (float) 0.5;
            }
            if (mSecretCnt > 30) {
                fAlpha = (float) 0.4;
            }
            if (mSecretCnt > 35) {
                fAlpha = (float) 0.3;
            }
            if (mSecretCnt > 40) {
                fAlpha = (float) 0.2;
            }
            if (mSecretCnt > 45) {
                fAlpha = (float) 0.1;
            }
            if (mSecretCnt > 50) {
                //fAlpha = (float) 0.0;
            }
            mKey789Ll.setAlpha(fAlpha);
            mKey456Ll.setAlpha(fAlpha);
            mKey123Ll.setAlpha(fAlpha);
            mKey000Ll.setAlpha(fAlpha);
            // 背景画像ランダム変更
            Random r = new Random();
            int n = r.nextInt(5);
            switch (n) {
                case 0:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_01));
                    break;
                case 1:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_02));
                    break;
                case 2:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_03));
                    break;
                case 3:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_04));
                    break;
                case 4:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_05));
                    break;
                case 5:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_11));
                    break;
                case 6:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_12));
                    break;
                case 7:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_13));
                    break;
                case 8:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_14));
                    break;
                case 9:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.secret_long_15));
                    break;
            }
        } else {
            /* ノーマルモード */
            // ボタン透過率
            mKey789Ll.setAlpha((float) 0.8);
            mKey456Ll.setAlpha((float) 0.8);
            mKey123Ll.setAlpha((float) 0.8);
            mKey000Ll.setAlpha((float) 0.8);
            // 背景画像ランダム変更
            Random r = new Random();
            int n = r.nextInt(7);
            switch (n) {
                case 0:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.nomal_long_01));
                    break;
                case 1:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.nomal_long_02));
                    break;
                case 2:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.nomal_long_03));
                    break;
                case 3:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.nomal_long_04));
                    break;
                case 4:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.nomal_long_05));
                    break;
                case 5:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.nomal_long_06));
                    break;
                case 6:
                    CommonUtil.setBackground(mSecletLongIv, getResources().getDrawable(R.drawable.nomal_long_07));
                    break;
            }
        }
    }
}