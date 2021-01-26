package com.example.wemeet;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    BottomNavigationView bottomNavigationView;
    // fragment class.java를 가져오는군
    GroupFragment group;
    CalendarFragment calendar;
    SettingFragment setting;
    private FirebaseAuth mAuth ;
    private String providerId;
    private String uid;
    private String name;
    private String email;
    FirebaseFirestore db;
    AddScheduleFragment addSchedule;

    ArrayList<String> stringdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().setStatusBarColor(Color.BLACK);
        }

        setContentView(R.layout.main_);
        bottomNavigationView = findViewById(R.id.navigationBar);
        group = new GroupFragment();
        setting = new SettingFragment();
        calendar = new CalendarFragment();
        mAuth = FirebaseAuth.getInstance();
        addSchedule = new AddScheduleFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, calendar).commitAllowingStateLoss();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, calendar).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.setting:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, setting).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.group:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, group).commitAllowingStateLoss();
                        return true;
                    }
                    default:return false;
                }
            }
        });
    }
    public void replaceFragment(Fragment fr,Bundle bundle){

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fr).commit();;
        fr.setArguments(bundle);
    }
    public String userId(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                providerId = profile.getProviderId();
                // UID specific to the provider
                uid = profile.getUid();
                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            }
        }
        return email;
    }

    public void FragmentChange(int index){
        if(index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, addSchedule).commit();
        }
        else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, calendar).commit();
        }
    }


}