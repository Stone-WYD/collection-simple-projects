package com.wyd.excel.read;

import com.alibaba.excel.EasyExcel;
import com.wyd.excel.model.DynamicPrcessPriceData;

import java.io.InputStream;

public class DynamicColumnTest {

    public static void main(String[] args) {
        InputStream inputStream = DynamicColumnTest.class.getClassLoader().getResourceAsStream("报价模板-final.xlsx");
        if (inputStream != null) {
            // 这里 需要指定读用哪个class去读，然后读取第一个sheet
            EasyExcel.read(inputStream, DynamicPrcessPriceData.class, new DemoDataListener()).sheet()
                    // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
                    .headRowNumber(4).doRead();
        }


    }
}
