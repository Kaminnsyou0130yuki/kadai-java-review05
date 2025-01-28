
package kadai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
//            ドライバクラスの読み込み
            Class.forName("com.mysql.cj.jdbc.Driver");
//            DB接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Kaminnsyou1012"
                    );
//            Stamtementオブジェクト作成
            String sql = "SELECT name, age FROM person WHERE id = ?";
            pstmt = con.prepareStatement(sql);

//            SQL実行
            System.out.print("検索キーワードを入力してください > ");
            int input = KeyInNum();

            pstmt.setInt(1, input);

            rs = pstmt.executeQuery();

            //結果表示
            while(rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");

                System.out.println(name);
                System.out.println(age);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバの読み込みに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("DBに異常が発生しました。");
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました");
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch(SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました");
                    e.printStackTrace();
                }
            }

            if(con != null) {
                try {
                    con.close();
                } catch(SQLException e) {
                    System.err.println("DB切断時に異常が発生しました");
                    e.printStackTrace();
                }
            }
        }
    }

    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;
    }

    private static int KeyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }
}

