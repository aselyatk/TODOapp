package com.example.todoapp;


import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {

        super(context, "tasks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userDetails(" +
                "_id INTEGER primary key autoincrement not null," +
                "header TEXT not null," +
                "description TEXT," +
                "status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userDetails");
    }

    public Boolean insertData(String header, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("header", header);
        contentValues.put("description", description);
        contentValues.put("status", "false");
        long result = db.insert("userDetails", null, contentValues);
        if (result == -1)
            return false;
        else return true;
    }

    public Boolean updateData(String id, String header, String description, Boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("header", header);
        contentValues.put("description", description);
        if (status) contentValues.put("status", "true");
        else contentValues.put("status", "false");

        Cursor cursor = db.rawQuery("select * from userDetails where _id = ?",
                new String[]{id});
        if (cursor.getCount() > 0) {
            long result = db.update("userDetails", contentValues,
                    "_id=?", new String[]{id});
            if (result == -1)
                return false;
            else return true;
        }
        return false;
    }
    public Boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userDetails where _id = ?",
                new String[]{id});
        if (cursor.getCount() > 0) {
            long result = db.delete("userDetails",
                    "_id=?", new String[]{id});
            if (result == -1)
                return false;
            else return true;
        }
        Log.println(Log.ASSERT, String.valueOf(1), "РўР°РєРѕРµ id РЅРµ РЅР°Р№РґРµРЅРѕ");
        return false;
    }
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userDetails",null);
        return cursor;
    }
    public Cursor getData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userDetails where _id = ?",new String[]{id});
        return cursor;
    }
    public void deleteTask(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tasks", "id = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }
}
