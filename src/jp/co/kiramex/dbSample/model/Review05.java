package jp.co.kiramex.dbSample.model;

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
        //データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        //ドライバのクラスを読み込む
        Class.forName("com.mysql.cj.jdbc.Driver");

        //DBと接続する
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "GrandTester881077");

        //DBとやりとりする窓口の作成
        String sql = "SELECT * FROM person WHERE id = ?";
        pstmt = con.prepareStatement(sql);

        //Select文の実行と結果を格納/代入
        System.out.print("検索キーワードを入力してください > ");
        int input = keyInAsInt();

        //PrepareStatementオブジェクトの？に値をセット
        pstmt.setInt(1, input);

        rs = pstmt.executeQuery();

        //結果を表示する
        while(rs.next()){
            //Name列の値の取得
            String name = rs.getString("Name");
            //age列の値の取得
            int age = rs.getInt("age");

            //取得した値を表示
            System.out.println(name);
            System.out.println(age);
        }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            //接続を閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }
    //キーボードから入力された値をIntで返す。
    private static int keyInAsInt() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
            return Integer.parseInt(line);

        }catch(IOException e) {
            e.printStackTrace();
            //エラー時に0を返す
            return 0;


        }catch(NumberFormatException e) {
            e.printStackTrace();
            //エラー時に0を返す
            return 0;
        }
}
}
