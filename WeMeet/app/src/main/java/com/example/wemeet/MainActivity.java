package com.example.wemeet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    FragmentManager fm;
    FragmentTransaction tran;
    GroupFragment gr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintest);
        gr=new GroupFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frgmentA,gr).commit();

    }
}