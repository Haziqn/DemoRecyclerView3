package com.example.haziq.demorecyclerview3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Haziq on 10/19/2017.
 */

public class FileSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_GROUPS = "files_db";
    public static final String COLUMN_GROUP = "groupName_";
    public static final String COLUMN_FILE = "file_";

    public static final String[] allColumns = {
            COLUMN_GROUP,
            COLUMN_FILE
    };

    public static final String CREATE_DATABASE = "CREATE TABLE " + TABLE_GROUPS + "("
            + COLUMN_GROUP + " String,"
            + COLUMN_FILE + " String)";

    public static final int DATABASE_VERSION = 3;

    public FileSQLiteHelper(Context context) {
        super(context, TABLE_GROUPS, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {sqLiteDatabase.execSQL(CREATE_DATABASE);}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS " + TABLE_GROUPS);
        onCreate(sqLiteDatabase);
    }
}
