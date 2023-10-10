package com.zhixu.core.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.zhixu.core.config.ESDataSource;
import com.zhixu.core.excetion.JsonToEntityException;
import com.zhixu.core.operate.BaseEntity;
import com.zhixu.core.operate.DSL;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author wuzhixu
 * @date 2023/10/10
 * 描述:
 */
@Slf4j
public abstract class BaseImpl<T> extends BaseConnect implements BaseEntity<T> {

    public BaseImpl(RestTemplate restTemplate, ESDataSource esDataSource) {
        super(restTemplate, esDataSource);
    }



    protected String delIndex(@NonNull String indexName) {

        String url = getHost() + indexName;
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Content-Type", CONTENT_TYPE_JSON);
            requestHeaders.add("Authorization", authorization());
            HttpEntity<String> httpEntity = new HttpEntity<>(requestHeaders);
            ResponseEntity<String> responseEntity = getRestTemplate().exchange(url, HttpMethod.DELETE, httpEntity, String.class);

            return responseEntity.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("delete index error");
        } finally {
//            log.info(String.format("StatusCode[%d], 耗时:[%d]毫秒, 请求地址:[%s]", statusCode, System.currentTimeMillis() - time, url));
        }
    }






    /**
     * 将es查询的json格式数据，转换成实体对象
     *
     * @param jsonStr
     * @param dsl
     * @return
     */
    protected T resultJsonToObj(@NonNull String jsonStr, DSL<T> dsl) {
        try {
            Map<String, String> fields = getFields(dsl.getEntityClass());
            JSONObject json = JSONObject.parseObject(jsonStr);

            JSONObject objJson = new JSONObject();
            for (String field : fields.keySet()) {
                String jsonKey = fields.get(field);
                Object value = null;

                if(json.containsKey(jsonKey)) {
                    value = json.get(jsonKey);
                }else if(json.containsKey(field)) {
                    value = json.get(field);
                }

                objJson.put(field, value);
            }

            return JSONObject.parseObject(objJson.toJSONString(), dsl.getEntityClass());
        } catch (Exception e) {
            throw new JsonToEntityException(String.format("json转实体对象错误, class[%s], json[%s]", dsl.getEntityClass().getName(), jsonStr), e);
        }
    }

}
