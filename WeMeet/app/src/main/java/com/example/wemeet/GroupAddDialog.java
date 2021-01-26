package com.example.wemeet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Iterator;

public class GroupAddDialog extends BottomSheetDialogFragment {
    Button cancel,group;
    Button add;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GroupMemberListAdapater adapter;
    ArrayList<String> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }
    private void initDataset() {
        list=new ArrayList<>();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.group_add_,container,false);
        final Context context=view.getContext();
        mLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.memberList);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter=new GroupMemberListAdapater(context,list);
        recyclerView.setAdapter(adapter);

        group=(Button)view.findViewById(R.id.submit_group);
        cancel=(Button)view.findViewById(R.id.cancel_add);
        add=(Button)view.findViewById(R.id.addMember);
        EditText groupName=(EditText)view.findViewById(R.id.groupName);
        EditText member=(EditText)view.findViewById(R.id.memberName);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String mb= member.getText().toString();
                adapter.addItem(mb+"@gmail.com");
                member.setText("");

            }
        });
        group.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                String gn=groupName.getText().toString();
                if(!gn.trim().isEmpty()&&!list.isEmpty()){
                    ArrayList<String> members=new ArrayList<String>();
                    members=adapter.getItems();
                    Log.d("101",gn);
                    /*Toast.makeText(view.getContext(),gn,Toast.LENGTH_SHORT).show();*/
                    Iterator<String> iter=members.iterator();
                    Bundle result = new Bundle();
                    result.putStringArrayList("Members",members);
                    result.putString("GroupName",gn);
                    getParentFragmentManager().setFragmentResult("addGroup", result);
               /* while(iter.hasNext()){
                    Toast.makeText(view.getContext(),iter.next(),Toast.LENGTH_SHORT).show();
                }*/
                    view.setVisibility(View.INVISIBLE);
                    dismiss();
                }else if(gn.trim().isEmpty()){
                    Toast.makeText(getActivity(),"그룹 이름을 입력하세요",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"그룹 원을 추가하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
