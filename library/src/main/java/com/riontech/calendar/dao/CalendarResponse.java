package com.riontech.calendar.dao;

import java.util.ArrayList;

/**
 * Created by Dhaval Soneji on 13/5/16.
 */
public class CalendarResponse{
    private String startmon;
    private String endmon;
    private ArrayList<Event> monthdata;
    private ArrayList<EventData> currentDateData;

    public ArrayList<EventData> getCurrentDateData() {
        return currentDateData;
    }

    public void setCurrentDateData(ArrayList<EventData> currentDateData) {
        this.currentDateData = currentDateData;
    }

    public ArrayList<Event> getMonthdata() {
        return monthdata;
    }

    public void setMonthdata(ArrayList<Event> monthdata) {
        this.monthdata = monthdata;
    }

    public String getStartmonth() {
        return startmon;
    }

    public void setStartmonth(String startmonth) {
        this.startmon = startmonth;
    }

    public String getEndmonth() {
        return endmon;
    }

    public void setEndmonth(String endmonth) {
        this.endmon = endmonth;
    }

}
