package game.screenGame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import game.question.Question;

import java.io.IOException;
import java.util.ArrayList;

public class QuizController {
    private Stage stage;
    private Scene scene;

    private Parent root;


    @FXML
    private Label myLabel;
    @FXML
    private TextField QuestionText;
    @FXML
    private CheckBox myCheckBox1, myCheckBox2, myCheckBox3, myCheckBox4;

    private int score = 0;
    private String currentQuestionText;
    private String answerOption1, answerOption2, answerOption3, answerOption4;
    private boolean hasAnswered = false;
    private int questionIndex = 0;
    private ArrayList<Question> questions;

    public void backToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MenuGameController.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Get selected answer
    private String getSelectedAnswer() {
        if (myCheckBox1.isSelected()) {
            return answerOption1;
        }
        if (myCheckBox2.isSelected()) {
            return answerOption2;
        }
        if (myCheckBox3.isSelected()) {
            return answerOption3;
        }
        if (myCheckBox4.isSelected()) {
            return answerOption4;
        }
        return "";
    }

    // Check answer
    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(questions.get(questionIndex).getAnswers().get(questions.get(questionIndex).getCorrectAnswerIndex()))) {
            score += 1;
            System.out.println("Correct answer!");
        } else {
            System.out.println("Incorrect answer!");
        }

        // Chuyển đến câu hỏi tiếp theo ngay sau khi kiểm tra đáp án
        goToNextQuestion();
    }

    private void goToNextQuestion() {
        if (questionIndex < questions.size()) {
            Question nextQuestion = questions.get(questionIndex);
            currentQuestionText = nextQuestion.getQuestion();
            answerOption1 = nextQuestion.getAnswers().get(0);
            answerOption2 = nextQuestion.getAnswers().get(1);
            answerOption3 = nextQuestion.getAnswers().get(2);
            answerOption4 = nextQuestion.getAnswers().get(3);

            populateQuestion(currentQuestionText, answerOption1, answerOption2, answerOption3, answerOption4);
            hasAnswered = false;
            questionIndex++;
        } else {
            System.out.println("Quiz completed. Your total score: " + score + " points");
        }
    }


    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void initializeQuiz() {
        // Bắt đầu trò chơi và hiển thị câu hỏi đầu tiên
        if (questions != null && !questions.isEmpty()) {
            Question firstQuestion = questions.get(0);
            currentQuestionText = firstQuestion.getQuestion();
            answerOption1 = firstQuestion.getAnswers().get(0);
            answerOption2 = firstQuestion.getAnswers().get(1);
            answerOption3 = firstQuestion.getAnswers().get(2);
            answerOption4 = firstQuestion.getAnswers().get(3);

            populateQuestion(currentQuestionText, answerOption1, answerOption2, answerOption3, answerOption4);
            hasAnswered = false;
            questionIndex++;
        } else {
            System.out.println("No questions to play.");
        }
    }

    public void populateQuestion(String questionText, String option1, String option2, String option3, String option4) {
        QuestionText.setText(questionText);
        myLabel.setText("Score: " + score);
        myCheckBox1.setText(option1);
        myCheckBox2.setText(option2);
        myCheckBox3.setText(option3);
        myCheckBox4.setText(option4);
    }
}
