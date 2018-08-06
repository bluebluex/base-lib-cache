package tomax.loo.lesson.cache.service.centralize;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomax.loo.lesson.cache.memcached.factory.MemcachedClientFactoryBean;

import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-07-27 17:39
 **/
public class CentralizeMenCacheServiceImpl extends AbstractCentralizeCacheServiceImpl {

    private static final long serialVersionUID = -2105410151239239003L;
    private static Logger logger = LoggerFactory.getLogger(CentralizeMenCacheServiceImpl.class);
    private MemcachedClientFactoryBean memcachedClient;

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T get(String cacheName, String key) {
        if (memcachedClient == null) {
            return null;
        }
        try {
            MemcachedClient client = memcachedClient.getMemCachedClient();
            if (client == null) {
                return null;
            }
            return (T) client.get(getKey(cacheName, key));
        } catch (Exception e) {
            logger.error("memcached get缓存错误cachedName={}, key={}", cacheName, key, e);
        }
        return null;
    }

    @Override
    public <T extends Serializable> void put(String cacheName, String key, T obj) {
        // 默认十分钟过期
        put(cacheName, 60 * 10, key, obj);
    }

    @Override
    public <T extends Serializable> void put(String cacheName, int exp, String key, T obj) {
        if (memcachedClient == null || obj == null) {
            return;
        }
        try {
            MemcachedClient client = memcachedClient.getMemCachedClient();
            if (client == null) {
                return;
            }
            client.set(getKey(cacheName, key), exp, obj);
        } catch (Exception e) {
            logger.error("memcached put缓存错误cacheName={}，exp={}, key={}", cacheName, exp, key, e);
        }
    }

    @Override
    public <T extends Serializable> boolean add(String cacheName, int exp, String key, T obj) {
        if (memcachedClient == null || obj == null) {
            return false;
        }
        try {
            MemcachedClient client = memcachedClient.getMemCachedClient();
            if (client == null) {
                return false;
            }
            OperationFuture<Boolean> operationFuture = client.add(getKey(cacheName, key), exp, obj);
            if (operationFuture != null) {
                return getBooleanValue(operationFuture);
            }
        } catch (Exception e) {
            logger.error("memcache add缓存错误cacheName={}, exp={}, key={}", cacheName, exp, key, e);
        }
        return false;
    }

    public void setMemcachedClient(MemcachedClientFactoryBean memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    private boolean getBooleanValue(Future<Boolean> operationFuture) {
        try {
            Boolean bool = operationFuture.get(SpyMemcachedConstants.DEFAULT_TIMEOUT, SpyMemcachedConstants.DEFAULT_TIMEUNIT);
            return bool.booleanValue();
        } catch (Exception e) {
            logger.error("memchached getBooleanValue", e);
            operationFuture.cancel(false);
        }
        return false;
    }

    @Override
    public boolean remove(String cacheName, String key) {
        if (memcachedClient == null) {
            return false;
        }
        try {
            MemcachedClient client = memcachedClient.getMemCachedClient();
            OperationFuture<Boolean> result = client.delete(getKey(cacheName, key));
            return result.get();
        } catch (Exception e) {
            logger.error("memcached remove魂村错误cacheName={}, key={}", cacheName, key, e);
        }
        return false;
    }

    @Override
    public Long increment(String cacheName, String key) {
        return null;
    }

    @Override
    public Long increment(String cacheName, String key, long number) {
        return null;
    }

    @Override
    public Long decrement(String cacheName, String key) {
        return null;
    }

    @Override
    public Long decrement(String cacheName, String key, long number) {
        return null;
    }

    @Override
    public Boolean isDecrement(String cacheName, String key) {
        return null;
    }

    @Override
    public Long getLong(String cacheName, String key) {
        return null;
    }

    @Override
    public boolean setExpire(String cacheName, String key, long number) {
        return false;
    }

    @Override
    public Long getTtl(String cacheName, String key) {
        return null;
    }
}
