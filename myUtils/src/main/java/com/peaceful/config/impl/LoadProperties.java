package com.peaceful.config.impl;

import com.peaceful.config.exception.LoadPropertiesException;
import com.peaceful.util.StringUtils;
import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by wangjun on 14-4-21.
 */
public class LoadProperties {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(LoadProperties.class);

    public static Map<String, String> loadPropertiesFromClassPath(String path) throws LoadPropertiesException {
        if (StringUtils.isEmpty(path))
            return Collections.EMPTY_MAP;
        Map<String, String> conf = new HashMap<String, String>();
        InputStream is = null;
        if (conf.size() == 0) {
            try {
                is = getClassLoader().getResourceAsStream(
                        path);
                Properties p = new Properties();
                p.load(is);
                Enumeration en = p.propertyNames();
                while (en.hasMoreElements()) {
                    String key = (String) en.nextElement();
                    conf.put(key, p.getProperty(key));
                }
                logger.debug("success load properties from classPath:{}", path);
                logger.info("classPath:{},configs size:{}", path, conf.size());
                return conf;
            } catch (Exception e) {
                throw new LoadPropertiesException("load " + path + " from classPath error");
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        throw new LoadPropertiesException("close stream error", e.getCause());
                    }
                }
            }
        }
        return Collections.EMPTY_MAP;
    }

    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader == null) {
            classLoader = LoadProperties.class.getClassLoader();
        }
        return classLoader;
    }


}
