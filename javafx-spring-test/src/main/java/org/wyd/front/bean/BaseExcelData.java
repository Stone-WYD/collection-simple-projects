package org.wyd.front.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xh
 * @date 2025-02-07
 * @Description:
 */
@Getter
@Setter
@EqualsAndHashCode
public class BaseExcelData {
    private String id;
    private String name;
    private String oriLocation;
    private String targetLocation;
}
