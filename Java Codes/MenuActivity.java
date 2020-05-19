package com.example.myfirstfinalapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    String USERNAME;
    TextView username;
    ImageView image;
    LayoutInflater inflater;
    SharedPreferences sharedPreferences;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_about:
                Intent aboutIntent = new Intent(MenuActivity.this, SharedPreferencesActivity.class);
                aboutIntent.putExtra("USERNAME", USERNAME);
                startActivity(aboutIntent);
                break;
            case R.id.nav_mail:
                Intent mailIntent = new Intent(MenuActivity.this, CreateMailActivity.class);
                mailIntent.putExtra("USERNAME", USERNAME);
                startActivity(mailIntent);
                break;
            case R.id.nav_list:
                Intent listIntent = new Intent(MenuActivity.this, ListActivity.class);
                listIntent.putExtra("USERNAME", USERNAME);
                startActivity(listIntent);
                break;
            case R.id.nav_note:
                Intent noteIntent = new Intent(MenuActivity.this, ListNoteActivity.class);
                noteIntent.putExtra("USERNAME", USERNAME);
                startActivity(noteIntent);
                break;
            case R.id.nav_sensor:
                Intent sensorIntent = new Intent(MenuActivity.this, SensorActivity.class);
                sensorIntent.putExtra("USERNAME", USERNAME);
                startActivity(sensorIntent);
                break;
            case R.id.nav_download:
                Intent downloadIntent = new Intent(MenuActivity.this, AsyncTaskActivity.class);
                startActivity(downloadIntent);
                break;
            case R.id.nav_alarm:
                Intent alarmIntent = new Intent(MenuActivity.this, AlarmActivity.class);
                startActivity(alarmIntent);
                break;
            case R.id.nav_location:
                Intent locationIntent = new Intent(MenuActivity.this, LocationActivity.class);
                startActivity(locationIntent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setTitle("Menu");

        Bundle extras = getIntent().getExtras();

        if (extras != null)
            USERNAME = extras.getString("USERNAME");

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean darkSP = sharedPreferences.getBoolean("DarkKey" + "_" + USERNAME, false);

        if (darkSP)
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeader = navigationView.getHeaderView(0);
        username = navHeader.findViewById(R.id.usr_name);
        image = navHeader.findViewById(R.id.usr_img);
        ArrayList<Person> persons = Person.getData();

        int i = 0;
        boolean found = false;
        while (i < persons.size() && ! found) {
            if (persons.get(i).getUsername().equals(USERNAME))
                found = true;
            else
                i++;
        }
        image.setImageResource(persons.get(i).getImageName());
        username.setText(USERNAME);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean darkSP = sharedPreferences.getBoolean("DarkKey" + "_" + USERNAME, false);

        if (darkSP)
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }


}
