package com.example.xnotes.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.example.xnotes.adapters.RecycleViewAdapter;
import com.example.xnotes.database.NotesDatabase;
import com.example.xnotes.entities.Note;

import java.util.ArrayList;
import java.util.List;

import static com.example.xnotes.MainActivity.mLinearNoData;
import static com.example.xnotes.MainActivity.rcView;

public class getDataFromDatabase {
    private List<Note> noteList;
    private RecycleViewAdapter notesAdapter;
    NotesDatabase notesDatabase;

    public void getData(final Context context) {
        notesDatabase = NotesDatabase.getDatabase(context);
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
                notesAdapter = new RecycleViewAdapter(noteList, context);
                if (notesAdapter.getItemCount() > 0) {
                    rcView.setAdapter(notesAdapter);
                    notesAdapter.notifyDataSetChanged();
                    mLinearNoData.setVisibility(View.GONE);
                } else {
                    rcView.setAdapter(notesAdapter);
                    notesAdapter.notifyDataSetChanged();
                    mLinearNoData.setVisibility(View.VISIBLE);
                }
                super.onPostExecute(noteList);
            }
        }
        classGetData data = new classGetData();
        data.execute();
    }
}