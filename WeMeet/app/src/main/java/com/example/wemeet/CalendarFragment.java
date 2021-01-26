package com.example.wemeet;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Collection;

import static androidx.core.content.ContextCompat.getColor;

public class CalendarFragment extends Fragment {
    private static final String TAG = "homecalendar";

    AddScheduleFragment addschedule;

    int syear, smonth, sday;
    int ayear,amonth,aday;

    ArrayList<String> eventdates;

    String Uid;

    ArrayList<CalendarDay> decodate;

    private FirebaseFirestore fstore;

    //일정이 추가된 날짜 가져오기 -> 데이터베이스에서 가져오는 방법으로 해야할듯
    public void onCreate(@NonNull @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

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

                        //all 없을  때
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

       /* getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                int addyear = bundle.getInt("addyear");
                int addmonth = bundle.getInt("addmonth");
                int addday = bundle.getInt("addday");
                ayear = addyear;
                amonth = addmonth;
                aday = addday;
            }
        });*/

        // c = CalendarDay.from(ayear,amonth,aday);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_calendar,container,false);

        /*final DocumentReference docRef = fstore.collection("Adates").document("all");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });*/

        addschedule = new AddScheduleFragment();

        MaterialCalendarView materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.home_calendarView);

        ImageButton addButton = (ImageButton) view.findViewById(R.id.okbotton);



        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){


           /*     Intent intent = new Intent(getApplicationContext(), AddScheduleActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                intent.putExtra("cdate", materialCalendarView.getSelectedDate().getDay());*/



                MainActivity mainactivity = (MainActivity) getActivity();
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

        Collection<CalendarDay> eventslist = new ArrayList<>();
        //업데이트 함수로 구현??
        eventslist.add(CalendarDay.today());
        //1월1일은 표시 안됨 ->왜지..
        eventslist.add(CalendarDay.from(2020,01,01));
        materialCalendarView.addDecorator(new DateDecorator(getActivity(), getColor(getActivity(), R.color.black), eventslist));
        //리스트에 있는 날짜 dot로 표시
        //materialCalendarView.addDecorator(new DateDecorator(getActivity(), getColor(getActivity(), R.color.black), decodate));


        return view;
    }
}
