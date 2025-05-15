package org.example.VIEW;

import org.example.DatabaseManage.ClubService;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class CreatActTable {

    private ClubService clubService = new ClubService();
    private String Key;


    private JTable table = null;
    public CreatActTable(String type, String Key){

        this.Key = Key;
        List<Map<String, Object>> activity = clubService.getClubAct(Key);
        if(type.equals("按社团/活动查询")){
            String[] columns = {"社团名称", "活动名称", "到场人数", "活动地点","活动概述"};
            Object[][] data = new Object[activity.size()][];
            for (int i = 0; i < activity.size(); i++) {
                Map<String, Object> club = activity.get(i);
                // 提取 Map 中的社团名称、负责人和社团类型
                String clubName = (String) club.get("社团名称");
                String ActName = (String) club.get("活动名称");
                int Number = (int) club.get("到场人数");
                String Add = (String) club.get("活动地点");
                String Describ = (String) club.get("活动概述");

                // 填充二维数组中的一行数据
                data[i] = new Object[] { clubName, ActName, Number, Add, Describ };
            }
            table = new JTable(data, columns);
        }
    }

    public JTable getTable() {
        return table;
    }
}
