package org.wyd.util;

import org.wyd.common.BusinessException;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author xh
 * @date 2025-02-07
 * @Description:
 */
public class DataUtils {

    public static Double[] location2Array(String location) {
        try {
            String[] parts = location.replaceAll("[()]", "").split(",");
            // 设置三位小数格式
            DecimalFormat df = new DecimalFormat("0.000");
            // 提取并格式化每个坐标值
            Double[] result = new Double[parts.length];

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                double number = Double.parseDouble(part.trim());
                result[i] = number;
            }
            return result;
        } catch (NumberFormatException e) {
            throw new BusinessException(location + "转换失败!");
        }
    }

    public static String array2Location(Double[] nums) {
        // 检查数组是否为null或者是否为空
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("数组不能为空或null");
        }

        // 使用Stream API将数组元素转换为字符串，并用逗号连接
        return Arrays.stream(nums)
                .map(coord -> String.format("%.3f", coord))
                .collect(Collectors.joining(", ", "(", ")"));
    }

    public static void main(String[] args) {
        Double[] nums = {3.5, 4.7, 5.9};
        String s = array2Location(nums);
        System.out.println(s);
    }

}
