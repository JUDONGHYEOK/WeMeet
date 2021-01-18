package com.example.layouttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.time.LocalDate;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCalendarView materialCalendarView = (MaterialCalendarView) findViewById(R.id.home_calendarView);

        ImageButton addButton = (ImageButton) findViewById(R.id.okbotton);


        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), addevent.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                intent.putExtra("cdate", materialCalendarView.getSelectedDate().getDay());
            }
        });


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(MainActivity.this, ""+date, Toast.LENGTH_LONG).show();
            }
        });


    }
}