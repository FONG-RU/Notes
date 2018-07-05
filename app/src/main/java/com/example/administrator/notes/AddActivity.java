package com.example.administrator.notes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    Button buttonOK;
    EditText editDate, editContext;
    String new_Date, new_Context, new_Color;
    private NoteAdapter noteAdapter;
    Bundle bundle;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        noteAdapter = new NoteAdapter(this);
        bundle = this.getIntent().getExtras();

        editDate = findViewById(R.id.editDate);
        editContext = findViewById(R.id.editContext);

        //--------------------------------------------------------------------
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final String[] color = {"blue", "yellow", "red", "white", "green"};
        ArrayAdapter<String> colorList = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_spinner_dropdown_item, color);
        spinner.setAdapter(colorList);

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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //將選定日期設定至edt_birth
                        editDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });


        //----------------------------------------------------------------------
        buttonOK = findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_Date = editDate.getText().toString();
                new_Context = editContext.getText().toString();

                try {
                    noteAdapter.createNote(new_Date, new_Context);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
