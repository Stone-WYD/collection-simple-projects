package com.wyd.test.text2voice;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Text2VoiceWinTest {

    private static final Logger logger = LoggerFactory.getLogger(Text2VoiceWinTest.class);

    // 注意：jdk 中要有 jacob 的 dll
    public static void main(String[] args) {
        textToSpeech("叶絮依是小可爱");
    }

    public static void textToSpeech(String text) {
        try {
            // 发音
            speak(text);
            // 下面是构建文件流把生成语音文件
            generateVoiceFile(text);
        } catch (Exception e) {
            logger.error("textToSpeech error", e);
        }
    }
    private static void speak(String text) {
        ActiveXComponent ax = null;
        try {
            ax = new ActiveXComponent("Sapi.SpVoice");
            Dispatch spVoice = ax.getObject();
            // 运行时输出语音内容
            // 音量 0-100
            ax.setProperty("Volume", new Variant(100));
            // 语音朗读速度 -10 到 +10
            ax.setProperty("Rate", new Variant(2));
            // 执行朗读
            Dispatch.call(spVoice, "Speak", new Variant(text));
            spVoice.safeRelease();
            ax.safeRelease();
        } finally {
            if (ax!= null) {
                ax.safeRelease();
            }
        }
    }

    private static void generateVoiceFile(String text) {
        ActiveXComponent fileAx = null;
        ActiveXComponent voiceAx = null;
        ActiveXComponent formatAx = null;
        try {
            // 创建文件流对象
            fileAx = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = fileAx.getObject();

            // 创建语音对象
            voiceAx = new ActiveXComponent("Sapi.SpVoice");
            Dispatch spVoice = voiceAx.getObject();

            // 创建音频格式对象
            formatAx = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = formatAx.getObject();

            // 设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            // 设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            // 调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant("./text.wav"), new Variant(3), new Variant(true));
            // 设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            // 设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            // 设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(2));
            // 开始朗读并生成文件
            Dispatch.call(spVoice, "Speak", new Variant(text));
            // 关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);

            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
        } finally {
            // 确保ActiveXComponent资源被释放
            if (formatAx != null) {
                formatAx.safeRelease();
            }
            if (voiceAx != null) {
                voiceAx.safeRelease();
            }
            if (fileAx != null) {
                fileAx.safeRelease();
            }
        }
    }
}