package com.example.wemeet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;
import java.util.Collection;

public class MainActivityTest extends FragmentActivity {
    BottomNavigationView bottomNavigationView;
    // fragment class.java를 가져오는군
    GroupFragment group;
    CalendarFragment calendar;
    SettingFragment setting;
    AddScheduleFragment addSchedule;

    private FirebaseAuth mAuth;
    private String providerId;
    private String uid;
    private String name;
    private String email;
    FirebaseFirestore db;

    public static class DateDecorator implements DayViewDecorator {
        private int color = 0;
        private final HashSet<CalendarDay> dates;
        private ColorDrawable drawable;
        private Context context;

        public DateDecorator(Context context, int color, Collection<CalendarDay> dates) {
            this.context = context;
            this.color = color;
            this.dates = new HashSet<>(dates);
            drawable = new ColorDrawable(color);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(10, color));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bottomNavigationView = findViewById(R.id.navigationBar);
        group = new GroupFragment();
        setting = new SettingFragment();
        calendar = new CalendarFragment();
        addSchedule = new AddScheduleFragment();

        mAuth = FirebaseAuth.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, calendar).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, calendar).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.setting: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, setting).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.group: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, group).commitAllowingStateLoss();
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });
    }

    public String userId() {
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