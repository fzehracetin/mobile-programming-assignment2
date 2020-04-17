package com.example.myfirstfinalapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SaveNoteActivity extends AppCompatActivity {
    EditText titleED, bodyED;
    String body, title, fname;
    TextView headerTW;
    Button deleteButton;
    String USERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_note);
        titleED = findViewById(R.id.title);
        bodyED = findViewById(R.id.note_body);
        deleteButton = findViewById(R.id.delete);
        headerTW = findViewById(R.id.header);

        if (getIntent().getStringExtra("Note") != null) {
            setTitle("Edit Note");
            fname = getIntent().getStringExtra("Note");
            headerTW.setText("EDIT NOTE");
            load();
        }
        else {
            setTitle("Create Note");
            headerTW.setText("NEW NOTE");
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                File gonnaDelete = new File(getApplicationContext().getFilesDir().toString()
                        + "/" + fname + ".txt");
                gonnaDelete.delete();

                bodyED.getText().clear();
                titleED.getText().clear();
            }
        });

    }

    public void save(View v) {
        FileOutputStream fos = null;
        body = bodyED.getText().toString();
        fname = titleED.getText().toString();
        USERNAME = getIntent().getStringExtra("USERNAME");

        try {
            fos = openFileOutput(fname + "_" + USERNAME + ".txt", MODE_PRIVATE);
            fos.write(body.getBytes());

            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + fname + "_" +
                            USERNAME + ".txt", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load () {
        FileInputStream fis = null;
        USERNAME = getIntent().getStringExtra("USERNAME");

        try {
            fis = openFileInput(fname + ".txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            bodyED.setText(sb.toString());
            titleED.setText(fname.split("_")[0]);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null ) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
