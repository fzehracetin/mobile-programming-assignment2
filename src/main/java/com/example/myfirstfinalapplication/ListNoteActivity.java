package com.example.myfirstfinalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListNoteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addButton;
    String USERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        setTitle("Notes");

        USERNAME = getIntent().getStringExtra("USERNAME");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        NoteAdapter noteAdapter = new NoteAdapter(this, Note.getData(this, USERNAME),
                USERNAME);
        recyclerView.setAdapter(noteAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNote = new Intent(ListNoteActivity.this, SaveNoteActivity.class);
                addNote.putExtra("USERNAME", USERNAME);
                startActivity(addNote);
            }
        });
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        USERNAME = getIntent().getStringExtra("USERNAME");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        NoteAdapter noteAdapter = new NoteAdapter(this, Note.getData(this, USERNAME),
                USERNAME);
        recyclerView.setAdapter(noteAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNote = new Intent(ListNoteActivity.this, SaveNoteActivity.class);
                addNote.putExtra("USERNAME", USERNAME);
                startActivity(addNote);
            }
        });
    }
}
