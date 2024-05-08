package com.example.englishforkids.viewcontroller;

//import com.example.englishforkids.GetResourceController;
//import com.google.api.gax.core.FixedCredentialsProvider;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.speech.v1.*;

import com.example.englishforkids.dao.SpeakingDAO;
import com.example.englishforkids.model.Lesson;
import com.example.englishforkids.model.Speaking;
import com.google.protobuf.ByteString;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpeakingViewController {
    private List<Speaking> lstSpeaking;
    private Lesson lesson;
    @FXML
    Label lblTitle;
    @FXML
    Label lblContent;
    @FXML
    TextArea txtExample;
    @FXML
    Button btnPrevious;
    @FXML
    Button btnNext;
    @FXML
    Button btnListenExample;
    @FXML
    Button btnStopListen;
    @FXML
    Button btnTalk;
    @FXML
    Button btnStopTalk;
    private int index;
    @FXML
    Label lblUsername;
    @FXML
    Pane paneUsername;
    @FXML
    ImageView imgAvatar;
    private AudioClip audioClip;
//    private AudioInputStream audioStream;
//    private ByteArrayOutputStream audioBuffer;
//    private TargetDataLine targetLine;
//    private SpeechClient speechClient;
    public void initialize(){
        MainViewController.createPaneUsername(lblUsername, paneUsername,imgAvatar);
        index = 0;
        loadScene();
        btnListenExample.setOnAction(event -> {
            audioClip.play();
        });
        btnStopListen.setOnAction(event -> {
            audioClip.stop();
        });
        btnPrevious.setOnAction(event -> {
            if(index > 0){
                index--;
                loadScene();
            }
        });
        btnNext.setOnAction(event -> {
            if (index < this.lstSpeaking.size()-1)
            {
                index++;
                loadScene();
            }
        });
        btnTalk.setOnAction(event -> {
//            try {
//                loadGooleAPIClient();
//                AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
//                DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
//                targetLine = (TargetDataLine) AudioSystem.getLine(info);
//                targetLine.open(audioFormat);
//                targetLine.start();
//
//                audioStream = new AudioInputStream(targetLine);
//                audioBuffer = new ByteArrayOutputStream();
//
//                ExecutorService executorService = Executors.newSingleThreadExecutor();
//                executorService.execute(() -> {
//                    try {
//                        byte[] buffer = new byte[4096];
//                        int bytesRead;
//                        while ((bytesRead = audioStream.read(buffer)) != -1) {
//                            audioBuffer.write(buffer, 0, bytesRead);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//            } catch (LineUnavailableException e) {
//                e.printStackTrace();
//            }
        });
        btnStopTalk.setOnAction(event -> {
//            targetLine.stop();
//            targetLine.close();
//            byte[] audioData = audioBuffer.toByteArray();
//            ByteString audioBytes = ByteString.copyFrom(audioData);
//
//            RecognitionConfig config =
//                    RecognitionConfig.newBuilder()
//                            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
//                            .setSampleRateHertz(16000)
//                            .setLanguageCode("en-US")
//                            .build();
//
//            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();
//            RecognizeResponse response = speechClient.recognize(config, audio);
//
//            for (SpeechRecognitionResult result : response.getResultsList()) {
//                System.out.println("Transcription: " + result.getAlternatives(0).getTranscript());
//            }
//            if (speechClient != null) {
//                speechClient.close();
//            }
        });
    }

/*    private void loadGooleAPIClient(){
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(String.valueOf(GetResourceController.getFXMLResourcePath("englishforkids-421707-36398b74d3b7.json"))));
            FixedCredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials);
            SpeechSettings settings = SpeechSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
            speechClient = SpeechClient.create(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void loadScene(){
        clearScene();
        lesson = LessonViewController.curLesson;
        SpeakingDAO speakingDAO = new SpeakingDAO();
        this.lstSpeaking = speakingDAO.selectBySql(SpeakingDAO.SELECT_ALL_SPEAKING_IN_LESSON_QUERY, lesson.getIdLesson());
        if(this.lstSpeaking != null){
            txtExample.setWrapText(true);
            this.lblTitle.setText(this.lstSpeaking.get(index).getTitle());
            this.lblContent.setText(this.lstSpeaking.get(index).getContent());

            if (this.lstSpeaking.get(index).getExample()!=null){
                byte[] audioData = Base64.getDecoder().decode(this.lstSpeaking.get(index).getExample());
                File tempFile = createTempFileFromByteArray(audioData);
                this.audioClip = new AudioClip(tempFile.toURI().toString());
            }
           // this.audioClip = new AudioClip(new ByteArrayInputStream(this.lstSpeaking.get(index).getExample()).toString());
        }
    }

    private void clearScene(){
        lblTitle.setText("");
        lblContent.setText("");
        audioClip = null;
    }

    private File createTempFileFromByteArray(byte[] data) {
        try {
            File tempFile = File.createTempFile("audio", ".mp3");
            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                outputStream.write(data);
            }
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
