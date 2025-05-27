package com.njxnet.service.tmsp.pagehelp;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.njxnet.service.tmsp.constant.DelEnum;
import com.njxnet.service.tmsp.dao.TmspSysUserDao;
import com.njxnet.service.tmsp.entity.TmspSysUser;
import com.njxnet.service.tmsp.model.dto.TmspSysUserDTO;
import com.njxnet.service.tmsp.service.TmspSysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Stone
 * @since 2025-05-27
 */
@SpringBootTest
public class PageHelperTest {

    @Resource
    private TmspSysUserService sysUserService;


    // 使用 pageHelper 配合 mp 完成分页查询
    @Test
    public void testPageHelper() {
        // 1. 创建一个 PageHelper 对象
        PageHelper pageHelper = new PageHelper();
        // 2. 设置分页参数
        pageHelper.startPage(2, 2);
        List<TmspSysUser> list = sysUserService.list(sysUserService.query().eq("del_mark", DelEnum.EXIST.getCode())
                .like("user_name", "%" + "ad" + "%")
                .getWrapper());

        List<TmspSysUser> list2 = sysUserService.list(sysUserService.query().eq("del_mark", DelEnum.EXIST.getCode())
                .getWrapper());

        PageInfo<TmspSysUser> pageInfo = new PageInfo<>(list);
        PageInfo<TmspSysUser> pageInfo2 = new PageInfo<>(list2);
        System.out.println(pageInfo);
        System.out.println(pageInfo2);
    }


    // 使用 pageHelper 完成多表连接的多个分页查询



}
