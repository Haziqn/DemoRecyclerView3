package com.example.haziq.demorecyclerview3;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by Haziq on 10/27/2017.
 */

public class GroupInfo {

    public static String TAG = "GroupInfo";
    int count = -1;
    Cursor cursor;
    int position;
    private String groupColumnName;

    public GroupInfo(Cursor cursor, int position, String groupColumnName) {
        this.cursor = cursor;
        this.position = position;
        this.groupColumnName = groupColumnName;
    }

    public int getCount() {
        int columnIndex = 0;
        if (count < 0) {
            cursor.moveToPosition(position);
            Log.d(TAG, "getCount: ::position in getCount(): " + position);
            columnIndex = cursor.getColumnIndex("COUNT(" + groupColumnName + ")");
            Log.d(TAG, "getCount: ::columnIndex " + columnIndex);
            count = cursor.getInt(columnIndex);
            return count;
        }
        return count;
    }

//    public void getGroupInfoDetail() {}

}
