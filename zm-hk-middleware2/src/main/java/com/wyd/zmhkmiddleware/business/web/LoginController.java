package com.wyd.zmhkmiddleware.business.web;

import com.wyd.zmhkmiddleware.business.model.local.dto.CaptchaDTO;
import com.wyd.zmhkmiddleware.business.model.local.dto.LoginDTO;
import com.wyd.zmhkmiddleware.business.model.local.query.LoginQuery;
import com.wyd.zmhkmiddleware.business.service.local.UserService;
import com.wyd.zmhkmiddleware.common.AjaxResult;
import com.wyd.zmhkmiddleware.common.AjaxResultUtil;
import com.wyd.zmhkmiddleware.common.BaseException;
import com.wyd.zmhkmiddleware.common.enums.ResultStatusCode;
import com.wyd.zmhkmiddleware.util.LoginUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Stone
 * @since 2025-05-10
 */
@RestController
@Api(value = "登录", protocols = "http/https", tags = "登录")
public class LoginController {

    @Resource
    private LoginUtils loginUtils;

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public AjaxResult<LoginDTO> login(@RequestBody @Validated LoginQuery param) throws BaseException {
        // 验证码校验
        if (!loginUtils.checkCaptcha(param.getCaptchaKey(), param.getCaptchaValue())) {
            throw new BaseException(ResultStatusCode.CAPTCHA_ERROR.getCode(), ResultStatusCode.CAPTCHA_ERROR.getName());
        }
        // 登录
        LoginDTO loginDTO = userService.login(param.getUsername(), param.getPassword());
        return AjaxResultUtil.getTrueAjaxResult(loginDTO);
    }

    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.POST)
    public AjaxResult<CaptchaDTO> getCaptcha() {
        try {
            return AjaxResultUtil.getTrueAjaxResult(loginUtils.generateCaptcha());
        } catch (Exception e) {
            return AjaxResultUtil.getFalseAjaxResult(null);
        }
    }

}
