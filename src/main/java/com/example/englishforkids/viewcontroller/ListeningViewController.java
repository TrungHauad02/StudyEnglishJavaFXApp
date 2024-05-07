package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.GetResourceController;
import com.example.englishforkids.dao.ListeningDAO;
import com.example.englishforkids.feature.DataUpdateListener;
import com.example.englishforkids.model.Lesson;
import com.example.englishforkids.model.Listening;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListeningViewController {
    @FXML
    TextArea txtDescription;
    @FXML
    Button btnDetail;
    @FXML
    Button btnVideo;
    @FXML
    Button btnScript;
    @FXML
    Button btnPrevious;
    @FXML
    Button btnNext;
    @FXML
    Label lblTitle;
    @FXML
    Label lblLesson;
    @FXML
    Pane paneContainerListening;
    @FXML
    Label lblUsername;
    @FXML
    Pane paneUsername;
    @FXML
    ImageView imgAvatar;
    private List<Listening> lstListening;
    private int index;
    ListeningDAO listeningDAO;
    private final List<DataUpdateListener> subscribers = new ArrayList<>();
    public void initialize(){
        MainViewController.createPaneUsername(lblUsername, paneUsername, imgAvatar);
        txtDescription.setWrapText(true);
        Lesson lesson = LessonViewController.curLesson;
        listeningDAO = new ListeningDAO();
        lstListening = listeningDAO.selectBySql(ListeningDAO.SELECT_LISTENING_FROM_LESSON, lesson.getIdLesson());
        index = 0;
        updateButtonColors(btnDetail);
        loadPane(GetResourceController.getFXMLResourcePath("listening_detail_view.fxml"));
        btnPrevious.setOnAction(event -> {
            if(index >= 1){
                index--;
                notifySubscribers();
            }
        });
        btnNext.setOnAction(event -> {
            if(index < lstListening.size()-1){
                index++;
                notifySubscribers();
            }
        });
        btnScript.setOnAction(event -> {
            updateButtonColors(btnScript);
            loadPane(GetResourceController.getFXMLResourcePath("listening_script_view.fxml"));
        });
        btnVideo.setOnAction(event -> {
            updateButtonColors(btnVideo);
            loadPane(GetResourceController.getFXMLResourcePath("listening_video_view.fxml"));
        });
        btnDetail.setOnAction(event -> {
            updateButtonColors(btnDetail);
            loadPane(GetResourceController.getFXMLResourcePath("listening_detail_view.fxml"));
        });
    }
    private void loadPane(URL urlPane){
        try {
            FXMLLoader loader = new FXMLLoader(urlPane);
            Pane pane = loader.load();

            paneContainerListening.getChildren().clear();
            paneContainerListening.getChildren().add(pane);
            DataUpdateListener controller = loader.getController();
            subscribers.clear();
            subscribers.add(controller);
            notifySubscribers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateButtonColors(Button selectedButton) {
        btnScript.setTextFill(btnScript == selectedButton ? Color.RED : Color.BLACK);
        btnVideo.setTextFill(btnVideo == selectedButton ? Color.RED : Color.BLACK);
        btnDetail.setTextFill(btnDetail == selectedButton ? Color.RED : Color.BLACK);
    }
    private void notifySubscribers() {
        for (DataUpdateListener listener : subscribers) {
            listener.onUpdateData(lstListening, index);
        }
    }
}
