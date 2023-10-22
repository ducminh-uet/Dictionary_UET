package game.tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import game.question.Question;

public class InputData {
    public static ArrayList<Question> loadQuestionsFromFile(String filePath) {
        ArrayList<Question> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Question currentQuestion = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title:")) {
                    currentQuestion = new Question();
                    currentQuestion.setTitle(line.substring("Title:".length()).trim());
                } else if (line.startsWith("QuestionText:")) {
                    currentQuestion.setQuestionText(line.substring("QuestionText:".length()).trim());
                } else if (line.startsWith("Answer:")) {
                    currentQuestion.getAnswer().add(line.substring("Answer:".length()).trim());
                } else if (line.startsWith("CorrectAnswer:")) {
                    currentQuestion.setCorrectAnswer(line.substring("CorrectAnswer:".length()).trim());
                } else if (line.startsWith("IsAttempt:")) {
                    currentQuestion.setAttempt(Boolean.parseBoolean(line.substring("IsAttempt:".length()).trim()));
                    questions.add(currentQuestion); // Add the current question to the list.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }
}