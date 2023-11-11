package screen;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import dictionary.tool.Sound;
import dictionary.tool.TranslateAPI;

import java.io.IOException;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] args) throws Exception {
        TranslateAPI translateAPI = new TranslateAPI();
        System.out.println(translateAPI.translate("fr", "vi", "je t'aime"));

        Sound.Speech(translateAPI.translate("fr", "vi", "je t'aime"));
        System.out.println(translateAPI.translate("fr", "vi", "je t'aime"));
    }
}
