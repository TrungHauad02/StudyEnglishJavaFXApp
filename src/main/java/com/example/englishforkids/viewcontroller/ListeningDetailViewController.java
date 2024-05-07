package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.feature.DataUpdateListener;
import com.example.englishforkids.model.Listening;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;

public class ListeningDetailViewController implements DataUpdateListener{
    @FXML
    Label lblTitle;
    @FXML
    TextArea txtDescription;
    public void initialize(){

    }
    @Override
    public void onUpdateData(List<Listening> lstListening, int currentIndex){
        lblTitle.setText(lstListening.get(currentIndex).getTitle());
        txtDescription.setWrapText(true);
        txtDescription.setText(lstListening.get(currentIndex).getDescription());
    }
}
