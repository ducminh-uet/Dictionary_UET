package dictionary.tool;

import dictionary.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DictionarySorter {
    public static void sortWords(ArrayList<Word> words) {
        Collections.sort(words, new WordComparator());
    }

    private static class WordComparator implements Comparator<Word> {
        @Override
        public int compare(Word word1, Word word2) {
            return word1.getWord_target().compareTo(word2.getWord_target());
        }
    }
}
