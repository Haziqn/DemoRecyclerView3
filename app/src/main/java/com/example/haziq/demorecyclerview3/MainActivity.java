package com.example.haziq.demorecyclerview3;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";

    RecyclerView recyclerView;
    Button buttonInsert, buttonQuery;
    CustomAdapter customAdapter;

    Cursor cursorGroupCount = null;
    Cursor cursorAll = null;

    int totalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonQuery = (Button) findViewById(R.id.buttonQuery);

        String[] projection = {"COUNT(" + FileSQLiteHelper.COLUMN_FILE + ")", FileSQLiteHelper.COLUMN_GROUP};
        //retrieving how many files there are in each group
        cursorGroupCount = getContentResolver().query(Uri.parse("content://com.example.haziq.demorecyclerview3.FileContentProvider/files/1"), projection, null, null, FileSQLiteHelper.COLUMN_GROUP + " ASC");
//        Log.d(TAG, "onCreate: cursorGroupCount " + cursorGroupCount.getCount());
//        //retrieving all files from each group
        cursorAll = managedQuery(FileContentProvider.CONTENT_URI, FileSQLiteHelper.allColumns, null, null, FileSQLiteHelper.COLUMN_GROUP + " ASC");
        Log.d(TAG, "onCreate: cursorAll count " + cursorAll.getCount());
//
        //retrieve item count in each group
        if (cursorGroupCount != null && cursorGroupCount.moveToFirst()) {
            do {

                String[] columns = cursorGroupCount.getColumnNames();

                for (String column:
                        columns) {
//                    Log.d(TAG, "onCreate: column " + column);
                }

                int columnIndex = cursorGroupCount.getColumnIndex("COUNT(" + FileSQLiteHelper.COLUMN_FILE + ")");
//                Log.d(TAG, "onCreate: column Index " + columnIndex);
                int itemCountInGroup = cursorGroupCount.getInt(columnIndex);
                totalCount += itemCountInGroup;
//                Log.d(TAG, "onCreate: ::retrieving item count in group : " + itemCountInGroup);

            } while (cursorGroupCount.moveToNext());
        }
        totalCount += cursorGroupCount.getCount();
////        Log.d(TAG, "onCreate: ::total count : " + totalCount);
//
//        if (cursorAll != null && cursorAll.moveToFirst()) {
//            do {
//                int columnIndex = cursorAll.getColumnIndex(FileSQLiteHelper.COLUMN_FILE );
//
//                String itemInGroup = cursorAll.getString(columnIndex);
//
////                Log.d(TAG, "onCreate: ::retrieving item in group : " +  itemInGroup);
//
//            } while (cursorAll.moveToNext());
//        }

        customAdapter = new CustomAdapter(cursorGroupCount, cursorAll, totalCount);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layout = new GridLayoutManager(MainActivity.this, 4);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(customAdapter.getItemViewType(position)){
                    case CustomAdapter.GROUP_TYPE:
                        return 4;
                    case CustomAdapter.ITEM_TYPE:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(customAdapter);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.photot222)
                        + '/' + getResources().getResourceTypeName(R.drawable.photot222) + '/' + getResources().getResourceEntryName(R.drawable.photot222));
                Uri imageUri1 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.photo321)
                        + '/' + getResources().getResourceTypeName(R.drawable.photo321) + '/' + getResources().getResourceEntryName(R.drawable.photo321));
                Uri imageUri2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.photo1)
                        + '/' + getResources().getResourceTypeName(R.drawable.photo1) + '/' + getResources().getResourceEntryName(R.drawable.photo1));
                Uri imageUri3 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.photo12)
                        + '/' + getResources().getResourceTypeName(R.drawable.photo12) + '/' + getResources().getResourceEntryName(R.drawable.photo12));
                Uri imageUri4 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.photo1234)
                        + '/' + getResources().getResourceTypeName(R.drawable.photo1234) + '/' + getResources().getResourceEntryName(R.drawable.photo1234));
                Uri imageUri5 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.photo213)
                        + '/' + getResources().getResourceTypeName(R.drawable.photo213) + '/' + getResources().getResourceEntryName(R.drawable.photo213));
                Uri imageUri6 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.photo321)
                        + '/' + getResources().getResourceTypeName(R.drawable.photo321) + '/' + getResources().getResourceEntryName(R.drawable.photo321));
                Uri imageUri7 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.photo333)
                        + '/' + getResources().getResourceTypeName(R.drawable.photo333) + '/' + getResources().getResourceEntryName(R.drawable.photo333));

                String uri = imageUri.toString();
                String uri1 = imageUri1.toString();
                String uri2 = imageUri2.toString();
                String uri3 = imageUri3.toString();
                String uri4 = imageUri4.toString();
                String uri5 = imageUri5.toString();
                String uri6 = imageUri6.toString();
                String uri7 = imageUri7.toString();

                ContentValues values = new ContentValues();
                ContentValues values1 = new ContentValues();
                ContentValues values2 = new ContentValues();
                ContentValues values3 = new ContentValues();
                ContentValues values4 = new ContentValues();
                ContentValues values5 = new ContentValues();
                ContentValues values6 = new ContentValues();
                ContentValues values7 = new ContentValues();

                values.put(FileSQLiteHelper.COLUMN_GROUP, "A");
                values.put(FileSQLiteHelper.COLUMN_FILE, uri);

                values1.put(FileSQLiteHelper.COLUMN_FILE, uri1);
                values1.put(FileSQLiteHelper.COLUMN_GROUP, "A");

                values2.put(FileSQLiteHelper.COLUMN_FILE, uri2);
                values2.put(FileSQLiteHelper.COLUMN_GROUP, "A");

                values3.put(FileSQLiteHelper.COLUMN_FILE, uri3);
                values3.put(FileSQLiteHelper.COLUMN_GROUP, "A");

                values4.put(FileSQLiteHelper.COLUMN_FILE, uri4);
                values4.put(FileSQLiteHelper.COLUMN_GROUP, "B");

                values5.put(FileSQLiteHelper.COLUMN_FILE, uri5);
                values5.put(FileSQLiteHelper.COLUMN_GROUP, "B");

                values6.put(FileSQLiteHelper.COLUMN_FILE, uri6);
                values6.put(FileSQLiteHelper.COLUMN_GROUP, "C");

                values7.put(FileSQLiteHelper.COLUMN_FILE, uri7);
                values7.put(FileSQLiteHelper.COLUMN_GROUP, "C");

                Log.d(TAG, "onClick: ::inserting contentValues :" + values);

                Uri insertUri = getContentResolver().insert(
                        FileContentProvider.CONTENT_URI, values);
                Uri insertUri1 = getContentResolver().insert(
                        FileContentProvider.CONTENT_URI, values1);
                Uri insertUri2 = getContentResolver().insert(
                        FileContentProvider.CONTENT_URI, values2);
                Uri insertUri3 = getContentResolver().insert(
                        FileContentProvider.CONTENT_URI, values3);
                Uri insertUri4 = getContentResolver().insert(
                        FileContentProvider.CONTENT_URI, values4);
                Uri insertUri5 = getContentResolver().insert(
                        FileContentProvider.CONTENT_URI, values5);
                Uri insertUri6 = getContentResolver().insert(
                        FileContentProvider.CONTENT_URI, values6);
                if (insertUri != null && insertUri1 != null && insertUri2 != null && insertUri3 != null && insertUri4 != null && insertUri5 != null && insertUri6 != null) {
                    Toast.makeText(MainActivity.this, "successfully inserted rows", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(customAdapter);

            }
        });
    }
}
