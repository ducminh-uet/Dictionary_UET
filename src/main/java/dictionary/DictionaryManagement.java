package dictionary;

import dictionary.tool.IOdictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    protected Dictionary dictionary = new Dictionary();

    /** public DictionaryManagement(){
        this.insertFromFile();
    } */
    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        String target = sc.nextLine();
        String explain = sc.nextLine();
        Word word = new Word(target, explain);
        dictionary.push(word);
    }

    public void insertFromFile(File file) {
        IOdictionary io = new IOdictionary();
        ArrayList<Word> words_add = io.read();
        for (Word adds : words_add) {
            dictionary.push(adds);
        }
    }
    public void saveWordsToFile() {
        IOdictionary ioDictionaries = new IOdictionary();
        ioDictionaries.write(dictionary.getDictionary());
    }
    public void addWord(Word word) {
        dictionary.push(word);
        this.saveWordsToFile();
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