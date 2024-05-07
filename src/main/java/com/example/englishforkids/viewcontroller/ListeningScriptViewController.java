package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.feature.DataUpdateListener;
import com.example.englishforkids.model.Listening;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;

public class ListeningScriptViewController implements DataUpdateListener {
    @FXML
    Label lblTitle;
    @FXML
    TextArea txtScript;

    @Override
    public void onUpdateData(List<Listening> lstListening, int currentIndex){
        lblTitle.setText(lstListening.get(currentIndex).getTitle());
        txtScript.setWrapText(true);
        txtScript.setText(lstListening.get(currentIndex).getScript());
    }
}
