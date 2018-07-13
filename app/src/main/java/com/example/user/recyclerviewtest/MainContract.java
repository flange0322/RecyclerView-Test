package com.example.user.recyclerviewtest;

import android.provider.BaseColumns;

public class MainContract {

    private MainContract() {};
    public static final class MainEntry implements BaseColumns{
        public static final String TABLE_NAME = "TaskTable";
        public static final String COLUMN_NAME = "task";
    }
}
