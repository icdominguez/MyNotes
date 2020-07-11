package com.icdominguez.mynotes.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.icdominguez.mynotes.db.entity.NoteEntity;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert
    void insert(NoteEntity note);

    @Update
    void update(NoteEntity note);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("DELETE FROM notes WHERE id = :noteId")
    void deleteById(int noteId);

    @Query("SELECT * FROM notes ORDER BY title ASC")
    LiveData<List<NoteEntity>> getAll();

    @Query("SELECT * FROM notes WHERE favorite LIKE 1")
    LiveData<List<NoteEntity>> getFavorites();

}
