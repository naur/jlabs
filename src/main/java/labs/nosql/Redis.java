package labs.nosql;

import labs.Enable;
import labs.Sub;
import labs.repositories.redis.support.RedisCommands;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: jiaruizhi
 * Date: 13-5-15
 * Time: 下午6:07
 * To change this template use File | Settings | File Templates.
 */
@Enable(true)
public class Redis extends Sub {
    @Override
    public void execute() throws Exception {
        double code = Math.floor(Math.random() * 100);
        RedisCommands commands = cacheService.getJedis();
        String result = commands.set(perfix + "a", "Hello world ! " + code);
        System.out.println(result + ", code: " + code);
    }

    private JedisCacheClient cacheService = JedisCacheClient.getInstance();
    private String perfix = "labs:";
}
