package com.example.wemeet;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static androidx.core.content.ContextCompat.getColor;


public class GroupCalendarFragment extends Fragment {
    TextView groupName;
    View view;
    int syear, smonth, sday;
    ArrayList<String> eventdates;
    String Uid;
    MaterialCalendarView materialCalendarView;
    Collection<CalendarDay> decodate = new ArrayList<CalendarDay>(Arrays.asList(CalendarDay.from(2021,01,01)));;
    private FirebaseFirestore fstore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.group_calendar_,container, false);
        Bundle bundle=getArguments();
        String objectId=bundle.getString("objectId");
        ArrayList<String> memberList=bundle.getStringArrayList("memberList");
        String gn=bundle.getString("groupName");
        groupName=(TextView)view.findViewById(R.id.groupName);
        groupName.setText(gn+"'s calendar");
        Iterator<String> iter=memberList.iterator();
        while(iter.hasNext()){
            Toast.makeText(getActivity(), iter.next(),Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getActivity(), objectId,Toast.LENGTH_SHORT).show();


        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.home_calendarView);
        materialCalendarView.setSelectionColor(Color.BLACK);

        materialCalendarView.setSelectedDate(CalendarDay.today());

        ImageButton addButton = (ImageButton) view.findViewById(R.id.plus);
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

        DocumentReference docRef = fstore.collection("Adates").document("all"+Uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    //all exists
                    if (document.exists()) {
                        Log.d("그룹달력", "DocumentSnapshot data: " + document.getData());
                        ArrayList<String> strdates = (ArrayList<String>) document.getData().get("Eventdates");
                        //string -> CalendarDay
                        DateData st = new DateData(strdates);
                        decodate = st.CalendardateChange();

                        materialCalendarView.addDecorator(new DateDecorator(getActivity(), getColor(getActivity(), R.color.black), decodate));
                    } else {
                        Log.d("그룹달력", "No such document");
                    }
                } else {
                    Log.d("그룹달력", "get failed with ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("그룹달력","error "+ decodate);
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("그룹달력","create "+ decodate);

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                syear = date.getYear();
                smonth = date.getMonth();
                sday = date.getDay();
            }
        });



        return view;










    }

    @Override
    public void onStart() {

        super.onStart();
    }
}
