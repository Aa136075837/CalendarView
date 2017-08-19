package com.yang.mac.calendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yang.mac.calendarview.widget.CalendarTitle;

public class MainActivity extends AppCompatActivity implements CalendarTitle
        .OnDateChangedListener, View.OnClickListener {

    private CalendarTitle mActMainCalendarTitle;
    private Button mDayBtn;
    private Button mWeekBtn;
    private Button mYearBtn;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initViews ();
        initListener ();
    }

    private void initViews () {
        mActMainCalendarTitle = (CalendarTitle) findViewById (R.id.act_main_calendar_title);
        mDayBtn = (Button) findViewById (R.id.act_main_day);
        mWeekBtn = (Button) findViewById (R.id.act_main_week);
        mYearBtn = (Button) findViewById (R.id.act_main_month);
    }

    private void initListener () {
        mActMainCalendarTitle.setOnDateChangedListener (this);
        mDayBtn.setOnClickListener (this);
        mWeekBtn.setOnClickListener (this);
        mYearBtn.setOnClickListener (this);
    }

    @Override
    public void onDateChanged (int month, int day) {

    }

    @Override
    public void onWeekChanged (int year, int week) {

    }

    @Override
    public void onMonthChanged (int year, int month) {

    }

    @Override
    public void onClick (View view) {
        switch (view.getId ()) {
            case R.id.act_main_day:
                mActMainCalendarTitle.setDateStyle (CalendarTitle.STYLE_DATA);
                break;
            case R.id.act_main_week:
                mActMainCalendarTitle.setDateStyle (CalendarTitle.STYLE_WEEK);
                break;
            case R.id.act_main_month:
                mActMainCalendarTitle.setDateStyle (CalendarTitle.STYLE_MONTH);
                break;
        }
    }
}
