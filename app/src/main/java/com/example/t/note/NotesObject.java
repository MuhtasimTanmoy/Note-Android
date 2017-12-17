package com.example.t.note;

/**
 * Created by t on 11/21/17.
 */

public class NotesObject {
    private String id;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStarred(Boolean starred) {
        this.starred = starred;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getId() {
        return id;
    }

    private String title;
    private String content;
    private String color;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getColor() {
        return color;
    }

    public Boolean getStarred() {
        return starred;
    }

    private Boolean starred;

    public NotesObject(String title, String content, String color, Boolean starred) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.starred = starred;
    }
}
