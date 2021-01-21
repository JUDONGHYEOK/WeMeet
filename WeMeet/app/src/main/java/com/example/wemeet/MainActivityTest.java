package com.example.wemeet;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class MainActivityTest extends FragmentActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bottomNavigationView = findViewById(R.id.navigationBar);
        group = new GroupFragment();
        setting = new SettingFragment();
        calendar = new CalendarFragment();
        mAuth = FirebaseAuth.getInstance();
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


}