package com.example.bhairavpardiwala.helloworldiot;

/**
 * Created by Bhairav Pardiwala on 28-10-2017.
 */

public class Note {
    public int id;

    public Note()
    {

    }
    public Note(int id, String note, boolean done) {
        this.id = id;
        this.note = note;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String note;
    public boolean done;

}
