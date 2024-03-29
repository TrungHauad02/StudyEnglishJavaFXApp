package com.example.englishforkids.model;

import java.util.Date;

public class Lesson {
    private String idLesson;
    private String name;
    private String description;
    private Date createDay;
    private LessonStatus status;

    public enum LessonStatus {
        LOCK,
        UNLOCK,
        HIDDEN
    }

    public String getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(String idLesson) {
        this.idLesson = idLesson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }
}