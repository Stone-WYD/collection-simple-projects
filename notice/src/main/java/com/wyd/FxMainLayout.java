package com.wyd;

import cn.hutool.core.util.StrUtil;
import com.wyd.util.ConfigPropertiesUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.awt.*;

public class FxMainLayout {

    // 用于调用 windows 系统托盘
    private final TrayIcon trayIcon;

    private final TextField userNameField = new TextField();
    private final TextField hostField = new TextField();
    private final TextField intervalField = new TextField();
    private final ComboBox<Integer> beginHourCombo = new ComboBox<>();
    ComboBox<Integer> beginMinuteCombo = new ComboBox<>();

    private final ComboBox<Integer> endHourCombo = new ComboBox<>();
    ComboBox<Integer> endMinuteCombo = new ComboBox<>();
    private final CheckBox enableIntervalAlertCheckBox = new CheckBox("开启间隔提醒");

    // 日志区域
    private final TextArea logArea = new TextArea();
    // 用法：logArea.appendText("================\n");

    public FxMainLayout(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
        // 数据库参数初始化
        String host = ConfigPropertiesUtil.getProperty("host");
        String userName = ConfigPropertiesUtil.getProperty("userName");
        String beginStr = ConfigPropertiesUtil.getProperty("beginStr");
        String endStr = ConfigPropertiesUtil.getProperty("endStr");
        String interval = ConfigPropertiesUtil.getProperty("interval");
        String enableIntervalAlert = ConfigPropertiesUtil.getProperty("enableIntervalAlert");
        if (!StrUtil.isEmpty(userName)) userNameField.setText(userName);
        if (!StrUtil.isEmpty(host)) hostField.setText(host);
        if (!StrUtil.isEmpty(beginStr)) {
            String[] split = beginStr.split("-");
            beginHourCombo.setValue(Integer.parseInt(split[0]));
            beginMinuteCombo.setValue(Integer.parseInt(split[1]));
        }
        if (!StrUtil.isEmpty(endStr)) {
            String[] split = endStr.split("-");
            endHourCombo.setValue(Integer.parseInt(split[0]));
            endMinuteCombo.setValue(Integer.parseInt(split[1]));
        }
        if (!StrUtil.isEmpty(interval)) {
          intervalField.setText(interval);
        }
        if (!StrUtil.isEmpty(enableIntervalAlert)) {
            enableIntervalAlertCheckBox.setSelected(Boolean.parseBoolean(enableIntervalAlert));
        } else {
            enableIntervalAlertCheckBox.setSelected(true); // 默认开启
        }
    }

    public VBox getMainLayout() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        // 配置区域
        TitledPane configSection = createConfigSection();

        // 操作按钮
        Button saveBtn = new Button("保存配置");
        saveBtn.setOnAction(e -> handleSaveConfig());
            Button connectBtn = new Button("启用通知");
        connectBtn.setOnAction(e -> handleConnection());
        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(saveBtn, connectBtn);

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
        grid.setHgap(20);
        grid.setVgap(15);
        // 用户名配置
        grid.add(new Label("用户名:"), 0, 0);
        grid.add(userNameField, 1, 0);
        GridPane.setHgrow(userNameField, Priority.ALWAYS);
        // 主机配置
        grid.add(new Label("主机地址:"), 0, 1);
        grid.add(hostField, 1, 1);
        GridPane.setHgrow(hostField, Priority.ALWAYS);

        // 时间配置 - 自定义时间选择器
        HBox startTimeBox = createTimeSelector(true);
        grid.add(new Label("开始通知时间:"), 0, 2);
        grid.add(startTimeBox, 1, 2);
        GridPane.setHgrow(startTimeBox, Priority.ALWAYS);

        HBox endTimeBox = createTimeSelector(false);
        grid.add(new Label("结束通知时间:"), 0, 3);
        grid.add(endTimeBox, 1, 3);
        GridPane.setHgrow(endTimeBox, Priority.ALWAYS);

        // 执行间隔
        intervalField.setPromptText("小时");
        grid.add(new Label("执行间隔:"), 0, 4);
        grid.add(intervalField, 1, 4);
        GridPane.setHgrow(intervalField, Priority.ALWAYS);
        Label innerLabel = new Label("(小时，可带小数点表示分钟，但设置间隔请大于十分钟)");
        innerLabel.setMaxWidth(600);
        grid.add(innerLabel, 2, 4);

        // 是否开启间隔提醒
        grid.add(enableIntervalAlertCheckBox, 0, 5);

        TitledPane section = new TitledPane("系统配置", grid);
        section.setCollapsible(false);
        return section;
    }

    private void handleSaveConfig() {
        try {
            // 保存配置到文件
            ConfigPropertiesUtil.setProp("userName", userNameField.getText());
            ConfigPropertiesUtil.setProp("host", hostField.getText());
            ConfigPropertiesUtil.setProp("interval", intervalField.getText());
            // 保存时间
            String beginStr = String.format("%02d-%02d", beginHourCombo.getValue(), beginMinuteCombo.getValue());
            String endStr = String.format("%02d-%02d", endHourCombo.getValue(), endMinuteCombo.getValue());
            ConfigPropertiesUtil.setProp("beginStr", beginStr);
            ConfigPropertiesUtil.setProp("endStr", endStr);
            ConfigPropertiesUtil.setProp("enableIntervalAlert", String.valueOf(enableIntervalAlertCheckBox.isSelected()));
            ConfigPropertiesUtil.saveProp();

            // 记录日志
            logArea.appendText("配置已保存成功\n");

            // 显示系统托盘通知（如果可用）
//            if (trayIcon != null) {
//                trayIcon.displayMessage("配置保存", "配置信息已成功保存", TrayIcon.MessageType.INFO);
//            }
        } catch (Exception e) {
            logArea.appendText("保存配置失败: " + e.getMessage() + "\n");
            showAlert("保存失败", "配置保存失败: " + e.getMessage());
        }
    }


    private void handleConnection() {
        logArea.appendText("连接中...\n");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 创建时间选择器（时:分:秒）
    private HBox createTimeSelector(boolean beginFlag) {
        ComboBox<Integer> hourCombo, minuteCombo, secondCombo;
        if (beginFlag) {
            hourCombo = beginHourCombo;
            minuteCombo = beginMinuteCombo;
        } else {
            hourCombo = endHourCombo;
            minuteCombo = endMinuteCombo;
        }

        // 填充小时选项 (0-23)
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }
        hourCombo.setItems(hours);
        hourCombo.setPrefWidth(60);  // 增加宽度
        hourCombo.setMinWidth(60);   // 设置最小宽度
        hourCombo.setPromptText("时");

        // 填充分钟选项 (0-59)
        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i++) {
            minutes.add(i);
        }
        minuteCombo.setItems(minutes);
        minuteCombo.setPrefWidth(60);  // 增加宽度
        minuteCombo.setMinWidth(60);   // 设置最小宽度
        minuteCombo.setPromptText("分");
        Label colon1 = new Label(":");

        HBox timeBox = new HBox(10);
        timeBox.getChildren().addAll(hourCombo, colon1, minuteCombo);
        return timeBox;
    }

}
