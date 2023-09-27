
public class Question {
    private String questionText;
    private String[] choices;

    private String correctChoice;
    public void setQuestionText(String questionText) {

        this.questionText = questionText;
    }

    public void setChoices(String[] choices) {

        this.choices = choices;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    public Question(String questionText, String[] choices, String correctChoice) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctChoice = correctChoice;
    }
    public String getQuestionText() {
        return questionText;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getCorrectChoice() {
        return correctChoice;
    }
}

