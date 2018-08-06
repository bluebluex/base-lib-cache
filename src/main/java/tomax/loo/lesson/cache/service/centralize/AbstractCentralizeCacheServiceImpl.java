package tomax.loo.lesson.cache.service.centralize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-07-27 17:38
 **/
public abstract class AbstractCentralizeCacheServiceImpl implements CentralizeCacheService {

    private static final long serialVersionUID = -908229181587697933L;

    private static Logger log = LoggerFactory.getLogger(AbstractCentralizeCacheServiceImpl.class);
    private final String KEY_MODEL = "%s_%s_%s";
    private static String ACTIVE_ENVIRONMENT = null;
    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    protected String getKey(String cacheName, String key) {
        return String.format(KEY_MODEL, cacheName, key, getActiveEnvironment());
    }

    private static String getActiveEnvironment() {
        if (ACTIVE_ENVIRONMENT == null) {
            System.getProperty(SPRING_PROFILES_ACTIVE);
            log.info("================获取系统环境ACTIVE_ENVIRONMENT={}=========", ACTIVE_ENVIRONMENT);
        }
        return ACTIVE_ENVIRONMENT;
    }
}
