package com.example.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity {
    RecyclerView recyclerView;
    public static ArrayList<Task> tasks;
    DbHelper dbHelper;
    static TaskAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        dbHelper = new DbHelper(this);
        recyclerView = findViewById(R.id.recycleView);

        tasks = new ArrayList<>();
        myAdapter = new TaskAdapter(this);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayData();
    }

    private void displayData() {
        tasks.clear(); // Очищаем список перед добавлением
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Нет данных", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            tasks.add(new Task(
                    cursor.getString(0), // id
                    cursor.getString(1), // header
                    cursor.getString(2), // description
                    cursor.getString(3)  // isCompleted
            ));
        }
        myAdapter.notifyDataSetChanged(); // Уведомляем адаптер об изменении данных
    }

    // Переход к добавлению новой задачи
    public void addNewTask(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayData(); // Обновляем список при возвращении
    }
}
