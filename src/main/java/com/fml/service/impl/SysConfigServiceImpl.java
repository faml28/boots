package com.fml.service.impl;

import com.fml.config.util.StringUtils;
import com.fml.entity.SysConfig;
import com.fml.mapper.SysConfigMapper;
import com.fml.service.ISysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by fanml
 * Created by 2020/6/5
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {
    @Resource
    SysConfigMapper configMapper;
    @Override
    public String selectConfigByKey(String configKey) {
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        return StringUtils.isNotNull(retConfig) ? retConfig.getConfigValue() : "";

    }
}
