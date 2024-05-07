package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.GetResourceController;
import com.example.englishforkids.dao.VocabularyDAO;
import com.example.englishforkids.feature.CurrentUser;
import com.example.englishforkids.model.Vocabulary;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class ListVocabularyViewController {
    @FXML
    Label lblUsername;
    @FXML
    Pane paneUsername;
    @FXML
    TextField txtSearch;
    @FXML
    Button btnSearch;
    @FXML
    TableView<Vocabulary> tbvListVocabulary;
    @FXML
    TableColumn<Integer, String> tbcWord;
    @FXML
    TableColumn<Integer, String> tbcMean;
    @FXML
    TableColumn<Integer, String> tbcPhonetic;
    @FXML
    TableColumn<Vocabulary, Button> tbcDetail;
    VocabularyDAO vocabularyDAO;
    List<Vocabulary> lstVocabulary;
    public void initialize() {
        MainViewController.createPaneUsername(lblUsername, paneUsername);

        vocabularyDAO = new VocabularyDAO();
        lstVocabulary = vocabularyDAO.selectAll();

        tbvListVocabulary.getItems().addAll(lstVocabulary);
        tbcWord.setCellValueFactory(new PropertyValueFactory<>("word"));
        tbcMean.setCellValueFactory(new PropertyValueFactory<>("mean"));
        tbcPhonetic.setCellValueFactory(new PropertyValueFactory<>("phonetic"));

        tbcDetail.setCellFactory(param -> new TableCell<>() {
            private final Button detailButton = new Button("Detail");
            {
                detailButton.setStyle("-fx-background-color: transparent; -fx-underline: true;");
                detailButton.setOnAction(event -> {
                    TableRow<Vocabulary> row = getTableRow();
                    if (row != null) {
                        Vocabulary vocabulary = row.getItem();
                        if (vocabulary != null) {
                            FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("vocabulary_view.fxml"));
                            try {
                                Parent root = loader.load();
                                VocabularyViewController controller = loader.getController();
                                controller.setVocabulary(vocabulary);
                                Scene scene = new Scene(root, 482, 316);
                                Stage dialogStage = new Stage();
                                dialogStage.setTitle("Vocabulary");
                                dialogStage.setScene(scene);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.showAndWait();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(detailButton);
                }
            }
        });

        btnSearch.setOnAction(event -> {
            String searchText = txtSearch.getText().trim().toLowerCase();

            if (!searchText.isEmpty()) {
                tbvListVocabulary.getItems().clear();

                for (Vocabulary vocabulary : lstVocabulary) {
                    if (vocabulary.getWord().toLowerCase().contains(searchText)) {
                        tbvListVocabulary.getItems().add(vocabulary);
                    }
                }
            }
        });
    }
}
