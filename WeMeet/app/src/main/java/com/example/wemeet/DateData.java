package com.example.wemeet;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;

public class DateData {
    String usergroupid;
    public ArrayList<CalendarDay> eventdate;

    public DateData(String usergroupid, ArrayList<CalendarDay> eventdate) {
        this.usergroupid = usergroupid;
        this.eventdate = eventdate;
    }

    public ArrayList<CalendarDay> getEventdate(){
        return eventdate;
    }

}
