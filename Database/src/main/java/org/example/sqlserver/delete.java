package org.example.sqlserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class delete {
//    public static void main(String[] args) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            conn = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName = 社团管理系统", "sa", "123456");
//            ps =conn.prepareStatement("delete from student where name = ?");
//            ps.setString(1,"王二麻子");
//            int i = ps.executeUpdate();
//            if (i > 0){
//                System.out.println("删除成功！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (ps != null){
//                    ps.close();
//                }
//                if(conn != null){
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
