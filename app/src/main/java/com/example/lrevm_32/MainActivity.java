package com.example.lrevm_32;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    public Map<String, String> notes = new TreeMap<String, String>();

    TextView today_plans_textView;
    TextView tomorrow_plans_textView;
    TextView textViewUnderTodayPlans;
    TextView textViewUnderTomorrowPlans;

    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm" );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewUnderTodayPlans = findViewById(R.id.textViewUnderTodayPlans);
        textViewUnderTomorrowPlans = findViewById(R.id.textViewUnderTomorrowPlans);


        /*//установка нового background
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bg = sharedPref.getInt("background_resource_solid", android.R.color.white); // the second parameter will be fallback if the preference is not found
        relativeLayout.setBackgroundResource(bg);*/

        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bg = sharedPref.getInt("background_resource_solid", android.R.color.white); // the second parameter will be fallback if the preference is not found
        int bgg = sharedPref.getInt("background_resource_main", android.R.color.white); // the second parameter will be fallback if the preference is not found
        //getWindow().setBackgroundDrawableResource(bg);
        today_plans_textView = findViewById(R.id.today_plans_textView);
        tomorrow_plans_textView = findViewById(R.id.tomorrow_plans_textView);
        today_plans_textView.setBackgroundResource(bgg);
/*
        today_plans_textView.setAlpha((float) 0.5);

*/
        tomorrow_plans_textView.setBackgroundResource(bgg);
/*
        tomorrow_plans_textView.setAlpha((float) 0.5);
*/
        textViewUnderTodayPlans.setBackgroundResource(bg);
        textViewUnderTomorrowPlans.setBackgroundResource(bg);




        //установка notes
        //convert to string using gson
        Gson gson = new Gson();

        //save in shared prefs
        SharedPreferences prefs = getSharedPreferences("test", MODE_PRIVATE);
        //get from shared prefs
        String storedHashMapString = prefs.getString("hashString", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<TreeMap<String, String>>(){}.getType();
        notes = gson.fromJson(storedHashMapString, type);


        getFromButtonOnRecycleView();


        //remove_notes();
        fill_in_notes();

        output_today_notes();
        output_tomorrow_notes();
    }

    public void remove_notes(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = dateFormat.format(date);
        List<String> keys = new ArrayList<String>(notes.keySet());
        for(int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (key.split(" ")[0].split("\\.")[2].compareTo(today.split("\\.")[2]) < 0)
            {
                notes.remove(key);
            }
            else if ((key.split(" ")[0].split("\\.")[1].compareTo(today.split("\\.")[1]) < 0) && (key.split(" ")[0].split("\\.")[2].compareTo(today.split("\\.")[2]) >= 0))
            {
                notes.remove(key);
            }
            else if ((key.split(" ")[0].split("\\.")[0].compareTo(today.split("\\.")[0]) < 0) &&(key.split(" ")[0].split("\\.")[1].compareTo(today.split("\\.")[1]) >= 0) && (key.split(" ")[0].split("\\.")[2].compareTo(today.split("\\.")[2]) >= 0))
            {
                notes.remove(key);
            }
        }
    }

    public void fill_in_notes(){
        SharedPreferences sharedPreferencesNewPlan = this.getSharedPreferences("_prefs", this.MODE_PRIVATE);
        String new_note = sharedPreferencesNewPlan.getString("new_note", ""); // the second parameter will be fallback if the preference is not found
        String date_new_note = sharedPreferencesNewPlan.getString("date", ""); // the second parameter will be fallback if the preference is not found
        String time_new_note = sharedPreferencesNewPlan.getString("time", ""); // the second parameter will be fallback if the preference is not found
        if (date_new_note != "")
        {
            String string_date = date_new_note + " " + time_new_note;
            notes.put(string_date, new_note);

        }
        //notes.put("24.11.2022 20.00", "Happy Day!");
        //notes.remove("1.12.2022 21:55");

        SharedPreferences.Editor editor2 = sharedPreferencesNewPlan.edit();
        editor2.putString("date", "");
        editor2.putString("time", "");
        editor2.apply();



        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(notes);

        //save in shared prefs
        SharedPreferences prefs = getSharedPreferences("test", MODE_PRIVATE);
        prefs.edit().putString("hashString", hashMapString).apply();

        //get from shared prefs
        String storedHashMapString = prefs.getString("hashString", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<TreeMap<String, String>>(){}.getType();
        notes = gson.fromJson(storedHashMapString, type);
    }

    public void output_today_notes(){
        //Date today = new Date(System.currentTimeMillis());
        Boolean today_have_plans = false;
        String temp = "";
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = dateFormat.format(date);

        for (Map.Entry<String, String> entry: notes.entrySet())
            if (entry.getKey().split(" ")[0].equals(today))
            {
                temp += "                  " + entry.getKey().split(" ")[1] + "   " + entry.getValue() + "\n";
                today_have_plans = true;
            }
        today_plans_textView.setMovementMethod(new ScrollingMovementMethod());
        today_plans_textView.setText(temp);
        today_plans_textView.setGravity(Gravity.VERTICAL_GRAVITY_MASK);
        if (today_have_plans == false)
        {
            today_plans_textView.setText(R.string.not_plans_today);
            today_plans_textView.setGravity(Gravity.CENTER);
        }
    }

    public void output_tomorrow_notes(){
        Boolean tomorrow_have_plans = false;
        String temp = "";
        Date date = new Date();
        Date tomorrowDate = new Date(date.getTime() + (1000 * 60 * 60 * 24));
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String tomorrow = dateFormat.format(tomorrowDate);

        for (Map.Entry<String, String> entry: notes.entrySet())
            if (entry.getKey().split(" ")[0].equals(tomorrow))
            {
                temp += "                  " + entry.getKey().split(" ")[1] + "   " + entry.getValue() + "\n";
                tomorrow_have_plans = true;
            }
        tomorrow_plans_textView.setText(temp);
        today_plans_textView.setMovementMethod(new ScrollingMovementMethod());
        tomorrow_plans_textView.setGravity(Gravity.VERTICAL_GRAVITY_MASK);
        if (tomorrow_have_plans == false)
        {
            tomorrow_plans_textView.setText(R.string.not_plans_tomorrow);
            tomorrow_plans_textView.setGravity(Gravity.CENTER);
        }
    }

    public void onCalendarClick(View view) {

        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferenc",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("day", "");
        editor.apply();
        Intent intent = new Intent(MainActivity.this, MyCalendarActivity.class);
        startActivity(intent);
    }
    public void onNewPlanClick(View view) {
        Intent intent = new Intent(MainActivity.this, NewPlanActivity.class);
        startActivity(intent);
    }
    public void onSettingClick(View view) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void onWeekCalendarClickToday(View view)
    {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = dateFormat.format(date);


        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferenc",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("day", today);
        editor.apply();


        Intent intent = new Intent(MainActivity.this, WeekCalendarActivity2.class);
        startActivity(intent);
    }

    public void onWeekCalendarClickTomorrow(View view)
    {
        Date date = new Date();
        Date tomorrowDate = new Date(date.getTime() + (1000 * 60 * 60 * 24));
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String tomorrow = dateFormat.format(tomorrowDate);


        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferenc",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("day", tomorrow);
        editor.apply();


        Intent intent = new Intent(MainActivity.this, WeekCalendarActivity2.class);
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