package tomax.loo.lesson.cache.service.centralize;

import java.io.Serializable;

/**
 * @program: base-core
 * @description: 不被调用链跟踪的缓存接口
 * @author: Tomax
 * @create: 2018-07-27 17:41
 **/
public interface UntrackingCentralizeCacheService {
    int EXP_MIN_5 = 60 * 5;
    int EXP_MIN_10 = 60 * 10;
    int EXP_MIN_30 = 60 * 30;
    int EXP_HOUR_1 = 60 * 60;
    int EXP_HOUR_3 = 60 * 60 * 3;
    int EXP_HOUR_5 = 60 * 60 * 6;
    int EXP_HOUR_12 = 60 * 60 * 12;
    int EXP_DAY_1 = 60 * 60 * 24;
    int EXP_DAY_3 = 60 * 60 * 24 * 3;
    int EXP_DAY_7 = 60 * 60 * 24 * 7;
    int EXP_DAY_15 = 60 * 60 * 24 * 15;
    int EXP_DAY_30 = 60 * 60 * 24 * 30;

    /**
     * 获取缓存对象
     * @param cacheName
     * @param key
     * @param <T>
     * @return
     */
    <T extends Serializable> T get(String cacheName, String key);

    /**
     * 缓存对象，默认10分钟过期
     * @param cacheName
     * @param key
     * @param obj
     * @param <T>
     */
    <T extends Serializable> void put(String cacheName, String key, T obj);

    /**
     * 缓存对象，exp 为过期时间，单位秒
     * @param cacheName
     * @param exp
     * @param key
     * @param obj
     * @param <T>
     */
    <T extends Serializable> void put(String cacheName, int exp, String key, T obj);

    /**
     * 尝试向缓存中添加对象如果该对象在缓存中不存在(使用默认的transcoder), exp 为过期时间，单位秒
     * @param cacheName
     * @param exp
     * @param key
     * @param obj
     * @param <T>
     * @return
     */
    <T extends Serializable> boolean add(String cacheName, int exp, String key, T obj);

    /**
     * 删除缓存
     * @param cacheName
     * @param key
     * @return
     */
    boolean remove(String cacheName, String key);

    /**
     * 自增缓存
     * @param cacheName
     * @param key
     * @return
     */
    Long increment(String cacheName, String key);

    /**
     * 自增缓存num
     * @param cacheName
     * @param key
     * @param number
     * @return
     */
    Long increment(String cacheName, String key, long number);

    /**
     * 自减缓存
     * @param cacheName
     * @param key
     * @return
     */
    Long decrement(String cacheName, String key);

    /**
     * 自减缓存 num
     * @param cacheName
     * @param key
     * @param number
     * @return
     */
    Long decrement(String cacheName, String key, long number);

    /**
     * 判断自减缓存是否成功
     * @param cacheName
     * @param key
     * @return
     */
    Boolean isDecrement(String cacheName, String key);

    /**
     * 获取自增加的值
     * @param cacheName
     * @param key
     * @return
     */
    Long getLong(String cacheName, String key);

    /**
     * 缓存对象，exp 为过期时间， 单位秒
     * @param cacheName
     * @param key
     * @param number
     * @return
     */
    boolean setExpire(String cacheName, String key, long number);

}
