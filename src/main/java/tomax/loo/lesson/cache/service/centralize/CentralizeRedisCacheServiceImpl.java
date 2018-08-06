package tomax.loo.lesson.cache.service.centralize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomax.loo.lesson.redis.service.RedisService;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-07-27 17:39
 **/
public class CentralizeRedisCacheServiceImpl extends AbstractCentralizeCacheServiceImpl {

    private static final long serialVersionUID = 2197687068492763432L;
    private static Logger logger = LoggerFactory.getLogger(CentralizeRedisCacheServiceImpl.class);
    @Resource(name = "redisService")
    private RedisService redisService;

    @Override
    public <T extends Serializable> T get(String cacheName, String key) {
        try {
            return redisService.getValue(getKey(cacheName, key));
        } catch (Exception e) {
            logger.error("redis获取缓存错误cacheName={}, key={}", cacheName, key, e);
        }
        return null;
    }

    @Override
    public <T extends Serializable> void put(String cacheName, String key, T obj) {
        // 默认10分钟过期
        put(cacheName, 60 * 10, key, obj);
    }

    @Override
    public <T extends Serializable> void put(String cacheName, int exp, String key, T obj) {
        try {
            redisService.setValue(getKey(cacheName, key), exp, obj);
        } catch (Exception e) {
            logger.error("redis添加缓存错误cacheName={}, key={}", cacheName, key, e);
        }
    }

    @Override
    public <T extends Serializable> boolean add(String cacheName, int exp, String key, T obj) {
        try {
            return redisService.setValueNEX(getKey(cacheName, key), exp, obj);
        } catch (Exception e) {
            logger.error("redis添加缓存错误(set if not exists) cacheName={}, key={}", cacheName, key, e);
        }
        return false;
    }

    @Override
    public boolean remove(String cacheName, String key) {
        try {
            return redisService.remove(get(cacheName, key));
        } catch (Exception e) {
            logger.error("redis删除缓存错误cacheName={}, key={}", cacheName, key, e);
        }
        return false;
    }

    @Override
    public Long increment(String cacheName, String key) {
        try {
            return redisService.increment(getKey(cacheName, key));
        } catch (Exception e) {
            logger.error("redis自增cacheName={}, key={}", cacheName, key, e);
        }
        return -1L;
    }

    @Override
    public Long increment(String cacheName, String key, long number) {
        try {
            return redisService.increment(getKey(cacheName, key), number);
        } catch (Exception e) {
            logger.error("redis自增cacheName={}, key={}", cacheName, key, e);
        }
        return null;
    }

    @Override
    public Long decrement(String cacheName, String key) {
        try {
            return redisService.decrement(getKey(cacheName, key));
        } catch (Exception e) {
            logger.error("redis自减异常cacheName={}, key={}", cacheName, key, e);
        }
        return -1L;
    }

    @Override
    public Long decrement(String cacheName, String key, long number) {
        try {
            return redisService.decrement(getKey(cacheName, key), number);
        } catch (Exception e) {
            logger.error("redis自减异常cacheName={}, key={}", cacheName, key, e);
        }
        return -1L;
    }

    @Override
    public Boolean isDecrement(String cacheName, String key) {
        try {
            return redisService.isDecrement(getKey(cacheName, key));
        } catch (Exception e) {
            logger.error("redis删除缓存异常cacheName={}, key={}", cacheName, key, e);
        }
        return false;
    }

    @Override
    public Long getLong(String cacheName, String key) {
        try {
            return redisService.getLong(getKey(cacheName, key));
        } catch (Exception e) {
            logger.error("redis获取缓存异常cacheName={}, key={}", cacheName, key, e);
        }
        return null;
    }

    @Override
    public boolean setExpire(String cacheName, String key, long number) {
        try {
            return redisService.setExpire(getKey(cacheName, key), number);
        } catch (Exception e) {
            logger.error("redis 设置过期时间异常 cacheName={}, key={}", cacheName, key, e);
        }
        return false;
    }

    @Override
    public Long getTtl(String cacheName, String key) {
        try {
            return redisService.getTimeToLive(getKey(cacheName, key));
        } catch (Exception e) {
            logger.error("redis获取异常cacheName={}, key={}剩余时间", cacheName, key, e);
        }
        return null;
    }
}
