package com.riontech.calendar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.riontech.calendar.CustomCalendar;
import com.riontech.calendar.Singleton;
import com.riontech.calendar.adapter.CalendarGridviewAdapter;
import com.riontech.calendar.dao.CalendarDecoratorDao;
import com.riontech.calendar.dao.CalendarResponse;
import com.riontech.calendar.dao.Event;
import com.riontech.calendar.dao.EventData;
import com.riontech.calendar.utils.CalendarUtils;
import com.riontech.calendar.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Dhaval Soneji on 31/3/16.
 */
public class CalendarFragment extends Fragment {
    private static final String TAG = CalendarFragment.class.getSimpleName();

    private GridView mGridview;
    private LinearLayout mLlDayList;
    private RelativeLayout mRlHeader;
    private GregorianCalendar month;
    private CalendarGridviewAdapter mCalendarGridviewAdapter;
    private boolean flagMaxMin = false;
    public static String currentDateSelected;
    private Calendar mCalendar;
    private DateFormat mDateFormat;
    private GregorianCalendar mPMonth;
    private int mMonthLength;
    private GregorianCalendar mPMonthMaxSet;
    private ArrayList<CalendarDecoratorDao> mEventList = new ArrayList<>();
    private ViewGroup rootView;
    private static CustomCalendar mCustomCalendar;


    /**
     * create CalendarFragment object and call setCalendar().
     *
     * @param calendar
     * @return
     */
    public static CalendarFragment newInstance(CustomCalendar calendar) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setCalendar(calendar);
        return fragment;
    }

    /**
     * initialize CustomCalendar to access method of it.
     *
     * @param calendar
     */
    private void setCalendar(CustomCalendar calendar) {
        mCustomCalendar = calendar;
    }

    /**
     * initialize Calendar and for the first time load Current Month data.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragement, container, false);

        initCurrentMonthInGridview();

        if (Singleton.getInstance().getIsSwipeViewPager() == 2)
            refreshDays();

        return rootView;
    }

    /**
     * initialize DaysName(Sun,Mon,...) Layout,
     * initialize Current MonthHeader(June 2016) Layout,
     * initialize Gridview(Current month) With click event
     */
    private void initCurrentMonthInGridview() {

        mLlDayList = (LinearLayout) rootView.findViewById(R.id.llDayList);
        mRlHeader = (RelativeLayout) rootView.findViewById(R.id.rlMonthTitle);

        month = Singleton.getInstance().getMonth();

        mCalendarGridviewAdapter = new CalendarGridviewAdapter(getActivity(), mEventList, month);

        mCalendar = month;
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        mDateFormat = CalendarUtils.getCalendarDBFormat();

        mGridview = (GridView) rootView.findViewById(R.id.gvCurrentMonthDayList);
        mGridview.setAdapter(mCalendarGridviewAdapter);

        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format(CalendarUtils.getCalendarMonthTitleFormat(), month));

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String selectedDate = mEventList.get(position).getDate();
                ((CalendarGridviewAdapter) parent.getAdapter()).setSelected(v, selectedDate);
                fetchEvents(selectedDate);

            }
        });
    }

    /**
     * check if date has event or not,
     * then
     *
     * @param date
     */
    public void fetchEvents(String date) {
        boolean flag = false;
        int pos = 0;
        for (int i = 0; i < Singleton.getInstance().getEventManager().size(); i++) {
            if (Singleton.getInstance().getEventManager().get(i).getDate().equalsIgnoreCase(date)) {
                flag = true;
                pos = i;
            }
        }
        ArrayList<EventData> eventDataArrayList = new ArrayList();
        if (flag) {
            if (Singleton.getInstance().getEventManager().get(pos).getEventData() != null) {
                eventDataArrayList = Singleton.getInstance().getEventManager().get(pos).getEventData();
            }
        }

        if (mCustomCalendar != null)
            mCustomCalendar.setDateSelectionData(eventDataArrayList);
    }

    /**
     * refresh current month
     */
    public void refreshCalendar() {
        TextView title = (TextView) rootView.findViewById(R.id.title);
        refreshDays();
        title.setText(android.text.format.DateFormat.format(CalendarUtils.getCalendarMonthTitleFormat(), month));
    }

    /**
     * refresh current month days
     */
    public void refreshDays() {

        //clear List
        mEventList.clear();
        //create clone
        mPMonth = (GregorianCalendar) mCalendar.clone();

        CalendarGridviewAdapter.firstDay = mCalendar.get(GregorianCalendar.DAY_OF_WEEK);

        int mMaxWeekNumber = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

        mMonthLength = mMaxWeekNumber * 7;
        int mMaxP = getmMaxP();
        int mCalMaxP = mMaxP - (CalendarGridviewAdapter.firstDay - 1);

        mPMonthMaxSet = (GregorianCalendar) mPMonth.clone();

        mPMonthMaxSet.set(GregorianCalendar.DAY_OF_MONTH, mCalMaxP + 1);

        setData(getCalendarData());

    }

    /**
     * @return
     */
    private CalendarResponse getCalendarData() {
        CalendarResponse calendarResponse = new CalendarResponse();
        calendarResponse.setStartmonth(Singleton.getInstance().getStartMonth());
        calendarResponse.setEndmonth(Singleton.getInstance().getEndMonth());
        calendarResponse.setMonthdata(Singleton.getInstance().getEventManager());
        return calendarResponse;
    }

    /**
     * @param calendarResponse
     */
    private void setData(CalendarResponse calendarResponse) {

        mLlDayList.setVisibility(View.VISIBLE);
        mRlHeader.setVisibility(View.VISIBLE);
        mGridview.setVisibility(View.VISIBLE);

        if (calendarResponse.getMonthdata() != null) {

            ArrayList<Event> monthDataList = calendarResponse.getMonthdata();

            boolean handled = false;

            for (int n = 0; n < mMonthLength; n++) {
                String mItemValue = mDateFormat.format(mPMonthMaxSet.getTime());
                mPMonthMaxSet.add(GregorianCalendar.DATE, 1);

                /**
                 * Check on event list if's the current date has events
                 */
                for (int i = 0; i < monthDataList.size(); i++) {
                    if(mItemValue.equalsIgnoreCase(monthDataList.get(i).getDate())) {
                        CalendarDecoratorDao eventDao = new CalendarDecoratorDao(
                                monthDataList.get(i).getDate(),
                                Integer.parseInt(monthDataList.get(i).getCount()));
                        mEventList.add(eventDao);
                        handled = true;
                        break;
                    }
                }

                if(!handled) {
                    CalendarDecoratorDao eventDao = new CalendarDecoratorDao(mItemValue, 0);
                    mEventList.add(eventDao);
                } else {
                    handled = false;
                }
            }

            mCalendarGridviewAdapter.notifyDataSetChanged();

            if (!flagMaxMin) {
                flagMaxMin = true;
            }
        }
    }

    /**
     * setup next month and check for calendar range
     */

    public void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
            Singleton.getInstance().setMonth(month);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
            Singleton.getInstance().setMonth(month);
        }
    }

    /**
     * setup previous month and check for calendar range
     */
    public void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
            Singleton.getInstance().setMonth(month);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
            Singleton.getInstance().setMonth(month);
        }
    }

    /**
     * @return
     */
    private int getmMaxP() {
        int maxP;
        if (mCalendar.get(GregorianCalendar.MONTH) == mCalendar
                .getActualMinimum(GregorianCalendar.MONTH)) {
            mPMonth.set((mCalendar.get(GregorianCalendar.YEAR) - 1),
                    mCalendar.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            mPMonth.set(GregorianCalendar.MONTH,
                    mCalendar.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = mPMonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }

}
