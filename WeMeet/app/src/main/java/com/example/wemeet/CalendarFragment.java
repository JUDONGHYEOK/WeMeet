package com.example.wemeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_main,container,false);

        ImageButton addButton = (ImageButton) view.findViewById(R.id.okbotton);


        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.changeFragment(0);
            }
        });

        return view;
    }
}
