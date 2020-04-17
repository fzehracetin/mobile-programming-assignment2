package com.example.myfirstfinalapplication;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

public class Note {

    private String title;
    private File text;
    private String body;

    public Note (String title, File text) {
        this.title = title;
        this.text = text;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(File text) {
        this.text = text;
    }

    public File getText() {
        return text;
    }

    public static ArrayList<Note> getData(Context context, String USERNAME) {
        ArrayList<Note> noteList = new ArrayList<Note>();

        File directory = context.getFilesDir();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().contains( "_" + USERNAME + ".txt")) {
                    Note note = new Note(file.getName().split("\\.")[0], file);
                    noteList.add(note);
                }
            }
        }
        return noteList;
    }
}
