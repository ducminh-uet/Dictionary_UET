package game.Input;
import game.question.Question;
import game.question.Category;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputData {
    // Import Questions From File
    public static ArrayList<Category> importQuestionsFromFile(String filePath) {
        ArrayList<Category> categories = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Category currentCategory = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    // Đây là tiêu đề của một danh mục mới
                    String categoryTitle = line.substring(1).trim();
                    currentCategory = new Category(categoryTitle);
                    categories.add(currentCategory);
                } else if (currentCategory != null && !line.trim().isEmpty()) {
                    // Đây là câu hỏi trong danh mục hiện tại
                    String[] parts = line.split(",");
                    if (parts.length == 5) {
                        String questionText = parts[0].trim();
                        String answer = parts[1].trim();
                        int score = Integer.parseInt(parts[2].trim());
                        int correctAnswerIndex = Integer.parseInt(parts[3].trim());
                        ArrayList<String> answers = new ArrayList<>();
                        String[] answerOptions = parts[4].split(";");
                        for (String answerOption : answerOptions) {
                            answers.add(answerOption.trim());
                        }
                        Question question = new Question(questionText, answers, correctAnswerIndex, score);
                        currentCategory.addQuestion(question);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
