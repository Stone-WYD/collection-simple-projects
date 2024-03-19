package com.wyd.satokendemospringboot.demos.service;

import com.wyd.satokendemospringboot.demos.entity.MyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (MyUser)表服务接口
 *
 * @author makejava
 * @since 2023-05-29 15:50:54
 */
public interface MyUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MyUser queryById(Long id);

    /**
     * 分页查询
     *
     * @param myUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<MyUser> queryByPage(MyUser myUser, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param myUser 实例对象
     * @return 实例对象
     */
    MyUser insert(MyUser myUser);

    /**
     * 修改数据
     *
     * @param myUser 实例对象
     * @return 实例对象
     */
    MyUser update(MyUser myUser);

}
