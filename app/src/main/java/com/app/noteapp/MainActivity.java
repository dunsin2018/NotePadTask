package com.app.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    Button buttonCreate;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        init();
        setDataFromDatabase();


    }

    private void setDataFromDatabase()
    {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mydb").build();
        final NotePadDao notePadDao = db.userDao();
        new Thread()
        {
            @Override
            public void run()
            {

                super.run();
                final List<Notepad> AllData = notePadDao.getAll();

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        if(AllData.size()<1)  //IF LIST IS EMPTY
                        {

                            Toast.makeText(MainActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            recyclerView.setAdapter(new recycleadapter(MainActivity.this,AllData));
                        }

                    }
                });

            }
        }.start();

    }

    private void init()
    {


        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        // BUTTON
        buttonCreate=findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(MainActivity.this,NoteEditActivity.class));

            }
        });


    }
}
