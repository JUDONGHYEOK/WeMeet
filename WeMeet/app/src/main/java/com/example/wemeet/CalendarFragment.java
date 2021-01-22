package com.example.wemeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class CalendarFragment extends Fragment {
    AddScheduleFragment addschedule;

    int syear;
    int smonth;
    int sday ;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_calendar,container,false);

        addschedule = new AddScheduleFragment();

        MaterialCalendarView materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.home_calendarView);

        ImageButton addButton = (ImageButton) view.findViewById(R.id.okbotton);


        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){


           /*     Intent intent = new Intent(getApplicationContext(), AddScheduleActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                intent.putExtra("cdate", materialCalendarView.getSelectedDate().getDay());*/




                MainActivityTest mainactivity = (MainActivityTest) getActivity();
                mainactivity.FragmentChange(0);
            }
        });


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
              //  Toast.makeText(getContext(), " 123"+date, Toast.LENGTH_LONG).show();
                syear = date.getYear();
                smonth = date.getMonth();
                sday = date.getDay();
                Toast.makeText(getContext(), syear+"?"+smonth+"?"+sday, Toast.LENGTH_LONG).show();

                Bundle result = new Bundle();
                result.putInt("keyyear", syear);
                result.putInt("keymonth", smonth);
                result.putInt("keyday", sday);
                getParentFragmentManager().setFragmentResult("requestKey", result);
            }
        });


    return view;
    }
}
