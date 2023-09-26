

import dictionary.tool.IOdictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    public dictionary.Dictionary dictionary = new dictionary.Dictionary();

    public ArrayList<Word> getWords() {
        return dictionary.getDictionary();
    }

    /** public DictionaryManagement(){
     this.insertFromFile();
     } */
    public int max = 26;
    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        String target = sc.nextLine();
        String explain = sc.nextLine();
        Word word = new Word(target, explain);
        dictionary.addWord(word);
    }

    public void insertFromFile(File file) {
        IOdictionary io = new IOdictionary();
        ArrayList<Word> words_add = io.read();
        for (Word adds : words_add) {
            dictionary.addWord(adds);
        }
    }
    public void saveWordsToFile() {
        IOdictionary ioDictionaries = new IOdictionary();
        ioDictionaries.write(dictionary.getDictionary());
    }
    public void addWord(Word word) {
        dictionary.addWord(word);
        this.saveWordsToFile();
    }
    public void removeWord(String word_target) {
        ArrayList<Word> words = dictionary.getDictionary();
        Word wordToRemove = null;

        for (Word word : words) {
            if (word.getWord_target().equals(word_target)) {
                wordToRemove = word;
                break;
            }
        }

        if (wordToRemove != null) {
            words.remove(wordToRemove);
            System.out.println("Word removed: " + word_target);
            this.saveWordsToFile();
        } else {
            System.out.println("Word not found: " + word_target);
        }
    }
    public void dictionaryLookUp(String keyword) {
        ArrayList<Word> words = dictionary.getDictionary();
        for (Word word : words) {
            if (word.getWord_target().startsWith(keyword)) {
                System.out.println("Word: " + word.getWord_target());
                System.out.println("Explanation: " + word.getWord_explain());
            }
        }
    }
}