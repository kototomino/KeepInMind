package com.example.semestralkavamz.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.semestralkavamz.R;
import com.example.semestralkavamz.adapter.NoteAdapter;
import com.example.semestralkavamz.data.Note;
import com.example.semestralkavamz.database.NotesDatabase;

import java.util.ArrayList;
import java.util.List;

public class IntroScreenActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    private RecyclerView notesRecycler;
    private List<Note> notesList;
    private NoteAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        ImageView addNote = findViewById(R.id.addNoteImage);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), NewNoteActivity.class),REQUEST_CODE_ADD_NOTE);
            }
        });

        initializeRecyclerView();
        getNotes();

    }
    private void initializeRecyclerView() {

        notesRecycler = findViewById(R.id.recyclerView);
        //keby nieco tak tu staggeredGridlayout
        notesRecycler.setLayoutManager(new GridLayoutManager(this,GridLayoutManager.VERTICAL));
        notesList = new ArrayList<>();
        notesAdapter = new NoteAdapter(notesList);
        notesRecycler.setAdapter(notesAdapter);


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
                if (notesList.size() == 0) {
                    notesList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();

                } else {
                    notesList.add(0,notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                }
                notesRecycler.smoothScrollToPosition(0);

            }
        }
        new GetNoteTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNotes();
        }
    }
}
