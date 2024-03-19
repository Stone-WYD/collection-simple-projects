package com.wyd.satokendemospringboot.demos.service.impl;

import com.wyd.satokendemospringboot.demos.common.ex.DBException;
import com.wyd.satokendemospringboot.demos.dao.MyUserDao;
import com.wyd.satokendemospringboot.demos.dao.RoleDao;
import com.wyd.satokendemospringboot.demos.entity.MyUser;
import com.wyd.satokendemospringboot.demos.entity.Permission;
import com.wyd.satokendemospringboot.demos.dao.PermissionDao;
import com.wyd.satokendemospringboot.demos.entity.Role;
import com.wyd.satokendemospringboot.demos.entity.po.PermissionPo;
import com.wyd.satokendemospringboot.demos.constants.OpsEnum;
import com.wyd.satokendemospringboot.demos.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 不同角色对不同业务有默认的权限，如果对某个账户设置了权限，则按照账户权限为准(Permission)表服务实现类
 *
 * @author makejava
 * @since 2023-05-22 17:49:02
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionDao permissionDao;

    @Resource
    private MyUserDao myUserDao;

    @Resource
    private RoleDao roleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Permission queryById(Long id) {
        return this.permissionDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param permission 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Permission> queryByPage(Permission permission, PageRequest pageRequest) {
        long total = this.permissionDao.count(permission);
        return new PageImpl<>(this.permissionDao.queryAllByLimit(permission, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param permission 实例对象
     * @return 实例对象
     */
    @Override
    public Permission insert(Permission permission) {
        this.permissionDao.insert(permission);
        return permission;
    }

    /**
     * 修改数据
     *
     * @param permission 实例对象
     * @return 实例对象
     */
    @Override
    public Permission update(Permission permission) {
        this.permissionDao.update(permission);
        return this.queryById(permission.getId());
    }


    @Override
    public List<String> queryAllPermission(Long userId) {
        // 查询账号所有权限信息
        List<String> permission = new ArrayList<>();
        // 查找用户角色
        MyUser myUser = myUserDao.queryById(userId);
        if (myUser == null) throw new DBException("用户不存在！");
        Long roleId = myUser.getRoleId();

        // 查询数据库
        Permission query = new Permission();
        query.setUserId(userId);
        List<Permission> permissionList;
        permissionList = permissionDao.queryAllByLimit(query, null);
        // 如果没有权限信息，则根据角色id查询权限
        if (CollectionUtils.isEmpty(permissionList))  {
            query.setRoleId(roleId);
            query.setUserId(null);
            permissionList = permissionDao.queryAllByLimit(query, null);
        }
        if (CollectionUtils.isEmpty(permissionList)) throw new DBException("角色没有对应的权限信息，roleId为：" + roleId );
        // 获取结果
        for (Permission p : permissionList) {
            PermissionPo po = permissionToPo(p);
            List<String> ops = po.getOps();
            String business = po.getBusiness();
            for (String op : ops) {
                permission.add(business + "." + op );
            }
        }
        // 去重
        permission = permission.stream().distinct().collect(Collectors.toList());
        return permission;
    }

    @Override
    public String queryRole(Long userId) {
        MyUser myUser = myUserDao.queryById(userId);
        if (myUser == null) throw new DBException("用户不存在！");
        Role role = roleDao.queryById(myUser.getRoleId());
        return role.getRoleName();
    }


    private PermissionPo permissionToPo(Permission permission){
        String permissions = permission.getPermissions();
        List<String> ops = new ArrayList<>();
        if (permissions==null || permissions.isEmpty()) throw new DBException("permissionToPo: 无操作权限信息！");

        // 操作权限的转换
        for (OpsEnum opsEnum : OpsEnum.values())
            if (permissions.contains(opsEnum.getCode())) ops.add(opsEnum.getName());

        // 得到 PermissionPo
        PermissionPo permissionPo = new PermissionPo();
        permissionPo.setBusiness(permission.getBusiness());
        permissionPo.setRoleId(permission.getRoleId());
        permissionPo.setId(permissionPo.getId());
        permissionPo.setOps(ops);
        return permissionPo;
    }

    private Permission poToPermission(PermissionPo permissionPo){
        List<String> ops = permissionPo.getOps();
        if (ops==null || ops.isEmpty()) throw new DBException("poToPermission: 无操作权限信息！");
        StringBuilder sb = new StringBuilder();

        // 操作权限转换
        for (String op : ops) {
            for (OpsEnum opsEnum : OpsEnum.values()) {
                opsEnum.getName().equals(op);
                sb.append(opsEnum.getCode());
            }
        }
        // 返回结果
        Permission permission = new Permission();
        permission.setRoleId(permissionPo.getRoleId());
        permission.setBusiness(permissionPo.getBusiness());
        permission.setPermissions(sb.toString());
        return permission;
    }

}
