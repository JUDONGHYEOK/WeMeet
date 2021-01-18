package com.example.wemeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class CalendarActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCalendarView materialCalendarView = (MaterialCalendarView) findViewById(R.id.home_calendarView);

        ImageButton addButton = (ImageButton) findViewById(R.id.okbotton);


        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), AddScheduleActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                intent.putExtra("cdate", materialCalendarView.getSelectedDate().getDay());
            }
        });


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(com.example.wemeet.CalendarActivity.this, ""+date, Toast.LENGTH_LONG).show();
            }
        });


    }
}