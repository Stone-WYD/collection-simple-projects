package org.wyd;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FxApplication extends Application {

    // 输入文件选择 识别图号
    private TextField filePathField = new TextField();
    // 文件类型选择
    private CheckBox docxCheckBox = new CheckBox("Word(.docx)");
    private CheckBox xlsxCheckBox = new CheckBox("Excel(.xlsx)");
    private CheckBox pdfCheckBox = new CheckBox("PDF(.pdf)");
    private CheckBox pptxCheckBox = new CheckBox("PPT(.pptx)");
    private CheckBox stepCheckBox = new CheckBox("3D(.step)");
    private CheckBox allCheckBox = new CheckBox("全部类型");
    // 选择搜索目录
    private Button inputDirBtn = new Button("选择搜索目录");
    private TextField inputDirField = new TextField();
    // 选择输出目录
    private Button outputDirBtn = new Button("选择输出目录");
    private TextField outputDirField = new TextField();
    // 输出目录组件
    private TextArea logArea = new TextArea();



    private HBox createFileTypeSelectionBox() {
        VBox checkBoxes = new VBox(10,
                allCheckBox,
                docxCheckBox,
                xlsxCheckBox,
                pdfCheckBox,
                stepCheckBox,
                pptxCheckBox
        );

        // 全选逻辑绑定
        allCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            boolean selected = newVal;

            docxCheckBox.setSelected(selected);
            xlsxCheckBox.setSelected(selected);
            pdfCheckBox.setSelected(selected);
            pptxCheckBox.setSelected(selected);
            stepCheckBox.setSelected(selected);

        });

        // 某一项取消选中时，取消全选状态
        docxCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateAllCheckBox());
        xlsxCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateAllCheckBox());
        pdfCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateAllCheckBox());
        pptxCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateAllCheckBox());
        stepCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateAllCheckBox());
        return new HBox(checkBoxes);
    }

    private void updateAllCheckBox() {
        boolean allSelected = docxCheckBox.isSelected() &&
                xlsxCheckBox.isSelected() &&
                pdfCheckBox.isSelected() &&
                pptxCheckBox.isSelected() &&
                stepCheckBox.isSelected();
        allCheckBox.setSelected(allSelected);
    }


    @Override
    public void start(Stage primaryStage) {
        // 主窗口关闭时关闭jvm
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });

        primaryStage.setTitle("文件移动工具");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));


        // 文件选择区域
        HBox fileSelectBox = new HBox(10);
        Button browseBtn = new Button("请选择 excel 文件");
        browseBtn.setOnAction(e -> handleFileSelect());
        filePathField.setPromptText("请选择输入文件...");
        fileSelectBox.getChildren().addAll(filePathField, browseBtn);

        // 选择搜索目录
        HBox inputDirBox = new HBox(20, inputDirField, inputDirBtn);
        inputDirBtn.setOnAction(e -> handleInputDirSelect());
        // 选择输入目录
        HBox outputDirBox = new HBox(20, outputDirField, outputDirBtn);
        outputDirBtn.setOnAction(e -> handleOutputDirSelect());



        // 操作按钮
        Button processBtn = new Button("移动文件");
        processBtn.setStyle("-fx-base: #4CAF50;");
        processBtn.setOnAction(e -> handleProcess());

        // 日志区域
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        TitledPane logSection = new TitledPane("操作日志", logArea);
        logSection.setCollapsible(false);

        // 组合布局
        mainLayout.getChildren().addAll(
                new Separator(),
                new Label("选择输入文件:"),
                fileSelectBox,
                new Separator(),
                new Label("选择搜索目录:"),
                inputDirBox,
                new Label("选择要移动的文件类型:"),
                createFileTypeSelectionBox(), // 插入新区域
                new Separator(),
                new Label("选择输出目录:"),
                outputDirBox,
                processBtn,
                logSection
        );

        Scene scene = new Scene(mainLayout, 650, 700);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleInputDirSelect() {
        DirectoryChooser chooser = new DirectoryChooser();
        File dir = chooser.showDialog(null);
        if (dir != null) {
            inputDirField.setText(dir.getAbsolutePath());
        }
    }

    private void handleOutputDirSelect() {
        DirectoryChooser chooser = new DirectoryChooser();
        File dir = chooser.showDialog(null);
        if (dir != null) {
            outputDirField.setText(dir.getAbsolutePath());
        }
    }

    private void handleFileSelect() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择输入文件");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void handleProcess() {
        // 获取输入文件路径
        String inputFile = filePathField.getText();
        if (inputFile.isEmpty()) {
            showAlert("输入错误", "请先选择输入文件！");
            return;
        }
        // 获取文件类型
        List<String> fileTypes = new ArrayList<>();
        if (allCheckBox.isSelected()) {
            fileTypes.addAll(Arrays.asList("docx", "xlsx", "pdf", "pptx", "step"));
        } else {
            if (docxCheckBox.isSelected()) {
                fileTypes.add("docx");
            }
            if (xlsxCheckBox.isSelected()) {
                fileTypes.add("xlsx");
            }
            if (pdfCheckBox.isSelected()) {
                fileTypes.add("pdf");
            }
            if (pptxCheckBox.isSelected()) {
                fileTypes.add("pptx");
            }
            if (stepCheckBox.isSelected()) {
                fileTypes.add("step");
            }
        }
        // 处理文件
        MoveHandler.copy(inputFile, inputDirField.getText(),
                outputDirField.getText(), fileTypes, logArea);
    }


    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
