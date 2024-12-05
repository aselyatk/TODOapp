package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    Button addBtn;
    EditText editHeader, editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.btnAdd);
        editHeader = findViewById(R.id.eHeader);
        editDescription = findViewById(R.id.eDescription);
        dbHelper = new DbHelper(this);

        // Добавление новой задачи
        addBtn.setOnClickListener(v -> {
            String header = editHeader.getText().toString().trim();
            String desc = editDescription.getText().toString().trim();

            if (header.isEmpty()) {
                Toast.makeText(MainActivity.this, "Введите заголовок задачи", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                boolean isInserted = dbHelper.insertData(header, desc);
                if (isInserted) {
                    editHeader.setText("");
                    editDescription.setText("");
                    Toast.makeText(MainActivity.this, "Задача добавлена", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка добавления задачи", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Переход к списку задач
    public void moveToList(View v) {
        Intent intent = new Intent(getApplicationContext(), ToDoList.class);
        startActivity(intent);
    }
}
