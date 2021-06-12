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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class NoteList extends Fragment {

    private RecyclerView recyclerView;
    private Context context;

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
        NoteCardsSource data = new NoteCardsSource(getResources()).init();
        initRecyclerView(data);
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

    private void initRecyclerView(NoteCardsSource data) {


        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        NoteAdapter adapter = new NoteAdapter(data);
        recyclerView.setAdapter(adapter);
        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.separator, null)));
        recyclerView.addItemDecoration(itemDecoration);
        // Установим слушателя
        adapter.SetOnItemClickListener((view, position) -> {
            view.setBackgroundResource(R.color.teal_700);
            ((NoteScreen) requireActivity()).openNoteScreen(data.getNoteData(position));
        });

    }

}