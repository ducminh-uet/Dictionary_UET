package dictionary;

public class Word {
    private String word_target;
    private String word_explain;

    /**
     * Chuyen target va explain ve chu thuong.
     */
    public Word(String word_target, String word_explain) {
        this.word_explain = word_explain.trim().toLowerCase();
        this.word_target = word_target.trim().toLowerCase();
    }

    public String getWord_target() {
        return word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
    public static int hashCode(String word_target) {
        int result =1;
        for(int i=0; i<word_target.length(); i++) {
            char c=word_target.charAt(i);
            int charCode;
            if(c>='a'&&c<='z') charCode=c-'a'+1;
            else if(c>='A'&&c<='Z') charCode=c-'A'+1;
            else charCode=0;
            result = 26 * result + charCode;
        }
        return result;
    }
    @Override
    public int hashCode() {
        return hashCode(this.word_target);
    }
    public String toString(){
        return word_target;
    }
}