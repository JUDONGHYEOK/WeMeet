package com.example.wemeet;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.internal.$Gson$Preconditions;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static androidx.core.content.ContextCompat.getColor;

public class CalendarFragment extends Fragment {
    private static final String TAG = "homecalendar";

    private Context context;
    private HashSet<CalendarDay> dates;

    int syear, smonth, sday;

    ArrayList<String> eventdates;
    String Uid;
    MaterialCalendarView materialCalendarView;

    Collection<CalendarDay> markdate = new ArrayList<CalendarDay>(Arrays.asList(CalendarDay.from(2021,01,03)));
    ArrayList<CalendarDay> decodate = new ArrayList<CalendarDay>(Arrays.asList(CalendarDay.from(2021,01,01)));;

    private FirebaseFirestore fstore;



    public void onCreate(@NonNull @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        markdate.add(CalendarDay.from(2021,01,4));
        markdate.add(CalendarDay.from(2021,01,29));
        markdate.add(CalendarDay.from(2021,01,15));
        //GetDB getDB = new GetDB(decodate);
       // getDB.setPriority(10);
       // getDB.start();

        fstore = FirebaseFirestore.getInstance();
        eventdates = new ArrayList<>();
        Uid = ((MainActivity)getActivity()).userId();

        DocumentReference docRef = fstore.collection("Adates").document("all");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    //all exists
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        ArrayList<String> strdates = (ArrayList<String>) document.getData().get("Eventdates");
                        //string -> CalendarDay
                        DateData st = new DateData(strdates);
                        decodate = st.CalendardateChange();

                        Log.d(TAG, "if" + decodate);
                        long priority = Thread.currentThread().getId();
                        String name = Thread.currentThread().getName();
                        Log.d(TAG, "우선순위" + priority);
                        Log.d(TAG, "우선순위" + name);


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"error "+ decodate);
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG,"create "+ decodate);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.home_calendarView);
        materialCalendarView.setSelectionColor(Color.BLACK);

        materialCalendarView.setSelectedDate(CalendarDay.today());

        ImageButton addButton = (ImageButton) view.findViewById(R.id.okbotton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Bundle result = new Bundle();
                result.putInt("keyyear", syear);
                result.putInt("keymonth", smonth);
                result.putInt("keyday", sday);
                ((MainActivity)getActivity()).replaceFragment(new AddScheduleFragment(),result);

            }
        });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                syear = date.getYear();
                smonth = date.getMonth();
                sday = date.getDay();
            }
        });

        //  Collection<CalendarDay> eventslist = new ArrayList<>();
        //eventslist.add(CalendarDay.today());
        //eventslist.add(CalendarDay.from(2020,01,01));
        // materialCalendarView.addDecorator(new DateDecorator(getActivity(), getColor(getActivity(), R.color.black), eventslist));
        Log.d(TAG,"표시 전 "+ decodate);
       // Decorator deco = new Decorator(getActivity(), getColor(getActivity() ,R.color.gray), decodate, materialCalendarView);
        //deco.setPriority(1);
        //deco.start();
        materialCalendarView.addDecorator(new DateDecorator(getActivity(), getColor(getActivity(), R.color.gray), decodate));
        Log.d(TAG,"표시 후 "+ decodate);


        return view;
    }

}

