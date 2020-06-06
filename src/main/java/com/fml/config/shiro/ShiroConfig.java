package com.fml.config.shiro;


import com.fml.config.filter.CaptchaValidateFilter;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
/**
 * Created by fanml
 * Created by 2020/6/3
 */
@Configuration
public class ShiroConfig {

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

    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm= new UserRealm();
        return userRealm;
    }
    @Bean
    public SecurityManager securityManager(UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return  defaultWebSecurityManager;
    }
    @Bean
    public CaptchaValidateFilter captchaValidateFilter() {
        CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
        captchaValidateFilter.setCaptchaEnabled(captchaEnabled);
        captchaValidateFilter.setCaptchaType(captchaType);
        captchaValidateFilter.setCaptchaType("char");
        return captchaValidateFilter;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");

        // 权限认证失败，跳转其他页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
        //权限连接约束配置，即过滤的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //d对静态页面设置匿名访问
        filterChainDefinitionMap.put("favucib,ici**","anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");

        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/libs/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
        shiroFilterFactoryBean.setFilters(filterMap);
        // 所有请求需要认证
        filterChainDefinitionMap.put("/**","user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }
}
