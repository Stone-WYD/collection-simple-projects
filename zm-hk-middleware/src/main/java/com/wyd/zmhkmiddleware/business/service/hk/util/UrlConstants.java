package com.wyd.zmhkmiddleware.business.service.hk.util;

/**
 * @author xh
 * @date 2025-03-19
 * @Description:
 */
public class UrlConstants {

    // 批量添加组织
    public static final String BATCH_ADD_ORG = "/api/resource/v1/org/batch/add";
    // 批量删除组织
    public static final String BATCH_DELETE_ORG = "/api/resource/v1/org/batch/delete";
    // 根据组织编号查询组织
    public static final String GET_ORG = "/api/resource/v1/org/orgIndexCodes/orgInfo";
    // 可根据名称模糊查询组织
    public static final String QUERY_ORG = "/api/resource/v2/org/advance/orgList";
    // 修改组织
    public static final String UPDATE_ORG = "/api/resource/v1/org/single/update";

    // 批量添加人员
    public static final String BATCH_ADD_PERSON = "/api/resource/v1/person/batch/add";
    // 批量删除人员
    public static final String BATCH_DELETE_PERSON = "/api/resource/v1/person/batch/delete";
    // 修改人员
    public static final String UPDATE_PERSON = "/api/resource/v1/person/single/update";

    // 下面的接口不一定需要使用
    // 获取人员
    // 获取组织下的人员列表
    public static final String GET_PERSON_FROM_ORG = "/api/resource/v2/person/orgIndexCode/personList";
    // 获取人员详情 根据人员唯一字段获取人员详细信息
    public static final String GET_PERSON_DETAIL = "/api/resource/v1/person/condition/personInfo";

}
