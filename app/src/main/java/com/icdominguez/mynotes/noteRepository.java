package com.icdominguez.mynotes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.icdominguez.mynotes.db.NoteRoomDatabase;
import com.icdominguez.mynotes.db.dao.NoteDAO;
import com.icdominguez.mynotes.db.entity.NoteEntity;

import java.util.List;

public class noteRepository {
    private NoteDAO noteDAO;
    private LiveData<List<NoteEntity>> allNotes;
    private LiveData<List<NoteEntity>> allFavNotes;

    public noteRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        noteDAO = db.noteDAO();
        allNotes = noteDAO.getAll();
        allFavNotes = noteDAO.getFavorites();
    }

    public LiveData<List<NoteEntity>> getAll() {
        return allNotes;
    }

    public LiveData<List<NoteEntity>> getAllFavs() {
        return allFavNotes;
    }

    public void insert (NoteEntity note) {
        new insertAsyncTask(noteDAO).execute(note);
    }

    public void update (NoteEntity note) {
        new updateAsyncTask(noteDAO).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDAO noteDAOAsyncTask;

        insertAsyncTask(NoteDAO dao) {
            noteDAOAsyncTask = dao;
        }


        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDAOAsyncTask.insert(noteEntities[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDAO noteDAOAsyncTask;

        updateAsyncTask(NoteDAO dao) {
            noteDAOAsyncTask = dao;
        }


        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDAOAsyncTask.update(noteEntities[0]);
            return null;
        }
    }
}
