package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.GetResourceController;
import com.example.englishforkids.feature.ShowNewScene;
import com.example.englishforkids.model.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

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
            FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("vocabulary_view.fxml"));
            ShowNewScene.show(loader, "Vocabulary");
        });
        btnSpeaking.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("speaking_view.fxml"));
            ShowNewScene.show(loader, "Speaking");
        });
        btnGrammar.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("grammar_view.fxml"));
            ShowNewScene.show(loader, "Grammar");
        });
        btnQuiz.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("quiz_view.fxml"));
            ShowNewScene.show(loader, "Quiz");
        });
        btnListening.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("listening_view.fxml"));
            ShowNewScene.show(loader, "Listening");
        });
    }
}
