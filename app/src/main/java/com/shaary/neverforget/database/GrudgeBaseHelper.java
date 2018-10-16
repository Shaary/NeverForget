package com.shaary.neverforget.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shaary.neverforget.database.GrudgeDbSchema.GrudgeTable;
import com.shaary.neverforget.model.Grudge;

public class GrudgeBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "grudgeBase.db";

    public GrudgeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + GrudgeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                GrudgeTable.Cols.UUID + ", " +
                GrudgeTable.Cols.TITLE + ", " +
                GrudgeTable.Cols.DATE + ", " +
                GrudgeTable.Cols.TIME + ", " +
                GrudgeTable.Cols.REMIND + ", " +
                GrudgeTable.Cols.REVENGE + ", " +
                GrudgeTable.Cols.REVENGED + ", " +
                GrudgeTable.Cols.FORGIVE + ", " +
                GrudgeTable.Cols.VICTIM + ", " +
                GrudgeTable.Cols.DESCRIPTION + ", " +
                GrudgeTable.Cols.GENDER +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
