package com.njxnet.asset_manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.common.AjaxResultUtil;
import com.njxnet.asset_manage.dao.MyUserDao;
import com.njxnet.asset_manage.dao.WbInfoDao;
import com.njxnet.asset_manage.entity.MyUser;
import com.njxnet.asset_manage.entity.WbInfo;
import com.njxnet.asset_manage.service.WbInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: asset-manage
 * @description:
 * @author: Stone
 * @create: 2024-04-16 14:52
 **/
@Service
public class WbInfoServiceImpl extends ServiceImpl<WbInfoDao, WbInfo> implements WbInfoService {

    @Resource
    private MyUserDao myUserDao;

    @Override
    public AjaxResult change(WbInfo wbInfo) {
        if (wbInfo.getId() == null) {
            // 新增
            wbInfo.setCreateTime(LocalDateTime.now());
            save(wbInfo);
        } else {
            // 更新
            wbInfo.setReceiveTime(LocalDateTime.now());
            updateById(wbInfo);
        }
        AjaxResult<WbInfo> result = new AjaxResult<>();
        result.setData(wbInfo);
        return AjaxResultUtil.getTrueAjaxResult(result);
    }

    @Override
    public AjaxResult<List<WbInfo>> queryByAssetId(Integer assetId) {
        LambdaQueryWrapper<WbInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WbInfo::getAssetId, assetId);
        List<WbInfo> wbInfos = getBaseMapper().selectList(wrapper);
        List<Long> receiveIds = wbInfos.stream().map(WbInfo::getReceiveId).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(receiveIds)) {
            // 填充用户名
            LambdaQueryWrapper<MyUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MyUser::getId, receiveIds);
            List<MyUser> myUserList = myUserDao.selectList(queryWrapper);
            Map<Long, String> userNameMap = myUserList.stream().collect(Collectors.toMap(MyUser::getId, MyUser::getUserName));
            for (WbInfo wbInfo : wbInfos) {
                wbInfo.setReceiveName(userNameMap.get(wbInfo.getReceiveId()));
            }
        }

        AjaxResult<List<WbInfo>> result = new AjaxResult<>();
        result.setData(wbInfos);
        return AjaxResultUtil.getTrueAjaxResult(result);
    }

}

