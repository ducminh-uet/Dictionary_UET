package game.question;

import java.util.ArrayList;

public class Category {
    private ArrayList<Question> questions;
    private String title;

    public Category(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public String toString() {
        return "Category{" +
                "questions=" + questions +
                ", title='" + title + '\'' +
                '}';
    }
}
