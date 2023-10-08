

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Game {
    private List<Question> questions;
    private int current;
    private int score;
    private Scanner scanner;




    public Game() {
        questions = new ArrayList<>();
        current = 0;
        score = 0;
        scanner = new Scanner(System.in);

        initializeQuestions();

    }

    private void initializeQuestions() {
        // Khởi tạo danh sách câu hỏi

        questions.add(new Question("What _ you doing?", new String[]{"[A] are", "[B] do", "[C] is", "[D have"}, "A"));
        // Thêm các câu hỏi khác vào danh sách
    }

    public void startGame() {
        initializeQuestions();
        System.out.println("Welcome to Vocabulary Quiz!");
        System.out.println("You will be presented with questions. Choose the correct option.");
        System.out.println("Type 'exit' to quit the game at any time.\n");

        while (current < questions.size()) {
            Question currentQuestion = questions.get(current);
            displayQuestion(currentQuestion);

            String userChoice = scanner.nextLine().trim();
            if (userChoice.equalsIgnoreCase("exit")) {
                endGame();
                return;
            }

            if (answerCurrentQuestion(userChoice)) {
                System.out.println("Correct!\n");
            } else {
                System.out.println("Incorrect. The correct answer is " + currentQuestion.getCorrectChoice() + ".\n");
            }
        }

        endGame();
    }

    private void displayQuestion(Question question) {
        System.out.println(question.getQuestionText());
        for (String choice : question.getChoices()) {
            System.out.println(choice);
        }
        System.out.print("Your choice [A/B/C/D]: ");
    }

    private boolean answerCurrentQuestion(String userChoice) {
        Question currentQuestion = questions.get(current);
        if (userChoice.equalsIgnoreCase(currentQuestion.getCorrectChoice())) {
            score++;
            current++;
            return true;
        }
        return false;
    }

    private void endGame() {
        System.out.println("Game over! Your score: " + score + " out of " + questions.size());
        scanner.close();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
