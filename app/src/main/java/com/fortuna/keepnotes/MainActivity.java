package com.fortuna.keepnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.fortuna.keepnotes.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayList<Note> noteArrayList;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        noteArrayList=new ArrayList<>();

        binding.recyclerViewBinding.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter=new NoteAdapter(noteArrayList);
        binding.recyclerViewBinding.setAdapter(noteAdapter);



        getData();

    }

    private void getData(){

        try{
            SQLiteDatabase sqLiteDatabase=this.openOrCreateDatabase("Not",MODE_PRIVATE,null);
            Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM nott",null);

            int titleIx=cursor.getColumnIndex("title");
            int noteIx=cursor.getColumnIndex("note");
            int idIx=cursor.getColumnIndex("id");

            while(cursor.moveToNext()){

                String title=cursor.getString(titleIx);
                int id=cursor.getInt(idIx);
                String note=cursor.getString(noteIx);
                Note notes=new Note(title,note,id);
                noteArrayList.add(notes);
            }

            noteAdapter.notifyDataSetChanged();
            cursor.close();



        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.note_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_note){

            Intent intent=new Intent(this,NoteActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}