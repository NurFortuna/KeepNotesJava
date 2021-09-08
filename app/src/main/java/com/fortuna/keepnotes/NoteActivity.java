package com.fortuna.keepnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;

import com.fortuna.keepnotes.databinding.ActivityNoteBinding;

public class NoteActivity extends AppCompatActivity {

    private ActivityNoteBinding binding;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNoteBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        database=this.openOrCreateDatabase("Not",MODE_PRIVATE,null);


        Intent intent=getIntent();
        String info=intent.getStringExtra("info");

        if(info.equals("new")){
            //new note
            binding.titleText.setText("");
            binding.noteText.setText("");
            binding.button.setVisibility(View.VISIBLE);


        }else{

            int titleId=intent.getIntExtra("titleId",0);
            binding.button.setVisibility(View.INVISIBLE);


            try{

                Cursor cursor=database.rawQuery("SELECT * FROM nott WHERE id=?",new String[]{String.valueOf(titleId)});
                int titleIx=cursor.getColumnIndex("title");
                int noteIx=cursor.getColumnIndex("note");

                while(cursor.moveToNext()){
                    binding.titleText.setText(cursor.getString(titleIx));
                    binding.noteText.setText(cursor.getString(noteIx));
                }

                cursor.close();

            }catch (Exception e){

                e.printStackTrace();
            }
        }
    }

    public void save(View view){

        String title=binding.titleText.getText().toString();
        String note=binding.noteText.getText().toString();


        try{

            database.execSQL("CREATE TABLE IF NOT EXISTS nott (id INTEGER PRIMARY KEY,title VARCHAR,note VARCHAR)");
            String sqlString="INSERT INTO nott (title,note) VALUES (?,?)";
            SQLiteStatement sqLiteStatement=database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,title);
            sqLiteStatement.bindString(2,note);
            sqLiteStatement.execute();


        }catch (Exception e){
            e.printStackTrace();
        }

        Intent intent=new Intent(NoteActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}