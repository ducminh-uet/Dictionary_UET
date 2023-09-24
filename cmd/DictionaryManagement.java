// File DictionaryManagement.java
import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    public void insertFromCommandLine(Dictionary dictionary) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of words: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < n; i++) {
            System.out.print("Enter word in English: ");
            String word_target = scanner.nextLine();
            System.out.print("Enter word explanation in Vietnamese: ");
            String word_explain = scanner.nextLine();

            Word word = new Word(word_target, word_explain);
            dictionary.addWord(word);
        }
    }
    String filename = "dictionaries.txt";
    public void insertFromFile(Dictionary dictionary, String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    String word_target = parts[0];
                    String word_explain = parts[1];
                    Word word = new Word(word_target, word_explain);
                    dictionary.addWord(word);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    public void dictionaryLookup(Dictionary dictionary, String word) {
        boolean found = false;
        for (Word w : dictionary.getWords()) {
            if (w.getWord_target().equalsIgnoreCase(word)) {
                System.out.println("English: " + w.getWord_target());
                System.out.println("Vietnamese: " + w.getWord_explain());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Word not found in the dictionary.");
        }
    }

    public void addWord(Dictionary dictionary, String word_target, String word_explain) {
        Word word = new Word(word_target, word_explain);
        dictionary.addWord(word);
    }

    public void editWord(Dictionary dictionary, String word_target, String newWord_explain) {
        for (Word w : dictionary.getWords()) {
            if (w.getWord_target().equalsIgnoreCase(word_target)) {
                w.setWord_explain(newWord_explain);
                return;
            }
        }
        System.out.println("Word not found in the dictionary.");
    }

    public void deleteWord(Dictionary dictionary, String word_target) {
        for (Word w : dictionary.getWords()) {
            if (w.getWord_target().equalsIgnoreCase(word_target)) {
                dictionary.getWords().remove(w);
                return;
            }
        }
        System.out.println("Word not found in the dictionary.");
    }

    public void saveToFile(Dictionary dictionary, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (Word w : dictionary.getWords()) {
                writer.write(w.getWord_target() + "\t" + w.getWord_explain() + "\n");
            }
            writer.close();
            System.out.println("Dictionary saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void dictionaryExportToFile(Dictionary dictionary, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (Word w : dictionary.getWords()) {
                writer.write(w.getWord_target() + "\t" + w.getWord_explain() + "\n");
            }
            writer.close();
            System.out.println("Dictionary exported to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
