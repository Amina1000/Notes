package com.example.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;


public class NoteList extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private final List<Note> noteLinkedList = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view_notes);
        context = getContext();
        initPopupMenu(view);
        initRecyclerView();
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

    private void initRecyclerView() {

        createNoteFromArrays();
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        NoteAdapter adapter = new NoteAdapter(noteLinkedList);
        recyclerView.setAdapter(adapter);
        // Установим слушателя
        adapter.SetOnItemClickListener((view, position) -> {
            view.setBackgroundResource(R.color.teal_700);
            ((NoteScreen) requireActivity()).openNoteScreen(noteLinkedList.get(position));
        });

    }

    private void createNoteFromArrays() {
        String[] notesArray = getResources().getStringArray(R.array.notes);
        for (int i = 0, notesArrayLength = notesArray.length; i < notesArrayLength; i++) {
            String note = notesArray[i];
            noteLinkedList.add(new Note(note,getResources().getStringArray(R.array.descriptions)[i]));
        }
    }
}