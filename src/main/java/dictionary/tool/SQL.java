package dictionary.tool;
import dictionary.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL {
    /**
     * Ý tưởng là query từ riêng, nghĩa riêng rồi trả về 2 cái danh sách từ với nghĩa.
     * Class này đến thời điểm commit chỉ query từ với nghĩa.
     * Method đã là static nên anh em cứ đập SQL.getAllWords,...mà không cần tạo đối tượng.
     */
    public static List<String> getAllWords() {

        List<String> words = new ArrayList<>();
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/newschema";
            String username = "root";
            String password = "a2vodich";

            conn = DriverManager.getConnection(url, username, password);

            // Ko lấy index <72, toàn từ linh tinh.
            String query = "SELECT word FROM tbl_edict where idx > 72";

            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String word = resultSet.getString("word");
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

    public static List<String> getAllDetails() {
        List<String> details = new ArrayList<>();
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/newschema";
            String username = "root";
            String password = "a2vodich";

            conn = DriverManager.getConnection(url, username, password);


            String query = "SELECT detail FROM tbl_edict where idx >72";

            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String detail = resultSet.getString("detail");
                details.add(detail);
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
        return details;
    }
}