package com.example.layouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class addevent extends AppCompatActivity {

    EditText eventform;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);

        eventform = findViewById(R.id.event);
        addEvent = findViewById(R.id.okbotton);
        FirebaseFirestore fStore;

        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!eventform.getText().toString().isEmpty()) {


                    if(intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }else{
                        Toast.makeText(addevent.this, "no app that support action",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(addevent.this, "please fill", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}