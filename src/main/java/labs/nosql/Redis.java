package labs.nosql;

import labs.Enable;
import labs.Sub;
import labs.repositories.redis.support.RedisCommands;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: jiaruizhi
 * Date: 13-5-15
 * Time: 下午6:07
 * To change this template use File | Settings | File Templates.
 */
@Enable(true)
public class Redis extends Sub {
    private static final Logger logger = LoggerFactory.getLogger(Redis.class);

    public void execute() throws Exception {
        double code = Math.floor(Math.random() * 100);
        replicatedZookeeper("a", code);
    }


    private void replicatedZookeeper(String key, Object value) {
        RedisCommands commands = cacheService.getJedis();
        String result = commands.set(perfix + key, "Hello world ! " + String.valueOf(value));
        System.out.println(result + ", key=" + key + ", value=: " + value);
    }

    private String perfix = "labs:";
    private JedisCacheClient cacheService = JedisCacheClient.getInstance();
}
