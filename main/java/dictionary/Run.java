package screen;
import dictionary.DictionaryCommandLine;
public class Test {
    public static void main(String[] args) {
        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
        //dictionaryCommandLine.dictionaryBasic(); --> Xong Advanced không cần Basic ?
        // Hiển thị menu chức năng
        dictionaryCommandLine.dictionaryAdvanced();
    }
}
