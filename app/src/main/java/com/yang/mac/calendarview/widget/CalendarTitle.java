package com.yang.mac.calendarview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yang.mac.calendarview.R;

import java.util.Calendar;
import java.util.Date;

/**
 * @author bo.
 * @Date 2017/8/19.
 * @desc
 */

public class CalendarTitle extends LinearLayout implements View.OnClickListener {
    public static final int STYLE_DATA = 0;
    public static final int STYLE_WEEK = 1;
    public static final int STYLE_MONTH = 2;
    public int CURRENT_STYLE = STYLE_DATA;
    private ImageView mLeftArr;
    private TextView mContentTv;
    private ImageView mRightArr;
    private int mYear;
    private int mMonth;
    private int mDate1;
    private int mWeekForYear;

    public CalendarTitle (Context context) {
        this (context, null);
    }

    public CalendarTitle (Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public CalendarTitle (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        init (context, attrs, defStyleAttr);
    }

    private void init (Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from (context).inflate (R.layout.data_subtitle, this, true);
        mLeftArr = (ImageView) findViewById (R.id.subtitle_left_arrow);
        mContentTv = (TextView) findViewById (R.id.subtitle_center_tv);
        mRightArr = (ImageView) findViewById (R.id.subtitle_right_arrow);

        mLeftArr.setOnClickListener (this);
        mContentTv.setOnClickListener (this);
        mRightArr.setOnClickListener (this);

        initContent ();
        show ();
    }

    private void show () {
        switch (CURRENT_STYLE) {
            case STYLE_DATA:
                mContentTv.setText (mMonth + getResources ().getString (R.string.month) + mDate1
                        + getResources ().getString (R.string.date));
                break;
            case STYLE_WEEK:
                mContentTv.setText (mYear + getResources ().getString (R.string.year_week) +
                        mWeekForYear + getResources ().getString (R.string.week));
                break;
            case STYLE_MONTH:
                mContentTv.setText (mYear + getResources ().getString (R.string.year) + mMonth +
                        getResources ().getString (R.string.month));
                break;
        }
    }

    private void initContent () {
        Date date = new Date ();
        mYear = date.getYear () + 1900;
        mMonth = date.getMonth () + 1;
        mDate1 = date.getDate ();
        mWeekForYear = Calendar.getInstance ().get (Calendar.WEEK_OF_YEAR);
    }

    @Override
    public void onClick (View v) {
        int i = v.getId ();
        if (i == R.id.subtitle_left_arrow) {
            switch (CURRENT_STYLE) {
                case STYLE_DATA:
                    reduceDate ();
                    if (mListener != null) {
                        mListener.onDateChanged (mMonth, mDate1);
                    }
                    break;
                case STYLE_WEEK:
                    reduceWeek ();
                    if (mListener != null) {
                        mListener.onWeekChanged (mYear, mWeekForYear);
                    }
                    break;
                case STYLE_MONTH:
                    reduceMonth ();
                    if (mListener != null) {
                        mListener.onMonthChanged (mYear, mMonth);
                    }
                    break;
            }
            show ();
        } else if (i == R.id.subtitle_center_tv) {

        } else if (i == R.id.subtitle_right_arrow) {
            Date currentDate = new Date ();
            int currentYear = currentDate.getYear () + 1900;
            int currentMonth = currentDate.getMonth () + 1;
            int currentDay = currentDate.getDate ();
            int currentWeek = Calendar.getInstance ().get (Calendar.WEEK_OF_YEAR);
            switch (CURRENT_STYLE) {
                case STYLE_DATA:
                    if (currentMonth == mMonth && currentDay == mDate1) {  // 不能增加到明天
                        return;
                    }
                    plusDate ();
                    if (mListener != null) {
                        mListener.onDateChanged (mMonth, mDate1);
                    }
                    break;
                case STYLE_WEEK:
                    if (currentYear == mYear && currentWeek == mWeekForYear) { //不能增加到下周
                        return;
                    }
                    plusWeek ();
                    if (mListener != null) {
                        mListener.onWeekChanged (mYear, mWeekForYear);
                    }
                    break;
                case STYLE_MONTH:
                    if (currentYear == mYear && currentMonth == mMonth) { // 不能增加到下个月
                        return;
                    }
                    plusMonth ();
                    if (mListener != null) {
                        mListener.onMonthChanged (mYear, mMonth);
                    }
                    break;
            }
            show ();
        }
    }

    private void plusMonth () {
        if (mMonth == 12) {
            mMonth = 1;
            mYear += 1;
        } else {
            mMonth += 1;
        }
    }

    private void reduceMonth () {
        if (mMonth == 1) {
            mMonth = 12;
            mYear -= 1;
        } else {
            mMonth -= 1;
        }
    }

    private void plusWeek () {
        if (mWeekForYear == 52) {
            mWeekForYear = 1;
            mYear += 1;
        } else {
            mWeekForYear += 1;
        }
    }

    private void reduceWeek () {
        if (mWeekForYear == 1) {
            mWeekForYear = 52;
            mYear -= 1;
        } else {
            mWeekForYear -= 1;
        }
    }

    /**
     * 加
     */
    private void plusDate () {
        if (mDate1 == 31) {
            if (mMonth == 12) {
                mMonth = 1;
                mDate1 = 1;
                mYear += 1;
            } else {
                mDate1 = 1;
                mMonth += 1;
            }
        } else if (mDate1 == 30) {
            if (MonthUtil.getInstance ().contains (mMonth)) {
                mDate1 += 1;
            } else {
                mDate1 = 1;
                mMonth += 1;
            }
        } else if (mDate1 == 28) {
            if (mMonth == 2 && mYear % 4 != 0) {
                mMonth += 1;
                mDate1 = 1;
            } else {
                mDate1 += 1;
            }
        } else if (mDate1 == 29 && mMonth == 2) {
            mMonth += 1;
            mDate1 = 1;
        } else {
            mDate1 += 1;
        }
    }

    /**
     * 减
     */
    private void reduceDate () {
        if (mDate1 == 1) {
            if (mMonth == 1) {
                mMonth = 12;
                mDate1 = 31;
                mYear -= 1;
            }
            mMonth -= 1;
            if (MonthUtil.getInstance ().contains (mMonth)) {
                mDate1 = 31;
            } else {
                if (mMonth == 2) {
                    if (mYear % 4 == 0) {
                        mDate1 = 29;
                    } else {
                        mDate1 = 28;
                    }
                } else {
                    mDate1 = 30;
                }
            }
        } else if (mDate1 > 1) {
            mDate1 -= 1;
        }
    }

    /**
     * @param style
     *         展示数据的方式 DataSubtitleView.STYLE_DATA { 月，日} ; DataSubtitleView.STYLE_WEEK { 年，周} ;
     *         DataSubtitleView.STYLE_MONTH { 年，月}
     */
    public void setDateStyle (int style) {
        CURRENT_STYLE = style;
        initContent ();
        show ();
        switch (CURRENT_STYLE) {
            case STYLE_DATA:
                if (mListener != null) {
                    mListener.onDateChanged (mMonth, mDate1);
                }
                break;
            case STYLE_WEEK:
                if (mListener != null) {
                    mListener.onWeekChanged (mYear, mWeekForYear);
                }
                break;
            case STYLE_MONTH:
                if (mListener != null) {
                    mListener.onMonthChanged (mYear, mMonth);
                }
                break;
        }
    }

    private OnDateChangedListener mListener;

    public void setOnDateChangedListener (OnDateChangedListener listener) {
        mListener = listener;
    }

    public interface OnDateChangedListener {
        void onDateChanged (int month, int day);

        void onWeekChanged (int year, int week);

        void onMonthChanged (int year, int month);
    }

}