package com.wyd.satokendemospringboot.demos.service;

import com.wyd.satokendemospringboot.demos.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 不同角色对不同业务有默认的权限，如果对某个账户设置了权限，则按照账户权限为准(Permission)表服务接口
 *
 * @author makejava
 * @since 2023-05-22 17:49:02
 */
public interface PermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Permission queryById(Long id);

    /**
     * 分页查询
     *
     * @param permission 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Permission> queryByPage(Permission permission, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param permission 实例对象
     * @return 实例对象
     */
    Permission insert(Permission permission);

    /**
     * 修改数据
     *
     * @param permission 实例对象
     * @return 实例对象
     */
    Permission update(Permission permission);


    List<String> queryAllPermission(Long userId);

    String queryRole(Long userId);

}
