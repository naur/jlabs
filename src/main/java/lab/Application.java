package lab;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2/23/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class Application {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Class clazz = null;
        Enable enable = null;
        Object object = null;
        List<Class> classes = new ArrayList<Class>();
        File directory = new File(classLoader.getResource("lab").getPath());
        if (directory.isDirectory()) {
            for (String file : directory.list()) {
                if (!file.contains("Application") && !file.contains("Sub")) {
                    clazz = classLoader.loadClass("lab." + file.replace(".class", ""));
                    if (Sub.class.isAssignableFrom(clazz) && null != clazz.getAnnotation(Enable.class) && ((Enable) clazz.getAnnotation(Enable.class)).value()) {
                        enable = clazz.getMethod("execute").getAnnotation(Enable.class);
                        if (null == enable || enable.value()) {
                            classes.add(clazz);
                        }
                    }
                }
            }
        }

        Sub sub = null;
        for (Class cla : classes) {
            sub = (Sub) cla.newInstance();
            sub.execute();
        }
    }
}