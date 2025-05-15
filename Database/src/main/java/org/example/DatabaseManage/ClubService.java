package org.example.DatabaseManage;

import org.example.sqlserver.DatabaseHelper;

import java.sql.*;
import java.util.List;

import java.util.*;

public class ClubService {

    private DatabaseHelper dbHelper;

    public ClubService() {
        this.dbHelper = new DatabaseHelper();
    }

    // 获取所有社团数据
    public List<Map<String, Object>> getAllClubs() {
        String sql = "SELECT * FROM Club";
        return dbHelper.executeQuery(sql);
    }
    public List<Map<String, Object>> getClubItro() {
        String sql = "SELECT * FROM ClubLeaderView";
        return dbHelper.executeQuery(sql);
    }

    public int addClub(int clubId, String newClubName,String newClubType,int newManagerId,
                       String newClubTeacher, int number, String newClubAdd) {
        String sql = "INSERT INTO Club (Club_ID,Club_Name, Club_Type,Club_MangeID, Club_Teacher,Club_Number, Club_Add) " +
                "VALUES (?,?,?,?,?,?,?);";
        return dbHelper.executeUpdate(sql, clubId, newClubName, newClubType, newManagerId, newClubTeacher,number, newClubAdd);
    }
    public int deleteClub(int clubId) {
        String sql = "DELETE FROM Club WHERE Club_ID = ?";
        return dbHelper.executeUpdate(sql, clubId);
    }

    public int updateClub(int clubId, String newClubName,String newClubType,int newManagerId,
                          String newClubTeacher, String newClubAdd) {
        String sql =
                "UPDATE Club " +
                        "SET Club_Name = ?, Club_Type = ?, Club_MangeID = ?, Club_Teacher = ?,  Club_Add = ?" +
                        " WHERE Club_ID = ?";
        return dbHelper.executeUpdate(sql, newClubName, newClubType, newManagerId, newClubTeacher, newClubAdd, clubId);
    }

    public List<Map<String, Object>> getClubAct(String key) {
        System.out.println(key);
        String sql = "SELECT * FROM ClubactView WHERE 社团名称 LIKE '%" + key + "%' or 活动名称 LIKE '%"+ key + "%'";
        return dbHelper.executeQuery(sql);
    }

//    public static void main(String[] args) {
//        ClubService clubService = new ClubService();
//        List<Map<String, Object>> clubs = clubService.getAllClubs();
//
//        // 输出查询结果
//        for (Map<String, Object> club : clubs) {
//            System.out.println("Club ID: " + club.get("Club_ID") + ", Club Name: " + club.get("Club_Name"));
//        }
//    }
}


