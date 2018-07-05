package com.example.administrator.notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public class MainActivity extends AppCompatActivity {

    public ListView listView;
    public NoteAdapter noteAdapter;
    public SimpleCursorAdapter dataSimpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.item_list);
        noteAdapter = new NoteAdapter(this);
        displaylist();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }


    //---------------------------顯示便條---------------------------------------------
    private void displaylist(){
        Cursor cursor = noteAdapter.listNote();
        String[] columns = new String[]{
                NoteAdapter.KEY_DATE,
                NoteAdapter.KEY_CONTEXT,
        };
        int[] view = new int[]{
                R.id.textDate,
                R.id.textContext,
        };
        dataSimpleAdapter = new SimpleCursorAdapter(this,R.layout.item_list, cursor, columns, view, 0);
        listView.setAdapter(dataSimpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor current_cursor = (Cursor) listView.getItemAtPosition(position);
                int item_id = current_cursor.getInt(current_cursor.getColumnIndexOrThrow("_id"));
                Log.i("item_id=", String.valueOf(item_id));
                Intent intent = new Intent(MainActivity.this, EditActivity.class );
                intent.putExtra("item_id",item_id);
                startActivity(intent);
            }
        });
    }
}
