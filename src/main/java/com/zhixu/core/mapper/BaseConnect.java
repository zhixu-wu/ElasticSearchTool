package com.zhixu.core.mapper;

import com.zhixu.core.annotation.EsDS;
import com.zhixu.core.config.ESDataSource;
import com.zhixu.core.excetion.NotFoundAnnotationException;
import com.zhixu.core.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author wuzhixu
 * @date 2023/10/10
 * 描述:
 */
public abstract class BaseConnect {
    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";

    public static final String SCROLL_INDEX = "_search/scroll";

    public static final String SCROLL_PARAM = "?scroll=";

    private RestTemplate restTemplate;

    private ESDataSource esDataSource;


    public BaseConnect(RestTemplate restTemplate, ESDataSource esDataSource) {
        this.restTemplate = restTemplate;
        this.esDataSource = esDataSource;
    }


    public EsDS getEsDS() {
        try {
            EsDS esDS = ClassUtils.getAnnotate(this.getClass(), EsDS.class);
            if (esDS == null) {
                throw new NotFoundAnnotationException("未找到EsDS注解!");
            }

            if (esDS.dsName() == null) {
                throw new NotFoundAnnotationException("esDS dsName is null");
            }

            if (esDS.index() == null) {
                throw new NotFoundAnnotationException("esDS index is null");
            }

            return esDS;
        } catch (Exception e) {
            throw new NotFoundAnnotationException("未找到EsDS注解!", e);
        }
    }


    public String getUrl() {
        if (getEsDS().index() == null || "".equals(getEsDS().index())) {
            throw new NotFoundAnnotationException("index未空!");
        }
        return esDataSource.getUrl(getEsDS().dsName(), getEsDS().index());
    }

    public String getBaseUrl() {
        return esDataSource.getBaseUrl(getEsDS().dsName());
    }

    public String getHost() {
        return esDataSource.getHost(getEsDS().dsName());
    }


    public String authorization() {
        try {
            return esDataSource.getAuthorization(getEsDS().dsName());
        } catch (Exception e) {
            throw new RuntimeException("获取authorization错误");
        }
    }


    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public ESDataSource getEsDataSource() {
        return esDataSource;
    }


    public boolean printLog() {
        return esDataSource.printLog();
    }

}
