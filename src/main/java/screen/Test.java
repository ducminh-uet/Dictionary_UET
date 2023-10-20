package screen;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import dictionary.tool.Sound;
import dictionary.tool.TranslateAPI;

import java.io.IOException;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String wordResult = TranslateAPI.translate("vi", "en", "Kẻ địch là huyền thoại");
        System.out.println(wordResult);
        Sound.Speech(wordResult);

    }
}
