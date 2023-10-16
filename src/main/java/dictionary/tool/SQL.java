package dictionary.tool;
import java.sql.*;

public class SQL {
    public static void query() {
        Connection conn = null;
        try {
            // 3 dòng này là tên bảng với cơ sở dữ liệu thôi.
            String url = "jdbc:mysql://localhost:3306/newschema";
            String username = "root";
            String password = "a2vodich";

            conn = DriverManager.getConnection(url, username, password);

            String query = "SELECT word, detail FROM tbl_edict WHERE idx = ?";

            // Có 140000 từ ứng với index cơ ae ko lo.
            for (int idx : new int[]{6000, 120000, 33333, 45432}) {
                // Tạo 1 truy vấn
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, idx);

                //Trả kết quả về
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String word = resultSet.getString("word");
                    String detail = resultSet.getString("detail");

                    System.out.println("Word: " + word);
                    System.out.println("Detail: " + detail+"\n");
                }

                // Xong việc phải close lại.
                resultSet.close();
                statement.close();
            }
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
    }
}