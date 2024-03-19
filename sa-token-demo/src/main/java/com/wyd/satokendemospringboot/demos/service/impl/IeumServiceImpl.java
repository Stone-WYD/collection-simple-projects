package com.wyd.satokendemospringboot.demos.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.common.result.MyResultUtil;
import com.wyd.satokendemospringboot.demos.constants.TestIEum;
import com.wyd.satokendemospringboot.demos.dao.IeumDao;
import com.wyd.satokendemospringboot.demos.entity.Ieum;
import com.wyd.satokendemospringboot.demos.entity.dto.IEumContainerDTO;
import com.wyd.satokendemospringboot.demos.service.IeumService;
import org.springframework.stereotype.Service;

/**
 * (Ieum)表服务实现类
 *
 * @author makejava
 * @since 2023-06-24 16:38:43
 */
@Service("ieumService")
public class IeumServiceImpl extends ServiceImpl<IeumDao, Ieum> implements IeumService {

    @Override
    public MyResult testIEum(IEumContainerDTO iEumContainerDTO) {
        System.out.println(iEumContainerDTO);
        System.out.println("===================");
        // 更改一下枚举类
        TestIEum iEum = iEumContainerDTO.getTestIeum();
        if (iEum == null) {
            return MyResultUtil.getBussinessErrorResult("没有转换枚举类属性成功...");
        }
        if (TestIEum.YXY.getValue().equals(iEum.getValue())){
            iEumContainerDTO.setTestIeum(TestIEum.WYD);
        } else iEumContainerDTO.setTestIeum(TestIEum.YXY);
        System.out.println(iEumContainerDTO);

        // 返回结果
        MyResult<IEumContainerDTO> result = new MyResult<>();
        result.setData(iEumContainerDTO);
        return MyResultUtil.getTrueResult(result);
    }

    @Override
    public MyResult testIEumWithDataBaseOps(IEumContainerDTO iEumContainerDTO) {
        Ieum ieum = query().eq("test_name", iEumContainerDTO.getTestName())
                .eq("test_ieum", iEumContainerDTO.getTestIeum().getCode()).list().get(0);
        IEumContainerDTO data = BeanUtil.copyProperties(ieum, IEumContainerDTO.class);
        MyResult result = new MyResult();
        result.setData(data);
        return MyResultUtil.getTrueResult(result);
    }
}

