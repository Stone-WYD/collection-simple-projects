package com.wyd.zmhkmiddleware.business.model.hk.result;

import lombok.Data;

import java.util.List;

/**
 * @author Stone
 * @since 2025-06-18
 */
@Data
public class HaiKangDataResult {

    private List<HaiKangDataResultContent> successes;

    private List<HaiKangDataResultContent> failures;

}
