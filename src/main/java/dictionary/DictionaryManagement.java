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
        dictionary.addWord(word);
    }

    public void insertFromFile(File file) {
        IOdictionary io = new IOdictionary();
        ArrayList<Word> words_add = io.read("src/main/java/data/V_E.txt");
        for (Word adds : words_add) {
            dictionary.addWord(adds);
        }
    }
    public void saveWordsToFile() {
        IOdictionary ioDictionaries = new IOdictionary();
    //    ioDictionaries.write(dictionary.getDictionary());
    }
    public void addWord(Word word) {
        dictionary.addWord(word);
        this.saveWordsToFile();
    }
    public void dictionaryLookUp(String keyword) {
        ArrayList<Word> words = dictionary.findAllWords(keyword);
        for (Word word : words) {
            System.out.println("Word: " + word.getWord_target());
            System.out.println("Explanation: " + word.getWord_explain());
        }
    }
}