package com.wyd.asset_manage.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: GISP_V1.0
 * @description: pio工具类
 * @author: Stone
 * @create: 2024-03-07 09:42
 **/
public class PioUtil {


    public static String getCellStringValue(XSSFCell cell) {
        String value = "";
        if (cell != null) {
            try {
                // 判断类型
                switch (cell.getCellType()) {//字符串
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 日期类型
                            // 短日期转化为字符串
                            Date date = cell.getDateCellValue();
                            if (date != null) {
                                // 标准0点 1970/01/01 08:00:00
                                if (date.getTime() % 86400000 == 16 * 3600 * 1000 && cell.getCellStyle().getDataFormat() == 14) {
                                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                } else {
                                    value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                                }
                            }
                        } else {// 数值
                            //System.out.println("Value:"+cell.getNumericCellValue());
                            String numberStr = new HSSFDataFormatter().formatCellValue(cell);
                            // 货币格式，如：1,200.00
                            if (numberStr.contains(",")) {
                                numberStr = numberStr.replace(",", "");
                            }
                            if (numberStr.contains("E")) {    // 科学计算法
                                numberStr = new DecimalFormat("0").format(cell.getNumericCellValue());    //4.89481368464913E14还原为长整数
                                value = String.valueOf(Long.parseLong(numberStr));
                            } else {
                                if (numberStr.contains(".")) {    // 小数
                                    value = String.valueOf(Double.parseDouble(numberStr));
                                } else {    // 转换为整数
                                    value = String.valueOf(Long.parseLong(numberStr));
                                }
                            }
                        }
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        // 导入时如果为公式生成的数据则无值
                        break;
                    case Cell.CELL_TYPE_BLANK://空
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        value = (cell.getBooleanCellValue() ? "Y" : "N");
                        break;
                }
            } catch (Exception e) {
                System.out.println("读取XLS异常，异常单元格：" + cell);
                throw e;
            }
        }

        return value;
    }

}

