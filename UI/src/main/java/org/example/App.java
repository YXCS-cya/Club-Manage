package org.example;

import org.example.MainWindow;

import javax.swing.*;

public class App
{
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow();
                //测试过程中使用UserId为“CYA”
                window.setUserId("CYA");
                window.setVisible(true);  // Make the window visible
            }
        });
    }
}
