package com.example.wemeet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddScheduleFragment extends Fragment {
    private static final String TAG = "addevent_user";

    private EditText eventform;
    private Button addEvent;
    private Button backthome;
    private FirebaseFirestore fStore;


    private TextView showdate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private TextView Timer1;

    private String datedb;

    private String time;
    int hour,min;

    Map<String, Object> events = new HashMap<>();


    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");


    int syear, smonth, sday;
    int ayear, amonth, aday;

    public AddScheduleFragment(){}

    public void onCreate(@NonNull @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // 달력에서 선택된 날짜 가져오기 위함 -> 탭을 바꿔야 적용되는 문제 발생
                int kyear = bundle.getInt("keyyear");
                int kmonth = bundle.getInt("keymonth");
                int kday = bundle.getInt("keyday");

                syear= kyear;
                smonth = kmonth;
                sday = kday;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_schedule, container, false);

        showdate = (TextView) rootView.findViewById(R.id.setDate);

        showdate.setHint(syear+"년"+smonth+"월"+sday+"일");

      //  Toast.makeText(getContext(),"?? "+caldate,Toast.LENGTH_SHORT).show();

        showdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR)-1;
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
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

                //달력으로 보낼 데이터
                ayear = year;
                amonth = month;
                aday = day;
            }
        };

        Timer1 = rootView.findViewById(R.id.setTime);

        Timer1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
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


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }

        String userid = user.toString();
        String email = user.getEmail();

        fStore = FirebaseFirestore.getInstance();

        eventform = (EditText) rootView.findViewById(R.id.event);
        addEvent = (Button) rootView.findViewById(R.id.ok_btn);


        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String title = eventform.getText().toString();
                Map<String, String> Eventsmap = new HashMap<>();

                Eventsmap.put("Title", title);
                Eventsmap.put("Date", datedb);
                Eventsmap.put("Time", time);
                Eventsmap.put("User_Email", email);
                Eventsmap.put("User_id", userid);

                //일정 추가한 날짜를 달력 프레그먼트로 보내기
              /*  Bundle adddates = new Bundle();
                adddates.putInt("addyear", ayear);
                adddates.putInt("addmonth", amonth);
                adddates.putInt("addday", aday);
                getParentFragmentManager().setFragmentResult("requestKey", adddates);*/

                fStore.collection("Events").add(Eventsmap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(),"Data upload :)",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(getContext(),"Error : "+ error, Toast.LENGTH_SHORT).show();
                    }
                });


                //홈화면으로
                MainActivityTest mainactivity = (MainActivityTest) getActivity();
                mainactivity.FragmentChange(1);
            }
        });

        backthome = (Button) rootView.findViewById(R.id.cancle_btn);
        backthome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vi){
                MainActivityTest mainactivity = (MainActivityTest) getActivity();
                mainactivity.FragmentChange(1);
            }
        });

    return rootView;
    }
}