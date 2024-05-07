package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.dao.GrammarDAO;
import com.example.englishforkids.model.Grammar;
import com.example.englishforkids.model.Lesson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;

public class GrammarViewController {
    private Lesson lesson;
    List<Grammar> lstGrammar;
    GrammarDAO grammarDAO;
    @FXML
    Label lblTitle;
    @FXML
    TextArea txtContent;
    @FXML
    TextArea txtRule;
    @FXML
    TextArea txtExample;
    @FXML
    ImageView imgExample;
    @FXML
    Button btnPrevious;
    @FXML
    Button btnNext;
    private int index;
    @FXML
    Label lblUsername;
    @FXML
    Pane paneUsername;
    public void initialize(){
        MainViewController.createPaneUsername(lblUsername, paneUsername);
        txtContent.setWrapText(true);
        txtRule.setWrapText(true);
        grammarDAO = new GrammarDAO();
        lesson = LessonViewController.curLesson;
        index = 0;
        this.lstGrammar = grammarDAO.selectBySql(GrammarDAO.SELECT_ALL_GRAMMAR_IN_LESSON_QUERY, lesson.getIdLesson());
        loadScene();
        btnPrevious.setOnAction(event -> {
            if(index >= 1){
                index--;
                loadScene();
            }
        });
        btnNext.setOnAction(event -> {
            if(index < lstGrammar.size()-1){
                index++;
                loadScene();
            }
        });
    }

    private void loadScene() {
        Grammar grammar = this.lstGrammar.get(index);
        lblTitle.setText(grammar.getTitle());
        txtContent.setText(grammar.getContent());
        txtRule.setText(grammar.getRule());
        txtExample.setText(grammar.getExample());
        byte[] decodedImage = Base64.getDecoder().decode(grammar.getImage());
        Image image = new Image(new ByteArrayInputStream(decodedImage));
        imgExample.setImage(image);
    }
}
