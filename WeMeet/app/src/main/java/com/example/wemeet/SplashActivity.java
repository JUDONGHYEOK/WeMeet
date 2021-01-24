package com.example.wemeet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    Animation anim;
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_);
    anim= AnimationUtils.loadAnimation(this,R.anim.anim_splash);
    ImageView imageView=(ImageView)findViewById(R.id.imageView2);

    imageView.startAnimation(anim);
    Handler hd = new Handler();
    hd.postDelayed(new SplashHandler(), 3000); // 3000ms = 3초

}

    private class SplashHandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), GuidelineActivity.class));
            SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

}
