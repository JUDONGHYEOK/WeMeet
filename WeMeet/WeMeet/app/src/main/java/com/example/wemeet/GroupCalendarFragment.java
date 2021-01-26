package com.example.wemeet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Iterator;


public class GroupCalendarFragment extends Fragment {
    TextView groupName;
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.group_calendar_,container, false);
        Bundle bundle=getArguments();
        String objectId=bundle.getString("objectId");
        ArrayList<String> memberList=bundle.getStringArrayList("memberList");
        String gn=bundle.getString("groupName");
        groupName=(TextView)view.findViewById(R.id.groupName);
        groupName.setText(gn+"'s Calendar");
        Iterator<String> iter=memberList.iterator();
        while(iter.hasNext()){
            Toast.makeText(getActivity(), iter.next(),Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getActivity(), objectId,Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
    }
}
