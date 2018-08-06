package tomax.loo.lesson.cache.memcached.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-07-27 17:48
 **/
public class KdMencachedClientFactoryBean {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Resource(name="memcacheSrvUrls")
    private MemcachedSrvUrlsBean memcachedSrvUrlsBean;

    public KdMencachedClientFactoryBean() {
        super();
    }

    public String getCacheSrvUrls() {
        try {
            return memcachedSrvUrlsBean.getCacheSrvUrls();
        } catch (Exception e) {
            log.error("获取缓存服务URL错误", e);
        }
        return null;
    }
}
