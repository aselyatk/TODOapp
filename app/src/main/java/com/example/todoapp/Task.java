package com.example.todoapp;

public class Task {
    String id, header, description, isCompleted;

    public Task() {
    }

    public Task(String id, String header, String description, String isCompleted) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    // Геттеры и сеттеры (опционально)
}
