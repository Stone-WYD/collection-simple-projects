package com.wyd.sql.temp;

/**
 * @program: sa-token-demo-springboot
 * @description:
 * @author: Stone
 * @create: 2023-10-26 10:15
 **/
public class MainTest {
    public static void main(String[] args) {
        String x = "20230102112028";
        System.out.println(x.length());
        System.out.println(x.matches("\\d{14}"));
    }
}
