package com.wyd.zmhkmiddleware.business.model.local.query;

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
@Data
public class PersonQuery {

    @ApiModelProperty(notes = "页码", name = "page", required = true)
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小为1")
    private Integer page;

    @ApiModelProperty(notes = "每页数量", name = "size", required = true)
    @NotNull(message = "每页数量不能为空")
    @Min(value = 1, message = "每页数量最少为1")
    private Integer limit;

}
