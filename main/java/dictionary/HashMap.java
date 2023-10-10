

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HashMap{
    public dictionary.Word word;
    public HashMap[] next=null;

    /**
     * Constructor
     */
    public HashMap(){}

    /**
     * Find a children hash map by its preceding word's hash code
     *
     * @param hashCode
     * @return children hash map
     */
    // Thay vì sử dụng Word.hashCode("z")


    public HashMap findChildrenHashMap(int hashCode) {
        if(hashCode<=52) return this;
        if(next==null || next[hashCode%26]==null) return null;
        return next[hashCode%26];
    }

    /**
     * Find a word by its hash code
     *
     * @param hashCode
     * @return the word
     */
    public dictionary.Word findWord(int hashCode) {

        return findChildrenHashMap(hashCode).word;
    }

    /**
     * add a word to the hash code place
     *
     * @param word
     * @param hashCode
     */
    public void addWord(dictionary.Word word, int hashCode){
        if(hashCode<= dictionary.Word.hashCode("z")){
            this.word = word;
        }
        else {
            if(next==null) next=new HashMap[26];
            if(next[hashCode%26]==null) next[hashCode%26]=new HashMap();
            next[hashCode%26].addWord(word,hashCode/26);
        }
    }
    public void removeWord(dictionary.Word word, int hashCode) {

        if (hashCode <= dictionary.Word.hashCode("z")) {
            if (this.word != null && this.word.equals(word)) {
                this.word = null;
            }
        } else {
            if (next != null && next[hashCode % 26] != null) {
                next[hashCode % 26].removeWord(word, hashCode / 26);
            }
        }
    }


}