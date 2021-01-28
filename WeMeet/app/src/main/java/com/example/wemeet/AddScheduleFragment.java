package com.example.wemeet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    private String datedb,inititle;
    private Timestamp timestampdb;

    private String time;
    int hour,min;

    ArrayList eventdates;

    Map<String, Object> events = new HashMap<>();


    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");


    int syear, smonth, sday;


    public AddScheduleFragment(){}

    public void onCreate(@NonNull @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle=getArguments();
        int kyear = bundle.getInt("keyyear");
        int kmonth = bundle.getInt("keymonth");
        int kday = bundle.getInt("keyday");

        syear= kyear;
        smonth = kmonth;
        sday = kday;
        datedb = null;
        inititle = syear+"년"+smonth+"월"+sday+"의 일정";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_schedule, container, false);

        showdate = (TextView) rootView.findViewById(R.id.setDate);

        showdate.setHint(syear+"년"+smonth+"월"+sday+"일");

        showdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int year = syear;
                int month = smonth;
                int day =sday;

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month-1, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                String monthzero = (month+1) < 10 ? "0"+(month+1) : String.valueOf(month+1);
                String dayzero = day < 10 ? "0"+day : String.valueOf(day);

                Log.d(TAG, "onDateSet: date: " + year + "년" + monthzero + "월" + day + "일");

                String date = year + "년" +(month +1 )+ "월" + day + "일";

                datedb = year+"-"+(monthzero)+"-"+dayzero;

                showdate.setHint(date);

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

        //db update
        fStore = FirebaseFirestore.getInstance();

        eventform = (EditText) rootView.findViewById(R.id.event);
        eventform.setHint("제목");
        addEvent = (Button) rootView.findViewById(R.id.ok_btn);

        DocumentReference dateRef = fStore.collection("Adates").document("all"+email);

        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //제목 없을때
                String ti = eventform.getText().toString();
                if(ti.trim().isEmpty()&&datedb != null){

                    Map<String, String> Eventsmap = new HashMap<>();

                    Eventsmap.put("Title", inititle);
                    Eventsmap.put("Date", datedb);
                    Eventsmap.put("Time", time);
                    Eventsmap.put("User_Email", email);
                    Eventsmap.put("User_id", userid);


                    fStore.collection("Events").add(Eventsmap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText(getContext(),"Error : "+ error, Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Adates의 all 유무로 data 생성 및 update
                    DocumentReference docRef = fStore.collection("Adates").document("all"+email);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                //all 이 있을 때
                                if (document.exists()) {
                                    dateRef.update("Eventdates", FieldValue.arrayUnion(datedb));
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());


                                 //all 없을  때
                                } else {
                                    Map<String, Object> Dmap = new HashMap<>();
                                    Dmap.put("Eventdates", Arrays.asList(datedb));
                                    Dmap.put("DUser_Email", email);

                                    fStore.collection("Adates").document("all"+email)
                                            .set(Dmap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error writing document", e);
                                                }
                                            });
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                    //확인시 홈화면으로
                    MainActivity mainactivity = (MainActivity) getActivity();
                    mainactivity.FragmentChange(1);

                }
                //제목 있을 때
                else if(!ti.trim().isEmpty()&&datedb != null){
                    String title = eventform.getText().toString();
                    Map<String, String> Eventsmap = new HashMap<>();

                    Eventsmap.put("Title", title);
                    Eventsmap.put("Date", datedb);
                    Eventsmap.put("Time", time);
                    Eventsmap.put("User_Email", email);
                    Eventsmap.put("User_id", userid);


                    fStore.collection("Events").add(Eventsmap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText(getContext(),"Error : "+ error, Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Adates의 all 유무로 data 생성 및 update
                    DocumentReference docRef = fStore.collection("Adates").document("all"+email);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                //all 이 있을 때
                                if (document.exists()) {
                                    dateRef.update("Eventdates", FieldValue.arrayUnion(datedb));
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());


                                    //all 없을  때
                                } else {
                                    Map<String, Object> Dmap = new HashMap<>();
                                    Dmap.put("Eventdates", Arrays.asList(datedb));
                                    Dmap.put("DUser_Email", email);

                                    fStore.collection("Adates").document("all"+email)
                                            .set(Dmap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error writing document", e);
                                                }
                                            });
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                    //확인시 홈화면으로
                    MainActivity mainactivity = (MainActivity) getActivity();
                    mainactivity.FragmentChange(1);

                }
                //날짜 없을 시 작동
                else if(datedb == null){
                    Toast.makeText(getActivity(),"날짜를 선택하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //취소시 홈화면으로
        backthome = (Button) rootView.findViewById(R.id.cancle_btn);
        backthome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vi){
                MainActivity mainactivity = (MainActivity) getActivity();
                mainactivity.FragmentChange(1);
            }
        });

    return rootView;
    }
}