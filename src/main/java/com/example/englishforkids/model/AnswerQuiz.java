package com.example.englishforkids.model;

public class AnswerQuiz {
    private String idAnswerQuiz;
    private String content;
    private boolean isCorrect;
    private String idQuestionQuiz;

    public String getIdAnswerQuiz() {
        return idAnswerQuiz;
    }

    public void setIdAnswerQuiz(String idAnswerQuiz) {
        this.idAnswerQuiz = idAnswerQuiz;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getIdQuestionQuiz() {
        return idQuestionQuiz;
    }

    public void setIdQuestionQuiz(String idQuestionQuiz) {
        this.idQuestionQuiz = idQuestionQuiz;
    }
}

