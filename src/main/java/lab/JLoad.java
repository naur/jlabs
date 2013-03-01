package lab;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created with IntelliJ IDEA.
 * User: jiaruizhi
 * Date: 13-3-1
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */

@Enable(true)
public class JLoad extends Sub {
    @Override
    public void execute() throws Exception {
        System.out.println(format(1, "Java Loader Tree."));
        //ClassLoaderTree
        ClassLoader loader = JLoad.class.getClassLoader();
        while (loader != null) {
            System.out.println(loader.toString());
            loader = loader.getParent();
        }

        //Class.forName
        System.out.println(format(1, "End"));
    }


    //
    public class CustomClassLoader extends ClassLoader {
        private String rootDir;

        public CustomClassLoader(String rootDir) {
            this.rootDir = rootDir;
        }

        /**
         *真正完成类的加载工作是通过调用 defineClass来实现的；
         * 而启动类的加载过程是通过调用 loadClass来实现的。
         * 前者称为一个类的定义加载器（defining loader），后者称为初始加载器（initiating loader）。
         * 在 Java 虚拟机判断两个类是否相同的时候，使用的是类的定义加载器。
         * 也就是说，哪个类加载器启动类的加载过程并不重要，重要的是最终定义这个类的加载器。
         * 两种类加载器的关联之处在于：一个类的定义加载器是它引用的其它类的初始加载器。
         * 如类 com.example.Outer引用了类 com.example.Inner，则由类 com.example.Outer的定义加载器负责启动类 com.example.Inner的加载过程。
         * 方法 loadClass()抛出的是 java.lang.ClassNotFoundException异常；方法 defineClass()抛出的是 java.lang.NoClassDefFoundError异常。
         */
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classData = getClassData(name);
            if (classData == null) {
                throw new ClassNotFoundException();
            } else {
                return defineClass(name, classData, 0, classData.length);
            }
        }

        private byte[] getClassData(String className) {
            String path = classNameToPath(className);
            try {
                InputStream ins = new FileInputStream(path);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];
                int bytesNumRead = 0;
                while ((bytesNumRead = ins.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesNumRead);
                }
                return baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String classNameToPath(String className) {
            return rootDir + File.separatorChar +
                    className.replace('.', File.separatorChar) + ".class";
        }
    }
}
