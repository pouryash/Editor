package com.example.pourya.editor;


public class Note {


    private String subject;
    private String content;
    private int id;

    public Note(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

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
