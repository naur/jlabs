/* 
 * JedisClient.java
 * 
 * Created on 2011-12-29
 * 
 * Copyright(C) 2011, by 360buy.com.
 * 
 * Original Author: LiangWang
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log$
 */
package labs.nosql;

import labs.repositories.redis.JedisOperation;
import labs.repositories.redis.shard.ShardClientFactory;
import labs.repositories.redis.shard.ShardRedisConfig;
import labs.repositories.redis.support.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class JedisCacheClient {

    private static final Logger log = LoggerFactory.getLogger(JedisCacheClient.class);

    private static final Properties jedisProp = new Properties();

    private static ShardRedisConfig shardRedisConfig;

    private static ShardClientFactory shardClientFactory;

    private static RedisCommands jedis;


    //private static  JedisPool pool;

    static {
        InputStream inStream = JedisCacheClient.class.getResourceAsStream("/redis.properties");
        try {
            jedisProp.load(inStream);
            if (inStream != null) {
                log.debug("load redis.properties file success! " + jedisProp);
            }
            shardRedisConfig = new ShardRedisConfig();
            shardRedisConfig.setMinEvictableIdleTimeMillis(30000);
            shardRedisConfig.setHeartbeat(Long.parseLong(jedisProp.getProperty("redis.heartbeat")));
            shardRedisConfig.setMaxActive(Integer.parseInt(jedisProp.getProperty("redis.maxActive")));
            shardRedisConfig.setMaxIdle(Integer.parseInt(jedisProp.getProperty("redis.maxIdle")));
            shardRedisConfig.setMaxWait(Integer.parseInt(jedisProp.getProperty("redis.maxWait")));
            shardRedisConfig.setTimeOut(Integer.parseInt(jedisProp.getProperty("redis.timeout")));
            shardRedisConfig.setHeartbeatRetryTimes(Integer.parseInt(jedisProp.getProperty("redis.heartbeatRetryTimes")));
            shardRedisConfig.setHeartbeatRetryInterval(Long.parseLong(jedisProp.getProperty("redis.heartbeatRetryInterval")));
            shardRedisConfig.setRedisRetryTimes(Integer.parseInt(jedisProp.getProperty("redis.redisRetryTimes")));
            shardRedisConfig.setRedisRetryInterval(Long.parseLong(jedisProp.getProperty("redis.redisRetryInterval")));
            shardRedisConfig.setZkServers(jedisProp.getProperty("redis.zkServers"));
            shardRedisConfig.setZkSessionTimeout(Integer.parseInt(jedisProp.getProperty("redis.zkSessionTimeout")));

            shardClientFactory = new ShardClientFactory();
            shardClientFactory.setConfig(shardRedisConfig);
            shardClientFactory.setHosts(jedisProp.getProperty("redis.hosts"));
            jedis = shardClientFactory.createShardClient();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public RedisCommands getJedis() {
        return jedis;
    }
}
