package com.wyd.zmhkmiddleware.business.model.local.query;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Stone
 * @since 2025-05-12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonQuery {

    @ApiModelProperty(notes = "人员姓名", name = "zemplnm")
    private String zemplnm;

    @ApiModelProperty(notes = "组织名称", name = "zorgnm")
    private String zorgnm;

    @ApiModelProperty(notes = "页码", name = "page", required = true)
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小为1")
    private Integer page;

    @ApiModelProperty(notes = "每页数量", name = "size", required = true)
    @NotNull(message = "每页数量不能为空")
    @Min(value = 1, message = "每页数量最少为1")
    private Integer limit;

    @ApiModelProperty(notes = "是否已同步", name = "syncFlag")
    private String syncFlag;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag;
    }

    public String getZemplnm() {
        return getSplitStr(zemplnm);
    }

    public void setZemplnm(String zemplnm) {
        this.zemplnm = zemplnm;
    }

    public String getZorgnm() {
        return getSplitStr(zorgnm);
    }

    public void setZorgnm(String zorgnm) {
        this.zorgnm = zorgnm;
    }

//    private void adjustQuery(PersonQuery query) {
//        String userName = query.getZemplnm();
//        if (StrUtil.isNotEmpty(userName)) {
//            // 给每个字符中间加入 “ % || ”
//            query.setZemplnm(split(userName));
//        }
//        String orgName = query.getZorgnm();
//        if (StrUtil.isNotEmpty(orgName)) {
//            query.setZorgnm(split(orgName));
//        }
//    }

    private String getSplitStr(String str) {
        if (StrUtil.isNotEmpty(str)) {
            return split(str);
        } else return null;
    }

    private String split(String  str) {
        // 将字符串拆分成单个字符组成的数据
        StringBuilder sb = new StringBuilder();
        String[] splits = str.split("");
        for (String split : splits) {
            sb.append(" '%' || ").append("'").append(split).append("'").append(" ||");
        }
        sb.append(" '%'");
        return sb.toString().trim();
    }

/*    public static void main(String[] args) {
        PersonQuery query = new PersonQuery();
        query.setZorgnm("李美莉");
        query.setZemplnm("小明");
        adjustQuery( query);
        System.out.println( query.getZorgnm());
        System.out.println( query.getZemplnm());
    }*/


}
