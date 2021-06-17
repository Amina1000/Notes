package com.example.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoteListFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private NoteSourceImp data;
    private NoteAdapter adapter;
    private List<Object> objectListItem = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initView(view);
        initPopupMenu(view);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_notes);
        context = getContext();
        initList();
        data = new NoteSourceImp(objectListItem);
        adapter = new NoteAdapter(data);
        initRecyclerView();
    }

    private void initList() {
        if (objectListItem.isEmpty()){
            objectListItem = new ArrayList<>();
            // строки заголовков из ресурсов
            String[] titles = getResources().getStringArray(R.array.notes);
            // строки описаний из ресурсов
            String[] descriptions = getResources().getStringArray(R.array.descriptions);
            // изображения
            // заполнение источника данных
            for (int i = 0; i < descriptions.length; i++) {
                Note note = new Note(titles[i], descriptions[i]);
                objectListItem.add(note.getDate());
                objectListItem.add(note);
            }
        }
    }

    private void initPopupMenu(View view) {
        Button btnPopupMenu = view.findViewById(R.id.btn_popup_menu);
        btnPopupMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, v);
            popup.inflate(R.menu.popup_main_menu);
            popup.show();
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_add) {
                    Note newNote = new Note("", "");
                    addUpdateNote(newNote, data.size());
                    return true;
                } else if (item.getItemId() == R.id.action_clear) {
                    data.clearNoteData();
                    //Обновляет данные списка.
                    adapter.notifyDataSetChanged();
                    return true;
                }
                return true;
            });
        });
    }

    public interface Controller {
        void openNoteScreen(Note note, int position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implement NoteScreen");
        }
    }

    private void initRecyclerView() {

        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        recyclerView.setAdapter(adapter);
        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.separator, null)));
        recyclerView.addItemDecoration(itemDecoration);
        // Установим слушателя
        adapter.SetOnItemClickListener((view, position, itemId) -> {
            view.setBackgroundResource(R.color.teal_700);
            if (itemId == adapter.CMD_UPDATE) {
                ((Controller) requireActivity()).openNoteScreen(data.getNoteData(position), position);
            } else if (itemId == adapter.CMD_DELETE) {
                data.deleteNoteData(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void addUpdateNote(Note newNote, int position) {

        if (data.size() != position) {
            data.updateNoteData(position, newNote);
        } else data.addNoteData(newNote);
        adapter.notifyItemInserted(position);
        //позицианируется на новой позиции
        recyclerView.scrollToPosition(position);
    }

}