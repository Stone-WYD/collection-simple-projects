package com.wyd.zmhkmiddleware.business.service.client;

import com.wyd.zmhkmiddleware.business.web.model.test.AjaxResult;
import com.wyd.zmhkmiddleware.business.web.model.test.Query;
import com.wyd.zmhkmiddleware.business.web.model.test.UserEntity;
import com.wyd.zmhkmiddleware.business.web.model.zm.ZmCommonResult;
import com.wyd.zmhkmiddleware.business.web.model.zm.user.UserInfoBody;
import com.wyd.zmhkmiddleware.config.FeignGsonConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xh
 * @date 2025-01-05
 * @Description: 服务端来自于 arthas-test，但实际使用时不依赖服务端，这里仅是学习测试功能，实际使用时只需参考当前项目内容即可。
 */
@FeignClient(name = "arthas-test", url = "${test.feign.url}", configuration = FeignGsonConfig.class)
public interface FeignTestClient {

    @PostMapping(value = "/arthas/leak/feign2", consumes = MediaType.APPLICATION_JSON_VALUE)
    AjaxResult<UserEntity> feignTest(@RequestBody Query<UserEntity> query);

    @PostMapping("/arthas/leak/feign4")
    ZmCommonResult<UserInfoBody> feignTest4();
}
