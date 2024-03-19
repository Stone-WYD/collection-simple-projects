package com.wyd.sql.temp;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: sa-token-demo-springboot
 * @description: 梅江数据获取
 * @author: Stone
 * @create: 2023-10-25 15:59
 **/
public class MeiJiangSqlTest {

    public static void main(String[] args) {
        File sourceFile = new File("C:\\Users\\HP\\Desktop\\needHandle.txt");
        File targetFile = new File("C:\\Users\\HP\\Desktop\\target.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {
            // 写入第一行内容
            writeFirstLine(writer);

            String line;
            while ((line = reader.readLine()) != null) {
                // 对读取到的内容进行处理，这里只是简单地添加了一个前缀
                String processedLine = handleLine(line);
                writer.write(processedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeFirstLine(BufferedWriter writer) throws IOException {
        writer.write("case_id,ah,court_code,undertaker,case_status,case_type,register_time,accept_time,update_date");
        writer.newLine();
    }

    private static String handleLine(String line) throws ParseException {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String[] split = line.split("\\s");
        for (int i = 0; i < split.length; i++) {
            String s = split[i].trim();
            // 空置处理
            if ("null".equals(s)) {
                s = "";
            }
            // 对日期进行处理
            if (s.matches("\\d{14}")) {
                Date date = dateFormat.parse(s);
                if (i == split.length - 1){
                    // 日期转 datetime String
                    SimpleDateFormat forNeedDateString = new SimpleDateFormat("yyyy-MM-dd");
                    s = forNeedDateString.format(date);
                } else {
                    // 日期转 date String
                    SimpleDateFormat forNeedDateString = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    s = forNeedDateString.format(date);
                }
            }
            sb.append(s).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), "");
        return sb.toString();
    }
}
