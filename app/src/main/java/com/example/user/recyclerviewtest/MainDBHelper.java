package com.example.user.recyclerviewtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "TaskBase2.db";
    private static final int DB_VERSION = 1;
    public MainDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TODOLIST_TABLE = "create table " +
                MainContract.MainEntry.TABLE_NAME + "(" +
                MainContract.MainEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MainContract.MainEntry.COLUMN_NAME + " text not null)";

        db.execSQL(SQL_CREATE_TODOLIST_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + MainContract.MainEntry.TABLE_NAME);
        onCreate(db);
    }
}
