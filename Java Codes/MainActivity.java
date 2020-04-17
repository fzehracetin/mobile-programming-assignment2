package com.example.myfirstfinalapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final TextView count = (TextView) findViewById(R.id.count);
        final ArrayList<Person> persons = Person.getData();

        Uri uri = Uri.parse("android.resource://" + "com.example.myfirstfinalapplication"
                + "/drawable/" + "icon.png");

        if (getIntent().hasExtra("Close")) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
                if (checkPassword(username.getText().toString(), password.getText().toString(),
                        persons, count)) {
                    Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
                    menuIntent.putExtra("USERNAME", username.getText().toString());
                    startActivity(menuIntent);
                    count.setText("0");
                }
                else {
                    username.setText("");
                    password.setText("");
                }
            }

        });
    }

    private boolean checkPassword (String username, String password, ArrayList<Person> persons,
                                   TextView count) {
        Handler handler = new Handler();
        int i = 0;
        boolean found = false;
        while (i < persons.size() && ! found) {
            if (username.equals(persons.get(i).getUsername()) && password.equals(persons.get(i).
                    getPassword()))
                found = true;
            else
                i++;
        }
        Toast toast;
        if (found) {
            toast = Toast.makeText(getApplicationContext(), "Username and password valid.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            int counti = Integer.parseInt(count.getText().toString()) + 1;
            if (counti == 3) {
                toast = Toast.makeText(getApplicationContext(),
                        "You made 3 incorrect try, app will shut down", Toast.LENGTH_SHORT);
                toast.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                }, 3000);
            }
            else {
                toast = Toast.makeText(getApplicationContext(), "Username or password incorrect.",
                        Toast.LENGTH_SHORT);
                count.setText(Integer.toString(counti));
                toast.show();
            }
        }
        return found;
    }
}


