package com.example.myfirstfinalapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class SensorActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor accelerometer, light;
    private EditText sensorBlank;
    Toast toast, toast2;
    double gravity;
    float x, y, z;
    double oldVector = 0;
    boolean count = false;
    ConstraintLayout layout;
    Runnable timerRunnable;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        setTitle("Sensors");

        sensorBlank = findViewById(R.id.sensorBlank);
        layout = findViewById(R.id.layout);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> mList= mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 1; i < mList.size(); i++) {
            sensorBlank.setVisibility(View.VISIBLE);
            sensorBlank.append("\n" + Integer.toString(i) + ". " + mList.get(i).getName() +
                    "\n" + mList.get(i).getVendor() + "\n" + mList.get(i).getVersion());
        }

        if (mSensorManager != null && mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER ) != null) {
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER );
            mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        else {
            toast = Toast.makeText(getApplicationContext(), "Accelerometer sensor is not avaliable.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        if (mSensorManager != null && mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            light = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            mSensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_UI);
        }
        else {
            toast2 = Toast.makeText(getApplicationContext(), "Light sensor is not avaliable.",
                    Toast.LENGTH_SHORT);
            toast2.show();
        }
    }

    public void timer() {
        handler = new Handler();
        handler.postDelayed(timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (count) {
                    Toast.makeText(SensorActivity.this,
                            "Phone waited inactive for 5 seconds. App will shut down.",
                            Toast.LENGTH_LONG).show();
                    Intent closeIntent = new Intent(SensorActivity.this, MainActivity.class);
                    closeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    closeIntent.putExtra("Close", "true");
                    startActivity(closeIntent);
                    finish();
                }
            }
        }, 5000);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            gravity = SensorManager.GRAVITY_EARTH;

            double vector = Math.sqrt((x*x +y*y + z*z) / (gravity*gravity));

            /*String text = "Accelerometer values \n" + "x: " + String.valueOf(x) + " y: "
                    + String.valueOf(y) + " z: " + String.valueOf(z) + "\n" +
                    String.valueOf(vector) + "\n" + String.valueOf(count);

            sensorBlank.setText(text);*/
            if (oldVector != 0) {
                if (oldVector - vector < 0.10 && !count) {
                    count = true;
                    timer();
                } else if (oldVector - vector > 0.10) {
                    count = false;
                    handler.removeCallbacks(timerRunnable);
                }
            }
            oldVector = vector;
        }
        else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float maxValue = light.getMaximumRange();
            float value = event.values[0];

            if (value < 100) { //karanlık
                layout.setBackgroundColor(Color.rgb(0, 0, 0));
                sensorBlank.setTextColor(Color.rgb(255, 255, 255));
            }
            else {
                layout.setBackgroundColor(Color.rgb(255, 255, 255));
                sensorBlank.setTextColor(Color.rgb(0, 0, 0));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(timerRunnable);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
