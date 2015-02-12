package com.peaceful.auth.Impl;

import com.peaceful.auth.api.CacheService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于 ehcache 框架实现缓存，处理从权限中心读取配置信息，然后缓存到用户服务器
 * <p/>
 * Created by WangJun on 14-4-20.
 */
public class CacheServiceImpl implements CacheService {
    private static CacheManager cacheManager;
    private static Cache cache = null;
    private static Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    private static Cache getCache() {
        try {
            if (cacheManager == null)
                cacheManager = CacheManager.create();
            cache = cacheManager.getCache("userQuery");
            if (cache == null) {
                cacheManager.addCache("userQuery");
                logger.info("CACHE: Create a CacheManager {} ", "userQuery");
                cache = cacheManager.getCache("userQuery");
            }
        } catch (Exception e) {
            //当使用热部署编译时，会引起ehcache抛出shutdown异常，该问题是在利用sbt构建时发现
            logger.error("注意：权限系统检测到你正在使用热部署方式编译,缓存系统在你热部署时自动重启，缓存的数据将会被清除");
            cacheManager = CacheManager.create();
            cache = cacheManager.getCache("userQuery");
            if (cache == null) {
                cacheManager.addCache("userQuery");
                logger.info("CACHE: Create a CacheManager {} ", "userQuery");
                cache = cacheManager.getCache("userQuery");
                return cache;
            }
        }
        return cache;
    }

    public void put(String key, Object value, Integer expire) {
        Cache cache1 = getCache();
        Element element = new Element(key, value, false, expire, expire);
        cache1.put(element);
    }

    public Object get(String key) {
        Cache cache1 = getCache();
        Element element = cache1.get(key);
        if (element != null)
            logger.debug("CACHE:{}, the hit count is {}", key, element.getHitCount());
        return element;
    }

    public void clear(String key) {
        Cache cache1 = getCache();
        cache1.remove(key);
        if (cache1.get(key) == null) {
            logger.info("CACHE:{} is clear", key);
        }
    }

    @Override
    public void clearAll() {
        Cache cache1 = getCache();
        cache1.removeAll();
    }
}
