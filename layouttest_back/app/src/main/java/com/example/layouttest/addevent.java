package com.example.layouttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class addevent extends AppCompatActivity {

    private static final String TAG = "addevent";

    private EditText eventform;
    private Button addEvent;
    private FirebaseFirestore fStore;

    private TextView showdate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private TextView Timer1;

    private String datedb;
    private String time;
    int hour,min;

    Map<String, Object> events = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);

        showdate = (TextView) findViewById(R.id.setDate);

        Intent a = new Intent(getIntent());
        String caldate = a.getStringExtra("cdate");
        showdate.setText(caldate);
       /* Toast.makeText(getApplicationContext(),"?? "+caldate,Toast.LENGTH_SHORT).show(); */

        showdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR)-1;
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        addevent.this,android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "onDateSet: date: " + year + "년" + month + "월" + day + "일");

                String date = year + "년" +(month +1 )+ "월" + day + "일";
                datedb = year+"-"+(month+1)+"-"+day;
                showdate.setText(date);
            }
        };

        Timer1 = findViewById(R.id.setTime);

        Timer1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        addevent.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        min = minute;

                        time = hour + ":" + min;
                        SimpleDateFormat f24Hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try{
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "aa hh:mm"
                            );
                            Timer1.setText(f12Hours.format(date));
                        }catch (ParseException e){
                            e.printStackTrace();
                        }
                    }
                },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
            }
        });


        fStore = FirebaseFirestore.getInstance();

        eventform = (EditText) findViewById(R.id.event);
        addEvent = (Button) findViewById(R.id.okbotton);

        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String title = eventform.getText().toString();
                Map<String, String> Eventsmap = new HashMap<>();

                Eventsmap.put("Title", title);
                Eventsmap.put("Date", datedb);
                Eventsmap.put("Time", time);

                fStore.collection("Events").add(Eventsmap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(addevent.this,"Data upload :)",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(addevent.this,"Error : "+ error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}