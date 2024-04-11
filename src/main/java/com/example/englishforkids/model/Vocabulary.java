package com.example.englishforkids.model;

public class Vocabulary {
    private String idVocabulary;
    private String word;
    private String mean;
    private byte[] image;
    private String phonetic;
    private Vocabulary[] Antonyms;
    private Vocabulary[] Synonyms;

    public String getIdVocabulary() {
        return idVocabulary;
    }

    public void setIdVocabulary(String idVocabulary) {
        this.idVocabulary = idVocabulary;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }
}
