package com.wyd.test.text2voice;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.*;

public class VoicePlayTest {

    private static final Logger logger = LoggerFactory.getLogger(VoicePlayTest.class);

    public static void main(String[] args) {
        new JFXPanel();
        Platform.runLater(() -> {
            playAudioWithJLayer("./test.mp3");
                });

    }

    public static void playAudioWithJLayer(String filePath) {
        try {
            File audioFile = new File(filePath);

            String uriString = audioFile.toURI().toString();

            MediaPlayer player = new MediaPlayer(new Media(uriString));

            System.out.println("开始播放音频: " + filePath);

            // 播放整个文件
            player.play();

            System.out.println("播放完成");
        }  catch (Exception e) {
            logger.error("播放音频时发生未知错误: " + filePath, e);
        }
    }
}
