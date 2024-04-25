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
    private Lesson lesson;
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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("vocabulary_view.fxml"));
                Parent root = loader.load();

                VocabularyViewController controller = loader.getController();
                controller.setLesson(lesson);

                Scene scene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
        Label lblTile = (Label) paneLesson.getChildren().get(0);
        lblTile.setText(lesson.getName());
    }
}
