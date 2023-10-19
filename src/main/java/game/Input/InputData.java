package game.Input;
import game.question.Question;
import game.question.Category;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputData {    public static ArrayList<Category> importQuestionsFromFile(String filePath) {
        ArrayList<Category> categories = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Category currentCategory = null;
            Question currentQuestion = null;
            ArrayList<String> currentAnswers = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    // Đây là tiêu đề của một danh mục mới
                    String categoryTitle = line.substring(1).trim();
                    currentCategory = new Category(categoryTitle);
                    categories.add(currentCategory);
                } else if (currentCategory != null && !line.trim().isEmpty()) {
                    if (currentQuestion == null) {
                        // Đây là câu hỏi mới
                        currentQuestion = new Question(line, new ArrayList<>(), -1, 0);
                        currentAnswers = currentQuestion.getAnswers();
                    } else {
                        // Đây là một đáp án cho câu hỏi
                        String answerOption = line;
                        currentAnswers.add(answerOption);
                    }

                    if (currentAnswers.size() == 4) {
                        // Đã đọc đủ 4 đáp án, thêm câu hỏi vào danh mục
                        currentCategory.addQuestion(currentQuestion);
                        currentQuestion = null;
                        currentAnswers = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }

}
