package dictionary;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> myDictionary = new ArrayList<>();

    /**
     * Constructor 1
     */
    public Dictionary() {
    }

    /**
     * Constructor 2
     */
    public Dictionary(ArrayList<Word> newDic) {
        this.myDictionary = newDic;
    }

    /**
     * Setter and Getter
     *
     * @return Dic
     */
    public ArrayList<Word> getDictionary() {
        return myDictionary;
    }

    public void setDictionary(ArrayList<Word> newDic) {
        this.myDictionary = newDic;
    }

    /**
     * Find a word
     *
     * @param start
     * @param n
     * @param target
     * @return location of the word
     */
    public int binarySearch(int start, int n, String target) {
        if (n < start) return start;
        int length = myDictionary.size();
        int mid = (start + n) / 2;
        if (mid == length) return mid;
        Word word = myDictionary.get(mid);
        int compare = word.getWord_target().compareTo(target);
        if (compare == 0) return -1;
        else if (compare > 0) return binarySearch(start, mid - 1, target);
        return binarySearch(mid + 1, n, target);
    }

    /**
     * Add a new word
     *
     * @param word need to be added
     */
    public void push(Word word) {
        int length = myDictionary.size();
        int index = binarySearch(0, length - 1, word.getWord_target());
        if (index <= length && index >= 0) myDictionary.add(index, word);
    }
}