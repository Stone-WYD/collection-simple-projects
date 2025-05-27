package org.wyd.back.db;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.wyd.common.BusinessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author xh
 * @date 2025-02-08
 * @Description:
 */
public class DynamicDataSource extends AbstractDataSource {

    @Override
    public Connection getConnection() throws SQLException {
        String username = SecurityContextHolder.getUsername();
        String password = SecurityContextHolder.getPassword();
        String url = SecurityContextHolder.getUrl();
        if (username == null || password == null || url == null) {
            throw new BusinessException("数据库参数不能为空");
        }
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        String url = SecurityContextHolder.getUrl();
        if (username == null || password == null || url == null) {
            throw new BusinessException("数据库参数不能为空");
        }
        return DriverManager.getConnection(url, username, password);
    }
}
