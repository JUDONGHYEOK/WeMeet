package com.example.wemeet;

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
import androidx.fragment.app.FragmentResultListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import static androidx.core.content.ContextCompat.getColor;

public class CalendarFragment extends Fragment {
    private static final String TAG = "homecalendar";

    int syear, smonth, sday;

    ArrayList<String> eventdates;
    String Uid;
    MaterialCalendarView materialCalendarView;

    Collection<CalendarDay> markdate = new ArrayList<>(Arrays.asList(CalendarDay.from(2021,01,03)));
    ArrayList<CalendarDay> decodate = new ArrayList<CalendarDay>(Arrays.asList(CalendarDay.from(2021,01,01)));;

    private FirebaseFirestore fstore;


    //일정이 추가된 날짜 가져오기 -> 데이터베이스에서 가져오는 방법으로 해야할듯
    public void onCreate(@NonNull @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        decodate.clear();
        Log.d(TAG,"초기화"+decodate);

        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.home_calendarView);
        materialCalendarView.setSelectionColor(Color.BLACK);


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
                    //all 이 있을 때
                    if (document.exists()) {

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        ArrayList<String> strdates = (ArrayList<String>) document.getData().get("Eventdates");

                        DateData st = new DateData(strdates);

                        decodate = st.CalendardateChange(strdates);

                        Log.d(TAG, "if문 안에서" + decodate);

                        //all 없을  때
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
                Log.d(TAG,"실패 "+ decodate);
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        markdate.addAll(decodate);
        Log.d(TAG,"create 안에서 "+ decodate);

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
       // materialCalendarView.addDecorator(new DateDecorator(getActivity(), getColor(getActivity(), R.color.black), markdate));
        Log.d(TAG,"표시 후 "+ decodate);


        return view;
    }
}
