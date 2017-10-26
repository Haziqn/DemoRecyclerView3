package com.example.haziq.demorecyclerview3;

/**
 * Created by Haziq on 10/19/2017.
 */

public class File { private String uri;
    private String group_name;

    public File() {
    }

    public File(String uri, String group_name) {
        this.uri = uri;
        this.group_name = group_name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}