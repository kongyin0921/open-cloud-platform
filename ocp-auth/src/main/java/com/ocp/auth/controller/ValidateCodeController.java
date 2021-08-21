package com.ocp.auth.controller;

import com.ocp.auth.service.IValidateCodeService;
import com.ocp.common.bean.Result;
import com.ocp.common.constant.EndpointConstant;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码
 * @author kong
 * @date 2021/08/13 22:34
 * blog: http://blog.kongyin.ltd
 */
@Api("验证码")
@Controller
public class ValidateCodeController {
    /**
     * 默认生成图形验证码宽度
     */
    private static final int DEFAULT_IMAGE_WIDTH = 100;

    /**
     * 默认生成图像验证码高度
     */
    private static final int  DEFAULT_IMAGE_HEIGHT = 35;

    /**
     * 默认生成图形验证码长度
     */
    private static final int  DEFAULT_IMAGE_LENGTH = 4;

    @Autowired
    private IValidateCodeService validateCodeService;

    /**
     * 创建验证码
     * @param deviceId 设备编号
     * @param response 响应
     * @throws IOException
     */
    @ApiOperation("创建验证码")
    @GetMapping(EndpointConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{deviceId}")
    public void createCode(@PathVariable String deviceId, HttpServletResponse response) throws IOException {
        Assert.notNull(deviceId, "设备编号[deviceId]不能为空");
        // 设置请求头为输出图片类型
        CaptchaUtil.setHeader(response);
        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(
                DEFAULT_IMAGE_WIDTH,
                DEFAULT_IMAGE_HEIGHT,
                DEFAULT_IMAGE_LENGTH);
        // 设置类型：字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        // 保存验证码
        validateCodeService.saveImageCode(deviceId, gifCaptcha.text().toLowerCase());
        // 输出图片流
        gifCaptcha.out(response.getOutputStream());
    }

    /**
     *
     * 手机验证码
     * @param mobile
     * @return
     */
    @ApiOperation("手机验证码")
    @GetMapping(EndpointConstant.MOBILE_VALIDATE_CODE_URL_PREFIX + "{/mobile}")
    @ResponseBody
    public ResponseEntity<Result<Object>> createCode(@PathVariable String mobile) {
        Assert.notNull(mobile, "手机号[mobile]不能为空");
        validateCodeService.sendSmsCode(mobile);
        return Result.success().response();
    }
}
