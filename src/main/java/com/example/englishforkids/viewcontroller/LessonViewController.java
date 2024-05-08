package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.GetResourceController;
import com.example.englishforkids.MainController;
import com.example.englishforkids.dao.*;
import com.example.englishforkids.feature.ChangeMainPane;
import com.example.englishforkids.feature.MessageBox;
import com.example.englishforkids.feature.ShowNewScene;
import com.example.englishforkids.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
    ImageView imgAvatar;
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
        MainViewController.createPaneUsername(lblUsername, paneUsername,imgAvatar);
        btnVocabulary.setOnAction(event -> {
            try {
                VocabularyDAO dao = new VocabularyDAO();
                List<Vocabulary> lst = dao.selectBySql(VocabularyDAO.SELECT_ALL_VOCABULARY_IN_LESSON_QUERY, LessonViewController.curLesson.getIdLesson());
                if (lst == null || lst.isEmpty()){
                    MessageBox.show("Thông báo","Phần học này vẫn chưa được thêm vào, bé học phần khác nhé", Alert.AlertType.CONFIRMATION);
                    return;
                }
                FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("vocabulary_lesson_view.fxml"));
                Pane pane = loader.load();
                mainViewController.onPaneChange(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnSpeaking.setOnAction(event -> {
            try {
                SpeakingDAO dao = new SpeakingDAO();
                List<Speaking> lst = dao.selectBySql(SpeakingDAO.SELECT_ALL_SPEAKING_IN_LESSON_QUERY, LessonViewController.curLesson.getIdLesson());
                if (lst == null || lst.isEmpty()){
                    MessageBox.show("Thông báo","Phần học này vẫn chưa được thêm vào, bé học phần khác nhé", Alert.AlertType.CONFIRMATION);
                    return;
                }
                FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("speaking_view.fxml"));
                Pane pane = loader.load();
                mainViewController.onPaneChange(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnGrammar.setOnAction(event -> {
            try {
                GrammarDAO dao = new GrammarDAO();
                List<Grammar> lst = dao.selectBySql(GrammarDAO.SELECT_ALL_GRAMMAR_IN_LESSON_QUERY, LessonViewController.curLesson.getIdLesson());
                if (lst == null || lst.isEmpty()){
                    MessageBox.show("Thông báo","Phần học này vẫn chưa được thêm vào, bé học phần khác nhé", Alert.AlertType.CONFIRMATION);
                    return;
                }
                FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("grammar_view.fxml"));
                Pane pane = loader.load();
                mainViewController.onPaneChange(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnQuiz.setOnAction(event -> {
            try {
                QuizDAO dao = new QuizDAO();
                List<Quiz> lst = dao.selectBySql(QuizDAO.SELECT_QUIZ_FROM_LESSON_QUERY, LessonViewController.curLesson.getIdLesson());
                if (lst == null || lst.isEmpty()){
                    MessageBox.show("Thông báo","Phần học này vẫn chưa được thêm vào, bé học phần khác nhé", Alert.AlertType.CONFIRMATION);
                    return;
                }
                QuestionDAO questionDAO = new QuestionDAO();
                if(showConfirmationDialog(questionDAO.selectBySql(QuestionDAO.SELECT_QUESTIONS_FROM_QUIZ_QUERY, lst.get(0).getIdQuiz())))
                {
                    FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("quiz_view.fxml"));
                    Pane pane = loader.load();
                    ChangeMainPane controller = loader.getController();
                    controller.setMainViewController(mainViewController);
                    mainViewController.onPaneChange(pane);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnListening.setOnAction(event -> {
            try {
                ListeningDAO dao = new ListeningDAO();
                List<Listening> lst = dao.selectBySql(ListeningDAO.SELECT_LISTENING_FROM_LESSON, LessonViewController.curLesson.getIdLesson());
                if (lst == null || lst.isEmpty()){
                    MessageBox.show("Thông báo","Phần học này vẫn chưa được thêm vào, bé học phần khác nhé", Alert.AlertType.CONFIRMATION);
                    return;
                }
                FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("listening_view.fxml"));
                Pane pane = loader.load();
                mainViewController.onPaneChange(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void setMainViewController(MainViewController mainViewController){
        this.mainViewController = mainViewController;
    }

    private boolean showConfirmationDialog(List<QuestionQuiz> lstQuestion) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn đã sẵn sàng làm bài kiểm tra chưa?");
        alert.setContentText("Bài kiểm tra sẽ có " + lstQuestion.size() + " câu hỏi");

        ButtonType readyButton = new ButtonType("Sẵn sàng");
        ButtonType notReadyButton = new ButtonType("Chưa sẵn sàng");

        alert.getButtonTypes().setAll(readyButton, notReadyButton);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initModality(Modality.APPLICATION_MODAL);
        AtomicBoolean result = new AtomicBoolean(false);

        Optional<ButtonType> buttonClicked = alert.showAndWait();
        buttonClicked.ifPresent(buttonType -> {
            if (buttonType == readyButton) {
                result.set(true);
            } else if (buttonType == notReadyButton) {
                result.set(false);
            }
        });

        return result.get();
    }
}
