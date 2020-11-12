package com.dmyaniuk.lb_3;

import java.util.Date;

public class Note {
    private String note;
    private Date date;

    public Note(String note, Date date) {
        this.note = note;
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
