package com.example.wemeet;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Arrays;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class GetDB extends Thread {
    private FirebaseFirestore fstore;
    private ArrayList<String> eventdates;
    ArrayList<CalendarDay> decodate;

    public GetDB(ArrayList<CalendarDay> decodate){
        this.decodate = decodate;
    }

    public void run(){

        fstore = FirebaseFirestore.getInstance();
        eventdates = new ArrayList<>();

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

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        Log.d(TAG,"create "+ decodate);
    }
}
