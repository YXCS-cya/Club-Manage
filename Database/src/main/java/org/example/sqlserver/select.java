

package org.example.sqlserver;

import java.sql.*;

public class select {
//    public static void main(String[] args) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String userName = "sa";
//            String password = "123456";
//            String dbURL="jdbc:sqlserver://localhost:1433;databaseName=社团管理系统;encrypt=true;trustServerCertificate=true";
//            conn = DriverManager.getConnection(dbURL,
//                    userName,
//                    password);
//            ps =conn.prepareStatement("select * from Club" +
//                    "");
//            rs = ps.executeQuery();
//            while(rs.next()) {
//                int id = rs.getInt(1);
//                String name = rs.getString(2);
//                System.out.println(id + " " + name);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (rs != null){
//                    rs.close();
//                }
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
