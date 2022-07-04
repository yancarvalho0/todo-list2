package br.edu.ifsuldeminas.tarefas2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.tarefas2.domain.Category;
import br.edu.ifsuldeminas.tarefas2.domain.Task;

public class CategoriesDAO extends DAO<Category> {

    public CategoriesDAO(Context context){
        super(context);
    }

    @Override
    public void save(Category entity) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", entity.getNome());

        database.insert("categories", null, contentValues);

        database.close();
    }

    @Override
    public void update(Category entity) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", entity.getNome());

        String[] params = {entity.getId().toString()};
        database.update("categories", contentValues,
                "id = ?", params);

        database.close();
    }

    @Override
    public void delete(Category entity) {
        SQLiteDatabase database = openToWrite();
        String[] params = {entity.getId().toString()};
        database.delete("categories", " id = ? ", params);

        database.close();
    }

    @Override
    public List<Category> listAll() {
        SQLiteDatabase database = openToRead();
        List<Category> categories = new ArrayList<>();

        String sql = " SELECT * FROM categories ";

        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String nome = cursor.getString(
                    cursor.getColumnIndexOrThrow("nome"));
            Category category = new Category(id, nome);
            categories.add(category);
        }

        cursor.close();
        database.close();

        return categories;
    }
}
