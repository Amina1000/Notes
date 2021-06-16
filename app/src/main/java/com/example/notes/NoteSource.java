package com.example.notes;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 12.06.2021
 */
public interface NoteSource {
    Note getNoteData(int position);
    int size();

    void deleteNoteData(int position);
    void updateNoteData(int position, Note noteData);
    void addNoteData(Note noteData);
    void clearNoteData();
}
