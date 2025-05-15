package org.example;

import org.example.DatabaseManage.ClubService;
import org.example.DatabaseManage.UserService;
import org.example.UserWindow.UserPermissionManagement;
import org.example.VIEW.CreatActTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame {

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    private String UserId ;
    private ClubService clubService = new ClubService();

    //创建社团数据库管理类

    private List<Map<String, Object>> clubs = clubService.getClubItro();
    //把社团的信息存入容器列表供调用
    //private Map<String, Object> map = null;

    // Constructor to set up the main window
    public MainWindow() {
        // Set up the main window title and size
        setTitle("学生社团管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout manager (BorderLayout)
        setLayout(new BorderLayout());

        // Create a menu bar
        createMenuBar();

        // Create a tabbed pane to hold different modules
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs to the tabbed pane
        tabbedPane.addTab("社团信息", createClubManagementPanel());
        //tabbedPane.addTab("学生管理", createStudentManagementPanel());
        tabbedPane.addTab("活动报表", createStatisticsPanel());
        tabbedPane.addTab("权限管理", createPermissionManagementPanel());
        tabbedPane.addTab("使用说明", createIntroduce());
        // Add the tabbed pane to the center of the window
        add(tabbedPane, BorderLayout.CENTER);
    }



    // Create the menu bar with options like File, Help, etc.
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("设置");
        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application
            }
        });
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        setLocationRelativeTo(null);
        // Help menu
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aboutItem = new JMenuItem("系统信息");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainWindow.this, "学生社团管理系统 v1.0\n" +
                        "开发者: 3122004777陈耀安\n" +
                        "DBMS：Sql Server", "关于", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);


        // Set the menu bar for the frame
        setJMenuBar(menuBar);
    }


    private JPanel createClubManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a placeholder table for club information
        String[] columns = {"社团名称", "社团负责人", "社团类型"};
        Object[][] data = new Object[clubs.size()][];
        for (int i = 0; i < clubs.size(); i++) {
            Map<String, Object> club = clubs.get(i);
            // 提取 Map 中的社团名称、负责人和社团类型
            String clubName = (String) club.get("社团名称");
            String leaderName = (String) club.get("社团负责人");
            String clubType = (String) club.get("社团类型");

            // 填充二维数组中的一行数据
            data[i] = new Object[] { clubName, leaderName, clubType };
        }
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);



        // Add a label and table to the panel
        panel.add(new JLabel("各社团名单"), BorderLayout.NORTH);

        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());


        JButton memberManageButton = new JButton("社团管理");
        buttonPanel.add(memberManageButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        memberManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClubInfoFrame().setUserId(UserId);
            }
        });

        return panel;
    }

    // Create the statistics panel
    public JPanel createStatisticsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        // Create a panel to hold the form components (drop-down, text field, and button)
        JPanel formPanel = new JPanel();
        // First row: "查询报表类型" with a combo box
        JLabel reportTypeLabel = new JLabel("查询报表方式:");
        String[] reportTypes = { "按社团/活动查询"};
        // Sample report types
        JComboBox<String> reportTypeComboBox = new JComboBox<>(reportTypes);
        reportTypeComboBox.setPreferredSize(new Dimension(150, 30));
        // Set width and height
        formPanel.add(reportTypeLabel);
        formPanel.add(reportTypeComboBox);
        // Second row: "查询关键字" with a text field
        JLabel keywordLabel = new JLabel("查询关键字:");
        JTextField keywordTextField = new JTextField();
        keywordTextField.setPreferredSize(new Dimension(150, 30));
        formPanel.add(keywordLabel);
        formPanel.add(keywordTextField);
        // Third row: "查询" button
        JButton queryButton = new JButton("查询");
        queryButton.setPreferredSize(new Dimension(150, 30));
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Handle query button click (e.g., print the selected values)
                String selectedReportType = (String) reportTypeComboBox.getSelectedItem();
                String keyword = keywordTextField.getText();
                //System.out.println("报表类型: " + selectedReportType);
                //System.out.println("查询关键字: " + keyword);
                CreatActTable creatActTable = new CreatActTable(selectedReportType,keyword);
                JTable table = creatActTable.getTable();
                JScrollPane scrollPane = new JScrollPane(table);

                getContentPane().remove(scrollPane);
                revalidate();
                repaint();
                panel.add(scrollPane, BorderLayout.CENTER);
            }
        });

        formPanel.add(new JLabel());
        formPanel.add(queryButton);
        // Add the form panel and label to the main panel
        panel.add(formPanel, BorderLayout.NORTH);
        // Set preferred size for the overall panel
        panel.setPreferredSize(new Dimension(100, 50));
        return panel;
    }
    private JPanel createIntroduce() {
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        // 创建 JTextArea 用于显示大段文本
        JTextArea textArea = new JTextArea(20, 60);
        textArea.setText("使用说明&介绍：\n\n" +
                "!!!本程序需要使用相应的社团管理系统数据库，请通过文件夹中的社团管理系统.mdf和.ldf文件将数据库附加在本地（Sql Server2012）\n" +
                "参考流程可见网页：https://blog.csdn.net/gengkui9897/article/details/89321844\n\n" +
                "1. 主界面/社团信息界面——通过建立视图查询Club表及Member表的信息\n\n" +
                "2. 社团信息管理界面——增删改查、复杂查询、级联删除、视图\n单点左侧列表，可在中心查看对应社团信息——通过复杂查询读取数据库\n" +
                "社员管理按钮：请单击列表选择一个社团，再点击该按钮。显示该社团的成员信息（SCHOOL无成员）\n" +
                "——通过建立视图，快捷查询Club表、Member表和Club_Member表的信息。使用级联删除，避免社团信息中还有成员信息。通过视图方便对复杂查询的信息筛选\n" +
                "社团信息修改按钮：请单击列表选择一个社团，再点击该按钮。显示该社团的当前信息后，请直接在文本框中修改。\n" +
                "删除社团按钮：请单击列表选择一个社团，再点击该按钮。显示该社团的当前信息后，再确定是否删除。\n" +
                "！！！上述操作点击确定后，请点击社团信息管理界面的刷新按钮，系统会将数据库更新后的信息重新放入此界面\n\n" +
                "3. 活动报表界面——主要使用视图与模糊查询\n" +
                "不输入查询关键字：查看所有已录入的信息——建立视图，调取Club、Activity、ClubActivityRecord 三张表的信息\n" +
                "输入查询信息：查看与关键字相关的社团举办的活动/包含关键字的活动——模糊查询\n\n" +
                "4. 权限管理界面——视图保证密码安全性、设置用户身份保证操作合法\n" +
                "登录：默认请使用管理员用户（用户名“CYA” 密码“1234”）进行登录，只有管理员才有资格修改信息——通过视图等操作，保证密码传输时的安全性（输入密码时，用户也不可见）\n" +
                "修改信息：只有管理员可以进行修改，其他用户无法打开修改页面；管理员用户只能修改身份（管理员、负责人、普通学生），修改有误会报错；全过程密码对用户隐藏\n\n" +
                "本程序主要操作如上，期间所有对数据的操作都会实时同步到数据库内。\n" +
                "注：本系统仅用于实践对数据库系统的学习，故部分同质化严重的操作未进行实现，如有对测试数据的需要，请在DBMS中进行添加\n" +
                "另注：本系统基于Maven，同时借用JDBC连接至Sql Server2012，实现对数据库管理");

        // 设置文本框为只读模式
        textArea.setEditable(false);

        // 设置文本框自动换行
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        // 创建滚动面板，并将 JTextArea 添加到滚动面板中
        JScrollPane scrollPane = new JScrollPane(textArea);

        // 将滚动面板添加到 JPanel 中
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPermissionManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a placeholder label for permissions
        JLabel label = new JLabel("权限管理功能", SwingConstants.CENTER);
        //label.setFont(new Font("Arial", Font.BOLD, 20));

        // Add the label to the panel
        panel.add(label, BorderLayout.NORTH);

        // Create the authentication panel inside the permission management panel
        JPanel authPanel = new JPanel();
        //authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));
        // Use vertical BoxLayout for centering

        // Set a maximum width for the authentication panel
        authPanel.setPreferredSize(new Dimension(300, 200));
        authPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Center authPanel horizontally

        // Create labels and text fields for username and password
        JLabel usernameLabel = new JLabel("用户名:");
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        // Set a fixed width for text field
        JLabel passwordLabel = new JLabel("密码:");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        // Set a fixed width for password field

        // Create login button
        JButton loginButton = new JButton("登录");
        loginButton.setPreferredSize(new Dimension(200, 40));
        // Set a fixed width for the button

        // Add components to the authentication panel
        authPanel.add(usernameLabel);
        authPanel.add(usernameField);
        authPanel.add(passwordLabel);
        authPanel.add(passwordField);
        authPanel.add(Box.createVerticalStrut(10));
        // Add some space between the fields and button
        authPanel.add(loginButton);

        // Add the authentication panel to the center of the main panel
        panel.add(authPanel, BorderLayout.CENTER);

        // Set up login button action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                UserPermissionManagement userPermissionManagement = new UserPermissionManagement(username);
                // Authenticate user
                if (userPermissionManagement.authenticate(new String(password))) {
                    JOptionPane.showMessageDialog(panel, "登录成功!");
                    userPermissionManagement.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(panel, "用户名或密码错误!", "登录失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return panel;
    }


}
