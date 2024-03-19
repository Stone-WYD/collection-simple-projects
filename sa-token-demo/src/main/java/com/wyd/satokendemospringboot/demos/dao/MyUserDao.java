package com.wyd.satokendemospringboot.demos.dao;

import com.wyd.satokendemospringboot.demos.entity.MyUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (MyUser)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-29 18:18:43
 */
public interface MyUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MyUser queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param myUser 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<MyUser> queryAllByLimit(@Param("myUser") MyUser myUser, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param myUser 查询条件
     * @return 总行数
     */
    long count(@Param("myUser") MyUser myUser);

    /**
     * 新增数据
     *
     * @param myUser 实例对象
     * @return 影响行数
     */
    int insert(MyUser myUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MyUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MyUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MyUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MyUser> entities);

    /**
     * 修改数据
     *
     * @param myUser 实例对象
     * @return 影响行数
     */
    int update(MyUser myUser);


}

