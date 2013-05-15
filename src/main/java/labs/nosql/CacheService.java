package labs.nosql;

import labs.repositories.redis.support.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: jiaruizhi
 * Date: 13-4-25
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */

/**
 * 提供缓存服务
 */
public class CacheService {

    private CacheService() {
    }

    static {
        cacheClient = new JedisCacheClient();
    }

    public static CacheService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new CacheService();
            }
        }
        return instance;
    }

    public RedisCommands getServie() {
        return cacheClient.getJedis();
    }

    private int defaultCacheExpir = 30 * 24 * 60 * 60;
    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
    private static final JedisCacheClient cacheClient;
    // 使用单例
    private static CacheService instance;
    private static Object lock = new Object();
}
