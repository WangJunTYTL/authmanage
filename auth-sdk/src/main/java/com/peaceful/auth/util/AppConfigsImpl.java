package com.peaceful.auth.util;


import java.util.Map;

/**
 * 读取properties配置文件工具类,
 * Created by wangjun on 14-8-18.
 *
 */
public class AppConfigsImpl extends AbstractConfiguration {
    private Map configMap = null;

    private AppConfigsImpl() {
    }

    public static AppConfigs getMyAppConfigs(String propertiesPath) {
        try {
            AppConfigsImpl appConfigsUtils = new AppConfigsImpl();
            appConfigsUtils.configMap = LoadProperties.loadPropertiesFromClassPath(propertiesPath);
            return appConfigsUtils;
        } catch (LoadPropertiesException e) {
            throw new LoadPropertiesException("未成功载入配置文件".concat(propertiesPath), e);
        }
    }

    @Override
    protected Map<String, String> getConfigData() {
        return configMap;
    }


    @Override
    protected String getStringFromStore(String key) {
        return getConfigData().get(key);
    }


}
