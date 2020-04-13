package com.khodko.organizer.model;

public class Pair {

    private Integer weekDay;
    private Integer num;
    private String teacher;
    private String lesson;
    private String pairType;
    private String cabinet;

    public Pair () {

    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    @Override
    public String toString() {
        return " \nПара: " + num + "\n" +
                "\tпредмет: " + lesson + " (" + pairType + ")\n" +
                "\tпреподаватель: " + teacher + "\n" +
                "\tкабинет: " + cabinet + "\n";
    }
}
