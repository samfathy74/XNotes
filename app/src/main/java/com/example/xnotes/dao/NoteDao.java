package com.example.xnotes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.xnotes.entities.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id ASC")
    List<Note> getAllNotes();

    @Query("Delete FROM notes Where id=:id")
    void deleteNote(int id);

    @Query("Update notes set title =:title , note_text =:body,date_time=:date_time  Where id=:id")
    void updateNote(String title, String body, String date_time, int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);
}
