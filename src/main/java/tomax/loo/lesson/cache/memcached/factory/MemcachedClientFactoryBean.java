package tomax.loo.lesson.cache.memcached.factory;

import net.spy.memcached.MemcachedClient;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-07-27 17:33
 **/
public interface MemcachedClientFactoryBean {

    MemcachedClient getMemCachedClient();

}
