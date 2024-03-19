package com.njxnet.service.tmsp.design.core1_postprocessor.send.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.design.core1_postprocessor.PostContext;
import com.njxnet.service.tmsp.design.core1_postprocessor.send.SendMessageOuterPostProcessor;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.BlackListService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.njxnet.service.tmsp.common.ResultStatusCode.PHONES_EMPTY_ERROR;

@Component
public class SendMessageOuterCheckBlackListPostProcessor implements SendMessageOuterPostProcessor {

    @Resource
    private BlackListService blackListService;

    @Override
    public boolean handleBefore(SendInfo sendInfo) {
        if (StrUtil.isBlank(sendInfo.getMobile()) && StrUtil.isBlank(sendInfo.getMobiles())){
            // 没有手机号
            throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());
        }

        if (StrUtil.isNotBlank(sendInfo.getMobile())) {
            // 有 mobile，说明是单发
            if (isPhoneInBlackList(sendInfo.getMobile())) {
                throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());
            }
        }
        if (StrUtil.isNotBlank(sendInfo.getMobiles())){
            // 有 mobiles，说明是群发
            String mobiles = removeBlackListPhoneReturnString(sendInfo.getMobiles(), sendInfo);
            sendInfo.setMobiles(mobiles);
        }
        return false;
    }

    @Override
    public int getPriprity() {
        // 在最外面被执行，应该是先执行的
        return -1;
    }

    private boolean isPhoneInBlackList(String phone){
        return blackListService.query().eq("phone_number", phone).one() != null;
    }

    private String removeBlackListPhoneReturnString(String mobiles, SendInfo sendInfo) {
        List<String> resultList = new ArrayList<>();
        List<String> mobileList = Arrays.stream(mobiles.split(",")).map(String::trim).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(mobileList)) throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());

        for (String mobile : mobileList){
            if (!isPhoneInBlackList(mobile)) {
                resultList.add(mobile);
            }
        }
        if (resultList.size() == 0){
            throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());
        }

        // 手机号list存入context对象中
        sendInfo.setMobileList(resultList);

        if (resultList.size() == 1){
            return resultList.get(0);
        }
        return StrUtil.join(",", resultList);
    }
}
