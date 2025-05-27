package org.wyd;

import com.alibaba.excel.util.StringUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.wyd.back.db.SecurityContextHolder;
import org.wyd.common.BusinessException;
import org.wyd.front.DataHandler;
import org.wyd.util.ConfigPropertiesUtil;

import java.io.File;
import java.util.Map;

public class FxApplication extends Application {

    // Spring IOC 容器
    private static ApplicationContext springContext;

    private static Map<String ,String> showNameMap;

    public static void setSpringContext(ApplicationContext context) {
        springContext = context;
        DataHandler.springContext = springContext;
        showNameMap = DataHandler.getShowNameMap();
    }

    // 数据库相关组件
    private CheckBox enableDBCheckBox = new CheckBox("启用数据库连接");
    private TextField hostField = new TextField("localhost");
    private TextField portField = new TextField("3306");
    private TextField dbNameField = new TextField("testdb");
    private TextField userField = new TextField("root");
    private PasswordField passwordField = new PasswordField();
    // 输出目录组件
    private TextArea logArea = new TextArea();
    private Button outputDirBtn = new Button("选择输出目录");

    // 文件处理组件
    private TextField filePathField = new TextField();
    private CheckBox outputCheckBox = new CheckBox("生成输出文件");
    private TextField outputDirField = new TextField();

    // 业务类型组件
    private ComboBox<String> businessTypeCombo = new ComboBox<>();


    public FxApplication() {
        // 数据库参数初始化
        String user = ConfigPropertiesUtil.getProperty("user");
        String port = ConfigPropertiesUtil.getProperty("port");
        String host = ConfigPropertiesUtil.getProperty("host");
        String dbName = ConfigPropertiesUtil.getProperty("dbName");
        if (!StringUtils.isEmpty(user)) userField.setText(user);
        if (!StringUtils.isEmpty(port)) portField.setText(port);
        if (!StringUtils.isEmpty(host)) hostField.setText(host);
        if (!StringUtils.isEmpty(dbName)) dbNameField.setText(dbName);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("坐标映射工具");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        // 数据库配置区域
        TitledPane dbSection = createDbSection();

        // 新增业务类型选择区域
        HBox businessSelectBox = new HBox(10);
        businessTypeCombo.setPromptText("请选择业务类型");
        businessTypeCombo.setPrefWidth(200);
        businessTypeCombo.setId("businessSelectBox");

        // 初始化业务类型选项
        businessTypeCombo.setItems(FXCollections.observableArrayList(
                showNameMap.keySet()
        ));
        Label bussinessLabel = new Label("业务类型:");
        bussinessLabel.setId("bussinessLabel");
        businessSelectBox.getChildren().addAll(
                bussinessLabel, businessTypeCombo);

        // 文件选择区域
        HBox fileSelectBox = new HBox(10);
        Button browseBtn = new Button("选择文件");
        browseBtn.setOnAction(e -> handleFileSelect());
        filePathField.setPromptText("请选择输入文件...");
        fileSelectBox.getChildren().addAll(filePathField, browseBtn);

        // 操作按钮
        Button processBtn = new Button("处理文件");
        processBtn.setStyle("-fx-base: #4CAF50;");
        processBtn.setOnAction(e -> handleProcess());

        // 日志区域
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        TitledPane logSection = new TitledPane("操作日志", logArea);
        logSection.setCollapsible(false);

        // 组合布局
        mainLayout.getChildren().addAll(
                dbSection,
                new Separator(),
                businessSelectBox,
                new Separator(),
                new Label("选择输入文件:"),
                fileSelectBox,
                new Separator(),
                processBtn,
                logSection
        );

        Scene scene = new Scene(mainLayout, 650, 700);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TitledPane createDbSection() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(10));

        // 第一行：启用复选框
        grid.add(enableDBCheckBox, 0, 0, 4, 1);

        // 配置数据库字段
        grid.add(new Label("数据库地址:"), 0, 1);
        grid.add(hostField, 1, 1);
        grid.add(new Label("端口:"), 2, 1);
        grid.add(portField, 3, 1);

        grid.add(new Label("数据库名称:"), 0, 2);
        grid.add(dbNameField, 1, 2);
        grid.add(new Label("用户名:"), 2, 2);
        grid.add(userField, 3, 2);

        grid.add(new Label("密码:"), 0, 3);
        grid.add(passwordField, 1, 3);

        // 绑定字段可用状态
        hostField.disableProperty().bind(enableDBCheckBox.selectedProperty().not());
        portField.disableProperty().bind(enableDBCheckBox.selectedProperty().not());
        dbNameField.disableProperty().bind(enableDBCheckBox.selectedProperty().not());
        userField.disableProperty().bind(enableDBCheckBox.selectedProperty().not());
        passwordField.disableProperty().bind(enableDBCheckBox.selectedProperty().not());

        // 绑定输出文件目录
        grid.add(outputCheckBox, 0, 4, 4, 1);
        outputDirField.setPromptText("输出目录（默认与输入文件相同）");
        outputDirBtn.setOnAction(e -> handleOutputDirSelect());
        HBox outputDirBox = new HBox(20, outputDirField, outputDirBtn);
        VBox vBox = new VBox(10,
                new HBox(10, outputCheckBox),
                new HBox(10, new Label("输出目录:"), outputDirBox)
        );
        grid.add(vBox, 0, 5, 4, 1);
        outputDirBox.disableProperty().bind(outputCheckBox.selectedProperty().not());

        TitledPane section = new TitledPane("可选配置", grid);
        section.setCollapsible(true);
        section.setExpanded(true);
        return section;
    }

    private void handleFileSelect() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择输入文件");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void handleOutputDirSelect() {
        DirectoryChooser chooser = new DirectoryChooser();
        File dir = chooser.showDialog(null);
        if (dir != null) {
            outputDirField.setText(dir.getAbsolutePath());
        }
    }

    private void handleProcess() {
        // 获取输入文件路径
        String inputFile = filePathField.getText();
        if (inputFile.isEmpty()) {
            showAlert("输入错误", "请先选择输入文件！");
            return;
        }

        // 获取数据库配置
        boolean useDB = enableDBCheckBox.isSelected();
        if (useDB) {
            if (!validateDBConfig()) {
                return;
            }
        }

        // 获取输出配置
        boolean generateOutput = outputCheckBox.isSelected();
        String outputDir = outputDirField.getText().isEmpty() ?
                new File(inputFile).getParent() : outputDirField.getText();

        new Thread(() -> {
            try {
                // 开始处理
                logArea.appendText("======== 开始处理 ========\n");
                // 数据库参数保存
                saveDBParams();
                // 获取dataHandler
                DataHandler dataHandler = DataHandler.getInstance(showNameMap.get(businessTypeCombo.getValue()));
                dataHandler.setUseDB(useDB);
                dataHandler.setGenerateOutput(generateOutput);
                dataHandler.setLogArea(logArea);
                dataHandler.handle(inputFile, outputDir);
            } catch (BusinessException be) {
                Platform.runLater(() -> {
                    logArea.appendText("处理过程中出现问题：" + be.getMessage() + "\n");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    logArea.appendText("处理过程中出现未预料问题：" + e.getMessage() + "\n");
                    showAlert("处理异常！", "详细异常请参考日志");
                });
            }
            logArea.appendText("======== 本次处理结束 ========\n");
        }).start();
    }

    private void saveDBParams() {
        // 输入配置保存到文件中
        ConfigPropertiesUtil.setProp("user", userField.getText());
        ConfigPropertiesUtil.setProp("port", portField.getText());
        ConfigPropertiesUtil.setProp("host", hostField.getText());
        ConfigPropertiesUtil.setProp("dbName", dbNameField.getText());
        ConfigPropertiesUtil.saveProp();
        // 保存到上下文中，实际连接数据库时使用
        SecurityContextHolder.setCredentials(hostField.getText(), portField.getText(),
                dbNameField.getText(), userField.getText(), passwordField.getText());
    }

    private boolean validateDBConfig() {
        if (hostField.getText().isEmpty() ||
                portField.getText().isEmpty() ||
                dbNameField.getText().isEmpty() ||
                userField.getText().isEmpty()) {

            showAlert("配置错误", "启用数据库时必须填写所有字段");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
