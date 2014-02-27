package labs;

import labs.common.patterns.Func;
import labs.common.util.FileUtil;
import labs.common.util.IteratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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

    //private static String[] packageNames = PropertiesUtils.getProperty("packageNames").split(",");

    /**
     * 加载 labs 里的 class
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        classLoader = Thread.currentThread().getContextClassLoader();

        //file:/D:/Research/projects/jlabs/target/classes/
        final String classPath = classLoader.getResource("").getPath();
        List<String> classNames = FileUtil.getFiles(classPath, new Func<File, String>() {
            @Override
            public String execute(File file) {
                return file.getPath().replace(classPath, "").replace("\\", ".") + ".class";
            }
        });
        List<Class> classes = IteratorUtil.select(classNames, loadClass);

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

    private static Func<String, Class> loadClass = new Func<String, Class>() {
        @Override
        public Class execute(String className) {
            Enable enable = null;
            if (!className.contains("Application") && !className.contains("Sub")) {

                try {
                    Class clazz = classLoader.loadClass(className);
                    if (Sub.class.isAssignableFrom(clazz) && null != clazz.getAnnotation(Enable.class) && ((Enable) clazz.getAnnotation(Enable.class)).value()) {
                        enable = clazz.getMethod("execute").getAnnotation(Enable.class);
                        if (null == enable || enable.value()) {
                            return clazz;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };

    private static ClassLoader classLoader;

//    private static List<Class> loadPackage(String packageName, ClassLoader classLoader) throws ClassNotFoundException, NoSuchMethodException {
//        List<Class> classes = new ArrayList<Class>();
//        Class clazz = null;
//        Enable enable = null;
//        File directory = new File(classLoader.getResource(packageName.replace(".", "/")).getPath());
//        if (directory.isDirectory()) {
//            for (String file : directory.list()) {
//                if (!file.contains("Application") && !file.contains("Sub")) {
//                    clazz = classLoader.loadClass(packageName + "." + file.replace(".class", ""));
//                    if (Sub.class.isAssignableFrom(clazz) && null != clazz.getAnnotation(Enable.class) && ((Enable) clazz.getAnnotation(Enable.class)).value()) {
//                        enable = clazz.getMethod("execute").getAnnotation(Enable.class);
//                        if (null == enable || enable.value()) {
//                            classes.add(clazz);
//                        }
//                    }
//                }
//            }
//        }
//        return classes;
//    }
}