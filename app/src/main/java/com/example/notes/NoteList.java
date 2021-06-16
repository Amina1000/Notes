package com.example.notes;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class NoteList extends Fragment {

    private LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        linearLayout = view.findViewById(R.id.list_container);
        addNotesToList();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof NoteScreen)) {
            throw new RuntimeException("Activity must implement NoteScreen");
        }
    }
    private void addNotesToList() {

        String[] notesArray = getResources().getStringArray(R.array.notes);
        for (int i = 0, notesArrayLength = notesArray.length; i < notesArrayLength; i++) {
            String note = notesArray[i];
            TextView notesView = new TextView(getContext());
            notesView.setText(note);
            notesView.setTextSize(20);
            notesView.setTypeface(null, Typeface.BOLD_ITALIC);
            final int fi = i;
            notesView.setOnClickListener(v -> {
                v.setBackgroundResource(R.color.teal_700);
                ((NoteScreen) requireActivity()).openNoteScreen(new Note(note,
                        getResources().getStringArray(R.array.descriptions)[fi]));
            });
            linearLayout.addView(notesView);
            //noteList.add(new Note(note, ""));
        }

    }
}