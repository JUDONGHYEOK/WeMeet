package com.example.wemeet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    Context context;
    ArrayList<GroupData>items=new ArrayList<>();

    public GroupAdapter(Context context, ArrayList<GroupData> data) {
        this.context=context;
        this.items=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.group_fragment,parent,false);
        return new ViewHolder(v);
    }
    public GroupData getItem(int position){return items.get(position);}
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      GroupData data=items.get(position);
      holder.setItem(data);
    }
    public void addItem(GroupData group){
        items.add(group);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<GroupData> items){
        this.items=items;
    }
    public GroupData getData(int position){
        return items.get(position);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView GroupName;
        public TextView GroupPerson;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            GroupName=(TextView)itemView.findViewById(R.id.groupName);
            GroupPerson=(TextView)itemView.findViewById(R.id.groupPerson);
        }

        public void setItem(GroupData group){
            GroupName.setText(group.GroupName);
            int NumberOfPeople=group.getMembers().size();
            if(NumberOfPeople>=2) {
                GroupPerson.setText(group.getMember(0).replaceAll("@gmail.com"," ") + "외 " + (NumberOfPeople - 1) + "명");
            }else{
                GroupPerson.setText(group.getMember(0));
            }

        }
    }
}
