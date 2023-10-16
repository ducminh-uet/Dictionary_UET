package dictionary.tool;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Sound {
    public static void Speech (String text) {
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
}
