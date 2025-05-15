package org.example;



import org.example.DatabaseManage.ClubService;
import org.example.sqlserver.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class ClubInfoManager extends JFrame {
    private JTextField clubNameField, clubTypeField, instructorField, clubLocationField, leaderIdField;
    private JLabel clubIdLabel;
    private JButton saveButton;
    //private ClubInfo clubInfo;
    private String userId;
    private Map<String, Object> map = null;

    private ClubService clubService = new ClubService();
    //private DatabaseHelper dbHelper;
    //调用数据库控制类

    // 构造方法
    public ClubInfoManager(String UserID,Map<String, Object> map) {
        //String userId, ClubInfo clubInfo
        this.map = map;
        this.userId = UserID;
        //this.dbHelper = new DatabaseHelper();
        //this.clubInfo = clubInfo;

        // 检查用户是否是负责人
        if (!haveRight()) {
            JOptionPane.showMessageDialog(this, "您不是负责人，无法修改社团信息！", "权限不足",
                    JOptionPane.WARNING_MESSAGE);
            this.dispose();  // 关闭窗口
            return;
        }

        // 设置窗口属性
        setTitle("社团信息修改");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // 居中显示
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        // 初始化社团信息字段
        clubIdLabel = new JLabel("社团ID: " + map.get("Club_ID"));
        //leaderIdLabel = new JLabel("负责人ID: " + map.get("Club_MangeID"));

        clubNameField = new JTextField(String.valueOf(map.get("Club_Name")));
        clubTypeField = new JTextField(String.valueOf(map.get("Club_Type")));
        leaderIdField = new JTextField(String.valueOf(map.get("Club_MangeID")));
        instructorField = new JTextField(String.valueOf(map.get("Club_Teacher")));
        clubLocationField = new JTextField(String.valueOf(map.get("Club_Add")));

        saveButton = new JButton("保存修改");

        // 添加组件到面板
        panel.add(new JLabel("社团名称:"));
        panel.add(clubNameField);
        panel.add(new JLabel("社团类型:"));
        panel.add(clubTypeField);
        panel.add(new JLabel("负责人ID:"));
        panel.add(leaderIdField);
        panel.add(new JLabel("指导老师:"));
        panel.add(instructorField);
        panel.add(new JLabel("社团位置:"));
        panel.add(clubLocationField);

        panel.add(clubIdLabel);
        //panel.add(leaderIdLabel);

        panel.add(saveButton);

        // 将面板添加到窗口
        add(panel);

        // 保存按钮事件
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新社团信息
                String Club_Name, Club_Type , Club_MangeID, Club_Teacher, Club_Add;
                Club_Name = clubNameField.getText();
                Club_Type = clubTypeField.getText();
                Club_MangeID = leaderIdField.getText();
                Club_Teacher = instructorField.getText();
                Club_Add = clubLocationField.getText();

                clubService.updateClub((Integer)map.get("Club_ID"),Club_Name, Club_Type ,
                        Integer.parseInt(Club_MangeID), Club_Teacher, Club_Add);

                // 弹窗提示保存成功   !!待修改，加判断是否成功
                JOptionPane.showMessageDialog(ClubInfoManager.this, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                // 关闭窗口
                dispose();
            }
        });

        // 显示窗口
        setVisible(true);
    }



    //看情况增加登陆界面时在修改
    private boolean haveRight(){
        if(this.userId == map.get("Club_MangeID")){
            return true;
        }
            return true;

    }

}

