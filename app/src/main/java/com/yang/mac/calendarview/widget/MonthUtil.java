package com.yang.mac.calendarview.widget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bo.
 * @Date 2017/8/7.
 * @desc
 */

public class MonthUtil {
    private List<Integer> month;

    private MonthUtil () {
        month = new ArrayList<> ();
        month.add (1);
        month.add (3);
        month.add (5);
        month.add (7);
        month.add (8);
        month.add (10);
        month.add (12);
    }

    static class SingleInstance {
        static MonthUtil Instance = new MonthUtil ();
    }

    public static MonthUtil getInstance () {
        return SingleInstance.Instance;
    }

    public boolean contains(Object o){
        return month.contains (o);
    }
}
