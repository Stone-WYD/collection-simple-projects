package com.njxnet.asset_manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.common.AjaxResultUtil;
import com.njxnet.asset_manage.common.BaseException;
import com.njxnet.asset_manage.common.ResultStatusCode;
import com.njxnet.asset_manage.dao.AssetDao;
import com.njxnet.asset_manage.dao.MyUserDao;
import com.njxnet.asset_manage.entity.Asset;
import com.njxnet.asset_manage.entity.MyUser;
import com.njxnet.asset_manage.model.AssetDTO;
import com.njxnet.asset_manage.model.UserDTO;
import com.njxnet.asset_manage.model.query.UserManageQuery;
import com.njxnet.asset_manage.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static com.njxnet.asset_manage.service.common.MyCommonService.queryForPage;

/**
 * @program: asset-manage
 * @description: 用户service实现类
 * @author: Stone
 * @create: 2024-04-15 13:45
 **/
@Service
public class UserServiceImpl extends ServiceImpl<MyUserDao, MyUser> implements UserService {

    @Resource
    private MyUserDao myUserDao;

    @Override
    public AjaxResult change(UserDTO query) {
        if (query.getRoleId() == null) {
            return AjaxResultUtil.getBussiseFalseAjaxResult(new AjaxResult<>(), "用户角色不能为空！", 500);
        }

        if (query.getId() == null) {
            // 新增
            LambdaQueryWrapper<MyUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MyUser::getUserName, query.getUserName());
            List<MyUser> list = myUserDao.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                throw new BaseException(ResultStatusCode.FAIL.getCode(), "已存在同名账号！");
            }
            myUserDao.insert(query);
        } else {
            // 更新
            myUserDao.updateById(query);
        }
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }

    @Override
    public AjaxResult<Page<UserDTO>> query(UserManageQuery query) {
        return queryForPage(UserDTO.class, () ->
                page(new Page<>(query.getPage(), query.getSize()),
                        query().ge(query.getBeginDate()!=null, "begin_time", query.getBeginDate())
                                .le(query.getEndDate()!=null, "end_time", query.getEndDate())
                                .getWrapper()));
    }

    @Override
    public void delete(String userId) {
        removeById(userId);
    }

    @Override
    public MyUser queryById(Long userId) {
        return getById(userId);
    }
}

