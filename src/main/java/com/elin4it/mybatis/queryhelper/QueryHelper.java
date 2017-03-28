package com.elin4it.mybatis.queryhelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhouFeng zhoufeng@duiba.com.cn
 * @version $Id: QueryHelper.java , v 0.1 2017/3/7 下午3:18 ZhouFeng Exp $
 */
public class QueryHelper {

    private static final ThreadLocal<QueryHelper> LOCAL_QUERY = new ThreadLocal<>();

    private Map<String, Boolean>                  querySet;

    protected void set(String field) {
        if (querySet == null) {
            querySet = new HashMap<>();
        }
        querySet.put(field, true);
    }

    public static void query(QueryHelper qh) {
        LOCAL_QUERY.set(qh);
    }

    public static QueryHelper getQuery() {
        return LOCAL_QUERY.get();
    }

    public Map<String, Boolean> getQuerySet() {
        return querySet;
    }

    public void setQuerySet(Map<String, Boolean> querySet) {
        this.querySet = querySet;
    }

}
