package com.jgdsun;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.jgdsun.qms.model.TQueueManageEntity;
import com.jgdsun.qms.model.TQueueManageStatusEntity;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Stone
 * @since 2025-10-15
 *///TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class EqTest {
    private static EasyEntityQuery entityQuery;
    public static void main(String[] args) {
        LogFactory.useStdOutLogging();
        DataSource dataSource = getDataSource();
        EasyQueryClient client = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    //进行一系列可以选择的配置
                    //op.setPrintSql(true);
                })
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .build();
        entityQuery = new DefaultEasyEntityQuery(client);

        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        //如果不存在数据库则创建
        databaseCodeFirst.createDatabaseIfNotExists();
        //自动同步数据库表
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(TQueueManageEntity.class, TQueueManageStatusEntity.class));
        //执行命令
        codeFirstCommand.executeWithTransaction(arg->{
            System.out.println(arg.getSQL());
            arg.commit();
        });
    }

    /**
     * 初始化数据源
     * @return
     */
    private static DataSource getDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/ibms_dag?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);

        return dataSource;
    }
}
