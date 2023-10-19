package game.question;

import java.util.ArrayList;
public class Question {
    private String question;
    private ArrayList<String> answersOptions;
    private int correctAnswerIndex;
    private int score;
    private boolean isAttempted;

    // Constructor
    public Question(String question, ArrayList<String> answers, int correctAnswerIndex, int score) {
        this.question = question;
        this.answersOptions = answers;
        this.correctAnswerIndex = correctAnswerIndex;
        this.score = score;
        this.isAttempted = false;
    }
    // Setter and Getter

    /**
     * question .
     *
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * answers .
     *
     * @return answers
     */
    public ArrayList<String> getAnswers() {
        return answersOptions;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answersOptions = answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    /**
     * get Score .
     *
     * @return Score of question .
     */
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Kiểm tra câu hỏi đã được hỏi .
     *
     * @return isAttempted .
     */
    public boolean isAttempted() {
        return isAttempted;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }

    public boolean checkAnswer(int userAnswerIndex) {
        // Kiểm tra xem đáp án của người dùng có đúng hay không
        return userAnswerIndex == correctAnswerIndex;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answersOptions +
                ", correctAnswerIndex=" + correctAnswerIndex +
                ", score=" + score +
                ", isAttempted=" + isAttempted +
                '}';
    }

}
