package com.wyd.satokendemospringboot.demos.dao;

import com.wyd.satokendemospringboot.demos.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 不同角色对不同业务有默认的权限，如果对某个账户设置了权限，则按照账户权限为准(Permission)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-29 18:19:59
 */
public interface PermissionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Permission queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param permission 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Permission> queryAllByLimit(@Param("permission") Permission permission, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param permission 查询条件
     * @return 总行数
     */
    long count(@Param("permission") Permission permission);

    /**
     * 新增数据
     *
     * @param permission 实例对象
     * @return 影响行数
     */
    int insert(Permission permission);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Permission> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Permission> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Permission> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Permission> entities);

    /**
     * 修改数据
     *
     * @param permission 实例对象
     * @return 影响行数
     */
    int update(Permission permission);


}

