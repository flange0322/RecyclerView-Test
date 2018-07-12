package com.example.user.recyclerviewtest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter{

    private ArrayList<String> mdata;
    private LayoutInflater inflater;
    public MainAdapter(Context context, ArrayList<String> mdata){
        inflater = LayoutInflater.from(context);
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.text,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((ItemHolder)holder).textView.setText(mdata.get(position));

        ((ItemHolder)holder).mbtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        TextView textView;
        Button mbtn_delete;
        public ItemHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            mbtn_delete = itemView.findViewById(R.id.button);
        }
    }

    public void AddItem(String task){
        if(task.length() == 0)
            return;

        mdata.add(task);
        notifyItemRangeChanged(0, getItemCount());
    }

    public void DeleteItem(int pos){
        mdata.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(0, getItemCount());
    }
}

