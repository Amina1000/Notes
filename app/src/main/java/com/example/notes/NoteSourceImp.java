package com.example.notes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 12.06.2021
 */
public class NoteSourceImp implements NoteSource{


    private final List <Object> objectListItem;


    public NoteSourceImp(List <Object> objectListItem) {
        this.objectListItem = objectListItem;
    }


    public boolean isGroupItem(int position) {
        return !(objectListItem.get(position) instanceof Note);
    }

    public String getGroupTitle(int position) {
        Date date = (Date) objectListItem.get(position);
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    public Note getNoteData(int position) {
          return (Note) objectListItem.get(position);
    }

    public int size(){
        return objectListItem.size();
    }

    @Override
    public void deleteNoteData(int position) {
        objectListItem.remove(position);
    }

    @Override
    public void updateNoteData(int position, Note noteData) {
        objectListItem.set(position, noteData);
    }

    @Override
    public void addNoteData(Note noteData) {
        objectListItem.add(noteData);
    }

    @Override
    public void clearNoteData() {
        objectListItem.clear();
    }
}
