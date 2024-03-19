package com.wyd.satokendemospringboot.demos.service.impl;

import com.wyd.satokendemospringboot.demos.entity.MyUser;
import com.wyd.satokendemospringboot.demos.dao.MyUserDao;
import com.wyd.satokendemospringboot.demos.service.MyUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (MyUser)表服务实现类
 *
 * @author makejava
 * @since 2023-05-29 15:50:55
 */
@Service("myUserService")
public class MyUserServiceImpl implements MyUserService {
    @Resource
    private MyUserDao myUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MyUser queryById(Long id) {
        return this.myUserDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param myUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<MyUser> queryByPage(MyUser myUser, PageRequest pageRequest) {
        long total = this.myUserDao.count(myUser);
        return new PageImpl<>(this.myUserDao.queryAllByLimit(myUser, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param myUser 实例对象
     * @return 实例对象
     */
    @Override
    public MyUser insert(MyUser myUser) {
        this.myUserDao.insert(myUser);
        return myUser;
    }

    /**
     * 修改数据
     *
     * @param myUser 实例对象
     * @return 实例对象
     */
    @Override
    public MyUser update(MyUser myUser) {
        this.myUserDao.update(myUser);
        return this.queryById(myUser.getId());
    }

}
