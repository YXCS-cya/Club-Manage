package org.example;


import org.example.DatabaseManage.ClubService;
import org.example.Member.MemberManagerFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Optional;
public class ClubInfoApp {

    public static void main(String[] args) {
        // 设置UI界面
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClubInfoFrame();
            }
        });
    }
}

class ClubInfoFrame extends JFrame {
    public void setChosenClub(String chosenClub) {
        this.chosenClub = chosenClub;
    }
    public void setUserId(String userId) {
        UserId = userId;
    }
    private String UserId;
    private String chosenClub = null;
    private JPanel panel;
    private JTextArea clubInfoTextArea;
    private JButton memberManageButton;
    private JButton infoManageButton;
    private JButton AddClubButton;
    //private JButton activityReviewButton;
    private JButton DeleteClubButton;
    private JList<String> clubList;
    // 用于显示社团名称
    private DefaultListModel<String> clubListModel;
    // 用于管理社团名称数据
    private ClubService clubService = new ClubService();
    //创建社团数据库管理类
    private List<Map<String, Object>> clubs = clubService.getAllClubs();
    //把社团的信息存入容器列表供调用
    private Map<String, Object> map = null;
    //存储当前选中的社团信息

    // 构造方法，创建并设置GUI界面
    public ClubInfoFrame() {
        // 设置窗口标题
        setTitle("社团信息管理");
        // 设置窗口大小
        setSize(600, 400);
        // 设置窗口关闭时退出程序
        //setDefaultCloseOpertion(JFrame.DISPOSE_ON_CLOSE);
        // 创建主面板
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        // 创建社团名称列表
        clubListModel = new DefaultListModel<>();
        for (Map<String, Object> club : clubs) {
            clubListModel.addElement((String) club.get("Club_Name"));
        }
//        clubListModel.addElement("编程爱好者");
//        clubListModel.addElement("音乐社");
//        clubListModel.addElement("摄影社");
//        clubListModel.addElement("篮球队");

        clubList = new JList<>(clubListModel);
        clubList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clubList.setVisibleRowCount(5);
        JScrollPane clubListScrollPane = new JScrollPane(clubList);
        panel.add(clubListScrollPane, BorderLayout.WEST);

        // 创建上半部分显示社团信息的区域
        clubInfoTextArea = new JTextArea();
        clubInfoTextArea.setEditable(false);
        clubInfoTextArea.setText(getClubInfo());
        JScrollPane scrollPane = new JScrollPane(clubInfoTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 创建下半部分包含按钮的面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        memberManageButton = new JButton("社员管理");
        infoManageButton = new JButton("社团信息修改");
        //activityReviewButton = new JButton("社团活动回顾");
        AddClubButton = new JButton("新增社团");
        DeleteClubButton = new JButton("删除社团");

        buttonPanel.add(memberManageButton);
        buttonPanel.add(infoManageButton);
        buttonPanel.add(AddClubButton);
        buttonPanel.add(DeleteClubButton);
        //buttonPanel.add(activityReviewButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // 创建一个按钮，点击后刷新文本
        JButton button = new JButton("刷新窗口");
        panel.add(button, BorderLayout.EAST);

        // 添加按钮点击事件
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取当前窗口的位置
                int x = getX();
                int y = getY();
                // 创建一个新的窗口
                JFrame newFrame = new ClubInfoFrame();
                //newFrame.setSize(400, 300);
                newFrame.setLocation(x, y);  // 设置新窗口位置与当前窗口相同
                // 显示新窗口
                newFrame.setVisible(true);
                // 关闭当前窗口
                dispose();
            }
        });

        // 将面板添加到窗口
        add(panel);

        setLocationRelativeTo(null);
        // 设置窗口可见
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 添加按钮事件监听器
        memberManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMemberManagement();
            }
        });

        infoManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInfoManagement();
            }
        });
        AddClubButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddNewClub();
            }
        });
        DeleteClubButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteClub();
            }
        });
//        activityReviewButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showActivityReview();
//            }
//        });

        // 添加双击事件监听器
        clubList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // 双击事件
                    String selectedClub = clubList.getSelectedValue();
                    showClubWindow(selectedClub);
                }
            }
        });
        clubList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 获取选中的索引
                int selectedIndex = clubList.getSelectedIndex();
                // 如果有选中的项，获取选中的文本
                if (selectedIndex != -1) {
                    String selectedItem = clubList.getSelectedValue();
                    setChosenClub(selectedItem);
                    clubInfoTextArea.setText(getClubInfo());
                    //JOptionPane.showMessageDialog(panel, "你选择了: " + selectedItem);
                }else{
                    clubInfoTextArea.setText(null);
                }
            }
        });
    }
    // 获取社团信息
    /*
    * 以实现：读取所选的
    * */
    private String getClubInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        String searchKey = "Club_Name";
        String searchValue;
        if(this.chosenClub !=null){
            searchValue = this.chosenClub;
        }else{
            searchValue = "School";
        }
        // 查找符合条件的社团
        Optional<Map<String, Object>> club = clubs.stream()
                .filter(temp -> searchValue.equals(temp.get(searchKey)))
                .findFirst();
        if (club.isPresent()) {
            // 找到了符合条件的记录
            map = club.get();
        } else {
            // 没有找到符合条件的记录
            return "未找到符合条件的社团信息。";
        }
        // 拼接社团信息
        stringBuilder
                .append("社团ID: ").append(map.get("Club_ID")).append("\n")
                .append("社团名称: ").append(this.chosenClub).append("\n")
                .append("社团类型: ").append(map.get("Club_Type")).append("\n")
                .append("负责人ID: ").append(map.get("Club_MangeID")).append("\n")
                .append("指导老师: ").append(map.get("Club_Teacher")).append("\n")
                .append("成员数量: ").append(map.get("Club_Number")).append("\n")
                .append("社团位置: ").append(map.get("Club_Add")).append("\n");
        return stringBuilder.toString();
    }
    // 显示社员管理界面
    private void showMemberManagement() {
        //JOptionPane.showMessageDialog(this, "进入社员管理界面");
        SwingUtilities.invokeLater(() -> {
            new MemberManagerFrame(chosenClub).setVisible(true);
            //frame;
        });
    }
    // 显示社团信息管理界面
    private void showInfoManagement() {

        new ClubInfoManager(this.UserId,this.map);
    }
    //显示新增社团界
    private void AddNewClub() {
        new ClubAdding(this.UserId,this.map);
    }
    //显示删除社团界面
    private void DeleteClub() {
        new ClubDelete(this.UserId,this.map);
    }
    // 显示社团活动回顾界面
    private void showActivityReview() {
        JOptionPane.showMessageDialog(this, "进入社团活动回顾界面");
    }
    private void showClubWindow(String clubName) {
        JOptionPane.showMessageDialog(this, "预设功能：社团详情页（可由社团管理人员使用）");
    }
}
