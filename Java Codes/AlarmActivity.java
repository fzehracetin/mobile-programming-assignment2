package com.example.myfirstfinalapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private ImageButton alarmButton;
    private Button setButton, cancelButton;
    private TextView timeTV;
    private EditText alarmNameET;
    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmButton = findViewById(R.id.alarmButton);
        alarmNameET = findViewById(R.id.alarmName);
        setButton = findViewById(R.id.setButton);
        cancelButton = findViewById(R.id.cancelButton);
        timeTV = findViewById(R.id.timeText);
        c = Calendar.getInstance();
        timeTV.setText(new SimpleDateFormat("HH:mm").format(c.getTime()));

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Alarm setted.", Toast.LENGTH_SHORT).show();
                createAlarm(c);
                Calendar f = Calendar.getInstance();
                timeTV.setText(new SimpleDateFormat("HH:mm").format(f.getTime()));
                alarmNameET.setText("");
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        timeTV.setText(new SimpleDateFormat("HH:mm").format(c.getTime()));

    }

    public void createAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getApplicationContext()
                .getSystemService(getApplicationContext().ALARM_SERVICE);
        Intent intent  = new Intent(getApplicationContext(), AlertReceiver.class);
        intent.putExtra("Alarm name", alarmNameET.getText().toString());
        String time = new SimpleDateFormat("HH:mm").format(c.getTime());
        intent.putExtra("Time", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getApplicationContext()
                .getSystemService(getApplicationContext().ALARM_SERVICE);

        Intent intent  = new Intent(getApplicationContext(), AlertReceiver.class);
        intent.putExtra("Alarm name", alarmNameET.getText().toString());
        String time = new SimpleDateFormat("HH:mm").format(c.getTime());
        intent.putExtra("Time", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                1, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(AlarmActivity.this, "Alarm canceled.", Toast.LENGTH_SHORT).show();
    }
}
