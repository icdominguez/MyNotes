package com.icdominguez.mynotes.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.icdominguez.mynotes.db.entity.NoteEntity;
import com.icdominguez.mynotes.noteRepository;

import java.util.List;

public class NewNoteDialogViewModel extends AndroidViewModel {
    private LiveData<List<NoteEntity>> allNotes;
    private com.icdominguez.mynotes.noteRepository noteRepository;

    public NewNoteDialogViewModel(Application application) {
        super(application);

        noteRepository = new noteRepository(application);
        allNotes = noteRepository.getAll();
    }

    // The fragment that recives the new list of notes
    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    // The fragment that insert a new note, must comunicate it to the view model
    public void insertNote(NoteEntity newNoteEntity) {
        noteRepository.insert(newNoteEntity);
    }

    public void updateNote(NoteEntity newNoteEntity) {
        noteRepository.update(newNoteEntity);
    }

}