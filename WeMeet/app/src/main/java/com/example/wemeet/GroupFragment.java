package com.example.wemeet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroupFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GroupAdapter adapter;
    ArrayList<GroupData> list;
    String userId;
    FirebaseFirestore db;
    Query qr;
     public GroupFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();
        userId=((MainActivityTest)getActivity()).userId();
        try{
            db = FirebaseFirestore.getInstance();}catch(Exception e){
            Toast.makeText(getActivity(), "연결 오류",Toast.LENGTH_LONG).show();
        }
        qr =db.collection("groups").orderBy("date")
                .whereArrayContains("groupMembers", userId);
        listenToChildFragment();

        /*listenerAdd();*/
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.grouplist_,container,false);
        final Context context=view.getContext();
        mLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.Recyclerview_group);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter=new GroupAdapter(context,list);
        recyclerView.setAdapter(adapter);
        RecyclerDecoration spaceDecoration=new RecyclerDecoration(30);
        recyclerView.addItemDecoration(spaceDecoration);
        /*initDataset();*/
        listenerAdd();
        adapter.setOnItemClickListener(new OnGroupClickListener() {
            @Override
            public void onItemClick(GroupAdapter.ViewHolder holder, View view, int position) {
                GroupData item=adapter.getItem(position);
                Bundle result = new Bundle();
                item.getMembers().add(0,userId);
                result.putString("objectId",item.getObjectId());
                result.putStringArrayList("memberList",item.getMembers());
                result.putString("groupName",item.getGroupName());
                ((MainActivityTest)getActivity()).replaceFragment(new GroupCalendarFragment(),result);
            }
        });
        Toast.makeText(getActivity(), userId,Toast.LENGTH_SHORT).show();
        Button button =(Button)view.findViewById(R.id.Button_addGroup);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                getChildFragmentManager().beginTransaction().replace(R.id.addGroup, new GroupAddFragment()).commitNow();
            }
        });

        return view;
    }
    private void initDataset() {
         Toast.makeText(getContext(),"함수호출",Toast.LENGTH_LONG).show();

                qr.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String GroupName= (String) document.getData().get("groupName");
                                ArrayList<String>members= (ArrayList<String>) document.getData().get("groupMembers");
                                members.remove(userId);
                                String Id=document.getId();
                                list.add(new GroupData(Id,GroupName, members));
                                Log.d("그룹추가", "  "+document.getId() + " => " + document.getData().get("last"));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }


    private void listenerAdd() {
        qr.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "listen:error", e);
                            return;
                        }
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    String GroupName= (String) dc.getDocument().getData().get("groupName");
                                    ArrayList<String>members= (ArrayList<String>) dc.getDocument().getData().get("groupMembers");
                                    String Id=dc.getDocument().getId();
                                    members.remove(userId);
                                    list.add(new GroupData(Id,GroupName, members));
                                    break;
                                case MODIFIED:
                                    break;
                                case REMOVED:
                                    break;
                            }
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
    }

    private void listenToChildFragment() {
        getChildFragmentManager()
                .setFragmentResultListener("addGroup", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                        String result = bundle.getString("GroupName");
                        ArrayList<String> member=bundle.getStringArrayList("Members");
                        submitGroup(result,member);
                        member.remove(userId);
                    }
                });
    }

    public void submitGroup(String groupName, ArrayList<String> member){
        Map<String, Object> group = new HashMap<>();
        Date time = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        String time1=format1.format(time);
        member.add(userId);
        group.put("groupName",groupName);
        group.put("groupMembers",member);
        group.put("date", time1.replaceAll("-","").replaceAll(":",""));
        db.collection("groups")
                .add(group)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });

    }
}
