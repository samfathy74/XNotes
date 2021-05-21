package com.example.xnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xnotes.classes.RegionLanguage;
import com.example.xnotes.classes.getDataFromDatabase;
import com.example.xnotes.database.NotesDatabase;
import com.example.xnotes.entities.Note;
import com.example.xnotes.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class NoteEditorActivity extends AppCompatActivity {
    EditText editText, editTitle;
    int mDataId;
    String mDataTitle, mDataBody;
    Note note;
    NotesDatabase notesDatabase;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.ok) {
            if (editText.getText().toString().trim().length() < 1) {
                Toast.makeText(this, "Cannot take note empty!", Toast.LENGTH_SHORT).show();
            } else {
                if (mDataId != -1) {
                    updateData();
                } else {
                    insertData();
                }
            }
            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        setTitle("Write notes");

        new RegionLanguage(NoteEditorActivity.this);
        notesDatabase = NotesDatabase.getDatabase(getApplicationContext());
        editText = (EditText) findViewById(R.id.editText);
        editTitle = (EditText) findViewById(R.id.editTitle);

        mDataId = getIntent().getIntExtra("noteID", -1);
        mDataTitle = getIntent().getStringExtra("title");
        mDataBody = getIntent().getStringExtra("body");

        if (mDataId != -1) {
            editTitle.setText(mDataTitle);
            editText.setText(mDataBody);
        } else {
            mDataTitle = "";
            mDataBody = "";
        }

    }

    private void insertData() {
        String title;
        if (editTitle.getText().toString().trim().equals("")) {
            title = "No Title";
        } else {
            title = editTitle.getText().toString().trim();
        }
        String DateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH).format(Calendar.getInstance().getTime()).toString();
        note = new Note(title, DateTime, editText.getText().toString().trim());

        if (note != null) {
            notesDatabase.noteDao().insertNote(note);

            new getDataFromDatabase().getData(getApplicationContext());
            finish();
            Toast.makeText(this, "Saved note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Problem when saved note", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData() {
        String title;
        if (editTitle.getText().toString().trim().equals("")) {
            title = "No Title";
        } else {
            title = editTitle.getText().toString().trim();
        }

        String DateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH).format(Calendar.getInstance().getTime()).toString();
        notesDatabase.noteDao().updateNote(title, editText.getText().toString().trim(), DateTime, mDataId);

        new getDataFromDatabase().getData(getApplicationContext());
        finish();
        Toast.makeText(this, "Updated note", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        if (editText.getText().toString().trim().length() < 1) {
            finish();
        } else {
            if (!editTitle.getText().toString().equals(mDataTitle) || !editText.getText().toString().equals(mDataBody)) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(NoteEditorActivity.this, R.style.Theme_MaterialComponents_Light_Dialog_Alert);
                if (mDataId != -1) {
                    builder1.setTitle("Update");
                    builder1.setMessage("Do you want to update notes?");
                } else {
                    builder1.setTitle("Save");
                    builder1.setMessage("Do you want to save notes?");
                }
                builder1.setIcon(R.mipmap.ic_launcher);
                builder1.setCancelable(false);
                builder1.setPositiveButton(Html.fromHtml("Yes"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mDataId != -1) {
                            updateData();
                        } else {
                            insertData();
                        }
                    }
                });
                builder1.setNegativeButton(Html.fromHtml("No"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder1.create();
                dialog.show();
            } else {
                finish();
            }
        }
    }
}
