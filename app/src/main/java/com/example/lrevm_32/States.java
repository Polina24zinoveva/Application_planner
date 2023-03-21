package com.example.lrevm_32;

public class States {

    private String time;
    private String note;
    private String fullDate;

    public States(String time, String note, String fullDate){

        this.time = time;
        this.note = note;
        this.fullDate = fullDate;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFullDate() {
        return this.fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }


}
