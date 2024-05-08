package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.dao.VocabularyDAO;
import com.example.englishforkids.model.Vocabulary;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;

public class VocabularyViewController {
    @FXML
    Label txtWord;
    @FXML
    Label txtMean;
    @FXML
    Label txtPhonetic;
    @FXML
    Label txtSynonyms;
    @FXML
    Label txtAntonyms;
    @FXML
    ImageView imgWord;
    private Vocabulary vocabulary;
    VocabularyDAO vocabularyDAO;
    public void initialize() {
        vocabularyDAO = new VocabularyDAO();
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
        getAntonyms();
        getSynonyms();
        loadScene();
    }
    private void loadScene(){
        txtWord.setText(vocabulary.getWord());
        txtMean.setText(vocabulary.getMean());
        txtPhonetic.setText(vocabulary.getPhonetic());
        String antonyms = getStringFromListVocabulary(vocabulary.getAntonyms());
        String synonyms = getStringFromListVocabulary(vocabulary.getSynonyms());
        txtAntonyms.setText(antonyms);
        txtSynonyms.setText(synonyms);
        if(vocabulary.getImage()!=null){
            byte[] decodedImage = Base64.getDecoder().decode(vocabulary.getImage());
            Image image = new Image(new ByteArrayInputStream(decodedImage));
            imgWord.setImage(image);
        }else{
            imgWord.setImage(null);
        }
    }
    private String getStringFromListVocabulary(List<Vocabulary> lst){
        StringBuilder stringBuilder = new StringBuilder();
        for (Vocabulary vocabulary: lst) {
            stringBuilder.append(vocabulary.getWord()).append(" ");
        }
        return stringBuilder.toString();
    }
    private void getAntonyms(){
        vocabulary.setAntonyms(vocabularyDAO.selectBySql(VocabularyDAO.SELECT_ALL_ANTONYMS_VOCABULARY_QUERY,
                vocabulary.getIdVocabulary(),vocabulary.getIdVocabulary()));
    }

    private void getSynonyms(){
        vocabulary.setSynonyms(vocabularyDAO.selectBySql(VocabularyDAO.SELECT_ALL_SYNONYMS_VOCABULARY_QUERY,
                vocabulary.getIdVocabulary(),vocabulary.getIdVocabulary()));
    }
}
