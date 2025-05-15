package org.example.DatabaseManage;

import org.example.sqlserver.DatabaseHelper;

import java.util.List;
import java.util.Map;

public class MemberService {
    private DatabaseHelper dbHelper;

    public MemberService() {
        this.dbHelper = new DatabaseHelper();
    }

    // 获取所有学生数据
    public List<Map<String, Object>> getAllMember() {
        String sql = "SELECT * FROM Member";
        return dbHelper.executeQuery(sql);
    }
    public List<Map<String, Object>> getMember() {
        String sql = "SELECT * " +
                "FROM ClubMemberView " ;

        return dbHelper.executeQuery(sql);
    }

//    public int updateMember(int clubId, String newClubName,String newClubType,int newManagerId,
//                          String newClubTeacher, String newClubAdd) {
//        String sql =
//                "UPDATE Club " +
//                        "SET Club_Name = ?, Club_Type = ?, Club_MangeID = ?, Club_Teacher = ?,  Club_Add = ?" +
//                        " WHERE Club_ID = ?";
//        return dbHelper.executeUpdate(sql, newClubName, newClubType, newManagerId, newClubTeacher, newClubAdd, clubId);
//    }
    public int deleteMember(String Member_Name) {
        String sql = "DELETE FROM Member WHERE Member_Name = ?";
        return dbHelper.executeUpdate(sql, Member_Name);
    }
}
