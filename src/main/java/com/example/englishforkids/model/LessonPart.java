package com.example.englishforkids.model;

public class LessonPart {
    private String idLessonPart;
    private LessonPartType type;
    private String idLesson;

    public enum LessonPartType {
        VOCABULARY,
        LISTENING,
        SPEAKING,
        QUIZ,
        GRAMMAR
    }

    public String getIdLessonPart() {
        return idLessonPart;
    }

    public void setIdLessonPart(String idLessonPart) {
        this.idLessonPart = idLessonPart;
    }

    public LessonPartType getType() {
        return type;
    }

    public void setType(LessonPartType type) {
        this.type = type;
    }

    public String getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(String idLesson) {
        this.idLesson = idLesson;
    }
}
