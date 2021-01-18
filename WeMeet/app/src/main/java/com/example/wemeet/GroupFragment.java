package com.example.wemeet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MainViewAdapter adapter;
     ArrayList<GroupData> list;

     public GroupFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }

    private void initDataset() {
        list=new ArrayList<>();
        list.add(new GroupData("group1","그룹원들",3));
        list.add(new GroupData("group2","그룹원들",3));
        list.add(new GroupData("group3","그룹원들",3));
        list.add(new GroupData("group4","그룹원들",3));
        list.add(new GroupData("group5","그룹원들",3));
        list.add(new GroupData("group6","그룹원들",3));
        list.add(new GroupData("group7","그룹원들",3));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.grouplist,container,false);
        final Context context=view.getContext();
        mLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.Recyclerview_group);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter=new MainViewAdapter(context,list);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
