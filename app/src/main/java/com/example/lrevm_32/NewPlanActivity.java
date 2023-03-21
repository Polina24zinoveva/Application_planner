package com.example.lrevm_32;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class NewPlanActivity extends AppCompatActivity {

    EditText write_new_plan_editText;
    TextView dateTextView;
    TextView timeTextView;
    TextView topTextView;
    TextView colorButtonAdd;
    TextView colorButtonCancel;
    RelativeLayout relativeLayoutUnderButtonCancel;


    int hour;
    int minute;
    DatePickerDialog.OnDateSetListener setListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);

        topTextView = findViewById(R.id.topTextView);
        colorButtonAdd = findViewById(R.id.colorButtonAdd);
        colorButtonCancel = findViewById(R.id.colorButtonCancel);


        //установка цветов
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bg = sharedPref.getInt("background_resource_circle", android.R.color.white); // the second parameter will be fallback if the preference is not found
        topTextView.setBackgroundResource(bg);
        int bgg = sharedPref.getInt("background_resource_main", android.R.color.white); // the second parameter will be fallback if the preference is not found
        colorButtonCancel.setBackgroundResource(bgg);
        int bggg = sharedPref.getInt("background_resource_solid", android.R.color.white); // the second parameter will be fallback if the preference is not found
        colorButtonAdd.setBackgroundResource(bggg);

        relativeLayoutUnderButtonCancel = findViewById(R.id.relativeLayoutUnderButtonCancel);
        int bgggg = sharedPref.getInt("background_resource_solid", android.R.color.white); // the second parameter will be fallback if the preference is not found
        relativeLayoutUnderButtonCancel.setBackgroundResource(bgggg);


        //getWindow().setBackgroundDrawableResource(bg);

        write_new_plan_editText = findViewById(R.id.write_new_plan_editText);

        //выбор даты и времени
        dateTextView = findViewById(R.id.date_textView);
        timeTextView = findViewById(R.id.time_textView);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
//
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewPlanActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                if (month < 10 && dayOfMonth < 10) {

                    String fMonth = "0" + month;
                    String fDay = "0" + dayOfMonth;
                    String date = fDay + "." + fMonth + "." + year;
                    dateTextView.setText(date);
                }
                else if (month < 10)
                {
                    String fMonth = "0" + month;
                    String date = dayOfMonth + "." + fMonth + "." + year;
                    dateTextView.setText(date);
                }
                else if(dayOfMonth < 10)
                {
                    String fDay = "0" + dayOfMonth;
                    String date = fDay + "." + month + "." + year;
                    dateTextView.setText(date);
                }
                else
                {
                    String date = dayOfMonth + "." + month + "." + year;
                    dateTextView.setText(date);
                }
            }
        };
    }

    public void onTimeClick(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if (hour < 10 && minute < 10) {

                    String fHour = "0" + hour;
                    String fMinute = "0" + minute;
                    String time = fHour + ":" + fMinute;
                    timeTextView.setText(time);
                }
                else if (hour < 10)
                {
                    String fHour = "0" + hour;
                    String time = fHour + ":" + minute;
                    timeTextView.setText(time);
                }
                else if(minute < 10)
                {
                    String fMinute = "0" + minute;
                    String time = hour + ":" + fMinute;
                    timeTextView.setText(time);
                }
                else
                {
                    String time = hour + ":" + minute;
                    timeTextView.setText(time);
                }
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    public void write_notes(){
        SharedPreferences sharedPreferencesNewPlan = this.getSharedPreferences("_prefs", this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferencesNewPlan.edit();
        editor2.putString("new_note", String.valueOf(write_new_plan_editText.getText()));
        String date = dateTextView.getText().toString();
        String time = timeTextView.getText().toString();
        editor2.putString("date", date);
        editor2.putString("time", time);
        editor2.apply();
    }

    public void onAddClick(View view) {
        write_notes();
        Intent intent = new Intent(NewPlanActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onCancelClick(View view) {
        Intent intent = new Intent(NewPlanActivity.this, MainActivity.class);
        startActivity(intent);
    }
}