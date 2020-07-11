package com.icdominguez.mynotes.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.icdominguez.mynotes.db.dao.NoteDAO;
import com.icdominguez.mynotes.db.entity.NoteEntity;

@Database(entities = { NoteEntity.class }, version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase {
    public abstract NoteDAO noteDAO();

    private static volatile NoteRoomDatabase INSTANCE;

    public static NoteRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
