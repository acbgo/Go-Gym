package com.example.go_gym;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String Table_name = "users";

    public MyDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据表，暂定有Id，用户，年龄，身高，体重这5列
        String sql = "create table if not exists " + Table_name + " (Id integer primary key, user text, age integer, height integer, weight integer)";
        db.execSQL(sql);
    }

    //数据库升级，比如增加一个表这种情况
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
