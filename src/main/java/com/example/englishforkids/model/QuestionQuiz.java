package com.example.englishforkids.model;

import java.util.List;

public class QuestionQuiz {
    private String idQuestionQuiz;
    private String content;
    private int serial;
    private byte[] image;
    private String idQuiz;
    private List<AnswerQuiz> lstAnswers;
    public String getIdQuestionQuiz() {
        return idQuestionQuiz;
    }

    public void setIdQuestionQuiz(String idQuestionQuiz) {
        this.idQuestionQuiz = idQuestionQuiz;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(String idQuiz) {
        this.idQuiz = idQuiz;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<AnswerQuiz> getLstAnswers() {
        return lstAnswers;
    }

    public void setLstAnswers(List<AnswerQuiz> lstAnswers) {
        this.lstAnswers = lstAnswers;
    }
}
