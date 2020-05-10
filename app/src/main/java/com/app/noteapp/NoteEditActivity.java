package com.app.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class NoteEditActivity extends AppCompatActivity
{



    EditText editTextTitle,editTextMessage;
    Button buttonAdd;

    int editUId;
    boolean isEdit=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        init();

        isEdit();
    }

    private void isEdit()
    {

        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            isEdit=true;
            editUId=extras.getInt("Id");
            editTextTitle.setText(extras.getString("Title"));
            editTextMessage.setText(extras.getString("Message"));

            buttonAdd.setText("Edit");

        }

    }

    private void init()
    {

        editTextTitle=findViewById(R.id.editTextTitle);
        editTextMessage=findViewById(R.id.editTextMessage);

        buttonAdd=findViewById(R.id.buttonAdd);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(notEmpty())
                {


                    addInDatabase();
                }

            }
        });

    }

    private boolean notEmpty()
    {



        if(TextUtils.isEmpty(editTextTitle.getText()))
        {
            editTextTitle.setError("Required Field");
            editTextTitle.requestFocus();
            return false;
        }


        if(TextUtils.isEmpty(editTextMessage.getText()))
        {
            editTextMessage.setError("Required Field");
            editTextMessage.requestFocus();
            return false;
        }


        return true;
    }



    private void addInDatabase()
    {


        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mydb").build();


        final NotePadDao notePadDao = db.userDao();

        final Notepad notepad=new Notepad();



        if(isEdit)
            notepad.setUid(editUId);

        notepad.setDate(getDate());

        notepad.setTitle(editTextTitle.getText().toString());
        notepad.setMessage(editTextMessage.getText().toString());

        new Thread()  //Thread The Code In Run Method Of Thread Run In Background
        {
            @Override
            public void run()
            {

                super.run();

                if(isEdit)
                    notePadDao.Update(notepad);
                else
                    notePadDao.insertAll(notepad);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {

                        if(isEdit)
                            Toast.makeText(NoteEditActivity.this,"Edited Successfully",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(NoteEditActivity.this,"Added Successfully",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }.start();

    }
    public  String getDate()
    {
        Date d = new Date();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        return String.valueOf(df.format("MMMM dd,yyyy hh:mm", new Date()));

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(NoteEditActivity.this,MainActivity.class));
    }
}
