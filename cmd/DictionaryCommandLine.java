
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;

public class DictionaryCommandLine{
    private DictionaryManagement dictionaryManagement;
    public DictionaryCommandLine() {

        dictionaryManagement = new DictionaryManagement();
    }
    public void showAllWords(){
        ArrayList<Word> words = dictionaryManagement.dictionary.getDictionary();
        System.out.println("No | English | Vietnamese");
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            System.out.println((i + 1) + " | " + word.getWord_target() + " | " + word.getWord_explain());
        }
    }

    public void dictionaryBasic() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----Dictionary Basic-----");
        System.out.println("1. Insert word from command line.");
        System.out.println("2. Show all words.");
        System.out.println("0. Exit.");

        while (true) {
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter word (English): ");
                    String target = scanner.nextLine();
                    System.out.println("Enter explanation (Vietnamese): ");
                    String explain = scanner.nextLine();
                    Word newWord = new Word(target,explain);
                    dictionaryManagement.addWord(newWord);
                    break;
                case 2:
                    showAllWords();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    public void dictionarySearcher(String prefix) {
        ArrayList<Word> matchedWords = new ArrayList<>();
        for (Word word : dictionaryManagement.dictionary.getDictionary()) {
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
                    dictionaryManagement.addWord(word);

                    break;
                case "2":
                    // Xóa từ
                    System.out.print("Enter word in English to remove: ");
                    String word_remove = scanner.nextLine();
                    dictionaryManagement.removeWord(word_remove);
                    System.out.println("Word removed.");
                    break;
                case "3":
                    // Sửa từ
                    System.out.print("Enter word to edit: ");
                    String word_edit = scanner.nextLine();
                    // Tìm từ cần sửa
                    Word word_new = null;
                    for (Word w : dictionaryManagement.dictionary.getDictionary()) {
                        if (w.getWord_target().equals(word_edit)) {
                            word_new = w;
                            break;
                        }
                    }

                    if (word_new != null) {
                        System.out.print("Enter new explanation (Vietnamese): ");
                        String newExplanation = scanner.nextLine();
                        word_new.setWord_explain(newExplanation);
                        System.out.println("Word updated successfully.");
                    } else {
                        System.out.println("Word not found in the dictionary.");
                    }
                    break;
                case "4":
                    showAllWords();
                    //Hiển thị danh sách các từ trong từ điển
                    break;
                case "5":
                    // Tra cứu từ
                    //

                    System.out.print("Enter word to look up: ");
                    String wordToLookup = scanner.nextLine();

                    // Tìm từ trong từ điển
                    Word word_found = null;
                    for (Word w : dictionaryManagement.dictionary.getDictionary()) {
                        if (w.getWord_target().equals(wordToLookup)) {
                            word_found = w;
                            break;
                        }
                    }

                    if (word_found != null) {
                        System.out.println("English: " + word_found.getWord_target());
                        System.out.println("Vietnamese: " + word_found.getWord_explain());
                    } else {
                        System.out.println("Word not found in the dictionary.");
                    }
                    break;

                case "6":
                    System.out.print("Enter a prefix to search for: ");
                    String prefix = scanner.nextLine();
                    dictionarySearcher(prefix);
                    break;
                case "7":
                    // Truy cập phần Game
                    Game game = new Game();
                    game.startGame();

                    break;

                case "8":
                    // Import từ file
                    //Chỉnh sửa định dạng
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
