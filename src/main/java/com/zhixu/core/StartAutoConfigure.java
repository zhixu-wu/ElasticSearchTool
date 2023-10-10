package com.zhixu.core;

import com.zhixu.core.config.DataSourceProperties;
import com.zhixu.core.config.ESDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class StartAutoConfigure {

    @Autowired
    private DataSourceProperties dataSourceProperties;


    @Bean
    @ConditionalOnMissingBean
    ESDataSource esDataSource() {
        ESDataSource esDataSource = new ESDataSource(dataSourceProperties);
        esDataSource.checkLoad();
        return esDataSource;
    }

}