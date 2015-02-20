package com.example.memo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.memo.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DailyPadCalenderActivity extends ActionBarActivity {
    private FrameLayout mMainLayout;

    private SharedPreferences mShared;

    private View mBeforeView;
    private View mView;
    private View mNextView;

    private Calendar mCalender;
    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_pad_calender);
        mMainLayout = (FrameLayout)findViewById(R.id.fl_daily_pad_calender_main);
        mShared = PreferenceManager.getDefaultSharedPreferences(this);
        String shared_calender = mShared.getString("daily_pad","");
        if(mCalender == null){
            mCalender = Calendar.getInstance();
        }

        Date date_now = new Date(System.currentTimeMillis());
        if(!shared_calender.equals("")){
            mCalender.setTime(Date.valueOf(shared_calender));
            Calendar calendar_now = Calendar.getInstance();
            int now = Integer.valueOf(calendar_now.get(Calendar.YEAR)+(calendar_now.get(Calendar.MONTH)+1)+calendar_now.get(Calendar.DATE));
            int shared  = Integer.valueOf(mCalender.get(Calendar.YEAR)+mCalender.get(Calendar.MONTH)+mCalender.get(Calendar.DATE));
            if(shared > now){
                mCalender.setTime(date_now);
            }
        }else{
            mCalender.setTime(date_now);
        }
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView =  inflater.inflate(R.layout.daily_pad_calender,mMainLayout);
        mNextView = inflater.inflate(R.layout.daily_pad_calender,mMainLayout);
        makeDailyPad(mView);
//        mCalender.add(Calendar.DATE,1);
//        makeDailyPad(mNextView);

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

    private void makeDailyPad(View pad){
        RelativeLayout pad_main = (RelativeLayout)pad.findViewById(R.id.rl_daily_pad_calender_main);
        TextView year_view = (TextView)pad.findViewById(R.id.txt_year);
        TextView month_view = (TextView)pad.findViewById(R.id.txt_month);
        TextView day_view = (TextView)pad.findViewById(R.id.txt_day);

        String year_str = String.valueOf(mCalender.get(Calendar.YEAR));
        String month_str = String.valueOf(mCalender.get(Calendar.MONTH)+1);
        String day_str = String.valueOf(mCalender.get(Calendar.DATE));

        String date_str = year_str+"-"+month_str+"-"+day_str;
        mShared.edit().putString("daily_pad",date_str).commit();

        year_view.setText(year_str);
        month_view.setText(month_str);
        day_view.setText(day_str);

        pad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBeforeView = mView;
                mView = mNextView;
                mCalender.add(Calendar.DATE,1);
                makeDailyPad(mView);
//                if(v.equals(mMainLayout.getChildAt(0))){
//                    mMainLayout.removeView(mView);
//                    makeDailyPad(mNextView);
//                    ViewGroup parent_next = (ViewGroup)mNextView.getParent();
//                    if(parent_next !=null){
//                        parent_next.removeView(mNextView);
//                    }
//                    mMainLayout.addView(mNextView);
//                }
            }
        });

    }
}
