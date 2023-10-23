package dictionary.tool;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.voicerss.tts.*;
import com.voicerss.tts.AudioFormat;


import javax.sound.sampled.*;
import java.io.*;

public class Sound {
    public static void Speech(String text) {
        // Khởi tạo trình quản lý giọng nói
        VoiceManager voiceManager = VoiceManager.getInstance();

        // Dòng quan trọng, ko có dòng này thì âm thanh không chạy đâu.
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        // Chọn một giọng nói từ danh sách các giọng nói đã cài đặt. Ae để nguyên kevin16 nhé,ổn áp nhất r
        Voice voice = voiceManager.getVoice("kevin16");

        if (voice == null) {
            System.out.println("Không thể tìm thấy giọng nói!");
            return;
        }

        // Khởi động giọng nói
        voice.allocate();

        // Đọc đoạn văn bản
        voice.speak(text);

        // Giải phóng tài nguyên giọng nói
        voice.deallocate();
    }

    public static void SpeechAPI(String text) throws Exception {
        //API key tieng anh
        String apiKey = "d3e63e8353374e97879f809e9fe49d42";
        VoiceProvider tts = new VoiceProvider(apiKey);
        VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);


        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_mono);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = tts.speech(params);

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(voice))) {
            // Lấy AudioFormat
            javax.sound.sampled.AudioFormat format = audioInputStream.getFormat();

            // Tạo một DataLine.Info cho SourceDataLine
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            // Lấy SourceDataLine từ DataLine.Info
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

            // Mở và bắt đầu phát âm thanh
            line.open(format);
            line.start();

            // Đọc và phát dữ liệu âm thanh
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            // Kết thúc phát âm thanh
            line.drain();
            line.stop();
            line.close();
        }
    }

    public static void SpeechVietNam(String text) throws Exception {
        //API key tieng viet
        String apiKey = "f17e778c46e44f57be4e990d07c273d9";
        VoiceProvider tts = new VoiceProvider(apiKey);
        VoiceParameters params = new VoiceParameters(text, Languages.Vietnamese);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_mono);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = tts.speech(params);

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(voice))) {
            // Lấy AudioFormat
            javax.sound.sampled.AudioFormat format = audioInputStream.getFormat();

            // Tạo một DataLine.Info cho SourceDataLine
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            // Lấy SourceDataLine từ DataLine.Info
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

            // Mở và bắt đầu phát âm thanh
            line.open(format);
            line.start();

            // Đọc và phát dữ liệu âm thanh
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            // Kết thúc phát âm thanh
            line.drain();
            line.stop();
            line.close();
        }
    }
}
