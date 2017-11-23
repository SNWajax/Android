package com.example.ajays.serachview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ajays on 2/25/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<Users> arrayList = new ArrayList<>();
    RecyclerAdapter(ArrayList<Users> arrayList){

        this.arrayList = arrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.profs.setImageResource(arrayList.get(position).getName_id());
        holder.names.setText(arrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView profs;
        TextView names;

        public MyViewHolder(View itemView) {
            super(itemView);

            profs = (ImageView) itemView.findViewById(R.id.ajax);
            names = (TextView) itemView.findViewById(R.id.name);
        }
    }
    public void setFilter(ArrayList<Users> newList){
        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }
}
