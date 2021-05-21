package com.example.xnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.xnotes.adapters.RecycleViewAdapter;
import com.example.xnotes.classes.RegionLanguage;
import com.example.xnotes.database.NotesDatabase;
import com.example.xnotes.entities.Note;
import com.example.xnotes.services.BackgroundNotification;
import com.example.xnotes.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> noteList;
    private RecycleViewAdapter notesAdapter;
    FloatingActionButton actionButton;
    public static RecyclerView rcView;
    NotesDatabase notesDatabase;
    public static LinearLayout mLinearNoData;
    public static Activity mainA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("XNotes");

        new RegionLanguage(MainActivity.this);
        mLinearNoData = findViewById(R.id.linearNoData);
        actionButton = findViewById(R.id.fab);
        rcView = findViewById(R.id.rcView);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                startActivity(intent);
            }
        });

        new BackgroundNotification(this).performForegroundServices();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, RecyclerView.VERTICAL, false);
        rcView.setLayoutManager(gridLayoutManager);

        notesDatabase = NotesDatabase.getDatabase(getApplicationContext());
        noteList = new ArrayList<>();

        @SuppressLint("StaticFieldLeak")
        class classGetData extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids) {
                noteList = notesDatabase.noteDao().getAllNotes();
                return noteList;
            }

            @Override
            protected void onPostExecute(List<Note> noteList) {
                notesAdapter = new RecycleViewAdapter(noteList, getApplicationContext());
                if (notesAdapter.getItemCount() > 0) {
                    rcView.setAdapter(notesAdapter);
                    mLinearNoData.setVisibility(View.GONE);
                    notesAdapter.notifyDataSetChanged();
                } else {
                    mLinearNoData.setVisibility(View.VISIBLE);
                    rcView.setAdapter(notesAdapter);
                    notesAdapter.notifyDataSetChanged();
                }
                super.onPostExecute(noteList);
            }
        }
        classGetData data = new classGetData();
        data.execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainA = this;

    }

    //to stop
    public static void onFinishActivity() {
        MainActivity.mainA.finish();
    }

}