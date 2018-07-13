package com.example.user.recyclerviewtest;

import android.content.ContentValues;
import android.content.Context;;
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
    private Cursor mCursor;
    public MainAdapter(Context context, ArrayList<String> mdata,Cursor cursor){
        inflater = LayoutInflater.from(context);
        this.mdata = mdata;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.text,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(!mCursor.moveToPosition(position)){
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(MainContract.MainEntry.COLUMN_NAME));
        final long id = mCursor.getLong(mCursor.getColumnIndex(MainContract.MainEntry._ID));

        ((ItemHolder)holder).textView.setText(name);
        ((ItemHolder)holder).itemView.setTag(id);

        ((ItemHolder)holder).mbtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.DeleteItem(id,mdata);
                notifyItemRemoved((int)id);
                notifyItemRangeChanged(0, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
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

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }
}

