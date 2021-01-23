package com.example.wemeet;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

public class DateData {
    private static final String TAG = "!!DATA!!";
    ArrayList<CalendarDay> eventdate;
    Collection<CalendarDay> emp;
    ArrayList<String> strdate;
    String t;

    public DateData(ArrayList<String> strdate) {
        this.strdate = strdate;
    }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public ArrayList<CalendarDay> CalendardateChange (ArrayList<String> strdate) {
        this.strdate = strdate;
        ArrayList<CalendarDay> eventdate = new ArrayList<>();

        for(int i=0; i<strdate.size(); i++){
            LocalDate localDate = LocalDate.parse(strdate.get(i));
            int year = localDate.getYear();
            int month = localDate.getMonthValue() ;
            int day = localDate.getDayOfMonth();
            CalendarDay date = CalendarDay.from(year,month,day);
            eventdate.add(i,date);
        }
        return eventdate;
    }

}
