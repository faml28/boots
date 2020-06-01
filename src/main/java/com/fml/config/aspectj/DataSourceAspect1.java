package com.fml.config.aspectj;

import com.fml.config.druid.DynamicDataSourceContextHolder;
import com.fml.config.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Aspect
@Order(1)
@Component
public class DataSourceAspect1 {
    //定义切点
    @Pointcut("@annotation(com.fml.config.annotation.DataSource)"+"|| @within(com.fml.config.annotation.DataSource)")
    public void dsPointCut()
    {

    }

    //前置处理
    @Before("dsPointCut()")
    public void bth(){
        System.out.println("开始切换");
    }
    @Around("dsPointCut()")
    public Object setData(ProceedingJoinPoint point) throws Throwable {
        DataSource dataSource=getDataSource(point);
        if(dataSource != null){
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }
         try {
             return point.proceed();
         }
        finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }


    /*
     * 获取需要切换的数据源
     */
    private DataSource getDataSource(ProceedingJoinPoint point) {
        MethodSignature methodSignature= (MethodSignature) point.getSignature();
        DataSource dataSource = AnnotationUtils.findAnnotation(methodSignature.getMethod(), DataSource.class);
        if(Objects.nonNull(dataSource)){
            return dataSource;
        }
        return AnnotationUtils.findAnnotation(methodSignature.getMethod(),DataSource.class);
    }
    @After("dsPointCut()")
    public void af(){
        System.out.println("aop");
    }
}
