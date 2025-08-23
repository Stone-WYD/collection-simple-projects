package com.wyd;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    // fixme 需要注意项目以 GBK 的编码运行，项目中出现的

    private static JFrame configFrame;
    private static final JFXPanel fxPanel = new JFXPanel();
    private static FxMainLayout fxMainLayout;

    public static void main(String[] args) {
        // 确保系统支持系统托盘
        if (!SystemTray.isSupported()) {
            System.err.println("系统不支持托盘功能");
            return;
        }
        // 预加载一次，否则第一次加载会比较慢
        Platform.runLater(() -> {
            fxMainLayout = new FxMainLayout(null);
            Scene scene = new Scene(fxMainLayout.getMainLayout(), 650, 550);
            fxPanel.setScene(scene);
        });

        SwingUtilities.invokeLater(() -> {
            try {
                // 创建系统托盘图标
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().getImage("notice.png");
                Image trayImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);

                TrayIcon trayIcon = new TrayIcon(trayImage, "西洲mes");
                trayIcon.setImageAutoSize(true);


                // 创建弹出菜单
                PopupMenu popup = new PopupMenu();

                // 添加菜单项
                MenuItem configItem = new MenuItem("配置");
                configItem.addActionListener(e -> showConfigWindow(trayIcon));

                MenuItem exitItem = new MenuItem("退出");
                exitItem.addActionListener(e -> {
                    tray.remove(trayIcon);
                    System.exit(0);
                });

                popup.add(configItem);
                popup.addSeparator();
                popup.add(exitItem);

                // 设置托盘图标右键菜单
                trayIcon.setPopupMenu(popup);

                // 添加双击事件
                trayIcon.addActionListener(e -> showConfigWindow(trayIcon));

                // 添加托盘图标
                tray.add(trayIcon);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private static void showConfigWindow(TrayIcon trayIcon) {
        Platform.runLater(() -> {
            fxMainLayout = new FxMainLayout(trayIcon);
            Scene scene = new Scene(fxMainLayout.getMainLayout(), 650, 550);
            fxPanel.setScene(scene);
        });

        // 显示通知
        //trayIcon.displayMessage("蒋业伟", "您有 1 个待审核的拆图工单", TrayIcon.MessageType.INFO);

        if (configFrame != null && configFrame.isVisible()) {
            configFrame.toFront();
            return;
        }

        // 创建配置窗口
        configFrame = new JFrame("系统配置");
        configFrame.setSize(650, 550);
        configFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ImageIcon icon = new ImageIcon("notice.png");
        configFrame.setIconImage(icon.getImage());

        // 将窗口居中显示
        configFrame.setLocationRelativeTo(null);

        // 添加窗口监听器
        configFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 窗口关闭时不退出程序，只是隐藏窗口
                configFrame.setVisible(false);
            }
        });
        // 设置托盘图标
        configFrame.add(fxPanel);

        // 显示窗口
        configFrame.setVisible(true);
    }

}