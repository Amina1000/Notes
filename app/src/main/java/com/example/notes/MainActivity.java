package com.example.notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NoteFragment.Controller,NoteScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, new NoteList())
                .commit();
    }

    @Override
    public void openNoteScreen(Note note) {

        boolean isLandscape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;

        getSupportFragmentManager()
                .beginTransaction()
                .add(isLandscape ? R.id.detail_container : R.id.main_container,
                        NoteFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void saveResult(Note note) {
        //to do
    }
}