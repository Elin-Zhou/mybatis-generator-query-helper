package com.elin4it.mybatis.queryhelper.interceptor;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.elin4it.mybatis.queryhelper.QueryHelper;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

/**
 * 查询拦截器
 *
 * @author ZhouFeng zhoufeng@duiba.com.cn
 * @version $Id: QueryInterceptor.java , v 0.1 2017/3/7 下午4:51 ZhouFeng Exp $
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                                                                           RowBounds.class, ResultHandler.class }) })
public class QueryInterceptor implements Interceptor {

    private static final String              PK_KEY    = "pk";

    private static final String              QUERY_SET = "querySet";

    private static final Map<Class, BeanMap> PK_CACHE  = new HashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        QueryHelper qh = QueryHelper.getQuery();

        if (qh != null) {

            Object object = invocation.getArgs()[1];

            BeanGenerator generator = new BeanGenerator();
            Object temp;
            BeanMap beanMap;
            if (Modifier.isFinal(object.getClass().getModifiers())) {
                // 如果参数类型有final修饰，则无法创建子类,需要用把参数进行包装

                // 使用cglib在参数中主键对应类型的属性
                generator.setSuperclass(PrimaryKey.class);
                generator.addProperty(PK_KEY, object.getClass());
                temp = generator.create();
                beanMap = BeanMap.create(temp);
                beanMap.put(PK_KEY, object);
                beanMap.getBean();

            } else {

                // 使用cglib在参数中加入QueryHelper属性
                generator.setSuperclass(object.getClass());
                generator.addProperty(QUERY_SET, Map.class);
                temp = generator.create();
                beanMap = BeanMap.create(temp);
            }
            beanMap.put(QUERY_SET, qh.getQuerySet());
            invocation.getArgs()[1] = temp;
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
