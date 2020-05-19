package com.example.myfirstfinalapplication;

import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AsyncTaskActivity extends AppCompatActivity {
    Button downloadButton;
    ProgressBar progressBar;
    ImageView image;
    Bitmap bmImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        downloadButton = findViewById(R.id.downloadButton);
        progressBar = findViewById(R.id.progressBar);
        image = findViewById(R.id.image);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskExample asyncTask = new AsyncTaskExample();
                asyncTask.execute();
            }
        });
    }

    private class AsyncTaskExample extends AsyncTask<String, Integer, Void> {
        int ilerleme;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(0);
            image.setVisibility(View.INVISIBLE);
        }

        protected Void doInBackground(String... strings) {
            Random random = new Random();
            int toplam = 0;
            while(toplam < 100) {
                ilerleme = random.nextInt(100);
                publishProgress(ilerleme);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                toplam = toplam + ilerleme;
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            image.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Download completed.", Toast.LENGTH_LONG).show();
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

        }
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer ilerleme = values[0];
            int progress = progressBar.getProgress();
            progressBar.setProgress(progress + ilerleme);
        }
    }
}

