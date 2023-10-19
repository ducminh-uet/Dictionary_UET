package game.question;

import game.Input.InputData;
import game.question.Category;
import game.question.Question;

import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
    private ArrayList<Category> categories; //Nhóm câu hỏi
    private int currentCategoryIndex; // Nhóm câu hỏi hiện tại
    private int currentQuestionIndex; // Số thứ tự câu hỏi hiêện tại
    private int score; // Điểm
    private Scanner scanner;
    // Constructor
    public Quiz(ArrayList<Category> categories) {
        this.categories = categories;
        this.currentCategoryIndex = 0;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.scanner = new Scanner(System.in);
    }
    // Getter and Setter

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public int getCurrentCategoryIndex() {
        return currentCategoryIndex;
    }

    public void setCurrentCategoryIndex(int currentCategoryIndex) {
        this.currentCategoryIndex = currentCategoryIndex;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // get current question
    public Question getCurrentQuestion(){
        return this.categories.get(currentCategoryIndex).getQuestions().get(getCurrentQuestionIndex());
    }
    public void startQuiz() {
        if (categories.isEmpty()) {
            System.out.println("No categories found. Quiz cannot start.");
            return;
        }

        while (currentCategoryIndex < categories.size()) {
            Category currentCategory = categories.get(currentCategoryIndex);
            ArrayList<Question> questions = currentCategory.getQuestions();

            if (currentQuestionIndex < questions.size()) {
                Question currentQuestion = questions.get(currentQuestionIndex);
                displayQuestion(currentQuestion);

                // Đọc câu trả lời từ người chơi
                int userAnswer = getUserAnswer(questions.size());

                if (currentQuestion.checkAnswer(userAnswer)) {
                    System.out.println("Correct! You earned " + currentQuestion.getScore() + " points.");
                    score += currentQuestion.getScore();
                } else {
                    System.out.println("Wrong answer. The correct answer is: " + currentQuestion.getCorrectAnswerIndex());
                }

                currentQuestionIndex++;
            } else {
                System.out.println("Category completed. Your score: " + score + " points");
                currentCategoryIndex++;
                currentQuestionIndex = 0;
            }
        }

        System.out.println("Quiz completed. Your total score: " + score + " points");
    }

    private int getUserAnswer(int maxOptions) {
        int userAnswer = -1;
        do {
            System.out.print("Your answer (1-" + maxOptions + "): ");
            try {
                userAnswer = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (userAnswer < 1 || userAnswer > maxOptions);
        return userAnswer - 1;
    }

    private void displayQuestion(Question question) {
        System.out.println("\nCategory: " + categories.get(currentCategoryIndex).getTitle());
        System.out.println("Question: " + question.getQuestion());
        for (int i = 0; i < question.getAnswers().size(); i++) {
            System.out.println((i + 1) + ". " + question.getAnswers().get(i));
        }
    }

    public static void main(String[] args) {
        ArrayList<Category> categories = InputData.importQuestionsFromFile("data.txt");
        Quiz quiz = new Quiz(categories);
        quiz.startQuiz();
    }
}
