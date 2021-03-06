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


/**
 * homework com.example.notes
 *
 * @author Amina
 * 11.06.2021
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.BaseViewHolder> {
    private final NoteSource dataSource;
    private OnItemClickListener itemClickListener;  // Слушатель будет устанавливаться извне
    public final int CMD_UPDATE = 0;
    public final int CMD_DELETE = 1;
    private static final int NOTE_VIEW_TYPE = 1;
    private static final int GROUP_VIEW_TYPE = 0;
    private final boolean isLandscape;


    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public NoteAdapter(NoteSource dataSource, boolean isLandscape) {
        this.dataSource = dataSource;
        this.isLandscape = isLandscape;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public NoteAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Создаём новый элемент пользовательского интерфейса
        // Через Inflater

        switch (viewType) {
            case NOTE_VIEW_TYPE:
                View vNote = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item, viewGroup, false);
                // Здесь можно установить всякие параметры
                return new NoteViewHolder(vNote);
            case GROUP_VIEW_TYPE:
                View vGroup = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.group_title, viewGroup, false);
                // Здесь можно установить всякие параметры
                return new GroupViewHolder(vGroup);
            default:
                throw new RuntimeException("Не верно указан тип View holder");
        }
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.BaseViewHolder viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder
        viewHolder.setData(dataSource, i);
    }

    @Override
    public int getItemViewType(int position) {
        return dataSource.isGroupItem(position) ? GROUP_VIEW_TYPE : NOTE_VIEW_TYPE;
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
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class NoteViewHolder extends BaseViewHolder {

        private final TextView title;
        private final TextView description;

        @RequiresApi(api = Build.VERSION_CODES.N)
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            AppCompatImageView image = itemView.findViewById(R.id.imageView);

            // Обработчик нажатий на этом ViewHolder
            image.setOnClickListener(v -> {
                if (isLandscape && itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition(), CMD_UPDATE);
                } else {
                    image.showContextMenu(getAdapterPosition(), getAdapterPosition());
                }
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

        public void setData(NoteSource noteSourceImp, int position) {
            Note note = noteSourceImp.getNoteData(position);
            title.setText(note.getName());
            description.setText(note.getDescription());
        }
    }

    public static class GroupViewHolder extends BaseViewHolder {

        private final TextView title;

        public GroupViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.group_title);
        }

        public void setData(NoteSource noteSourceImp, int position) {
            title.setText(noteSourceImp.getGroupTitle(position));
        }
    }

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(NoteSource noteSourceImp, int position) {
        }
    }


}
