package screen;

import dictionary.DictionaryCommandLine;
import java.sql.*;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Kết nối vào cơ sở dữ liệu
            String url = "jdbc:mysql://localhost:3306/newschema";
            String username = "root";
            String password = "a2vodich";
            connection = DriverManager.getConnection(url, username, password);

            // Kiểm tra kết nối
            if (connection != null) {
                System.out.println("Kết nối thành công!");

                // Tạo câu truy vấn SQL
                StringBuffer bf = new StringBuffer("select * from tbl_edict where word=\"");
                System.out.println("Nhap tu can tra");
                Scanner sc = new Scanner(System.in);
                String tumoi = sc.next();
                bf.append(tumoi);
                bf.append("\"");
            //   String sql = "SELECT * FROM entries";
                String sql = bf.toString();
            //    System.out.println(sql);

                // Thực hiện truy vấn
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                // Xử lý kết quả
                while (resultSet.next()) {
                    String word  = resultSet.getString("word");
                    StringBuilder definition = new StringBuilder( resultSet.getString("detail"));
                    int startIndex = definition.indexOf("-");
                    int lessThanIndex = definition.indexOf("<", startIndex);

                    if (startIndex != -1 && lessThanIndex != -1) {
                        String extractedString = definition.substring(startIndex+2, lessThanIndex);
                        System.out.println(extractedString);
                    } else {
                        System.out.println("Không tìm thấy '-' hoặc '<' trong chuỗi.");
                    }
                //    System.out.println("Word: " + word + ", Definition: " + definition);
                }

                // Đóng kết nối
                connection.close();
            } else {
                System.out.println("Kết nối thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Đảm bảo kết nối được đóng
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
