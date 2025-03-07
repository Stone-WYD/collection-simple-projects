package com.wyd.zmhkmiddleware.business.web.model.test;

import lombok.Data;

/**
 * @author xh
 * @date 2025-03-04
 * @Description:
 */
@Data
public class Query<T> {

    private T query;

    private int pageNum;

    private int pageSize;

}
