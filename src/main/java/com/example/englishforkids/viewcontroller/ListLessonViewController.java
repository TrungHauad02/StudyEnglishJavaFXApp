package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.GetResourceController;
import com.example.englishforkids.feature.ShowNewScene;
import com.example.englishforkids.dao.LessonDAO;
import com.example.englishforkids.model.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class ListLessonViewController {
    @FXML
    private Pane paneContainer;
    public void initialize() {
        LessonDAO lessonDAO = new LessonDAO();
        List<Lesson> lstLesson = new LinkedList<Lesson>();
        lstLesson = lessonDAO.selectAll();
        double initialX = 228.0;
        double initialY = 89.0;
        double deltaX = 131.0;
        double deltaY = 111.0;
        for (int i = 0; i < lstLesson.size() || i < 12; i++) {
            Lesson lesson = lstLesson.get(i);
            Pane lessonPane = createLessonPane(lesson);

            double layoutX = initialX + (i % 4) * deltaX;
            double layoutY = initialY + (i / 4) * deltaY;

            lessonPane.setLayoutX(layoutX);
            lessonPane.setLayoutY(layoutY);

            paneContainer.getChildren().add(lessonPane);
        }
    }

    private Pane createLessonPane(Lesson lesson) {
        Pane pane = new Pane();
        pane.setPrefSize(110.0, 98.0);
        pane.setStyle("-fx-background-color: #D4C223; -fx-background-radius: 22; -fx-border-radius: 20; -fx-border-color: #ffffff; -fx-border-width: 5;");

        String nameLesson = "Lesson " + String.valueOf(lesson.getSerial());
        Label labelNumber = new Label(nameLesson);
        labelNumber.setLayoutX(27.0);
        labelNumber.setLayoutY(39.0);
        labelNumber.setTextFill(javafx.scene.paint.Color.WHITE);
        labelNumber.setFont(new javafx.scene.text.Font("System Bold", 14.0));

        ImageView imageView = new ImageView();
        imageView.setLayoutX(-2.0);
        imageView.setLayoutY(-3.0);
        imageView.setFitWidth(110.0);
        imageView.setFitHeight(70.0);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        String imgPath = "/img/graphic_advance.png";
        InputStream inputStream = getClass().getResourceAsStream(imgPath);
        imageView.setImage(new Image(inputStream));

        pane.getChildren().addAll(labelNumber, imageView);
        pane.setOnMouseClicked(event -> {
            Stage stage = (Stage) paneContainer.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("lesson_view.fxml"));
            LessonViewController.curLesson = lesson;
            ShowNewScene.show(loader, "Lesson");
        });
        return pane;
    }
}



