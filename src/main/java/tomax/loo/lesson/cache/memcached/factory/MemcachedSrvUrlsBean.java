package tomax.loo.lesson.cache.memcached.factory;

/**
 * @program: base-core
 * @description: memcache 服务连接bean装载类
 * @author: Tomax
 * @create: 2018-07-27 19:47
 **/
public class MemcachedSrvUrlsBean {

    private String cacheSrvUrls;

    public String getCacheSrvUrls() {
        return cacheSrvUrls;
    }

    public void setCacheSrvUrls(String cacheSrvUrls) {
        this.cacheSrvUrls = cacheSrvUrls;
    }
}
