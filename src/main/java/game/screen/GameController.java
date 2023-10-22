package game.screen;

import game.tool.InputData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import game.question.Question;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    @FXML
    Label myTitle;
    @FXML
    Label myQuestion;
    @FXML
    RadioButton option1, option2, option3, option4;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int score;

    private ArrayList<Question> questions;
    private int currentQuestionIndex; // Keep track of the current question index.

    public void initialize() {
        // Load questions from a file using the InputData class or another method.
        questions = InputData.loadQuestionsFromFile("D:\\Java\\Dictionary_UET\\src\\main\\java\\game\\tool\\input.txt");
        currentQuestionIndex = 0; // Start with the first question.
        displayCurrentQuestion();
    }

    public void backToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void nextQuestion(ActionEvent event) {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++; // Move to the next question.
            displayCurrentQuestion();
        } else {
            // Handle the end of the quiz (e.g., show the user's score).
        }
    }

    public void selectAnswer(ActionEvent event) {
        // Get the selected answer.
        String selectedAnswer = getSelectedAnswer();

        // Get the current question.
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Check if the selected answer is correct.
        if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
            // Increment the user's score or perform other actions for a correct answer.
            score++;
        }

        // Move to the next question.
        nextQuestion(event);
    }

    public String getSelectedAnswer() {
        // Implement this method to get the selected answer (e.g., from the RadioButton).
        // Return the selected answer as a String.
        return "";
    }

    public void displayCurrentQuestion() {
        // Get the current question.
        Question currentQuestion = questions.get(currentQuestionIndex);
        // Update the UI elements (labels, radio buttons) with the current question data.
        myTitle.setText(currentQuestion.getTitle());
        myQuestion.setText(currentQuestion.getQuestionText());
        option1.setText(currentQuestion.getAnswer().get(0));
        option2.setText(currentQuestion.getAnswer().get(1));
        option3.setText(currentQuestion.getAnswer().get(2));
        option4.setText(currentQuestion.getAnswer().get(3));
    }
}
