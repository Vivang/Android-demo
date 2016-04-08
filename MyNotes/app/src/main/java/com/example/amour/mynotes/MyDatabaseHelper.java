package com.example.amour.mynotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amour on 2016/4/7.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    private static final String CREAT_NOTE="create table Note("
            +"NoteId integer primary key autoincrement,"
            +"NoteTitle text,"
            +"NoteText text,"
            +"NoteTime text)";
    private static final String CREAT_DELETENOTE="create table DeleteNote("
            +"NoteId integer primary key autoincrement,"
            +"NoteTitle text,"
            +"NoteText text,"
            +"NoteTime text)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_NOTE);
        db.execSQL(CREAT_DELETENOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exit Note");
        db.execSQL("drop table if exit DeleteNote");
        onCreate(db);
    }
}
