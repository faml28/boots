package com.fml.config.filter;

import com.fml.config.shiro.ShiroConstants;
import com.fml.config.util.StringUtils;
import com.google.code.kaptcha.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码 过滤器类
 * Created by fanml
 * Created by 2020/6/3
 */
public class CaptchaValidateFilter extends AccessControlFilter {

    //登录地址
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;
    //未授权
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;
    //验证码开关
    @Value("${shiro.user.captchaEnabled}")
    private boolean captchaEnabled;
    //验证码类型
    @Value("${shiro.user.captchaType}")
    private String captchaType;
    /**
     * 是否开启验证码
     */
    //private boolean captchaEnabled = ;

    /**
     * 验证码类型
     */
   // private String captchaType = "math";

    public void setCaptchaEnabled(boolean captchaEnabled)
    {
        this.captchaEnabled = captchaEnabled;
    }

    public void setCaptchaType(String captchaType)
    {
        this.captchaType = captchaType;
    }
    public String getCaptchaType(){
        return this.captchaType;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        request.setAttribute(ShiroConstants.CURRENT_ENABLED, captchaEnabled);
        request.setAttribute(ShiroConstants.CURRENT_TYPE, captchaType);
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 验证码禁用 或不是表单提交 允许访问
        if (captchaEnabled == false || !"post".equals(httpServletRequest.getMethod().toLowerCase()))
        {
            return true;
        }
        return validateResponse(httpServletRequest,servletRequest.getParameter(ShiroConstants.CURRENT_VALIDATECODE));
    }
    public boolean validateResponse(HttpServletRequest request, String validateCode)
    {
        Object obj = SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String code = String.valueOf(obj != null ? obj : "");
        if (StringUtils.isEmpty(validateCode) || !validateCode.equalsIgnoreCase(code))
        {
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        servletRequest.setAttribute(ShiroConstants.CURRENT_CAPTCHA, ShiroConstants.CAPTCHA_ERROR);
        return true;
    }
}
