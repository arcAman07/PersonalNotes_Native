package com.example.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;

public class AddActivity extends AppCompatActivity {
    Button button;
    EditText addText;
    Intent intent1;


    public void save(View view) {
        addText = (EditText) findViewById(R.id.addText);
        button = (Button) findViewById(R.id.button);

        int noteId = intent1.getIntExtra("noteId",-2);
        Log.i("info",String.valueOf(noteId));
        if (noteId!=-1) {
            MainActivity.notes.set(noteId,String.valueOf(addText.getText()));
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
        if (noteId == -1) {
            MainActivity.notes.add(String.valueOf(addText.getText()));
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("noteNo",-2);
        startActivity(intent);

    }

    public void returnBack(View view) {
        Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent2);
//        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addText = (EditText) findViewById(R.id.addText);
        intent1 = getIntent();
        int noteId = intent1.getIntExtra("noteId",-1);
        if (noteId!=-1) {
            addText.setText(MainActivity.notes.get(noteId));
        }


    }
}