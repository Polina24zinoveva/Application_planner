package com.example.lrevm_32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {

    //     android:checked
    Switch beige_background_button ;
    Switch blue_background_button ;
    Switch green_background_button;
    Switch pink_background_button;
    RelativeLayout relativeLayout;


    private ConstraintLayout settingActivityLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        settingActivityLayout = findViewById(R.id.root_layout);

        beige_background_button = findViewById(R.id.beige_background_button);
        blue_background_button = findViewById(R.id.blue_background_button);
        green_background_button = findViewById(R.id.green_background_button);
        pink_background_button = findViewById(R.id.pink_background_button);



        //установка кнопок
        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferences", this.MODE_PRIVATE);
        blue_background_button.setChecked(sharedPreferences.getBoolean("save_blue_button", false));
        green_background_button.setChecked(sharedPreferences.getBoolean("save_green_button", false));
        beige_background_button.setChecked(sharedPreferences.getBoolean("save_beige_button", false));
        pink_background_button.setChecked(sharedPreferences.getBoolean("save_pink_button", false));


        relativeLayout = findViewById(R.id.relativeLayout);

        //установка нового background
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bg = sharedPref.getInt("background_resource_solid", android.R.color.white); // the second parameter will be fallback if the preference is not found
        relativeLayout.setBackgroundResource(bg);

        /*//установка нового background
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        int bg = sharedPref.getInt("background", android.R.color.white); // the second parameter will be fallback if the preference is not found
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, bg));*/

    }

    public void onBlueClick(View view) {
/*
        settingActivityLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_background));
*/
        relativeLayout.setBackgroundResource(R.drawable.blue_background_solid);


        beige_background_button.setChecked(false);
        green_background_button.setChecked(false);
        pink_background_button.setChecked(false);


        beige_background_button.setClickable(true);
        blue_background_button.setClickable(false);
        pink_background_button.setClickable(true);
        green_background_button.setClickable(true);

        //сохранение кнопок
        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferences",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("save_blue_button", true);
        editor.putBoolean("save_green_button", false);
        editor.putBoolean("save_beige_button", false);
        editor.putBoolean("save_pink_button", false);

        editor.apply();
        //отправка нового фона в другие активити
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref.edit();
        editor2.putInt("background_resource_main", R.drawable.blue_background);
        editor2.putInt("background_resource_circle", R.drawable.blue_background_circle);
        editor2.putInt("background_resource_solid", R.drawable.blue_background_solid);

        editor2.apply();

    }
    public void onGreenClick(View view) {
/*
        settingActivityLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.green_fon));
*/
        relativeLayout.setBackgroundResource(R.drawable.green_background_solid);

        //settingActivityLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.green_background));
        beige_background_button.setChecked(false);
        blue_background_button.setChecked(false);
        pink_background_button.setChecked(false);

        beige_background_button.setClickable(true);
        blue_background_button.setClickable(true);
        green_background_button.setClickable(false);
        pink_background_button.setClickable(true);


        //сохранение кнопок
        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferences",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("save_green_button", true);
        editor.putBoolean("save_blue_button", false);
        editor.putBoolean("save_beige_button", false);
        editor.putBoolean("save_pink_button", false);

        editor.apply();
        //отправка нового фона в другие активити
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref.edit();
        editor2.putInt("background", R.drawable.green_fon);
        editor2.putInt("background_resource_main", R.drawable.green_background);
        editor2.putInt("background_resource_circle", R.drawable.green_background_circle);
        editor2.putInt("background_resource_solid", R.drawable.green_background_solid);

        editor2.apply();
    }
    public void onBeigeClick(View view) {
/*
        settingActivityLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.beige_fon));
*/
        relativeLayout.setBackgroundResource(R.drawable.beige_background_solid);


        //settingActivityLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.beige_background));
        blue_background_button.setChecked(false);
        green_background_button.setChecked(false);
        pink_background_button.setChecked(false);

        beige_background_button.setClickable(false);
        blue_background_button.setClickable(true);
        green_background_button.setClickable(true);
        pink_background_button.setClickable(true);


        //сохранение кнопок
        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferences",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("save_beige_button", true);
        editor.putBoolean("save_blue_button", false);
        editor.putBoolean("save_green_button", false);
        editor.putBoolean("save_pink_button", false);

        editor.apply();
        //отправка нового фона в другие активити
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref.edit();
        editor2.putInt("background", R.drawable.beige_fon);
        editor2.putInt("background_resource_main", R.drawable.beige_background);
        editor2.putInt("background_resource_circle", R.drawable.beige_background_circle);
        editor2.putInt("background_resource_solid", R.drawable.beige_background_solid);
        editor2.apply();
    }

    public void onPinkClick(View view) {
/*
        settingActivityLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_fon));
*/
        relativeLayout.setBackgroundResource(R.drawable.pink_background_solid);

        //settingActivityLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_background));
        blue_background_button.setChecked(false);
        green_background_button.setChecked(false);
        beige_background_button.setChecked(false);

        pink_background_button.setClickable(false);
        beige_background_button.setClickable(true);
        blue_background_button.setClickable(true);
        green_background_button.setClickable(true);

        //сохранение кнопок
        SharedPreferences sharedPreferences = this.getSharedPreferences("my_preferences",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("save_beige_button", false);
        editor.putBoolean("save_blue_button", false);
        editor.putBoolean("save_green_button", false);
        editor.putBoolean("save_pink_button", true);

        editor.apply();
        //отправка нового фона в другие активити
        SharedPreferences sharedPref = this.getSharedPreferences("my_prefs", this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref.edit();
        editor2.putInt("background_resource_main", R.drawable.pink_background);
        editor2.putInt("background_resource_circle", R.drawable.pink_background_circle);
        editor2.putInt("background_resource_solid", R.drawable.pink_background_solid);

        editor2.apply();
    }

    public void onCancelClick(View view) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }
}