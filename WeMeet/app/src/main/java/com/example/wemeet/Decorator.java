package com.example.wemeet;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Collection;
import java.util.HashSet;

import static androidx.core.content.ContextCompat.getColor;

public class Decorator extends Thread{
    private int color = 0;
    private final HashSet<CalendarDay> dates;
    private ColorDrawable drawable;
    private Context context;
    private MaterialCalendarView view;

    public Decorator(Context context, int color, Collection<CalendarDay> dates, MaterialCalendarView view){
        this.context = context;
        this.dates = new HashSet<>(dates);
        this.view = view;
    }

    public void run(){
        view.addDecorator(new DateDecorator(context, getColor(context, R.color.gray), dates));

    }

}
