package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.GetResourceController;
import com.example.englishforkids.MainController;
import com.example.englishforkids.feature.ChangeMainPane;
import com.example.englishforkids.feature.ShowNewScene;
import com.example.englishforkids.model.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LessonViewController implements ChangeMainPane {
    private MainViewController mainViewController;
    public static Lesson curLesson;
    @FXML
    private Pane paneLesson;
    @FXML
    Pane paneUsername;
    @FXML
    Label lblUsername;
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
        MainViewController.createPaneUsername(lblUsername, paneUsername);
        btnVocabulary.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("vocabulary_lesson_view.fxml"));
                Pane pane = loader.load();
                mainViewController.onPaneChange(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    @Override
    public void setMainViewController(MainViewController mainViewController){
        this.mainViewController = mainViewController;
    }
}
