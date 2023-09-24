package dictionary.tool;
import dictionary.Word;
import javafx.scene.chart.ScatterChart;

import java.io.*;
import java.util.ArrayList;

public class IOdictionary {
    public void write(ArrayList<Word> tumoi) {
        try {
            FileWriter fileWriter = new FileWriter("dictionaries.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Word word : tumoi) {
                bufferedWriter.write(word.getWord_target() + " " + word.getWord_explain());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Khong tim thay file yeu cau");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Word> read() {
        ArrayList<Word> ketqua = new ArrayList<>();
        String[] words;
        try {
            FileReader fileReader = new FileReader("dictionaries.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while (true) {
                if (line == null) {
                    break;
                }
                line = bufferedReader.readLine();
                words = line.split("\t");
                if (words.length >= 2) {
                    Word word = new Word(words[0], words[1]);
                    ketqua.add(word);
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    return ketqua;
    }
}