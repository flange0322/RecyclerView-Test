package com.example.user.recyclerviewtest;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    static SQLiteDatabase mDatabase;
    static MainAdapter mainAdapter;
    ArrayList<String>list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        MainDBHelper dbHelper = new MainDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this,list,getAllItems());
        recyclerView.setAdapter(mainAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
                case R.id.item_add:
                    final EditText editTextTask = new EditText(this);
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("Add New Task")
                            .setMessage("What do you want to do next?")
                            .setView(editTextTask)
                            .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String task = String.valueOf(editTextTask.getText());
                                    AddItem(task);
                                }
                            })
                            .setNegativeButton("Cancel",null)
                            .create();
                    dialog.show();
                    return true;

                case R.id.item_choose:
                    Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show();
                    return true;

                default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void AddItem(String task){
        if(task.length() == 0)
            return;

        list.add(task);
        ContentValues cv = new ContentValues();
        cv.put(MainContract.MainEntry.COLUMN_NAME,task);
        mDatabase.insert(MainContract.MainEntry.TABLE_NAME,null,cv);
        mainAdapter.notifyItemRangeChanged(0, list.size());
        mainAdapter.swapCursor(getAllItems());
    }

    public static void DeleteItem(long pos,ArrayList<String> list){
        list.remove(pos);
        mDatabase.delete(MainContract.MainEntry.TABLE_NAME,MainContract.MainEntry._ID + "=" + pos,null);
        mainAdapter.swapCursor(getAllItems());
    }

    private static Cursor getAllItems(){
        return mDatabase.query(MainContract.MainEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
