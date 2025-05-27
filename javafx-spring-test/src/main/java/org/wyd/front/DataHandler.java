package org.wyd.front;

import cn.hutool.core.util.StrUtil;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.wyd.back.bean.MonitorCamera;
import org.wyd.back.mapper.MonitorCameraMapper;

import java.util.List;
import java.util.Map;


@Setter
public abstract class DataHandler {

    protected boolean useDB;

    protected TextArea logArea;

    // 是否输出文件
    protected boolean generateOutput;

    // Spring 容器
    public static ApplicationContext springContext;

    // 返回所有 DataHandler 的 name 给客户端使用
    public static Map<String, String> getShowNameMap() {
        return springContext.getBeansOfType(DataHandler.class).entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                        e -> {
                            ShowName annotation = e.getValue().getClass().getAnnotation(ShowName.class);
                            if (annotation == null || StrUtil.isEmpty(annotation.value())) {
                                return e.getValue().getClass().getSimpleName();
                            }
                            return annotation.value();
                        },
                        e -> e.getValue().getClass().getSimpleName()
                ));
    }

    public static DataHandler getInstance(String name) {
        // 将 name 的首字母变为小写
        String beanName = name.substring(0, 1).toLowerCase() + name.substring(1);
        return springContext.getBean(beanName, DataHandler.class);
    }

    public void handle(String inputFile, String outPutPath) {
        // 处理 excel 文件
        fileHandle(inputFile);
        // 输出文件
        if (generateOutput) {
            String fileName = outputFile(inputFile, outPutPath);
            Platform.runLater(() -> {
                logArea.appendText("生成输出文件：" + fileName + "\n");
                showInfoDialog("文件生成", "输出文件已生成：" + fileName);
            });
        }
        // 数据库处理
        if (useDB) {
            handleDB();
            Platform.runLater(() -> {
                logArea.appendText("数据库更新完毕");
                showInfoDialog("数据库更新完毕", "数据库更新成功！");
            });
        }
    }

    // 处理文件
    protected abstract void fileHandle(String inputFile);
    // 输出文件
    protected abstract String outputFile(String inputFile, String outputDir);
    // 数据库处理
    protected abstract void handleDB();

    private void showInfoDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
