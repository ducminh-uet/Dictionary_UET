import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandLine {
    private Dictionary dictionary;

    public DictionaryCommandLine(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void showAllWords() {
        List<Word> words = dictionary.getWords();
        System.out.println("No | English | Vietnamese");
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            System.out.println((i + 1) + " | " + word.getWord_target() + " | " + word.getWord_explain());
        }
    }

    public void dictionaryBasic() {
        insertFromCommandLine();
        showAllWords();
    }

    public void insertFromCommandLine() {
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

    public void dictionarySearcher(String prefix) {
        List<Word> matchedWords = new ArrayList<>();
        for (Word word : dictionary.getWords()) {
            if (word.getWord_target().toLowerCase().startsWith(prefix.toLowerCase())) {
                matchedWords.add(word);
            }
        }

        if (matchedWords.isEmpty()) {
            System.out.println("No matching words found.");
        } else {
            System.out.println("Matching words starting with '" + prefix + "':");
            for (int i = 0; i < matchedWords.size(); i++) {
                Word word = matchedWords.get(i);
                System.out.println((i + 1) + " | " + word.getWord_target() + " | " + word.getWord_explain());
            }
        }
    }
    public void dictionaryAdvanced() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to My Application!");
            System.out.println("[0] Exit");
            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Update");
            System.out.println("[4] Display");
            System.out.println("[5] Lookup");
            System.out.println("[6] Search");
            System.out.println("[7] Game");
            System.out.println("[8] Import from file");
            System.out.println("[9] Export to file");

            System.out.print("Your action: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "0":
                    System.exit(0);
                    break;
                case "1":
                    // Thêm từ
                    System.out.print("Enter word in English: ");
                    String word_target = scanner.nextLine();
                    System.out.print("Enter word explanation in Vietnamese: ");
                    String word_explain = scanner.nextLine();
                    Word word = new Word(word_target, word_explain);
                    dictionary.addWord(word);

                    break;
                case "2":
                    // Xóa từ
                    break;
                case "3":
                    // Sửa từ
                    break;
                case "4":
                    showAllWords();
                    break;
                case "5":
                    // Tra cứu từ
                    break;
                case "6":
                    System.out.print("Enter a prefix to search for: ");
                    String prefix = scanner.nextLine();
                    dictionarySearcher(prefix);
                    break;
                case "7":
                    // Truy cập phần Game
                    break;
                case "8":
                    // Import từ file
                    break;
                case "9":
                    // Export ra file
                    break;
                default:
                    System.out.println("Action not supported.");
                    break;
            }
        }
    }
}
