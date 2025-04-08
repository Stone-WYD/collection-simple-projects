package com.wyd.feignsimpletest.kaiheyun.model.result.employee;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * @author xh
 * @date 2025-04-08
 * @Description:
 */
@Data
public class EmployeeContent {

    @SerializedName("Station")
    private String station;

    @SerializedName("Contents")
    private List<Content> contents;
}
