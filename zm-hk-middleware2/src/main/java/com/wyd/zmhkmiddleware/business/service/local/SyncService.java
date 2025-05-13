package com.wyd.zmhkmiddleware.business.service.local;

import com.wyd.zmhkmiddleware.business.model.local.dto.SyncInfoDTO;
import com.wyd.zmhkmiddleware.business.model.local.query.PersonQuery;
import com.wyd.zmhkmiddleware.common.AjaxCustomResult;
import com.wyd.zmhkmiddleware.common.AjaxResult;

import java.util.List;

/**
 * @author Stone
 * @since 2025-05-12
 */
public interface SyncService {


    AjaxCustomResult<List<SyncInfoDTO>> getPersonList(PersonQuery personQuery);

    AjaxResult<Boolean> syncInfo(List<SyncInfoDTO> dtoList);
}
