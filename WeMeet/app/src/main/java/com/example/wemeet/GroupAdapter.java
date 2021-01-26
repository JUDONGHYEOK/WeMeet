package com.example.wemeet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    Context context;
    ArrayList<GroupData>items=new ArrayList<>();
    OnGroupClickListener listener;
    OnGroupLongClickListener listen;

    public GroupAdapter(Context context, ArrayList<GroupData> data) {
        this.context=context;
        this.items=data;
    }
    public void setOnItemLongClickListener(OnGroupLongClickListener listen){this.listen=listen;}
    public void setOnItemLongClick(ViewHolder holder,View view,int position){
        if(listen!=null){
            listen.onItemLongClick(holder,view,position);
        }
    }

    public void setOnItemClickListener(OnGroupClickListener listener){
        this.listener=listener;
    }
    public void setOnItemClick(ViewHolder holder,View view,int position){
        if(listener!=null){
            listener.onItemClick(holder,view,position);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.group_fragment_,parent,false);
        return new ViewHolder(v);
    }
    public GroupData getItem(int position){return items.get(position);}
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      GroupData data=items.get(position);
      holder.setItem(data);
      holder.setOnItemClickListener(listener);
      holder.setOnItemLongClickListener(listen);
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
        public ImageView NumberOfMember;
        OnGroupClickListener listener;
        OnGroupLongClickListener listen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            GroupName=(TextView)itemView.findViewById(R.id.groupName);
            GroupPerson=(TextView)itemView.findViewById(R.id.groupPerson);
            NumberOfMember =(ImageView)itemView.findViewById(R.id.number_Member);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this,view,position);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {

                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        listen.onItemLongClick(ViewHolder.this,v,pos);}
                    return true;
                }
            });
        }

        public void setItem(GroupData group){
            GroupName.setText(group.GroupName);
            int NumberOfPeople=group.getMembers().size();
            switch(NumberOfPeople){
                case 1:
                    NumberOfMember.setImageResource(R.drawable.groupmember1);
                    break;
                case 2:
                    NumberOfMember.setImageResource(R.drawable.groupmember2);
                    break;
                case 3:
                    NumberOfMember.setImageResource(R.drawable.groupmember3);
                    break;
                default:
                    NumberOfMember.setImageResource(R.drawable.groupmember4);
                    break;
            }
            if(NumberOfPeople>=2) {
                GroupPerson.setText(group.getMember(0).replaceAll("@gmail.com"," ") + "외 " + (NumberOfPeople - 1) + "명");
            }else{
                GroupPerson.setText(group.getMember(0).replaceAll("@gmail.com",""));
            }

        }

        public void setOnItemClickListener(OnGroupClickListener listener){
            this.listener=listener;
        }
        public void setOnItemLongClickListener(OnGroupLongClickListener listen){
            this.listen=listen;
        }
    }
}
