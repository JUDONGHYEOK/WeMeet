package com.example.wemeet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupMemberListAdapater extends RecyclerView.Adapter<GroupMemberListAdapater.ViewHolder> {
    Context context;
    ArrayList<String>items=new ArrayList<>();

    public GroupMemberListAdapater(Context context, ArrayList<String> data) {
        this.context=context;
        this.items=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_member,parent,false);
        return new ViewHolder(v);
    }
    public String getItem(int position){return items.get(position);}
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      String data=items.get(position);
      holder.setItem(data);
      holder.button.setTag(position);
      holder.button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              int btnPosition=(int)v.getTag();
              items.remove(btnPosition);
              notifyDataSetChanged();
          }
      });
    }
    public  ArrayList<String>getItems(){return items;}
    public void addItem(String group){
        items.add(group);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<String> items){
        this.items=items;
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Member;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Member=(TextView)itemView.findViewById(R.id.member);
            button=(Button)itemView.findViewById(R.id.kickMember);
        }

        public void setItem(String member){
            Member.setText(member);
        }
    }
}
