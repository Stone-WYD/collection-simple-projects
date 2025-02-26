package com.wyd.asset_manage.test.mybatis.ocp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @program: asset-manage
 * @description: mybatis关于开闭原则使用的测试的实体类
 * @author: Stone
 * @create: 2024-04-07 15:28
 **/
@Data
public class OcpTest {

    //主键
    @TableId(type = IdType.AUTO)
    private Long id;

    //随便加的字段
    private String test;

}

