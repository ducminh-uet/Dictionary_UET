package dictionary;

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
}
