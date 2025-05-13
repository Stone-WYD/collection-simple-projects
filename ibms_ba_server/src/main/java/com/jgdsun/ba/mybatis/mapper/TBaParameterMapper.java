package com.jgdsun.ba.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jgdsun.ba.mybatis.entity.TBaParameter;

/**
* @author 86186
* @description 针对表【t_ba_parameter(BA参数)】的数据库操作Mapper
* @createDate 2022-09-07 10:34:38
* @Entity com.jgdsun.ba.mybatis.entity.TBaParameter
*/
public interface TBaParameterMapper extends BaseMapper<TBaParameter> {

    /**
     * 编辑楼栋
     * @param parameter
     * @return
     */
    int updateParameter(TBaParameter parameter);


}
