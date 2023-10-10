package dictionary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Dictionary extends HashMap{
    /**
     * Constructor 1
     */
    public Dictionary() {
    }

    /**
     * Constructor 2
     */
    public Dictionary(ArrayList<Word> newDic) {
        addAllWords(newDic);
    }

    /**
     * Getter
     *
     * @return Dictionary
     */
    public ArrayList<Word> getDictionary() {
        ArrayList<Word> dic = this.findAllWords("");
        dic.remove(0);
        return dic;
    }

    /**
     * Find a word by its word_target
     *
     * @param word_target
     * @return the word
     */
    public Word findWord(String word_target) {
        return findWord(Word.hashCode(word_target));
    }

    /**
     * Find all words starting with string
     *
     * @param
     * @return all words
     */
    public ArrayList<Word> findAllWords(String string) {
        ArrayList<Word> wordList=new ArrayList<>();
        Queue<HashMap> hashMapsQueue = new LinkedList<>();
        hashMapsQueue.offer(findChildrenHashMap(Word.hashCode(string)));
        while (hashMapsQueue.size()>0) {
            HashMap currentHashMap=hashMapsQueue.poll();
            if(currentHashMap.word != null) wordList.add(currentHashMap.word);
            if(currentHashMap.next != null) for (HashMap nextHashMap : currentHashMap.next){
                if (nextHashMap.word != null) hashMapsQueue.offer(nextHashMap);
            }
        }
        return wordList;
    }

    /**
     * Add a new word
     *
     * @param word
     */
    public void addWord(Word word){
        addWord(word,word.hashCode());
    }

    /**
     * Add all words from the word list
     * @param wordList
     */
    public void addAllWords(ArrayList<Word> wordList){
        for(Word word : wordList){
            addWord(word);
        }
    }
}