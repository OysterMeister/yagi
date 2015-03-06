package com.example.memo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * 最強のメモ帳 abeshi tekitou
 */
public class MainActivity extends ActionBarActivity {

    /* メンバ変数 */
    private Context mContext;           // コンテキスト
    private Button mButton;             // メモるボタン
    private EditText mEditText;         // 入力フォーム
    private ListView mListView;         // リストビュー
    private ArrayAdapter mAdapter;      // アダプター
    private SharedPreferences mShared;  // シェアード

    private DrawerLayout mDrawerLayout;             // ドロワーレイアウト
    private ActionBarDrawerToggle mDrawerToggle;    // ドロワートグル
    private ListView mSideMenuLv;                   // サイドメニューリストビュー
    private ArrayAdapter mSideMenuAdapter;          // サイドメニューアダプター

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初期化
        initialization();
        initDrawer();

        // キーボード非表示
        HideKeyboard();

        // コンテキストメニューを表示するリストビューを登録
        registerForContextMenu(mListView);

        // アプリに保存したリスト内容を読み込む
        readList();

        // クリックイベント設定
        setOnClick();
    }

    /**
     * 初期化
     */
    public void initialization() {
        mContext = this;                                                            // コンテキスト
        mButton = (Button) findViewById(R.id.button);                               // メモるボタン
        mEditText = (EditText) findViewById(R.id.editText);                         // 入力フォーム
        mListView = (ListView) findViewById(R.id.listView);                         // リストビュー
        mAdapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1); // アダプター
        mShared = PreferenceManager.getDefaultSharedPreferences(mContext);          // シェアード
        // ListViewとAdapterを結びつける
        mListView.setAdapter(mAdapter);
    }

    /**
     * ドロワー初期化
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
        mSideMenuAdapter.add("test3");
    }

    /**
     * クリックイベント設定
     */
    public void setOnClick() {
        // メモるボタンにクリックイベントを設定
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString().equals("")) {
                    // 空ならトーストでエラーメッセージ
                    Toast.makeText(mContext, getString(R.string.err_msg1), Toast.LENGTH_SHORT).show();

                } else {
                    // アダプターの先頭に入力フォームの内容を追加
                    mAdapter.insert(mEditText.getText().toString(), 0);

                    // 入力フォームをクリア
                    mEditText.setText("");

                    // リストの内容をアプリに保存
                    saveList();

                    // トーストで完了メッセージを表示
                    Toast.makeText(mContext, getString(R.string.add_memo), Toast.LENGTH_SHORT).show();
                }
                // キーボード非表示
                HideKeyboard();
            }
        });

        mSideMenuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent daily_pad_calendar_intent = new Intent();
                        daily_pad_calendar_intent.setClassName(MainActivity.this.getPackageName(),MainActivity.this.getPackageName()	+ ".DailyPadCalenderActivity");
                        MainActivity.this.startActivity(daily_pad_calendar_intent);
                        break;
                    case 1:
                        // おみくじ画面に遷移する
                        Intent mIntent = new Intent(mContext, OmikujiActivity.class);
                        mContext.startActivity(mIntent);
                        break;
                    default:
                }
            }
        });
    }

    /**
     * コンテキストメニュー
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // 選択されたアイテム情報
        AdapterView.AdapterContextMenuInfo adapterInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        // 登録したリストビュー
        ListView listView = (ListView) v;
        // コンテキストメニューのヘッダーに選択された内容を設定
        menu.setHeaderTitle((String) listView.getItemAtPosition(adapterInfo.position));
        // コンテキストメニューに「削除する」を追加
        menu.add(R.string.action_del);
    }

    /**
     * コンテキストメニュー選択
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // コンテキストメニュー情報
        AdapterView.AdapterContextMenuInfo adapterInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        // 削除するメニューがタップされた場合
        if (item.getTitle().equals(getString(R.string.action_del))) {
            // 選択された項目をアダプターから削除
            mAdapter.remove(mAdapter.getItem(adapterInfo.position));
            // リストの内容をアプリに保存
            saveList();
            // トースト（削除メッセージ）
            Toast.makeText(mContext, getString(R.string.del_memo), Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * オプションメニュー
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * オプションメニュー選択
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // 全消し
        if (id == R.id.action_all_clear) {
            // アダプターをクリア
            mAdapter.clear();
            // リストの内容をアプリに保存
            saveList();
            // トースト（削除メッセージ）
            Toast.makeText(mContext, getString(R.string.del_memo), Toast.LENGTH_SHORT).show();
            return true;
        }

        // 終了
        if (id == R.id.action_finish) {
            // アプリを終了する
            finish();
            return true;
        }

        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * リストデータを端末（アプリ内）に保存
     */
    public void saveList() {
        // データリストをJSON配列に変換
        JSONArray array = new JSONArray();
        for (int i = 0, length = mListView.getCount(); i < length; i++) {
            try {
                array.put(i, mListView.getItemAtPosition(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // key名を"list"としてシリアライズ化データをアプリに保存
        mShared.edit().putString("list", array.toString()).commit();
    }

    /**
     * 端末（アプリ内）に保存したリストデータを読み込む
     */
    public void readList() {
        // key名が"list"のシリアライズ化データをアプリから取り出す
        String sList = mShared.getString("list", "");
        try {
            // JSONに変換
            JSONArray array = new JSONArray(sList);
            for (int i = 0, length = array.length(); i < length; i++) {
                // アダプターの最後尾に入力内容を追加
                mAdapter.add(array.optString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * キーボード非表示
     */
    public void HideKeyboard() {
        // アプリ起動時にEditTextへのフォーカスによるキーボード非表示
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // EditTextへのフォーカス移動時にキーボード非表示
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
}
