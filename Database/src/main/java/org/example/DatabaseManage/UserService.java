package org.example.DatabaseManage;

import org.example.sqlserver.DatabaseHelper;

import java.util.List;
import java.util.Map;

public class UserService {
    private DatabaseHelper dbHelper;
    public UserService() {
        this.dbHelper = new DatabaseHelper();
    }
    public List<Map<String, Object>> getAllUsers() {
        String sql = "SELECT * FROM Users";
        return dbHelper.executeQuery(sql);
    }
    public int updateUser(String newPermission, String UserName) {
        String sql =
                "UPDATE Users " +
                        "SET Role = ? " +
                        "WHERE UserName = ?";
        return dbHelper.executeUpdate(sql, newPermission, UserName);
    }
}
