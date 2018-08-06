package tomax.loo.lesson.cache.service.local;

/**
 * @program: base-core
 * @description: 通用缓存服务，只需要注入cache bean 配置，配置不同的bean 名称， 使用@Resource("cacheBeanId")注入
 * @author: Tomax
 * @create: 2018-07-27 17:36
 **/
public interface LocalCacheService {
    /**
     * 添加数据到缓存
     * @param key
     * @param value
     */
    void put(String key, Object value);

    /**
     * 删除指定key的缓存数据
     * @param key
     * @return
     */
    boolean remove(String key);

    /**
     * 获取指定key的缓存数据
     * @param key
     * @param <T>
     * @return
     */
    <T> T get(String key);
}
