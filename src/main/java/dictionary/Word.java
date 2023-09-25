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


}