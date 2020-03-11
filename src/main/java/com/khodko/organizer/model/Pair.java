package com.khodko.organizer.model;

public class Pair {

    private String teacher;
    private String lesson;
    private String pairType;
    private String cabinet;

    public Pair () {

    }

    public Pair(String teacher, String lesson, String pairType, String cabinet) {
        this.teacher = teacher;
        this.lesson = lesson;
        this.pairType = pairType;
        this.cabinet = cabinet;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getPairType() {
        return pairType;
    }

    public void setPairType(String pairType) {
        this.pairType = pairType;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }
}
