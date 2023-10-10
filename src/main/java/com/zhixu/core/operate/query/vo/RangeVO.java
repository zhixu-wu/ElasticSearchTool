package com.zhixu.core.operate.query.vo;

import com.zhixu.core.enums.Range;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
public class RangeVO {

    private Map<Range, String> value;


    public RangeVO() {
        this.value = new HashMap<>();
    }

    public static RangeVO of() {
        return new RangeVO();
    }

    public void putRange(Range range, String value) {
        this.value.put(range,value);
    }

    public Map<Range, String> getRange() {
        return this.value;
    }

}
