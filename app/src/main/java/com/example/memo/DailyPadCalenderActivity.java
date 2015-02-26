package com.example.memo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;

public class DailyPadCalenderActivity extends ActionBarActivity {
    private FrameLayout mMainLayout;

    private SharedPreferences mShared;

    private View mBeforeView;
    private View mView;
    private View mNextView;

    private Calendar mCalender;

    private HashMap<Integer, String> mWeekList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_pad_calender);
        mMainLayout = (FrameLayout) findViewById(R.id.fl_daily_pad_calender_main);
        mWeekList = new HashMap<>();

        mWeekList.put(1, getString(R.string.sun));
        mWeekList.put(2, getString(R.string.mon));
        mWeekList.put(3, getString(R.string.tue));
        mWeekList.put(4, getString(R.string.wed));
        mWeekList.put(5, getString(R.string.thu));
        mWeekList.put(6, getString(R.string.fri));
        mWeekList.put(7, getString(R.string.sat));


        mShared = PreferenceManager.getDefaultSharedPreferences(this);
        String shared_calender = mShared.getString("daily_pad", "");
        if (mCalender == null) {
            mCalender = Calendar.getInstance();
        }

        Date date_now = new Date(System.currentTimeMillis());
//        if(!shared_calender.equals("")){
//            Toast.makeText(this,shared_calender,Toast.LENGTH_SHORT).show();
//            mCalender.setTime(Date.valueOf(shared_calender));
//            Calendar calendar_now = Calendar.getInstance();
//            int now = Integer.valueOf(calendar_now.get(Calendar.YEAR)+(calendar_now.get(Calendar.MONTH)+1)+calendar_now.get(Calendar.DATE));
//            int shared  = Integer.valueOf(mCalender.get(Calendar.YEAR)+mCalender.get(Calendar.MONTH)+mCalender.get(Calendar.DATE));
//            if(shared > now){
//                mCalender.setTime(date_now);
//            }
//        }else{
//            mCalender.setTime(date_now);
//        }

        mCalender.setTime(date_now);

        View pad = makeDailyPad();
        mView = pad;
        mMainLayout.addView(mView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalender.add(Calendar.DATE, 1);
                View pad = makeDailyPad();
                mBeforeView = mView;
                mView = pad;

                Animator anime = AnimatorInflater.loadAnimator(DailyPadCalenderActivity.this, R.animator.daily_pad_out);
                anime.setTarget(mMainLayout.getChildAt(0));
                anime.start();
//                try {
//                    Thread.sleep(1300);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }

                mMainLayout.removeView(mMainLayout.getChildAt(0));
                mMainLayout.addView(mView);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily_pad_calender, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View makeDailyPad() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pad = inflater.inflate(R.layout.daily_pad_calender, null);
        RelativeLayout pad_main = (RelativeLayout) pad.findViewById(R.id.rl_daily_pad_calender_main);
        TextView year_view = (TextView) pad.findViewById(R.id.txt_year);
        TextView month_view = (TextView) pad.findViewById(R.id.txt_month);
        TextView day_view = (TextView) pad.findViewById(R.id.txt_day);
        TextView week_view = (TextView) pad.findViewById(R.id.txt_week);

        String year_str = String.valueOf(mCalender.get(Calendar.YEAR)) + this.getString(R.string.year);
        String month_str = String.valueOf(mCalender.get(Calendar.MONTH) + 1) + this.getString(R.string.month);
        String day_str = String.valueOf(mCalender.get(Calendar.DATE));

        StringBuilder date_shared = new StringBuilder();
        date_shared.append(String.valueOf(mCalender.get(Calendar.YEAR)));
        date_shared.append("-");
        date_shared.append(String.valueOf(mCalender.get(Calendar.MONTH)));
        date_shared.append("-");
        date_shared.append(String.valueOf(mCalender.get(Calendar.DATE)));

        mShared.edit().putString("daily_pad", date_shared.toString()).commit();

        year_view.setText(year_str + month_str);
//        month_view.setText(month_str);
        day_view.setText(day_str);
        week_view.setText(mWeekList.get(mCalender.get(Calendar.DAY_OF_WEEK)));

        ViewGroup parent = (ViewGroup) pad.getParent();
        if (parent != null) {
            parent.removeView(pad);
        }

        return pad;
    }
}
