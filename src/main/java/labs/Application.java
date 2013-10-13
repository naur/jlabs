package labs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedExceptionUtils;

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

    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    private static String[] packageNames = new String[]{
            "labs.feature",
            "labs.algorithms",
            "labs.nosql",
            "labs.database"
    };

    /**
     * 加载 labs 里的 class
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        List<Class> classes = new ArrayList<Class>();
        for (String packageName : packageNames) {
            classes.addAll(loadPackage(packageName, classLoader));
        }

        Sub sub = null;
        for (Class cla : classes) {
            try {
                sub = (Sub) cla.newInstance();
                sub.execute();
            } catch (Exception ex) {
                logger.error("ERROR", ex);
            }
        }

        logger.debug("___________complete___________");
        //System.exit(0);
    }

    private static List<Class> loadPackage(String packageName, ClassLoader classLoader) throws ClassNotFoundException, NoSuchMethodException {
        List<Class> classes = new ArrayList<Class>();
        Class clazz = null;
        Enable enable = null;
        File directory = new File(classLoader.getResource(packageName.replace(".", "/")).getPath());
        if (directory.isDirectory()) {
            for (String file : directory.list()) {
                if (!file.contains("Application") && !file.contains("Sub")) {
                    clazz = classLoader.loadClass(packageName + "." + file.replace(".class", ""));
                    if (Sub.class.isAssignableFrom(clazz) && null != clazz.getAnnotation(Enable.class) && ((Enable) clazz.getAnnotation(Enable.class)).value()) {
                        enable = clazz.getMethod("execute").getAnnotation(Enable.class);
                        if (null == enable || enable.value()) {
                            classes.add(clazz);
                        }
                    }
                }
            }
        }
        return classes;
    }
}