package com.fml.controller;


import com.fml.config.ajaxResult.AjaxResult;
import com.fml.config.util.ServletUtils;
import com.fml.config.util.StringUtils;
import com.google.code.kaptcha.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class SysLoginController extends BaseController {

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
       /* // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }*/

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username,String password,boolean reme,String validateCode)
    {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, reme);
        Subject subject= SecurityUtils.getSubject();
        String reValicatp= (String) getRequest().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        try {
            subject.login(token);
            if(!validateCode.equals(reValicatp))
            {
                return AjaxResult.error("验证码错误");
            }
            return success();
        } catch (AuthenticationException e) {

            if (StringUtils.isEmpty(e.getMessage())) {
                e.getMessage();
            }
            return AjaxResult.error(e.getMessage());
        }
    }
}
