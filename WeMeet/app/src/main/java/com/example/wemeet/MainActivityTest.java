package com.example.wemeet;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityTest extends FragmentActivity {
    BottomNavigationView bottomNavigationView;
    // fragment class.java를 가져오는군
    GroupFragment group;
    CalendarFragment calendar;
    SettingFragment setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */
        setContentView(R.layout.main);
        bottomNavigationView = findViewById(R.id.navigationBar);


        group = new GroupFragment();
        setting = new SettingFragment();
        calendar = new CalendarFragment();

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
}