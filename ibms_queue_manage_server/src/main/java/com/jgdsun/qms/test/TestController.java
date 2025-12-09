package com.jgdsun.qms.test;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.solon.annotation.Db;
import com.jgdsun.qms.model.TQueueManageEntity;
import com.jgdsun.qms.model.TQueueManageStatusEntity;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.MethodType;
import org.noear.solon.data.annotation.Ds;

/**
 * @author Stone
 * @since 2025-10-16
 */
@Controller
@Mapping("/test")
public class TestController {

    /**
     * 注意必须是配置多数据源的其中一个
     */
    @Db("db1")
    private EasyEntityQuery easyEntityQuery;

    @Mapping(value = "/hello",method = MethodType.GET)
    public String hello(){
        return "Hello World";
    }
    @Mapping(value = "/queryTopic",method = MethodType.GET)
    public Object queryTopic(){
        TQueueManageEntity entity = easyEntityQuery.queryable(TQueueManageEntity.class).where(t -> t.name().eq("wyd")).singleNotNull("没找到");
        System.out.println(entity);
        return null;
    }
}
