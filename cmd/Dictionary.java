import java.util.ArrayList;

public class Dictionary {
    //Chuyển sang Hash Table cho nhanh
    private ArrayList<Word> words;

    public Dictionary() {
        this.words = new ArrayList<>();
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public ArrayList<Word> getWords() {
        return words;
    }
}
