package com.app.noteapp;

import android.app.Activity;
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
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class recycleadapter extends RecyclerView.Adapter<recycleadapter.ViewHolder> {

    private List<Notepad> mData;
    private LayoutInflater mInflater;
    private  Context mContext;

    // data is passed into the constructor
    public recycleadapter(Context context, List<Notepad> data) {
        this.mInflater = LayoutInflater.from(context);
        mContext=context;
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.recordlayout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {

        final Notepad obj = mData.get(position);
        holder.Title.setText("Title : "+obj.getTitle());
        holder.Message.setText("Message  : "+obj.getMessage());
        holder.Date.setText("Date  : "+obj.getDate());


        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                showDialog(obj,position);
                return false;
            }
        });


    }

    private void showDialog(final Notepad notepad, final int position)
    {


        AlertDialog.Builder dialog=new AlertDialog.Builder(mContext);
        dialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {


                Intent intent=new Intent(mContext,NoteEditActivity.class);
                intent.putExtra("Id",notepad.getUid());
                intent.putExtra("Title",notepad.getTitle());
                intent.putExtra("Message",notepad.getMessage());
                intent.putExtra("Date",notepad.getDate());
                getActivity().startActivity(intent);
                getActivity().finish();


            }
        });

        dialog.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {


                new Thread()
                {
                    @Override
                    public void run() {

                        AppDatabase db = Room.databaseBuilder(mContext, AppDatabase.class, "mydb").build();
                        final NotePadDao notePadDao = db.userDao();
                        notePadDao.delete(notepad);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                mData.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mContext,"Deleted Successfully",Toast.LENGTH_SHORT).show();


                            }
                        });
                        super.run();
                    }
                }.start();

            }
        });


        dialog.setNegativeButton("Cancel", null);

        dialog.show();

    }

    private Activity getActivity() {

        return (Activity) mContext;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView Title;
        TextView Message;
        TextView Date;
        ConstraintLayout parent;

        ViewHolder(View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            Title=itemView.findViewById(R.id.Title);
            Message = itemView.findViewById(R.id.Message);
            Date = itemView.findViewById(R.id.Date);
        }

    }

}