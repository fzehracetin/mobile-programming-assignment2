package com.example.myfirstfinalapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateMailActivity extends AppCompatActivity {

    EditText to, subj, text;
    Button sendButton;
    ImageButton attachButton;
    String subject, message;
    String[] recipientList;
    Uri result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mail);

        setTitle("Compose Mail");

        to = findViewById(R.id.to);
        subj = findViewById(R.id.subject);
        text = findViewById(R.id.compose);
        attachButton = findViewById(R.id.attachment);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipientList = to.getText().toString().split(",");
                subject = subj.getText().toString();
                message = text.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, recipientList);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                if (result != null)
                    emailIntent.putExtra(Intent.EXTRA_STREAM, result);
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Choose an email app."));

            }
        });

        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent attachIntent = new Intent();
                attachIntent.setType("*/*");
                attachIntent.setAction(Intent.ACTION_GET_CONTENT);
                attachIntent.putExtra("return", true);
                startActivityForResult(Intent.createChooser(attachIntent, "Complete action using"),
                        1);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            result = data.getData();
            Toast.makeText(CreateMailActivity.this,
                    "Attachement added.", Toast.LENGTH_LONG).show();
        }
    }
}
