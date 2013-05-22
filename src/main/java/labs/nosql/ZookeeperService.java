package labs.nosql;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 5/18/13.
 */
public class ZookeeperService {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperService.class);

    public void test() {
        double code = Math.floor(Math.random() * 100);
//        RedisCommands commands = cacheService.getJedis();
//        String result = commands.set(perfix + "a", "Hello world ! " + code);
//        System.out.println(result + ", code: " + code);

        String hostPort = "vmhost:2181,vmhost:2182,vmhost:2183/labs";

        cdl = new CountDownLatch(1);
        try {
            countDownLatch = new CountDownLatch(1);
            ZooKeeper zk = new ZooKeeper(hostPort, 3000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected && countDownLatch != null) {
                        //在收到连接事件KeeperState.SyncConnected时，connectedSignal被创建时，计数为1，代表需要在
                        //释放所有等待线程前发生事件的数量。在调用一次countDown()方法后，此计数器会归零，await操作返回。
                        countDownLatch.countDown();
                    } else if (event.getState() == Event.KeeperState.Expired) {//注意KeeperState的Expired枚举值

                    }
                    logger.warn("zookeeper state change: " + event.toString());
                }
            });
            countDownLatch.await();

//            String result = zk.create("/" + String.valueOf(code), "aaa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            System.out.println(result);


            try {
                String path = zk.create("/leader", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } catch (Exception ex) {
            }

            zk.create("/leader/lock-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            watchNode("/leader", zk);

            System.out.println("-------");

            cdl.await();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        System.out.println("zookeeper connected. ");
    }

    private void watchNode(final String path, final ZooKeeper zk) throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren(path, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.toString());
                if (event.getType() == Event.EventType.NodeChildrenChanged ||
                        event.getType() == Event.EventType.NodeCreated ||
                        event.getType() == Event.EventType.NodeDeleted) {
                    try {
                        watchNode(path, zk);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (KeeperException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        System.out.println(children.toString());
    }

    private CountDownLatch countDownLatch;
    private CountDownLatch cdl;
}
