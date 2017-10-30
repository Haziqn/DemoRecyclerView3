package com.example.haziq.demorecyclerview3;

import android.database.Cursor;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Haziq on 10/26/2017.
 */

public class GroupedCursor {

    public static String TAG = "GroupedCursor";

    Map<Integer, GroupPositionDetail> positionDetailMap = new HashMap<>();

    private Cursor cursorAllRows;
    private Cursor cursorGroups;
    private String columnName;
//    private GroupInfo groupInfo;

    Map<Integer, GroupInfo> groupInfoMap = new HashMap<>();

    public GroupedCursor(Cursor cursorAllRows, Cursor cursorGroups, String columnName) {
        this.cursorAllRows = cursorAllRows;
        this.cursorGroups = cursorGroups;
        this.columnName = columnName;

        Log.d(TAG, "constructor: :: columnName " + columnName);
    }

    public boolean isGroup(int position) {

        GroupPositionDetail positionDetail = getPositionDetail(position);
        return positionDetail.group;
//        GroupPositionDetail groupPositionDetail = positionDetailMap.get(position);
//        if (groupPositionDetail != null) {
//            Log.d(TAG, "isGroup: groupPositionDetail is not null");
//            if (groupPositionDetail.group) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        return false;
    }

//    private static void getType(int position) {
//        GroupPositionDetail positionDetail = positionDetailMap.get(position);
//        if(positionDetail != null){
//            Log.d(TAG, "getType: ");
//            System.out.println( position + "\t" +positionDetail.group);
//            return;
//        }
//
//        getPositionDetail(position);
//
//        System.out.println(position + "\t" + positionDetailMap.get(position));
//
//    }
//
//    private static void getItemOrGroup(int position) {
//        GroupPositionDetail positionDetail = positionDetailMap.get(position);
//        if(positionDetail != null){
//            Log.d(TAG, "getItemOrGroup: ");
//            if (positionDetail.group){
//                Log.d(TAG, "getItemOrGroup: ::group ");
//                cursorGroups.moveToPosition(positionDetail.realPosition);
//                int columnIndex = cursorGroups.getColumnIndex("COUNT(" + columnName + ")");
//                System.out.println(position + "\t" + cursorGroups.getInt(columnIndex));
//            }else{
//                Log.d(TAG, "getItemOrGroup: item");
//                cursorAllRows.moveToPosition(positionDetail.realPosition);
//                int columnIndex = cursorAllRows.getColumnIndex(columnName);
//                System.out.println(position + "\t" + cursorAllRows.getString(columnIndex));
//            }
//
//            return;
//        }
//        getPositionDetail(position);
//
//        GroupPositionDetail pd = positionDetailMap.get(position);
//        if (pd.group){
//            Log.d(TAG, "getItemOrGroup: ");
//            cursorGroups.moveToPosition(pd.realPosition);
//            int columnIndex = cursorGroups.getColumnIndex("COUNT(" + columnName + ")");
//            System.out.println(position + "\t" + cursorGroups.getInt(columnIndex));
//        }else{
//            Log.d(TAG, "getItemOrGroup: ");
//            cursorAllRows.moveToPosition(positionDetail.realPosition);
//            int columnIndex = cursorAllRows.getColumnIndex(columnName);
//            System.out.println(position + "\t" + cursorAllRows.getString(columnIndex));
//        }
//    }

    private GroupPositionDetail getPositionDetail(int position) {

        GroupPositionDetail groupPositionDetail = positionDetailMap.get(position);
        if (groupPositionDetail != null) {
            return groupPositionDetail;
        }

        int total = 0;
        int groupIndex = 0;
        boolean isGroup = false;

        for (; groupIndex < cursorGroups.getCount(); groupIndex++) {

            GroupInfo groupInfo = groupInfoMap.get(groupIndex);
            if (groupInfo == null) {
//                cursorGroups.moveToPosition(groupIndex);
                groupInfo = new GroupInfo(cursorGroups, groupIndex, columnName);
                groupInfoMap.put(groupIndex,groupInfo);
                Log.d(TAG, "getPositionDetail: creating new groupInfo");
            }

            if (position == total) {
                Log.d(TAG, "getPositionDetail: ::position isGroup " + position);
                isGroup = true;
                break;
            }

            Log.d(TAG, "getPositionDetail: item count in group " + groupInfo.getCount());

            total += groupInfo.getCount() + 1;
            Log.d(TAG, "getPositionDetail: ::total count : " + total);

            if (position < total) {
                Log.d(TAG, "getPositionDetail: ::position : " + position);
                break;
            }

        }

        if (isGroup) {

            Log.d(TAG, "getPositionDetail: isGroup");
            groupPositionDetail = new GroupPositionDetail(isGroup, groupIndex);
            positionDetailMap.put(position, groupPositionDetail);
        } else {

            Log.d(TAG, "getPositionDetail: isItem");
            groupPositionDetail = new GroupPositionDetail(isGroup, position - groupIndex - 1);
            positionDetailMap.put(position, groupPositionDetail);
        }
        return groupPositionDetail;
    }

    public Cursor moveToPosition(int position) {

//        int totalItemCount = 0;
//        if (isGroup(position)) {
//            if (cursorGroups != null) {
//                int total = 0;
//                int groupIndex = 0;
//
//                GroupPositionDetail groupPositionDetail = positionDetailMap.get(groupIndex);
//                cursorGroups.moveToPosition(groupPositionDetail.realPosition);
//
//                totalItemCount += groupInfo.getCount() + 1;
//                Log.d(TAG, "moveToPosition: ::totalItemCount : " + totalItemCount);
//                Log.d(TAG, "moveToPosition: ::current Position : " + position);
//
////                if (position == 0 || position == totalItemCount) {
////
////                    return cursorGroups;
////
////                }
//
//                groupIndex++;
//                Log.d(TAG, "moveToPosition: ::groupIndex : " + groupIndex);
//                total += groupInfo.getCount();
//
//                return cursorGroups;
//
//            }
//        } else {
//            cursorAllRows.moveToPosition(position);
//            return cursorAllRows;
//        }
//        return null;

        GroupPositionDetail positionDetail = getPositionDetail(position);
        if(positionDetail.group){
            cursorGroups.moveToPosition(positionDetail.realPosition);
            return cursorGroups;
        }else {
            cursorAllRows.moveToPosition(positionDetail.realPosition);
            return cursorAllRows;
        }

    }

//    public int getItemViewType(int position) {
//
//        int totalItemCount = 0;
//
//        if (cursorGroups != null && cursorGroups.moveToFirst()) {
//            int total = 0;
//            do {
//                int columnIndex = cursorGroups.getColumnIndex("COUNT(" + columnName + ")");
//
//                totalItemCount += cursorGroups.getInt(columnIndex) + 1;
//
//                if (position == 0 || position == totalItemCount) {
//
//                    return 0;
//                }
//
//                total += cursorGroups.getInt(columnIndex);
//            } while (cursorGroups.moveToNext());
//        }
//        return 1;
//    }


}
