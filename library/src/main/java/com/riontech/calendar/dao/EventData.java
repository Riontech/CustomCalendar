package com.riontech.calendar.dao;

import java.util.ArrayList;

/**
 * Created by Dhaval Soneji on 13/5/16.
 */
public class EventData {
    private String section;
    private ArrayList<dataAboutDate> data;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public ArrayList<dataAboutDate> getData() {
        return data;
    }

    public void setData(ArrayList<dataAboutDate> data) {
        this.data = data;
    }
}
