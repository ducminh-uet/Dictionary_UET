package game.screen;
import game.tool.InputData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

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
    private int score = 0;
    private Timeline questionTimer;

    private ArrayList<Question> questions;
    private int currentQuestionIndex; // Keep track of the current question index.

    public void initialize() {
        // Load questions from a file using the InputData class or another method.
        questions = InputData.loadQuestionsFromFile("src\\main\\java\\game\\tool\\input.txt");
        currentQuestionIndex = 0; // Start with the first question.
        displayCurrentQuestion();
        questionTimer = new Timeline(
                new KeyFrame(
                        Duration.seconds(5), // Set the duration to 15 seconds
                        event -> {
                            // Code to move to the next question after 15 seconds
                            nextQuestion(null);
                        }
                )
        );
        questionTimer.setCycleCount(1); // Run only once per question
        questionTimer.play();
    }

    public void backToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MenuController.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void nextQuestion(ActionEvent event) {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++; // Move to the next question.
            displayCurrentQuestion();
            resetTimer();
        } else {
            // No more questions; display the score screen.
            displayScoreScreen();
        }
    }

    public void selectAnswer(ActionEvent event) {
        // Stop the question timer
        questionTimer.stop();
        // Get the selected answer.
        String selectedAnswer = getSelectedAnswer();

        // Get the current question.
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Check if the selected answer is correct.
        if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
            // Increment the user's score for a correct answer.
            score += 10;
        }

        // Disable radio buttons to prevent multiple selections.
        option1.setDisable(true);
        option2.setDisable(true);
        option3.setDisable(true);
        option4.setDisable(true);

        // Move to the next question.
        nextQuestion(event);
    }

    public String getSelectedAnswer() {
        String res = "";
        if (option1.isSelected()) {
            res =  option1.getText();
        } else if (option2.isSelected()) {
            res = option2.getText();
        } else if (option3.isSelected()) {
            res = option3.getText();
        } else if (option4.isSelected()) {
            res = option4.getText();
        }
        return res;
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

        // Enable radio buttons.
        option1.setDisable(false);
        option2.setDisable(false);
        option3.setDisable(false);
        option4.setDisable(false);

        // Clear the selection of all radio buttons.
        option1.setSelected(false);
        option2.setSelected(false);
        option3.setSelected(false);
        option4.setSelected(false);
    }

    public void displayScoreScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScoreScreen.fxml"));
            Parent scoreScreenRoot = loader.load();

            ScoreScreenController scoreScreenController = loader.getController();
            scoreScreenController.setScore(score);

            stage = (Stage) myTitle.getScene().getWindow();
            scene = new Scene(scoreScreenRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void resetTimer() {
        questionTimer.stop();
        questionTimer.play();
    }
}
