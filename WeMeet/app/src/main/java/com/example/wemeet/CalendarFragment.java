package com.example.wemeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.core.content.ContextCompat.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.wemeet.MainActivityTest.DateDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import static androidx.core.content.ContextCompat.getColor;

public class CalendarFragment extends Fragment {
    AddScheduleFragment addschedule;

    int syear, smonth, sday;
    int ayear,amonth,aday;

    CalendarDay c;
//일정이 추가된 날짜 가져오기 -> 데이터베이스에서 가져오는 방법으로 해야할듯
  /*  public void onCreate(@NonNull @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                int addyear = bundle.getInt("addyear");
                int addmonth = bundle.getInt("addmonth");
                int addday = bundle.getInt("addday");
                ayear = addyear;
                amonth = addmonth;
                aday = addday;
            }
        });

        c = CalendarDay.from(ayear,amonth,aday);

    }*/

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
                //선택 날짜 보내기
                Bundle result = new Bundle();
                result.putInt("keyyear", syear);
                result.putInt("keymonth", smonth);
                result.putInt("keyday", sday);
                getParentFragmentManager().setFragmentResult("requestKey", result);
            }
        });
        //일정이 있는 날짜 리스트
        Collection<CalendarDay> eventslist = new ArrayList<>();
        //업데이트 함수로 구현??
        eventslist.add(CalendarDay.today());
        //리스트에 있는 날짜 dot로 표시
        materialCalendarView.addDecorator(new DateDecorator(getActivity(), getColor(getActivity(), R.color.black), eventslist));


    return view;
    }
}
