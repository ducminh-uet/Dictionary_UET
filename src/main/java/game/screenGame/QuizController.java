package game.screenGame;


import game.question.Question;
import game.question.Question;
import game.question.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class QuizController {

    private Quiz quiz;
    private int score;
    // Getter and Setter
    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private Button backToMenu;
    @FXML
    private Label questionText;
    @FXML
    private Label categoryText;
    @FXML
    private RadioButton Option1;
    @FXML
    private RadioButton Option2;
    @FXML
    private RadioButton Option3;
    @FXML
    private RadioButton Option4;

    public void backToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MenuGameController.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void displayQuestionAndAnswer() {
        // Get the current question and category from the quiz
        String category = quiz.getCategories().get(quiz.getCurrentCategoryIndex()).getTitle();
        categoryText.setText("Category: " + category);
        String question = quiz.getCategories().get(quiz.getCurrentCategoryIndex()).getQuestions().get(quiz.getCurrentQuestionIndex()).getQuestion();
        questionText.setText("Question: " + question);
        // Get the answer options
        ArrayList<String> answerOptions = quiz.getCategories().get(quiz.getCurrentCategoryIndex()).getQuestions().get(quiz.getCurrentQuestionIndex()).getAnswers();

        // Update the answer option buttons with the answer text
        Option1.setText("A" + answerOptions.get(0));
        Option2.setText("B" + answerOptions.get(1));
        Option3.setText("C" + answerOptions.get(2));
        Option4.setText("D" + answerOptions.get(3));
    }
    private void checkAnswer(int selectedAnswerIndex) {
        Question question = quiz.getCategories().get(quiz.getCurrentCategoryIndex()).getQuestions().get(quiz.getCurrentQuestionIndex());
        if (selectedAnswerIndex == question.getCorrectAnswerIndex()) {
            score += 1;
            System.out.println("Correct answer!");
        } else {
            System.out.println("Incorrect answer!");
        }

        // Chuyển đến câu hỏi tiếp theo ngay sau khi kiểm tra đáp án
        // goToNextQuestion();
    }
    public void chooseTheAnswer(ActionEvent event){
        if(Option1.isSelected()) {
            checkAnswer(1);
        } else if (Option2.isSelected()) {
            checkAnswer(2);
        } else if (Option3.isSelected()) {
            checkAnswer(3);
        } else if (Option3.isSelected()) {
            checkAnswer(4);
        }
    }
}

