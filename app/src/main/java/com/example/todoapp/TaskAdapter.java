package com.example.todoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.todoapp.ToDoList.tasks;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private final Context context;
    private final DbHelper dbHelper;

    public TaskAdapter(Context context) {
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.tasks_adapter, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task currentTask = tasks.get(position);

        holder.header.setText(currentTask.header);
        holder.description.setText(currentTask.description);
        holder.checkBox.setChecked(currentTask.isCompleted.equals("true"));

        Log.d("TaskAdapter", "Task loaded: " + currentTask.id + " - " + currentTask.header);

        // Обновление состояния задачи (isCompleted)
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTask.isCompleted = isChecked ? "true" : "false";

            boolean isUpdated = dbHelper.updateData(
                    currentTask.id,
                    currentTask.header,
                    currentTask.description,
                    isChecked
            );

            String message = isUpdated ? "Статус обновлён" : "Ошибка обновления статуса";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        });

        // Удаление задачи при долгом нажатии
        holder.itemView.setOnLongClickListener(v -> {
            boolean isDeleted = dbHelper.deleteData(currentTask.id);

            if (isDeleted) {
                int adapterPosition = holder.getAdapterPosition();
                tasks.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                Toast.makeText(context, "Задача удалена", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Ошибка удаления задачи", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView header, description;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            description = itemView.findViewById(R.id.description);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
