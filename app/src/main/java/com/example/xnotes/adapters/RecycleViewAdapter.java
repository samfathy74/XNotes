package com.example.xnotes.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xnotes.NoteEditorActivity;
import com.example.xnotes.R;
import com.example.xnotes.classes.getDataFromDatabase;
import com.example.xnotes.database.NotesDatabase;
import com.example.xnotes.entities.Note;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.DataHolderView> {
    private List<Note> notes;

    Context context;
    NotesDatabase notesDatabase;

    public RecycleViewAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public DataHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataHolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.custm_rv_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DataHolderView holder, final int position) {

//        holder.txtId.setText(String.valueOf(notes.get(position).getId()));
        holder.txtTitle.setText(notes.get(position).getTitle());
        holder.txtTime.setText(notes.get(position).getDateTime());

        if (notes.get(position).getNoteText().length() >= 15) {
            holder.txtBody.setText(notes.get(position).getNoteText().substring(0, 15));
        } else {
            holder.txtBody.setText(notes.get(position).getNoteText());
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NoteEditorActivity.class);
                intent.putExtra("noteID", notes.get(position).getId());
                intent.putExtra("title", notes.get(position).getTitle());
                intent.putExtra("body", notes.get(position).getNoteText());
                view.getContext().startActivity(intent);
            }
        });

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getContext())                   // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
                            {
                                notesDatabase = NotesDatabase.getDatabase(context);
                                notesDatabase.noteDao().deleteNote(notes.get(position).getId());
                                Toast.makeText(view.getContext(), "Deleted note", Toast.LENGTH_SHORT).show();
                                new getDataFromDatabase().getData(view.getContext());

                            }
                        }).setNegativeButton("No", null)
                        .show();
            }
        });

        holder.imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, notes.get(position).getTitle() + "\n" + notes.get(position).getNoteText() + "\n" + notes.get(position).getDateTime());
                view.getContext().startActivity(Intent.createChooser(intent, "Share Note by Using:"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class DataHolderView extends RecyclerView.ViewHolder {
        TextView txtId, txtTitle, txtBody, txtTime;
        CardView cardView;
        ImageView imageDelete, imageShare;

        public DataHolderView(@NonNull View itemView) {
            super(itemView);
//            txtId = itemView.findViewById(R.id.txtId);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtBody = itemView.findViewById(R.id.txtBody);
            txtTime = itemView.findViewById(R.id.txtTime);
            cardView = itemView.findViewById(R.id.cardDonors);
            imageDelete = itemView.findViewById(R.id.imageDelete);
            imageShare = itemView.findViewById(R.id.imageShare);
        }
    }
}
