package com.example.myfirstfinalapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private ArrayList<Note> noteList;
    LayoutInflater inflater;
    Context context;
    String USERNAME;

    public NoteAdapter (Context context, ArrayList<Note> noteList, String USERNAME) {
        inflater = LayoutInflater.from(context);
        this.noteList = noteList;
        this.context = context;
        this.USERNAME = USERNAME;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_item,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note selectedNote = noteList.get(position);
        holder.setData(selectedNote, position);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parentLayout;
        TextView titleTV, bodyTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            titleTV = itemView.findViewById(R.id.title);
            bodyTV = itemView.findViewById(R.id.body);

        }

        public void setData(final Note selectedNote, int position) {
            this.titleTV.setText(selectedNote.getTitle().split("_")[0]);

            FileInputStream fis = null;
            try {
                fis = context.openFileInput(selectedNote.getTitle() + ".txt");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;
                if ((text = br.readLine()) != null) {
                    this.bodyTV.setText(text);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent noteIntent = new Intent(view.getContext(), SaveNoteActivity.class);
                    noteIntent.putExtra("Note", selectedNote.getTitle());
                    noteIntent.putExtra("USERNAME", USERNAME);
                    view.getContext().startActivity(noteIntent);
                }
            });
        }
    }



}
