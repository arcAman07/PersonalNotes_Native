package com.example.notesapp;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //    ListView listView;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_reso,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                intent.putExtra("noteId",-1);
                startActivity(intent);
                return true;
            default:
                Log.i("error","Add activity is not being shown");
                return false;
        }

    }
    @Override
    public void onBackPressed() {
        //do your action
        try {
            sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(notes)).apply();
            Log.i("Notes stored",ObjectSerializer.serialize(notes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = getIntent();
//        String extra = intent.getStringExtra("note");
//        notes.add(extra);
        ListView listView = (ListView) findViewById(R.id.listView);


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                intent.putExtra("noteId",i);
                startActivity(intent);



            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).
                        setTitle("Alert Box!").setMessage("Are you sure you want to delete the note?").
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int a) {
                                notes.remove(i);
                                arrayAdapter.notifyDataSetChanged();
                                Log.i("info",String.valueOf(i));
                                Log.i("button pressed",String.valueOf(a));



                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Note wasn't deleted",Toast.LENGTH_SHORT);

                    }
                }).show();
                return true;

            }
        });




    }
}
// For Reference on Serialization

//try {
//        newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends", ObjectSerializer.serialize(new ArrayList<String>())));
//        } catch (Exception e) {
//        e.printStackTrace();
//        }
//
//        Log.i("new Friends",newFriends.toString());
//
////sharedPreferences.edit().putString("username","nick").apply();
//
////String username = sharedPreferences.getString("username","");
//
////Log.i("This is the username",username);