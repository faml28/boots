package com.fml.config.util;

import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Created by fanml
 * Created by 2020/6/4
 */

public class ServletUtils {
    public static HttpServletRequest getRequest(){

        return getRequestAttributes().getRequest();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
    public static HttpServletResponse getResponse()
    {
        return getRequestAttributes().getResponse();
    }
    public static HttpSession getSession()
    {
        return getRequest().getSession();
    }

}
