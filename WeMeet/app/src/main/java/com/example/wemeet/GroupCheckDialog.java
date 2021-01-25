package com.example.wemeet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class GroupCheckDialog extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GroupMemberListAdapater adapter;
    ArrayList<String> list;
    String objectId;
    Button cancel;
    Button check;

    public GroupCheckDialog() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.group_check_dialog,container,false);
            Bundle bundle=getArguments();
            objectId=bundle.getString("objectId");
            list=bundle.getStringArrayList("memberList");
            String gn=bundle.getString("groupName");
            EditText editText=(EditText)view.findViewById(R.id.groupNameForCheck);
            editText.setText(gn);
            LinearLayout layout=view.findViewById(R.id.memberNameForCheck);
            layout.setVisibility(View.INVISIBLE);
            final Context context=view.getContext();
            mLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
            recyclerView=(RecyclerView)view.findViewById(R.id.memberListForCheck);
            recyclerView.setLayoutManager(mLayoutManager);
            adapter=new GroupMemberListAdapater(context,list);
            recyclerView.setAdapter(adapter);
            check=(Button)view.findViewById(R.id.submit_groupForCheck);
            cancel=(Button)view.findViewById(R.id.cancelForCheck);
            cancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return view;
     }
}
