package com.example.notes;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;


public class NoteList extends Fragment {

    private LinearLayout linearLayout;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        linearLayout = view.findViewById(R.id.list_container);
        context = getContext();
        initPopupMenu(view);
        addNotesToList();
    }

    private void initPopupMenu(View view) {
        Button btnPopupMenu = view.findViewById(R.id.btn_popup_menu);
        btnPopupMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, v);
            popup.inflate(R.menu.popup_main_menu);
            popup.show();
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_add) {
                    Toast.makeText(context, "Создали заметку", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.action_clear) {
                    Toast.makeText(context, "Удалили заметку", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return true;
            });
        });
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
            TextView notesView = new TextView(context);
            notesView.setText(note);
            notesView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textSize));
            notesView.setTypeface(null, Typeface.BOLD_ITALIC);
            final int fi = i;
            notesView.setOnClickListener(v -> {
                v.setBackgroundResource(R.color.teal_700);
                ((NoteScreen) requireActivity()).openNoteScreen(new Note(note,
                        getResources().getStringArray(R.array.descriptions)[fi]));
            });
            linearLayout.addView(notesView);
        }
    }
}