package com.wyd;

import com.wyd.connect.netty.NoticeClient;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class Main {
    // fixme 需要注意项目以 GBK 的编码运行，项目中出现的

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final URL imageUrl = Main.class.getClassLoader().getResource("notice.png");
    private static JFrame configFrame;
    private static JFXPanel fxPanel;
    private static FxMainLayout fxMainLayout;

    // 添加文件锁相关变量
    private static FileLock fileLock;
    private static FileChannel fileChannel;
    private static RandomAccessFile randomAccessFile;

    private static NoticeClient noticeClient;

    public static void main(String[] args) {
        // 检查是否已经运行
        if (!acquireLock()) {
            logger.warn("程序已经在运行中...");
            return;
        }
        // 添加关闭钩子来释放锁
        Runtime.getRuntime().addShutdownHook(new Thread(Main::releaseLock));

        // 确保系统支持系统托盘
        if (!SystemTray.isSupported()) {
            logger.error("系统不支持托盘功能");
            return;
        }

        fxPanel = new JFXPanel();
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
                Image image = Toolkit.getDefaultToolkit().getImage(imageUrl);
                Image trayImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);

                TrayIcon trayIcon = new TrayIcon(trayImage, "MES信息通知");
                trayIcon.setImageAutoSize(true);

                // 创建弹出菜单
                PopupMenu popup = new PopupMenu();

                // 添加菜单项
                MenuItem configItem = new MenuItem("配置");
                configItem.addActionListener(e -> showConfigWindow());

                MenuItem exitItem = new MenuItem("退出");
                exitItem.addActionListener(e -> {
                    tray.remove(trayIcon);
                    noticeClient.stop();
                    System.exit(0);
                });

                popup.add(configItem);
                popup.addSeparator();
                popup.add(exitItem);

                // 设置托盘图标右键菜单
                trayIcon.setPopupMenu(popup);

                // 添加双击事件
                trayIcon.addActionListener(e -> showConfigWindow());

                // 添加托盘图标
                tray.add(trayIcon);

                // 初始化 noticeClient
                noticeClient = new NoticeClient(trayIcon, null);
                new Thread(() -> {
                    // 尝试运行一次
                    noticeClient.start();
                }).start();
            } catch (Exception e) {
                logger.error("添加托盘图标异常", e);
            }
        });
    }

    private static void showConfigWindow() {
        Platform.runLater(() -> {
            fxMainLayout = new FxMainLayout(noticeClient);
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

        ImageIcon icon = new ImageIcon(imageUrl);
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

    /**
     * 获取程序锁，确保只有一个实例运行
     * @return true-获取成功，false-获取失败（已在运行）
     */
    private static boolean acquireLock() {
        try {
            // 使用用户临时目录创建锁文件
            String userHome = System.getProperty("user.home");
            File lockFile = new File(userHome, ".mes_notice_app.lock");

            randomAccessFile = new RandomAccessFile(lockFile, "rw");
            fileChannel = randomAccessFile.getChannel();

            // 尝试获取文件锁
            fileLock = fileChannel.tryLock();

            if (fileLock == null) {
                // 获取锁失败，说明程序已在运行
                releaseLock();
                return false;
            }

            // 写入当前进程信息
            String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
            long pid = Long.parseLong(processName.split("@")[0]);
            randomAccessFile.writeBytes("MES Notice App PID: " + pid);

            return true;
        } catch (Exception e) {
            logger.error("获取程序锁失败", e);
            releaseLock();
            return false;
        }
    }

    /**
     * 释放程序锁
     */
    private static void releaseLock() {
        try {
            if (fileLock != null) {
                fileLock.release();
            }
            if (fileChannel != null) {
                fileChannel.close();
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        } catch (Exception e) {
            logger.error("释放程序锁失败", e);
        }
    }

}