package game.question;

import java.util.ArrayList;

public class Question {
    private String title;
    private String questionText;
    private ArrayList<String> answer = new ArrayList<>();
    private String correctAnswer;
    private Boolean isAttempt;
    // Constructor
    public Question(String title, String questionText, ArrayList<String> answer, String correctAnswer, Boolean isAttemp) {
        this.title = title;
        this.questionText = questionText;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
        this.isAttempt = isAttempt;
    }

    public Question() {
    }
    // Getter and setter

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Boolean getAttempt() {
        return isAttempt;
    }

    public void setAttempt(Boolean attempt) {
        isAttempt = attempt;
    }
}
