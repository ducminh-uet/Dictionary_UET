package game.screenGame;

import game.Input.InputData;
import game.question.Category;
import game.question.Question;
import game.question.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class QuizController {

    private Quiz quiz;
    private Stage stage;
    private Parent root;
    private Scene scene;

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

    public void initialize() {
        ArrayList<Category> categories = InputData.importQuestionsFromFile("data.txt");
        quiz = new Quiz(categories);
        displayQuestionAndAnswer();
    }

    public void backToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MenuGameController.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayQuestionAndAnswer() {
        String category = quiz.getCategories().get(quiz.getCurrentCategoryIndex()).getTitle();
        categoryText.setText("Category: " + category);
        Question question = quiz.getCurrentQuestion();
        questionText.setText("Question: " + question.getQuestion());

        ArrayList<String> answerOptions = question.getAnswers();
        Option1.setText("A. " + answerOptions.get(0));
        Option2.setText("B. " + answerOptions.get(1));
        Option3.setText("C. " + answerOptions.get(2));
        Option4.setText("D. " + answerOptions.get(3));
    }

    private void checkAnswer(int selectedAnswerIndex) {
        Question question = quiz.getCurrentQuestion();
        if (selectedAnswerIndex == question.getCorrectAnswerIndex()) {
            // Điểm tăng lên khi trả lời đúng
            // quiz.increaseScore();
            System.out.println("Correct answer!");
        } else {
            System.out.println("Incorrect answer!");
        }
        // quiz.goToNextQuestion();

//        // Kiểm tra xem còn câu hỏi tiếp theo hay không
//        if (quiz.hasMoreQuestions()) {
//            displayQuestionAndAnswer();
//        } else {
//            // Quiz đã kết thúc, bạn có thể thực hiện hành động nào đó ở đây.
//            System.out.println("Quiz ended. Your total score: " + quiz.getScore() + " points");
//        }
    }

    public void chooseTheAnswer(ActionEvent event) {
        if (Option1.isSelected()) {
            checkAnswer(0);
        } else if (Option2.isSelected()) {
            checkAnswer(1);
        } else if (Option3.isSelected()) {
            checkAnswer(2);
        } else if (Option4.isSelected()) {
            checkAnswer(3);
        }
    }
}
