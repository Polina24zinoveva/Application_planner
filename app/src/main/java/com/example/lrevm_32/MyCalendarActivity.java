package com.example.lrevm_32;

import static com.example.lrevm_32.CalendarUtils.daysInMonthArray;
import static com.example.lrevm_32.CalendarUtils.monthYearFromDate;
import static com.example.lrevm_32.CalendarUtils.selectedDate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyCalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LinearLayout topLayout;
    private RecyclerView recyclerView ;



    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);

        /*relativeLayout = findViewById(R.id.relativeLayout);

        //установка нового background
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bgg = sharedPref.getInt("background_resource_solid", android.R.color.white); // the second parameter will be fallback if the preference is not found
        relativeLayout.setBackgroundResource(bgg);*/

        topLayout = findViewById(R.id.topLayout);
        //установка нового background
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bg = sharedPref.getInt("background_resource_circle", android.R.color.white); // the second parameter will be fallback if the preference is not found
        //getWindow().setBackgroundDrawableResource(bg);
        topLayout.setBackgroundResource(bg);

        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();

        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferenc",this.MODE_PRIVATE);
        String dateString = sharedPreferences.getString("day", ""); // the second parameter will be fallback if the preference is not found
        if (dateString != "")
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = null;
            try {
                date = formatter.parse(dateString);
                setMonthViewWithDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else
        {
            setMonthView();
        }


    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTextView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate).toUpperCase(Locale.ROOT));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthViewWithDate(Date date)
    {
        //из Date в LocalDate
        selectedDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate).toUpperCase(Locale.ROOT));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate localDate)
    {
        if(localDate != null)
        {

            CalendarUtils.selectedDate = localDate;
            setMonthView();

            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String today = dateFormat.format(date);


            SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferenc",this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("day", today);
            editor.apply();

            Intent intent = new Intent(MyCalendarActivity.this, WeekCalendarActivity2.class);
            startActivity(intent);
        }
    }
    public void toMainActivity(View view)
    {
        Intent intent = new Intent(MyCalendarActivity.this, MainActivity.class);
        startActivity(intent);
    }


}