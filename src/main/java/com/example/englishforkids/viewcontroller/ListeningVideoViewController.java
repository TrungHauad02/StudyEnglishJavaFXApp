package com.example.englishforkids.viewcontroller;

import com.example.englishforkids.feature.DataUpdateListener;
import com.example.englishforkids.model.Listening;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;

import java.io.*;
import java.util.Base64;
import java.util.List;

// subscribers in Pattern Observer
public class ListeningVideoViewController implements DataUpdateListener {
    @FXML
    Label lblTitle;
    @FXML
    MediaView mediaVideo;
    @FXML
    Label lblScript;
    @FXML
    Button btnPlay;
    @FXML
    Button btnPause;
    @FXML
    TextFlow textFlow;

    @Override
    public void onUpdateData(List<Listening> lstListening, int currentIndex){
        lblTitle.setText(lstListening.get(currentIndex).getTitle());

        byte[] videoData = Base64.getDecoder().decode(lstListening.get(currentIndex).getVideo());
        String base64Data = Base64.getEncoder().encodeToString(videoData);

        try {
            File tempFile = createTempFileFromBase64(base64Data);
            playVideoFromFile(tempFile);
            //updateTextFlowWithWords(lstListening.get(currentIndex).getScript());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void playVideoFromFile(File videoFile) {
        if (videoFile != null && videoFile.exists()) {
            Media media = new Media(videoFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaVideo.setMediaPlayer(mediaPlayer);

            btnPlay.setOnAction(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.play();
                }
            });

            btnPause.setOnAction(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                }
            });

            mediaPlayer.setOnError(() -> {
                System.out.println("Error occurred: " + mediaPlayer.getError());
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            });
        }
    }
    private File createTempFileFromBase64(String base64Data) throws IOException {
        byte[] data = Base64.getDecoder().decode(base64Data);
        File tempFile = File.createTempFile("temp-video", ".mp4");

        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(data);
        }

        return tempFile;
    }
    private void updateTextFlowWithWords(String script) {
        textFlow.getChildren().clear();

        String[] words = script.split("\\s+");

        for (String word : words) {
            Text text = new Text(word + " ");
            text.setFill(Color.BLACK);
            textFlow.getChildren().add(text);
        }
    }
}
