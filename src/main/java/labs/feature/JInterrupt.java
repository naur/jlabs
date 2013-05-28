package labs.feature;

import labs.Enable;
import labs.Sub;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: jiaruizhi
 * Date: 13-5-22
 * Time: 上午11:33
 * To change this template use File | Settings | File Templates.
 */
@Enable(false)
public class JInterrupt extends Sub {

    @Override
    public void execute() throws Exception {
        final int[] array = new int[8000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(i + 1);
        }

        Thread thread = new Thread() {
            public void run() {
                try {
                    System.out.println(sort(array));
                    System.out.println(array.toString());
                } catch (Error err) {
                    err.printStackTrace();
                }
            }
        };

        thread.start();
        //TimeUnit.SECONDS.sleep(1);
        System.out.println("go to stop thread");
        thread.interrupt();
        System.out.println("finish main");
    }

    private int sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] < array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                System.out.println(String.valueOf(i) + "-" + String.valueOf(j));
            }
        }
        return array[0];
    }
}
