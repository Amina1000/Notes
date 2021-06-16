package com.example.notes;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;


/**
 * homework com.example.notes
 *
 * @author Amina
 * 11.06.2021
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final NoteSourceImp dataSource;
    private OnItemClickListener itemClickListener;  // Слушатель будет устанавливаться извне
    public int CMD_UPDATE = 0;
    public int CMD_DELETE = 1;

    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public NoteAdapter(NoteSourceImp dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаём новый элемент пользовательского интерфейса
        // Через Inflater
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        // Здесь можно установить всякие параметры
        return new ViewHolder(v);
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder
        viewHolder.setData(dataSource.getNoteData(i));
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view, int position, int itemId);
    }

    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;

        @RequiresApi(api = Build.VERSION_CODES.N)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            AppCompatImageView image = itemView.findViewById(R.id.imageView);

            // Обработчик нажатий на этом ViewHolder
            image.setOnClickListener(v -> {
                image.showContextMenu(getAdapterPosition(), getAdapterPosition());
            });

            image.setOnCreateContextMenuListener((menu, v, menuInfo) -> {

                menu.setHeaderTitle(R.string.item_title);
                menu.add(0, CMD_UPDATE, 0, R.string.item_update).
                        setOnMenuItemClickListener(item -> {
                            if (itemClickListener != null) {
                               itemClickListener.onItemClick(v, getAdapterPosition(), CMD_UPDATE);
                            }
                            return true;
                        });

                menu.add(0, CMD_DELETE, 0, R.string.item_delete).
                        setOnMenuItemClickListener(item -> {
                            if (itemClickListener != null) {
                                itemClickListener.onItemClick(v, getAdapterPosition(), CMD_DELETE);
                            }
                            return true;
                        });

            });
        }

        public void setData(Note note) {
            title.setText(note.getName());
            description.setText(note.getDescription());
        }
    }
}
