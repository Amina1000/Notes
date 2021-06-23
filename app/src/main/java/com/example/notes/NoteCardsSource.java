package com.example.notes;

import android.content.res.Resources;

import java.util.LinkedList;
import java.util.List;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 12.06.2021
 */
public class NoteCardsSource implements CardSource{
    private final List<Note> noteLinkedList;
    private final Resources resources;

    public NoteCardsSource(Resources resources) {
        this.noteLinkedList = new LinkedList<>();
        this.resources = resources;
    }

    public NoteCardsSource init(){
        // строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.notes);
        // строки описаний из ресурсов
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        // изображения

        // заполнение источника данных
        for (int i = 0; i < descriptions.length; i++) {
            noteLinkedList.add(new Note(titles[i], descriptions[i]));
        }
        return this;
    }
    public Note getNoteData(int position) {
        return noteLinkedList.get(position);
    }

    public int size(){
        return noteLinkedList.size();
    }

}
