package com.riontech.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.riontech.calendar.CustomCalendar;
import com.riontech.calendar.dao.EventData;
import com.riontech.calendar.dao.dataAboutDate;
import com.riontech.calendar.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CustomCalendar mCustomCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCustomCalendar = (CustomCalendar) findViewById(R.id.customCalendar);

        String[] arr = {"2016-08-22", "2016-08-11", "2016-08-15", "2016-09-16", "2016-09-25"};
        for (int i = 0; i < 5; i++) {
            int eventCount = 10;
            mCustomCalendar.addAnEvent(arr[i], eventCount, getEventDataList(eventCount));
        }
    }

    public ArrayList<EventData> getEventDataList(int count) {
        ArrayList<EventData> eventDataList = new ArrayList();

        for (int i = 0; i < count; i++) {
            EventData dateData = new EventData();
            ArrayList<dataAboutDate> dataAboutDates = new ArrayList();

            dateData.setSection(CalendarUtils.getNAMES()[new Random().nextInt(CalendarUtils.getNAMES().length)]);
            dataAboutDate dataAboutDate = new dataAboutDate();

            int index = new Random().nextInt(CalendarUtils.getEVENTS().length);

            dataAboutDate.setTitle(CalendarUtils.getEVENTS()[index]);
            dataAboutDate.setSubject(CalendarUtils.getEventsDescription()[index]);
            dataAboutDates.add(dataAboutDate);

            dateData.setData(dataAboutDates);
            eventDataList.add(dateData);
        }

        return eventDataList;
    }
}
