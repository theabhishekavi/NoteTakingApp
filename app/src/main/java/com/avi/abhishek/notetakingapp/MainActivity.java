package com.avi.abhishek.notetakingapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<Note> notes = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText noteTitle = findViewById(R.id.etNoteTitle);

        //Adding a view by JAVA
//        LinearLayout linearLayout = findViewById(R.id.linearLayout);
//
//        Button button = new Button(this);
//        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        button.setText("New Button");
//        linearLayout.addView(button);


        //Here the EditText is empty so the String titleNote has the value ""
//        String titleNote = noteTitle.getText().toString();

        ListView listView;
        listView = findViewById(R.id.listView);

        SharedPreferences sharedPreferences= getSharedPreferences("Preferences",MODE_PRIVATE);
        notes.clear();
        int size= sharedPreferences.getInt("Status_size",0);
        for(int i=0;i<size;i++)
        {
            notes.add(SharedPreferences.getString("Status_" + i, null));
        }


        final NotesAdapter notesAdapter = new NotesAdapter(notes);

        listView.setAdapter(notesAdapter);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title,time;
                title = noteTitle.getText().toString();
                noteTitle.setText("");

                time = String.valueOf(System.currentTimeMillis());
                Note note = new Note(title, time);

                notes.add(note);
                notesAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    protected void onStop() {

        SharedPreferences sharedPreferences = getSharedPreferences("Preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("Status_size", notes.size());

        for(int i=0;i<notes.size();i++)
        {
            editor.remove("Status_" + i);
            editor.putString("Status_" + i, "" +notes.get(i));
        }
        editor.apply();
        super.onStop();
    }
}

