package com.njxnet.asset_manage.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njxnet.asset_manage.entity.Asset;
import com.njxnet.asset_manage.entity.MyUser;
import org.apache.ibatis.annotations.Param;


import java.awt.print.Pageable;
import java.util.List;

/**
 * (MyUser)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-29 18:18:43
 */
public interface MyUserDao extends BaseMapper<MyUser>{

}

