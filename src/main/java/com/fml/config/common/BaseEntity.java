package com.fml.config.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by fanml
 * Created by 2020/6/3
 */

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    //搜索值
    private String searchValue;
    //创建者
    private String createBy;
    //创建时间
    private Date createTime;
    //更新者
    private String updateBy;
    //更新时间
    private Date updateTime;
    //备注
    private String remark;
    //请求参数
    private Map<String, Object> params;

}