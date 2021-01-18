package com.example.wemeet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.splash);

    Handler hd = new Handler();
    hd.postDelayed(new SplashHandler(), 3000); // 3000ms = 3초

}

    private class SplashHandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), GuidelineActivity1.class));
            SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

}
