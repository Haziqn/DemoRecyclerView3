package com.example.haziq.demorecyclerview3;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by Haziq on 10/19/2017.
 */

public class FileContentProvider extends ContentProvider {

    public static String TAG = "FileContentProvider";
    static final String PROVIDER_NAME = "com.example.haziq.demorecyclerview3.FileContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/files";
    static final Uri CONTENT_URI = Uri.parse(URL);

    private static HashMap<String, String> FILES_PROJECTION_MAP;

    static final int STUDENTS = 1;
    static final int STUDENT_ID = 2;
    static final UriMatcher uriMatcher;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "files", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "files/#", STUDENT_ID);
    }

    private SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        FileSQLiteHelper fileSQLiteHelper = new FileSQLiteHelper(context);
        sqLiteDatabase = fileSQLiteHelper.getWritableDatabase();
        return sqLiteDatabase!= null;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        Log.d(TAG, "attachInfo: " + context);
        Log.d(TAG, "attachInfo: " + info);
        super.attachInfo(context, info);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: ::uri : " + uri);

        SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
        sqb.setTables(FileSQLiteHelper.TABLE_GROUPS);

        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                sqb.setProjectionMap(FILES_PROJECTION_MAP);
                Cursor cursor = sqb.query(sqLiteDatabase, projection, selection,
                        selectionArgs,null, null, sortOrder);
                Log.d(TAG, "query: uri matches");
                return cursor;
            case STUDENT_ID:
                sqb.setProjectionMap(FILES_PROJECTION_MAP);
                Cursor cursor2 = sqb.query(sqLiteDatabase, projection, selection,
                        selectionArgs,FileSQLiteHelper.COLUMN_GROUP, null, sortOrder);
                Log.d(TAG, "query: uri2 matches");
                return cursor2;

        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "insert: ::uri : " + uri);

        Log.d(TAG, "insert: ::contentValues : " + contentValues);

        long rowId = sqLiteDatabase.insert(FileSQLiteHelper.TABLE_GROUPS, null, contentValues);

        Log.d(TAG, "insert: ::rowId : " + rowId);

        if(rowId > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            Log.d(TAG, "insert: ::_uri : " + _uri);
            return _uri;
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
