package tomax.loo.lesson.cache.memcached.factory;

import net.spy.memcached.*;
import net.spy.memcached.transcoders.SerializingTranscoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import tomax.loo.lesson.cache.memcached.transcoder.KdSpyMemSerializingTranscoder;

import java.io.IOException;

/**
 * @program: base-core
 * @description: memcached 缓存工程基础服务
 * @author: Tomax
 * @create: 2018-07-27 17:47
 **/
public abstract class AbstractMemcachedClientFactoryBean implements MemcachedClientFactoryBean, InitializingBean, DisposableBean {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
    private MemcachedClient memcachedClient = null;
    private String cacheSrvUrls;

    public AbstractMemcachedClientFactoryBean() {
        super();
        initCacheParams();
    }

    /**
     * 初始化参数
     */
    protected void initCacheParams() {
        connectionFactoryBuilder.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
        SerializingTranscoder transcoder = new KdSpyMemSerializingTranscoder();
        transcoder.setCompressionThreshold(1024);
        connectionFactoryBuilder.setTranscoder(transcoder);
        connectionFactoryBuilder.setOpTimeout(1000);
        connectionFactoryBuilder.setTimeoutExceptionThreshold(1998);
        connectionFactoryBuilder.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH);
        connectionFactoryBuilder.setLocatorType(ConnectionFactoryBuilder.Locator.CONSISTENT);
        connectionFactoryBuilder.setFailureMode(FailureMode.Redistribute);
        connectionFactoryBuilder.setUseNagleAlgorithm(false);
    }

    @Override
    public MemcachedClient getMemCachedClient() {
        String tpCacheSrvUrls = getCacheSrvUrls();
        if (StringUtils.isBlank(tpCacheSrvUrls)) {
            destroyMemCachedClient();
            this.cacheSrvUrls = null;
            return null;
        }
        if (this.memcachedClient == null) {
            return instanceMemCachedClient(tpCacheSrvUrls);
        } else {
            // 地址繁盛变化，重新初始化
            if (!tpCacheSrvUrls.equals(this.cacheSrvUrls)) {
                destroyMemCachedClient();
            }
            return instanceMemCachedClient(tpCacheSrvUrls);
        }
    }

    private MemcachedClient instanceMemCachedClient(String tpCacheSrvUrls) {
        if (this.memcachedClient == null) {
            synchronized (connectionFactoryBuilder) {
                if (this.memcachedClient == null) {
                    try {
                        this.memcachedClient = new MemcachedClient(connectionFactoryBuilder.build(), AddrUtil.getAddresses(tpCacheSrvUrls));
                        cacheSrvUrls = tpCacheSrvUrls;
                    } catch (IOException e) {
                        log.error("构建memCachedClient失败，cacheSrvUrls={}", tpCacheSrvUrls, e);
                        return null;
                    }
                }
            }
        }
        return this.memcachedClient;
    }

    private void destroyMemCachedClient() {
        if (this.memcachedClient != null) {
            synchronized (connectionFactoryBuilder) {
                if (this.memcachedClient != null) {
                    this.memcachedClient.shutdown();
                    this.memcachedClient = null;
                }
            }
        }
    }

    /**
     * 获取缓存服务URL
     * @return
     */
    protected abstract String getCacheSrvUrls();

    @Override
    public void afterPropertiesSet() throws Exception {
        String tpCacheSrvUrls = getCacheSrvUrls();
        if (StringUtils.isNotBlank(tpCacheSrvUrls)) {
            this.memcachedClient = instanceMemCachedClient(tpCacheSrvUrls);
            if (this.memcachedClient == null) {
                log.error("服务器启动构建memCachedClient失败，cacheSrvUrls={}", tpCacheSrvUrls);
            }
        } else {
            log.error("服务器启动构建memCachedClient失败，服务器缓存地址为空");
        }
    }

    @Override
    public void destroy() throws Exception {
        if (this.memcachedClient != null) {
            this.memcachedClient.shutdown();
            this.memcachedClient = null;
        }
    }
}
