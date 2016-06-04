package com.riontech.calendar;

import com.riontech.calendar.dao.Event;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Dhaval Soneji on 3/6/16.
 */
public class Singleton {
    private final static String TAG = Singleton.class.getSimpleName();
    private static Singleton mInstance = null;
    private GregorianCalendar mMonth;
    private String mCurrentDate;
    private String mTodayDate;
    private int mIsSwipeViewPager = 2;
    private String startMonth;
    private String endMonth;
    private ArrayList<Event> mEventManager;

    public Singleton() {

    }

    public static Singleton getInstance() {
        if (mInstance == null) {
            mInstance = new Singleton();
        }
        return mInstance;
    }

    public GregorianCalendar getMonth() {
        return mMonth;
    }

    public void setMonth(GregorianCalendar month) {
        this.mMonth = month;
    }

    public String getCurrentDate() {
        return mCurrentDate;
    }

    public void setCurrentDate(String mCurrentDate) {
        this.mCurrentDate = mCurrentDate;
    }

    public String getTodayDate() {
        return mTodayDate;
    }

    public void setTodayDate(String mTodayDate) {
        this.mTodayDate = mTodayDate;
    }

    public int getIsSwipeViewPager() {
        return mIsSwipeViewPager;
    }

    public void setIsSwipeViewPager(int isSwipeViewPager) {
        this.mIsSwipeViewPager = isSwipeViewPager;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public ArrayList<Event> getEventManager() {
        return mEventManager;
    }

    public void setEventManager(ArrayList<Event> eventManagerList) {
        this.mEventManager = eventManagerList;
    }

}
