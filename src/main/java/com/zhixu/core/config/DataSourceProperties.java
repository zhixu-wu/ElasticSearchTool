package com.zhixu.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */

@ConfigurationProperties(prefix = "spring.datasource.es-tool")
@Getter
@Setter
public class DataSourceProperties {

    private String primary = "master";

    private boolean printDSL = false;

    private Map<String, DataSourceProperty> datasource;


    public DataSourceProperties() {
    }

}
