package com.wyd.zmhkmiddleware.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddConvertAnnotation {
    private static final String TARGET_ANNOTATION_REGEX = "@Table(?:Id|Field)\\(\"([^\"]+)\"\\)";
    private static final String SERIALIZED_NAME_ANNOTATION = "@SerializedName(\"%s\")";

    public static void main(String[] args) throws IOException {
        /*Scanner scanner = new Scanner(System.in);

        System.out.print("请输入要处理的包路径（示例：src/main/java/com/wyd/zmhkmiddleware）：");
        String packagePath = scanner.nextLine();*/

        String packagePath = "src/main/java/com/wyd/zmhkmiddleware/business/model/local/po";
        processJavaFiles(Paths.get(packagePath));
        System.out.println("处理完成！");
    }

    private static void processJavaFiles(Path rootPath) throws IOException {
        Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".java")) {
                    processSingleFile(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void processSingleFile(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            List<String> modifiedLines = new ArrayList<>();
            boolean modified = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                modifiedLines.add(line);

                if (line.matches(".*" + TARGET_ANNOTATION_REGEX + ".*")) {
                    String value = line.replaceAll(".*" + TARGET_ANNOTATION_REGEX + ".*", "$1");
                    String newAnnotation = String.format(SERIALIZED_NAME_ANNOTATION, value);

                    // 检查下一行是否已存在该注解
                    if (i + 1 >= lines.size() || !lines.get(i + 1).contains(newAnnotation)) {
                        modifiedLines.add(newAnnotation);
                        modified = true;
                    }
                }
            }

            if (modified) {
                Files.write(filePath, modifiedLines, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("已处理文件: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("处理文件失败: " + filePath + " - " + e.getMessage());
        }
    }
}
