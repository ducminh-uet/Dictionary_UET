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
            String url = "jdbc:mysql://localhost:3306/test";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);

            String query = "SELECT word, detail FROM tbl_edict order by word";

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

    public static void addWordToDataBase(String word, String detail) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            String url = "jdbc:mysql://localhost:3306/test";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);

            // Chuẩn bị câu lệnh SQL INSERT
            String query = "INSERT INTO tbl_edict (word, detail) VALUES (?, ?)";
            statement = conn.prepareStatement(query);

            // Đặt giá trị cho các tham số
            statement.setString(1, word);
            statement.setString(2, detail);

            // Thực hiện câu lệnh SQL INSERT
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thêm từ mới thành công!");
            } else {
                System.out.println("Không có từ nào được thêm.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void replaceWord(String word, String newDetails) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            String url = "jdbc:mysql://localhost:3306/test";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);

            // Chuẩn bị câu lệnh SQL UPDATE
            String query = "UPDATE tbl_edict SET detail = ? WHERE word = ?";
            statement = conn.prepareStatement(query);

            // Đặt giá trị cho các tham số
            statement.setString(1, newDetails);
            statement.setString(2, word);

            // Thực hiện câu lệnh SQL UPDATE
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thay đổi nội dung của từ thành công!");
            } else {
                System.out.println("Không có từ nào được thay đổi.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void deleteWord(String wordToDelete) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            String url = "jdbc:mysql://localhost:3306/test";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);

            // Chuẩn bị câu lệnh SQL DELETE
            String query = "DELETE FROM tbl_edict WHERE word = ?";
            statement = conn.prepareStatement(query);

            // Đặt giá trị cho tham số
            statement.setString(1, wordToDelete);

            // Thực hiện câu lệnh SQL DELETE
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa từ khỏi cơ sở dữ liệu thành công!");
            } else {
                System.out.println("Không có từ nào được xóa.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}