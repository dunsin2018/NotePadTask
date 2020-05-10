package com.app.noteapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Notepad

{
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @ColumnInfo(name = "title")
    public String Title;

    @ColumnInfo(name = "message")
    public String Message;
    @ColumnInfo(name = "date")
    public String Date;


}

