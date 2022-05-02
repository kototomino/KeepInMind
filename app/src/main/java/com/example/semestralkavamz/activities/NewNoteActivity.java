package com.example.semestralkavamz.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semestralkavamz.R;
import com.example.semestralkavamz.data.Note;
import com.example.semestralkavamz.database.NotesDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewNoteActivity extends AppCompatActivity {

    private ImageView imageBack;
    private EditText title;
    private EditText inputNote;
    private TextView date;
    private ImageView addNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        imageBack = findViewById(R.id.backArrow);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        title = findViewById(R.id.title);
        inputNote = findViewById(R.id.inputNote);
        date = findViewById(R.id.dateTime);
        addNote = findViewById(R.id.submitNote);
        setTime();
        setStuff();



    }
    private void setStuff() {
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }
    private void setTime() {
        date.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date()));
    }
    private void saveNote() {
        if(inputNote.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Title is empty ... please insert title before saving note!",Toast.LENGTH_LONG).show();
            return;
        }
        Note note = new Note();
        note.setTitle(title.getText().toString());
        note.setInputNote(inputNote.getText().toString());
        note.setTime(date.getText().toString());

        @SuppressLint("StaticFieldLeak")
        class SaveNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        new SaveNote().execute();
    }
}