package com.example.semestralkavamz.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.semestralkavamz.R;
import com.example.semestralkavamz.data.Note;
import com.example.semestralkavamz.database.NotesDatabase;

import java.util.List;

public class IntroScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        ImageView addNote = findViewById(R.id.addNoteImage);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewNoteActivity.class));
            }
        });

        getNotes();

    }
    private void getNotes() {

        @SuppressLint("StaticFieldLeak")
                class GetNoteTask extends AsyncTask<Void,Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getDatabase(getApplicationContext()).noteDao().notesList();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                Log.d("MY_NOTES",notes.toString());
            }
        }
        new GetNoteTask().execute();
    }


}
