package com.example.wemeet;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class GuidelineActivity extends FragmentActivity {
    private static final int NUM_PAGES=3;
    private ViewPager2 mPager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines_);

        mPager=findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(3);
       ScreenSlidePagerAdapter pagerAdapter=new ScreenSlidePagerAdapter(this);
        TabFragment1 fragment1=new TabFragment1();
        pagerAdapter.addItem(fragment1);
        TabFragment2 fragment2=new TabFragment2();
        pagerAdapter.addItem(fragment2);
        TabFragment3 fragment3=new TabFragment3();
        pagerAdapter.addItem(fragment3);
        mPager.setAdapter(pagerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void nextFragment(){
        if(mPager.getCurrentItem()!=NUM_PAGES){
            mPager.setCurrentItem(mPager.getCurrentItem()+1);
        }else{

        }
    }
    public void toLoginActivity(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, 101);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        ArrayList<Fragment> items=new ArrayList<Fragment>();
        public ScreenSlidePagerAdapter(FragmentActivity fm) {
            super(fm);
        }
        public void addItem(Fragment item){
            items.add(item);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return items.get(position);

        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}
