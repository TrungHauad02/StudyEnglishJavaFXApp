package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.dao.VocabularyDAO;
import com.example.englishforkids.model.Lesson;
import com.example.englishforkids.model.Vocabulary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;

public class VocabularyLessonViewController {
    private Lesson lesson;
    private List<Vocabulary> lstVocabulary;
    private VocabularyDAO vocabularyDAO;
    @FXML
    private Label txtWord;
    @FXML
    private Label txtMean;
    @FXML
    private Label txtPhonetic;
    @FXML
    private Label txtSynonyms;
    @FXML
    private Label txtAntonyms;
    @FXML
    private Button btnPrevious;
    @FXML
    private Button btnNext;
    @FXML
    private ImageView imgWord;
    private int index;
    @FXML
    Label lblUsername;
    @FXML
    Pane paneUsername;
    @FXML
    ImageView imgAvatar;
    public VocabularyLessonViewController() {
    }

    public void initialize(){
        MainViewController.createPaneUsername(lblUsername, paneUsername,imgAvatar);
        lesson = LessonViewController.curLesson;
        index = 0;
        vocabularyDAO = new VocabularyDAO();
        lstVocabulary = vocabularyDAO.selectBySql(VocabularyDAO.SELECT_ALL_VOCABULARY_IN_LESSON_QUERY, lesson.getIdLesson());
        getAntonyms();
        getSynonyms();
        loadScene();
        btnPrevious.setOnAction(event -> {
            if(index >= 1){
                index--;
                loadScene();
            }
        });
        btnNext.setOnAction(event -> {
            if(index < lstVocabulary.size()-1){
                index++;
                loadScene();
            }
        });
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    private void getAntonyms(){
        for (Vocabulary vocabulary: lstVocabulary) {
            vocabulary.setAntonyms(vocabularyDAO.selectBySql(VocabularyDAO.SELECT_ALL_ANTONYMS_VOCABULARY_QUERY,
                    vocabulary.getIdVocabulary()));
        }
    }

    private void getSynonyms(){
        for (Vocabulary vocabulary: lstVocabulary) {
            vocabulary.setSynonyms(vocabularyDAO.selectBySql(VocabularyDAO.SELECT_ALL_SYNONYMS_VOCABULARY_QUERY,
                    vocabulary.getIdVocabulary()));
        }
    }

    private String getStringFromListVocabulary(List<Vocabulary> lst){
        StringBuilder stringBuilder = new StringBuilder();
        for (Vocabulary vocabulary: lst) {
            stringBuilder.append(vocabulary.getWord()).append(" ");
        }
        return stringBuilder.toString();
    }

    private void loadScene() {
        Vocabulary vocabulary = lstVocabulary.get(index);
        txtWord.setText(vocabulary.getWord());
        txtMean.setText(vocabulary.getMean());
        txtPhonetic.setText(vocabulary.getPhonetic());
        String antonyms = getStringFromListVocabulary(vocabulary.getAntonyms());
        String synonyms = getStringFromListVocabulary(vocabulary.getSynonyms());
        txtAntonyms.setText(antonyms);
        txtSynonyms.setText(synonyms);
        byte[] decodedImage = Base64.getDecoder().decode(vocabulary.getImage());
        Image image = new Image(new ByteArrayInputStream(decodedImage));
        imgWord.setImage(image);
    }
}
