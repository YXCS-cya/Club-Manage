package org.example.UserWindow;

import org.example.DatabaseManage.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserPermissionManagement extends JFrame {

    // 创建 JTable 的列名
    private String[] columnNames = {"用户ID", "用户名", "权限", "成员ID"};

    private UserService userService = new UserService();
    private List<Map<String, Object>> users = userService.getAllUsers();
    private Object[][] data = new Object[users.size()][];

    private String presentUser;
    private String presentright ;

    // 构造函数
    public UserPermissionManagement(String presentUser) {
        this.presentUser = presentUser;
        for (int i = 0; i < users.size(); i++) {
            Map<String, Object> user = users.get(i);
            // 提取 Map 中的社团名称、负责人和社团类型
            int UserID = (int) user.get("UserID");
            String UserName = (String) user.get("UserName");
            String Role = (String) user.get("Role");
            if(UserName.equals(presentUser)){
                this.presentright = Role;
            }
            //String PassWord = (String) user.get("PassWord");
            int MemberID = (int) user.get("MemberID");
            // 填充二维数组中的一行数据
            data[i] = new Object[] { UserID, UserName, Role, MemberID };
        }


        // 设置窗口标题
        setTitle("用户权限管理");

        // 设置窗口关闭行为
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建一个表格并将数据和列名传入
        JTable userTable = new JTable(data, columnNames);

        // 创建一个 JScrollPane 包裹 JTable，这样可以在表格内容较多时滚动查看
        JScrollPane scrollPane = new JScrollPane(userTable);

        // 创建修改权限按钮
        JButton modifyButton = new JButton("修改权限");

        // 按钮点击事件
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 当按钮被点击时，弹出修改权限的对话框
                int selectedRow = userTable.getSelectedRow();
                //判断是否有权限修改
                if(!presentright.equals("管理员")){
                    JOptionPane.showMessageDialog(UserPermissionManagement.this,
                            "您的权限为："+ presentright + ",无法更改用户信息",
                            "权限不足",
                            JOptionPane.WARNING_MESSAGE);
                }
                else{
                    if (selectedRow != -1) {
                        // 获取选中的用户数据
                        String userName = (String) userTable.getValueAt(selectedRow, 1);
                        String currentPermission = (String) userTable.getValueAt(selectedRow, 2);

                        // 弹出修改权限对话框
                        String newPermission;
                        newPermission = JOptionPane.showInputDialog(
                                UserPermissionManagement.this,
                                "修改 " + userName + " 的权限",
                                "当前权限: " + currentPermission,
                                JOptionPane.PLAIN_MESSAGE
                        );
                        if(!newPermission.equals("管理员") && !newPermission.equals("普通学生")
                                &&!newPermission.equals("负责人")){
                            JOptionPane.showMessageDialog(UserPermissionManagement.this,
                                    "可选的权限：管理员/负责人/普通学生",
                                    "权限类型错误",
                                    JOptionPane.WARNING_MESSAGE);
                            newPermission = JOptionPane.showInputDialog(
                                    UserPermissionManagement.this,
                                    "修改 " + userName + " 的权限",
                                    "当前权限: " + currentPermission,
                                    JOptionPane.PLAIN_MESSAGE
                            );
                        }
                        if (newPermission != null && !newPermission.trim().isEmpty()) {
                            // 更新表格中的权限列
                            userTable.setValueAt(newPermission, selectedRow, 2);
                            userService.updateUser(newPermission,userName);

                        }
                    } else {
                        // 如果没有选中行，提示用户
                        JOptionPane.showMessageDialog(UserPermissionManagement.this,
                                "请先选择一个用户来修改权限。",
                                "未选择用户",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        // 设置布局管理器
        setLayout(new BorderLayout());

        // 将表格添加到窗口的中心区域
        add(scrollPane, BorderLayout.CENTER);

        // 将修改权限按钮添加到窗口的底部
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modifyButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 设置窗口大小
        setSize(600, 400);
        setLocationRelativeTo(null);  // 居中显示窗口
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public boolean authenticate( String password) {
        //this.presentUser = username;
        Iterator<Map<String, Object>> iterator = users.iterator();
        // 遍历每个用户的 Map
        while (iterator.hasNext()) {
            Map<String, Object> user = iterator.next();

            // 检查 Map 中是否包含 'username' 和 'password' 键
            if (user.containsKey("UserName") && user.containsKey("PassWord")) {
                // 获取用户名和密码
                Object storedUsername = user.get("UserName");
                Object storedPassword = user.get("PassWord");
                //注：此处得到的密码，因为数据库中设置的长度，可能会有空格
                //所以通过trim()方法去除字符串前后的空格
                String checkPass = ((String)storedPassword).trim();
                // 如果用户名和密码都匹配
                if (storedUsername != null && storedUsername.equals(presentUser) &&
                        storedPassword != null && checkPass.equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }




}

