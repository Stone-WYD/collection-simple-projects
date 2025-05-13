package com.jgdsun.ba.brand.baorui.client;

import com.jgdsun.ba.brand.baorui.model.BrCommonResult;
import com.jgdsun.ba.brand.baorui.model.deviceparam.BrDeviceParam;
import com.jgdsun.ba.brand.baorui.model.deviceparam.query.BrDeviceParamQuery;
import com.jgdsun.ba.brand.baorui.model.deviceparam.query.control.BrControlQuery;
import com.jgdsun.ba.brand.baorui.model.devicetype.BrDeviceType;
import com.jgdsun.ba.brand.baorui.model.login.result.BrLoginResult;
import com.jgdsun.ba.framework.logincheck.annotation.LoginCheck;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xh
 * @date 2025-01-05
 * @Description:
 */
@FeignClient(name = "br-service", url = "${feign.baorui.url}", configuration = BrFeignConfig.class)
public interface BrFeignClient {

    @PostMapping("blade-auth/oauth/token")
    BrLoginResult login(@RequestParam("password") String password, @RequestParam("tenantId") String tenantId,
                        @RequestParam("username") String username, @RequestParam("grant_type") String grantType);


    @LoginCheck(value = "baorui")
    @GetMapping("brcon/subsystemsettings/queryAll")
    BrCommonResult<List<BrDeviceType>> queryAllsubsystemsettings();

    @LoginCheck(value = "baorui")
    @PostMapping("brcon/commondeviceinfo/queryAll")
    BrCommonResult<List<BrDeviceParam>> queryAllcommondeviceinfo(@RequestBody BrDeviceParamQuery brDeviceParamQuery);

    @LoginCheck(value = "baorui")
    @PostMapping("brcon/commondeviceinfo/sendChangeData")
    BrCommonResult<String> sendChangeData(@RequestBody BrControlQuery brControlQuery);

}
