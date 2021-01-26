package com.example.wemeet;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class DateData extends Thread {
    private static final String TAG = "!!DATA!!";
    ArrayList<CalendarDay> eventdate;
    Collection<CalendarDay> emp;
    ArrayList<String> strdate;
    String t;
    ArrayList<String> eventdates;
    String Uid;

    ArrayList<CalendarDay> decodate;
    private FirebaseFirestore fstore;

    ArrayList<String> outdate;

    public DateData(ArrayList<String> strdate) {
        this.strdate = strdate;
    }

   @RequiresApi(api = Build.VERSION_CODES.O)

   public ArrayList<CalendarDay> CalendardateChange () {

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
