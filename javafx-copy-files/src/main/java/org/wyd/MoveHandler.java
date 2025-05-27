package org.wyd;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import org.wyd.util.ExcelUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.wyd.FxApplication.showAlert;

/**
 * @author Stone
 * @since 2025-05-24
 */
public class MoveHandler {


    public static void copy(String inputFile, String inputDir,
                            String outPutPath, List<String> fileTypes, TextArea logArea) {
        logArea.appendText("开始移动文件...");
        if (CollectionUtil.isEmpty(fileTypes)) return;

        // 创建一个10个线程的线程池
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(10);

        // 获取所有图号
        Set<String> picNumbers = ExcelUtil.getPicNumFromExcel(inputFile);
        Map<String, Boolean> picNumbersMap = picNumbers.stream().collect(Collectors.toMap(k -> k, v -> false));
        List<Future<Boolean>> futures = new ArrayList<>();
        // 遍历 inputDir 下所有文件
        List<File> files = FileUtil.loopFiles(inputDir);
        for (File file : files) {
            // 获取文件名
            String fileName = file.getName();
            // 判断文件类型
            for (String fileType : fileTypes) {
                if (fileName.endsWith(fileType) || fileName.endsWith(fileType.toUpperCase())) {
                    picNumbers.forEach(
                            picNumber -> {
                                if (fileName.contains(picNumber)) {
                                    picNumbersMap.put(picNumber, true);
                                    // 移动文件到指定目录
                                    futures.add( threadPoolExecutor.submit(() -> {
                                        try {
                                            FileUtil.copy(file, new File(outPutPath + File.separator + fileName)
                                                    , true);
                                            return true;
                                        } catch (Exception e) {
                                            logArea.appendText("文件移动失败，文件路径为：" + file.getAbsolutePath() + "\n");
                                            return true;
                                        }
                                    }));
                                }
                            }
                    );
                }
            }
        }
        // 等待所有任务执行结束
        for (Future<Boolean> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                logArea.appendText("执行异常...");
            }
        }
        picNumbersMap.forEach((k, v) -> {
            if (!v) {
                logArea.appendText("图号未找到对应文件：" + k + "\n");
            }
        });
        showAlert("处理完成", "文件移动完毕！");
        logArea.appendText("文件移动完毕！");
    }

}
