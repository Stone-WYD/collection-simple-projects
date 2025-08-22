package com.wyd;

import cn.hutool.core.util.StrUtil;
import com.wyd.util.ConfigPropertiesUtil;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;

public class FxMainLayout {

/*
    // 用于调用 windows 系统托盘
    private TrayIcon trayIcon;

    private TextField userNameField = new TextField("userName");
    private TextField hostField = new TextField("userName");

    // 日志区域
    private TextArea logArea = new TextArea();
    // 用法：logArea.appendText("================\n");

    // 文件处理组件
    private CheckBox saveCheckBox = new CheckBox("保存");



    public FxMainLayout(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
        // 数据库参数初始化
        String host = ConfigPropertiesUtil.getProperty("host");
        String userName = ConfigPropertiesUtil.getProperty("userName");
        if (!StrUtil.isEmpty(userName)) userNameField.setText(userName);
        if (!StrUtil.isEmpty(host)) hostField.setText(host);
    }

    public VBox getMainLayout() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        // 日志区域
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        TitledPane logSection = new TitledPane("操作日志", logArea);

        // 组合布局
        mainLayout.getChildren().addAll(
                new Separator(),
                new Separator(),
                new Label("选择输入文件:"),
                new Separator(),
                logSection
        );
        return mainLayout;
    }
*/




    // 用于调用 windows 系统托盘
    private TrayIcon trayIcon;

    private TextField userNameField = new TextField();
    private TextField hostField = new TextField();

    // 日志区域
    private TextArea logArea = new TextArea();
    // 用法：logArea.appendText("================\n");

    // 文件处理组件
    private CheckBox saveCheckBox = new CheckBox("保存");

    public FxMainLayout(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
        // 数据库参数初始化
        String host = ConfigPropertiesUtil.getProperty("host");
        String userName = ConfigPropertiesUtil.getProperty("userName");
        if (!StrUtil.isEmpty(userName)) userNameField.setText(userName);
        if (!StrUtil.isEmpty(host)) hostField.setText(host);
    }

    public VBox getMainLayout() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        // 配置区域
        TitledPane configSection = createConfigSection();

        // 操作按钮
        Button saveBtn = new Button("保存配置");
        saveBtn.setOnAction(e -> handleSaveConfig());
        Button testBtn = new Button("测试连接");
        testBtn.setOnAction(e -> handleTestConnection());
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(saveBtn, testBtn);

        // 日志区域
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        TitledPane logSection = new TitledPane("操作日志", logArea);
        logSection.setCollapsible(false);

        // 组合布局
        mainLayout.getChildren().addAll(
                configSection,
                buttonBox,
                new Separator(),
                logSection
        );

        return mainLayout;
    }

    private TitledPane createConfigSection() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        // 用户名配置
        grid.add(new Label("用户名:"), 0, 0);
        grid.add(userNameField, 1, 0);
        GridPane.setHgrow(userNameField, Priority.ALWAYS);

        // 主机配置
        grid.add(new Label("主机地址:"), 0, 1);
        grid.add(hostField, 1, 1);
        GridPane.setHgrow(hostField, Priority.ALWAYS);

        TitledPane section = new TitledPane("系统配置", grid);
        section.setCollapsible(false);
        return section;
    }

    private void handleSaveConfig() {
        try {
            // 保存配置到文件
            ConfigPropertiesUtil.setProp("userName", userNameField.getText());
            ConfigPropertiesUtil.setProp("host", hostField.getText());
            ConfigPropertiesUtil.saveProp();

            // 记录日志
            logArea.appendText("配置已保存成功\n");

            // 显示系统托盘通知（如果可用）
            if (trayIcon != null) {
                trayIcon.displayMessage("配置保存", "配置信息已成功保存", TrayIcon.MessageType.INFO);
            }
        } catch (Exception e) {
            logArea.appendText("保存配置失败: " + e.getMessage() + "\n");
            showAlert("保存失败", "配置保存失败: " + e.getMessage());
        }
    }

    private void handleTestConnection() {
        logArea.appendText("点击了测试按钮...\n");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 添加设置托盘图标的方法
    public void setTrayIcon(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

}
