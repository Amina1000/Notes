package com.example.notes;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements NoteFragment.Controller, NoteScreen {

    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
        initView();
        readSettings();
        addFragment(R.id.main_container, new NoteList(), true);
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void addFragment(int idView, Fragment fragment, boolean replace) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // Добавить фрагмент
        if (replace) {
            fragmentTransaction.replace(idView, fragment);
        } else {
            fragmentTransaction.add(idView, fragment);
        }
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void openNoteScreen(Note note) {
        int idView = isLandscape ? R.id.detail_container : R.id.main_container;
        addFragment(idView, NoteFragment.newInstance(note), true);
    }

    @Override
    public void saveResult(Note note) {
        //to do
    }

    // Чтение настроек
    private void readSettings() {
        // Специальный класс для хранения настроек
        SharedPreferences sharedPref = getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        // Считываем значения настроек
        Settings.fontSize = sharedPref.getInt(Settings.FONT_SIZE, 20);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Здесь определяем меню приложения (активити)
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }
            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            addFragment(R.id.main_container, new SettingsFragment(), true);
            return true;
        } else if (id == R.id.action_main) {
            addFragment(R.id.main_container, new NoteList(), true);
            return true;
        } else if (id == R.id.action_sort) {
            Toast.makeText(this, "Сортировка пока не работает", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}