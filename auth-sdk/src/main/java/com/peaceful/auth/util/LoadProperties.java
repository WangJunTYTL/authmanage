package com.peaceful.auth.util;

import org.apache.commons.lang3.StringUtils;
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

    /**
     * Load properties file to {@link java.util.Properties} from class path.
     *
     * @param fileName properties file name. for example: <code>dubbo.properties</code>, <code>METE-INF/conf/foo.properties</code>
     * @param allowMultiFile if <code>false</code>, throw {@link IllegalStateException} when found multi file on the class path.
     * @param optional is optional. if <code>false</code>, log warn when properties config file not found!s
     * @return loaded {@link java.util.Properties} content. <ul>
     * <li>return empty Properties if no file found.
     * <li>merge multi properties file if found multi file
     * </ul>
     * @throws IllegalStateException not allow multi-file, but multi-file exsit on class path.
     */
    public static Properties loadProperties(String fileName, boolean allowMultiFile, boolean optional) {
        Properties properties = new Properties();
        if (fileName.startsWith("/")) {
            try {
                FileInputStream input = new FileInputStream(fileName);
                try {
                    properties.load(input);
                } finally {
                    input.close();
                }
            } catch (Throwable e) {
                logger.warn("Failed to load " + fileName + " file from " + fileName + "(ingore this file): " + e.getMessage(), e);
            }
            return properties;
        }

        List<java.net.URL> list = new ArrayList<java.net.URL>();
        try {
            Enumeration<java.net.URL> urls = getClassLoader().getResources(fileName);
            list = new ArrayList<java.net.URL>();
            while (urls.hasMoreElements()) {
                list.add(urls.nextElement());
            }
        } catch (Throwable t) {
            logger.warn("Fail to load " + fileName + " file: " + t.getMessage(), t);
        }

        if(list.size() == 0) {
            if (! optional) {
                logger.warn("No " + fileName + " found on the class path.");
            }
            return properties;
        }

        if(! allowMultiFile) {
            if (list.size() > 1) {
                String errMsg = String.format("only 1 %s file is expected, but %d dubbo.properties files found on class path: %s",
                        fileName, list.size(), list.toString());
                logger.warn(errMsg);
                // throw new IllegalStateException(errMsg); // see http://code.alibabatech.com/jira/browse/DUBBO-133
            }

            // fall back to use method getResourceAsStream
            try {
                properties.load(getClassLoader().getResourceAsStream(fileName));
            } catch (Throwable e) {
                logger.warn("Failed to load " + fileName + " file from " + fileName + "(ingore this file): " + e.getMessage(), e);
            }
            return properties;
        }

        logger.info("load " + fileName + " properties file from " + list);

        for(java.net.URL url : list) {
            try {
                Properties p = new Properties();
                InputStream input = url.openStream();
                if (input != null) {
                    try {
                        p.load(input);
                        properties.putAll(p);
                    } finally {
                        try {
                            input.close();
                        } catch (Throwable t) {}
                    }
                }
            } catch (Throwable e) {
                logger.warn("Fail to load " + fileName + " file from " + url + "(ingore this file): " + e.getMessage(), e);
            }
        }

        return properties;
    }



}
