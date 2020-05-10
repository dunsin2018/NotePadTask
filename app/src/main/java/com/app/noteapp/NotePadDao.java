package com.app.noteapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotePadDao {


    @Query("SELECT * FROM notepad")
    List<Notepad> getAll();


    @Insert
    void insertAll(Notepad... users);

    @Delete
    void delete(Notepad user);
    @Update
    void Update(Notepad user);
}
