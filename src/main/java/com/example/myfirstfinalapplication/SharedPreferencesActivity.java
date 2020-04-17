package com.example.myfirstfinalapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SharedPreferencesActivity extends AppCompatActivity {
    EditText age, weight, height, username;
    Button button;
    SharedPreferences sharedPreferences;
    Spinner language, gender;
    Switch dark;

    public static final String MyPrefences = "MyPrefs";
    public static final String Age = "AgeKey";
    public static final String Weight = "WeightKey";
    public static final String Height = "HeightKey";
    public static final String Username = "UsernameKey";
    public static final String DarkMode = "DarkKey";
    public static final String Language = "LanguageKey";
    public static final String Gender = "GenderKey";

    String ageSP, weightSP, heightSP, usernameSP, USERNAME;
    int languageSP, genderSP;
    boolean darkSP;
    String darkMode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        setTitle("User Settings");

        USERNAME = getIntent().getStringExtra("USERNAME");
        username = (EditText) findViewById(R.id.userName);
        age = (EditText) findViewById(R.id.age);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        button = (Button) findViewById(R.id.save);
        language = (Spinner) findViewById(R.id.language);
        gender = (Spinner) findViewById(R.id.gender);
        dark = (Switch) findViewById(R.id.darkMode);

        ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(SharedPreferencesActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.langs));
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(langAdapter);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(SharedPreferencesActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gender));
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);

        sharedPreferences = getSharedPreferences(MyPrefences, Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = age.getText().toString();
                String w = weight.getText().toString();
                String h = height.getText().toString();
                // String u = username.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                //editor.putString(Username + "_" + USERNAME, USERNAME);
                editor.putString(Age + "_" + USERNAME, a);
                editor.putString(Weight + "_" + USERNAME, w);
                editor.putString(Height + "_" + USERNAME, h);
                editor.apply();
            }
        });
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = language.getSelectedItemPosition();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(Language + "_" + USERNAME, position);
                editor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = gender.getSelectedItemPosition();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(Gender + "_" + USERNAME, position);
                editor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(DarkMode + "_" + USERNAME, dark.isChecked());
                editor.apply();

                if (isChecked)
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        loadAndUpdateData();
    }

    public void loadAndUpdateData() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPrefences, Context.MODE_PRIVATE);
        ageSP = sharedPreferences.getString(Age + "_" + USERNAME, "");
        weightSP = sharedPreferences.getString(Weight + "_"  + USERNAME, "");
        heightSP = sharedPreferences.getString(Height + "_"  + USERNAME, "");
        usernameSP = sharedPreferences.getString(Username + "_" + USERNAME, USERNAME);
        languageSP = sharedPreferences.getInt(Language + "_" + USERNAME, -1);
        genderSP = sharedPreferences.getInt(Gender + "_" + USERNAME, -1);
        darkSP = sharedPreferences.getBoolean(DarkMode + "_" + USERNAME, false);

        age.setText(ageSP);
        weight.setText(weightSP);
        height.setText(heightSP);
        username.setText(usernameSP);
        language.setSelection(languageSP);
        gender.setSelection(genderSP);
        dark.setChecked(darkSP);

    }


}
