package com.fml.controller;

import com.fml.config.ajaxResult.AjaxResult;
import com.fml.config.util.ServletUtils;
import com.fml.config.util.StringUtils;
import lombok.extern.slf4j.Slf4j;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Created by fanml
 * Created by 2020/6/4
 */
@Slf4j
public class BaseController {
    /**
     * 获取request
     */
    public HttpServletRequest getRequest(){
        return ServletUtils.getRequest();
    }
    /**
     * response
     */
    public HttpServletResponse  getResponse(){
        return ServletUtils.getResponse();
    }
    /**
     * session
     */
    public HttpSession getSession(){
        return getRequest().getSession();
    }
    /**
     * 响应返回结果
     */
    protected AjaxResult toAjax(int rows){
        return rows>0 ? success():error();
    }
    /**
     * 成功
     */
    public AjaxResult success(){
        return AjaxResult.success();
    }
    /**
     * 返回失败信息
     *
     */
    public AjaxResult error(){
        return AjaxResult.error();

    }

    /**
     * 返回成功信息
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败信息
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }
    /**
     * &#x8FD4;&#x56DE;&#x9519;&#x8BEF;&#x7801;&#x6D88;&#x606F;
     */
    public AjaxResult error(AjaxResult.Type type, String message){
        return new AjaxResult(type, message);
    }
    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

}
