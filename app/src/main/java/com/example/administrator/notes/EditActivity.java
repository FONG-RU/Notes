package com.example.administrator.notes;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    Bundle bundle;
    EditText editDate, editContext;
    int cursor_id, index;
    NoteAdapter noteAdapter;
    Button buttonOK;
    String new_Date, new_Context;
    private int mYear, mMonth, mDay;

    Spinner spinner;
    ArrayAdapter<CharSequence> data;
    ArrayList<ColorItem> colorItems;
    SpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //----------------------------------------------------------------------
        spinner = findViewById(R.id.spinner);
        colorItems = new ArrayList<ColorItem>();
        colorItems.add(new ColorItem("Red","#FFCCCC"));
        colorItems.add(new ColorItem("Green","#99FF99"));
        colorItems.add(new ColorItem("Blue","#CEFDFD"));
        colorItems.add(new ColorItem("Yellow","#EEFFBB"));
        spinnerAdapter = new SpinnerAdapter(this,colorItems);
        spinner.setAdapter(spinnerAdapter);
        //----------------------------------------------------------------------

        editDate = findViewById(R.id.editDate);
        editContext = findViewById(R.id.editContext);
        buttonOK = findViewById(R.id.buttonOK);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_Date = editDate.getText().toString();
                new_Context = editContext.getText().toString();
                try {
                    noteAdapter.updateNote(index, new_Date, new_Context);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


        bundle = this.getIntent().getExtras();
        cursor_id = bundle.getInt("item_id");
        noteAdapter = new NoteAdapter(this);
        Cursor cursor = noteAdapter.queryById(cursor_id);

        index = cursor.getInt(0);
        editDate.setText(cursor.getString(1));
        editContext.setText(cursor.getString(2));

        //--------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(EditActivity.this);
                builder.setTitle("警告")
                        .setMessage("確定要刪除此筆資料? 刪除後無法回復")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            //設定確定按鈕
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Boolean isDeleted = noteAdapter.deleteNote(index);
                                if(isDeleted)
                                    Toast.makeText(EditActivity.this,"已刪除!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            //設定取消按鈕
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        //--------------------------------------------------------------------
        editDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editDate.setInputType(InputType.TYPE_NULL);
                return false;
            }
        });
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //將選定日期設定至edt_birth
                        editDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
    }

}
