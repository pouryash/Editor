package com.example.pourya.editor;



public class Note {


    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_CONTENT = "content";
    private String subject;
    private String content;
    private int id;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SUBJECT + " TEXT,"
                    + COLUMN_CONTENT + " TEXT "
                    + ")";

    public Note() {
    }

    public Note(String subject, String content, int id) {
        this.subject = subject;
        this.content = content;
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
