package com.example.englishforkids;

import com.example.englishforkids.model.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LessonViewController {
    public static Lesson curLesson;
    @FXML
    private Pane paneLesson;
    @FXML
    private Button btnVocabulary;
    @FXML
    private Button btnSpeaking;
    @FXML
    private Button btnGrammar;
    @FXML
    private Button btnQuiz;
    @FXML
    private Button btnListening;
    public void initialize(){
        btnVocabulary.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vocabulary_view.fxml"));
            ShowNewScene.show(loader, "Vocabulary");
        });
        btnSpeaking.setOnAction(event -> {

        });
        btnGrammar.setOnAction(event -> {

        });
        btnQuiz.setOnAction(event -> {

        });
        btnListening.setOnAction(event -> {

        });
    }
}
