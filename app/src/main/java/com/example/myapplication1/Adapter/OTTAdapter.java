package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Database.OTTdb;
import com.example.myapplication1.Modals.OTT;
import com.example.myapplication1.R;

import java.util.ArrayList;


public class OTTAdapter extends RecyclerView.Adapter<OTTAdapter.OTTViewHolder> {

    Context context;
    ArrayList<OTT> list;
    OTTdb ottdb;

    public static class OTTViewHolder extends RecyclerView.ViewHolder{
        public TextView name, plan, count, date, groupid;
        public ImageView ottLogo;
        public RadioButton status;
        public ImageButton cancel;
        public OTTViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            plan = itemView.findViewById(R.id.plandetails);
            count = itemView.findViewById(R.id.friendscount);
            date = itemView.findViewById(R.id.membershipDate);
            groupid = itemView.findViewById(R.id.groupId);
            ottLogo = itemView.findViewById(R.id.ottplatform);
            status = itemView.findViewById(R.id.status);
            cancel = itemView.findViewById(R.id.cancel_action);
        }
    }

    @NonNull
    @Override
    public OTTAdapter.OTTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.platformlist, parent, false);
        return new OTTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OTTAdapter.OTTViewHolder holder, int position) {
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.status.isSelected()){
                    holder.status.setSelected(false);
                    holder.status.setChecked(false);
                }
                else{
                    holder.status.setSelected(true);
                    holder.status.setChecked(true);
                }
            }
        });
        Log.d("Platform", "onBindViewHolder: " + list.get(position).getPlatform());
        switch (list.get(position).getPlatform()) {

            case "Netflix":
                holder.ottLogo.setImageResource(R.drawable.ic_netflix_logo_wine);
                break;
            case "Prime":
                holder.ottLogo.setImageResource(R.drawable.ic_prime_video_logo_wine);
                break;
            case "Disney":
                holder.ottLogo.setImageResource(R.drawable.ic_disney);
                break;
            default:
                holder.ottLogo.setImageResource(R.drawable.ic_spotify_logo_wine);
                break;
        }

        holder.plan.setText(String.format("Plan: %s", list.get(position).getPlan()));
        holder.name.setText(String.format("UserName: %s", list.get(position).getUserName()));
        holder.date.setText(String.format("Start Date: %s", list.get(position).getStartDate()));
        holder.groupid.setText(String.format("Group Id: %s", list.get(position).getGroupId()));

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTTdb db = new OTTdb(context);
                db.deleteOTT(list.get(position).getPlatform());
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public OTTAdapter(Context context, ArrayList<OTT> list){
        this.context = context;
        ottdb = new OTTdb(context);
        this.list = ottdb.getOTTList();
        //this.list = list;
    }

    public void insert(){
        list = ottdb.getOTTList();
        notifyDataSetChanged();
    }
}
