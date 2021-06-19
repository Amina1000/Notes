package com.example.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NOTE_ARG_PARAM = "NOTE_ARG_PARAM";
    private static final String POSITION_ARG_PARAM = "POSITION_ARG_PARAM";

    // TODO: Rename and change types of parameters
    private Note note = null;
    private EditText eName;
    private EditText eDescription;
    private EditText eDate;
    private TextView tvAuthor;
    private int position;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param note Parameter 1.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(Note note, int position) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_ARG_PARAM, note);
        args.putInt(POSITION_ARG_PARAM,position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        setHasOptionsMenu(true);
        eName = view.findViewById(R.id.name_edit_text);
        eDescription = view.findViewById(R.id.descriptions_edit_text);
        eDate = view.findViewById(R.id.date);
        tvAuthor = view.findViewById(R.id.author);
        Button saveChanges = view.findViewById(R.id.save_changes);

        saveChanges.setOnClickListener(v -> {
            Controller controller = (Controller) getActivity();
            assert controller != null;
            Note newNote = new Note(eName.getText().toString(),
                    eDescription.getText().toString(), Calendar.getInstance().getTime());
            controller.saveResult(newNote, position);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implement NoteFragment.Controller");
        }
        assert getArguments() != null;
        if (getArguments().getParcelable(NOTE_ARG_PARAM)!= null) {

            note = getArguments().getParcelable(NOTE_ARG_PARAM);
            position = getArguments().getInt(POSITION_ARG_PARAM);
        }else throw new RuntimeException("Создайте фрагмент при помощи newInstance");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        eName.setText(note.getName());
        eDescription.setText(note.getDescription());
        eDate.setText(note.getDate().toString());
        tvAuthor.setText(note.getAuthor());

    }

    public interface Controller {
        void saveResult(Note note, int position);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_send){
            Toast.makeText(getContext(), "Отправили заметку", Toast.LENGTH_SHORT).show();
            return true;
        }else if (item.getItemId() == R.id.add_image){
            Toast.makeText(getContext(), "Добавили картинку", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}