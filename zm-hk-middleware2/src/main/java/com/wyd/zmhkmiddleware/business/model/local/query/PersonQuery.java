package com.wyd.zmhkmiddleware.business.model.local.query;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.Length;
import org.checkerframework.checker.units.qual.min;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Stone
 * @since 2025-05-12
 */
public class PersonQuery {

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
}
