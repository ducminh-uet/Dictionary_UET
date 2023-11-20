package dictionary.tool;
import dictionary.Word;
import javafx.scene.chart.ScatterChart;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOdictionary {
    public void write( Word newWord, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Thêm từ mới vào danh sách từ điển
            bufferedWriter.write("<i>" + newWord.getWord_target() + "</i> <html>" + newWord.getWord_explain() + "</html>");
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<Word> read(String path) {
        ArrayList<Word> result = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                // Sử dụng biểu thức chính quy để trích xuất nội dung giữa thẻ <i> và <br/>
                String regexTarget = "<i>(.*?)</i>";
                String regexExplain = "<html>(.*?)</html>";
                Pattern patternTarget = Pattern.compile(regexTarget);
                Pattern patternExplain = Pattern.compile(regexExplain);

                // Lấy matcher cho từng pattern
                Matcher matcherTarget = patternTarget.matcher(line);
                Matcher matcherExplain = patternExplain.matcher(line);

                // Nếu có sự khớp, lấy nội dung giữa thẻ <i> và <br/> làm target và nội dung giữa <html> và </html> làm explain
                if (matcherTarget.find() && matcherExplain.find()) {
                    String target = matcherTarget.group(1);
                    String explain = matcherExplain.group(1);

                    // Tạo đối tượng Word và thêm vào danh sách kết quả
                    Word newWord = new Word(target, explain);
                    result.add(newWord);
                }
            }

            DictionarySorter.sortWords(result);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}