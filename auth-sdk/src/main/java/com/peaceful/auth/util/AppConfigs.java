package com.peaceful.auth.util;


/**
 * Created by wangjun on 14-8-26.
 */
public interface AppConfigs {

    Double getDouble(String s);

    Double getDouble(String s, Double aDouble);

    Float getFloat(String s);

    Float getFloat(String s, Float aFloat);

    Integer getInt(String s);

    Integer getInt(String s, Integer integer);

    Long getLong(String s);

    Long getLong(String s, Long aLong);

    String getString(String s);

    String getString(String s, String s1);

    java.util.Map<String, String> toMap();

    Boolean getBoolean(String s);

    Boolean getBoolean(String s, boolean s1);
}
