package com.elin4it.mybatis.queryhelper.interceptor;

import java.util.Map;

/**
 * 主键包装类
 *
 * @author ZhouFeng zhoufeng@duiba.com.cn
 * @version $Id: PrimaryKey.java , v 0.1 2017/3/24 下午4:36 ZhouFeng Exp $
 */
public class PrimaryKey {

    private Map<String, Boolean> querySet;

    public Map<String, Boolean> getQuerySet() {
        return querySet;
    }

    public void setQuerySet(Map<String, Boolean> querySet) {
        this.querySet = querySet;
    }
}
