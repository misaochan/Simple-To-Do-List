package com.misao.todolist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.misao.todolist.db.TaskContract;
import com.misao.todolist.db.TaskDBHelper;

public class MainActivity extends AppCompatActivity {

    private TaskDBHelper helper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add a task");
                builder.setMessage("What do you want to do?");

                final EditText inputField = new EditText(this);
                builder.setView(inputField);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = inputField.getText().toString();
                        Log.d("Task entered: ",task);

                        TaskDBHelper helper = new TaskDBHelper(MainActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.clear();
                        values.put(TaskContract.Columns.TASK,task);

                        db.insertWithOnConflict(TaskContract.TABLE, null, values,
                                SQLiteDatabase.CONFLICT_IGNORE);
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
                return true;

            default:
                return false;
        }
    }
}
