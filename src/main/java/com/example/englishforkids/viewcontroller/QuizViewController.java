package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.dao.*;
import com.example.englishforkids.feature.CurrentUser;
import com.example.englishforkids.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class QuizViewController {
    @FXML
    Label lblSerial;
    @FXML
    Label lblQuizName;
    @FXML
    Label lblQuestionContent;
    @FXML
    RadioButton rdbOption1;
    @FXML
    RadioButton rdbOption2;
    @FXML
    RadioButton rdbOption3;
    @FXML
    RadioButton rdbOption4;
    @FXML
    Button btnNextQuestion;
    @FXML
    Button btnExit;
    @FXML
    Button btnSubmit;
    @FXML
    ImageView imgQuestion;
    @FXML
    Pane paneListQuestion;
    @FXML
    ScrollPane scrollPane;
    QuizDAO quizDAO;
    QuestionDAO questionDAO;
    AnswerDAO answerDAO;
    SubmitQuizDAO submitQuizDAO;
    AnswerSubmitQuizDAO answerSubmitQuizDAO;
    List<QuestionQuiz> lstQuestion;
    List<AnswerSubmitQuiz> lstAnswerSubmit;
    SubmitQuiz submitQuiz;
    Quiz quiz;
    int indexCurrentQuestion;
    public void initialize(){
        quizDAO = new QuizDAO();
        questionDAO = new QuestionDAO();
        answerDAO = new AnswerDAO();
        submitQuizDAO = new SubmitQuizDAO();
        answerSubmitQuizDAO = new AnswerSubmitQuizDAO();
        lstAnswerSubmit = new LinkedList<>();

        btnNextQuestion.setOnAction(event -> {
            if (indexCurrentQuestion < lstQuestion.size() - 1) {
                indexCurrentQuestion++;
                loadQuestion();
            }
        });
        btnExit.setOnAction(event -> {
            Stage currentStage = (Stage) lblQuestionContent.getScene().getWindow();
            currentStage.close();
        });
        btnSubmit.setOnAction(event -> {
            submit();
        });

        indexCurrentQuestion = 0;
        loadData();
        lblQuizName.setText(quiz.getTitle());
        loadQuestion();
        loadToggleGroup();
        loadListQuestionPane();
        showConfirmationDialog();
    }
    private void loadData(){
        Lesson lesson = LessonViewController.curLesson;
        quiz = quizDAO.selectBySql(QuizDAO.SELECT_QUIZ_FROM_LESSON_QUERY, lesson.getIdLesson()).get(0);
        lstQuestion = questionDAO.selectBySql(QuestionDAO.SELECT_QUESTIONS_FROM_QUIZ_QUERY, quiz.getIdQuiz());
        for (QuestionQuiz questionQuiz : lstQuestion) {
            String idQuestion = questionQuiz.getIdQuestionQuiz();
            questionQuiz.setLstAnswers(answerDAO.selectBySql(AnswerDAO.SELECT_ANSWER_FROM_QUESTION_QUERY, idQuestion));
            lstAnswerSubmit.add(new AnswerSubmitQuiz());
        }
    }
    private void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn đã sẵn sàng làm bài kiểm tra chưa?");
        alert.setContentText("Bài kiểm tra sẽ có " + lstQuestion.size() + " câu hỏi");

        ButtonType readyButton = new ButtonType("Sẵn sàng");
        ButtonType notReadyButton = new ButtonType("Chưa sẵn sàng");

        alert.getButtonTypes().setAll(readyButton, notReadyButton);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == readyButton) {
                startQuiz();
            } else if (buttonType == notReadyButton) {
                Stage currentStage = (Stage) lblQuestionContent.getScene().getWindow();
                currentStage.close();
            }
        });
    }

    private void startQuiz(){
        submitQuiz = new SubmitQuiz();
        submitQuiz.setIdQuiz(quiz.getIdQuiz());
        submitQuiz.setIdUser(CurrentUser.getInstance().getCurrentUser().getIdUser());
        submitQuiz.setStartTime(new Date());
    }
    private void loadToggleGroup(){
        ToggleGroup toggleGroup = new ToggleGroup();
        rdbOption1.setToggleGroup(toggleGroup);
        rdbOption2.setToggleGroup(toggleGroup);
        rdbOption3.setToggleGroup(toggleGroup);
        rdbOption4.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selectedRadioButton = (RadioButton) newToggle;
                String selectedAnswerText = selectedRadioButton.getText();
                QuestionQuiz currentQuestion = lstQuestion.get(indexCurrentQuestion);
                for (AnswerQuiz answer : currentQuestion.getLstAnswers()) {
                    if (answer.getContent().equals(selectedAnswerText)) {
                        lstAnswerSubmit.get(indexCurrentQuestion).setIdAnswerQuiz(answer.getIdAnswerQuiz());
                        break;
                    }
                }
            }
        });
    }
    private void loadListQuestionPane(){
        int numButtons = lstQuestion.size();
        int buttonWidth = 25;
        int buttonHeight = 25;
        int startX = 12;
        int startY = 10;
        int spacingX = 38;
        int spacingY = 40;

        for (int i = 0; i < numButtons; i++) {
            Button button = new Button();
            int row = i / 6;
            int col = i % 6;
            double buttonX = startX + col * spacingX;
            double buttonY = startY + row * spacingY;
            button.setLayoutX(buttonX);
            button.setLayoutY(buttonY);
            button.setPrefWidth(buttonWidth);
            button.setPrefHeight(buttonHeight);
            button.setText(String.valueOf(lstQuestion.get(i).getSerial()));
            final int buttonIndex = i;
            button.setOnAction(event -> {
                indexCurrentQuestion = buttonIndex;
                loadQuestion();
            });
            paneListQuestion.getChildren().add(button);
        }
        int numRows = (numButtons + 5) / 6;
        double requiredHeight = numRows * spacingY + startY;
        double scrollPaneHeight = scrollPane.getHeight();
        if (requiredHeight > scrollPaneHeight) {
            paneListQuestion.setMinHeight(requiredHeight);
            scrollPane.setFitToHeight(true);
        }else {
            scrollPane.setFitToHeight(false);
        }
        scrollPane.setFitToWidth(false);
    }

    private void loadQuestion(){
        lblSerial.setText(String.valueOf(lstQuestion.get(indexCurrentQuestion).getSerial()));
        lblQuestionContent.setText(String.valueOf(lstQuestion.get(indexCurrentQuestion).getContent()));
        byte[] decodedImage = Base64.getDecoder().decode(lstQuestion.get(indexCurrentQuestion).getImage());
        imgQuestion.setImage(new Image(new ByteArrayInputStream(decodedImage)));
        rdbOption1.setText(String.valueOf(lstQuestion.get(indexCurrentQuestion).getLstAnswers().get(0).getContent()));
        rdbOption2.setText(String.valueOf(lstQuestion.get(indexCurrentQuestion).getLstAnswers().get(1).getContent()));
        rdbOption3.setText(String.valueOf(lstQuestion.get(indexCurrentQuestion).getLstAnswers().get(2).getContent()));
        rdbOption4.setText(String.valueOf(lstQuestion.get(indexCurrentQuestion).getLstAnswers().get(3).getContent()));
    }

    private void submit(){
        int score = 0;
        for(AnswerSubmitQuiz answerSubmitQuiz: lstAnswerSubmit){
            for(QuestionQuiz questionQuiz : lstQuestion){
                for(AnswerQuiz answerQuiz: questionQuiz.getLstAnswers()){
                    if(answerSubmitQuiz.getIdAnswerQuiz().equals(answerQuiz.getIdAnswerQuiz())){
                        if(answerQuiz.isCorrect()){
                            score++;
                        }
                        break;
                    }
                }
            }
        }
        submitQuiz.setScore(score);
        submitQuiz.setEndTime(new Date());
        submitQuizDAO.insert(submitQuiz);
        for(AnswerSubmitQuiz answerSubmitQuiz: lstAnswerSubmit){
            answerSubmitQuizDAO.insert(answerSubmitQuiz);
        }
    }
}
