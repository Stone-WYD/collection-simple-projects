package com.wyd.zmhkmiddleware.business.service.local;

import com.wyd.zmhkmiddleware.business.model.local.po.EtEmplBasic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyd.zmhkmiddleware.business.model.local.query.PersonQuery;
import com.wyd.zmhkmiddleware.common.AjaxCustomResult;

import java.util.List;

/**
 * <p>
 * 员工基本信息 服务类
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
public interface EtEmplBasicService extends IService<EtEmplBasic> {

    AjaxCustomResult<List<EtEmplBasic>> getPersonListWithQuery(PersonQuery personQuery);


}
