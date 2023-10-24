package game.question;

import game.tool.InputData;

import java.util.ArrayList;

public class QuizModel {
    ArrayList<Question> questions;
    // Getter and Setter
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    // Constructor
    public QuizModel(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public static void main(String[] args) {
        QuizModel quiz = new QuizModel(InputData.loadQuestionsFromFile("D:\\Java\\Dictionary_UET\\src\\main\\java\\game\\tool\\input.txt"));
        System.out.println(quiz.getQuestions().get(0).getQuestionText());
    }
}
