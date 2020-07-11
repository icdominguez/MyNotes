package com.icdominguez.mynotes;

/**
 * Manages the interaction of note type elements
 */
public interface NotesInteractionListener {
    void editNote(Note note);
    void deleteNote(Note note);
    void addFavorite(Note note);
}
