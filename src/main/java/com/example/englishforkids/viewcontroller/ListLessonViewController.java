package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.GetResourceController;
import com.example.englishforkids.dao.LessonDAO;
import com.example.englishforkids.feature.ChangeMainPane;
import com.example.englishforkids.model.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListLessonViewController implements ChangeMainPane {
    private MainViewController mainViewController;
    @FXML
    private Pane paneContainer;
    @FXML
    Label lblUsername;
    @FXML
    Pane paneUsername;
    @FXML
    ImageView imgAvatar;
    @FXML
    Button btnNext;
    @FXML
    Button btnPrevious;
    private List<List<Lesson>> lessonGroups;
    private int currentGroupIndex;
    public void initialize() {
        MainViewController.createPaneUsername(lblUsername, paneUsername, imgAvatar);

        LessonDAO lessonDAO = new LessonDAO();
        List<Lesson> lstLesson = new LinkedList<Lesson>();
        lstLesson = lessonDAO.selectAll();
        for(Lesson lesson: lstLesson){
            if(!lesson.getStatus().equals(Lesson.LessonStatus.UNLOCK))
                lstLesson.remove(lesson);
        }
        lessonGroups = new ArrayList<>();
        for (int i = 0; i < lstLesson.size(); i += 12) {
            int endIndex = Math.min(i + 12, lstLesson.size());
            lessonGroups.add(new ArrayList<>(lstLesson.subList(i, endIndex)));
        }
        currentGroupIndex = 0;
        displayLessonGroup(currentGroupIndex);

        btnNext.setOnAction(event -> {
            if (currentGroupIndex < lessonGroups.size() - 1) {
                currentGroupIndex++;
                displayLessonGroup(currentGroupIndex);
            }
        });

        btnPrevious.setOnAction(event -> {
            if (currentGroupIndex > 0) {
                currentGroupIndex--;
                displayLessonGroup(currentGroupIndex);
            }
        });
    }

    private void displayLessonGroup(int groupIndex) {
        paneContainer.getChildren().clear();

        List<Lesson> currentGroup = lessonGroups.get(groupIndex);
        double initialX = 228.0;
        double initialY = 69.0;
        double deltaX = 131.0;
        double deltaY = 111.0;

        for (int i = 0; i < currentGroup.size(); i++) {
            Lesson lesson = currentGroup.get(i);
            Pane lessonPane = createLessonPane(lesson);

            double layoutX = initialX + (i % 4) * deltaX;
            double layoutY = initialY + (i / 4) * deltaY;

            lessonPane.setLayoutX(layoutX);
            lessonPane.setLayoutY(layoutY);

            paneContainer.getChildren().add(lessonPane);
        }
    }
    @Override
    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
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
            try {
                FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("lesson_view.fxml"));
                LessonViewController.curLesson = lesson;
                Pane newPane = loader.load();
                ChangeMainPane controller = loader.getController();
                controller.setMainViewController(this.mainViewController);
                mainViewController.onPaneChange(newPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return pane;
    }
}



