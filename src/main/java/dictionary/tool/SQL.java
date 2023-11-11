package dictionary.tool;

import dictionary.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL {
    public static List<Word> getAllWords() {
        List<Word> words = new ArrayList<>();
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/newschema";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);

            String query = "SELECT word, detail FROM tbl_edict";

            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String word_target = resultSet.getString("word");
                String word_explain = resultSet.getString("detail");
                Word word = new Word(word_target, word_explain);
                words.add(word);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return words;
    }
}