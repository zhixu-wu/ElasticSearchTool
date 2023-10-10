package com.zhixu.core.config;

import com.zhixu.core.excetion.LoadDataSourceException;
import lombok.NonNull;

import java.util.Base64;
import java.util.Map;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
public class ESDataSource {

    private DataSourceProperties dataSourceProperties;

    public ESDataSource(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    public String getAuthorization(@NonNull String dsName) {
        checkDsName(dsName);
        DataSourceProperty ds = dataSourceProperties.getDatasource().get(dsName);
        return "Basic " + Base64.getEncoder().encodeToString((ds.getUsername() + ":" + (ds.getPassword())).getBytes());
    }


    public String getUrl(@NonNull String dsName, @NonNull String index) {
        checkDsName(dsName);
        DataSourceProperty ds = dataSourceProperties.getDatasource().get(dsName);
        return "http://" + ds.getHost() + ":" + ds.getPort() + "/" + index + "/_search";
    }

    public String getBaseUrl(@NonNull String dsName) {
        checkDsName(dsName);
        DataSourceProperty ds = dataSourceProperties.getDatasource().get(dsName);
        return "http://" + ds.getHost() + ":" + ds.getPort() + "/_search";
    }


    public String getHost(@NonNull String dsName) {
        checkDsName(dsName);
        DataSourceProperty ds = dataSourceProperties.getDatasource().get(dsName);
        return "http://" + ds.getHost() + ":" + ds.getPort() + "/";
    }

    private void checkDsName(String dsName) throws RuntimeException {
        if (!dataSourceProperties.getDatasource().containsKey(dsName)) {
            throw new LoadDataSourceException(String.format("未查询到[%s]datasource", dsName));
        }
    }


    public void checkLoad() {
        Map<String, DataSourceProperty> map = dataSourceProperties.getDatasource();

        if (map != null) {
            if (map.isEmpty()) {
                throw new LoadDataSourceException("elasticSearch 配置空加载");
            }

            for (String key : map.keySet()) {
                DataSourceProperty ds = map.get(key);

                if (
                        ds.getHost() == null ||
                                ds.getPort() == null ||
                                ds.getUsername() == null ||
                                ds.getPassword() == null
                ) {
                    throw new LoadDataSourceException("elasticSearch 空参数!");
                }

            }

        } else {
            throw new LoadDataSourceException("elasticSearch 配置未正确读取!");
        }
    }

    public boolean printLog() {
        return dataSourceProperties.getPrintDSL();
    }
}
