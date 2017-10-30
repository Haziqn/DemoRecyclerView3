package com.example.haziq.demorecyclerview3;

/**
 * Created by Haziq on 10/26/2017.
 */

public class GroupPositionDetail {

    public boolean group;
    public int realPosition;

    public GroupPositionDetail(boolean group, int realPosition) {
        this.group = group;
        this.realPosition = realPosition;
    }

    @Override
    public String toString() {
        return "PositionDetail{" +
                "group=" + group +
                ", realPosition=" + realPosition +
                '}';
    }

}
