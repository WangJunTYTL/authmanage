package com.peaceful.config.api;


/**
 * Created by wangjun on 14-8-26.
 */
public interface MyAppConfigs {

    java.lang.Double getDouble(java.lang.String s);

    java.lang.Double getDouble(java.lang.String s, java.lang.Double aDouble);

    java.lang.Float getFloat(java.lang.String s);

    java.lang.Float getFloat(java.lang.String s, java.lang.Float aFloat);

    java.lang.Integer getInt(java.lang.String s);

    java.lang.Integer getInt(java.lang.String s, java.lang.Integer integer);

    java.lang.Long getLong(java.lang.String s);

    java.lang.Long getLong(java.lang.String s, java.lang.Long aLong);

    java.lang.String getString(java.lang.String s);

    java.lang.String getString(java.lang.String s, java.lang.String s1);

    java.util.Map<java.lang.String, java.lang.String> toMap();
}
