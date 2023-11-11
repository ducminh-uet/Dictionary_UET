package dictionary;

import java.util.ArrayList;

public class HashMap{
    protected Word word;
    protected HashMap[] next=null;

    /**
     * Constructor
     */
    public HashMap(){

    }

    /**
     * Find a children hash map by its preceding word's hash code
     *
     * @param hashCode
     * @return children hash map
     */
    public HashMap findChildrenHashMap(int hashCode) {
        if(hashCode<=52) return this;
        if(next==null || next[hashCode%26]==null) return null;
        return next[hashCode%26].findChildrenHashMap(hashCode/26);
    }

    /**
     * Find a word by its hash code
     *
     * @param hashCode
     * @return the word
     */
    public Word findWord(int hashCode) {
        return findChildrenHashMap(hashCode).word;
    }

    /**
     * add a word to the hash code place
     *
     * @param word
     * @param hashCode
     */
    public void addWord(Word word,int hashCode){
        if(hashCode<=Word.hashCode("z")){
            this.word = word;
        }
        else {
            if(next==null) next=new HashMap[26];
            if(next[hashCode%26]==null) next[hashCode%26]=new HashMap();
            next[hashCode%26].addWord(word,hashCode/26);
        }
    }

    /**
     * Remove a word by its hash code
     *
     * @param hashCode
     */
    public void removeWord(int hashCode){
        findChildrenHashMap(hashCode).word=null;
    }
}