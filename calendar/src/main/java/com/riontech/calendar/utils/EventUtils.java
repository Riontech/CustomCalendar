package com.riontech.calendar.utils;

import com.google.gson.Gson;
import com.riontech.calendar.dao.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Dhaval Riontech on 18/8/16.
 */
public class EventUtils {
    private static HashMap<String, ArrayList<Event>> eventMap;
    private static String[] monthYearArray;
    private static int currentYear;

    private static void generateMonthYearArray(int year) {
        if (monthYearArray == null || currentYear != year) {
            monthYearArray = new String[12];
            currentYear = year;
            for (int i = 0; i < monthYearArray.length; i++) {
                monthYearArray[i] = (i + 1) + "-" + year;
            }
        }
    }

    public static void generateMonthsEvents(ArrayList<Event> events, int cYear) {
//        if (eventMap == null || currentYear != cYear) {
            currentYear = cYear;
            eventMap = new HashMap<>();
            generateMonthYearArray(currentYear);
            System.out.println("Event List=>"+new Gson().toJson(events));
            for (int i = 0; i < monthYearArray.length; i++) {
                System.out.println("month-year " + monthYearArray[i]);
                ArrayList<Event> monthEvents = new ArrayList<>();
                for (Event event : events) {
                    System.out.println("event date " + event.getDate());
                    String monthYear = getMonthYearString(getDateFromString(event.getDate()));
                    System.out.println("event month-year" + monthYear);
                    if (monthYear.equalsIgnoreCase(monthYearArray[i])) {
                        System.out.println("month-year " + monthYear + " matched");
                        monthEvents.add(event);
                        putEvent(monthYear, monthEvents);
                    }
                }
            }
//        }

        System.out.println(new Gson().toJson(eventMap));
    }

    public static String getMonthYearString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (cal.get(Calendar.MONTH) + 1)+ "-" + cal.get(Calendar.YEAR);
    }

    public static Date getDateFromString(String date) {
        try {
            return CalendarUtils.getCalendarDBFormat().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static boolean hasMonth(String monthYear) {
        return eventMap.containsKey(monthYear);
    }

    public static ArrayList<Event> getMonthEvent(String key) {
        if (hasMonth(key)) {
            return eventMap.get(key);
        }
        return null;
    }

    public static void putEvent(String key, ArrayList<Event> events) {
        eventMap.put(key, events);
    }

//    public static int getDateDecoretorCount(String date){
//        Date currentDate = getDateFromString(date);
//        String monthYear = getMonthYearString(currentDate);
//        ArrayList<Event> events = getMonthEvent(monthYear);
//        int counter = 0;
//        if(events != null){
//            for (Event event : events) {
//                if(date.equalsIgnoreCase(event.getDate()))
//            }
//        }
//        return counter;
//    }

    public static HashMap<String, ArrayList<Event>> getEventMap(){
        return eventMap;
    }
}
