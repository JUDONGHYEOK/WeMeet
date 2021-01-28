package com.example.wemeet;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

import static androidx.core.content.ContextCompat.getColor;

public class DateDecorator implements DayViewDecorator  {
    private int color = 0;
    private final HashSet<CalendarDay> dates;
    private ColorDrawable drawable;
    private Context context;

    public DateDecorator(Context context, int color, Collection<CalendarDay> dates) {
        this.context = context;
        this.color = color;
        this.dates = new HashSet<>(dates);
        drawable = new ColorDrawable(color);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(10, color));
        drawable.setColor(000000);
        view.setBackgroundDrawable(drawable);
    }

}
