package com.example.lrevm_32;

import static com.example.lrevm_32.CalendarUtils.daysInWeekArray;
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
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class WeekCalendarActivity2 extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private RecyclerView list;
    private View top;
    private View topLayout;
    private TextView noHavePlanTextView;


    ArrayList<States> states = new ArrayList<States>();
    String[] selectedNotes;
    public Map<String, String> notes = new TreeMap<String, String>();


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_calendar2);


        //list_item = findViewById(R.id.list_item)
        topLayout = findViewById(R.id.topLayout);
        top = findViewById(R.id.top);

        //установка нового background
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bg = sharedPref.getInt("background_resource_circle", android.R.color.white); // the second parameter will be fallback if the preference is not found
        //getWindow().setBackgroundDrawableResource(bg);
        topLayout.setBackgroundResource(bg);
        //top.setBackgroundResource(bg);


        //установка notes
        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(notes);
        SharedPreferences prefs = getSharedPreferences("test", MODE_PRIVATE);
        //get from shared prefs
        String storedHashMapString = prefs.getString("hashString", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<TreeMap<String, String>>(){}.getType();
        notes = gson.fromJson(storedHashMapString, type);


        initWidgets();


        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferenc",this.MODE_PRIVATE);
        String dateString = sharedPreferences.getString("day", ""); // the second parameter will be fallback if the preference is not found
        if (dateString != "")
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = null;
            try {
                date = formatter.parse(dateString);
                setWeekViewWithDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else
        {
            setWeekView();
        }


        setText();


    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        list = findViewById(R.id.list);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView()
    {
        //Date date = new Date();
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate).toUpperCase(Locale.ROOT));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        setInitialData(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekViewWithDate(Date date)
    {
        //из Date в LocalDate
        selectedDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate).toUpperCase(Locale.ROOT));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        setInitialData(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        getFromButtonOnRecycleView();


        CalendarUtils.selectedDate = date;
        setWeekView();

        setText();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
        setText();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
        setText();
    }

    private void setText() {
        // начальная инициализация списка
        //setInitialData();
        RecyclerView recyclerView = findViewById(R.id.list);
        // создаем адаптер
        StateAdapter adapter = new StateAdapter(this, states);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setInitialData(Date date)
    {
        states.clear();
        //Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = dateFormat.format(date);

        for (Map.Entry<String, String> entry: notes.entrySet())
            if (entry.getKey().split(" ")[0].equals(today))
            {
                states.add(new States (entry.getKey().split(" ")[1], entry.getValue(), entry.getKey()));
            }
        if (states.size() == 0)
        {
            states.add(new States ("", "На этот день нет планов", ""));
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onMonthClick(View view)
    {
        Date dateDate = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(dateDate);


        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferenc",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("day", date);
        editor.apply();

        getFromButtonOnRecycleView();

        Intent intent = new Intent(WeekCalendarActivity2.this, MyCalendarActivity.class);
        startActivity(intent);
    }

    public void toMainActivity(View view)
    {
        getFromButtonOnRecycleView();
        Intent intent = new Intent(WeekCalendarActivity2.this, MainActivity.class);
        startActivity(intent);
    }

    private void getFromButtonOnRecycleView() {
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        String statesRemoveIndex = sharedPref.getString("statesRemoveIndex", ""); // the second parameter will be fallback if the preference is not found
        String EditNoteKey = sharedPref.getString("EditNoteKey", ""); // the second parameter will be fallback if the preference is not found
        String EditNoteText = sharedPref.getString("EditNoteText", ""); // the second parameter will be fallback if the preference is not found

        if (statesRemoveIndex != "")
        {
            notes.remove(statesRemoveIndex);

            //convert to string using gson
            Gson gson = new Gson();
            String hashMapString = gson.toJson(notes);

            //save in shared prefs
            SharedPreferences prefs = getSharedPreferences("test", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("hashString", gson.toJson(notes));
            editor.apply();


        }
        if (EditNoteKey != "")
        {
            notes.put(EditNoteKey, EditNoteText);

            //convert to string using gson
            Gson gson = new Gson();
            String hashMapString = gson.toJson(notes);

            //save in shared prefs
            SharedPreferences prefs = getSharedPreferences("test", MODE_PRIVATE);
            prefs.edit().putString("hashString", hashMapString).apply();


        }
    }


}