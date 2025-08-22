package com.wyd.zmhkmiddleware.business.mapper;

import com.wyd.zmhkmiddleware.business.model.local.dto.SyncInfoDTO;
import com.wyd.zmhkmiddleware.business.model.local.po.EtEmplBasic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyd.zmhkmiddleware.business.model.local.query.PersonQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工基本信息 Mapper 接口
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
public interface EtEmplBasicMapper extends BaseMapper<EtEmplBasic> {

    // 查询人员同步状态的总数
    // type 固定为 SyncRecordEnum.SYNC_TYPE_PERSON.getCode()
    // syncSuccessFlag 固定为 SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode()
    Long countSync(@Param("type") Integer type, @Param("syncStatus") boolean syncStatus, @Param("syncSuccessFlag") Integer syncSuccessFlag);

    // 分页查询指定状态的人员信息
    // type 固定为 SyncRecordEnum.SYNC_TYPE_PERSON.getCode()
    // syncSuccessFlag 固定为 SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode()
    List<EtEmplBasic> listBySyncStatus(@Param("type") Integer type, @Param("syncStatus") boolean syncStatus, @Param("syncSuccessFlag") Integer syncSuccessFlag,
                                       @Param("offset") Integer offset, @Param("size") Integer size);


    List<SyncInfoDTO> listSyncInfoDTO(@Param("query") PersonQuery query);

    Long countSyncInfoDTO(@Param("query") PersonQuery personQuery);
}
