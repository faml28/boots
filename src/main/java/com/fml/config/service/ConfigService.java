package com.fml.config.service;

import com.fml.service.ISysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by fanml
 * Created by 2020/6/5
 */
@Service("config")
public class ConfigService {
    @Resource
    private ISysConfigService configService;

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
    public String getKey(String configKey){
        return configService.selectConfigByKey(configKey);
    }


}
