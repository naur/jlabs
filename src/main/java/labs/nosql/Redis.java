package labs.nosql;

import labs.Enable;
import labs.Sub;
import labs.repositories.redis.support.RedisCommands;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: jiaruizhi
 * Date: 13-5-15
 * Time: 下午6:07
 * To change this template use File | Settings | File Templates.
 */
@Enable(true)
public class Redis extends Sub implements Watcher, Runnable, DataMonitor.DataMonitorListener {
    @Override
    public void execute() throws Exception {
        String hostPort = "10.12.216.68:2181,10.12.216.68:2182,10.12.216.68:2183";
        String znode = "labs";
        String exec[] = new String[args.length - 3];
        System.arraycopy(args, 3, exec, 0, exec.length);
        try {
            new Executor(hostPort, znode, filename, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ZooKeeper zk = new ZooKeeper(, 300, this);
        DataMonitor
    }

    private void replicatedZookeeper(String key, String value) {
        double code = Math.floor(Math.random() * 100);
        RedisCommands commands = cacheService.getJedis();
        String result = commands.set(perfix + "a", "Hello world ! " + code);
        System.out.println(result + ", code: " + code);
    }

    private JedisCacheClient cacheService = JedisCacheClient.getInstance();
    private String perfix = "labs:";

    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
