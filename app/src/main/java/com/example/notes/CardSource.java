package com.example.notes;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 12.06.2021
 */
public interface CardSource {
    Note getNoteData(int position);
    int size();
}
