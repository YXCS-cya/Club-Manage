package org.example.Member;

import org.example.DatabaseManage.MemberService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;
import java.util.List;


public class MemberManagerFrame extends JFrame {
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private JButton  deleteButton;

    private List<Member> members= new ArrayList<>();
    // 社员列表
    private String ClubName;
    private MemberService memberService = new MemberService();
    private List<Map<String, Object>> membersData = memberService.getMember();
    public MemberManagerFrame(String ClubName) {
        this.ClubName = ClubName;
        // 初始化社员数据
        //members = new ArrayList<>();
        for (Map<String, Object> memberMap : membersData) {
            String checkClub = (String) memberMap.get("Club_Name");
            if(checkClub.equals(ClubName)){
                int id = (int) memberMap.get("Member_ID");
                String name = (String) memberMap.get("Member_Name");
                String role = (String) memberMap.get("Member_Position");
                String grade = (String) memberMap.get("Grade");
                String major = (String) memberMap.get("Major");
                // 创建 Member 对象并添加到成员列表
                Member member = new Member(id, name, role, grade, major);
                members.add(member);
            }
        }
        // 创建表格模型，设置列名
        String[] columnNames = {"ID", "姓名", "身份", "年级", "专业"};
        tableModel = new DefaultTableModel(columnNames, 0);
        // 添加数据到表格模型
        for (Member member : members) {
            tableModel.addRow(new Object[]{
                    member.getId(),
                    member.getName(),
                    member.getRole(),
                    member.getGrade(),
                    member.getMajor()
            });
        }
        // 创建 JTable，并设置模型
        memberTable = new JTable(tableModel);
        // 设置表格的选择模式为单行选择
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(memberTable);
        // 创建按钮
        //modifyButton = new JButton("修改社员信息");
        deleteButton = new JButton("删除社员信息");
        // 设置按钮点击事件
        //modifyButton.addActionListener(e -> modifyMemberInfo());
        deleteButton.addActionListener(e -> deleteMemberInfo());
        // 布局
        setLayout(new BorderLayout());
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        //buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 设置窗口
        setTitle(ClubName + "社员管理");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 居中显示
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    // 修改社员信息
//    private void modifyMemberInfo() {
//        int selectedRow = memberTable.getSelectedRow();
//        if (selectedRow != -1) {
//            String id = (String) memberTable.getValueAt(selectedRow, 0);
//            String name = (String) memberTable.getValueAt(selectedRow, 1);
//            String role = (String) memberTable.getValueAt(selectedRow, 2);
//            String grade = (String) memberTable.getValueAt(selectedRow, 3);
//            String major = (String) memberTable.getValueAt(selectedRow, 4);
//
//            // 模拟修改操作，弹出一个对话框（实际中应该是一个编辑窗口）
//            JOptionPane.showMessageDialog(this,
//                    "修改社员信息：\nID: " + id + "\n姓名: " + name + "\n身份: " + role + "\n年级: " + grade + "\n专业: " + major,
//                    "修改社员信息",
//                    JOptionPane.INFORMATION_MESSAGE);
//        } else {
//            JOptionPane.showMessageDialog(this,
//                    "请先选择一个社员。",
//                    "提示",
//                    JOptionPane.WARNING_MESSAGE);
//        }
//    }

    // 删除社员信息
    private void deleteMemberInfo() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow != -1) {
            String name = (String) memberTable.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "确认删除社员：" + name + " 吗？",
                    "删除确认",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // 从表格模型中删除该行数据
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this,
                        "社员 " + name + " 删除成功。",
                        "删除成功",
                        JOptionPane.INFORMATION_MESSAGE);
                memberService.deleteMember(name);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "请先选择一个社员。",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}

