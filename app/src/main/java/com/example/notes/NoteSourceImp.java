package com.example.notes;

import android.content.res.Resources;

import java.util.List;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 12.06.2021
 */
public class NoteSourceImp implements NoteSource {
    private final List<Note> noteLinkedList;
    private final Resources resources;

    public NoteSourceImp(Resources resources, List<Note> noteLinkedList) {
        this.noteLinkedList = noteLinkedList;
        this.resources = resources;
    }



    public Note getNoteData(int position) {
          return noteLinkedList.get(position);
    }

    public int size(){
        return noteLinkedList.size();
    }

    @Override
    public void deleteNoteData(int position) {
        noteLinkedList.remove(position);
    }

    @Override
    public void updateNoteData(int position, Note noteData) {
        noteLinkedList.set(position, noteData);
    }

    @Override
    public void addNoteData(Note noteData) {
        noteLinkedList.add(noteData);
    }

    @Override
    public void clearNoteData() {
        noteLinkedList.clear();
    }
}
