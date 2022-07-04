package br.edu.ifsuldeminas.tarefas2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tarefas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS =
            " CREATE TABLE IF NOT EXISTS tasks ( " +
            " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " description TEXT NOT NULL, " +
            " active VARCHAR(1) NOT NULL," +
            "category_id INTEGER, " +
             "FOREIGN KEY (category_id) REFERENCES categories(id)" +
            ");";

    private static final String TABLE_CATEGORY =
            " CREATE TABLE IF NOT EXISTS categories ( " +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    " nome TEXT NOT NULL " +
                    ");";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CATEGORY);
        sqLiteDatabase.execSQL(TABLE_TASKS);
    }
    public void onDelete(@NonNull SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CATEGORY);
        sqLiteDatabase.execSQL(TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // não vamos tratar alterações
    }
}
