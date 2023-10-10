package com.zhixu.core.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EsResult {
    /**
     * 游标查询 _scroll_id
     */
    private String ScrollId;

    /**x
     * 查询消耗时长,单位毫秒
     */
    private long queryMillisecond;

    /**
     * 总条数
     */
    private Long total;

    private Boolean next = new Boolean(false);

    /**
     * hist/_source
     */
    private List<JSONObject> sourceList;

    private JSONObject aggregations;

    public static EsResult of(){
        EsResult e = new EsResult();
        e.setTotal(0L);
        e.setSourceList(new ArrayList<>());
        return e;
    }

    public void countPut(List<JSONObject> list){
        total+=list.size();
        sourceList.addAll(list);
    }

    public void setNext(boolean isNext){
        this.next = new Boolean(isNext);
    }

}
