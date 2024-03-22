package com.njxnet;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @program: asset-manage
 * @description: war包启动，修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 * @author: Stone
 * @create: 2024-03-22 16:45
 **/
public class WarApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(AssetManageApplication.class);
    }
}



