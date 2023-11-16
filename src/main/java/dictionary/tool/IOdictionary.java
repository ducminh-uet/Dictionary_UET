package dictionary.tool;
import dictionary.Word;
import javafx.scene.chart.ScatterChart;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOdictionary {
    public void write(ArrayList<Word> words, Word newWord) {
        try {
            FileWriter fileWriter = new FileWriter("dictionaries.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Thêm từ mới vào danh sách từ điển
            words.add(newWord);

            // Ghi lại toàn bộ danh sách từ điển vào tệp tin
            for (Word word : words) {
                bufferedWriter.write(word.getWord_target() + " " + word.getWord_explain());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<Word> read() {
        ArrayList<Word> result = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader("src/main/java/data/V_E.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            // Biểu thức chính quy để tìm từ và nghĩa từ định dạng HTML
            String regex = "<i>(.*?)</i><br/><ul><li><font color='#cc0000'><b>(.*?)</b></font></li></ul>";
            Pattern pattern = Pattern.compile(regex);

            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String word = matcher.group(1); // Nhóm 1 là từ
                    String meaning = matcher.group(2); // Nhóm 2 là nghĩa

                    // Tạo đối tượng Word và thêm vào danh sách kết quả
                    Word newWord = new Word(word, meaning);
                    result.add(newWord);
                }
            }

            DictionarySorter.sortWords(result);
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}