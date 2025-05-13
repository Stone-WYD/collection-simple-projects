package com.jgdsun.ba.mybatis.mapper;




import com.jgdsun.ba.mybatis.entity.BaseBuild;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BaseBuildMapper {


    /**
     * 获取所有楼栋
     * @return
     */
    List<BaseBuild> getAllBuild();

    /**
     * 根据id获取楼栋
     * @param id
     * @return
     */
    BaseBuild getBuildById(String id);

    /**
     * 根据名称获取楼栋
     * @param name
     * @return
     */
    BaseBuild getBuildByName(String name);

    /**
     * 获取楼栋数量
     * @param name
     * @return
     */
    int getBuildCount(@Param("name") String name);
    /**
     * 获取楼栋
     * @return
     */
    List<BaseBuild> getBuild(@Param("name") String name, @Param("startRow") int startRow, @Param("maxRows") int maxRows);

    /**
     * 添加楼栋
     * @param baseBuild
     * @return
     */
    int addBuild(BaseBuild baseBuild);

    /**
     * 编辑楼栋
     * @param baseBuild
     * @return
     */
    int updateBuild(BaseBuild baseBuild);

    /**
     * 删除楼栋
     * @param id
     * @return
     */
    int deleteBuild(String id);
}
