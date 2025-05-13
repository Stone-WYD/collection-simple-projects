package com.jgdsun.ba.mybatis.mapper;



import com.jgdsun.ba.mybatis.entity.BaseStorey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseStoreyMapper {


    /**
     * 获取所有楼层
     * @param buildId 楼层id
     * @return
     */
    List<BaseStorey> getAllStoreyByBuild(String buildId);

    /**
     * 根据id获取楼层
     * @param id
     * @return
     */
    BaseStorey getStoreyById(String id);

    /**
     * 根据名称获取楼层
     * @param name
     * @param name
     * @return
     */
    BaseStorey getStoreyByName(@Param("buildId") String buildId, @Param("name") String name);


    /**
     * 根据楼层获取楼栋id
     * @param id
     * @return
     */
    String getBuildIdByStorey(String id);

    /**
     * 获取楼层数量
     * @param name
     * @return
     */
    int getStoreyCount(@Param("buildId") String buildId, @Param("name") String name);
    /**
     * 获取楼层
     * @return
     */
    List<BaseStorey> getStorey(@Param("buildId") String buildId, @Param("name") String name, @Param("startRow") int startRow, @Param("maxRows") int maxRows);

    /**
     * 添加楼层
     * @param baseStorey
     * @return
     */
    int addStorey(BaseStorey baseStorey);

    /**
     * 编辑楼层
     * @param baseStorey
     * @return
     */
    int updateStorey(BaseStorey baseStorey);

    /**
     * 删除楼层
     * @param id
     * @return
     */
    int deleteStorey(String id);
}
