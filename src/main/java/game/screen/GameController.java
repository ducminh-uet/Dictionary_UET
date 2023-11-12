package game.screen;

import game.tool.InputData;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import game.question.Question;
import javafx.util.Duration;


import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GameController {
    @FXML
    Label myTitle;
    @FXML
    Label myQuestion;
    @FXML
    RadioButton option1, option2, option3, option4;
    @FXML
    Label countdownLabel;
    @FXML
    Label correctAnswerLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private int score = 0;
    private Timeline questionTimer = new Timeline();
    private PauseTransition correctAnswerTransition;

    private ArrayList<Question> questions;
    private int currentQuestionIndex; // Keep track of the current question index.
    private int timerSeconds = 15;
    private boolean answerSelected = false;

    private String playerName;
    private MediaPlayer mediaPlayer;
    private Media backgroundMusic;

    public GameController() {
    }

    public void initialize() {
        // Load questions from a file using the InputData class or another method.
        questions = InputData.loadQuestionsFromFile("src\\main\\java\\game\\tool\\input.txt");
        currentQuestionIndex = 0; // Start with the first question.
        displayCurrentQuestion();
        questionTimer = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            timerSeconds--;
                            updateCountdownLabel();
                            if (timerSeconds == 0) {
                                // Time is up, move to the next question.
                                nextQuestion(null);
                            }
                        }
                )
        );
        questionTimer.setCycleCount(timerSeconds); // Run for 15 seconds.
        questionTimer.setOnFinished(event -> {
            // Time's up, move to the next question.
            nextQuestion(null);
        });
        questionTimer.play();

        correctAnswerTransition = new PauseTransition(Duration.seconds(5));
        correctAnswerTransition.setOnFinished(event -> {
            // Hide the correct answer label after the delay.
            correctAnswerLabel.setVisible(false);
            // Move to the next question.
            nextQuestion(null);
        });
    }

    public void backToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MenuController.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void nextQuestion(ActionEvent event) {
        // Stop the correct answer transition if it's in progress.
        correctAnswerTransition.stop();

        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++; // Move to the next question.
            displayCurrentQuestion();
            resetTimer();
            enableAnswering(); // Enable radio buttons for the new question.
            // Reset the answerSelected flag for the new question.
            answerSelected = false;
        } else {
            // No more questions; display the score screen.
            displayScoreScreen();
        }
    }



    public void selectAnswer(ActionEvent event) {
        // Check if the answer has already been selected.
        if (answerSelected) {
            return;
        }

        // Stop the question timer
        questionTimer.stop();
        // Disable radio buttons and the "Select Answer" button.
        disableAnswering();
        // Get the selected answer.
        String selectedAnswer = getSelectedAnswer();
        // Get the current question.
        Question currentQuestion = questions.get(currentQuestionIndex);
        // Check if the selected answer is correct.
        if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
            // Increment the user's score for a correct answer.
            score += 10;
        }
        // Display the correct answer label for 5 seconds.
        displayCorrectAnswer(currentQuestion.getCorrectAnswer());

        // Set the answerSelected flag to true.
        answerSelected = true;
    }

    public String getSelectedAnswer() {
        String res = "";
        if (option1.isSelected()) {
            res = option1.getText();
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
        timerSeconds = 15;
        updateCountdownLabel();
        questionTimer.playFromStart();

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

            // Save the score data
            saveScore();

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

    private void updateCountdownLabel() {
        countdownLabel.setText(String.format("Time remaining: %02d seconds", timerSeconds));
    }

    private void displayCorrectAnswer(String correctAnswer) {
        correctAnswerLabel.setText("Correct Answer: " + correctAnswer);
        correctAnswerLabel.setVisible(true);
        correctAnswerTransition.playFromStart();
    }

    private void saveScore() {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            String scoreData = formattedDateTime + " - " + playerName + ": " + score + "\n";

            // Append the score data to a file named "scores.txt"
            Files.write(Paths.get("src\\main\\java\\game\\history\\scores.txt"), scoreData.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disableAnswering() {
        option1.setDisable(true);
        option2.setDisable(true);
        option3.setDisable(true);
        option4.setDisable(true);
    }

    private void enableAnswering() {
        option1.setDisable(false);
        option2.setDisable(false);
        option3.setDisable(false);
        option4.setDisable(false);
    }
}
