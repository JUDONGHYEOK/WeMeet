package com.example.wemeet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

public class GroupFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GroupAdapter adapter;
     ArrayList<GroupData> list;
    String userId;
     public GroupFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();
        initDataset();
        getChildFragmentManager()
                .setFragmentResultListener("addGroup", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                        String result = bundle.getString("GroupName");
                        ArrayList<String> member=bundle.getStringArrayList("Members");
                        Toast.makeText(getActivity(),result, Toast.LENGTH_LONG).show();
                        Iterator<String> iter=member.iterator();
                        while(iter.hasNext()){
                            Toast.makeText(getActivity(),iter.next(),Toast.LENGTH_LONG).show();
                        }
                        list.add(new GroupData(result,member));
                    }
                });
    }

    private void initDataset() {

        userId=((MainActivityTest)getActivity()).userId();
        ArrayList<String> members=new ArrayList<>();
        members.add("Judonghyeok@gmail.com");
        members.add("Wemeet@gmail.com");
        members.add("judonghyeok@google.com");
        list.add(new GroupData("group1",members));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.grouplist1,container,false);
        final Context context=view.getContext();
        mLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.Recyclerview_group);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter=new GroupAdapter(context,list);
        recyclerView.setAdapter(adapter);
        Toast.makeText(getActivity(), userId,Toast.LENGTH_SHORT).show();
        Button button =(Button)view.findViewById(R.id.Button_addGroup);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),"버튼눌림",Toast.LENGTH_LONG).show();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                getChildFragmentManager().beginTransaction().replace(R.id.addGroup, new GroupAddFragment()).commitNow();
            }
        });

        return view;
    }
    public void deleteFragment(){

    }
}
