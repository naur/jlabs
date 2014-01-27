package labs.common.util;

import labs.common.patterns.Func;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiaruizhi
 * Date: 12-12-28
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {
    //保存到文件
    public static void toFile(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, false);
            int readLength;
            byte[] buf = new byte[4096];
            while (((readLength = inputStream.read(buf)) != -1)) {
                outputStream.write(buf, 0, readLength);
                outputStream.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    //保存到文件
    public static void toFile(InputStream inputStream, String filePath, String fileName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileUtil.toFile(inputStream, new File(filePath + fileName));
    }

    //删除文件
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        boolean result = false;
        if (file.exists() && file.isFile()) {
            result = file.delete();
        }
        return result;
    }

    public static void copyFile(String source, String destination) {

    }

    //获取目录下最新文件
    public static File getLatestFile(String directory) {
        File result = null;
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            return result;
        }

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (null == result) result = file;
                if (file.lastModified() > result.lastModified()) result = file;
            }
        }

        return result;
    }

    /**
     * 获取目录下所有文件
     *
     * @param directory
     * @param traverse
     * @return
     */
    public static List<File> getFiles(String directory, boolean... traverse) {
        List<File> result = new ArrayList<File>();
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            return result;
        }

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory() && traverse.length > 0 && traverse[0]) {
                result.addAll(FileUtil.getFiles(file.getPath(), traverse));
            } else {
                result.add(file);
            }
        }

        return result;
    }

    /**
     * 获取目录下所有文件
     *
     * @param directory
     * @param selector
     * @return
     */
    public static List<String> getFiles(String directory, Func<File, String>... selector) {
        List<String> result = new ArrayList<String>();
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            return result;
        }

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                result.addAll(FileUtil.getFiles(file.getPath(), selector));
            } else {
                String temp = selector.length == 0 ? file.getPath() : selector[0].execute(file);
                if (null != temp) result.add(temp);
            }
        }

        return result;
    }

    //按照格式创建新的文件名
    public static String newFileName(String fileName) {
        //UUID.randomUUID().toString();
        return (new SimpleDateFormat("yyyyMMdd-HHmmss-SSS")).format(new Date()) + "-" + fileName(fileName) + fileType(fileName);
    }

    //获取文件扩展名
    public static String fileType(String fileName) {
        String type = "";
        if (null != fileName && fileName.lastIndexOf(".") >= 0) {
            type = fileName.substring(fileName.lastIndexOf("."));
        }
        return type;
    }

    //获取文件名
    public static String fileName(String fileName) {
        String name = "";
        if (null != fileName && fileName.lastIndexOf(".") >= 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        }
        return name;
    }
}
