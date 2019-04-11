package com.liwinon.ASR.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具
 *
 * @author XiongJL
 */
public class FileUtil {
    /**
     * 创建文件
     *
     * @param file
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    public static void createFile(byte[] file, String filePath, String fileName) throws IOException {
        File totalFile = new File(filePath);
        if (!totalFile.exists()) {
            totalFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 删除文件
     */
    public static boolean delFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件不存在,咋个删除?");
            return true;
        } else {
            if (file.isFile()) {
                return file.delete();
            } else {
                System.out.println("删除目标不是文件!");
                return false;
            }
        }
    }

}
