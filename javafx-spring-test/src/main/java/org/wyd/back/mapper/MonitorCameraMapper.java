package org.wyd.back.mapper;

import org.apache.ibatis.annotations.Param;
import org.wyd.back.bean.MonitorCamera;

import java.util.List;

/**
 * @author xh
 * @date 2025-02-08
 * @Description:
 */
public interface MonitorCameraMapper {

    MonitorCamera getById(@Param("id") String id);

    int updateByIdOrName(@Param("list")List<MonitorCamera> list);
}
